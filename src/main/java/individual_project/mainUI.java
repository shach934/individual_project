package individual_project;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class mainUI {
    ArrayList<String> options;
    Map<String, Runnable> commands;
    Scanner read = new Scanner(System.in);
    DBInterface taskInter = new DBInterface();

    mainUI(){
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
        System.out.println(optSB);
        commands.put("1", this::showTasks);
        commands.put("2", this::addTask);
        commands.put("3", this::editTask);
        commands.put("4", this::saveExit);
    }

    private void saveExit() {
    }

    private void showTasks() {

    }

    private void editTask() {
        System.out.println("Select the Task you would like to edit:");
        String title;
        boolean doneEdit = false;
        while(!doneEdit){
            System.out.println("The task to be edited is:");
            title = read.nextLine();
            Task t = taskInter.getTask(title);
            if(t != null){
                taskInter.showTask(t);

                title = t.getTitle();
                Date dueDate = inputDueDate();
                Status status = inputStatus();
                String project = inputProject();
                String description = inputDescription();
                taskInter.
                Task t = new Task();


            }else {
                System.out.println("The Task doesn't exit in the database, try again!");
            }
        }
    }

    public String inputTitle(){
        System.out.println("Task Title:");
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
                System.out.println("Task Due Date:");
                strDate = read.nextLine();
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
            System.out.println("Status:");
            strStatus = read.nextLine();
            status = Status.fromString(strStatus);
            if(status != null){
                validStatus = true;
            }
        }
        return status;
    }

    public String inputProject(){
        System.out.println("Description:");
        return read.nextLine();
    }

    public void addTask(){
        String title = inputTitle();
        Date dueDate = inputDueDate();
        Status status = inputStatus();
        String project = inputProject();
        String description = inputDescription();
        taskInter.addTask(new Task(title, dueDate, status, project, description));
    }

}
