package entities;

import java.util.ArrayList;

public class Feed {
    private final ArrayList<Post> posts;
    private ArrayList<Post> displayedPosts;

    /**
     * Construct a Feed object.
     *
     * @param posts The list of posts that will be stored inside the feed object.
     */
    public Feed(ArrayList<Post> posts) {
        this.posts = posts;
        this.displayedPosts = posts;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public ArrayList<Post> getDisplayedPosts() {
        return this.displayedPosts;
    }

    public void setDisplayedPosts(ArrayList<Post> displayedPosts) {
        this.displayedPosts = displayedPosts;
    }
}
