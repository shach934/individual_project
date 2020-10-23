package individual_project;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ToDoLyTest {

    @Test
    void runTest() {
        // backup of the std in/out stream.
        final InputStream oldIn = System.in;
        final PrintStream oldOut = System.out;

        // redirect the std input to file.
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File("./testInput.txt"));
            System.setIn(in);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // redirect the std output to file.
        try{
            FileOutputStream f = new FileOutputStream("./output.txt");
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