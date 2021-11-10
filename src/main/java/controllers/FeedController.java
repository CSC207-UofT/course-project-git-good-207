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
import java.util.UUID;

public class FeedController {
    private final FeedManager feedManager;
    private final LoginManager loginManager;
    private final PostController postController;
    private final InOut inOut;
    private final DatabaseManager databaseManager;
    private HashMap<Integer, Post> postsActionMap = new HashMap<>();

    public FeedController(InOut inOut, DatabaseManager dbManager,
                          LoginManager loginManager, PostController postController) {
        this.inOut = inOut;
        this.loginManager = loginManager;
        this.postController = postController;
        this.databaseManager = dbManager;
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

    /**
     * Run to allow the user to browse the Feed.
     */
    private void runBrowseFeed() {
        // Ask for a filter option for the feed
        ArrayList<Integer> filterInput = this.getFilterInput();
        this.feedManager.setFeedFilter(filterInput);
        Feed feed = this.feedManager.getCurrentUsersFeed();

        // Generate the posts and ask to select one to like/comment
        String postsString = this.generateDisplayedPosts(feed.getDisplayedPosts());
        this.selectOnePost(postsString, feed.getDisplayedPosts().size());
    }

    /**
     * Run the action chosen (add a like, add a comment) on the selected post.
     * @param selectedPost The Post selected to act on.
     * @param postAction The int representing the action to do to a Post.
     */
    private void runPostAction(Post selectedPost, Integer postAction) {
        switch (postAction) {
            case 0:
                // Call PostController to add like

                break;
            case 1:
                // Call PostController to add comment
                Comment newComment = this.getUserComment();

                break;
            default:
                this.inOut.setOutput("You entered an invalid action.");
        }
    }

    /**
     * Generate a selection of displayed posts' titles for the user to select.
     * @param posts An ArrayList of Posts representing the selection of displayed Posts on a Feed.
     * @return a String representing a selection of displayed Posts' titles on a Feed.
     */
    private String generateDisplayedPosts(ArrayList<Post> posts) {
        this.postsActionMap = new HashMap<>();
        StringBuilder postsString = new StringBuilder("Enter a post number for a detailed view of that post: ");

        for (int i = 0; i < posts.size(); i++) {
            this.postsActionMap.put(i, posts.get(i));
            postsString.append("\n");
            postsString.append(i).append(" ");
            // Change getAuthorId to getAuthor
            postsString.append(posts.get(i).getAuthorId());
            postsString.append("'s ").append(posts.get(i).getRecipe().getTitle());
        }
        return postsString.toString();
    }

    /**
     * Allow the user to act on a post selected.
     * @param postsString The String representing the selection of displayed Posts' titles generated.
     * @param numOfDisplayedPosts The number of displayed Posts on a Feed.
     */
    private void selectOnePost(String postsString, int numOfDisplayedPosts) {
        int postNumber = this.getSelectedPostInput(postsString, numOfDisplayedPosts);
        Post selectedPost = this.postsActionMap.get(postNumber);
        postController.displayPost(selectedPost);

        int postAction = this.getPostActionInput();

        this.runPostAction(selectedPost, postAction);
        postController.displayPost(selectedPost);
    }

    /**
     * Get the user to select a post to act on.
     * @param postsString The String representing the selection of displayed Posts' titles generated.
     * @param numOfDisplayedPosts The number of displayed Posts on a Feed.
     * @return an int representing the Post that the user selects.
     */
    private int getSelectedPostInput(String postsString, int numOfDisplayedPosts) {
        int postNumber = 0;
        try {
            do {
                String postSelection = this.inOut.getInput(postsString);
                postNumber = Integer.parseInt(postSelection);
            } while (postNumber < 0 || postNumber > numOfDisplayedPosts - 1);
        } catch (IOException e) {
            this.inOut.setOutput("An error occurred: " + e);
        } catch (NumberFormatException nfe) {
            this.inOut.setOutput("You entered an invalid action input.");
        }
        return postNumber;
    }

    /**
     * Get the user to select an action to do to a post.
     * @return an int representing the action that the user wants to do to a Post.
     */
    private int getPostActionInput() {
        int postAction = 0;
        try {
            do {
                String postActionPrompt = "Please select an action for this post: \n"
                        + "0 Like the Post \n" + "1 Comment on the Post";
                String postActionString = this.inOut.getInput(postActionPrompt);
                postAction = Integer.parseInt(postActionString);
            } while (postAction < 0 || postAction > 1);
        } catch (IOException e) {
            this.inOut.setOutput("An error occurred: " + e);
        } catch (NumberFormatException nfe) {
            this.inOut.setOutput("You entered an invalid action input.");
        }
        return postAction;
    }

    /**
     * Get the Comment that the user wants to add on a Post.
     * @return a new Comment that the user has decided to add on a Post.
     */
    private Comment getUserComment() {
        Comment comment = new Comment("", this.loginManager.getCurrUser().getId(),
                LocalDateTime.now(), UUID.randomUUID().toString());
        String commentText;
        try {
            do {
                commentText = this.inOut.getInput("Please write your comment:");
            } while (commentText.isEmpty());
            comment.setText(commentText);
        } catch (IOException e) {
            this.inOut.setOutput("An error occurred: " + e);
        }
        return comment;
    }

    /**
     * Get the choice of filter that the user wants to apply on the Feed.
     * @return an ArrayList of ints representing the choice of filter and the type of cuisine (if filtering by cuisine).
     */
    private ArrayList<Integer> getFilterInput() {
        int filterInput = 0;
        int cuisineInput = 0;
        try {
            do {
                String filterInputString = this.inOut.getInput("Please enter a valid number that corresponds " +
                        "to how you want to filter your feed: \n" + "0 No filter \n" + "1 Filter by cuisine \n" +
                        "2 Filter by likes \n" + "3 Filter by your following list \n" + "4 Filter by recommendations");
                filterInput = Integer.parseInt(filterInputString);
            } while (filterInput < 0 || filterInput > 4);

            if (filterInput == 1) {
                cuisineInput = this.getCuisineInput();
            }
        } catch (IOException e) {
            this.inOut.setOutput("An error occurred: " + e);
        } catch (NumberFormatException nfe) {
            this.inOut.setOutput("You entered an invalid action input.");
        }

        return new ArrayList<>(Arrays.asList(filterInput, cuisineInput));
    }

    /**
     * Get the type of cuisine that the user chooses to filter the Feed with.
     * @return an int representing the cuisine type that the user chooses to filter the Feed with.
     */
    private int getCuisineInput() {
        int cuisineInput = 0;
        try {
            do {
                String cuisineInputString = this.inOut.getInput("Enter the number that corresponds " +
                        "to the cuisine you would like to see: \n" + "0 Chinese \n" + "1 American \n" +
                        "2 Japanese \n" + "3 Italian \n" + "4 French \n" + "5 Mexican \n" + "6 Others");
                cuisineInput = Integer.parseInt(cuisineInputString);
            } while (cuisineInput < 0 || cuisineInput > 6);
        } catch (IOException e) {
            this.inOut.setOutput("An error occurred: " + e);
        } catch (NumberFormatException nfe) {
            this.inOut.setOutput("You entered an invalid action input.");
        }
        return cuisineInput;
    }
}
