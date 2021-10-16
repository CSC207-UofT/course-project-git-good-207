package use_cases;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import entities.User;

public class DatabaseManager {
    // Database for the recipe posts and their comments
    private String recipefileName;
    // Database for the usernames and passwords (and maybe their unique ids if they change usernames)
    private String userFileName;
    private UserManager usermanager;

    //TODO: Get a User object to check its username and password to help verify the login.
    public entities.User getUser() {

    }

    //TODO: Add a new User into the database with its username and password
    public boolean addUser() {

    }

    public List<String> readFile(String fileName) {
        List<String> list = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            list = stream
                    .filter()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("An error occurred while reading " + fileName);
            e.printStackTrace();
        }

        return list;
    }

    public boolean writeToFile(String fileName) {
        try {
            FileWriter writer = new FileWriter("");
            writer.write("");
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while writing to " + fileName);
            e.printStackTrace();
        }
        return false;
    }

    // Function to return dummy list of users, will return list of users from the database once db is completed
    public ArrayList<User> getUsers() {
        User user_shawn = this.usermanager.createUser("shawn_number1", "letmein");
        User user_justin = this.usermanager.createUser("math_god", "mandelbrot_set");
        User user_yolanda = this.usermanager.createUser("yooooolanda", "trainplane");
        User user_eric = this.usermanager.createUser("slayer334", "slayerone");
        User user_sebastian = this.usermanager.createUser("sebstiannn", "sebastian#1");
        User user_glen = this.usermanager.createUser("eren_yeager", "naughtylilfella");
        ArrayList<User> users = new ArrayList<>();
        users.add(user_shawn);
        users.add(user_justin);
        users.add(user_yolanda);
        users.add(user_eric);
        users.add(user_sebastian);
        users.add(user_glen);
        return users;
    }
}
