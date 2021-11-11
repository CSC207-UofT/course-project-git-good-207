package controllers;

import org.junit.jupiter.api.Test;
import use_cases.LoginManager;
import user_interface.RecipeAppInOut;


import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {
    MySQLController mySQLController = new MySQLController();
    LoginManager loginManager = new LoginManager(mySQLController);
    RecipeAppInOut inOut = new RecipeAppInOut();
    LoginController loginController = new LoginController(inOut, loginManager);


    @Test
    void testRun() {
        loginManager.login("shawn", "1234");
        loginController.run(ShellAction.LOGOUT);


        assertNull(loginManager.getCurrUser());
    }


}

