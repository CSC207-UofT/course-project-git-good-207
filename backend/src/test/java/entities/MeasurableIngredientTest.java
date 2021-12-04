package entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MeasurableIngredientTest {
    MeasurableIngredient measurableIngredient = new MeasurableIngredient("milk", 5.4F, "cups");

    @Test
    void testGetIngredientAmount() {
        assertEquals(5.4F, measurableIngredient.getIngredientAmount());
    }

    @Test
    void testGetIngredientMeasurementType() {
        assertEquals("cups", measurableIngredient.getIngredientMeasurementType());
    }

    @Test
    void testGetMeasurableIngredient() {
        assertEquals("5.4 cups milk", measurableIngredient.getMeasurableIngredient());
    }
}