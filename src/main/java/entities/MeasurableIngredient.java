package entities;

public class MeasurableIngredient extends Ingredient {
    private float ingredientAmount;
    private String ingredientMeasurementType;

    /**
     * Construct a MeasurableIngredient with its name, its amount,
     * and the type of measurement.
     *
     * @param name The name of the ingredient needed
     * @param amount The amount of the ingredient needed
     * @param measurementType The type of measurement that the ingredient uses
     */
    public MeasurableIngredient(String name, float amount, String measurementType) {
        super(name);
        this.ingredientAmount = amount;
        this.ingredientMeasurementType = measurementType;
    }

    /**
     *
     * @return Return the MeasurableIngredient's amount of measurement
     */
    public float getIngredientAmount(){
        return this.ingredientAmount;
    }

    /**
     *
     * @return Return the MeasurableIngredient's type of measurement
     */
    public String getIngredientMeasurementType(){
        return this.ingredientMeasurementType;
    }

    /**
     * Return the full MeasurableIngredient as a String.
     *
     * @return Return the MeasurableIngredient String representation.
     */
    public String getMeasurableIngredient() {
        return this.ingredientAmount + " " + this.ingredientMeasurementType + " " + getIngredientName();
    }
}
