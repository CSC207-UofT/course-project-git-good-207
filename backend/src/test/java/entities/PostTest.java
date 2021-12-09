package entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PostTest {
    private static final LocalDateTime dateTime = LocalDateTime.now();
    private static Post post;

    @BeforeAll
    static void setUpPost() {
        CountableIngredient cIngredient = new CountableIngredient("tomato", 1);
        MeasurableIngredient mIngredient = new MeasurableIngredient("pasta", 1, "cup");
        ArrayList<Ingredient> ingredients = new ArrayList<>(List.of(cIngredient, mIngredient));
        ArrayList<String> steps = new ArrayList<>(List.of("step1"));
        Recipe recipe = new Recipe("Pasta", ingredients, steps, "1");
        post = new Post("123", dateTime, recipe, "Italian", "1");
    }

    @Test
    void testEquals() {
        CountableIngredient cIngredient = new CountableIngredient("tomato", 1);
        MeasurableIngredient mIngredient = new MeasurableIngredient("pasta", 1, "cup");
        ArrayList<Ingredient> ingredients = new ArrayList<>(List.of(cIngredient, mIngredient));
        ArrayList<String> steps = new ArrayList<>(List.of("step1"));
        Recipe recipe = new Recipe("Pasta", ingredients, steps, "1");
        Post other = new Post("123", dateTime, recipe, "Italian", "2");

        assertNotEquals(post, other);
    }

    @Test
    void testGetRecipe() {
        assertSame("Pasta", post.getRecipe().getTitle());
    }

    @Test
    void testGetId() {
        assertSame("1", post.getId());
    }

    @Test
    void testGetTime() {
        assertSame(post.getCreatedTime(), dateTime);
    }

    @Test
    void testAddComment() {
        Comment new_comment = new Comment("nice", "121212", LocalDateTime.now(), "12");
        post.addComment(new_comment);
        assertSame(post.getComments().size(), 1);
    }

    @Test
    void testAddLike() {
        User new_user = new User("alex", "111", "hi", "10");
        post.addLike(new_user);
        assertSame(post.getLikedUsers().size(), 1);
    }

    @Test
    void testGetLikedUsers() {
        assertSame("alex", post.getLikedUsers().get(0).getUsername());
    }

    @Test
    void testGetNumLikes() {
        assertSame(1, post.getNumLikes());
    }

    @Test
    void testGetCategory() {
        assertSame("Italian", post.getCategory());
    }
}
