package entities;

import java.util.ArrayList;

public class Feed {
    private ArrayList<Post> posts;
    private ArrayList<Post> displayedPosts;

    public Feed(ArrayList<Post> posts) {
        this.posts = posts;
        this.displayedPosts = posts;
    }

    public void setFilter(String filter) {
        // TODO: displayedPosts = posts.filter((post has filter as a category) => return true)
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Post> getDisplayedPosts() {
        return displayedPosts;
    }

    public void setDisplayedPosts(ArrayList<Post> displayedPosts) {
        this.displayedPosts = displayedPosts;
    }
}
