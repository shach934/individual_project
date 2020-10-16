package individual_project;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class User{
    String lastName;
    String name;
    int age;
    User(String lastName, String name, int age){
        this.lastName = lastName;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getLastName(){
        return lastName;
    }

    @Override
    public String toString(){
        return this.lastName + " " + this.name + " " + age;
    }
}


public class Playground {

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User("a1", "b3", 10);
        User u2 = new User("a2", "b2", 20);
        User u3 = new User("a3", "b1", 39);

        users.add(u3);
        users.add(u1);
        users.add(u2);

        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getName().compareTo(u2.getName());
            }
        });

        System.out.println(users);

        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getLastName().compareTo(u2.getLastName());
            }
        });

        System.out.println(users);

    }





}
