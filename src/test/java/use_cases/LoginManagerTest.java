package use_cases;

import controllers.MySQLController;
import entities.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import java.util.UUID;


class LoginManagerTest {
    static LoginManager loginManager;
    static MySQLController dbManager;
    static User testUser;

    @BeforeAll
    static void setup() {
        dbManager = new MySQLController();
        loginManager = new LoginManager(dbManager);
        testUser = new User("test2", "test2", "", UUID.randomUUID().toString());
        dbManager.addNewUser(testUser);
    }


    @Test
    void testLoginValid() {
        boolean isValid = loginManager.login("test2", "test2");
        boolean correctCurrUser = loginManager.getCurrUser().getUsername().equals(testUser.getUsername())
                && loginManager.getCurrUser().getPassword().equals(testUser.getPassword());

        boolean actual = isValid && correctCurrUser;

        assert actual;
    }

    @Test
    void testLoginInvalid() {
        loginManager.logout();
        boolean isInvalid = !(loginManager.login("wronguser", "wrongpass"));
        boolean correctCurrUser = loginManager.getCurrUser() == null;
        boolean actual = isInvalid && correctCurrUser;

        assert actual;
    }

    @Test
    void testLogout() {
        loginManager.login("test2", "test2");
        loginManager.logout();

        User actual = loginManager.getCurrUser();

        assert actual == null;
    }

    @Test
    void testSignUpValid() {
        boolean isValid = loginManager.signUp("uniqueTestUsername2", "Testing2");
        int correctUserCount = 0;
        for (User user : dbManager.getAllUsers()) {
            if (user.getUsername().equals("uniqueTestUsername2")) {
                correctUserCount++;
            }
        }

        assert isValid && correctUserCount == 1;
    }

    @Test
    void testSignUpInvalid() {
        loginManager.signUp("anotherTestUser", "Testing2");
        boolean isValid = loginManager.signUp("anotherTestUser", "Testing2");

        //TODO also check that it wasn't added to database anyways once you can remove users
        assert !isValid;
    }

    @Test
    void getCurrUserLoggedIn() {
        loginManager.login("test2", "test2");

        String actual = loginManager.getCurrUser().getId();
        String expect = testUser.getId();

        assert actual.equals(expect);
    }

    @Test
    void getCurrUserLoggedOut() {
        loginManager.logout();

        User actual = loginManager.getCurrUser();

        assert actual == null;
    }

    @AfterAll
    static void removeTestUsers() {
        // TODO remove test users
    }
}