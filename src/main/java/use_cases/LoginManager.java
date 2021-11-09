package use_cases;

import entities.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Manages user login and logout
 */
public class LoginManager {
    private User currUser;
    private DatabaseManager databaseManager;


    public LoginManager(DatabaseManager dbManager) {
        this.currUser = null;
        this.databaseManager = dbManager;
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
            //get User from the Database
            // TODO: replace this with better code to guarantee we get a User (and not null)
            User[] allUsers = this.databaseManager.getAllUsers();

            this.currUser = getExistingUser(username, allUsers);
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

        boolean isValidUsername = this.isValidUsername(username);

        boolean isValidPassword = this.isValidPassword(password);

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
     * Get the user from an array of users that has the given username
     *
     * @param username a given username
     * @param allUsers array of all the existing users
     * @return User with the given username if it exists, null otherwise
     */
    private User getExistingUser(String username, User[] allUsers) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private boolean isValidPassword(String password){
        String developerPass = "1234";
        String oneUpper = "(?=.*[A-Z])";
        String oneLower = "(?=.*[a-z])";
        String oneNum = "(?=.*\\d)";
        String sixPlusChar = ".{6,}";

        Pattern passReq = Pattern.compile("^"+ oneUpper + oneLower + oneNum + sixPlusChar + "$");
        Matcher matcher = passReq.matcher(password);

        boolean isValidPass = matcher.matches();
        boolean isDevPass = password.equals(developerPass);

        return isValidPass || isDevPass;
    }

    private boolean isValidUsername(String username){
        String onePlusChar = ".+";
        String oneLetter = "(?=.*[a-zA-Z])";

        Pattern userReq = Pattern.compile("^" + oneLetter + onePlusChar + "$");
        Matcher matcher = userReq.matcher(username);

       boolean isUniqueUser = !(databaseManager.getLoginInfo().containsKey(username));
       boolean isValidUser = matcher.matches();

       return isValidUser && isUniqueUser;

    }

    public User getCurrUser() {
        return this.currUser;
    }
}
