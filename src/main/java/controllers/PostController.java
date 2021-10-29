package controllers;

import entities.*;
import use_cases.LoginManager;
import use_cases.PostManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import use_cases.PostManager;

public class PostController {
    private InOut inOut;
    private LoginManager loginManager;
    private PostManager postManager;

    public PostController(InOut inOut, PostManager postManager, LoginManager loginManager) {
        this.inOut = inOut;
        this.postManager = postManager;
        this.loginManager = loginManager;
    }

    public void run(ShellAction action) {
        if (action == ShellAction.POST) {
            User currUser = this.loginManager.getCurrUser();
            LocalDateTime now = LocalDateTime.now();
            String promptRecipeSteps = "Enter recipe steps in this comma-separated format: 'Add the water, mix flour'";
            String promptMeasurable = "Enter measurable ingredients (in grams, ounces etc) in format '50 grams sugar, 1 cup flour, etc.' or N/A if no measurable ingredients";
            String promptCountable = "Enter measurable ingredients (in grams, ounces etc) in format '1 lemon, 1 apple, etc.' or N/A if no countable ingredients";

            try {


                String inputMeasurable = this.inOut.getInput(promptMeasurable);
                if (!inputMeasurable.equals("N/A")) {
                    String[] measurable = inputMeasurable.split(", ");
                    for (String measurableIngredient : measurable) {
                        postManager.createMeasurableIngredient(measurableIngredient);
                    }
                }
                String inputCountable = this.inOut.getInput(promptCountable);
                if (!inputCountable.equals("N/A")) {
                    String[] countable = inputCountable.split(", ");
                    for (String countableIngredient : countable) {
                        postManager.createCountableIngredient(countableIngredient);
                    }
                }
                String recipeTitle = this.inOut.getInput("Enter title of recipe");
                Recipe recipe = new Recipe(recipeTitle);
                String category = this.inOut.getInput("What is the recipe category?");
                postManager.createPost(currUser, now, recipe, category);

            } catch (IOException e) {
                inOut.setOutput("There was an error: " + e);
            }

        }
    }

    public void displayPost(Post post) { /* return formatted post */ }
}
