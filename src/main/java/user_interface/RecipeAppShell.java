package user_interface;

import entities.Ingredient;
import entities.ShellAction;
import use_cases.LoginManager;
import use_cases.UserManager;

import java.util.Scanner;

public class RecipeAppShell {
    private LoginManager loginManager; // TODO: change this to LoginPresenter
    private Scanner in = new Scanner(System.in);

    public RecipeAppShell() {
        this.loginManager = new LoginManager(new UserManager());
    }

    public void run() {
        if (this.loginManager.getCurrUser() == null)
            this.runLoggedOutState();
        else {
            this.runLoggedInState();
        }
    }

    public String getWelcomeMessage() {
        return "Welcome to the Recipe App!";
    }

    public String getShellActions() {
        return """
            Enter an action:
            0 Browse your Feed
            1 Browse a User Profile
            2 Post a Recipe
            3 Follow a User
            4 Customize your User Profile
            5 Logout
        """;
    }

    private void runLoggedOutState() {
        while(this.loginManager.getCurrUser() == null) {
            System.out.println(this.getWelcomeMessage());
            System.out.println("Enter username: ");
            String username = this.in.nextLine();
            System.out.println("Enter password: ");
            String password = this.in.nextLine();
            if (this.loginManager.login(username, password)) {
                System.out.println("Login successful.");
            } else {
                System.out.println("Your username or password was incorrect.");
            }
        }
    }

    private void runLoggedInState() {
        while(this.loginManager.getCurrUser() != null) {
            System.out.println(this.getShellActions());
            String action = this.in.nextLine();
            this.runAction(this.getShellActionEnum(action));
            System.out.println("You selected action " + action);
        }
    }

    private ShellAction getShellActionEnum(String action) {
        if (action.equals("0")) {
            return ShellAction.BROWSEFEED;
        } else if (action.equals("1")) {
            return ShellAction.BROWSEPROFILE;
        } else if (action.equals("2")) {
            return ShellAction.POST;
        } else if (action.equals("3")) {
            return ShellAction.FOLLOW;
        } else if (action.equals("4")) {
            return ShellAction.CUSTOMIZEPROFILE;
        } else if (action.equals("5")) {
            return ShellAction.LOGOUT;
        } else {
            return ShellAction.INVALIDACTION;
        }
    }

    private void runAction(ShellAction action) {
        if (action == ShellAction.BROWSEFEED) {
            // TODO: get Feed from FeedPresenter
            System.out.println("This is your imaginary feed");
        } else if (action == ShellAction.BROWSEPROFILE) {
            // TODO: call UserProfilePresenter to display user profile info
        } else if (action == ShellAction.POST) {
            this.runPostCreation();
        } else if (action == ShellAction.FOLLOW) {
            this.runUserFollow();
        } else if (action == ShellAction.CUSTOMIZEPROFILE) {
            this.runProfileCustomize();
        } else if (action == ShellAction.LOGOUT) {
            this.runLogout();
        } else {
            System.out.println("That is not a valid action.");
        }
    }

    private void runPostCreation() {
        System.out.println("Enter your recipe title: ");
        String title = this.in.nextLine();
        while (true) {
            System.out.println("Enter an ingredient name: ");
            String ingredientName = this.in.nextLine();
            System.out.println("Is your ingredient measurable? (Y/N)");
            // TODO validate Y/N
            String measureableAnswer = this.in.nextLine();
            Boolean measurable = true;
            System.out.println("Enter a quantity for your ingredient: ");
            String quantity = this.in.nextLine();
            Ingredient ingredient = new Ingredient();
            System.out.println("Add another ingredient? (Y/N)");
            String addIngredientAnswer = this.in.nextLine();
            break;
        }
    }

    private void runProfileCustomize() {

    }

    private void runUserFollow() {}

    private void runLogout() {
        // TODO: replace with loginPresenter
        this.loginManager.logout();
        System.out.println("Logged out of the Recipe App.");
    };

    public static void main(String[] args) {
        RecipeAppShell shell = new RecipeAppShell();
        while(true) {
            shell.run();
        }
    }
}
