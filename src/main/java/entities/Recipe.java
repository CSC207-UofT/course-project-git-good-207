package entities;

import java.util.ArrayList;
import java.util.UUID;

public class Recipe {
    private ArrayList<String> steps;
    private ArrayList<Ingredient> ingredients;
    private String title;
    private UUID id = UUID.randomUUID();

    public Recipe(String title) {
        this.steps = new ArrayList<>();
        this.ingredients = new ArrayList<>();
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

    public void deleteLastStep() { steps.remove(steps.size() - 1); }
}
