package use_cases;

import entities.*;

import java.util.ArrayList;

public class FeedManager {
    private final User currentUser;
    private Feed currentUsersFeed;

    /**
     * Construct a Feed object.
     *
     * @param currentUser The user that is currently running the program
     * @param currentUsersFeed The feed that belongs to the current user running the program.
     */
    public FeedManager(User currentUser, Feed currentUsersFeed) {
        this.currentUser = currentUser;
        this.currentUsersFeed = currentUsersFeed;
    }

    public void setFeedFilter(ArrayList<Integer> filterNumInput) {
        Filter filterObject;
        if (filterNumInput.get(0) == 0) {
            filterObject = new Filter(this.getCurrentUsersFeed());
        } else if (filterNumInput.get(0) == 1) {
            filterObject = new FilterByCuisine(this.getCurrentUsersFeed(), filterNumInput.get(1));
        } else if (filterNumInput.get(0) == 2) {
            filterObject = new FilterByLikes(this.getCurrentUsersFeed());
        } else {
            filterObject = new FilterByFollowing(this.getCurrentUsersFeed(), this.getCurrentUser());
        }

        Feed filteredFeed = filterObject.runFilter();
        this.setCurrentUsersFeed(filteredFeed);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Feed getCurrentUsersFeed() {
        return currentUsersFeed;
    }

    public void setCurrentUsersFeed(Feed newFeed) {
        this.currentUsersFeed = newFeed;
    }
}
