package entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountableIngredientTest {
    CountableIngredient countableIngredient = new CountableIngredient("apples", 13);

    @Test
    void testGetIngredientNumber() {
        assertEquals(13, countableIngredient.getIngredientNumber());
    }

    @Test
    void testGetCountableIngredient() {
        assertEquals("13.0 apples", countableIngredient.getCountableIngredient());
    }
}