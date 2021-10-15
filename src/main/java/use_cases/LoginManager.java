package use_cases;

import entities.User;

/*
Manages user login and logout
 */
public class LoginManager {
    private User currUser;
    private UserManager userManager;

    // TODO add DatabaseManager parameter
    public LoginManager (UserManager userManager){
        this.currUser = null;
        this.userManager = userManager;
    }

    /**
     * Verifies user credentials and updates current user accordingly, logging them in.
     *
     * @param username The entered username
     * @param password The entered password
     * @return Return true if login was successful. Return false otherwise.
     */
    public boolean login(String username, String password){
        boolean isValidLogin = verifyUser(username, password);
//        if (isValidLogin){
            // TODO use UserManager method to create user with all needed data
            //currUser = [insert UserManager method] ;
//        }
          this.currUser = new User(username, password);
          return true; // dummy value, remove when we implement this method
//        return isValidLogin;
    }

    /**
     * Logout current user by resetting currUser.
     */
    public void logout(){
        currUser = null;
    }

    public boolean signUp (String username, String password){
        //TODO replace true isValidUsername value with databaseManager method

        boolean isValidUsername = true;
        //boolean isValidUsername = !(username in databaseManager.getUsers());

        //TODO can add future restrictions on what is a valid password
        //for now all passwords are valid
        boolean isValidPassword = true;

        if(isValidUsername && isValidPassword){
            //TODO use databaseManager to add new user to the database

            //databaseManager.addUser(username, password);

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
    private boolean verifyUser(String username, String password){
        //TODO use DatabaseManager to check if the given username and password
        //match a username and password pair in the Database

        /**
         if (username in databaseManager.getUsers()){
            //username is in database, so check if the password matches
            return password == databaseManager[user];
         }else{
            //username not in database
            return false;
         }
         */
        return true;
    }

    public User getCurrUser() {
        return currUser;
    }
}
