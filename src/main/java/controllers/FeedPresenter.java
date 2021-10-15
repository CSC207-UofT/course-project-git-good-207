package controllers;

import entities.Feed;
import entities.Post;
import entities.User;
import use_cases.FeedManager;

import java.util.ArrayList;

public class FeedPresenter {
    private FeedManager feedManager;

    public FeedPresenter(User currentUser, Feed currentUsersFeed) {
        this.feedManager = new FeedManager(currentUser, currentUsersFeed);
    }

    public void setFeedFilter(String filter) {
        this.feedManager.setFeedFilter(filter);
    }

    public void refreshFeed() {
        this.feedManager.refreshFeed();
    }

    public ArrayList<Post> getAllPosts(){return this.feedManager.getPostsFromFeed();}
}
