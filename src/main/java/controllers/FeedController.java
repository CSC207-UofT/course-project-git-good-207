package controllers;

import entities.*;
import use_cases.DatabaseManager;
import use_cases.FeedManager;
import use_cases.LoginManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FeedController {
    private FeedManager feedManager;
    private LoginManager loginManager;
    private InOut inOut;
    private DatabaseManager databaseManager;
    private String postsString;
    private HashMap<Integer, Post> postsActionMap = new HashMap<>();
    private String postActionPrompt = "Please select an action for this post: \n"
            + "0 Like the Post";

    public FeedController(InOut inOut, DatabaseManager dbManager) {
        this.inOut = inOut;
        this.loginManager = new LoginManager(dbManager);
        this.databaseManager = dbManager;
        User currUser = this.loginManager.getCurrUser();
        Post[] posts = this.databaseManager.getAllPosts();
        Feed currUserFeed = new Feed(new ArrayList<>(Arrays.asList(posts)));
        this.feedManager = new FeedManager(currUser, currUserFeed);
    }

    public void run(ShellAction action) {
        // TODO: get user inputs/display info based on the ShellAction
        if (action == ShellAction.BROWSEFEED) {
            this.runBrowseFeed();
        }
    }

    private void runBrowseFeed() {
        Feed feed = this.feedManager.getCurrentUsersFeed();
        ArrayList<Post> allPosts = feed.getPosts();
        this.postsString = this.generateFeedActions(allPosts);
        try {
            String postSelection = this.inOut.getInput(this.postsString);
            Integer postNumber = Integer.parseInt(postSelection);
            Post selectedPost = this.postsActionMap.get(postNumber);
            this.displayPostInfo(selectedPost);
            String postAction =  this.inOut.getInput(this.postActionPrompt);
            this.runPostAction(selectedPost, Integer.parseInt(postAction));
            this.displayPostInfo(selectedPost);
        } catch (IOException e) {
            this.inOut.setOutput("An error occurred: " + e);
        } catch (NumberFormatException nfe) {
            this.inOut.setOutput("You entered an invalid action input.");
        }

    }

    private void runPostAction(Post selectedPost, Integer postAction) {
        switch (postAction) {
            case 0:
                selectedPost.addLike(this.loginManager.getCurrUser());
                break;
            default:
                this.inOut.setOutput("You entered an invalid action.");
        }
    }

    private void displayPostInfo(Post selectedPost) {
        this.inOut.setOutput("Post Title: " + selectedPost.getRecipe().getTitle());
        this.inOut.setOutput("Category: " + selectedPost.getCategory());
        this.inOut.setOutput("Number of Likes: " + selectedPost.getNumLikes());
    }

    private String generateFeedActions(ArrayList<Post> posts) {
        this.postsActionMap = new HashMap<>();
        String postsString = "Enter a number for a detailed view of a post: ";

        for (int i=0; i < posts.size(); i++) {
            this.postsActionMap.put(i, posts.get(i));
            postsString += "\n" + i + " " + posts.get(i).getRecipe().getTitle();
        }
        return postsString;
    }
}
