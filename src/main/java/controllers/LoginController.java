package controllers;

import entities.InOut;
import entities.ShellAction;
import use_cases.LoginManager;

import java.io.IOException;

public class LoginController {
    private LoginManager loginManager;
    private String loginMessage = "Please enter your login info.";
    private String welcomeMessage = "Welcome to the Recipe App!";
    private String welcomeActionPrompt = "Please select an action: \n"
            + "0 Sign up\n"
            + "1 Log in";
    private InOut inOut;

    public LoginController(InOut inOut, LoginManager loginManager){
        this.loginManager = loginManager;
        this.inOut = inOut;
    }

    public void run(ShellAction action) {
        if (action == ShellAction.LOGOUT) {
            this.runLogout();
        }
    }

    public void runWelcomePage(){
        this.inOut.setOutput(this.getWelcomeMessage());
        try{
            String welcomeAction = inOut.getInput(welcomeActionPrompt);
            boolean isComplete = runWelcomeAction(Integer.parseInt(welcomeAction));
            while(!isComplete){
                isComplete = runWelcomeAction(Integer.parseInt(welcomeAction));
            }
        }catch (IOException e){
            inOut.setOutput("There was an error: " + e);
        }
    }

    /**
     * Runs the login page logic. Returns a boolean which is true if the user
     * successfully logged in, false otherwise.
     * */
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
            inOut.setOutput("There was an error: " + e);
            return false;
        }

    }

    private boolean runSignInPage(){
        try {
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

    private boolean runWelcomeAction(Integer welcomeAction){
        switch(welcomeAction){
            case(0):
                return this.runSignInPage();
            case(1):
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
