package individual_project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ToDoLyUI {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_YELLOW = "\u001b[33m";

    ArrayList<String> options;
    Map<String, Runnable> commands;
    Scanner read;
    DBInterface taskInter;
    StringBuilder allStatus;
    SimpleDateFormat sdf;
    String dateFormat = "yyyy-MM-dd HH:mm:ss";

    ToDoLyUI(){

        read = new Scanner(System.in);
        allStatus  = new StringBuilder();
        sdf = new SimpleDateFormat(dateFormat);
        taskInter = new DBInterface(dateFormat);
        getAllStatus();
        printWelcome();

        options = new ArrayList<>();
        options.add("1) View Task");
        options.add("2) Add Task");
        options.add("3) Edit Task");
        options.add("4) Save and QUit");
        commands = new HashMap<>();
        StringBuilder optSB = new StringBuilder();
        for(String op:options){
            optSB.append(op).append("\n");
        }
        commands.put("1", this::showTasks);
        commands.put("2", this::addTask);
        commands.put("3", this::editTask);
        commands.put("4", this::saveExit);
        boolean exit = false;
        String selected;
        while (!exit){
            System.out.println(optSB);
            selected = read.nextLine().trim();
            if(commands.containsKey(selected)){
                commands.get(selected).run();
                if(selected.equals("4"))
                    exit = true;
            }else {
                System.out.println(ANSI_RED + "No such option, check again!" + ANSI_RESET);
            }
        }
    }

    private void editTask() {
        boolean validOpt = false;
        while (!validOpt){
            System.out.println("1) Update a Task\n2) Mark a task as Done\n3) Remove a Task\n4) CANCEL");
            String selected = read.nextLine().trim();
            validOpt = true;
            switch (selected){
                case "1":
                    updateTask();
                    break;
                case "2":
                    markDone();
                    break;
                case "3":
                    removeTask();
                    break;
                case "4":
                    break;
                default:
                    System.out.println(ANSI_RED + "No such option, try again!" + ANSI_RESET);
                    validOpt = false;
                    break;
            }
        }
    }

    private void removeTask() {
        String title;
        boolean doneEdit = false;
        while(!doneEdit){
            System.out.println("The task to be removed or CANCEL to exit:");
            title = read.nextLine().trim();
            if(title.trim().equals("CANCEL"))
                break;
            Task t = taskInter.getTask(title);
            if(t != null){
                taskInter.removeTask(title);
                doneEdit = true;
            }else {
                System.out.println(ANSI_RED + "The Task doesn't exit in the database, try again!" + ANSI_RESET);
            }
        }
    }

    private void markDone() {
        String title;
        boolean doneEdit = false;
        while(!doneEdit){
            System.out.println("The task to be marked as Done or CANCEL to exit:");
            title = read.nextLine().trim();
            if(title.trim().equals("CANCEL"))
                break;
            Task t = taskInter.getTask(title);
            if(t != null){
                taskInter.showTask(t);
                t.setStatus(Status.DONE);
                doneEdit = true;
            }else {
                System.out.println(ANSI_RED + "The Task doesn't exit in the database, try again!" + ANSI_RESET);
            }
        }
    }

    private void printWelcome()
    {
        System.out.println("Welcome to the ToDoLy!");
        int[] counts = taskInter.counts();
        System.out.println("You have " + ANSI_YELLOW + counts[0] + ANSI_RESET + " tasks todo and " +ANSI_GREEN + counts[1] +ANSI_RESET+ " tasks are done!");
    }

    private void saveExit() { taskInter.saveDB(); }

    private void getAllStatus(){
        int count = 1;
        for(Status s:Status.values()){
            allStatus.append(count + ") ").append(s.getText() + " ");
            count += 1;
        }
    }

    private void showTasks() {
        boolean validOpt = false;
        while (!validOpt){
            System.out.println("1) Show Tasks by add time\n2) Show Tasks by Due date\n3) Show Tasks by Project\n4) Show tasks not done\n5) CANCEL");
            String selected = read.nextLine().trim();
            validOpt = true;
            switch (selected){
                case "1":
                    taskInter.showTaskDB();
                    break;
                case "2":
                    taskInter.showTaskByDueDate();
                    break;
                case "3":
                    taskInter.showTaskDBByProject();
                    break;
                case "4":
                    taskInter.showTaskNotDone();
                    break;
                case "5":
                    break;
                default:
                    System.out.println(ANSI_RED + "No such option, try again!" + ANSI_RESET);
                    validOpt = false;
                    break;
            }
        }

    }

    private void updateTask() {
        String title;
        boolean doneEdit = false;
        while(!doneEdit){
            System.out.println("The task to be edited is or CANCEL to exit:");
            title = read.nextLine().trim();
            if(title.trim().equals("CANCEL"))
                break;
            Task t = taskInter.getTask(title);
            if(t != null){
                taskInter.showTask(t);
                Date dueDate = inputDueDate();
                Status status = inputStatus();
                String project = inputProject();
                String description = inputDescription();
                t.setStatus(status);
                t.setDueDate(dueDate);
                t.setProject(project);
                t.setDescription(description);
                doneEdit = true;
            }else {
                System.out.println(ANSI_RED + "The Task doesn't exit in the database, try again!" + ANSI_RESET);
            }
        }
    }

    public String inputTitle(){
        System.out.println("Title or CANCEL to exit");
        return read.nextLine().trim();
    }

    public Date inputDueDate(){
        boolean validDate = false;
        String strDate;
        Date dueDate = null;
        Date currentDate = new Date();
        while (!validDate){
            try{
                System.out.println("Due Date(" + dateFormat + ") or CANCEL to exit");
                strDate = read.nextLine().trim();
                if(strDate.equals("CANCEL"))
                    return null;
                dueDate = sdf.parse(strDate);
                if(dueDate.before(currentDate)){
                    System.out.println(ANSI_RED + "The due date has passed!" + ANSI_RESET);
                }else
                {
                    validDate = true;
                }
            }catch (ParseException e){
                System.out.println(ANSI_RED + "The Data format is not correct, please try again!" + ANSI_RESET);
            }
        }
        return dueDate;
    }

    public String inputDescription(){
        System.out.println("Description or CANCEL to exit:");
        return read.nextLine().trim();
    }

    public Status inputStatus(){
        boolean validStatus = false;
        String strStatus;
        Status status = null;
        while (!validStatus){
            System.out.println("Status: " + this.allStatus + " or CANCEL to exit");
            strStatus = read.nextLine().trim();
            validStatus = true;
            switch (strStatus){
                case "1":
                    status = Status.DOING; break;
                case "2":
                    status = Status.PENDING; break;
                case "3":
                    status = Status.ASAP; break;
                case "4":
                    status = Status.DONE; break;
                case "CANCEL":
                    break;
                default:
                    System.out.println(ANSI_RED + "No such option, try again!" + ANSI_RESET);
                    validStatus = false;
            }
        }
        return status;
    }

    public String inputProject(){
        System.out.println("Project or CANCEL to exit:");
        return read.nextLine().trim();
    }

    public void addTask(){
        String title = inputTitle();
        if(title.equals("CANCEL"))
            return;
        Date dueDate = inputDueDate();
        if(dueDate == null)
            return;
        Status status = inputStatus();
        if(status == null)
            return;
        String project = inputProject();
        if(project.equals("CANCEL"))
            return;
        String description = inputDescription();
        if(description.equals("CANCEL"))
            return;

        taskInter.addTask(new Task(title, dueDate, status, project, description));
    }
}
