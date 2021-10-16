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
            "\n0 Username \n1 Password \n2 Bio \n3 Posts \n4 Following list";
    private String OtherUserScreen = "Select which action you'd like to take:" +
            "\n0 Follow user \n1 Browse user's posts";
    private String UserToBrowsePrompt = "Enter the username of the person you'd like to view: ";
    private LoginManager loginmanager;
    private UserManager usermanager;
    private DatabaseManager databasemanager;

    // Constructor
    public UserProfilePresenter(InOut inOut, LoginManager loginmanager) {
        this.inOut = inOut;
        this.loginmanager = loginmanager;
        this.databasemanager = new DatabaseManager();
        this.usermanager = new UserManager();
    }


    public void run(ShellAction action) {
        if (action == ShellAction.BROWSEPROFILE) {
            this.runBrowseProfile();

        }

        else if (action == ShellAction.CUSTOMIZEPROFILE) {
            this.runCustomizeProfile();
        }
    }

    /**
     * Return the choices available for user
     */
    private String getCustomizeProfileScreen() {
        return this.CustomizeProfileScreen;
    }

    /**
     * runBrowseProfile, Displays the user from the username given, return to main page if user not found
     */
    public void runBrowseProfile() {
        // Browse user's profile, given String username:
        // run a helper method (checkUserToBrowse) to call Database manager to check whether the username belongs
        // to a user in the database
        // run findUser to get the user that owns the username from the database
        try {
            String user_to_browse = this.inOut.getInput("Enter the username of the person you'd like to view: ");
            if (this.usermanager.checkUserToBrowse(user_to_browse) ||
                    user_to_browse.equals(this.loginmanager.getCurrUser().getUsername())) {
                User target_user = this.usermanager.findUser(user_to_browse);
                this.inOut.setOutput(this.usermanager.runBrowseOtherProfile(target_user));
                // Give the choice of look following user or looking into user's post
                int choice = Integer.parseInt(this.inOut.getInput(this.OtherUserScreen));
                if (choice == 0) {
                    this.runFollowUser(this.loginmanager.getCurrUser(), target_user);

                }
                else if (choice == 1) {
                    // TODO: Implement with a function from PostPresenter
                    // show user's posts w/ PostPresenter
                }
            }
            else {
                this.inOut.setOutput("Error! Either user is not in the database or user is yourself! " +
                        "Returning to main page");
            }
        } catch (IOException e) {
            inOut.setOutput("There was an error: " + e);
        }
    }

    /**
     * runCustomizeProfile, Displays current user's profile and asks
     */
    public void runCustomizeProfile() {
        this.inOut.setOutput(this.usermanager.runBrowseProfile(this.loginmanager.getCurrUser()));
        this.inOut.setOutput(this.getCustomizeProfileScreen());
        try {
            int choice = Integer.parseInt(this.inOut.getInput("Select an option: "));
            if (choice == 0) {
                String new_username = this.inOut.getInput("Enter what you'd like your new username to be: ");
                runChangeUsernameDisplay(this.loginmanager.getCurrUser(), new_username);
            }
            else if (choice == 1) {
                String new_password = this.inOut.getInput("Enter what you'd like your new password to be: ");
                runChangePasswordDisplay(this.loginmanager.getCurrUser(), new_password);

            }
            else if (choice == 2) {
                String new_bio = this.inOut.getInput("Enter what you'd like your new bio to be: ");
                runChangeBioDisplay(this.loginmanager.getCurrUser(), new_bio);
            }
            else if (choice == 3) {
                // TODO: Implement this with a function from PostPresenter to show currentUser's posts
            }
            else if (choice == 4) {
                // Show following list
//                this.inOut.setOutput(this.DisplayFollowingList(this.loginmanager.getCurrUser()));
                this.inOut.setOutput(this.usermanager.getFollowingListString(this.loginmanager.getCurrUser()));
                String action = this.inOut.getInput("Would you like to unfollow one of these users? (y/n): ");
                if (action.toLowerCase().equals("y")) {
                    String user_to_unfollow =
                            this.inOut.getInput("Enter the username of the person you'd like to unfollow: ");
                    if (this.usermanager.checkUserToBrowse(user_to_unfollow)) {
                        this.runUnfollowUser(loginmanager.getCurrUser(), usermanager.findUser(user_to_unfollow));
                    }
                    else { this.inOut.setOutput("User not in database! Returning to main page"); }
                }
            }
        } catch(IOException e) {
            inOut.setOutput("There was an error: " + e);
        }
    }

    /**
     * Displays the prompt to change username
     */
    private void runChangeUsernameDisplay(User user, String new_username) {
        if (this.usermanager.changeUsername(user, new_username)) {
            this.usermanager.changeUsername(user, new_username);
            this.inOut.setOutput("Successfully changed username to: " + new_username + "\n");
            this.inOut.setOutput("Returning to main page.");
        }
        else {
            this.inOut.setOutput("Username entered is the same as the old one. Returning to main page.");
        }
    }

    /**
     * Displays the prompt to change password
     */
    private void runChangePasswordDisplay(User user, String new_password) {
        if (this.usermanager.changePassword(user, new_password)) {
            this.usermanager.changePassword(user, new_password);
            this.inOut.setOutput("Successfully changed password to: " + new_password + "\n");
            this.inOut.setOutput("Returning to main page.");
        }
        else {
            this.inOut.setOutput("Password entered is the same as the old one. Returning to main page");
        }
    }

    /**
     * Displays the prompt to change bio
     */
    private void runChangeBioDisplay(User user, String new_bio) {
        if (this.usermanager.changeBio(user, new_bio)) {
            this.usermanager.changeBio(user, new_bio);
            this.inOut.setOutput("Successfully changed bio to: " + new_bio + "\n");
            this.inOut.setOutput("Returning to main page.");
        }
        else {
            this.inOut.setOutput("Bio given is the same as the old one. Returning to main page");
        }
    }

    /**
     * Return error msg if target_user is already followed by user, return successful msg
     * otherwise and follow target_user
     */
    public void runFollowUser(User user, User target_user) {
        if (this.usermanager.follow_user(user, target_user)) {
            this.usermanager.follow_user(user, target_user);
            this.inOut.setOutput("Successfully followed the target user!");
        }
        else {
            this.inOut.setOutput("Target user already followed!");
        }
    }

    /**
     * Return false if target_user is not followed by user, return true otherwise and unfollow target_user
     */
    public void runUnfollowUser(User user, User target_user) {
        if (this.usermanager.unfollow_user(user, target_user)) {
            this.inOut.setOutput("Successfully unfollowed the target user!");
        }
        else {
            this.inOut.setOutput("User is not followed to begin with!");
        }
    }

    /**
     * Display user's following list
     */
    public String DisplayFollowingList(User user) {
        ArrayList<String> following_list_usernames = this.usermanager.getFollowingListUsernames(user);
        String following_list = "";
        for (String username: following_list_usernames) {
            following_list.concat(username + ", ");
        }
        return following_list;
    }
}
