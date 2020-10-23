package individual_project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class playground {
    public static void main(String[] args) throws ParseException {
        Task t = new Task();
        t.setTitle("test");
        Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd").parse("2020-12-12 12:00:00");
        t.setDueDate(date);
        Status status = Status.DONE;
        t.setStatus(status);
        t.setProject("SDA");
        t.setDescription("test task");
        System.out.println(t.getStatus().equals("Done"));
    }
}
