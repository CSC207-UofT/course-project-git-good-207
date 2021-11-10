package use_cases;

import entities.*;

import java.util.ArrayList;

public class FeedManager {
    private final User currentUser;
    private Feed currentUsersFeed;

    /**
     * Construct a FeedManager object.
     *
     * @param currentUser The user that is currently running the program.
     * @param currentUsersFeed The feed that belongs to the current user running the program.
     */
    public FeedManager(User currentUser, Feed currentUsersFeed) {
        this.currentUser = currentUser;
        this.currentUsersFeed = currentUsersFeed;
    }

    /**
     * Apply the chosen filter on the current user's Feed.
     * @param filterNumInput an ArrayList of ints representing the choice of filter and the type of
     *                       cuisine (if filtering by cuisine).
     */
    public void setFeedFilter(ArrayList<Integer> filterNumInput) {
        Filter filterObject;
        if (filterNumInput.get(0) == 0) {
            filterObject = new Filter(this.getCurrentUsersFeed());
        } else if (filterNumInput.get(0) == 1) {
            filterObject = new FilterByCuisine(this.getCurrentUsersFeed(), filterNumInput.get(1));
        } else if (filterNumInput.get(0) == 2) {
            filterObject = new FilterByLikes(this.getCurrentUsersFeed());
        } else if (filterNumInput.get(0) == 3) {
            filterObject = new FilterByFollowing(this.getCurrentUsersFeed(), this.getCurrentUser());
        } else {
            filterObject = new FilterByRecommended(this.getCurrentUsersFeed(), this.getCurrentUser());
        }

        Feed filteredFeed = filterObject.runFilter();
        this.setCurrentUsersFeed(filteredFeed);
    }

    /**
     * Get the current User.
     * @return the current User.
     */
    public User getCurrentUser() { return currentUser; }

    /**
     * Get the current User's Feed.
     * @return the current User's Feed.
     */
    public Feed getCurrentUsersFeed() {
        return currentUsersFeed;
    }

    /**
     * Set the current User's Feed with a new Feed.
     * @param newFeed The new Feed to assign to the current User's Feed.
     */
    public void setCurrentUsersFeed(Feed newFeed) {
        this.currentUsersFeed = newFeed;
    }
}
