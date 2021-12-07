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
     * Saves the like in the database on a given post
     * @param post post object which is being given a like
     * @param user user object that is giving the like
     */
    public abstract void likePost(Post post, User user);

    /**
     * Saves the comment in the database of a given post
     * @param post post object which is being commented on
     * @param comment comment that stores the information about it
     */
    public abstract void commentPost(Post post, Comment comment);

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

    /**
     * Updates the attributes of the user to the new attributes
     * given the parameter
     * @param updatedUser user object with the updated values
     * @return true if the attributes were changed
     */
    public abstract boolean updateUser(User updatedUser);

    /**
     * deletes all the information except for recipes related
     * to the given user in the database
     * @param user user object that contains information about
     *             the user
     * @return true if it could delete all the information related
     * to the user
     */
    public abstract boolean deleteUser(User user);


    /**
     * Gets an array of all the Users saved in the database.
     * @return an Array of all the Users registered in the app.
     */
    public abstract User[] getAllUsers();
}