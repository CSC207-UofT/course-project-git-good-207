package controllers;

import entities.InOut;
import entities.ShellAction;
import entities.User;
import use_cases.LoginManager;
import use_cases.UserManager;

import java.io.IOException;

public class LoginPresenter {
    private LoginManager loginManager;
    private String welcomeMessage = "Welcome to the Recipe App!";
    private InOut inOut;

    public LoginPresenter(InOut inOut, LoginManager loginManager){
        this.loginManager = loginManager;
        this.inOut = inOut;
    }

    public void run(ShellAction action) {
        if (action == ShellAction.LOGOUT) {
            this.runLogout();
        }
    }

    /**
     * Runs the login page logic. Returns a boolean which is true if the user
     * successfully logged in, false otherwise.
     * */
    public boolean runLoginPage() {
        this.inOut.setOutput(this.getWelcomeMessage());
        try {
            String username = this.inOut.getInput("Enter username: ");
            String password = this.inOut.getInput("Enter password: ");
            if (this.loginManager.login(username, password)) {
                this.inOut.setOutput("Login successful.");
                return true;
            } else {
                this.inOut.setOutput("Your username or password was incorrect.");
                return false;
            }
        } catch (IOException e) {
            inOut.setOutput("There was an error: " + e);
            return false;
        }

    }

    private void runLogout() {
        this.loginManager.logout();
        System.out.println("Logged out of the Recipe App.");
    }

    private String getWelcomeMessage() {
        return this.welcomeMessage;
    }
}
