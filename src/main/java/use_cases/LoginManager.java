package use_cases;

import entities.User;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Manages user login and logout
 */
public class LoginManager {
    private User currUser;
    private final DatabaseManager databaseManager;

    /**
     * Create a LoginManager with a null currUser and a DatabaseManager
     *
     * @param dbManager DatabaseManager that is used by the LoginManager
     */
    public LoginManager(DatabaseManager dbManager) {
        this.currUser = null;
        this.databaseManager = dbManager;
    }

    /**
     * Verifies user credentials and updates current user accordingly, logging them in.
     *
     * @param username The entered username
     * @param password The entered password
     * @return boolean true if login was successful, false otherwise.
     */
    public boolean login(String username, String password) {
        boolean isValidLogin = verifyUser(username, password);
        if (isValidLogin) {
            //get User from the Database
            User[] allUsers = this.databaseManager.getAllUsers();

            this.currUser = getExistingUser(username, allUsers);
        }
        return isValidLogin;
    }

    /**
     * Logout current user by setting currUser to null.
     */
    public void logout() {
        this.currUser = null;
    }

    /**
     * Signs up a user with the given username and password and adds new user to the database
     * if username and password are valid
     *
     * @param username username of the user being signed up
     * @param password password of the user being signed up
     * @return boolean true if signup is successful, false otherwise
     */
    public boolean signUp(String username, String password) {

        //check for valid username
        boolean isValidUsername = this.isValidUsername(username);

        //check for valid password
        boolean isValidPassword = this.isValidPassword(password);

        if (isValidUsername && isValidPassword) {
            //use databaseManager to add new user to the database
            this.databaseManager.addNewUser(new User(username, password, "", UUID.randomUUID().toString()));

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

        User[] allUsers = this.databaseManager.getAllUsers();
        //use DatabaseManager to check if the given username and password
        //match a username and password pair in the Database
        User user = getExistingUser(username, allUsers);
        if (user != null) {
            //username is in database, so check if the password matches
            return password.equals(user.getPassword());
        } else {
            //username not in database
            return false;
        }
    }

    /**
     * Get the user that has the given username from an array of users
     *
     * @param username a given username
     * @param allUsers array of all the existing users
     * @return User with the given username if it exists, null otherwise
     */
    private User getExistingUser(String username, User[] allUsers) {
        for (User user: allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        //user does not exist
        return null;
    }

    /**
     * Check if the given password is valid
     *
     * @param password a given password
     * @return true if password is valid, false otherwise
     */
    private boolean isValidPassword(String password) {
        //valid, easier to enter password for testing purposes
        String developerPass = "1234";

        //regular expressions denoting different password restrictions
        String oneUpper = "(?=.*[A-Z])";
        String oneLower = "(?=.*[a-z])";
        String oneNum = "(?=.*\\d)";
        String sixPlusChar = ".{6,}";

        //compile and match regex
        Pattern passReq = Pattern.compile("^" + oneUpper + oneLower + oneNum + sixPlusChar + "$");
        Matcher matcher = passReq.matcher(password);

        boolean isValidPass = matcher.matches();
        boolean isDevPass = password.equals(developerPass);

        return isValidPass || isDevPass;
    }

    /**
     * Check if the given username is valid
     *
     * @param username a given username
     * @return true if username is valid, false otherwise
     */
    private boolean isValidUsername(String username) {

        //regular expressions denoting different password restrictions
        String onePlusChar = ".+";
        String oneLetter = "(?=.*[a-zA-Z])";

        //compile and match regex
        Pattern userReq = Pattern.compile("^" + oneLetter + onePlusChar + "$");
        Matcher matcher = userReq.matcher(username);

        //make sure username isn't take already
        boolean isUniqueUser = !(databaseManager.getLoginInfo().containsKey(username));
        boolean isValidUser = matcher.matches();

        return isValidUser && isUniqueUser;

    }

    /**
     * Return the currently logged-in user
     *
     * @return the currently logged-in user
     */
    public User getCurrUser() {
        return this.currUser;
    }
}
