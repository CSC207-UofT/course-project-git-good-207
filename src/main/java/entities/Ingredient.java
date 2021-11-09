package entities;

public class Ingredient {
    private String ingredientName;

    /**
     * Construct an Ingredient with its name.
     *
     * @param name The name of the ingredient needed
     */
    public Ingredient(String name) {
        this.ingredientName = name;
    }

    /**
     *
     * @return Return the Ingredient's name
     */
    public String getIngredientName(){
        return this.ingredientName;
    }

}
