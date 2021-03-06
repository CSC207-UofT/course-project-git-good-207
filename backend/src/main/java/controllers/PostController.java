package controllers;

import entities.*;
import use_cases.*;

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
            String promptMeasurable = getRecipePrompts()[1], promptCountable = getRecipePrompts()[2],
                    promptRecipeSteps = getRecipePrompts()[0];
                String inputMeasurable = getMeasurableIngredients(promptMeasurable);
                String inputCountable = getCountableIngredients(promptCountable);
                ArrayList<Ingredient> allIngredients = createAllIngredientList(inputCountable, createMeasurableIngredient(inputMeasurable));

                String recipeTitle = this.inOut.getInput("Enter title of recipe");
                String recipeSteps = this.inOut.getInput(promptRecipeSteps);

                Recipe recipe = recipeManager.createRecipe(recipeTitle, allIngredients, getRecipeStepsList(recipeSteps), UUID.randomUUID().toString());
                String category = this.inOut.getInput("What is the recipe category? Choose from: Chinese/French/American/Japanese/Mexican/Italian/Others (Type 'back' to discard draft and return to main menu)");
                if (category.toLowerCase().contains("back")) {
                    this.inOut.setOutput("Post deleted. Returning to main menu.");
                } else {
                    postManager.createPost(currUser, timeNow, recipe, category, UUID.randomUUID().toString());
                    this.inOut.setOutput("Post successfully created!");
                }
        }
    }

    protected void browsePost(Post selectedPost) {
        int postAction = -1;
        while (postAction != 99) {
            this.displayPost(selectedPost.getId());
            postAction = this.getPostActionInput();
            this.runPostAction(selectedPost, postAction);
        }
    }

    /**
     * Return String of countable ingredients from user input.
     *
     * @param promptCountable prompt for countable ingredients
     * @return String countable ingredients that user entered
     */
    private String getCountableIngredients(String promptCountable) {
        String countableInput = this.inOut.getInput(promptCountable);
        boolean empty = countableInput.contains("N/A") || countableInput.strip().equals("");
        if (empty && this.noMeasurableIngredients) {
            inOut.setOutput("Must enter at least one ingredient");
            countableInput = getCountableIngredients(promptCountable);
        } else if (empty) {
            return "N/A";
        }
        String[] splitIngredients = countableInput.split(",");
        String errorMessage = "That was an invalid input. Please enter ingredients again in correct format.";
        for (String ingredient: splitIngredients) {
            String strippedIngredient = ingredient.stripLeading();
            if (isInputInvalid(strippedIngredient) || strippedIngredient.split(" ").length < 2) {
                inOut.setOutput(errorMessage);
                countableInput = getCountableIngredients(promptCountable);
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
    private String getMeasurableIngredients(String promptMeasurable) {
        String measurableInput = this.inOut.getInput(promptMeasurable);
        if (measurableInput.contains("N/A") || measurableInput.strip().equals("")) {
            this.noMeasurableIngredients = true;
            return "N/A";
        } else { this.noMeasurableIngredients = false; }
        String[] splitIngredients = measurableInput.split(",");
        String errorMessage = "That was an invalid input. Please enter ingredients again in correct format.";
        for (String ingredient: splitIngredients) {
            String strippedIngredient = ingredient.stripLeading();
            if (isInputInvalid(strippedIngredient) || strippedIngredient.split(" ").length < 3) {
                inOut.setOutput(errorMessage);
                measurableInput = getMeasurableIngredients(promptMeasurable);
            }
        }
        return measurableInput;
    }

    private boolean isInputInvalid(String strippedIngredient) {
        if (!Character.isDigit(strippedIngredient.charAt(0)) || strippedIngredient.contains("/")) {
            return true;
        }
        String firstWord = strippedIngredient.split(" ")[0];
        return !firstWord.matches("^[0-9]+\\.?[0-9]*");
    }

    /**
     * Return array of prompts to user while creating a post
     *
     * @return String countable ingredients that user entered
     */
    private String[] getRecipePrompts() {
        String promptRecipeSteps = "Enter recipe steps in this comma-separated format: 'Add the water, mix flour'";
        String promptMeasurable = "Enter measurable ingredients (in grams, ounces etc) in format '50 grams sugar, 1 cup flour, etc.' or N/A if no measurable ingredients (please ensure no extra spaces between ingredient words)";
        String promptCountable = "Enter countable ingredients in format '1 lemon, 1 apple, etc.' or N/A if no countable ingredients (must have entered at least one ingredient per recipe and please write decimals instead of fractions)";
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
        if (!inputCountable.contains("N/A")) {
            String[] countable = inputCountable.split(", ");

            for (String countableIngredient : countable) {
                String[] splitIngredient = countableIngredient.split(" ");
                StringBuilder name = new StringBuilder();
                for (int i = 1; i < splitIngredient.length; i++) {
                    name.append(splitIngredient[i]).append(" ");
                }
                ingredientList.add(recipeManager.createCountableIngredient(name.toString(),
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
        if (!inputMeasurable.contains("N/A")) {
            String[] measurable = inputMeasurable.split(", ");
            ArrayList<Ingredient> ingredientList = new ArrayList<>();
            for (String measurableIngredient : measurable) {
                String[] splitIngredientParts = measurableIngredient.split(" ");
                StringBuilder name = new StringBuilder();
                for (int i = 2; i < splitIngredientParts.length; i++) {
                    name.append(splitIngredientParts[i]).append(" ");
                }
                ingredientList.add(recipeManager.createMeasurableIngredient(name.toString(),
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
        return likesDisplay.toString();
    }

    /**
     * Return the "Comments" section of post
     *
     * @param id the id of target post
     * @return String the formatted "Comments" section of post
     */
    private String getPostComments(String id) {
        String[] comments = postManager.getPostComments(id);
        StringBuilder formattedComments = new StringBuilder("Comments (").append(comments.length).append("):\n");
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
        String recipeTitle = recipeManager.getRecipeTitle(postManager.getPostRecipe(id));
        String[] recipeIngredients = recipeManager.getAllIngredients(postManager.getPostRecipe(id));
        ArrayList<String> recipeSteps = recipeManager.getRecipeSteps(postManager.getPostRecipe(id));
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
        String postedTime = postManager.getPostedTime(id).toString();
        return author + "\n" + postedTime + "\n\n";
    }

    /**
     * Run the action chosen (add a like, add a comment) on the selected post.
     * @param selectedPost The Post selected to act on.
     * @param postAction The int representing the action to do to a Post.
     */
    private void runPostAction(Post selectedPost, Integer postAction) {
        switch (postAction) {
            case 0:
                // Call PostController to add like
                this.interactPost(selectedPost.getId(), this.loginManager.getCurrUser(), false);
                break;
            case 1:
                // Call PostController to add comment
                Comment newComment = this.getUserComment();
                this.interactPost(selectedPost.getId(), newComment, true);
                break;
            case 99:
                this.inOut.setOutput("Returning to main menu.");
                break;
            default:
                this.inOut.setOutput("You entered an invalid action.");
        }
    }

    /**
     * Get the Comment that the user wants to add on a Post.
     * @return a new Comment that the user has decided to add on a Post.
     */
    private Comment getUserComment() {
        Comment comment = new Comment("", this.loginManager.getCurrUser().getId(),
                LocalDateTime.now(), UUID.randomUUID().toString());
        String commentText;
        do {
            commentText = this.inOut.getInput("Please write your comment:");
        } while (commentText.isEmpty());
        comment.setCommentText(commentText);
        return comment;
    }

    /**
     * Get the user to select an action to do to a post.
     * @return an int representing the action that the user wants to do to a Post.
     */
    private int getPostActionInput() {
        int postAction = -1;
        while (postAction < 0 || (postAction > 2 && postAction != 99)) {
            try {
                String postActionPrompt = "Please select an action for this post: \n"
                        + "0 Like the Post \n" + "1 Comment on the Post \n" + "99 Return to main menu";
                String postActionString = this.inOut.getInput(postActionPrompt);
                postAction = Integer.parseInt(postActionString);
            } catch (NumberFormatException nfe) {
                this.inOut.setOutput("You entered an invalid action input.");
            }
        }
        return postAction;
    }
}
