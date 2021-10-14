package use_cases;

import entities.Feed;
import entities.Post;
import entities.User;

import java.util.ArrayList;

public class FeedManager {
    private User currentUser;
    private Feed currentUsersFeed;

    public FeedManager(User currentUser, Feed currentUsersFeed) {
        this.currentUser = currentUser;
        this.currentUsersFeed = currentUsersFeed;
    }

    public void setFeedFilter(String filter) {
        // TODO: set the filter on the currentUsersFeed
    }

    public void refreshFeed() {
        // TODO: refresh the feed based users followed in currentUser and possibly likes in currentUser
        ArrayList<Post> newDisplayedPosts = new ArrayList<>();
        // TODO: iterate through Feed.posts and add to displayed
        //  posts if matches followed user ID or filter settings
        this.currentUsersFeed.setDisplayedPosts(newDisplayedPosts);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User newUser) {
        this.currentUser = newUser;
    }

    public Feed getCurrentUsersFeed() {
        return currentUsersFeed;
    }

    public void setCurrentUsersFeed(Feed newFeed) {
        this.currentUsersFeed = newFeed;
    }
}
