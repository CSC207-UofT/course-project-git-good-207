import entities.Feed;
import entities.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class FeedTest {
    private Feed feed;

    @BeforeEach
    void setupFeedTest() {
        this.feed = new Feed(new ArrayList<>(Arrays.asList(new Post(), new Post())));
    }

    @Test
    void testGetPosts() {
        assert !this.feed.getPosts().isEmpty();
    }
}
