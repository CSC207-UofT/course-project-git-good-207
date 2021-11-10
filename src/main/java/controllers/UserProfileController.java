package controllers;

import java.io.IOException;
import java.util.ArrayList;

import entities.InOut;
import entities.ShellAction;
import entities.User;
import entities.Post;
import use_cases.DatabaseManager;
import use_cases.LoginManager;
import use_cases.PostManager;
import use_cases.UserManager;

public class UserProfileController {
    private InOut inOut;
    private String customizeProfileScreen = "Select which option you'd like to customize:" +
            "\n0 Username \n1 Password \n2 Bio \n3 Posts \n4 Following list";
    private String otherUserScreen = "Select which action you'd like to take:" +
            "\n0 Follow user \n1 Browse user's posts";
    private String userToBrowsePrompt = "Enter the username of the person you'd like to view: ";
    private LoginManager loginManager;
    private UserManager userManager;
    private DatabaseManager databaseManager;

    // Constructor
    public UserProfileController(InOut inOut, LoginManager loginManager) {
        this.inOut = inOut;
        this.loginManager = loginManager;
        this.databaseManager = new DatabaseManager();
        this.userManager = new UserManager();
    }


    public void run(ShellAction action) {
        if (action == ShellAction.BROWSEPROFILE) {
            this.runBrowseProfile();

        } else if (action == ShellAction.CUSTOMIZEPROFILE) {
            this.runCustomizeProfile();
        }
    }

    /**
     * Return the choices available for user
     */
    private String getCustomizeProfileScreen() {
        return this.customizeProfileScreen;
    }

    /**
     * getUserInformation, Displays the user from the username given, return to main page if user not found
     */
    public void runBrowseProfile() {
        // Browse user's profile, given String username:
        // run a helper method (checkUserToBrowse) to call Database manager to check whether the username belongs
        // to a user in the database
        // run findUser to get the user that owns the username from the database
        try {
            // display all the usernames in the database
            this.runDisplayAllUsers();
            String userToBrowse = this.inOut.getInput("Enter the username of the person you'd like to view: ");
            if (this.userManager.checkUserToBrowse(userToBrowse) &&
                    !(userToBrowse.equals(this.loginManager.getCurrUser().getUsername()))) {
                User targetUser = this.userManager.findUser(userToBrowse);
                this.inOut.setOutput(this.userManager.runBrowseOtherProfile(targetUser));
                // Give the choice of following user or looking into user's post
                int choice = Integer.parseInt(this.inOut.getInput(this.otherUserScreen));
                if (choice == 0) {
                    this.runFollowUser(this.loginManager.getCurrUser(), targetUser);
                } else if (choice == 1) {
                    this.runDisplayUserPosts(targetUser);

                }
            } else {
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
        this.inOut.setOutput(this.getCustomizeProfileScreen());
        try {
            int choice = Integer.parseInt(this.inOut.getInput("Select an option: "));
            if (choice == 0) {
                this.runCustomizeUsername();
            } else if (choice == 1) {
                this.runCustomizePassword();
            } else if (choice == 2) {
                this.runCustomizeBio();
            } else if (choice == 3) {
                // TODO: Implement this with a function from PostPresenter to show currentUser's posts
                this.runDisplayUserPosts(this.loginManager.getCurrUser());
            } else if (choice == 4) {
                // Show following list
                this.inOut.setOutput(this.userManager.getFollowingListString(this.loginManager.getCurrUser()));
                String action = this.inOut.getInput("Would you like to unfollow one of these users? (y/n): ");
                if (action.toLowerCase().equals("y")) {
                    this.runUnfollowUser();
                }
            }
        } catch (IOException e) {
            inOut.setOutput("There was an error: " + e);
        }
    }

    /**
     * Displays the prompt to change username
     */
    private void runChangeUsernameDisplay(User user, String newUsername) {
        if (this.userManager.changeUsername(user, newUsername)) {
            this.userManager.changeUsername(user, newUsername);
            this.inOut.setOutput("Successfully changed username to: " + newUsername + "\n");
            this.inOut.setOutput("Returning to main page.");
        } else {
            this.inOut.setOutput("Username entered is the same as the old one. Returning to main page.");
        }
    }

    /**
     * Displays the prompt to change password
     */
    private void runChangePasswordDisplay(User user, String newPassword) {
        if (this.userManager.changePassword(user, newPassword)) {
            this.userManager.changePassword(user, newPassword);
            this.inOut.setOutput("Successfully changed password to: " + newPassword + "\n");
            this.inOut.setOutput("Returning to main page.");
        } else {
            this.inOut.setOutput("Password entered is the same as the old one. Returning to main page");
        }
    }

    /**
     * Displays the prompt to change bio
     */
    private void runChangeBioDisplay(User user, String newBio) {
        if (this.userManager.changeBio(user, newBio)) {
            user.setBio(newBio);
            this.databaseManager.updateUser(user);
            this.inOut.setOutput("Successfully changed bio to: " + newBio + "\n");
            this.inOut.setOutput("Returning to main page.");
        } else {
            this.inOut.setOutput("Bio given is the same as the old one. Returning to main page");
        }
    }

    /**
     * Return error msg if target_user is already followed by user, return successful msg
     * otherwise and follow target_user
     */
    private void runFollowUser(User user, User targetUser) {
        if (this.userManager.followUser(user, targetUser)) {
            this.userManager.followUser(user, targetUser);
            this.inOut.setOutput("Successfully followed the target user!");
        } else {
            this.inOut.setOutput("Target user already followed!");
        }
    }

    /**
     * Return false if target_user is not followed by user, return true otherwise and unfollow target_user
     */
    private void runUnfollowUser() {
        try {
            String usernameToUnfollow =
                    this.inOut.getInput("Enter the username of the person you'd like to unfollow: ");
            User userToUnfollow = this.userManager.findUser(usernameToUnfollow);
            if (this.userManager.unfollowUser(this.loginManager.getCurrUser(), userToUnfollow)) {
                this.inOut.setOutput("Successfully unfollowed the target user!");
            } else {
                this.inOut.setOutput("User is not followed to begin with!");
            }
        } catch (IOException e) {
            inOut.setOutput("There was an error: " + e);
        }
    }

    /**
     * return user's following list
     */
    private String displayFollowingList(User user) {
        ArrayList<String> followingListUsernames = this.userManager.getFollowingListUsernames(user);
        String followingList = "";
        for (String username : followingListUsernames) {
            followingList.concat(username + ", ");
        }
        return followingList;
    }

    /**
     * Display all currently registered usernames
     */
    private void runDisplayAllUsers() {
        User[] users = this.databaseManager.getAllUsers();
        String allUsernames = "";
        for (User user : users) {
            allUsernames += " " + this.userManager.getUsername(user) + ", ";
        }
        this.inOut.setOutput("Currently registered users:" + allUsernames.substring(0, allUsernames.length() - 2));
    }

    /**
     * Display user's posts
     */
    private void runDisplayUserPosts(User user) {
        PostManager postManager = new PostManager();
        PostController postController = new PostController(this.inOut, postManager, this.loginManager);
        for(Post post: this.userManager.getUserPosts(user)) {
            // TODO: UPDATE SO THAT POST ID IS THE PARAM FOR DISPLAYPOST
            // postController.displayPost(post);
        }
    }

    /**
     * Customize a logged-in user's username
     */
    private void runCustomizeUsername() {
        try {
            String newUsername = this.inOut.getInput("Enter what you'd like your new username to be: ");
            runChangeUsernameDisplay(this.loginManager.getCurrUser(), newUsername);
        } catch (IOException e) {
            inOut.setOutput("There was an error: " + e);
        }
    }

    /**
     * Customize a logged-in user's password
     */
    private void runCustomizePassword() {
        try {
            String newPassword = this.inOut.getInput("Enter what you'd like your new password to be: ");
            runChangePasswordDisplay(this.loginManager.getCurrUser(), newPassword);
        } catch (IOException e) {
            inOut.setOutput("There was an error: " + e);
        }
    }

    /**
     * Customize a logged-in user's bio
     */
    private void runCustomizeBio() {
        try {
            String newBio = this.inOut.getInput("Enter what you'd like your new bio to be: ");
            runChangeBioDisplay(this.loginManager.getCurrUser(), newBio);
        } catch (IOException e) {
            inOut.setOutput("There was an error: " + e);
        }
    }

}
