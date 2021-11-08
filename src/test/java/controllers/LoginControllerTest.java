package controllers;

import entities.InOut;
import entities.ShellAction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import use_cases.LoginManager;

import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {
    MySQLController mySQLController = new MySQLController();
    LoginManager loginManager = new LoginManager(mySQLController);
    InOut inOut;
    LoginController loginController = new LoginController(inOut, loginManager);

    @BeforeAll
    static void setup(){

    }

    @Test
    void testRun() {
        loginManager.login("shawn", "1234");
        loginController.run(ShellAction.LOGOUT);


        assertNull(loginManager.getCurrUser());
    }

}