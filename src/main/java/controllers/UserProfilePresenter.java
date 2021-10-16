package controllers;

import java.util.HashMap;
import java.util.ArrayList;

import entities.InOut;
import entities.ShellAction;
import entities.User;
import entities.Post;
import use_cases.LoginManager;
import use_cases.UserManager;

public class UserProfilePresenter {
    private InOut inOut;
    /*private String username; // the username that currentUser wants to view the profile of*/

    // Constructors
    public UserProfilePresenter(InOut inOut) {
        this.inOut = inOut;
    }

//    public UserProfilePresenter(InOut inOut, String username) {
//        this.inOut = inOut;
//        this.username = username;
//    } For when we have the database manager



    public void run(ShellAction action) {
        // TODO: get user inputs/display info based on the given action
        if (action == ShellAction.BROWSEPROFILE) {
            // Browse user's profile, given String username:
            // run a helper method (getUserToBrowse) to call Database manager to get the user object given the username
            // run a helper method called runBrowseProfile given User object to browse the user's profile
        }

        else if (action == ShellAction.CUSTOMIZEPROFILE) {
            // Customize user's profile:
            // 1. Display current user's profile
            // 2. Ask current user on what they want to customize: username, bio, password
            // 3. Customize one of the three: username, bio, or password
            // run a helper method called runBrowseProfile given current user to browse current user's profile
            // run a helper method called runCustomizeProfile
//            /*this.runBrowseProfile(LoginManager.getCurrUser());*/
//            A Non-static method cannot be referenced from a static context error
        }
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
}
