package controllers;

import entities.*;
import use_cases.DatabaseManager;
import use_cases.FeedManager;
import use_cases.LoginManager;
import use_cases.UserManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FeedController {
    private final LoginManager loginManager;
    private final PostController postController;
    private final InOut inOut;
    private final DatabaseManager databaseManager;
    private final UserManager userManager;
    private HashMap<Integer, Post> postsActionMap = new HashMap<>();

    /**
     * Construct a FeedController
     *
     * @param inOut        the InOut interface for managing input/output
     * @param dbManager the DatabaseManager
     * @param loginManager the LoginManager
     * @param postController the PostController
     */
    public FeedController(InOut inOut, DatabaseManager dbManager,
                          LoginManager loginManager, PostController postController) {
        this.inOut = inOut;
        this.loginManager = loginManager;
        this.postController = postController;
        this.databaseManager = dbManager;
        this.userManager = new UserManager(this.databaseManager);
    }

    /**
     * Run the appropriate ShellAction
     *
     * @param action ShellAction corresponding to the action that needs to be run
     */
    public void run(ShellAction action) {
        if (action == ShellAction.BROWSEFEED) {
            this.runBrowseFeed();
        }
    }

    /**
     * Run to allow the user to browse the Feed.
     */
    private void runBrowseFeed() {
        Post[] posts = this.databaseManager.getAllPosts();
        Feed currUserFeed = new Feed(new ArrayList<>(Arrays.asList(posts)));
        FeedManager feedManager = new FeedManager(this.loginManager.getCurrUser(), currUserFeed);
        // Ask for a filter option for the feed
        ArrayList<Integer> filterInput = this.getFilterInput();
        if (filterInput.get(0) == 99) {
            this.inOut.setOutput("Returning to main menu.");
        } else {
            feedManager.setFeedFilter(filterInput);
            Feed feed = feedManager.getCurrentUsersFeed();

            // Generate the posts and ask to select one to like/comment or go back
            String postsString = this.generateDisplayedPosts(feed.getDisplayedPosts());
            this.selectOnePost(postsString, feed.getDisplayedPosts().size());
        }
    }

    /**
     * Generate a selection of displayed posts' titles for the user to select.
     *
     * @param posts An ArrayList of Posts representing the selection of displayed Posts on a Feed.
     * @return a String representing a selection of displayed Posts' titles on a Feed.
     */
    public String generateDisplayedPosts(ArrayList<Post> posts) {
        this.postsActionMap = new HashMap<>();
        StringBuilder postsString = new StringBuilder("Enter a post number for a detailed view of " +
                "that post or enter 99 to choose a different feed filter: ");

        if (posts.size() == 0) {
            postsString = new StringBuilder("There aren't any posts suitable for the chosen filter. Pick a different filter.");
        } else {
            for (int i = 0; i < posts.size(); i++) {
                this.postsActionMap.put(i, posts.get(i));
                postsString.append("\n");
                postsString.append(i).append(" ");
                User user = this.userManager.getUserById(posts.get(i).getAuthorId());
                if (user != null) {
                    postsString.append(user.getUsername()).append("'s ");
                }
                postsString.append(posts.get(i).getRecipe().getTitle());
            }
        }
        return postsString.toString();
    }

    /**
     * Allow the user to act on a post selected.
     *
     * @param postsString         The String representing the selection of displayed Posts' titles generated.
     * @param numOfDisplayedPosts The number of displayed Posts on a Feed.
     */
    private void selectOnePost(String postsString, int numOfDisplayedPosts) {
        if (numOfDisplayedPosts == 0) {
            this.inOut.setOutput(postsString);
            this.runBrowseFeed();
        } else {
            int postNumber = this.getSelectedPostInput(postsString, numOfDisplayedPosts);
            if (postNumber == 99) {
                this.runBrowseFeed();
            } else {
                Post selectedPost = this.postsActionMap.get(postNumber);
                this.postController.browsePost(selectedPost);
            }
        }
    }

    /**
     * Get the user to select a post to act on.
     *
     * @param postsString         The String representing the selection of displayed Posts' titles generated.
     * @param numOfDisplayedPosts The number of displayed Posts on a Feed.
     * @return an int representing the Post that the user selects.
     */
    public int getSelectedPostInput(String postsString, int numOfDisplayedPosts) {
        int postNumber = -1;

        while (postNumber < 0 || (postNumber > numOfDisplayedPosts - 1 && postNumber != 99)) {
            try {
                String postSelection = this.inOut.getInput(postsString);
                postNumber = Integer.parseInt(postSelection);
            } catch (NumberFormatException nfe) {
                this.inOut.setOutput("You entered an invalid action input.");
            }
        }

        return postNumber;
    }

    /**
     * Get the choice of filter that the user wants to apply on the Feed.
     *
     * @return an ArrayList of ints representing the choice of filter and the type of cuisine (if filtering by cuisine).
     */
    public ArrayList<Integer> getFilterInput() {
        int filterInput = -1;
        int cuisineInput = 0;

        while (filterInput < 0 || (filterInput > 4 && filterInput != 99)) {
            try {
                String filterInputString = this.inOut.getInput("Please enter a valid number that corresponds " +
                        "to how you want to filter your feed: \n" + "0 No filter \n" + "1 Filter by cuisine \n" +
                        "2 Filter by likes \n" + "3 Filter by your following list \n" +
                        "4 Filter by recommendations \n" + "99 Return to the main menu");
                filterInput = Integer.parseInt(filterInputString);

                if (filterInput == 1) {
                    cuisineInput = this.getCuisineInput();
                    if (cuisineInput == 99) {
                        this.runBrowseFeed();
                    }
                }
            } catch (NumberFormatException nfe) {
                this.inOut.setOutput("You entered an invalid action input.");
            }
        }

        return new ArrayList<>(Arrays.asList(filterInput, cuisineInput));
    }

    /**
     * Get the type of cuisine that the user chooses to filter the Feed with.
     *
     * @return an int representing the cuisine type that the user chooses to filter the Feed with.
     */
    public int getCuisineInput() {
        int cuisineInput = -1;

        while (cuisineInput < 0 || (cuisineInput > 6 && cuisineInput != 99)) {
            try {
                String cuisineInputString = this.inOut.getInput("Enter the number that corresponds " +
                        "to the cuisine you would like to see: \n" + "0 Chinese \n" + "1 American \n" +
                        "2 Japanese \n" + "3 Italian \n" + "4 French \n" + "5 Mexican \n" + "6 Others \n" +
                        "99 Return to choose a different feed filter");
                cuisineInput = Integer.parseInt(cuisineInputString);
            } catch (NumberFormatException nfe) {
                this.inOut.setOutput("You entered an invalid action input.");
            }
        }
        return cuisineInput;
    }
}
