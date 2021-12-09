package controllers;

import entities.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import use_cases.LoginManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

class PostControllerTest {
    final static MySQLController mySQLController = new MySQLController();
    final static LoginManager loginManager = new LoginManager(mySQLController);
    final static LocalDateTime dateTime = LocalDateTime.of(2021, 12, 3, 4, 20, 1);
    final static String recipeId = UUID.randomUUID().toString();
    static Post post;
    final DummyInOut inOut = new DummyInOut();
    final PostController postController = new PostController(inOut, mySQLController, loginManager);

    @BeforeAll
    static void setup() {
        loginManager.login("shawn", "1234");
        ArrayList<Ingredient> ingredients = new ArrayList<>(List.of(new CountableIngredient("apples", 13)));
        ArrayList<String> steps = new ArrayList<>(Arrays.asList("Get apples", "Throw them"));
        Recipe recipe = new Recipe("Test", ingredients, steps, recipeId);
        post = new Post("2", dateTime, recipe, "test", "100");
        mySQLController.addNewPost(post);
    }

    @AfterEach
    void clearOutput() {
        inOut.clearOutputs();
    }

    @Test
    void testRunDiscardDraft() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("1 cup flour", "1 apple", "Test", "Add stuff", "back"));
        inOut.setInput(inputs);
        postController.run(ShellAction.POST);
        ArrayList<String> outputs = inOut.getOutputs();
        String actual = outputs.get(0);

        assertEquals("Post deleted. Returning to main menu.", actual);

    }

    @Test
    void testRunComplete() {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("N/A", "1 apple", "Test", "Add stuff", "French"));
        inOut.setInput(inputs);
        postController.run(ShellAction.POST);
        ArrayList<String> outputs = inOut.getOutputs();
        String actual = outputs.get(0);
        inOut.clearOutputs();
        assertEquals("Post successfully created!", actual);

    }


    @Test
    void testBrowsePostReturnToMenu() {
        ArrayList<String> inputs = new ArrayList<>(List.of("99"));
        inOut.setInput(inputs);
        postController.browsePost(post);

        String output = String.join("", inOut.getOutputs());

        assertTrue(output.endsWith("Returning to main menu."));
    }

    @Test
    void testBrowsePostInvalid() {
        ArrayList<String> inputs = new ArrayList<>(List.of("hi", "99"));
        inOut.setInput(inputs);
        postController.browsePost(post);

        String output = String.join("", inOut.getOutputs());

        assertTrue(output.contains("You entered an invalid action input."));
    }

    @Test
    void testDisplayPost() {
        postController.displayPost("100");
        ArrayList<String> outputs = inOut.getOutputs();
        String actual = String.join("", outputs);

        String expected = "shawn\n" +
                "2021-12-03T04:20:01\n" +
                "\n" +
                "Test\n" +
                "\n" +
                "Ingredients:\n" +
                "13.0 apples\n" +
                "\n" +
                "Steps:\n" +
                "1. Get apples\n" +
                "2. Throw them\n" +
                "\n" +
                "Category: test\n" +
                "\n" +
                "Liked by (1):\n" +
                "shawn\n"+
                "Comments (0):\n";

        outputs.clear();
        assertEquals(expected, actual);
    }

    @Test
    void testBrowsePostLikePost() {
        ArrayList<String> inputs = new ArrayList<>(List.of("0", "99"));
        inOut.setInput(inputs);
        postController.browsePost(post);
        int currLikes = post.getNumLikes();
        assertEquals(0, currLikes);
    }

    @Test
    void testBrowsePostCommentPost() {
        ArrayList<String> inputs = new ArrayList<>(List.of("1", "Comment", "99"));
        inOut.setInput(inputs);

        postController.browsePost(post);
        ArrayList<Comment> comments = post.getComments();
        ArrayList<String> commentText = new ArrayList<>(List.of("Comment"));
        for(Comment comment: comments){
            commentText.add(comment.getCommentText());
        }
        String actual = String.join("", commentText);
        assertTrue(actual.contains("Comment"));
    }

    @AfterAll
    static void cleanUp() {
        mySQLController.deletePost("100");
    }
}