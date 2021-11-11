package use_cases;

import entities.Feed;
import entities.Post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FilterByCuisine extends Filter {
    protected final int cuisineNumInput;
    protected final ArrayList<String> cuisineList = new ArrayList<>(Arrays.asList("chinese", "american",
            "japanese", "italian", "french", "mexican"));

    /**
     * Construct a FilterByCuisine object with the user's feed and the user's input choice of cuisine.
     *
     * @param currentUserFeed The current user's feed
     * @param cuisineNumInput The current user's choice of cuisine represented by a number
     */
    public FilterByCuisine(Feed currentUserFeed, int cuisineNumInput) {
        super(currentUserFeed);
        this.cuisineNumInput = cuisineNumInput;
    }

    /**
     * Get the list of Posts filtered by a cuisine type from the current User's Feed's Posts.
     * @return an ArrayList of Posts filtered by a cuisine type from the current User's Feed's Posts.
     */
    @Override
    ArrayList<Post> filterFeed() {
        ArrayList<Post> allPosts = this.currentUserFeed.getPosts();
        ArrayList<Post> cuisinePosts = new ArrayList<>();
        for (Post p : allPosts) {
            // Get the posts with the cuisine types that are not Chinese, American, Japanese, Italian, French, Mexican
            if (this.cuisineNumInput == 6) {
                if (!this.cuisineList.contains(p.getCategory().toLowerCase())) {
                    cuisinePosts.add(p);
                }
            } else {
                if (p.getCategory().toLowerCase().equals(this.cuisineList.get(this.cuisineNumInput))) {
                    cuisinePosts.add(p);
                }
            }
        }
        Collections.shuffle(cuisinePosts);
        return cuisinePosts;
    }
}