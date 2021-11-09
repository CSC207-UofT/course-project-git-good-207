package use_cases;

import entities.CountableIngredient;
import entities.Ingredient;
import entities.MeasurableIngredient;
import entities.Recipe;

import java.util.ArrayList;

public class RecipeManager {

    public Ingredient createMeasurableIngredient(String name, float amount, String units){
        return new MeasurableIngredient(name, amount, units);
    }

    public Ingredient createCountableIngredient(String name, Float number){
        return new CountableIngredient(name, number);
    }

    public Recipe createRecipe(String title, ArrayList<Ingredient> ingredients, ArrayList<String> steps, String id) {
        return new Recipe(title, ingredients, steps, id);
    }

    public String getRecipeTitle(Recipe recipe) {
        return recipe.getTitle();
    }

    public ArrayList<String> getRecipeSteps(Recipe recipe) {
        return recipe.getSteps();
    }


    public void deleteStep(Recipe recipe) {
        recipe.deleteLastStep();
    }

    public void addStep(Recipe recipe, String step) {
        recipe.addStep(step);
    }

    public void updateTitle(Recipe recipe, String title) {recipe.setTitle(title);}
}
