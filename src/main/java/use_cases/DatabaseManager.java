package use_cases;

import entities.Post;
import entities.Recipe;
import entities.User;
import java.time.LocalDateTime;

public class DatabaseManager {

    public DatabaseManager() {

    }

    public Post[] getAllPosts() {
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
        Post[] dummyPostArray = {chinesePost, koreanPost, italianPost, cursedPost};
        return dummyPostArray;
    }

    public User[] getAllUsers() {
        User justin = new User("justin", "pass");
        User sebastian = new User("sebastian", "pass");
        User glen = new User("glen", "pass");
        User shawn = new User("shawn", "pass");
        User[] dummyAllUsers = {justin, sebastian, glen, shawn};
        return dummyAllUsers;
    }
}
