package individual_project;

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
        commands.put("1", ()->taskInter.showTaskDB());
        commands.put("2", ()->addTask());
        commands.put("3", ()->editTask());
    }

    private void editTask() {
        System.out.println("Select the Task you would like to edit:");
        
    }

    public void addTask(){
        System.out.println("Task Title:");
        String title = read.nextLine();
        System.out.println("Task Due Date:");
        boolean validDate = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        String strDate;
        Date dueDate = null;
        Date currentDate = new Date();
        while (!validDate){
            try{
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
        System.out.println("Project:");
        String project = read.nextLine();
        System.out.println("Status:");
        boolean validStatus = false;
        String strStatus;
        Status status = null;
        while (!validStatus){
            strStatus = read.nextLine();
            status = Status.fromString(strStatus);
            if(status != null){
                validStatus = true;
            }
        }
        System.out.println("Description:");
        String description = read.nextLine();
        Task t = new Task();
        t.setTitle(title);
        t.setStatus(status);
        t.setDueDate(dueDate);
        t.setProject(project);
        t.setDescription(description);
        taskInter.addTask(t);
    }

}
