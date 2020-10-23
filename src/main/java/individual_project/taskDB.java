package individual_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class taskDB {

    /**
     * taskDB: task database. it uses a ArrayList<Task> to keep all the tasks. a Set to keep all the task titles.
     * Main responsibilities of taskDB:
     * 1 read/save tasks from/to file on disk.
     * 2 keep, add/remove task.
     * 3 sort tasks by different criteria.
     *
     *
     * Fields:
     *  ArrayList<Task> dataBase;
     *  Set<String> titleSet;
     *  String file_path = System.getProperty("user.dir") + "/ToDoLy.SDA";
     *  Scanner reader;
     *  SimpleDateFormat sdf;
     *  int done = 0;
     *  int notDone = 0;
     *
     *
     * Methods:
     * taskDB() constructor.
     * loadDB():Boolean, read the tasks from the file.
     * createDB():Boolean, create the database file if the file not exist.
     * initDB():void, initiate the database, it will call loadDB() and createDB().
     * countTasks(): void, count the done tasks for welcome information.
     * getCount(): int[], return the counted number of done tasks.
     * saveTaskDB():void, save the task database to the file.
     * addTask(Task):void, add a task to the task array.
     * hasTask(String):boolean, check if there is a task by its title.
     * removeTask(String):boolean remove a task by its title.
     * taskByDate():ArrayList<Task>, copy and sort the task array by due date, return the sorted task arraylist.
     * taskByProject():ArrayList<Task>, copy and sort the task array by Project, return the sorted task arraylist.
     * taskNotDone():ArrayList<Task>, copy and sort the task array by if Done, return the sorted task arraylist.
     * getDataBase():ArrayList<Task>, return the task arraylist.
     * getTask():Task, get a task by title.
     */

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    private ArrayList<Task> dataBase;
    private Set<String> titleSet;
    private String file_path = System.getProperty("user.dir") + "/ToDoLy.SDA";
    private Scanner reader;
    private SimpleDateFormat sdf;
    private int done = 0;
    private int notDone = 0;

    taskDB(String strDateFormat){
        reader = new Scanner(System.in);
        dataBase = new ArrayList<>();
        titleSet = new HashSet<>();
        this.sdf = new SimpleDateFormat(strDateFormat);
        initDB();
    }

    private boolean loadDB(){
        try{
            Scanner scanner = new Scanner(new File(this.file_path));
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] taskDetail = line.split("§@§", 0);
                Task t = new Task();
                t.setTitle(taskDetail[0]);
                t.setStatus(Status.fromString(taskDetail[1]));
                t.setDueDate(sdf.parse(taskDetail[2]));
                t.setProject(taskDetail[3]);
                t.setDescription(taskDetail[4]);
                dataBase.add(t);
                titleSet.add(taskDetail[0].toLowerCase());
                if(t.getStatus().toString().equals("Done"))
                    this.done += 1;
                else
                    this.notDone += 1;
            }
            scanner.close();
            return true;
        } catch (FileNotFoundException | ParseException e) {
            System.out.println(ANSI_RED + "An error occurred, check accessibility!" + ANSI_RESET);
            return false;
        }
    }

    private Boolean createDB(){
        try {
            File newDB = new File(this.file_path);
            if (newDB.createNewFile()) {
                System.out.println(ANSI_GREEN + "Database file created: <" + newDB.getName() + ">. Enjoy!" + ANSI_RESET);
                return true;
            }
        } catch (IOException e) {
            System.out.println("Unable to create file. Try with another directory.");
        }
        return false;
    }

    public void initDB(){
        System.out.println("Loading tasks database: <ToDoLy.sda>.");
        File DBFile = new File(this.file_path);
        if(!DBFile.exists()){
            System.out.println(ANSI_RED + "Does NOT found the database file. Creating a new one!" + ANSI_RESET);
            createDB();
        }else{
            if(!loadDB()){
                System.out.println(ANSI_RED + "File is corrupted. Remove and create a new database?" + ANSI_RESET + "\n1) Yes    2) No");
                String selected = reader.nextLine();
                if(selected.equals("1") || selected.equals("Y") || selected.equals("Yes")){
                    DBFile.delete();
                    createDB();
                }else {
                    countTasks();
                    return;
                }
            }
        }
    }

    private void countTasks() {
        for(Task t: dataBase){
            if(t.getStatus().equals(Status.DONE)){
                this.done += 1;
            }else{
                notDone += 1;
            }
        }
    }

    public int[] getCount(){
        int[] counts = new int[2];
        counts[0] = notDone;
        counts[1] = done;
        return counts;
    }

    public void saveTaskDB(){
        try {
            FileWriter write = new FileWriter(this.file_path);
            for(Task t:dataBase){
                //"Title", "Status", "Due Date", "Project", "Description"
                StringBuilder taskSB = new StringBuilder();
                taskSB.append(t.getTitle()).append("§@§");
                taskSB.append(t.getStatus()).append("§@§");
                taskSB.append(sdf.format(t.getDueDate())).append("§@§");
                taskSB.append(t.getProject()).append("§@§");
                taskSB.append(t.getDescription()).append("\n");
                write.append(taskSB);
            }
            write.close();
        }catch (IOException e) {
            System.out.println("There is problem to write the task database. Check!");
        }
    }

    public void addTask(Task t){ dataBase.add(t); }

    public boolean hasTask(String taskTitle){ return titleSet.contains(taskTitle.toLowerCase()); }

    public boolean removeTask(String title){
        if(hasTask(title)){
            dataBase.remove(title);
            titleSet.remove(title);
            return true;
        }else {
            System.out.println("There is no such task in data base. Check again!");
            return false;
        }
    }

    public ArrayList<Task> taskByDate(){
        ArrayList<Task> taskByDate = new ArrayList<>();
        for (Task t : dataBase) {
            taskByDate.add(new Task(t));
        }
        Collections.sort(taskByDate, new Comparator<>() {
            @Override
            public int compare(Task u1, Task u2) {
                return u1.getDueDate().compareTo(u2.getDueDate());
            }
        });
        return taskByDate;
    }

    public ArrayList<Task> taskByProject(){
        ArrayList<Task> taskByProject = new ArrayList<>();
        for (Task t : dataBase) {
            taskByProject.add(new Task(t));
        }
        Collections.sort(taskByProject, new Comparator<Task>() {
            @Override
            public int compare(Task u1, Task u2) {
                return u1.getProject().compareTo(u2.getProject());
            }
        });
        return taskByProject;
    }

    public ArrayList<Task> taskNotDone(){
        ArrayList<Task> taskNotDone = new ArrayList<>();
        for(int i = 0; i < dataBase.size(); i ++){
            Task t = dataBase.get(i);
            if(!t.getStatus().equals("Done")){
                taskNotDone.add(new Task(t));
            }
        }
        return taskNotDone;
    }

    public ArrayList<Task> getDataBase() { return dataBase;  }

    public Task getTask(String title){
        for(int i = 0; i < dataBase.size(); i++){
            Task t = dataBase.get(i);
            if(t.getTitle().equals(title))
                return t;
        }
        return null;
    }
}
