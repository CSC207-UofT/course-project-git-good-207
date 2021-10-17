package use_cases;

import java.util.HashMap;
import java.util.ArrayList;

import entities.User;
import entities.Post;
import use_cases.DatabaseManager;

public class UserManager {
    private DatabaseManager databasemanager;

    // Constructor
    public UserManager() {
        this.databasemanager = new DatabaseManager();
    }

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
    public boolean followUser(User current_user, User want_to_follow) {
        /**
         * Return false if user is already followed, true otherwise.
         */
        // check whether User want_to_follow is already followed by current_user
        if (current_user.getFollowing().contains(want_to_follow)) {
            return false;
        }
        else {
            // update current_user's following list: following
            ArrayList<User> updated_following_list = new ArrayList<>();
            updated_following_list.add(want_to_follow);
            for (User user:current_user.getFollowing()) {
                updated_following_list.add(user);
            }
            current_user.setFollowing(updated_following_list);
            return true;
        }
    }

    /**
     * unfollow User want_to_unfollow, removing want_to_unfollow from the logged-in user's following list,
     * return false if want_to_unfollow is not followed to begin with, true if action done successfully
     */
    public boolean unfollowUser(User current_user, User want_to_unfollow) {
        if (current_user.getFollowing().contains(want_to_unfollow)) {
            ArrayList<User> updated_following_list = new ArrayList<>();
            for (User user:current_user.getFollowing()) {
                updated_following_list.add(user);
            }
            updated_following_list.remove(want_to_unfollow);
            current_user.setFollowing(updated_following_list);
            return true;
        }
        return false;
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

    /**
     * Change username into new_username, return false if old username is the same as the new one given, true otherwise
     */
    public boolean changeUsername(User user, String new_username) {
        if (user.getUsername().equals(new_username)) {
            return false;
        }
        else {
            user.setUsername(new_username);
            return true;
        }
    }

    /**
     * Change password into new_password, return false if old password is the same as the new one, true otherwise
     */
    public boolean changePassword(User user, String new_password) {
        if (user.getPassword().equals(new_password)) {
            return false;
        }
        else {
            user.setPassword(new_password);
            return true;
        }
    }

    /**
     * Change bio into new_bio, return false if old bio is the same as new_bio. return true otherwise
     */
    public boolean changeBio(User user, String new_bio) {
        if (user.getBio().equals(new_bio)) {
            return false;
        }
        else {
            user.setBio(new_bio);
            return true;
        }
    }

    /**
     * return true if the username given belongs to a user in the database, false otherwise
     */
    public boolean checkUserToBrowse(String user_to_browse) {
        ArrayList<User> user_database = this.databasemanager.getUsers();
        for (User user: user_database) {
            if (user.getUsername().equals(user_to_browse)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return the information of user (username, password, bio)
     */
    public String runBrowseProfile(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String bio = user.getBio();
        return ("Username: " + username + "\nPassword: " + password + "\nBio: " + bio);
    }

    /**
     * views the information(attributes) of another user, therefore password is not given
     */
    public String runBrowseOtherProfile(User user) {
        String username = user.getUsername();
        String bio = user.getBio();
        return ("Username: " + username + "\nBio: " + bio);
    }

    /**
     * Return the User object that is affiliated to the given username, return a dummy variable that is not in the
     * database if not found
     */
    public User findUser(String target_user) {
        ArrayList<User> user_database = this.databasemanager.getUsers();
        for(User user: user_database) {
            if (user.getUsername().equals(target_user)) {
                return user;
            }
        }
        User user_bot = createUser("user_bot_username", "naughtylilfella");
        return user_bot;
    }

    /**
     * Return user's following list as User objects
     */
    public ArrayList<User> getFollowingList(User user) {
        return user.getFollowing();
    }

    /**
     * Return user's following list as usernames
     */
    public ArrayList<String> getFollowingListUsernames(User user) {
        ArrayList<User> following_list = user.getFollowing();
        ArrayList<String> following_list_usernames = new ArrayList<>();
        for (User target_user:following_list) {
            following_list_usernames.add(target_user.getUsername());
        }
        return following_list_usernames;
    }

    /**
     * Return user's following list as strings
     */
    public String getFollowingListString(User user) {
        String following_list = "";
        for (String username: this.getFollowingListUsernames(user)) {
            if (following_list == "") {
                following_list = username;
            }
            else {
                following_list = following_list + ", " + username;
            }
        }
        return following_list;
    }

    /**
     * Return user's username
     */
    public String getUsername(User user) {
        return user.getUsername();
    }
}
