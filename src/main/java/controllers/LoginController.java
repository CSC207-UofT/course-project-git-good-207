package controllers;

import entities.InOut;
import entities.ShellAction;
import use_cases.DatabaseManager;
import use_cases.LoginManager;

import java.io.IOException;

public class LoginController {
    private LoginManager loginManager;
    private String loginMessage = "Please enter your login info.";
    private String signUpMessage = "Please signup below:";
    private String welcomeMessage = "Welcome to the Recipe App!";
    private String welcomeActionPrompt = """
            Please select an action:
                        
            0 Sign up
                        
            1 Login""";
    private InOut inOut;

    public LoginController(InOut inOut, LoginManager loginManager) {
        this.loginManager = loginManager;
        this.inOut = inOut;
    }

    public void run(ShellAction action) {
        if (action == ShellAction.LOGOUT) {
            this.runLogout();
        }
    }

    /**
     * Runs the welcome page logic.
     */
    public void runWelcomePage() {
        this.inOut.setOutput(this.getWelcomeMessage());
        try {
            String welcomeAction = inOut.getInput(welcomeActionPrompt);
            boolean isComplete = runWelcomeAction(Integer.parseInt(welcomeAction));

            //Loops on chosen page until valid input is given
            // TODO: evaluate if this is the best way to make user stay on login/signup page if input invalid
            while (!isComplete) {
                isComplete = runWelcomeAction(Integer.parseInt(welcomeAction));
            }
        } catch (IOException e) {
            inOut.setOutput("There was an error: " + e);
        }
    }

    /**
     * Runs the login page logic. Returns a boolean which is true if the user
     * successfully logged in, false otherwise.
     */
    private boolean runLoginPage() {
        this.inOut.setOutput(this.loginMessage);
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
            this.inOut.setOutput("There was an error: " + e);
            return false;
        }

    }

    /**
     * Runs the sign in page logic.
     *
     * @return Returns true if user successfully signed up, false otherwise.
     */
    private boolean runSignUpPage() {
        try {
            this.inOut.setOutput(this.signUpMessage);
            String username = this.inOut.getInput("Set username: ");
            String password = this.inOut.getInput("Set password: ");
            if (this.loginManager.signUp(username, password)) {
                this.inOut.setOutput("Sign up successful.");
                return true;
            } else {
                this.inOut.setOutput("Your username or password was invalid.");
                return false;
            }
        } catch (IOException e) {
            inOut.setOutput("There was an error: " + e);
            return false;
        }
    }

    /**
     * Handles actions from the welcome page
     *
     * @param welcomeAction The entered action from welcome page
     * @return true if action was successful, false otherwise
     */
    private boolean runWelcomeAction(Integer welcomeAction) {
        switch (welcomeAction) {
            case (0):
                return this.runSignUpPage();
            case (1):
                return this.runLoginPage();
            default:
                this.inOut.setOutput("You entered an invalid action input.");
                return true;
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
