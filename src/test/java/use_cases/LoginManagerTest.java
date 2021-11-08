package use_cases;

import controllers.MySQLController;
import entities.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;


class LoginManagerTest {
    static LoginManager loginManager;
    static MySQLController dbManager;
    static User testUser;
    @BeforeAll
    static void setup(){
        dbManager = new MySQLController();
        loginManager = new LoginManager(dbManager);
        testUser = new User("test", "test");
        dbManager.addNewUser(testUser);
    }


    @Test
    void testLoginValid() {
        boolean isValid = loginManager.login("test", "test");
        boolean correctCurrUser = loginManager.getCurrUser().getUsername().equals(testUser.getUsername())
                                && loginManager.getCurrUser().getPassword().equals(testUser.getPassword());

        boolean actual = isValid && correctCurrUser;

        assert actual;
    }

    @Test
    void testLoginInvalid(){
        loginManager.logout();
        boolean isInvalid = !(loginManager.login("wronguser", "wrongpass"));
        boolean correctCurrUser = loginManager.getCurrUser() == null;
        boolean actual = isInvalid && correctCurrUser;

        assert actual;
    }

    @Test
    void testLogout() {
        loginManager.login("test", "test");
        loginManager.logout();

        User actual = loginManager.getCurrUser();

        assert actual == null;
    }

    @Test
    void testSignUpValid() {
        boolean isValid = loginManager.signUp("uniqueTestUsername", "test");
        int correctUserCount = 0;
        for (User user: dbManager.getAllUsers()){
            if(user.getUsername().equals("uniqueTestUsername")){
                correctUserCount++;
            }
        }

        assert isValid && correctUserCount == 1;
    }

    @Test
    void testSignUpInvalid(){
        boolean isValid = loginManager.signUp("uniqueTestUsername", "test");

        //TODO also check that it wasn't added to database anyways once you can remove users
        assert !isValid;
    }

    @Test
    void getCurrUserLoggedIn() {
        loginManager.login("test", "test");

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
    static void removeTestUsers(){
        // TODO remove test users
    }
}