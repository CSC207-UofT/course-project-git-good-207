package use_cases;

import entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PostManagerTest {
    private PostManager postManager;
    private Post samplePost;

    @BeforeEach
    void setupPostManagerTest() {
        // set up a list of predefined posts
        ArrayList<Post> listPosts = new ArrayList<>();
        ArrayList<Ingredient> ingredient = new ArrayList<>();
        ArrayList<String> steps = new ArrayList<>();
        steps.add("Add Cheese into the oven");
        ingredient.add(new Ingredient("Cheese"));
        listPosts.add(new Post("ibarrame", LocalDateTime.now(), new
                Recipe("Recipe Test", ingredient, steps, UUID.randomUUID().toString()), "Italian", UUID.randomUUID().toString()));

        this.postManager = new PostManager(listPosts);
        // setting up the sample post
        ArrayList<Ingredient> otherIngredients = new ArrayList<>();
        ArrayList<String> otherSteps = new ArrayList<>();
        steps.add("Add Cheese into the oven");
        ingredient.add(new Ingredient("Cheese"));
        this.samplePost = new Post("James", LocalDateTime.now(), new Recipe("Second Cheese",
                otherIngredients,
                otherSteps, UUID.randomUUID().toString()),
                "latin-american", UUID.randomUUID().toString());

    }

    @Test
    void testCommentPost() {
        Comment comment = new Comment("Great Place", "ibarrame", LocalDateTime.now(), UUID.randomUUID().toString());
        this.postManager.createPost(this.samplePost);
        boolean commented = this.postManager.commentPost(this.samplePost, comment);
        // the last comment should be the one we add it
        ArrayList<Comment> allComments = this.postManager.getSpecificPost(this.samplePost.getId()).getComments();
        assert(allComments.size() == 1 && commented);

    }

    @Test
    void testLikePost() {
        User user = new User("ibarrame", "password", "" ,UUID.randomUUID().toString());
        this.postManager.createPost(this.samplePost);
        boolean liked = this.postManager.likePost(this.samplePost, user);
        assert(this.postManager.getSpecificPost(this.samplePost.getId()).getNumLikes() == 1 && liked);


    }

    @Test
    void testCreatePost() {
        // create a simple post

        int total_posts = this.postManager.getPosts().size();
        this.postManager.createPost(this.samplePost);
        assert total_posts + 1 == this.postManager.getPosts().size();
    }

    @Test
    void getPosts() {
        assert this.postManager.getPosts().size() == 1;
    }
}