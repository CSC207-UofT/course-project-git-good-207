package controllers;

import entities.CountableIngredient;
import entities.Ingredient;
import entities.Post;
import entities.Recipe;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import use_cases.LoginManager;
import use_cases.UserManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

class UserProfileControllerTest {
    private static final MySQLController mySQLController = new MySQLController();
    private static final LoginManager loginManager = new LoginManager(mySQLController);
    private static final UserManager userManager = new UserManager(mySQLController);
    private static final DummyInOut inOut = new DummyInOut();
    private static final UserProfileController userProfileController = new UserProfileController(inOut, mySQLController, loginManager);
    final static LocalDateTime dateTime = LocalDateTime.of(2021, 12, 3, 4, 20, 1);
    final static String recipeId = UUID.randomUUID().toString();
    final ArrayList<String> inputs = new ArrayList<>();
    static Post post;

    @BeforeAll
    static void setup() {
        loginManager.signUp("tester", "1234");
        loginManager.login("tester", "1234");
    }

    @AfterEach
    void clearInputs(){
        inputs.clear();
    }

    @AfterAll
    static void clearUser(){
        mySQLController.deleteUser(loginManager.getCurrUser());
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
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("","1"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.BROWSEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String output = String.join("", outputs);

        assertTrue(output.endsWith("Either user is not in the database or user is yourself! Returning to main page"));
    }

    @Test
    void testRunUnfollowUserValid() {
        userManager.followUser(loginManager.getCurrUser(), userManager.findUser("shawn"));
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("4", "y", "shawn"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.CUSTOMIZEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String output = String.join("", outputs);

        assertTrue(output.endsWith("Successfully unfollowed the target user!"));
    }

    @Test
    void testRunBrowseProfileInvalid() {
        ArrayList<String> inputs = new ArrayList<>(List.of("troy"));
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

        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("2", loginManager.getCurrUser().getBio()));
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
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("1", loginManager.getCurrUser().getPassword()));
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

    @Test
    void testViewOwnPostsWithZeroPosts() {
        ArrayList<String> inputs = new ArrayList<>(List.of("3"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.CUSTOMIZEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String output = String.join("", outputs);

        assertTrue(output.endsWith("You have no posts! Returning to main menu."));
    }

    @Test
    void testViewOtherUserPostsWithZeroPosts() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("tester", "1"));
        inOut.setInput(inputs);
        userProfileController.run(ShellAction.BROWSEPROFILE);
        ArrayList<String> outputs = inOut.getOutputs();
        String output = String.join("", outputs);

        assertTrue(output.endsWith("Target user has no posts! Returning to main menu"));
    }

    @Test
    void testBrowsePost() {
        ArrayList<Ingredient> ingredients = new ArrayList<>(List.of(new CountableIngredient("apples", 13)));
        ArrayList<String> steps = new ArrayList<>(Arrays.asList("Get apples", "Throw them"));
        Recipe recipe = new Recipe("Test", ingredients, steps, recipeId);
        post = new Post("2", dateTime, recipe, "test", "100");
        loginManager.getCurrUser().addPost(post);
    }
}