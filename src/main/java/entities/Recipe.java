package entities;

import java.util.ArrayList;

public class Recipe {
    private ArrayList<String> steps;
    private ArrayList<Ingredient> ingredients;
    private String title;
    private String id;

    /**
     * Construct a Recipe with its name, its amount,
     * and the type of measurement.
     *
     * @param title The title of the recipe
     * @param ingredients The list of ingredients
     * @param steps The recipe steps
     * @param id the unique id of the recipe
     */
    public Recipe(String title, ArrayList<Ingredient> ingredients, ArrayList<String> steps, String id) {
        this.steps = steps;
        this.ingredients = ingredients;
        this.title = title;
        this.id = id;
    }

    /**
     * Add a step to this Recipe
     *
     * @param step the step to add
     */
    public void addStep(String step) {
        this.steps.add(step);
    }

    /**
     * Return all the ingredients of Recipe
     *
     * @return ArrayList of recipe's ingredients
     */
    public ArrayList<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    /**
     * Return all the Countable ingredients of Recipe
     *
     * @return ArrayList of recipe's Countable ingredients
     */
    public ArrayList<CountableIngredient> getCountableIngredients() {
        ArrayList<CountableIngredient> countableIngredients = new ArrayList<>();
        for (Ingredient ing: this.ingredients) {
            if (ing instanceof CountableIngredient)
                countableIngredients.add((CountableIngredient) ing);
        } return countableIngredients;
    }

    /**
     * Return all the Measurable ingredients of Recipe
     *
     * @return ArrayList of recipe's Measurable ingredients
     */
    public ArrayList<MeasurableIngredient> getMeasurableIngredients() {
        ArrayList<MeasurableIngredient> measurableIngredients = new ArrayList<>();
        for (Ingredient ing: this.ingredients) {
            if (ing instanceof MeasurableIngredient)
                measurableIngredients.add((MeasurableIngredient) ing);
        } return measurableIngredients;
    }

    /**
     * Return all the steps of Recipe
     *
     * @return ArrayList of recipe's steps
     */
    public ArrayList<String> getSteps() {
        return steps;
    }

    /**
     * Return title of Recipe
     *
     * @return recipe title
     */
    public String getTitle() { return title; }

    /**
     * Set title of recipe
     *
     * @param title title to be set
     */
    public void setTitle(String title) {this.title = title;}

    /**
     * Return the id of Recipe
     *
     * @return recipe id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Delete last step of recipe
     *
     */
    public void deleteLastStep() { steps.remove(steps.size() - 1); }
}
