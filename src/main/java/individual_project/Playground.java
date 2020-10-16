package individual_project;

import org.checkerframework.checker.units.qual.A;

import java.util.*;

class User{
    public String lastName;
    public String name;
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
        return name + " " + lastName + " " + age;
    }

    User(User u){
        this.lastName = u.lastName;
        this.name = u.name;
        this.age = u.age;
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

        ArrayList<User> userByName = new ArrayList<>();
        ArrayList<User> userByLName = new ArrayList<>();

        for(int i = 0; i < users.size(); i ++)
        {
            User u = new User(users.get(i));
            userByName.add(u);
            userByLName.add(u);
        }

        Queue<Integer> queue = new LinkedList<>();
        Collections.sort(userByName, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getName().compareTo(u2.getName());
            }
        });

        Collections.sort(userByLName, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getLastName().compareTo(u2.getLastName());
            }
        });
        User a = users.get(1);
        a.name = "Special";
        System.out.println(users);
        System.out.println(userByName);
        System.out.println(userByLName);
    }

}
