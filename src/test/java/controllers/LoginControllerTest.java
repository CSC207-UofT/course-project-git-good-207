package controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import use_cases.LoginManager;
import user_interface.RecipeAppInOut;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {
    MySQLController mySQLController = new MySQLController();
    LoginManager loginManager = new LoginManager(mySQLController);
    RecipeAppInOut inOut= new RecipeAppInOut();
    LoginController loginController = new LoginController(inOut, loginManager);

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeAll
    static void setup(){
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testRun() {
        loginManager.login("shawn", "1234");
        loginController.run(ShellAction.LOGOUT);


        assertNull(loginManager.getCurrUser());
    }

    @Test
    void testRunWelcomePage(){
        //String welcomeActionChoice = "0";
        //String signUpUsernameInput = "shawn";
        //String signUpPasswordInput = "1234";
        //ByteArrayInputStream inContent = new ByteArrayInputStream((welcomeActionChoice + System.lineSeparator() + signUpUsernameInput + System.lineSeparator() + signUpPasswordInput).getBytes());


        loginController.runWelcomePage();
        //System.setIn(inContent);
        //assertTrue(outContent.toString().contains("Sign up successful."));




    }

}