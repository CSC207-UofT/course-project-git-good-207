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

    private boolean interactPost(Post post, Object obj, boolean type){
        for (int i = 0; i < this.posts.size(); i++) {
            if (this.posts.get(i).equals(post)) {
                if (type) {
                    this.posts.get(i).addComment((Comment) obj);
                }
                else {
                    this.posts.get(i).addLike((User) obj);
                }
                this.databaseManager.updatePost(this.posts.get(i));
                return true;
            }
        }
        return false;
    }

    public Ingredient createMeasurableIngredient(String name, float amount, String units){
        MeasurableIngredient ing = new MeasurableIngredient(name, amount, units);
        return ing;
    }

    public Ingredient createCountableIngredient(String name, int number){
        CountableIngredient ing = new CountableIngredient(name, number);
        return ing;
    }

    public void createPost(User owner, LocalDateTime postedTime, Recipe recipe, String category)
    {
        Post p = new Post(owner, postedTime, recipe, category);
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
