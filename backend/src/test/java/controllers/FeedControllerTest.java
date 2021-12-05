package controllers;

import entities.Feed;
import entities.Post;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import use_cases.FeedManager;
import use_cases.LoginManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedControllerTest {
    private static final MySQLController mySQLController = new MySQLController();
    private static final LoginManager loginManager = new LoginManager(mySQLController);
    private static final DummyInOut inOut = new DummyInOut();
    private static FeedController feedController;
    private static FeedManager feedManager;

    @BeforeAll
    static void setupFeedController() {
        loginManager.login("shawn", "1234");
        PostController postController = new PostController(inOut, mySQLController, loginManager);
        feedController = new FeedController(inOut, mySQLController, loginManager, postController);
        Post[] posts = mySQLController.getAllPosts();
        Feed currUserFeed = new Feed(new ArrayList<>(Arrays.asList(posts)));
        feedManager = new FeedManager(loginManager.getCurrUser(), currUserFeed);
    }

    @Test
    void testGenerateDisplayedPosts(){
        Feed feed = feedManager.getCurrentUsersFeed();
        String actual = feedController.generateDisplayedPosts(feed.getDisplayedPosts());
        String expected = "Enter a post number for a detailed view of " +
                "that post or enter 99 to choose a different feed filter: " + "\n" +
                0 + " ";

        assert actual.contains(expected);
    }

    @Test
    void testGenerateDisplayedPosts2(){
        ArrayList<Post> emptyPostList = new ArrayList<>();
        String actual = feedController.generateDisplayedPosts(emptyPostList);
        String expected = "There aren't any posts suitable for the chosen filter. Pick a different filter.";

        assert actual.equals(expected);
    }

    @Test
    void testGetSelectedPostInput(){
        ArrayList<String> input = new ArrayList<>(List.of("0"));
        Feed feed = feedManager.getCurrentUsersFeed();
        String postsString = feedController.generateDisplayedPosts(feed.getDisplayedPosts());
        inOut.setInput(input);
        int actual = feedController.getSelectedPostInput(postsString, feed.getDisplayedPosts().size());
        int expected = 0;

        assert actual == expected;
    }

    @Test
    void testGetSelectedPostInputInvalid(){
        ArrayList<String> input = new ArrayList<>(List.of("asa!@!da", "0"));
        inOut.setInput(input);

        Feed feed = feedManager.getCurrentUsersFeed();
        String postsString = feedController.generateDisplayedPosts(feed.getDisplayedPosts());
        feedController.getSelectedPostInput(postsString, feed.getDisplayedPosts().size());

        ArrayList<String> actualMessage = inOut.getOutputs();
        String expectedMessage = "You entered an invalid action input.";

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetFilterInput() {
        ArrayList<String> input = new ArrayList<>(List.of("0"));
        inOut.setInput(input);
        ArrayList<Integer> actual = feedController.getFilterInput();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(0);

        assert actual.get(0).equals(expected.get(0)) && actual.get(1).equals(expected.get(1));
    }

    @Test
    void testGetFilterInputInvalid(){
        ArrayList<String> input = new ArrayList<>(List.of("asa!@!da", "0"));
        inOut.setInput(input);

        feedController.getFilterInput();

        ArrayList<String> actualMessage = inOut.getOutputs();
        String expectedMessage = "You entered an invalid action input.";

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetCuisineInput() {
        ArrayList<String> input = new ArrayList<>(List.of("0"));
        inOut.setInput(input);
        int actual = feedController.getCuisineInput();
        int expected = 0;

        assert actual == expected;
    }

    @Test
    void testGetCuisineInputInvalid(){
        ArrayList<String> input = new ArrayList<>(List.of("asa!@!da", "0"));
        inOut.setInput(input);

        feedController.getCuisineInput();

        ArrayList<String> actualMessage = inOut.getOutputs();
        String expectedMessage = "You entered an invalid action input.";

        assertTrue(actualMessage.contains(expectedMessage));
    }
}