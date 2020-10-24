package individual_project;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ToDoLyTest {

    @Test
    void creatDB(){
        /**
         * Check if there is ToDoLy.SDA file exist, if yes, remove it and then try to initiate application
         * to test if application would create a new data file.
         */
        // backup of the std in/out stream.
        final InputStream oldIn = System.in;
        final PrintStream oldOut = System.out;

        try{
            File file = new File("./ToDoLy.SDA");
            boolean result = Files.deleteIfExists(file.toPath());
        }catch (IOException e){
            System.out.println("There is problem to remove the previous data file!");
        }

        // redirect the std input to file.
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File("./test_files/createDB_in.txt"));
            System.setIn(in);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // redirect the std output to file.
        try{
            FileOutputStream f = new FileOutputStream("./test_files/createDB_out.txt");
            System.setOut(new PrintStream(f));
        }catch (FileNotFoundException e){
            System.out.println("There is no such file exists, check it!");
            System.out.println("The output Stream is not redirected. Watch out.");
        }

        // The ToDoLy application here.
        ToDoLy t = new ToDoLy();
        String[] args = {};
        t.main(args);

        // reset the std in and out stream to default.
        System.setIn(oldIn);
        System.setOut(oldOut);
    }

    @Test
    void addViewTasks() {
        /**
         * Add tasks to a database.
         * save and exist the application.
         */
        // backup of the std in/out stream.
        final InputStream oldIn = System.in;
        final PrintStream oldOut = System.out;

        // redirect the std input to file.
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File("./test_files/addviewtask_in.txt"));
            System.setIn(in);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // redirect the std output to file.
        try{
            FileOutputStream f = new FileOutputStream("./test_files/addviewtask_out.txt");
            System.setOut(new PrintStream(f));
        }catch (FileNotFoundException e){
            System.out.println("There is no such file exists, check it!");
            System.out.println("The output Stream is not redirected. Watch out.");
        }

        // The ToDoLy application here.
        ToDoLy t = new ToDoLy();
        String[] args = {};
        t.main(args);

        // reset the std in and out stream to default.
        System.setIn(oldIn);
        System.setOut(oldOut);
    }

    @Test
    void removeTaskTest() {
        /**
         * Add tasks to a database.
         * save and exist the application.
         */
        // backup of the std in/out stream.
        final InputStream oldIn = System.in;
        final PrintStream oldOut = System.out;

        // redirect the std input to file.
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File("./test_files/removeTask_in.txt"));
            System.setIn(in);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // redirect the std output to file.
        try{
            FileOutputStream f = new FileOutputStream("./test_files/removeTask_out.txt");
            System.setOut(new PrintStream(f));
        }catch (FileNotFoundException e){
            System.out.println("There is no such file exists, check it!");
            System.out.println("The output Stream is not redirected. Watch out.");
        }

        // The ToDoLy application here.
        ToDoLy t = new ToDoLy();
        String[] args = {};
        t.main(args);

        // reset the std in and out stream to default.
        System.setIn(oldIn);
        System.setOut(oldOut);
    }
}