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
     * @param targetPost the post we want to interact
     * @param obj the object in case of a adding a comment is a
     *           comment, but in the case of being a like is the user
     * @param type depending on its either a comment or a like
     *            then it would change the behavior
     * @return true iff the post was founded and could be interacted with
     */
    private boolean interactPost(Post targetPost, Object obj, boolean type){
        for (Post post: this.posts) {
            if (post.getId().equals(targetPost.getId())) {
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
     * @param owner who created the post
     * @param postedTime the time it was posted
     * @param recipe a recipe object containing the info of the recipe
     * @param category which category it belongs
     */
    public void createPost(User owner, LocalDateTime postedTime, Recipe recipe, String category)
    {
        Post p = new Post(owner.getId(), postedTime, recipe, category);
        this.createPost(p);
    }

    /**
     * Comments a post iff could be done returns true
     * @param post The post object itself
     * @param comment The comment that is being added
     * @return true if the comment was added to the Post
     */
    public boolean commentPost(Post post, Comment comment){
        return interactPost(post, comment, true);
    }

    /**
     *  likes a post and saves the state
     * @param post the post is being liked
     * @param user the user that is giving the like
     * @return true if the post was liked
     */
    public boolean likePost(Post post, User user) {
        return interactPost(post, user, false);
    }

    /**
     * Adds a post directly from object
     * @param post the post object is being added
     */
    public void createPost(Post post){

        this.posts.add(post);
        this.databaseManager.addNewPost(post);
    }

    /**
     * Return method that returns a list of the posts
     * @return a list of all the posts currrently available
     */
    public ArrayList<Post> getPosts() {
        return this.posts;
    }

    /**
     * Given a post id as a parameter, returns the same post
     * that is stored in the PostManager
     * @param id the unique identifier of a post
     * @return the post stored in the postManager
     */
    public Post getSpecificPost(String id)
    {
        for(Post element: this.getPosts())
        {
            if (id.equals(element.getId()))
            {
                return element;
            }
        }
        return null;
    }
    /**
     * Given a post id it returns a list with the liked users
     * of that post
     * @param postId id of the post
     * @return a list of the liked users
     */
    public ArrayList<User> getLikedUsers(String postId){
        return this.getSpecificPost(postId).getLikedUsers();
    }

    /**
     * Given a post id it returns a list with the comments
     * of that post
     * @param postId id of the post
     * @return a list of the comments associated to the post
     */
    public ArrayList<Comment> getComments(String postId){
        return this.getSpecificPost(postId).getComments();
    }

    /**
     * Given the post id it returns the recipe associated to
     * it
     * @param postId id of the post
     * @return the recipe object of the post
     */
    public Recipe getRecipe(String postId){
        return this.getSpecificPost(postId).getRecipe();
    }

    /**
     * Given the post id returns the category of it
     * @param postId id of the post
     * @return category of the post
     */
    public String getCategory(String postId){
        return this.getSpecificPost(postId).getCategory();
    }
}
