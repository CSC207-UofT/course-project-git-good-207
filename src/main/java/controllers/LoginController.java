package controllers;

import use_cases.LoginManager;


public class LoginController {

    private final LoginManager loginManager;
    private final InOut inOut;

    /**
     * Create a LoginController with the given InOut and LoginManager
     *
     * @param inOut        the InOut interface for managing input/output
     * @param loginManager the LoginManager
     */
    public LoginController(InOut inOut, LoginManager loginManager) {
        this.loginManager = loginManager;
        this.inOut = inOut;
    }

    /**
     * Run the appropriate ShellAction
     *
     * @param action ShellAction corresponding to the action that needs to be run
     */
    public void run(ShellAction action) {
        if (action == ShellAction.LOGOUT) {
            this.runLogout();
        }
    }

    /**
     * Run the welcome page logic.
     */
    public void runWelcomePage() {
        String welcomeActionPrompt = "Please select an action:\n" +
                "0 Sign up\n" +
                "1 Login\n";

        String welcomeMessage = "Welcome to the Recipe App!";

        this.inOut.setOutput(welcomeMessage);


            String welcomeAction = inOut.getInput(welcomeActionPrompt);
            boolean isComplete = runWelcomeAction(Integer.parseInt(welcomeAction));

            //Loops on chosen page until valid input is given
            while (!isComplete) {
                isComplete = runWelcomeAction(Integer.parseInt(welcomeAction));
            }
    }

    /**
     * Run the login page logic
     *
     * @return boolean true if the login is successful, false otherwise
     */
    private boolean runLoginPage() {
        String loginMessage = "Please enter your login info.";
        this.inOut.setOutput(loginMessage);

            //get username and password input
            String username = this.inOut.getInput("Enter username: ");
            String password = this.inOut.getInput("Enter password: ");

            //check if given username and password are valid and set appropriate output
            if (this.loginManager.login(username, password)) {
                this.inOut.setOutput("Login successful.");
                return true;
            } else {
                this.inOut.setOutput("Your username or password was incorrect.");
                return false;
            }

    }

    /**
     * Runs the signup page logic.
     *
     * @return boolean true if user successfully signed up, false otherwise.
     */
    private boolean runSignUpPage() {
        String signUpMessage = "Please signup below:";
        String signUpRulesMessage = "Username must contain:\n " +
                "at least one letter\n" +
                "Password must contain:\n " +
                "6 or more characters,\n " +
                "at least one lowercase letter,\n " +
                "at least one uppercase letter,\n " +
                "and at least one number\n";

            this.inOut.setOutput(signUpMessage);
            this.inOut.setOutput(signUpRulesMessage);
            String username = this.inOut.getInput("Set username: ");
            String password = this.inOut.getInput("Set password: ");
            if (this.loginManager.signUp(username, password)) {
                this.inOut.setOutput("Sign up successful.");
                return true;
            } else {
                this.inOut.setOutput("Your username or password was invalid.");
                return false;
            }
    }

    /**
     * Handles actions from the welcome page
     *
     * @param welcomeAction The entered action from welcome page
     * @return boolean true if action was successful, false otherwise
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

    /**
     * Run logout logic
     */
    private void runLogout() {
        this.loginManager.logout();
        inOut.setOutput("Logged out of the Recipe App.");
    }

}
