package use_cases;

import entities.CountableIngredient;
import entities.Ingredient;
import entities.MeasurableIngredient;
import entities.Recipe;

import java.util.ArrayList;

public class RecipeManager {

    /**
     * Return the new created MeasurableIngredient.
     *
     * @param name the name of the ingredient
     * @param amount the amount needed of ingredient
     * @param units the unit used to measure ingredient
     * @return the new MeasurableIngredient
     */
    public Ingredient createMeasurableIngredient(String name, float amount, String units){
        return new MeasurableIngredient(name, amount, units);
    }

    /**
     * Return the new created CountableIngredient.
     *
     * @param name the name of the ingredient
     * @param number the number needed of ingredient
     * @return the new CountableIngredient
     */
    public Ingredient createCountableIngredient(String name, Float number){
        return new CountableIngredient(name, number);
    }

    /**
     * Return the new created Recipe.
     *
     * @param title the title of the recipe
     * @param ingredients the list of ingredients for recipe
     * @param steps the recipe steps
     * @param id the id of the recipe
     * @return the new Recipe created
     */
    public Recipe createRecipe(String title, ArrayList<Ingredient> ingredients, ArrayList<String> steps, String id) {
        return new Recipe(title, ingredients, steps, id);
    }

    /**
     * Return the title of target recipe
     *
     * @param recipe the target recipe we need title of
     * @return the recipe's title
     */
    public String getRecipeTitle(Recipe recipe) {
        return recipe.getTitle();
    }

    /**
     * Return the steps of target recipe
     *
     * @param recipe the target recipe we need steps of
     * @return the ArrayList of recipe's steps
     */
    public ArrayList<String> getRecipeSteps(Recipe recipe) {
        return recipe.getSteps();
    }

    /**
     * Return all the ingredients of target recipe
     *
     * @param recipe the target recipe we need ingredients of
     * @return Array of recipe's ingredients
     */
    public String[] getAllIngredients(Recipe recipe) {
        ArrayList<MeasurableIngredient> measurableIngredients = recipe.getMeasurableIngredients();
        ArrayList<CountableIngredient> countableIngredients = recipe.getCountableIngredients();
        String[] ingredients = new String[measurableIngredients.size() + countableIngredients.size()];
        for (int i = 0; i < measurableIngredients.size(); i++) {
            ingredients[i] = measurableIngredients.get(i).getMeasurableIngredient();
        }
        for (int i = measurableIngredients.size(); i < ingredients.length; i++) {
            ingredients[i] = countableIngredients.get(i - measurableIngredients.size()).getCountableIngredient();
        }
        return ingredients;
    }

    public void deleteStep(Recipe recipe) {
        recipe.deleteLastStep();
    }

    public void addStep(Recipe recipe, String step) {
        recipe.addStep(step);
    }

    public void updateTitle(Recipe recipe, String title) {recipe.setTitle(title);}
}
