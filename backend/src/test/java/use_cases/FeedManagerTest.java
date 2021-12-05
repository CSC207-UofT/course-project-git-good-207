package use_cases;

import entities.Feed;
import entities.Post;
import entities.Recipe;
import entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class FeedManagerTest {
    private static FeedManager feedManager;
    private static User currentUser;
    private static User friend1;
    private static User friend2;

    @BeforeAll
    static void setupFeedManager() {
        setupUserFollowing();
        Feed feed = setupFeed();
        feedManager = new FeedManager(currentUser, feed);
    }

    @Test
    void testSetFeedFilterDefault() {
        ArrayList<Integer> filterNumInput = new ArrayList<>(Arrays.asList(0, 0));
        feedManager.setFeedFilter(filterNumInput);
        Feed actual = feedManager.getCurrentUsersFeed();
        assert actual.getDisplayedPosts().size() == 10;
    }

    @Test
    void testSetFeedFilterByCuisine() {
        ArrayList<Integer> filterNumInput = new ArrayList<>(Arrays.asList(1, 0));
        feedManager.setFeedFilter(filterNumInput);
        Feed actualFeed = feedManager.getCurrentUsersFeed();
        ArrayList<String> actual = new ArrayList<>();
        for (Post p : actualFeed.getDisplayedPosts()) {
            actual.add(p.getRecipe().getTitle());
        }
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("Szechuan Chicken", "Stir Fry Beef", "Mapo Tofu"));

        assert expected.containsAll(actual);
    }

    @Test
    void testSetFeedFilterByLikes() {
        ArrayList<Integer> filterNumInput = new ArrayList<>(Arrays.asList(2, 0));
        feedManager.setFeedFilter(filterNumInput);
        Feed actual = feedManager.getCurrentUsersFeed();
        ArrayList<String> actualPostTitles = new ArrayList<>();
        for (Post p : actual.getDisplayedPosts()) {
            actualPostTitles.add(p.getRecipe().getTitle());
        }
        ArrayList<String> expectedTitles = new ArrayList<>(Arrays.asList("Butter Chicken", "Beef Bourguignon"));
        assert actualPostTitles.containsAll(expectedTitles);
        assert actual.getDisplayedPosts().size() == 10;
    }

    @Test
    void testSetFeedFilterByFollowing() {
        ArrayList<Integer> filterNumInput = new ArrayList<>(Arrays.asList(3, 0));
        feedManager.setFeedFilter(filterNumInput);
        Feed actualFeed = feedManager.getCurrentUsersFeed();
        ArrayList<String> actual = new ArrayList<>();
        for (Post p : actualFeed.getDisplayedPosts()) {
            actual.add(p.getRecipe().getTitle());
        }
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("Szechuan Chicken", "Beef Burger",
                "Tonkotsu Ramen", "Pepperoni Pizza"));

        assert expected.containsAll(actual);
    }

    @Test
    void testSetFeedFilterByRecommended() {
        ArrayList<Integer> filterNumInput = new ArrayList<>(Arrays.asList(4, 0));
        feedManager.setFeedFilter(filterNumInput);
        Feed actualFeed = feedManager.getCurrentUsersFeed();
        ArrayList<String> actual = new ArrayList<>();
        for (Post p : actualFeed.getDisplayedPosts()) {
            actual.add(p.getRecipe().getTitle());
        }

        ArrayList<String> expected = new ArrayList<>();
        expected.add("Beef Taco");
        assert expected.containsAll(actual) && actual.containsAll(expected);
    }

    @Test
    void testGetCurrentUser() {
        User actual = feedManager.getCurrentUser();
        String expected = "justin";

        assert expected.equals(actual.getUsername());
    }

    @Test
    void testGetCurrentUsersFeed() {
        Feed actual = feedManager.getCurrentUsersFeed();

        assert !actual.getPosts().isEmpty();
    }

    @Test
    void testSetCurrentUsersFeed() {
        Feed emptyFeed = new Feed(new ArrayList<>());
        FeedManager newFeedManager = new FeedManager(friend1, emptyFeed);
        Feed updatedFeed = newFeedManager.getCurrentUsersFeed();

        assert updatedFeed.getPosts().isEmpty();
    }

    private static void setupUserFollowing() {
        currentUser = new User("justin", "1234", "", UUID.randomUUID().toString());
        friend1 = new User("glen", "1111", "", UUID.randomUUID().toString());
        friend2 = new User("eric", "2222", "", UUID.randomUUID().toString());
        currentUser.addFollowing(friend1);
        currentUser.addFollowing(friend2);
    }

    private static Feed setupFeed() {
        Post friend1Post1 = new Post(friend1.getId(), LocalDateTime.now(), new Recipe("Szechuan Chicken",
                new ArrayList<>(), new ArrayList<>(), "r1"), "Chinese", "f1p1");
        Post friend1Post2 = new Post(friend1.getId(), LocalDateTime.now(), new Recipe("Beef Burger",
                new ArrayList<>(), new ArrayList<>(), "r2"), "American", "f1p2");
        Post friend2Post1 = new Post(friend2.getId(), LocalDateTime.now(), new Recipe("Tonkotsu Ramen",
                new ArrayList<>(), new ArrayList<>(), "r3"), "Japanese", "f2p1");
        Post friend2Post2 = new Post(friend2.getId(), LocalDateTime.now(), new Recipe("Pepperoni Pizza",
                new ArrayList<>(), new ArrayList<>(), "r4"), "Italian", "f2p2");
        Post randomPost1 = new Post(UUID.randomUUID().toString(), LocalDateTime.now(),
                new Recipe("Stir Fry Beef", new ArrayList<>(), new ArrayList<>(), "r5"), "Chinese", "rp1");
        Post randomPost2 = new Post(UUID.randomUUID().toString(), LocalDateTime.now(),
                new Recipe("Chicken Burger", new ArrayList<>(), new ArrayList<>(), "r6"), "American", "rp2");
        Post randomPost3 = new Post(UUID.randomUUID().toString(), LocalDateTime.now(),
                new Recipe("Butter Chicken", new ArrayList<>(), new ArrayList<>(), "r7"), "Indian", "rp3");
        Post randomPost4 = new Post(UUID.randomUUID().toString(), LocalDateTime.now(),
                new Recipe("Mapo Tofu", new ArrayList<>(), new ArrayList<>(), "r8"), "Chinese", "rp4");
        Post randomPost5 = new Post(UUID.randomUUID().toString(), LocalDateTime.now(),
                new Recipe("Chicken Lasagna", new ArrayList<>(), new ArrayList<>(), "r9"), "Italian", "rp5");
        Post randomPost6 = new Post(UUID.randomUUID().toString(), LocalDateTime.now(),
                new Recipe("Beef Bourguignon", new ArrayList<>(), new ArrayList<>(), "r10"), "French", "rp6");
        Post randomPost7 = new Post(UUID.randomUUID().toString(), LocalDateTime.now(),
                new Recipe("Beef Taco", new ArrayList<>(), new ArrayList<>(), "r11"), "Mexican", "rp7");
        Post randomPost8 = new Post(UUID.randomUUID().toString(), LocalDateTime.now(),
                new Recipe("Chicken Taco", new ArrayList<>(), new ArrayList<>(), "r12"), "Mexican", "rp8");

        randomPost3.addLike(currentUser);
        randomPost3.addLike(friend1);
        randomPost3.addLike(friend2);
        randomPost6.addLike(currentUser);
        randomPost6.addLike(friend2);
        randomPost8.addLike(currentUser);
        currentUser.setLike("Mexican");

        ArrayList<Post> allPosts = new ArrayList<>(Arrays.asList(friend1Post1, friend1Post2,
                friend2Post1, friend2Post2, randomPost1, randomPost2, randomPost3, randomPost4,
                randomPost5, randomPost6, randomPost7, randomPost8));

        return new Feed(allPosts);
    }
}