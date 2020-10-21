package individual_project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class DBInterface {
    private taskDB database = new taskDB();
    private String format;
    private StringBuilder separateLine = new StringBuilder();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");

    // determines display format, later open API to let user to set the format.
    ArrayList<Integer> width = new ArrayList<>(Arrays.asList(20, 10, 20, 30, 50));
    private boolean leftAlign = false;

    DBInterface() {
        leftAlignment();
        constructSeparateLine();
        setFormat();
    }

    public void leftAlignment() {
        this.leftAlign = true;
    }

    public void setFormat() {
        StringBuilder sb = new StringBuilder();
        if (leftAlign) {
            for (int w : width)
                sb.append("%-").append(w).append("s ");

        } else {
            for (int w : width)
                sb.append("%").append(w).append("s ");
        }
        this.format = sb.toString();
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

    public SimpleDateFormat getDateFormat() {
        return sdf;
    }

    private void tableHead() {
        separateLine();
        System.out.printf(format, "Title", "Status", "Due Date", "Project", "Description");
        System.out.println();
        separateLine();
    }

    private void showLine(Task t) {
        System.out.format(format, t.getTitle(), t.getStatus(), sdf.format(t.getDueDate()), t.getProject(), t.getDescription());
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
        database.removeTask(database.searchTask(title));
    }

    public Task getTask(String title) {
        if (database.hasTask(title)) {
            return database.getTask(title);
        }
        return null;
    }

    public void saveDB() {
        database.saveTaskDB();
    }

    public void test() {
    }
}