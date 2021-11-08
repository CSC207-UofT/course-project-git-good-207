package use_cases;

import entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

/*
    add a new post, comment/like a Post
 */
public class PostManager {
    private ArrayList<Post> posts;
    private DatabaseManager databaseManager;

    public PostManager(DatabaseManager dbManager) {
        this.databaseManager = dbManager;
        Post[] allPosts = this.databaseManager.getAllPosts();
        this.posts = new ArrayList<>(Arrays.asList(allPosts));
    }

    private boolean interactPost(Post targetPost, Object obj, boolean type){
        for (Post post: this.posts) {
            if (post.equals(targetPost)) {
                if (type) {
                    post.addComment((Comment) obj);
                }
                else {
                    post.addLike((User) obj);
                }
                this.databaseManager.updatePost(post);
                return true;
            }
        }
        return false;
    }

    public void createPost(User owner, LocalDateTime postedTime, Recipe recipe, String category, String id)
    {
        Post p = new Post(owner.getId(), postedTime, recipe, category, id);
        this.addPostToList(p);
    }
    public boolean commentPost(Post post, Comment comment){
        return interactPost(post, comment, true);
    }

    public boolean likePost(Post post, User user) {
        return interactPost(post, user, false);
    }
    public void addPostToList(Post post){
        this.posts.add(post);
    }

    public ArrayList<Post> getPosts() {
        return this.posts;
    }


}
