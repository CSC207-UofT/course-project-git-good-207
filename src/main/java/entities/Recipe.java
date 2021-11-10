package entities;

import java.util.ArrayList;

public class Recipe {
    private final ArrayList<String> steps;
    private final ArrayList<Ingredient> ingredients;
    private String title;
    private final String id;

    public Recipe(String title, ArrayList<Ingredient> ingredients, ArrayList<String> steps, String id) {
        this.steps = steps;
        this.ingredients = ingredients;
        this.title = title;
        this.id = id;
    }

    public void addStep(String step) {
        this.steps.add(step);
    }

    public ArrayList<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public ArrayList<CountableIngredient> getCountableIngredients() {
        ArrayList<CountableIngredient> countableIngredients = new ArrayList<>();
        for (Ingredient ing: this.ingredients) {
            if (ing instanceof CountableIngredient)
                countableIngredients.add((CountableIngredient) ing);
        } return countableIngredients;
    }

    public ArrayList<MeasurableIngredient> getMeasurableIngredients() {
        ArrayList<MeasurableIngredient> measurableIngredients = new ArrayList<>();
        for (Ingredient ing: this.ingredients) {
            if (ing instanceof MeasurableIngredient)
                measurableIngredients.add((MeasurableIngredient) ing);
        } return measurableIngredients;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) {this.title = title;}

    public String getId() {
        return this.id;
    }

    public void deleteLastStep() { steps.remove(steps.size() - 1); }
}
