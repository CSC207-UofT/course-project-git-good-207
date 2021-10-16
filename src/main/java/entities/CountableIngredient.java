package entities;

public class CountableIngredient extends Ingredient {
    private int ingredientNumber;

    /**
     * Construct a MeasurableIngredient with its name, its amount,
     * and the type of measurement.
     *
     * @param name The name of the ingredient needed
     * @param number The number of the ingredient needed
     */
    public CountableIngredient(String name, int number) {
        super(name);
        this.ingredientNumber = number;
    }

    /**
     *
     * @return Return the CountableIngredient's number needed
     */
    public int getIngredientNumber(){
        return this.ingredientNumber;
    }
}
