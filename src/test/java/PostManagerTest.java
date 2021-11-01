import entities.Ingredient;
import entities.Post;
import entities.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_cases.PostManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PostManagerTest {
    private PostManager postManager;

    @BeforeEach
    void setupPostManagerTest(){
        // set up a list of predefined posts
        ArrayList<Post> listPosts = new ArrayList<>();
        ArrayList<Ingredient> ingredient = new ArrayList<>();
        ArrayList<String> steps = new ArrayList<>();
        steps.add("Add Cheese into the oven");
        ingredient.add(new Ingredient("Cheese"));
        listPosts.add(new Post("ibarrame", LocalDateTime.now(), new
                Recipe("Recipe Test", ingredient, steps), "Italian"));

        this.postManager = new PostManager(listPosts);
    }
}
