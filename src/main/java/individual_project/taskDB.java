package individual_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class taskDB {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    private ArrayList<Task> dataBase;
    private Set<String> titles;
    private String file_path = System.getProperty("user.dir") + "/ToDoLy.SDA";
    private Scanner reader = new Scanner(System.in);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");

    taskDB(){
        dataBase = new ArrayList<>();
        titles = new HashSet<>();
        initDB();
    }

    private boolean loadDB(){
        try{
            Scanner scanner = new Scanner(new File(this.file_path));
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] taskDetail = line.split("|@|", -1);

                Task t = new Task();
                t.setTitle(taskDetail[0]);
                t.setStatus(Status.fromString(taskDetail[1]));
                t.setDueDate(sdf.parse(taskDetail[2]));
                t.setProject(taskDetail[3]);
                t.setDescription(taskDetail[4]);
                dataBase.add(t);
                titles.add(taskDetail[0].toLowerCase());
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
                System.out.println("File is corrupted. Remove and create a new database?\n1) Yes    2) No");
                String selected = reader.nextLine();
                if(selected.equals("1") || selected.equals("Y") || selected.equals("Yes")){
                    DBFile.delete();
                    createDB();
                }else {
                    return;
                }
            }
        }
    }

    public void saveTaskDB(){
        try {
            FileWriter write = new FileWriter(this.file_path);
            for(Task t:dataBase){
                //"Title", "Status", "Due Date", "Project", "Description"
                StringBuilder taskSB = new StringBuilder();
                taskSB.append(t.getTitle()).append("|@|");
                taskSB.append(t.getStatus()).append("|@|");
                taskSB.append(t.getDueDate()).append("|@|");
                taskSB.append(t.getProject()).append("|@|");
                taskSB.append(t.getDescription());
                write.append(taskSB);
            }
            write.close();
        }catch (IOException e) {
            System.out.println("There is problem to write the task database. Check!");
        }
    }

    public boolean addTask(Task t){
        if(titles.contains(t.getTitle())){
            return false;
        }else {
            dataBase.add(t);
            return true;
        }
    }

    public boolean hasTask(Task t){  return titles.contains(t.getTitle());  }

    public boolean hasTask(String taskTitle){ return titles.contains(taskTitle); };

    public boolean removeTask(Task t){
        if(hasTask(t)){
            dataBase.remove(searchTask(t.getTitle()));
            return true;
        }else {
            System.out.println("There is no such task in data base. Check again!");
            return false;
        }
    }

    public boolean removeTask(int index){
        if(index < dataBase.size() && index >= 0){
            dataBase.remove(index);
            return true;
        }else {
            return false;
        }
    }

    public ArrayList<Task> taskByDate(){
        ArrayList<Task> taskByDate = new ArrayList<>();
        for(int i = 0; i < dataBase.size(); i ++){
            taskByDate.add(dataBase.get(i));
        }
        Collections.sort(taskByDate, new Comparator<Task>() {
            @Override
            public int compare(Task u1, Task u2) {
                return u1.getDueDate().compareTo(u2.getDueDate());
            }
        });
        return taskByDate;
    }

    public ArrayList<Task> taskByProject(){
        ArrayList<Task> taskByProject = new ArrayList<>();
        for(int i = 0; i < dataBase.size(); i ++){
            taskByProject.add(dataBase.get(i));
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
            if(!dataBase.get(i).getStatus().equals(Status.DONE)){
                taskNotDone.add(dataBase.get(i));
            }
        }
        return taskNotDone;
    }

    public ArrayList<Task> getDataBase() { return dataBase;  }
    public Task getTask(int index){  return dataBase.get(index);  }
    public Task getTask(String task){return dataBase.get(searchTask(task));  }

    public int searchTask(String t){
        for(int i = 0; i < dataBase.size(); i ++){
            if(dataBase.get(i).equals(t)){
                return i;
            }
        }
        return -1;
    }
}
