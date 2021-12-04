package user_interface;

import controllers.InOut;
import java.util.Scanner;

public class RecipeAppInOut implements InOut {
    private final Scanner in = new Scanner(System.in);

    public RecipeAppInOut() {}

    public String getInput(String prompt) {
        System.out.println(prompt);
        return this.in.nextLine();
    }

    public void setOutput(String output) {
        System.out.println(output);
    }
}
