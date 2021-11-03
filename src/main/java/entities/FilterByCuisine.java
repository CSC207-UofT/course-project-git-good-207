package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FilterByCuisine extends Filter {
    protected int cuisineNumInput;
    protected ArrayList<String> cuisineList = new ArrayList<>(Arrays.asList("chinese", "american",
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

    @Override
    ArrayList<Post> filterFeed() {
        ArrayList<Post> allPosts = this.currentUserFeed.getPosts();
        ArrayList<Post> cuisinePosts = new ArrayList<>();
        for (Post p : allPosts) {
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