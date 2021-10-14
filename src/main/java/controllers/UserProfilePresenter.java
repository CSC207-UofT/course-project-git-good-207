package controllers;

import java.util.HashMap;
import java.util.ArrayList;

import entities.User;
import entities.Post;
import use_cases.UserManager;

public class UserProfilePresenter {
    public static void main(String[] args) {

        // initialize some empty variables to create a new object User
        HashMap<String, Integer> like_history = new HashMap<String, Integer>();
        ArrayList<User> followers = new ArrayList<User>();
        ArrayList<User> following = new ArrayList<User>();
        ArrayList<Post> posts = new ArrayList<Post>();

        // create new User current_user
        User current_user = new User("Glen", "letmein", "Aspriring CS Student",
                like_history, followers, following, posts);
        /**
         * Update username by calling the update_username function in UserManager
         */
        // write a function here that update username 
    }
}
