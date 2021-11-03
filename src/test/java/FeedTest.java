import entities.Feed;
import entities.Post;
import entities.Recipe;
import entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class FeedTest {
    private static Feed feed;

    @BeforeAll
    static void setupFeedTest() {
        feed = new Feed(new ArrayList<>(Arrays.asList(
                new Post(UUID.randomUUID().toString(),
                    LocalDateTime.now(),
                    new Recipe("Stir Fry", new ArrayList<>(), new ArrayList<>()),
                    "Chinese"),
                new Post(UUID.randomUUID().toString(),
                    LocalDateTime.now(),
                    new Recipe("Stir Fry", new ArrayList<>(), new ArrayList<>()),
                    "Chinese")
        )));
    }

    @Test
    void testGetPosts() {assert !feed.getPosts().isEmpty();}

    @Test
    void testGetDisplayedPosts() { assert !feed.getDisplayedPosts().isEmpty();}
}
