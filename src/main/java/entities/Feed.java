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
        ArrayList<Post> currDisplayedPosts = new ArrayList<>();
        for (Post post : this.posts) {
            if (post.getCategory().equals(filter)) {
                currDisplayedPosts.add(post);
            }
        }
        this.setDisplayedPosts(currDisplayedPosts);
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Post> getDisplayedPosts() {
        return this.displayedPosts;
    }

    public void setDisplayedPosts(ArrayList<Post> displayedPosts) {
        this.displayedPosts = displayedPosts;
    }
}
