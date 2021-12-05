package controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import use_cases.LoginManager;
import use_cases.UserManager;

import java.util.ArrayList;
import java.util.Arrays;

class UserProfileControllerTest {
    private static final MySQLController mySQLController = new MySQLController();
    private static final LoginManager loginManager = new LoginManager(mySQLController);
    private static final UserManager userManager = new UserManager(mySQLController);
    private static final DummyInOut inOut = new DummyInOut();
    private static final UserProfileController userProfileController = new UserProfileController(inOut, mySQLController, loginManager);
    final ArrayList<String> inputs = new ArrayList<>();

    @BeforeEach
    void setup() {
        loginManager.login("tester", "1234");
    }

    @AfterEach
    void clearInputs(){
        inputs.clear();
    }

    @Test
    void testRunFollowUserInvalid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("tester","0"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.BROWSEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String output = String.join("", outputs);

        assertTrue(output.endsWith("Either target user already followed, or target user is yourself!"));
    }

    @Test
    void testRunFollowUserValid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("shawn","0"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.BROWSEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String output = String.join("", outputs);

        assertTrue(output.endsWith("Successfully followed the target user!"));
    }

    @Test
    void testRunUnfollowUserInvalid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("glen","0"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.BROWSEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String output = String.join("", outputs);

        assertTrue(output.endsWith("User is not followed to begin with!"));
    }

    @Test
    void testRunUnfollowUserValid() {
        userManager.followUser(loginManager.getCurrUser(), userManager.findUser("shawn"));
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("shawn","0"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.BROWSEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String output = String.join("", outputs);

        assertTrue(output.endsWith("Successfully unfollowed the target user!"));
    }

    @Test
    void testRunBrowseProfileInvalid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("troy"));
        inOut.setInput(inputs);
        userProfileController.runBrowseProfile();
        String output = String.join("", inOut.getOutputs());

        assertTrue(output.endsWith("Error! Either user is not in the database or user is yourself! " +
                "Returning to main page"));
    }


    @Test
    void testRunCustomizeUsernameInvalid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("0", "tester"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.CUSTOMIZEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String output = String.join("", outputs);

        assertTrue(output.endsWith("Username entered is the same as the old one. Returning to main page."));
    }

    @Test
    void testRunCustomizeUsernameValid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("0", "tester!"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.CUSTOMIZEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String output = String.join("", outputs);

        assertTrue(output.endsWith("Successfully changed username to: " + "tester!" + "\n" + "Returning to main page."));
    }

    @Test
    void testRunCustomizeBioInvalid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("2", "hi im tester"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.CUSTOMIZEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String output = String.join("", outputs);

        assertTrue(output.endsWith("Bio given is the same as the old one. Returning to main page"));
    }

    @Test
    void testRunCustomizeBioValid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("2", "Hi!"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.CUSTOMIZEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String output = String.join("", outputs);

        assertTrue(output.endsWith("Successfully changed bio to: " + "Hi!" + "\n" + "Returning to main page."));
    }

    @Test
    void testRunCustomizePasswordInvalid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("1", "1234"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.CUSTOMIZEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String output = String.join("", outputs);

        assertTrue(output.endsWith("Password entered is the same as the old one. Returning to main page"));
    }

    @Test
    void testRunCustomizePasswordValid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("1", "12345"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.CUSTOMIZEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String output = String.join("", outputs);

        assertTrue(output.endsWith("Successfully changed password to: " + "12345" + "\n" + "Returning to main page."));
    }
}