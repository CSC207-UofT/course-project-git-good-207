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
    private DatabaseManager databaseManager = new DatabaseManager();

    /**
     * Constructor from the database
     */
    public PostManager(){
        Post[] allPosts = this.databaseManager.getAllPosts();
        this.posts = new ArrayList<>(Arrays.asList(allPosts));
    }

    /**
     * Constructor given an array of posts
     */
    public PostManager(ArrayList<Post> posts){
        this.posts = posts;
    }

    /**
     * Private function in order to interacts with the posts
     * @param targetPost
     * @param obj
     * @param type
     * @return
     */
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

    /**
     * Creates a post and adds it to the list of post
     * @param owner
     * @param postedTime
     * @param recipe
     * @param category
     */
    public void createPost(User owner, LocalDateTime postedTime, Recipe recipe, String category)
    {
        Post p = new Post(owner.getId(), postedTime, recipe, category);
        this.createPost(p);
    }

    /**
     * Comments a post iff could be done returns true
     * @param post
     * @param comment
     * @return
     */
    public boolean commentPost(Post post, Comment comment){
        return interactPost(post, comment, true);
    }

    /**
     *  likes a post and saves the state
     * @param post
     * @param user
     * @return
     */
    public boolean likePost(Post post, User user) {
        return interactPost(post, user, false);
    }

    /**
     * Adds a post directly from object
     * @param post
     */
    public void createPost(Post post){
        this.posts.add(post);
    }

    /**
     * Return method that returns a list of the posts
     * @return
     */
    public ArrayList<Post> getPosts() {
        return this.posts;
    }


}
