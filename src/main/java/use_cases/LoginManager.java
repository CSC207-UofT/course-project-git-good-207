package use_cases;

import entities.User;

import java.util.HashMap;

/*
Manages user login and logout
 */
public class LoginManager {
    private User currUser;
    private UserManager userManager;
    private DatabaseManager databaseManager;


    public LoginManager(UserManager userManager) {
        this.currUser = null;
        this.userManager = userManager;
        this.databaseManager = new DatabaseManager();
    }

    /**
     * Verifies user credentials and updates current user accordingly, logging them in.
     *
     * @param username The entered username
     * @param password The entered password
     * @return Return true if login was successful. Return false otherwise.
     */
    public boolean login(String username, String password) {
        boolean isValidLogin = verifyUser(username, password);
        if (isValidLogin) {
            //use UserManager method to create user with all needed data
            // TODO: replace this with better code to guarantee we get a User (and not null)
            User[] allUsers = this.databaseManager.getAllUsers();
            for (User user: allUsers) {
                if (user.getUsername().equals(username)) {
                    this.currUser = user;
                }
            }
        }
        return isValidLogin;
    }

    /**
     * Logout current user by resetting currUser.
     */
    public void logout() {
        this.currUser = null;
    }

    public boolean signUp(String username, String password) {

        boolean isValidUsername = !(databaseManager.getLoginInfo().containsKey(username));

        //TODO can add future restrictions on what is a valid password
        //for now all passwords are valid
        boolean isValidPassword = true;

        if (isValidUsername && isValidPassword) {
            //use databaseManager to add new user to the database
            this.databaseManager.addNewUser(new User(username, password));

        }
        return isValidUsername && isValidPassword;
    }

    /**
     * Verifies if the given username and password correlate to an existing user
     * in the database and returns the corresponding boolean.
     *
     * @param username The entered username
     * @param password The entered password
     * @return Return true if both username and password match a username and
     * password pair in the Database. Return false otherwise.
     */
    private boolean verifyUser(String username, String password) {

        HashMap<String, String> loginInfo = this.databaseManager.getLoginInfo();
        //use DatabaseManager to check if the given username and password
        //match a username and password pair in the Database
        if (this.databaseManager.getLoginInfo().containsKey(username)) {
            //username is in database, so check if the password matches
            return password.equals(loginInfo.get(username));
        } else {
            //username not in database
            return false;
        }
    }

    public User getCurrUser() {
        return this.currUser;
    }
}
