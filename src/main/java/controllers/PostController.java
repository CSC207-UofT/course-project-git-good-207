package controllers;

import entities.*;
import use_cases.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PostController {
    private final InOut inOut;
    private final LoginManager loginManager;
    private final PostManager postManager;
    private final RecipeManager recipeManager;
    private final UserManager userManager;
    private boolean noMeasurableIngredients;

    public PostController(InOut inOut, DatabaseManager dbManager, LoginManager loginManager) {
        this.inOut = inOut;
        this.postManager = new PostManager(dbManager);
        this.userManager = new UserManager(dbManager);
        this.recipeManager = new RecipeManager();
        this.loginManager = loginManager;
        this.noMeasurableIngredients = false;
    }

    public void run(ShellAction action) {
        if (action == ShellAction.POST) {
            User currUser = this.loginManager.getCurrUser();
            LocalDateTime timeNow = LocalDateTime.now();
            String promptMeasurable = createPostHelper()[1], promptCountable = createPostHelper()[2],
                    promptRecipeSteps = createPostHelper()[0];

            try {
                String inputMeasurable = getMeasurableIngredients(promptMeasurable);
                String inputCountable = getCountableIngredients(promptCountable);
                ArrayList<Ingredient> allIngredients = createCountableIngredient(inputCountable, createMeasurableIngredient(inputMeasurable));

                String recipeTitle = this.inOut.getInput("Enter title of recipe");
                String recipeSteps = this.inOut.getInput(promptRecipeSteps);

                Recipe recipe = recipeManager.createRecipe(recipeTitle, allIngredients, getRecipeStepsList(recipeSteps), UUID.randomUUID().toString());
                String category = this.inOut.getInput("What is the recipe category?");
                postManager.createPost(currUser, timeNow, recipe, category, UUID.randomUUID().toString());

            } catch (IOException e) {
                inOut.setOutput("There was an error: " + e);
            }

        }
    }

    private String getCountableIngredients(String promptCountable) throws IOException {
        String countableInput = this.inOut.getInput(promptCountable);
        if (countableInput.contains("N/A") && this.noMeasurableIngredients) {
            inOut.setOutput("Must enter at least one ingredient");
            getCountableIngredients(promptCountable);
        } else if (countableInput.contains("N/A")) {
            return "N/A";
        }
        String[] splitIngredients = countableInput.split(",");
        String errorMessage = "That was an invalid input. Please enter countable ingredients again in correct format.";
        for (String ingredient: splitIngredients) {
            String strippedIngredient = ingredient.stripLeading();
            if (!Character.isDigit(strippedIngredient.charAt(0))) {
                inOut.setOutput("Invalid input. Did not specify numerical amount of one of the ingredients. Please enter countable ingredients again in correct format.");
                getCountableIngredients(promptCountable);
            } else if (strippedIngredient.split(" ").length < 2) {
                inOut.setOutput(errorMessage);
                getCountableIngredients(promptCountable);
            }
        }
        return countableInput;
    }

    private String getMeasurableIngredients(String promptMeasurable) throws IOException {
        String measurableInput = this.inOut.getInput(promptMeasurable);
        if (measurableInput.contains("N/A")) {
            this.noMeasurableIngredients = true;
            return "N/A";
        }
        String[] splitIngredients = measurableInput.split(",");
        String errorMessage = "That was an invalid input. Please enter measurable ingredients again in correct format.";
        for (String ingredient: splitIngredients) {
            String strippedIngredient = ingredient.stripLeading();
            if (!Character.isDigit(strippedIngredient.charAt(0))) {
                inOut.setOutput("Invalid input. Did not specify numerical amount of one of the ingredients. Please enter measurable ingredients again in correct format.");
                getMeasurableIngredients(promptMeasurable);
            } else if (strippedIngredient.split(" ").length != 3) {
                inOut.setOutput(errorMessage);
                getMeasurableIngredients(promptMeasurable);
            }
        }
        return measurableInput;
    }

    private String[] createPostHelper() {
        String promptRecipeSteps = "Enter recipe steps in this comma-separated format: 'Add the water, mix flour'";
        String promptMeasurable = "Enter measurable ingredients (in grams, ounces etc) in format '50 grams sugar, 1 cup flour, etc.' or N/A if no measurable ingredients";
        String promptCountable = "Enter countable ingredients in format '1 lemon, 1 apple, etc.' or N/A if no countable ingredients (must have entered at least one ingredient per recipe)";
        return new String[]{promptRecipeSteps, promptMeasurable, promptCountable};
    }

    private ArrayList<String> getRecipeStepsList(String recipeSteps) {
        String[] stepsList = recipeSteps.split(", ");
        List<String> list = Arrays.asList(stepsList);
        return new ArrayList<>(list);
    }

    private ArrayList<Ingredient> createCountableIngredient(String inputCountable, ArrayList<Ingredient> measurable) {
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        if (!inputCountable.equals("N/A")) {
            String[] countable = inputCountable.split(", ");

            for (String countableIngredient : countable) {
                String[] splitIngredient = countableIngredient.split(" ");
                ingredientList.add(recipeManager.createCountableIngredient(splitIngredient[1],
                        Float.valueOf(splitIngredient[0])));
            }
        }
        ingredientList.addAll(measurable);
        return ingredientList;
    }

    private ArrayList<Ingredient> createMeasurableIngredient(String inputMeasurable) {
        if (!inputMeasurable.equals("N/A")) {
            String[] measurable = inputMeasurable.split(", ");
            ArrayList<Ingredient> ingredientList = new ArrayList<>();
            for (String measurableIngredient : measurable) {
                String[] splitIngredientParts = measurableIngredient.split(" ");
                ingredientList.add(recipeManager.createMeasurableIngredient(splitIngredientParts[2],
                        Float.parseFloat(splitIngredientParts[0]), splitIngredientParts[1]));
            }
            return ingredientList;
        }
        return new ArrayList<>();
    }

    public void displayPost(String id) {
        String author = userManager.getUsernameById(postManager.getPostAuthor(id));
        String postedTime = postManager.getPostedTime(id);
        Recipe postRecipe = postManager.getPostRecipe(id);
        String recipeTitle = recipeManager.getRecipeTitle(postRecipe);
        String[] recipeIngredients = recipeManager.getAllIngredients(postRecipe);
        ArrayList<String> recipeSteps = recipeManager.getRecipeSteps(postRecipe);
        String category = postManager.getPostCategory(id);
        ArrayList<String> comments =

    }
}
