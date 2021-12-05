package entities;

import entities.Feed;
import entities.Post;
import entities.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class FeedTest {
    private Feed feed;

    @BeforeEach
    void setupFeedTest() {
        this.feed = new Feed(new ArrayList<>(Arrays.asList(
                new Post("eric",
                    LocalDateTime.now(),
                    new Recipe("Stir Fry", new ArrayList<>(), new ArrayList<>(), "a"),
                    "Chinese", "a1"),
                new Post("justin",
                    LocalDateTime.now(),
                    new Recipe("Stir Fry", new ArrayList<>(), new ArrayList<>(), "b"),
                    "Chinese", "b1")
        )));
    }

    @Test
    void testGetPosts() {
        assert !feed.getPosts().isEmpty();
    }

    @Test
    void testGetDisplayedPosts() {
        assert feed.getDisplayedPosts().size() == 2;
    }

    @Test
    void testSetDisplayedPosts() {
        ArrayList<Post> emptyPostList = new ArrayList<>();
        feed.setDisplayedPosts(emptyPostList);
        assert feed.getDisplayedPosts().isEmpty();
    }
}
