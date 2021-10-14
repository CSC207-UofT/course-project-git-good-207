package entities;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

// import other required classes
// import list of cuisine categories (?)

/**
 * User class stores: username, password, HashMap mapping cuisine to number of likes (Integer) user put for that
 * cuisine (String), a String bio, a list of Users that follow User (followers), a list of Users the user is following,
 * a list of Posts the user has posted
 */
public class User {
    public String username, password, bio;
    //HashMap<String cuisine_category, int likes>
    public HashMap<String, Integer> like_history;
    public ArrayList<User> followers, following;
    public ArrayList<Post> posts;

    /**
     * explanation of User
     */
    // constructor
    public User(String username, String password, String bio, HashMap<String, Integer> like_history,
                ArrayList followers, ArrayList following, ArrayList posts){
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.like_history = like_history;
        this.followers = followers;
        this.following = following;
        this.posts = posts;
        // Should like_history, followers, following, and posts be initialized as an empty HashMap / ArrayList?
    }
}
