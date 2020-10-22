package individual_project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * DBInterface, an interface to wrap the task database.
 *
 *
 * Methods:
 * DBInterface(String), constructor, the string is the SimpleDateFormat.
 * leftAlignment():void, to set if the field in the table is left aligned or not.
 * setTaskTableFormat():void, setup the task table format. it mainly to construct the format string.
 * constructSeparateLine():void, construct a separate line to construct the table.
 * separateLine():void, print a separate line.
 * tableHead():void, print out the table head.
 * showLine(Task):void, print out a task in a line.
 * showTaskDB():void, printout the task table by its added time.
 * showTaskDBByProject():void, print the task table by
 * showTaskByDueDate
 * showTaskNotDone
 * showTask
 * addTask
 * removeTask
 * getTask
 * hasTask
 * saveDB
 * counts
 */
public class DBInterface {
    private String taskTableFormat;
    private StringBuilder separateLine;
    private SimpleDateFormat sdf;
    private taskDB database;
    ArrayList<Integer> width = new ArrayList<>(Arrays.asList(20, 8, 25, 30, 50));
    private boolean leftAlign = false;

    DBInterface(String dateFormat) {
        sdf = new SimpleDateFormat(dateFormat);
        database  = new taskDB(dateFormat);
        separateLine  = new StringBuilder();
        leftAlignment();
        constructSeparateLine();
        setTaskTableFormat();
    }

    public void leftAlignment() {
        this.leftAlign = true;
    }

    public void setTaskTableFormat() {
        StringBuilder sb = new StringBuilder();
        if (leftAlign) {
            for (int w : width)
                sb.append("%-").append(w).append("s ");

        } else {
            for (int w : width)
                sb.append("%").append(w).append("s ");
        }
        this.taskTableFormat = sb.toString();
    }

    private void constructSeparateLine() {
        int totalWidth = 0;
        for (int w : width)
            totalWidth += w;

        for (int i = 0; i < totalWidth; i++) {
            separateLine.append("-");
        }
    }

    private void separateLine() {
        System.out.println(separateLine);
    }

    private void tableHead() {
        separateLine();
        System.out.printf(taskTableFormat, "Title", "Status", "Due Date", "Project", "Description");
        System.out.println();
        separateLine();
    }

    private void showLine(Task t) {
        System.out.format(taskTableFormat, t.getTitle(), t.getStatus(), sdf.format(t.getDueDate()), t.getProject(), t.getDescription());
        System.out.println();
    }

    public void showTaskDB() {
        tableHead();
        for (Task t : database.getDataBase()) {
            showLine(t);
        }
        separateLine();
    }

    public void showTaskDBByProject() {
        tableHead();
        for (Task t : database.taskByProject()) {
            showLine(t);
        }
        separateLine();
    }

    public void showTaskByDueDate() {
        tableHead();
        for (Task t : database.taskByDate()) {
            showLine(t);
        }
        separateLine();
    }

    public void showTaskNotDone() {
        tableHead();
        for (Task t : database.taskNotDone()) {
            showLine(t);
        }
        separateLine();
    }

    public void showTask(Task t) {
        tableHead();
        showLine(t);
        separateLine();
    }

    public void addTask(Task t) {
        database.addTask(t);
    }

    public void removeTask(String title) {
        database.removeTask(title);
    }

    public Task getTask(String title) {
        if (database.hasTask(title)) {
            return database.getTask(title);
        }
        return null;
    }

    public boolean hasTask(String title){ return database.hasTask(title); }

    public void saveDB() {
        database.saveTaskDB();
    }

    public int[] counts(){
        return database.getCount();
    }
}