package entities;
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
    private String username, password, bio;
    //HashMap<String cuisine_category, int likes>
    private HashMap<String, Integer> like_history;
    private ArrayList<User> followers, following;
    private ArrayList<Post> posts;

    /**
     * explanation of User
     */
    // constructors
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.bio = "";
        this.like_history = new HashMap<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public User(String username, String password, String bio, HashMap<String, Integer> like_history,
                ArrayList<User> followers, ArrayList<User> following, ArrayList<Post> posts){
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.like_history = like_history;
        this.followers = followers;
        this.following = following;
        this.posts = posts;
        // Should like_history, followers, following, and posts be initialized as an empty HashMap / ArrayList?
    }

    //setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setLike_history(HashMap<String, Integer> like_history) {
        this.like_history = like_history;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }

    public void setFollowing(ArrayList<User> following) {
        this.following = following;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    //getters
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getBio() {
        return this.bio;
    }

    public HashMap<String, Integer> getLike_history() {
        return this.like_history;
    }

    public ArrayList<User> getFollowers() {
        return this.followers;
    }

    public ArrayList<User> getFollowing() {
        return this.following;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }
}
