package use_cases;

import entities.CountableIngredient;
import entities.Ingredient;
import entities.MeasurableIngredient;
import entities.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        int numMeasurable = 0;
        int numCountable = 0;
        ArrayList<Ingredient> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients) {
            if (ingredient instanceof MeasurableIngredient) {
                numMeasurable++;
            } else if (ingredient instanceof CountableIngredient) {
                numCountable++;
            }
        }
        boolean correct = numCountable == 1 && numMeasurable == 2 && ingredients.get(2) instanceof CountableIngredient;
        assert correct;

    }
}
