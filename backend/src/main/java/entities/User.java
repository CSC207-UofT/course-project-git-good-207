package entities;
import java.util.HashMap;
import java.util.ArrayList;

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
    private final ArrayList<Post> posts;
    private final String id;

    // constructors
    /** Construct a User object.
     *
     * @param username this user's username
     * @param password this user's password
     * @param bio this user's bio
     * @param id this user's unique id
     */
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

    // setters
    /** Set this user's username.
     *
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /** Set this user's password.
     *
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Set this user's bio.
     *
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /** Set this user's like history
     *
     */
    public void setLikeHistory(HashMap<String, Integer> likeHistory) {
        this.likeHistory = likeHistory;
    }

    /** Add a user to this user's followers list.
     *
     */
    public void addFollower(User user) {
        this.followers.add(user);
    }

    /** Add a user to this user's following list.
     *
     */
    public void addFollowing(User user) {
        this.following.add(user);
    }

    /** Set user's followers list.
     *
     */
    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }

    /** Set user's following list.
     *
     */
    public void setFollowing(ArrayList<User> following) {
        this.following = following;
    }

    /** Add post to this user's list of posts.
     *
     */
    public void addPost(Post post) {
        this.posts.add(post);
    }

    // getters
    /** Return username of this user.
     *
     * @return username of this user.
     */
    public String getUsername() {
        return this.username;
    }

    /** Return password of this user.
     *
     * @return password of this user.
     */
    public String getPassword() {
        return this.password;
    }

    /** Return bio of this user.
     *
     * @return bio of this user.
     */
    public String getBio() {
        return this.bio;
    }

    /** Return id of this user.
     *
     * @return id of this user.
     */
    public String getId() { return this.id; }

    /** Return true if this user is same as the other user. False otherwise.
     *
     * @param other user to compare with
     * @return true if two users are the same
     */
    public boolean sameUser(User other) {
        return this.getId().equals(other.getId());
    }

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

    /**
     *
     * @return like history of this user.
     */
    public HashMap<String, Integer> getLikeHistory() {
        return this.likeHistory;
    }

    /**
     *
     * @return followers of this user.
     */
    public ArrayList<User> getFollowers() {
        return this.followers;
    }

    /**
     *
     * @return list of users that this user follows
     */
    public ArrayList<User> getFollowing() {
        return this.following;
    }

    /**
     *
     * @return posts of this user.
     */
    public ArrayList<Post> getPosts() {
        return posts;
    }
}
