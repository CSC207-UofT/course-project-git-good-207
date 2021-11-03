package entities;

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

    public Feed runFilter() {
        ArrayList<Post> tempDisplayedPosts = this.filterFeed();
        ArrayList<Post> maxDisplayedPosts = this.limitNumPosts(tempDisplayedPosts);
        ArrayList<Post> sortedDisplayedPosts = this.sortByPostedTime(maxDisplayedPosts);

        this.currentUserFeed.setDisplayedPosts(sortedDisplayedPosts);
        return this.currentUserFeed;
    }

    ArrayList<Post> filterFeed() {
        // For now, the default filter will be to just pick random posts.
        ArrayList<Post> allPosts = this.currentUserFeed.getPosts();
        Collections.shuffle(allPosts);
        return allPosts;
    }

    private ArrayList<Post> sortByPostedTime(ArrayList<Post> tempDisplayedPosts) {
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
        tempDisplayedPosts.sort(byPostedTime);
        return tempDisplayedPosts;
    }

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

    private boolean checkNumPosts(ArrayList<Post> tempDisplayedPosts) {
        return tempDisplayedPosts.size() <= 10;
    }
}
