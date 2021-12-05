package entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {
    private static Recipe recipe;

    @BeforeAll
    static void setUp() {
        CountableIngredient cIngredient = new CountableIngredient("apple", 1);
        MeasurableIngredient mIngredient = new MeasurableIngredient("flour", 3.4F, "cups");
        ArrayList<Ingredient> ingredients = new ArrayList<>(List.of(cIngredient, mIngredient));
        ArrayList<String> steps = new ArrayList<>(List.of("step1"));
        recipe = new Recipe("Title", ingredients, steps, "1");
    }


    @Test
    void testGetIngredients() {
        ArrayList<Ingredient> ingredients = recipe.getIngredients();
        assertTrue(ingredients.size() == 2 &&
                ingredients.get(0).getIngredientName().equals("apple") &&
                ingredients.get(1).getIngredientName().equals("flour"));
    }

    @Test
    void testGetCountableIngredients() {
        ArrayList<CountableIngredient> ingredients = recipe.getCountableIngredients();
        assertTrue(ingredients.size() == 1 &&
                ingredients.get(0).getIngredientName().equals("apple"));
    }

    @Test
    void testGetMeasurableIngredients() {
        ArrayList<MeasurableIngredient> ingredients = recipe.getMeasurableIngredients();
        assertTrue(ingredients.size() == 1 &&
                ingredients.get(0).getIngredientName().equals("flour"));
    }

    @Test
    void getSteps() {
        ArrayList<String> steps = recipe.getSteps();
        assertEquals("[step1]", steps.toString());
    }

    @Test
    void getTitle() {
        assertEquals("Title", recipe.getTitle());
    }

    @Test
    void setTitle() {
        recipe.setTitle("Title New");
        String actual = recipe.getTitle();
        recipe.setTitle("Title");
        assertEquals("Title New", actual);
    }

    @Test
    void getId() {
        assertEquals("1", recipe.getId());
    }
}