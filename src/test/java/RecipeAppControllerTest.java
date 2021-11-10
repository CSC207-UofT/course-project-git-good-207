import controllers.RecipeAppController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import user_interface.RecipeAppInOut;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class RecipeAppControllerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private RecipeAppController recipeAppController;

    @BeforeEach
    void setupRecipeAppControllerTest() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        this.recipeAppController = new RecipeAppController(new RecipeAppInOut());
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}
