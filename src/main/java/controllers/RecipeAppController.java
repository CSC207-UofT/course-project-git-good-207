package controllers;

import entities.*;
import use_cases.DatabaseManager;
import use_cases.LoginManager;
import use_cases.PostManager;
import use_cases.UserManager;

import java.io.IOException;
import java.util.Scanner;

public class RecipeAppController {
    private InOut inOut;
    private LoginPresenter loginPresenter;
    private FeedPresenter feedPresenter;
    private PostPresenter postPresenter;
    private UserProfilePresenter userProfilePresenter;
    private LoginManager loginManager;
    private PostManager postManager = new PostManager();
    private String shellActionPrompt =
            "Enter an action:\n" +
            "0 Browse your Feed\n" +
            "1 Browse a User Profile\n" +
            "2 Post a Recipe\n" +
            "3 Customize your User Profile\n" +
            "4 Logout\n";

    public RecipeAppController(InOut inOut) {
        this.inOut = inOut;
        this.feedPresenter = new FeedPresenter(inOut);
        this.loginManager = new LoginManager(new UserManager());
        this.loginPresenter = new LoginPresenter(inOut, this.loginManager);
        this.userProfilePresenter = new UserProfilePresenter(inOut, this.loginManager);
        this.postPresenter = new PostPresenter(inOut, this.postManager, this.loginManager);
    }

    public void run() {
        while (true) {
            if (this.loginManager.getCurrUser() == null) {
                this.loginPresenter.runLoginPage();
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
            this.feedPresenter.run(ShellAction.BROWSEFEED);
        } else if (action == ShellAction.BROWSEPROFILE) {
            this.userProfilePresenter.run(ShellAction.BROWSEPROFILE);
        } else if (action == ShellAction.POST) {
            this.postPresenter.run(ShellAction.POST);
        } else if (action == ShellAction.CUSTOMIZEPROFILE) {
            this.userProfilePresenter.run(ShellAction.CUSTOMIZEPROFILE);
        } else if (action == ShellAction.LOGOUT) {
            this.loginPresenter.run(ShellAction.LOGOUT);
        } else {
            this.inOut.setOutput("That is not a valid action.");
        }
    }

    private String getShellActionPrompt() {
        return this.shellActionPrompt;
    }
}
