package controllers;

import use_cases.LoginManager;

import java.io.IOException;

public class RecipeAppController {
    private final InOut inOut;
    private final MySQLController mySQLController;
    private final LoginController loginController;
    private final FeedController feedController;
    private final PostController postController;
    private final UserProfileController userProfileController;
    private final LoginManager loginManager;
    private final String shellActionPrompt = "Enter an action: \n" +
            "0 Browse your Feed\n" +
            "1 Browse a User Profile\n" +
            "2 Post a Recipe\n" +
            "3 Customize your User Profile\n" +
            "4 Logout\n";

    public RecipeAppController(InOut inOut) {
        this.inOut = inOut;
        this.mySQLController = new MySQLController();
        this.loginManager = new LoginManager(this.mySQLController);
        this.loginController = new LoginController(inOut, this.loginManager);
        this.postController = new PostController(inOut, this.mySQLController, this.loginManager);
        this.userProfileController = new UserProfileController(inOut, this.mySQLController, this.loginManager,
                this.postController);
        this.feedController = new FeedController(inOut, this.mySQLController, this.loginManager, this.postController);
    }

    public void run() {
        while (true) {
            if (this.loginManager.getCurrUser() == null) {
                this.loginController.runWelcomePage();
            } else {
                this.runLoggedInState();
            }
        }
    }

    private void runLoggedInState() {
        try {
            String action = this.inOut.getInput(this.getShellActionPrompt());
            this.runAction(this.getShellActionEnum(action));
            this.inOut.setOutput("You selected action: " + action);
        } catch (IOException e) {
            this.inOut.setOutput("An error occurred: " + e);
        }
    }

    private ShellAction getShellActionEnum(String action) {
        switch (action) {
            case "0":
                return ShellAction.BROWSEFEED;
            case "1":
                return ShellAction.BROWSEPROFILE;
            case "2":
                return ShellAction.POST;
            case "3":
                return ShellAction.CUSTOMIZEPROFILE;
            case "4":
                return ShellAction.LOGOUT;
            default:
                return ShellAction.INVALIDACTION;
        }
    }

    private void runAction(ShellAction action) {
        if (action == ShellAction.BROWSEFEED) {
            this.feedController.run(ShellAction.BROWSEFEED);
        } else if (action == ShellAction.BROWSEPROFILE) {
            this.userProfileController.run(ShellAction.BROWSEPROFILE);
        } else if (action == ShellAction.POST) {
            this.postController.run(ShellAction.POST);
        } else if (action == ShellAction.CUSTOMIZEPROFILE) {
            this.userProfileController.run(ShellAction.CUSTOMIZEPROFILE);
        } else if (action == ShellAction.LOGOUT) {
            this.loginController.run(ShellAction.LOGOUT);
        } else {
            this.inOut.setOutput("That is not a valid action.");
        }
    }

    private String getShellActionPrompt() {
        return this.shellActionPrompt;
    }
}
