package controllers;

import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_cases.LoginManager;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {
    final MySQLController mySQLController = new MySQLController();
    final LoginManager loginManager = new LoginManager(mySQLController);
    final DummyInOut inOut = new DummyInOut();
    final LoginController loginController = new LoginController(inOut, loginManager);
    final ArrayList<String> inputs = new ArrayList<>();

    @BeforeEach
    void clearInputs() {
        inputs.clear();
    }

    @Test
    void testRun() {
        loginManager.login("shawn", "1234");
        loginController.run(ShellAction.LOGOUT);


        assertNull(loginManager.getCurrUser());
    }

    @Test
    void testRunWelcomePageInvalidInput() {
        ArrayList<String> input = new ArrayList<>(List.of("hey"));
        inOut.setInput(input);
        loginController.runWelcomePage();
        ArrayList<String> actualOutputs = inOut.getOutputs();
        String expectedOutput = "You entered an invalid action input.";

        assertTrue(actualOutputs.contains(expectedOutput));
    }

    @Test
    void testRunWelcomePageSignUpValid() {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("0", "username", "1234"));
        inOut.setInput(input);
        loginController.runWelcomePage();
        ArrayList<String> actualOutputs = inOut.getOutputs();
        String expectedOutput = "Sign up successful.";

        //Clear user used
        this.deleteUser("username");

        assertTrue(actualOutputs.contains(expectedOutput));
    }

    @Test
    void testRunWelcomePageLoginValid() {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("1", "shawn", "1234"));
        inOut.setInput(input);
        loginController.runWelcomePage();
        ArrayList<String> actualOutputs = inOut.getOutputs();
        String expectedOutput = "Login successful.";

        assertTrue(actualOutputs.contains(expectedOutput));
    }

    @Test
    void testRunWelcomePageSignUpInvalid() {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("0", "shawn", "1234", "username2", "1234"));
        inOut.setInput(input);
        loginController.runWelcomePage();
        ArrayList<String> actualOutputs = inOut.getOutputs();
        String expectedOutput = "Your username or password was invalid.";

        //Clear user used
        this.deleteUser("username2");

        assertTrue(actualOutputs.contains(expectedOutput));
    }

    @Test
    void testRunWelcomePageLoginInvalid() {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("1", "thisuserdoesntexist", "1234", "shawn", "1234"));
        inOut.setInput(input);
        loginController.runWelcomePage();
        ArrayList<String> actualOutputs = inOut.getOutputs();
        String expectedOutput = "Your username or password was incorrect.";

        assertTrue(actualOutputs.contains(expectedOutput));
    }

    private void deleteUser(String username) {
        User[] allUsers = this.mySQLController.getAllUsers();
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                this.mySQLController.deleteUser(user);
            }
        }
    }
}

