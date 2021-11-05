package use_cases;

import entities.*;

import java.sql.*;
import java.util.HashMap;

public abstract class DatabaseManager {
    //Temp user storage for loginManager testing
    protected HashMap<String, String> loginInfo = new HashMap<>();
    protected Connection connection;

    public abstract HashMap<String, String> getLoginInfo();


    /**
     * Save a new post to the database.
     * @param newPost The Post to save to the database.
     */
    public abstract void addNewPost(Post newPost);

    /**
     * Save a new user to the database.
     * @param newUser The new User to save to the database.
     * @return A boolean which is true if the user was successfully
     * added (there was no user with the same username). False if
     * unsuccessful.
     */
    public abstract boolean addNewUser(User newUser);

    /**
     * Gets all the posts stored in the database.
     * @return an Array of all the posts stored in the database.
     */
    public abstract Post[] getAllPosts();

    public abstract boolean updatePost(Post updatedPost);

    public abstract boolean updateUser(User updatedUser);

    /**
     * Gets an array of all the Users saved in the database.
     * @return an Array of all the Users registered in the app.
     */
    public abstract User[] getAllUsers();
}
