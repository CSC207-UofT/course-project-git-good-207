package use_cases;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.time.LocalDateTime;

import entities.User;
import entities.Post;
import entities.Recipe;

public class DatabaseManager {
    // Database for the recipe posts and their comments
    private String recipefileName;
    // Database for the usernames and passwords (and maybe their unique ids if they change usernames)
    private String userFileName;

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
        UserManager um = new UserManager();
        User user_shawn = um.createUser("shawn_number1", "letmein");
        User user_justin = um.createUser("math_god", "mandelbrot_set");
        User user_yolanda = um.createUser("yooooolanda", "trainplane");
        User user_eric = um.createUser("slayer334", "slayerone");
        User user_sebastian = um.createUser("sebstiannn", "sebastian#1");
        User user_glen = um.createUser("eren_yeager", "naughtylilfella");
        ArrayList<User> users = new ArrayList<>();
        users.add(user_shawn);
        users.add(user_justin);
        users.add(user_yolanda);
        users.add(user_eric);
        users.add(user_sebastian);
        users.add(user_glen);
        // Generate posts:
        LocalDateTime right_now = LocalDateTime.now();
        Recipe recipe_1 = new Recipe("Russian Meatballs");
        Recipe recipe_2 = new Recipe("Canadian Meatballs");
        Recipe recipe_3 = new Recipe("Chinese Meatballs");
        Recipe recipe_4 = new Recipe("Russian Salmon");
        Recipe recipe_5 = new Recipe("Russian Chicken");

        Post post_1 = new Post(user_shawn, right_now, recipe_1, "Russian");
        Post post_2 = new Post(user_eric, right_now, recipe_1, "Canadian");
        Post post_3 = new Post(user_glen, right_now, recipe_1, "Chinese");
        Post post_4 = new Post(user_sebastian, right_now, recipe_1, "Russian");
        Post post_5 = new Post(user_yolanda, right_now, recipe_1, "Russian");
        return users;
    }

}