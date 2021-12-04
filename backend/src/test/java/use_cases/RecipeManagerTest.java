package use_cases;

import entities.CountableIngredient;
import entities.Ingredient;
import entities.MeasurableIngredient;
import entities.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeManagerTest {
    private RecipeManager recipeManager;
    private Recipe recipe;

    @BeforeEach
    void setUpRecipe() {
        recipeManager = new RecipeManager();
        // set up measurable and countable ingredients
        Ingredient measurableOats = recipeManager.createMeasurableIngredient("oats", 0.25F, "cup");
        Ingredient measurableWater = recipeManager.createMeasurableIngredient("water", 0.5F, "cup");
        Ingredient countableIngredient = recipeManager.createCountableIngredient("blueberries", 15F);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(measurableOats);
        ingredients.add(measurableWater);
        ingredients.add(countableIngredient);
        // set up steps
        ArrayList<String> steps = new ArrayList<>();
        steps.add("add water");
        steps.add("cook oatmeal");
        this.recipe = recipeManager.createRecipe("Oatmeal", ingredients, steps, "1323");
    }

    @Test
    void testGetRecipeTitle() {
        Assertions.assertEquals(recipeManager.getRecipeTitle(recipe), "Oatmeal");
    }

    @Test
    void testGetRecipeSteps() {
        ArrayList<String> steps = new ArrayList<>();
        steps.add("add water");
        steps.add("cook oatmeal");
        Assertions.assertEquals(recipeManager.getRecipeSteps(recipe), steps);
    }

    @Test
    void testGetAllIngredients() {
        String[] ingredients = recipeManager.getAllIngredients(recipe);
        for (int i = 0; i < ingredients.length; i++) {
            String[] splitIngredient = ingredients[i].split(" ");
            ingredients[i] = splitIngredient[splitIngredient.length - 1];
        }
        String[] expected = new String[]{"oats", "water", "blueberries"};

        assert Arrays.equals(ingredients, expected);
    }
}
