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
    ArrayList<Integer> width = new ArrayList<>(Arrays.asList(20, 10, 20, 30, 50));
    private int totalWidth = 130;
    private boolean leftAlign = false;

    DBInterface(){
        leftAlignment();
        constructSeparateLine();
        setFormat();
    }

    public void leftAlignment(){
        this.leftAlign = true;
    }

    public void setFormat() {
        this.format = "";
        if(leftAlign){
            for(int w:width)
                this.format += "%-" + w + "s ";
        }else {
            for(int w:width)
                this.format += "%" + w + "s ";
        }
    }

    private void constructSeparateLine(){
        for(int i = 0; i < totalWidth; i++){
            separateLine.append("-");
        }
    }
    private void separateLine(){  System.out.println(separateLine);  }

    public SimpleDateFormat getDateFormat(){  return sdf;  }

    private void tableHead(){
        separateLine();
        System.out.printf(format, "Title", "Status", "Due Date", "Project", "Description");
        System.out.println();
        separateLine();
    }

    private void showLine(Task t){
        System.out.format(format, t.getTitle(), t.getStatus(), sdf.format(t.getDueDate()), t.getProject(), t.getDescription());
        System.out.println();
    }

    public void showTaskDB(){
        tableHead();
        for(Task t: database.getDataBase()){
            showLine(t);
        }
        separateLine();
    }

    public void showTaskDBByProject(){
        tableHead();
        for(Task t: database.taskByProject()){
            showLine(t);
        }
        separateLine();
    }

    public void showTaskByDueDate(){
        tableHead();
        for(Task t: database.taskByDate()){
            showLine(t);
        }
        separateLine();
    }

    public void showTaskNotDone(){
        tableHead();
        for(Task t: database.taskNotDone()){
            showLine(t);
        }
        separateLine();
    }

    public void showTask(Task t){
        tableHead();
        showLine(t);
        separateLine();
    }

    public void addTask(Task t){  database.addTask(t); }

    public void removeTask(String title){  database.removeTask(database.searchTask(title));    }

    public Task getTask(String title){
        if(database.hasTask(title)){
            return database.getTask(title);
        }
        return null;
    }

    public void saveDB(){ database.saveTaskDB();}
}