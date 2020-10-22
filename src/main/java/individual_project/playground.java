package individual_project;

public class playground {
    public static void main(String[] args) {
        String str = "geekss§@§for§@§geekss";
        String[] arrOfStr = str.split("§@§", 3);

        for (String a : arrOfStr)
            System.out.println(a);
    }
}
