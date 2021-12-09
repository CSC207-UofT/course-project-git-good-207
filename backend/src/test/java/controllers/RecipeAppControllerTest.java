package controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Disabled //Intentionally ignored. See testing section of design document.
class RecipeAppControllerTest {
    private final DummyInOut inOut = new DummyInOut();
    private final RecipeAppController recipeAppController = new RecipeAppController(inOut);
    private final ArrayList<String> loginInput = new ArrayList<>(Arrays.asList("1", "shawn", "1234"));
    private final ArrayList<String> exitInput = new ArrayList<>(Arrays.asList("4", "2"));

    @Test
    void testRunBrowseFeed() {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.addAll(loginInput);
        inputs.addAll(List.of("0", "99"));
        inputs.addAll(exitInput);

        inOut.setInput(inputs);
        recipeAppController.run();
        ArrayList<String> outputs = inOut.getOutputs();
        String expected =  "Please select an action:\n" +
                "0 Sign up\n" +
                "1 Login\n" +
                "2 Exit\n";
        assertEquals(expected, outputs.get(1));
    }

    @Test
    void testRunBrowseProfile() {

        ArrayList<String> inputs = new ArrayList<>();
        inputs.addAll(loginInput);
        inputs.addAll(List.of("1", "shawn", "99"));
        inputs.addAll(exitInput);

        inOut.setInput(inputs);
        recipeAppController.run();
        ArrayList<String> outputs = inOut.getOutputs();
        String actual =  String.join("", outputs);
        assertTrue(actual.contains("Enter the username of the person you'd like to view:"));
    }

    @Test
    void testRunPost() {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.addAll(loginInput);
        inputs.addAll(List.of("2", "N/A", "1 apple", "test", "step", "back"));
        inputs.addAll(exitInput);

        inOut.setInput(inputs);
        recipeAppController.run();
        ArrayList<String> outputs = inOut.getOutputs();
        String actual = String.join("", outputs);
        assertTrue(actual.contains("Post deleted. Returning to main menu."));
    }

    @Test
    void testRunCustomizeProfile() {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.addAll(loginInput);
        inputs.addAll(List.of("3", "99"));
        inputs.addAll(exitInput);

        inOut.setInput(inputs);
        recipeAppController.run();
        ArrayList<String> outputs = inOut.getOutputs();
        String actual = String.join("", outputs);
        assertTrue(actual.contains("Select which option you'd like to customize:"));
    }

    @Test
    void testRunInvalidInput() {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.addAll(loginInput);
        inputs.addAll(List.of("hey"));
        inputs.addAll(exitInput);

        inOut.setInput(inputs);
        recipeAppController.run();
        ArrayList<String> outputs = inOut.getOutputs();
        String actual = String.join("", outputs);
        assertTrue(actual.contains("That is not a valid action."));
    }

    @AfterEach
    void clearOutput(){
        inOut.clearOutputs();
    }

}