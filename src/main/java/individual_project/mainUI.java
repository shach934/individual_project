package individual_project;

import com.google.common.graph.AbstractNetwork;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class mainUI {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    ArrayList<String> options;
    Map<String, Runnable> commands;
    Scanner read = new Scanner(System.in);
    DBInterface taskInter = new DBInterface();
    StringBuilder allStatus = new StringBuilder("");

    mainUI(){
        getAllStatus();
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

    private void saveExit() { taskInter.saveDB(); }

    private void getAllStatus(){
        for(Status s:Status.values()){
            allStatus.append(s.getText()).append("  ");
        }
    }

    private void showTasks() {
        boolean validOpt = false;
        while (!validOpt){
            System.out.println("1) Show Tasks by add time\n2) Show Tasks by Due date\n3) Show Tasks by Project\n4) CANCEL");
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
                case "4" :
                case "CANCEL":
                    break;
                default:
                    System.out.println(ANSI_RED + "No such option, try again!" + ANSI_RESET);
                    validOpt = false;
                    break;
            }
        }

    }

    private void editTask() {
        String title;
        boolean doneEdit = false;
        while(!doneEdit){
            System.out.println("The task to be edited is or CANCEL to exit:");
            title = read.nextLine();
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
            }else {
                System.out.println(ANSI_RED + "The Task doesn't exit in the database, try again!" + ANSI_RESET);
            }
        }
    }

    public String inputTitle(){
        System.out.println("Title or CANCEL to exit");
        return read.nextLine();
    }

    public Date inputDueDate(){
        boolean validDate = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        String strDate;
        Date dueDate = null;
        Date currentDate = new Date();
        while (!validDate){
            try{
                System.out.println("Due Date(yyyy-mm-dd, HH:MM:SS) or CANCEL to exit");
                strDate = read.nextLine();
                if(strDate.equals("CANCEL"))
                    return null;
                dueDate = sdf.parse(strDate);
                if(dueDate.before(currentDate)){
                    System.out.println("The due date has passed!");
                }else
                {
                    validDate = true;
                }
            }catch (ParseException e){
                System.out.println("The Data format is not correct, please try again!");
            }
        }
        return dueDate;
    }

    public String inputDescription(){
        System.out.println("Project:");
        return read.nextLine();
    }

    public Status inputStatus(){
        boolean validStatus = false;
        String strStatus;
        Status status = null;
        while (!validStatus){
            System.out.println("Status(" + this.allStatus + ") or CANCEL to exit");
            strStatus = read.nextLine();
            status = Status.fromString(strStatus);
            if(status != null){
                validStatus = true;
            }
        }
        return status;
    }

    public String inputProject(){
        System.out.println("Description or CANCEL to exit:");
        return read.nextLine();
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
