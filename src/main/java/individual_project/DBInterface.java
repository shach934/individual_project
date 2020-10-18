package individual_project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class DBInterface{
    private taskDB database = new taskDB();

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    // public static final String ANSI_YELLOW = "\u001B[33m";
    // public static final String ANSI_BLUE = "\u001B[34m";
    // public static final String ANSI_PURPLE = "\u001B[35m";
    // public static final String ANSI_CYAN = "\u001B[36m";
    public static final String italic = "\033[3m";
    public static final String BOLD = "\033[1m";
    public static final String endFormat = "\033[0m";

    private String format;
    private StringBuilder separateLine = new StringBuilder("");
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");

    // determines display format, later open API to let user to set the format.
    ArrayList<Integer> width = new ArrayList<Integer>(Arrays.asList(20, 10, 20, 30, 50));
    private int totalWidth = 130;
    private boolean leftAlign = true;

    public void setAlignment(){
        this.leftAlign = true;
    }

    public void setFormat() {
        this.format = "";
        if (leftAlign) {
            for (int w : width)
                this.format += "%-" + w + "s ";
        } else {
            for (int w : width)
                System.out.println(separateLine);
            System.out.printf(format, "Title", "Status", "Due Date", "Project", "Description");
            System.out.println();
            System.out.println(separateLine);
        }
    }

    private void separateLine(){
        System.out.println(separateLine);
    }

    public SimpleDateFormat getDateFormat(){
        return sdf;
    }

    private void tableHead(){
        System.out.println(separateLine);
        System.out.printf(format, "Title", "Status", "Due Date", "Project", "Description");
        System.out.println();
        System.out.println(separateLine);
    }

    public void showTaskDB(){
        tableHead();
        for(Task t: database.getDataBase()){
            System.out.format(format, t.getTitle(), t.getStatus(), sdf.format(t.getDueDate()), t.getProject(), t.getDescription());
            System.out.println();
        }
        separateLine();
    }

    public void showTaskDBByProject(){
        tableHead();
        for(Task t: database.taskByProject()){
            System.out.format(format, t.getTitle(), t.getStatus(), sdf.format(t.getDueDate()), t.getProject(), t.getDescription());
            System.out.println();
        }
        separateLine();
    }

    public void showTaskByDueDate(){
        tableHead();
        for(Task t: database.taskByDate()){
            System.out.format(format, t.getTitle(), t.getStatus(), sdf.format(t.getDueDate()), t.getProject(), t.getDescription());
            System.out.println();
        }
        separateLine();
    }

    public void showTaskNotDone(){
        tableHead();
        for(Task t: database.taskNotDone()){
            System.out.format(format, t.getTitle(), t.getStatus(), sdf.format(t.getDueDate()), t.getProject(), t.getDescription());
            System.out.println();
        }
        separateLine();
    }

    public void showTask(Task t){
        tableHead();
        System.out.format(format, t.getTitle(), t.getStatus(), sdf.format(t.getDueDate()), t.getProject(), t.getDescription());
        System.out.println();
        separateLine();
    }

    public void addTask(Task t){
        database.addTask(t);
    }

    public Task getTask(String title){
        if(database.hasTask(title)){
            return database.getTask(title);
        }
        return null;
    }
}