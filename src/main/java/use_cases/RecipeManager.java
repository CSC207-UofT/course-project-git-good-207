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

    public Ingredient createCountableIngredient(String name, int number){
        return new CountableIngredient(name, number);
    }

    public Recipe createRecipe(String title, ArrayList<Ingredient> ingredients, ArrayList<String> steps) {
        return new Recipe(title, ingredients, steps);
    }
}
