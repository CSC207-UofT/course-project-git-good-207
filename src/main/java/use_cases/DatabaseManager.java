package use_cases;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
}
