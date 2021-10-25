import entities.Feed;
import entities.Post;
import entities.Recipe;
import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class FeedTest {
    private Feed feed;

    @BeforeEach
    void setupFeedTest() {
        this.feed = new Feed(new ArrayList<>(Arrays.asList(
                new Post(UUID.randomUUID().toString(),
                    LocalDateTime.now(),
                    new Recipe("Stir Fry"),
                    "Chinese"),
                new Post(UUID.randomUUID().toString(),
                    LocalDateTime.now(),
                    new Recipe("Stir Fry"),
                    "Chinese")
        )));
    }

    @Test
    void testGetPosts() {
        assert !this.feed.getPosts().isEmpty();
    }
}
