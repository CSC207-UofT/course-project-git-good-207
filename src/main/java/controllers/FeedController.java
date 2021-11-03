package controllers;

import entities.*;
import use_cases.DatabaseManager;
import use_cases.FeedManager;
import use_cases.LoginManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FeedController {
    private final FeedManager feedManager;
    private final LoginManager loginManager;
    private final InOut inOut;
    private final DatabaseManager databaseManager = new DatabaseManager();
    // private final DatabaseManager databaseManager = DatabaseManager.getInstance();
    private HashMap<Integer, Post> postsActionMap = new HashMap<>();

    public FeedController(InOut inOut, LoginManager loginManager) {
        this.inOut = inOut;
        this.loginManager = loginManager;
        User currUser = this.loginManager.getCurrUser();
        Post[] posts = this.databaseManager.getAllPosts();
        Feed currUserFeed = new Feed(new ArrayList<>(Arrays.asList(posts)));
        this.feedManager = new FeedManager(currUser, currUserFeed);
    }

    public void run(ShellAction action) {
        if (action == ShellAction.BROWSEFEED) {
            this.runBrowseFeed();
        }
    }

    private void runBrowseFeed() {
        // Ask for a filter option for the feed
        ArrayList<Integer> filterInput = this.getFilterInput();
        this.feedManager.setFeedFilter(filterInput);
        Feed feed = this.feedManager.getCurrentUsersFeed();

        // Generate the posts and ask to select one to like/comment
        String postsString = this.generateFeedActions(feed.getDisplayedPosts());
        this.selectOnePost(postsString, feed.getDisplayedPosts().size());
    }

    private void runPostAction(Post selectedPost, Integer postAction) {
        switch (postAction) {
            case 0:
                selectedPost.addLike(this.loginManager.getCurrUser());
                break;
            case 1:
                selectedPost.addComment(this.getUserComment());
                break;
            default:
                this.inOut.setOutput("You entered an invalid action.");
        }
    }

    private String generateFeedActions(ArrayList<Post> posts) {
        this.postsActionMap = new HashMap<>();
        StringBuilder postsString = new StringBuilder("Enter a post number for a detailed view of that post: ");

        for (int i=0; i < posts.size(); i++) {
            this.postsActionMap.put(i, posts.get(i));
            postsString.append("\n");
            postsString.append(i).append(" ");
            // Change getAuthorId to getAuthor
            postsString.append(posts.get(i).getAuthorId());
            postsString.append("'s ").append(posts.get(i).getRecipe().getTitle());
        }
        return postsString.toString();
    }

    private void selectOnePost(String postsString, int numOfDisplayedPosts) {
        int postNumber = this.getSelectedPostInput(postsString, numOfDisplayedPosts);
        Post selectedPost = this.postsActionMap.get(postNumber);
        this.displayPostInfo(selectedPost);

        int postAction = this.getPostActionInput();

        this.runPostAction(selectedPost, postAction);
        this.displayPostInfo(selectedPost);
    }

    private int getSelectedPostInput(String postsString, int numOfDisplayedPosts) {
        int postNumber = 0;
        boolean success = false;
        while (!success) {
            try {
                do {
                    String postSelection = this.inOut.getInput(postsString);
                    postNumber = Integer.parseInt(postSelection);
                } while (postNumber < 0 || postNumber > numOfDisplayedPosts - 1);
                success = true;
            } catch (IOException e) {
                this.inOut.setOutput("An error occurred: " + e);
            } catch (NumberFormatException nfe) {
                this.inOut.setOutput("You entered an invalid action input.");
            }
        }
        return postNumber;
    }

    private int getPostActionInput() {
        int postAction = 0;
        boolean success = false;
        while (!success) {
            try {
                do {
                    String postActionPrompt = "Please select an action for this post: \n"
                            + "0 Like the Post \n" + "1 Comment on the Post";
                    String postActionString = this.inOut.getInput(postActionPrompt);
                    postAction = Integer.parseInt(postActionString);
                } while (postAction < 0 || postAction > 1);
                success = true;
            } catch (IOException e) {
                this.inOut.setOutput("An error occurred: " + e);
            } catch (NumberFormatException nfe) {
                this.inOut.setOutput("You entered an invalid action input.");
            }
        }
        return postAction;
    }

    private Comment getUserComment() {
        // This function MAY BE REMOVED if it is implemented in PostController instead.

        Comment comment = new Comment("", this.loginManager.getCurrUser().getId(),
                LocalDateTime.now());
        try {
            String commentText = this.inOut.getInput("Please write your comment:");
            comment.setText(commentText);
        } catch (IOException e) {
            this.inOut.setOutput("An error occurred: " + e);
        }
        return comment;
    }

    private void displayPostInfo(Post selectedPost) {
        // This function will be REMOVED once PostController.displayPost() is implemented.

        this.inOut.setOutput("Post Title: " + selectedPost.getAuthorId() + "'s " + selectedPost.getRecipe().getTitle());
        // this.inOut.setOutput("Recipe Ingredients: " + selectedPost.getRecipe().getIngredients())
        // this.inOut.setOutput("Recipe Steps: " + selectedPost.getRecipe().getSteps())
        this.inOut.setOutput("Category: " + selectedPost.getCategory());
        this.inOut.setOutput("Number of Likes: " + selectedPost.getNumLikes());
        // this.inOut.setOutput("Comments: " + selectedPost.getComments());
    }

    private ArrayList<Integer> getFilterInput() {
        int filterInput = 0;
        int cuisineInput = 0;
        boolean success = false;
        while (!success) {
            try {
                do {
                    String filterInputString = this.inOut.getInput("Please enter a valid number that corresponds " +
                            "to how you want to filter your feed: \n" + "0 No filter \n" + "1 Filter by cuisine \n" +
                            "2 Filter by likes \n" + "3 Filter by your following list");
                    filterInput = Integer.parseInt(filterInputString);
                } while (filterInput < 0 || filterInput > 3);

                if (filterInput == 1) {
                    cuisineInput = this.getCuisineInput();
                }
                success = true;
            } catch (IOException e) {
                this.inOut.setOutput("An error occurred: " + e);
            } catch (NumberFormatException nfe) {
                this.inOut.setOutput("You entered an invalid action input.");
            }

        }
        return new ArrayList<>(Arrays.asList(filterInput, cuisineInput));
    }

    private int getCuisineInput() {
        int cuisineInput = 0;
        boolean success = false;
        while (!success) {
            try {
                do {
                    String cuisineInputString = this.inOut.getInput("Enter the number that corresponds " +
                            "to the cuisine you would like to see: \n" + "0 Chinese \n" + "1 American \n" +
                            "2 Japanese \n" + "3 Italian \n" + "4 French \n" + "5 Mexican \n" + "6 Others");
                    cuisineInput = Integer.parseInt(cuisineInputString);
                } while (cuisineInput < 0 || cuisineInput > 6);
                success = true;
            } catch (IOException e) {
                this.inOut.setOutput("An error occurred: " + e);
            } catch (NumberFormatException nfe) {
                this.inOut.setOutput("You entered an invalid action input.");
            }
        }
        return cuisineInput;
    }
}
