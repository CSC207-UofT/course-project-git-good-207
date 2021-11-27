package entities;

import java.util.ArrayList;

public class Feed {
    private final ArrayList<Post> posts;
    private ArrayList<Post> displayedPosts;

    /**
     * Construct a Feed object.
     * @param posts The list of posts that will be stored inside the feed object.
     */
    public Feed(ArrayList<Post> posts) {
        this.posts = posts;
        this.displayedPosts = posts;
    }

    /**
     * Get the list of Posts stored in the Feed.
     * @return an ArrayList of Posts stored in the Feed.
     */
    public ArrayList<Post> getPosts() { return posts; }

    /**
     * Get the list of displayed Posts stored in the Feed.
     * @return an ArrayList of displayed Posts stored in the Feed.
     */
    public ArrayList<Post> getDisplayedPosts() {
        return this.displayedPosts;
    }

    /**
     * Set the list of displayed Posts stored in the Feed with a new list.
     * @param displayedPosts The new ArrayList of displayed Posts to store in the Feed.
     */
    public void setDisplayedPosts(ArrayList<Post> displayedPosts) {
        this.displayedPosts = displayedPosts;
    }
}
