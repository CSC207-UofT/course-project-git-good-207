package controllers;

import entities.*;
import use_cases.FeedManager;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class FeedPresenter {
    private FeedManager feedManager;
    private InOut inOut;

    public FeedPresenter(InOut inOut) {
        this.inOut = inOut;
    }

    public FeedPresenter(InOut inOut, User currentUser, Feed currentUsersFeed) {
        this.inOut = inOut;
        this.feedManager = new FeedManager(currentUser, currentUsersFeed);
    }

    public void run(ShellAction action) {
        // TODO: get user inputs/display info based on the ShellAction
        if (action == ShellAction.BROWSEFEED) {
            // Asks the user for a filter (can be none)
            String filterInput = this.getFilterInput();

            if (!filterInput.equals("none")) {
                // Calls FeedManager to update the feed to show only the filtered posts
                this.setFeedFilter(filterInput);
            }

            // Gets current user's feed
            Feed currentFeed = this.feedManager.getCurrentUsersFeed();
            // Convert the feed into strings
            ArrayList<ArrayList<String>> feedInStrings = this.convertFeedToString(currentFeed);
            // Send the strings to InOut to be shown
            this.setOutputToInOut(feedInStrings);
        }
    }

    public void setCurrentUser() {}

    public void setCurrentUsersFeed() {}

    public void setFeedFilter(String filter) {
        this.feedManager.setFeedFilter(filter);
    }

    public void refreshFeed() {
        this.feedManager.refreshFeed();
    }

    public String getFilterInput() {
        // Will implement restrictions later to the input that the user is allowed to type.
        try {
            String filterInput = this.inOut.getInput(
                    "What type of cuisine would you like to see in your feed?" +
                            "Type 'None' if you do not wish to have a filter.");
            return filterInput;
        } catch(IOException e) {
            return "That cuisine type does not exist here.";
        }
    }

    public void setOutputToInOut(ArrayList<ArrayList<String>> feedInStrings) {
        for (ArrayList<String> postInStrings : feedInStrings) {
            for (String postDetails : postInStrings) {
                this.inOut.setOutput(postDetails);
            }
            this.inOut.setOutput(System.getProperty("line.separator"));
        }
    }

    public ArrayList<ArrayList<String>> convertFeedToString(Feed feed) {
        ArrayList<ArrayList<String>> feedList = new ArrayList<ArrayList<String>>();

        for (Post post: feed.getDisplayedPosts()) {
            ArrayList<String> postList = new ArrayList<String>();

            //Getting the details of a single post
            String postOwner = post.getOwner().getUsername() + "'s post";
            //No method in Post class to get recipe yet.
            String recipe = ;
            String cuisineType = "Cuisine Type: " + post.getCategory();
            String postedTime = "Posted Time: " + post.getPostedTime();
            String numOfLikes = "Number of likes: " + post.getNumLikes();
            String comments = "Comments: " + post.getComments();

            //Add each post detail into postList to make a list that represents a post
            postList.add(postOwner);
            postList.add(recipe);
            postList.add(cuisineType);
            postList.add(postedTime);
            postList.add(numOfLikes);
            postList.add(comments);

            feedList.add(postList);
        }

        return feedList;
    }
}
