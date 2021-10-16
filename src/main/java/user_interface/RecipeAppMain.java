
package user_interface;

import controllers.RecipeAppController;

public class RecipeAppMain {
    public static void main(String[] args) {
        RecipeAppInOut io = new RecipeAppInOut();
        RecipeAppController app = new RecipeAppController(io);
        app.run();
    }
}