package individual_project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ToDoLyUI {
    /**
     *
     * Methods:
     *   ToDoLyUI(), initialize the main menu, commands, and prompt user the menu.
     *   verifyInput(String, Set<String>):String, using set to get the valid input from user.
     *   editTask():void,
     *   removeTask():void,
     *   markDone():void,
     *   printWelcome():void,
     *   saveExit():void,
     *   getAllStatus():void,
     *   showTasks():void,
     *   updateTask():void,
     *   inputTitle():String,
     *   inputDueDate():Date,
     *   inputDescription():String,
     *   inputStatus():Status,
     *   inputProject():String
     *   addTask():void,
     */

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_YELLOW = "\u001b[33m";

    String mainMenu;
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
        mainMenu = "1) View Task\n2) Add Task\n3) Edit Task\n4) Save and Quit";

        commands = new HashMap<>();
        commands.put("1", this::showTasks);
        commands.put("2", this::addTask);
        commands.put("3", this::editTask);
        commands.put("4", this::saveExit);
        boolean exit = false;
        String selected;
        while (!exit){
            System.out.println(mainMenu);
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

    private String verifyInput(String prompt, Set<String> validOption){
        boolean validSelection = false;
        while (!validSelection){
            System.out.println(prompt);
            String selected = read.nextLine().trim();
            if(validOption.contains(selected)){
                return selected;
            }else {
                System.out.println(ANSI_RED + "Not a valid selection! Try again!" + ANSI_RESET);
            }
        }
        return "";
    }

    private void editTask() {
        String prompts = "1) Update a Task\n2) Mark a task as Done\n3) Remove a Task\n4) CANCEL";
        Set<String> validOpt = new HashSet<>(Arrays.asList("1", "2", "3", "4"));
        String selected = verifyInput(prompts, validOpt);
        if(selected.equals("4"))
            return;
        Map<String, Runnable> editCommands = new HashMap<>();
        editCommands.put("1", this::updateTask);
        editCommands.put("2", this::markDone);
        editCommands.put("3", this::removeTask);
        editCommands.get(selected).run();
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
        Map<String, String> showTaskCommad = new HashMap<>();
        showTaskCommad.put("1", "default");
        showTaskCommad.put("2", "DueDate");
        showTaskCommad.put("3", "Project");
        showTaskCommad.put("4", "unDone");
        showTaskCommad.put("5", "Cancel");

        String prompt = "1) Show Tasks by add time\n2) Show Tasks by Due date\n3) Show Tasks by Project\n4) Show tasks not done\n5) CANCEL";
        Set<String> validOpt = new HashSet<>(Arrays.asList("1", "2", "3", "4", "5"));
        String option = verifyInput(prompt, validOpt);
        String order = showTaskCommad.get(option);
        if(option != "5")
            taskInter.showTaskDB(order);
        else
            return;
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
        boolean validTitle = false;
        String title = "";
        while (!validTitle){
            System.out.println("Title or CANCEL to exit");
            title = read.nextLine().trim();
            if(taskInter.hasTask(title)){
                System.out.println("There is already a task with this title exists.");
            }else if(title.length() == 0) {
                System.out.println("Empty is not a valid title, try again.");
            }else {
                validTitle = true;
            }
        }
        return title;
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
        boolean validProject = false;
        String project = "";
        while (!validProject){
            System.out.println("Project or CANCEL to exit:");
            project = read.nextLine().trim();
            if(project.length() == 0) {
                System.out.println("Project cannot be empty, try again.");
            }else {
                validProject = true;
            }
        }
        return project;
    }

    public void addTask(){
        String title = inputTitle();
        if(title.equals("CANCEL")){
            return;
        }
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
        Task t = new Task(title, dueDate, status, project, description);
        System.out.println("The following task is added to the Todo list");
        taskInter.showTask(t);
        taskInter.addTask(t);
    }
}
