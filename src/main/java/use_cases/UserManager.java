package use_cases;

import java.util.HashMap;
import java.util.ArrayList;

import entities.User;
import entities.Post;

public class UserManager {

    /**
     * Return a User object given the user's username and password
     */
    public User createUser(String username, String password) {
        User user = new User(username, password);
        return user;
    }

    /**
     * Update User's username into username given
     */
    public void update_username(User user, String username) {
        user.setUsername(username);
    }

    /**
     * Update User's password into password given
     */
    public void update_password(User user, String new_password) {
        user.setPassword(new_password);
    }

    /**
     * Update User's bio into bio given
     */
    public void update_bio(User user, String bio) {
        user.setBio(bio);
    }

    /**
     * Add 1 like to the cuisine category that User liked
     */
    public void add_like(User user, String cuisine_category) {
        int current_likes = user.getLike_history().get(cuisine_category);
        current_likes = current_likes + 1;
        user.getLike_history().put(cuisine_category, current_likes);
//        Transforming this function into the one line below makes it less readable
//        user.getLike_history().put(cuisine_category, user.getLike_history().get(cuisine_category) + 1);
    }

    /**
     * follow User want_to_follow, adding want_to_follow into the logged-in user's following list
     */
    public boolean follow_user(User current_user, User want_to_follow) {
        /**
         * Return false if user is already followed, true otherwise.
         */
        // check whether User want_to_follow is already followed by current_user
        if (current_user.getFollowing().contains(want_to_follow)) {
            return false;
        }
        else {
            // update current_user's following list: following
            current_user.getFollowing().add(want_to_follow);
            return true;
        }
    }

    /**
     * add a follower to User's followers' list
     */
    public boolean add_follower(User user, User new_follower){
        /**
         * Return false if new_follower is already following user, true otherwise.
         */
        if (user.getFollowers().contains(new_follower)){
            return false;
        }
        else {
            user.getFollowers().add(new_follower);
            return true;
        }
    }
}
