package use_cases;

import entities.*;

import java.sql.*;

public abstract class DatabaseManager {
    protected Connection connection;

    /**
     * Save a new post to the database.
     * @param newPost The Post to save to the database.
     */
    public abstract void addNewPost(Post newPost);

    /**
     * Edit the Post saved in the Database
     * @param newPost The Post to save to the database.
     */
    public abstract void editPost(Post newPost);


    /**
     * Delete a Post in the database.
     * @param postId The ID of the Post to delete.
     */
    public abstract void deletePost(String postId);


    /**
     * Save a new user to the database.
     * @param newUser The new User to save to the database.
     */
    public abstract void addNewUser(User newUser);

    /**
     * Gets all the posts stored in the database.
     * @return an Array of all the posts stored in the database.
     */
    public abstract Post[] getAllPosts();

    public abstract void updateUser(User updatedUser);

    /**
     * Gets an array of all the Users saved in the database.
     * @return an Array of all the Users registered in the app.
     */
    public abstract User[] getAllUsers();
}