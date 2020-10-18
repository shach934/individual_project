package individual_project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class myUI {
    ArrayList<String> options;
    Map<String, Runnable> commands;
    Scanner read = new Scanner(System.in);

    myUI(){
        DBInterface taskInter = new DBInterface();
        taskDB taskDB = new taskDB();
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
        commands.put("1", ()->taskInter.showTaskDB(taskDB));

    }

}
