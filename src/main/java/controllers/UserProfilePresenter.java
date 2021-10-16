package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

import entities.InOut;
import entities.ShellAction;
import entities.User;
import entities.Post;
import use_cases.DatabaseManager;
import use_cases.LoginManager;
import use_cases.UserManager;

public class UserProfilePresenter {
    private InOut inOut;
    private String CustomizeProfileScreen = "Select which option you'd like to customize:" +
            "\n0 Username \n1 Password \n2 Bio";
    private String UserToBrowsePrompt = "Enter the username of the person you'd like to view: ";
    private LoginManager loginmanager;
    //Added this attribute:
    private DatabaseManager databasemanager;


    // Constructor
    public UserProfilePresenter(InOut inOut,
                                LoginManager loginmanager, DatabaseManager databasemanager) {
        this.inOut = inOut;
        this.loginmanager = loginmanager;
        //Added this attribute:
        this.databasemanager = databasemanager;
    }


    public void run(ShellAction action) {
        if (action == ShellAction.BROWSEPROFILE) {
            // Browse user's profile, given String username:
            // run a helper method (checkUserToBrowse) to call Database manager to check whether the username belongs
            // to a user in the database
            // run getUser to get the user that owns the username from the database
            // run a helper method called runBrowseProfile given User object to browse the user's profile
            try {
                String user_to_browse = this.inOut.getInput("Enter the username of the person you'd like to view: ");
                if (this.checkUserToBrowse(user_to_browse)) {
                    this.inOut.setOutput(this.runBrowseOtherProfile(this.getUser(user_to_browse)));
                }
                else {
                    this.inOut.setOutput("User not found in database. Returning to main page");
                }
            } catch (IOException e) {
                inOut.setOutput("There was an error: " + e);
            }
        }

        else if (action == ShellAction.CUSTOMIZEPROFILE) {
            // Customize user's profile:
            // 1. Display current user's profile w/ runBrowseProfile
            // 2. Ask current user on what they want to customize: username, bio, password w/ runCustomizeProfile
            // 3. Customize one of the three: username, bio, or password
            this.inOut.setOutput(this.runBrowseProfile(this.loginmanager.getCurrUser()));
//            A Non-static method cannot be referenced from a static context error
            this.inOut.setOutput(this.getCustomizeProfileScreen());
            try {
                int choice = Integer.parseInt(this.inOut.getInput("Select an option: "));
                if (choice == 0) {
                    String new_username = this.inOut.getInput("Enter what you'd like your new username to be: ");
                    if (this.changeUsername(this.loginmanager.getCurrUser(), new_username)) {
                        this.inOut.setOutput("Successfully changed username to: " + new_username + "\n");
                        this.inOut.setOutput("Returning to main page.");
                    }
                    else {
                        this.inOut.setOutput("Username entered is the same as the old one. Returning to main page.");
                    }

                }
                else if (choice == 1) {
                    String new_password = this.inOut.getInput("Enter what you'd like your new password to be: ");
                    if (this.changePassword(this.loginmanager.getCurrUser(), new_password)) {
                        this.inOut.setOutput("Successfully changed password to: " + new_password + "\n");
                        this.inOut.setOutput("Returning to main page.");
                    }
                    else {
                        this.inOut.setOutput("Password entered is the same as the old one. Returning to main page");
                    }
                }
                else if (choice == 2) {
                    String new_bio = this.inOut.getInput("Enter what you'd like your new bio to be: ");
                    if (this.changeBio(this.loginmanager.getCurrUser(), new_bio)) {
                        this.inOut.setOutput("Successfully changed bio to : " + new_bio + "\n");
                        this.inOut.setOutput("Returning to main page.");
                    }
                    else {
                        this.inOut.setOutput("Bio given is the same as the old one. Returning to main page");
                    }
                }
            } catch(IOException e) {
                inOut.setOutput("There was an error: " + e);
            }
        }
    }

    /**
     * Return true if username given belongs to a user inside the database, false otherwise
     */
    public boolean checkUserToBrowse(String user_to_browse) {
        ArrayList<User> user_database = this.databasemanager.getUsers();
        for(User user: user_database) {
            if (user.getUsername().equals(user_to_browse)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return the User object that is affiliated to the given username
     */
    public User getUser(String target_user) {
        ArrayList<User> user_database = this.databasemanager.getUsers();
        for(User user: user_database) {
            if (user.getUsername().equals(target_user)) {
                return user;
            }
        }
        return null;
    }

    /**
     * views the information(attributes) of user
     */
    public String runBrowseProfile(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String bio = user.getBio();
        return ("Username: " + username + "\nPassword: " + password + "\nBio: " + bio);
    }

    /**
     * views the information(attributes) of another user, therefore password is not given
     */
    public String runBrowseOtherProfile(User user) {
        String username = user.getUsername();
        String bio = user.getBio();
        return ("Username: " + username + "\nBio: " + bio);
    }

    /**
     * Return the choices available for user
     */
    private String getCustomizeProfileScreen() {
        return this.CustomizeProfileScreen;
    }

    /**
     * Change username into new_username, return false if old username is the same as the new one given, true otherwise
     */
    public boolean changeUsername(User user, String new_username) {
        if (user.getUsername().equals(new_username)) {
            return false;
        }
        else {
            user.setUsername(new_username);
            return true;
        }
    }

    /**
     * Change password into new_password, return false if old password is the same as the new one given, true otherwise
     */
    public boolean changePassword(User user, String new_password) {
        if (user.getPassword().equals(new_password)) {
            return false;
        }
        else {
            user.setPassword(new_password);
            return true;
        }
    }

    /**
     * Change bio into new_bio, return false if old bio is the same as new_bio, true otherwise
     */
    public boolean changeBio(User user, String new_bio) {
        if (user.getBio().equals(new_bio)) {
            return false;
        }
        else {
            user.setBio(new_bio);
            return true;
        }
    }
}
