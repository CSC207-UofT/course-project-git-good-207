package use_cases;

import entities.Post;
import entities.Recipe;
import entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseManager {
    //Temp user storage for loginManager testing
    private HashMap<String, String> loginInfo = new HashMap<>();
    private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

    public DatabaseManager(){
        this.initDummyData();
    }

    public HashMap<String, String> getLoginInfo(){
        return loginInfo;
    }

    public void addNewPost(Post newPost) {
        this.posts.add(newPost);
    }

    /**
     * Save a new user to the database.
     * @param newUser The new User to save to the database.
     * @return A boolean which is true if the user was successfully
     * added (there was no user with the same username). False if
     * unsuccessful.
     */
    public boolean addNewUser(User newUser) {
        if (this.loginInfo.containsKey(newUser.getUsername())) {
            return false;
        }
        this.users.add(newUser);
        this.loginInfo.put(newUser.getUsername(), newUser.getPassword());
        return true;
    }

    public Post[] getAllPosts() {
        Post[] dummyPostArray = new Post[this.posts.size()];
        return this.posts.toArray(dummyPostArray);
    }

    public boolean updatePost(Post updatedPost) {
        for (int i=0; i < this.posts.size(); i++) {
            if (this.posts.get(i).equals(updatedPost)) {
                this.posts.set(i, updatedPost);
                return true;
            }
        } return false;
    }

    public boolean updateUser(User updatedUser) {
        for (int i=0; i < this.users.size(); i++) {
            if (this.users.get(i).getUsername().equals(updatedUser.getUsername())) {
                this.users.set(i, updatedUser);
                return true;
            }
        } return false;
    }

    public User[] getAllUsers() {
        User[] dummyUserArray = new User[this.users.size()];
        return this.users.toArray(dummyUserArray);
    }

    private void initDummyData() {
        this.loginInfo.put("justin", "pass");
        this.loginInfo.put("sebastian", "pass");
        this.loginInfo.put("glen", "pass");
        this.loginInfo.put("shawn", "pass");
        Post chinesePost = new Post(
                new User("justin", "password"),
                LocalDateTime.now(),
                new Recipe("Stir Fry"),
                "Chinese");
        Post koreanPost = new Post(
                new User("sebastian", "password"),
                LocalDateTime.now(),
                new Recipe("Bibimbap"),
                "Korean");
        Post italianPost = new Post(
                new User("glen", "password"),
                LocalDateTime.now(),
                new Recipe("Gelato"),
                "Italian");
        Post cursedPost = new Post(
                new User("shawn", "password"),
                LocalDateTime.now(),
                new Recipe("Peanut Butter and Onion Sandwich"),
                "Other");
        this.posts.add(chinesePost);
        this.posts.add(koreanPost);
        this.posts.add(italianPost);
        this.posts.add(cursedPost);
        User justin = new User("justin", "pass");
        User sebastian = new User("sebastian", "pass");
        User glen = new User("glen", "pass");
        User shawn = new User("shawn", "pass");
        this.users.add(justin);
        this.users.add(sebastian);
        this.users.add(glen);
        this.users.add(shawn);
    }
}
