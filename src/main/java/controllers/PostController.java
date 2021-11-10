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

    /**
     * Run the appropriate shell action to create a new post
     *
     * @param action The entered action from welcome page
     */
    public void run(ShellAction action) {
        if (action == ShellAction.POST) {
            User currUser = this.loginManager.getCurrUser();
            LocalDateTime timeNow = LocalDateTime.now();
            String promptMeasurable = createPostHelper()[1], promptCountable = createPostHelper()[2],
                    promptRecipeSteps = createPostHelper()[0];

            try {
                String inputMeasurable = getMeasurableIngredients(promptMeasurable);
                String inputCountable = getCountableIngredients(promptCountable);
                ArrayList<Ingredient> allIngredients = createAllIngredientList(inputCountable, createMeasurableIngredient(inputMeasurable));

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

    /**
     * Return String of countable ingredients from user input.
     *
     * @param promptCountable prompt for countable ingredients
     * @return String countable ingredients that user entered
     */
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

    /**
     * Return String of measurable ingredients from user input.
     *
     * @param promptMeasurable prompt for measurable ingredients
     * @return String measurable ingredients that user entered
     */
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

    /**
     * Return array of prompts to user while creating a post
     *
     * @return String countable ingredients that user entered
     */
    private String[] createPostHelper() {
        String promptRecipeSteps = "Enter recipe steps in this comma-separated format: 'Add the water, mix flour'";
        String promptMeasurable = "Enter measurable ingredients (in grams, ounces etc) in format '50 grams sugar, 1 cup flour, etc.' or N/A if no measurable ingredients";
        String promptCountable = "Enter countable ingredients in format '1 lemon, 1 apple, etc.' or N/A if no countable ingredients (must have entered at least one ingredient per recipe)";
        return new String[]{promptRecipeSteps, promptMeasurable, promptCountable};
    }

    /**
     * Return an ArrayList of recipe steps.
     *
     * @param recipeSteps String of recipe steps
     * @return Returns ArrayList of recipe steps
     */
    private ArrayList<String> getRecipeStepsList(String recipeSteps) {
        String[] stepsList = recipeSteps.split(", ");
        List<String> list = Arrays.asList(stepsList);
        return new ArrayList<>(list);
    }

    /**
     * Return ArrayList of all ingredients user entered
     *
     * @param inputCountable String input of countable ingredients from user
     * @param measurable the measurable ingredients that user entered
     * @return ArrayList<Ingredient> the ArrayList of all ingredients user entered
     */
    private ArrayList<Ingredient> createAllIngredientList(String inputCountable, ArrayList<Ingredient> measurable) {
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

    /**
     * Return ArrayList of all measurable ingredients user entered
     *
     * @param inputMeasurable String input of measurable ingredients from user
     * @return ArrayList<Ingredient> the ArrayList of all measurable ingredients user entered
     */
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

    /**
     * Add like or comment to a target post.
     *
     * @param id the id of target post
     * @param obj the User if liking the post; the Comment if commenting on post
     * @param type true if commenting; false if liking the post
     */
    public void interactPost(String id, Object obj, boolean type){
        if (type) {
            postManager.commentPost(postManager.getSpecificPost(id), (Comment) obj);
        } else {
            postManager.likePost(postManager.getSpecificPost(id), (User) obj);
        }
    }

    /**
     * Displays the post to the user given id of the post to be displayed
     *
     * @param id the id of the post to be displayed
     */
    public void displayPost(String id) {
        String header = getPostHeader(id);
        String recipeInfo = getRecipeInfo(id);
        String comments = getPostComments(id);
        String likes = getPostLikes(id);

        this.inOut.setOutput(header);
        this.inOut.setOutput(recipeInfo);
        this.inOut.setOutput(likes);
        this.inOut.setOutput(comments);

    }

    /**
     * Return the "Likes" section of post
     *
     * @param id the id of target post
     * @return String the formatted "likes" section of post
     */
    private String getPostLikes(String id) {
        String[] likesInfo = postManager.getPostLikedUsers(id);
        StringBuilder likesDisplay = new StringBuilder("Liked by (").append(likesInfo.length).append("):\n");
        for (String likedUser: likesInfo){
            likesDisplay.append(likedUser).append("\n");
        }
        return likesDisplay.append("\n\n").toString();
    }

    /**
     * Return the "Comments" section of post
     *
     * @param id the id of target post
     * @return String the formatted "Comments" section of post
     */
    private String getPostComments(String id) {
        String[] comments = postManager.getPostComments(id);
        StringBuilder formattedComments = new StringBuilder("Comments:\n\n");
        for (String comment: comments) {
            formattedComments.append(comment).append("\n");
        }
        return formattedComments.toString();
    }

    /**
     * Return the "Recipe" section of post
     *
     * @param id the id of target post
     * @return String the formatted "Recipe" section of post
     */
    private String getRecipeInfo(String id) {
        Recipe postRecipe = postManager.getPostRecipe(id);
        String recipeTitle = recipeManager.getRecipeTitle(postRecipe);
        String[] recipeIngredients = recipeManager.getAllIngredients(postRecipe);
        ArrayList<String> recipeSteps = recipeManager.getRecipeSteps(postRecipe);
        String category = postManager.getPostCategory(id);

        StringBuilder recipeInfo = new StringBuilder(recipeTitle + "\n" + "\nIngredients:\n");
        for (String ingredient: recipeIngredients) {
            recipeInfo.append(ingredient).append("\n");
        }
        recipeInfo.append("\nSteps:\n");
        for (int i = 0; i < recipeSteps.size(); i++) {
            recipeInfo.append(i + 1).append(". ").append(recipeSteps.get(i)).append("\n");
        }
        return recipeInfo.append("\nCategory: ").append(category).append("\n\n").toString();
    }

    /**
     * Return the post header
     *
     * @param id the id of target post
     * @return String the formatted post header
     */
    private String getPostHeader(String id) {
        String author = userManager.getUsernameById(postManager.getPostAuthor(id));
        String postedTime = postManager.getPostedTime(id);
        return author + "\n" + postedTime + "\n\n";
    }

}
