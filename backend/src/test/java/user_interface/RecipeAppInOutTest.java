package user_interface;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class RecipeAppInOutTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private RecipeAppInOut recipeAppInOut;

    @BeforeEach
    public void setupRecipeAppControllerTest() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        this.recipeAppInOut = new RecipeAppInOut();
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testSetOutput() {
        this.recipeAppInOut.setOutput("test");
        String out = outContent.toString();
        assert out.contains("test");
    }
}
