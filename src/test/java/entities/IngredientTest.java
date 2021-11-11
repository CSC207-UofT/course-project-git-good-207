package entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IngredientTest {
    MeasurableIngredient measurableIngredient;
    CountableIngredient countableIngredient;

    @BeforeEach
    void createIngredient() {
        measurableIngredient = new MeasurableIngredient("flour", 1.5F, "cups");
        countableIngredient = new CountableIngredient("apple", 2);
    }

    @Test
    void ingredientAttributesTest() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(measurableIngredient.getIngredientName(), "flour"),
                () -> Assertions.assertEquals(measurableIngredient.getIngredientAmount(), 1.5F),
                () -> Assertions.assertEquals(measurableIngredient.getIngredientMeasurementType(), "cups"),
                () -> Assertions.assertEquals(countableIngredient.getIngredientName(), "apple"),
                () -> Assertions.assertEquals(countableIngredient.getIngredientNumber(), 2));
    }

    @Test
    void getIngredientTest() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(measurableIngredient.getMeasurableIngredient(), "1.5 cups flour"),
                () -> Assertions.assertEquals(countableIngredient.getCountableIngredient(), "2.0 apple")
        );
    }

}
