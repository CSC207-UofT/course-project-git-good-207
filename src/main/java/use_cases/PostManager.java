package use_cases;

import entities.*;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

/*
    add a new post, comment/like a Post
 */
public class PostManager {
    private final ArrayList<Post> posts;
    private DatabaseManager databaseManager;
    private UserManager userManager;
    public PostManager(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.userManager = new UserManager(databaseManager);
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
     * Return true if like or comment is added to target post, false otherwise
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
    public void createPost(User owner, LocalDateTime postedTime, Recipe recipe, String category, String id)
    {
        Post p = new Post(owner.getId(), postedTime, recipe, category, id);
        this.createPost(p);

    }

    /**
     * Comments on a post if and only if could be done returns true
     * @param post The post object itself
     * @param comment The comment that is being added
     * @return true if the comment was added to the Post
     */
    public boolean commentPost(Post post, Comment comment){
        return interactPost(post, comment, true);
    }

    /**
     *  likes a post and saves the state
     * @param post the post that is being liked
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
     * @return a list of all the posts currently available
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
    public Post getSpecificPost(String id) {
        for(Post element: this.getPosts()) {
            if (id.equals(element.getId())) {
                return element;
            }
        }
        return null;
    }
    /**
     * Given a post id it returns a list with the liked users' usernames
     * of that post
     * @param postId id of the post
     * @return a list of the liked users' usernames
     */
    public String[] getPostLikedUsers(String postId){

        ArrayList<User> likedUsers = this.getSpecificPost(postId).getLikedUsers();
        String[] likes = new String[likedUsers.size()];
        for (int i = 0; i < likedUsers.size(); i++) {
            likes[i] = userManager.getUsername(likedUsers.get(i));
        }
        return likes;
    }

    /**
     * Return array of comments with usernames before each comment
     * of that post
     * @param postId id of the post
     * @return an array of the comments associated to the post
     */
    public String[] getPostComments(String postId){

        ArrayList<Comment> commentObjects = this.getSpecificPost(postId).getComments();
        String[] comments = new String[commentObjects.size()];
        for (int i = 0; i < commentObjects.size(); i++) {
            comments[i] = userManager.getUsernameById(commentObjects.get(i).getAuthorId()) + ": " + commentObjects.get(i).getCommentText();
        }
        return comments;
    }

    /**
     * Given the post id it returns the recipe associated to
     * it
     * @param postId id of the post
     * @return the recipe object of the post
     */
    public Recipe getPostRecipe(String postId){
        return this.getSpecificPost(postId).getRecipe();
    }

    /**
     * Return author id of the post
     * @param postId id of the post
     * @return author of the post
     */
    public String getPostAuthor(String postId){
        return this.getSpecificPost(postId).getAuthorId();
    }

    /**
     * Given the post id returns the category of it
     * @param postId id of the post
     * @return category of the post
     */
    public String getPostCategory(String postId){
        return this.getSpecificPost(postId).getCategory();
    }

    /**
     * Given the post id returns the posted time
     * @param postId id of the post
     * @return time this post was posted
     */
    public String getPostedTime(String postId){
        return this.getSpecificPost(postId).getCreatedTime();
    }

}
