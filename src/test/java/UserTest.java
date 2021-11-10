import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class UserTest {
    private User user;

    @BeforeEach
    void setupFeedTest() {
        this.user = new User("eren_yeager", "#1titan_slayer", "" , UUID.randomUUID().toString());
    }

    @Test
    void testGetUsername() {
        assert this.user.getUsername().equals("eren_yeager");
    }

    @Test
    void testGetPassword() {
        assert this.user.getPassword().equals("#1titan_slayer");
    }

    @Test
    void testGetBio() {
        this.user.setBio("What did the ocean say to the beach? Nothing, it just waved");
        assert this.user.getBio().equals("What did the ocean say to the beach? Nothing, it just waved");
    }

    @Test
    void testGetLikeHistory() {
        HashMap<String, Integer> like_history = new HashMap<>();
        like_history.put("Russian Food", 4);
        this.user.setLikeHistory(like_history);
        assert this.user.getLikeHistory().equals(like_history);
    }

    @Test
    void testGetFollowers() {
        User john_jones = new User("john_jones", "123", "" ,UUID.randomUUID().toString());
        ArrayList<User> followers = new ArrayList<>();
        followers.add(john_jones);
        this.user.setFollowers(followers);
        assert this.user.getFollowers().equals(followers);
    }

    @Test
    void testGetFollowing() {
        User john_jones = new User("john_jones", "123", "" ,UUID.randomUUID().toString());
        ArrayList<User> following = new ArrayList<>();
        following.add(john_jones);
        this.user.setFollowing(following);
        assert this.user.getFollowing().equals(following);
    }

    @Test
    void testGetPosts() {
        assert this.user.getPosts().isEmpty();
    }

}
