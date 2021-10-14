package use_cases;

import java.util.HashMap;
import java.util.ArrayList;

import entities.User;
import entities.Post;

public class UserManager {

    /**
     * Update User's username into username given
     */
    public void update_username(User user, String username) {
        user.username = username;
    }

    /**
     * Update User's password into password given
     */
    public void update_password(User user, String new_password) {
        user.password = new_password;
    }

    /**
     * Update User's bio into bio given
     */
    public void update_bio(User user, String bio) {
        user.bio = bio;
    }

    /**
     * Add 1 like to the cuisine category that User liked
     */
    public void add_like(User user, String cuisine_category) {
        int current_likes = user.like_history.get(cuisine_category);
        current_likes = current_likes + 1;
        user.like_history.put(cuisine_category, current_likes);
    }

    /**
     * follow User want_to_follow, adding want_to_follow into the logged-in user's following list
     */
    public String follow_user(User current_user, User want_to_follow) {
        // check whether User want_to_follow is already followed by current_user
        if (current_user.following.contains(want_to_follow)) {
            return "User is already followed, following list remain unchanged";
        }
        else {
            // update current_user's following list: following
            current_user.following.add(want_to_follow);
            return "User successfully followed";
        }
    }

    /**
     * add a follower to User's followers' list
     */
    public void add_follower(User user, User new_follower){
        user.followers.add(new_follower);
    }
}
