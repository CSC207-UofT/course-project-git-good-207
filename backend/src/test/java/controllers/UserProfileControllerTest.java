package controllers;

import entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import use_cases.LoginManager;
import use_cases.UserManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

class UserProfileControllerTest {
    static final MySQLController mySQLController = new MySQLController();
    static final LoginManager loginManager = new LoginManager(mySQLController);
    static final UserManager userManager = new UserManager(mySQLController);
    final DummyInOut inOut = new DummyInOut();
    final UserProfileController userProfileController = new UserProfileController(inOut, mySQLController, loginManager);
    final ArrayList<String> inputs = new ArrayList<>();

    @BeforeAll
    static void setup() {
        User tester = new User("tester", "1234", "hi im tester", UUID.randomUUID().toString());
        userManager.followUser(tester, userManager.findUser("shawn"));
        mySQLController.addNewUser(tester);
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
        String actual = outputs.get(0);

        assertEquals("Either target user already followed, or target user is yourself!", actual);
    }

    @Test
    void testRunFollowUserValid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("eric","0"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.BROWSEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String actual = outputs.get(0);

        assertEquals("Successfully followed the target user!", actual);
    }

    @Test
    void testRunUnfollowUserInvalid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("glen","0"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.BROWSEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String actual = outputs.get(0);

        assertEquals("Successfully unfollowed the target user!", actual);
    }

    @Test
    void testRunUnfollowUserValid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("shawn","0"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.BROWSEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String actual = outputs.get(0);

        assertEquals("User is not followed to begin with!", actual);
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
        String actual = outputs.get(0);

        assertEquals("Username entered is the same as the old one. Returning to main page.", actual);
    }

    @Test
    void testRunCustomizeUsernameValid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("0", "tester!"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.CUSTOMIZEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String actual = outputs.get(0);

        assertEquals("Successfully changed username to: " + "tester!" + "\n" + "Returning to main page.", actual);
    }

    @Test
    void testRunCustomizeBioInvalid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("2", "hi im tester"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.CUSTOMIZEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String actual = outputs.get(0);

        assertEquals("Bio given is the same as the old one. Returning to main page", actual);
    }

    @Test
    void testRunCustomizeBioValid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("2", "Hi!"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.CUSTOMIZEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String actual = outputs.get(0);

        assertEquals("Successfully changed bio to: " + "Hi!" + "\n" + "Returning to main page.", actual);
    }

    @Test
    void testRunCustomizePasswordInvalid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("1", "1234"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.CUSTOMIZEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String actual = outputs.get(0);

        assertEquals("Password entered is the same as the old one. Returning to main page", actual);
    }

    @Test
    void testRunCustomizePasswordValid() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("1", "12345"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.CUSTOMIZEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String actual = outputs.get(0);

        assertEquals("Successfully changed password to: " + "12345" + "\n" + "Returning to main page.", actual);
    }
}