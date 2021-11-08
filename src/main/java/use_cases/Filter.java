package use_cases;

import entities.Feed;
import entities.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Filter {
    protected Feed currentUserFeed;

    /**
     * Construct a Filter object and the user's feed.
     *
     * @param currentUserFeed The current user's feed
     */
    public Filter(Feed currentUserFeed) {
        this.currentUserFeed = currentUserFeed;
    }

    /**
     * Run the filter algorithm process.
     */
    public Feed runFilter() {
        ArrayList<Post> tempDisplayedPosts = this.filterFeed();
        ArrayList<Post> maxDisplayedPosts = this.limitNumPosts(tempDisplayedPosts);
        ArrayList<Post> sortedDisplayedPosts = this.sortByPostedTime(maxDisplayedPosts);

        this.currentUserFeed.setDisplayedPosts(sortedDisplayedPosts);
        return this.currentUserFeed;
    }

    /**
     * Get the list of Posts filtered by default from the current User's Feed's Posts.
     * @return an ArrayList of Posts filtered by default from the current User's Feed's Posts.
     */
    ArrayList<Post> filterFeed() {
        // For now, the default filter will be to just pick random posts.
        ArrayList<Post> allPosts = this.currentUserFeed.getPosts();
        Collections.shuffle(allPosts);
        return allPosts;
    }

    /**
     * Sort the list of filtered Posts from the most recent to the least.
     * @param maxDisplayedPosts An ArrayList of ten Posts maximum.
     * @return an ArrayList of Posts sorted from the most recent to the least.
     */
    private ArrayList<Post> sortByPostedTime(ArrayList<Post> maxDisplayedPosts) {
        // Sort from most recent to oldest
        Comparator<Post> byPostedTime = (p1, p2) -> {
            if (p1.getCreatedTime().isBefore(p2.getCreatedTime())) {
                return 1;
            } else if (p1.getCreatedTime().isEqual(p2.getCreatedTime())) {
                return 0;
            } else {
                return -1;
            }
        };
        maxDisplayedPosts.sort(byPostedTime);
        return maxDisplayedPosts;
    }

    /**
     * Get the list of ten Posts maximum.
     * @param tempDisplayedPosts An ArrayList of Posts.
     * @return an ArrayList of ten Posts maximum.
     */
    private ArrayList<Post> limitNumPosts(ArrayList<Post> tempDisplayedPosts) {
        ArrayList<Post> maxDisplayedPosts = new ArrayList<>();
        if (!this.checkNumPosts(tempDisplayedPosts)) {
            for (int i=0; i < 10; i++) {
                maxDisplayedPosts.add(tempDisplayedPosts.get(i));
            }
            return maxDisplayedPosts;
        }
        return tempDisplayedPosts;
    }

    /**
     * Check if the number of Posts in the parameter list has less than or equal to ten.
     * @param tempDisplayedPosts An ArrayList of Posts.
     * @return a boolean whether tempDisplayedPosts has less than or equal to ten posts.
     */
    private boolean checkNumPosts(ArrayList<Post> tempDisplayedPosts) {
        return tempDisplayedPosts.size() <= 10;
    }
}
