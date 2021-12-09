package controllers;

import entities.User;
import entities.Post;
import use_cases.DatabaseManager;
import use_cases.LoginManager;
import use_cases.UserManager;

import java.util.HashMap;

public class UserProfileController {
    private final InOut inOut;
    private final LoginManager loginManager;
    private final UserManager userManager;
    private final DatabaseManager databaseManager;

    /**
     * Create a UserProfileController with the given inOut, DatabaseManager, and LoginManager
     *
     * @param inOut          the inOut interface for managing input/output
     * @param dbManager      the DatabaseManager
     * @param loginManager   the LoginManager
     */
    public UserProfileController(InOut inOut, DatabaseManager dbManager, LoginManager loginManager) {
        this.inOut = inOut;
        this.loginManager = loginManager;
        this.databaseManager = dbManager;
        this.userManager = new UserManager(dbManager);
    }

    /**
     * Run the appropriate ShellAction
     *
     * @param action ShellAction corresponding to the action that needs to be run
     */
    public void run(ShellAction action) {
        if (action == ShellAction.BROWSEPROFILE) {
            this.runBrowseProfile();

        } else if (action == ShellAction.CUSTOMIZEPROFILE) {
            this.runCustomizeProfile();
        }
    }

    /**
     * Return the choices available for user
     *
     * @return a string representing options for the user
     */
    private String getCustomizeProfileScreen() {
        return "Select which option you'd like to customize:\n" +
                "0 Username\n" +
                "1 Password\n" +
                "2 Bio\n" +
                "3 Posts\n" +
                "4 Following list\n" +
                "99 Return to main menu\n";
    }

    /**
     * Run the browse profile logic
     */
    public void runBrowseProfile() {
        // display all the usernames in the database
        this.runDisplayAllUsers();
        String userToBrowse = this.inOut.getInput("Enter the username of the person you'd like to view: ");
        if (this.userManager.checkUserToBrowse(userToBrowse)) {
            User targetUser = this.userManager.findUser(userToBrowse);
            this.inOut.setOutput(this.userManager.runBrowseOtherProfile(targetUser));
            // Give the choice of following user or looking into user's post
            String otherUserScreen = "Select which action you'd like to take:\n" +
                    "0 Follow user\n" +
                    "1 Browse user's posts\n" +
                    "99 Return to main menu\n";
            int choice = Integer.parseInt(this.inOut.getInput(otherUserScreen));
            if (choice == 0) {
                this.runFollowUser(this.loginManager.getCurrUser(), targetUser);
            } else if (choice == 1) {
                if (this.runCheckUserAtleastOnePost(targetUser)) {
                    this.runDisplayUserPosts(targetUser);
                }
                else {
                    this.inOut.setOutput("Target user has no posts! Returning to main menu");
                }

            }
        } else {
            this.inOut.setOutput("Error! Either user is not in the database or user is yourself! " +
                    "Returning to main page");
        }
    }

    /**
     * Run the customize profile logic
     */
    public void runCustomizeProfile() {
        this.inOut.setOutput(this.getCustomizeProfileScreen());

        int choice = Integer.parseInt(this.inOut.getInput("Select an option: "));
        if (choice == 0) {
            this.runCustomizeUsername();
        } else if (choice == 1) {
            this.runCustomizePassword();
        } else if (choice == 2) {
            this.runCustomizeBio();
        } else if (choice == 3) {
            if (this.runCheckUserAtleastOnePost(this.loginManager.getCurrUser())) {
                this.runDisplayUserPosts(this.loginManager.getCurrUser());
            }
            else {
                this.inOut.setOutput("You have no posts! Returning to main menu.");
            }
        } else if (choice == 4) {
            // Show following list
            this.inOut.setOutput(this.userManager.getFollowingListString(this.loginManager.getCurrUser()));
            String action = this.inOut.getInput("Would you like to unfollow one of these users? (y/n): ");
            if (action.equalsIgnoreCase("y")) {
                this.runUnfollowUser();
            }
        }
    }

    /**
     * Run the change username logic
     */
    private void runChangeUsernameDisplay(User user, String newUsername) {
        if (this.userManager.changeUsername(user, newUsername)) {
            this.userManager.changeUsername(user, newUsername);
            this.inOut.setOutput("Successfully changed username to: " + newUsername + "\n" + "Returning to main page.");
        } else {
            this.inOut.setOutput("Username entered is the same as the old one. Returning to main page.");
        }
    }

    /**
     * Run the change password logic
     */
    private void runChangePasswordDisplay(User user, String newPassword) {
        if (this.userManager.changePassword(user, newPassword)) {
            this.userManager.changePassword(user, newPassword);
            this.inOut.setOutput("Successfully changed password to: " + newPassword + "\n" + "Returning to main page.");
        } else {
            this.inOut.setOutput("Password entered is the same as the old one. Returning to main page");
        }
    }

    /**
     * Run the change bio logic
     */
    private void runChangeBioDisplay(User user, String newBio) {
        if (this.userManager.changeBio(user, newBio)) {
            user.setBio(newBio);
            this.databaseManager.updateUser(user);
            this.inOut.setOutput("Successfully changed bio to: " + newBio + "\n" + "Returning to main page.");
        } else {
            this.inOut.setOutput("Bio given is the same as the old one. Returning to main page");
        }
    }

    /**
     * Runs the follow user logic: display error msg if target_user is already followed by user, return successful msg
     * otherwise and follow target_user
     *
     * @param user       the currently logged-in user
     * @param targetUser the user that the currently logged-in user wishes to follow
     */
    private void runFollowUser(User user, User targetUser) {
        if (this.userManager.followUser(user, targetUser)) {
            this.inOut.setOutput("Successfully followed the target user!");
        } else {
            this.inOut.setOutput("Either target user already followed, or target user is yourself!");
        }
    }

    /**
     * Runs the unfollow user logic
     */
    private void runUnfollowUser() {
        String usernameToUnfollow =
                this.inOut.getInput("Enter the username of the person you'd like to unfollow: ");
        User userToUnfollow = this.userManager.findUser(usernameToUnfollow);
        if (this.userManager.unfollowUser(this.loginManager.getCurrUser(), userToUnfollow)) {
            this.inOut.setOutput("Successfully unfollowed the target user!");
        } else {
            this.inOut.setOutput("User is not followed to begin with!");
        }
    }

    /**
     * Display all currently registered usernames
     */
    private void runDisplayAllUsers() {
        User[] users = this.databaseManager.getAllUsers();
        StringBuilder allUsernames = new StringBuilder();
        for (User user : users) {
            allUsernames.append(" ").append(this.userManager.getUsername(user)).append(", ");
        }
        this.inOut.setOutput("Currently registered users:" + allUsernames.substring(0, allUsernames.length() - 2));
    }

    /**
     * Display user's posts
     */
    private void runDisplayUserPosts(User user) {
        // Generate User posts mapping
        HashMap<Integer, Post> userPosts = this.userManager.generateUserPostsMap(user);
        // Generate the output of the user posts to view
        String userPostsString = this.generateDisplayedPosts(userPosts);
        // Give option to select which of the user's post to view and view
        this.selectOnePost(userPostsString, userPosts);
    }

    /**
     * Run the customize username logic
     */
    private void runCustomizeUsername() {
        String newUsername = this.inOut.getInput("Enter what you'd like your new username to be: ");
        this.runChangeUsernameDisplay(this.loginManager.getCurrUser(), newUsername);
    }

    /**
     * Run the customize password logic
     */
    private void runCustomizePassword() {
        String newPassword = this.inOut.getInput("Enter what you'd like your new password to be: ");
        this.runChangePasswordDisplay(this.loginManager.getCurrUser(), newPassword);
    }

    /**
     * Run the customize bio logic
     */
    private void runCustomizeBio() {
        String newBio = this.inOut.getInput("Enter what you'd like your new bio to be: ");
        this.runChangeBioDisplay(this.loginManager.getCurrUser(), newBio);
    }

    /**
     * Generate a selection of displayed posts' titles for the user to select.
     *
     * @param userPosts A hashmap of Posts representing a user's posts.
     * @return a String representing all the posts of a user.
     */
    public String generateDisplayedPosts(HashMap<Integer, Post> userPosts) {
        StringBuilder postsString = new StringBuilder("Enter a post number to view the post: ");

        for (int i = 0; i < userPosts.size(); i++) {
            postsString.append("\n");
            postsString.append(i).append(" ");
            postsString.append(userPosts.get(i).getRecipe().getTitle());
        }
        return postsString.toString();
    }

    /**
     * Allow the user to act on a post selected.
     *
     * @param userPostsString     the string representing a user's posts
     */
    public void selectOnePost(String userPostsString, HashMap<Integer, Post> userPosts) {
        PostController postController = new PostController(this.inOut, this.databaseManager, this.loginManager);
        int postNumber = this.getSelectedPostInput(userPostsString);
        Post selectedPost = userPosts.get(postNumber);
        postController.browsePost(selectedPost);

    }

    /**
     * Get the user to select a post to act on.
     *
     * @param userPostsString     the String representing the selection of displayed Posts' titles generated.
     * @return an int representing the Post that the user selects.
     */
    private int getSelectedPostInput(String userPostsString) {
        int postNumber;

        try {
            String postSelection = this.inOut.getInput(userPostsString);
            postNumber = Integer.parseInt(postSelection);
            if (!userPostsString.contains(postSelection)) {
                this.inOut.setOutput("You entered an invalid action input.");
                postNumber = getSelectedPostInput(userPostsString);
            }
        } catch (NumberFormatException nfe) {
            this.inOut.setOutput("You entered an invalid action input.");
            postNumber = getSelectedPostInput(userPostsString);
        }

        return postNumber;
    }

    /**
     * Return true if user has at least 1 post, false otherwise
     *
     * @param user the user to check whether they have at least 1 post
     * @return boolean true if user has at least 1 post, false otherwise
     */
    private boolean runCheckUserAtleastOnePost(User user) {
        return (this.userManager.generateUserPostsMap(user).size() > 0);
    }
}
