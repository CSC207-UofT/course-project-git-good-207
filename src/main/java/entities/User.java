package entities;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.UUID;

/**
 * User class stores: username, password, HashMap mapping cuisine to number of likes (Integer) user put for that
 * cuisine (String), a String bio, a list of Users that follow User (followers), a list of Users the user is following,
 * a list of Posts the user has posted
 */
public class User {
    private String username, password, bio;
    // HashMap<String cuisineCategory, int likes>
    private HashMap<String, Integer> likeHistory;
    private ArrayList<User> followers, following;
    private ArrayList<Post> posts;
    private final String id;

    // constructors
    public User(String username, String password, String bio, String id) {
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.id = id;
        this.likeHistory = new HashMap<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public User(String username, String password, String bio, HashMap<String, Integer> likeHistory,
                ArrayList<User> followers, ArrayList<User> following, ArrayList<Post> posts){
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.likeHistory = likeHistory;
        this.followers = followers;
        this.following = following;
        this.posts = posts;
        this.id = UUID.randomUUID().toString();
    }

    // setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setLikeHistory(HashMap<String, Integer> likeHistory) {
        this.likeHistory = likeHistory;
    }

    public void addFollower(User user) {
        this.followers.add(user);
    }

    public void addFollowing(User user) {
        this.following.add(user);
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }

    public void setFollowing(ArrayList<User> following) {
        this.following = following;
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    // getters
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getBio() {
        return this.bio;
    }

    public String getId() { return this.id; }

    /**
     * Increments a like on a cuisine category.
     * @param cuisine The cuisine category to like.
     */
    public void setLike(String cuisine) {
        if (this.likeHistory.containsKey(cuisine)) {
            int likeCount = this.likeHistory.get(cuisine);
            this.likeHistory.put(cuisine, likeCount + 1);
        } else {
            this.likeHistory.put(cuisine, 1);
        }
    }

    public HashMap<String, Integer> getLikeHistory() {
        return this.likeHistory;
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
