package entities;

public class CountableIngredient extends Ingredient {
    private float ingredientNumber;

    /**
     * Construct a MeasurableIngredient with its name, its amount,
     * and the type of measurement.
     *
     * @param name The name of the ingredient needed
     * @param number The number of the ingredient needed
     */
    public CountableIngredient(String name, float number) {
        super(name);
        this.ingredientNumber = number;
    }

    /**
     *
     * @return Return the CountableIngredient's number needed
     */
    public float getIngredientNumber(){
        return this.ingredientNumber;
    }

    /**
     * Return the full CountableIngredient as a String.
     *
     * @return Return the CountableIngredient String representation.
     */
    public String getCountableIngredient() {
        return this.ingredientNumber + getIngredientName();
    }
}
