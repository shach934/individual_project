package individual_project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * DBInterface, an interface to wrap the task database.
 *     private String taskTableFormat: format for display the task in the table.
 *     private StringBuilder separateLine: the line to separate the rows in the task table.
 *     private SimpleDateFormat sdf: the Date format to display in the table.
 *     private taskDB database: database, that is in charge of sort, add/remove, save/read tasks.
 *     ArrayList<Integer> width = new ArrayList<>(Arrays.asList(20, 8, 25, 30, 50));
 *     private boolean leftAlign = false;
 *
 * Methods:
 *  DBInterface(String), constructor, the string is the SimpleDateFormat.
 *  leftAlignment():void, to set if the field in the table is left aligned or not.
 *  setTaskTableFormat():void, setup the task table format. it mainly to construct the format string.
 *  constructSeparateLine():void, construct a separate line to construct the table.
 *  separateLine():void, print a separate line.
 *  tableHead():void, print out the table head.
 *  showLine(Task):void, print out a task in a line.
 *  showTaskDB(String):void, printout the task table by specified order.
 *  showTask(Task):void, displace a task in a line.
 *  addTask(Task):void, add a task to the database.
 *  removeTask(String):void, remove a task from task database by a task title.
 *  getTask(String):Task, find and return a task by title.
 *  hasTask(String):boolean, check if a task exists in the task database by title.
 *  saveDB():void, save the task database to the file.
 *  counts():int[], return the done/undone tasks in the task database.
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

    private void printSeparateLine() {
        System.out.println(separateLine);
    }

    private void tableHead() {
        printSeparateLine();
        System.out.printf(taskTableFormat, "Title", "Status", "Due Date", "Project", "Description");
        System.out.println();
        printSeparateLine();
    }

    private void showLine(Task t) {
        System.out.format(taskTableFormat, t.getTitle(), t.getStatus(), sdf.format(t.getDueDate()), t.getProject(), t.getDescription());
        System.out.println();
    }

    public void showTaskDB(String order) {
        tableHead();
        ArrayList<Task> db = new ArrayList<>();
        switch (order){
            case "default":
                db = database.getDataBase();
                break;
            case "DueDate":
                db = database.taskByDate();
                break;
            case "Project":
                db = database.taskByProject();
                break;
            case "unDone":
                db = database.taskNotDone();
                break;
        }
        for (Task t : db) {
            showLine(t);
        }
        printSeparateLine();
    }

    public void showTask(Task t) {
        tableHead();
        showLine(t);
        printSeparateLine();
    }

    public void addTask(Task t) {
        database.addTask(t);
    }

    public void removeTask(String title) {
        Task t = database.getTask(title);
        System.out.println("The following task is removed!");
        showTask(t);
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