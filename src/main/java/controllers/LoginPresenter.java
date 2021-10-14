package controllers;

import use_cases.LoginManager;
import use_cases.UserManager;

public class LoginPresenter {
    private LoginManager loginManager;

    public LoginPresenter(UserManager userManager){
        this.loginManager = new LoginManager(userManager);
    }

    public boolean signUp (String username, String password){
        return loginManager.signUp(username, password);
    }

    public boolean login (String username, String password){
        return loginManager.login(username, password);
    }

    public void logout(){
        loginManager.logout();
    }





}
