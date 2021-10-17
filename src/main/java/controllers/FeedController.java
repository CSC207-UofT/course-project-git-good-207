package controllers;

import entities.Feed;
import entities.InOut;
import entities.ShellAction;
import entities.User;
import use_cases.FeedManager;

public class FeedController {
    private FeedManager feedManager;
    private InOut inOut;

    public FeedController(InOut inOut) {
        this.inOut = inOut;
    }

    public FeedController(InOut inOut, User currentUser, Feed currentUsersFeed) {
        this.inOut = inOut;
        this.feedManager = new FeedManager(currentUser, currentUsersFeed);
    }

    public void run(ShellAction action) {
        // TODO: get user inputs/display info based on the ShellAction
    }

    public void setCurrentUser() {}

    public void setCurrentUsersFeed() {}

    public void setFeedFilter(String filter) {
        this.feedManager.setFeedFilter(filter);
    }

    public void refreshFeed() {
        this.feedManager.refreshFeed();
    }
}
