package entities;

import java.util.ArrayList;
import java.util.UUID;

public class Recipe {
    private ArrayList<String> steps;
    private ArrayList<Ingredient> ingredients;
    private String title;
    private String id = UUID.randomUUID().toString();

    public Recipe(String title, ArrayList<Ingredient> ingredients, ArrayList<String> steps) {
        this.steps = steps;
        this.ingredients = ingredients;
        this.title = title;
    }

    public void addStep(String step) {
        this.steps.add(step);
    }

    public void addIngredients(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
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
