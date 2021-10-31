package use_cases;

import java.util.ArrayList;

import entities.Post;
import entities.User;

public class UserManager {
    private DatabaseManager databaseManager;
    private User[] allUsers;

    // Constructor
    public UserManager() {
        this.databaseManager = new DatabaseManager();
        // TODO: make allUsers stay up to date with Observable design pattern
        this.allUsers = this.databaseManager.getAllUsers();
    }

    /**
     * Return a User object given the user's username and password
     */
    public User createUser(String username, String password) {
        User user = new User(username, password);
        this.databaseManager.addNewUser(user);
        return user;
    }

    /**
     * Get the User with the given ID. Returns null if no user with
     * the given ID exists.
     *
     * @param id The ID of the User to find.
     * @return a User with the given ID.
     */
    public User getUserById(String id) {
        for (User user : this.allUsers) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Update User's username into username given
     */
    public void updateUsername(User user, String username) {
        user.setUsername(username);
    }

    /**
     * Update User's password into password given
     */
    public void updatePassword(User user, String new_password) {
        user.setPassword(new_password);
    }

    /**
     * Update User's bio into bio given
     */
    public void updateBio(User user, String bio) {
        user.setBio(bio);
    }

    /**
     * Add 1 like to the cuisine category that User liked
     */
    public void addLike(User user, String cuisineCategory) {
        int currentLikes = user.getLikeHistory().get(cuisineCategory);
        currentLikes = currentLikes + 1;
        user.getLikeHistory().put(cuisineCategory, currentLikes);
//        Transforming this function into the one line below makes it less readable
//        user.getLike_history().put(cuisine_category, user.getLike_history().get(cuisine_category) + 1);
    }

    /**
     * follow User want_to_follow, adding want_to_follow into the logged-in user's following list
     */
    public boolean followUser(User currentUser, User wantToFollow) {
        /**
         * Return false if user is already followed, true otherwise.
         */
        // check whether User want_to_follow is already followed by current_user
        if (currentUser.getFollowing().contains(wantToFollow)) {
            return false;
        } else {
            // update current_user's following list: following
            ArrayList<User> updatedFollowingList = new ArrayList<>();
            updatedFollowingList.add(wantToFollow);
            for (User user : currentUser.getFollowing()) {
                updatedFollowingList.add(user);
            }
            currentUser.setFollowing(updatedFollowingList);
            return true;
        }
    }

    /**
     * unfollow User want_to_unfollow, removing want_to_unfollow from the logged-in user's following list,
     * return false if want_to_unfollow is not followed to begin with, true if action done successfully
     */
    public boolean unfollowUser(User currentUser, User wantToUnfollow) {
        if (currentUser.getFollowing().contains(wantToUnfollow)) {
            ArrayList<User> updatedFollowingList = new ArrayList<>();
            for (User user : currentUser.getFollowing()) {
                updatedFollowingList.add(user);
            }
            updatedFollowingList.remove(wantToUnfollow);
            currentUser.setFollowing(updatedFollowingList);
            return true;
        }
        return false;
    }

    /**
     * add a follower to User's followers' list
     */
    public boolean addFollower(User user, User new_follower) {
        /**
         * Return false if new_follower is already following user, true otherwise.
         */
        if (user.getFollowers().contains(new_follower)) {
            return false;
        } else {
            user.getFollowers().add(new_follower);
            return true;
        }
    }

    /**
     * Change username into new_username, return false if old username is the same as the new one given, true otherwise
     */
    public boolean changeUsername(User user, String newUsername) {
        if (user.getUsername().equals(newUsername)) {
            return false;
        } else {
            user.setUsername(newUsername);
            return true;
        }
    }

    /**
     * Change password into new_password, return false if old password is the same as the new one, true otherwise
     */
    public boolean changePassword(User user, String newPassword) {
        if (user.getPassword().equals(newPassword)) {
            return false;
        } else {
            user.setPassword(newPassword);
            return true;
        }
    }

    /**
     * Change bio into new_bio, return false if old bio is the same as new_bio. return true otherwise
     */
    public boolean changeBio(User user, String newBio) {
        if (user.getBio().equals(newBio)) {
            return false;
        } else {
            user.setBio(newBio);
            this.databaseManager.updateUser(user);
            return true;
        }
    }

    /**
     * return true if the username given belongs to a user in the database, false otherwise
     */
    public boolean checkUserToBrowse(String userToBrowse) {
        User[] userDatabase = this.databaseManager.getAllUsers();
        for (User user : userDatabase) {
            if (user.getUsername().equals(userToBrowse)) {
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
        User updatedUser = this.findUser(user.getUsername());
        String username = updatedUser.getUsername();
        String bio = updatedUser.getBio();
        return ("Username: " + username + "\nBio: " + bio);
    }

    /**
     * Return the User object that is affiliated to the given username, return null otherwise
     */
    public User findUser(String targetUser) {
        User[] users = this.databaseManager.getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(targetUser)) {
                return user;
            }
        }
        return null;
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
        ArrayList<User> followingList = user.getFollowing();
        ArrayList<String> followingListUsernames = new ArrayList<>();
        for (User targetUser : followingList) {
            followingListUsernames.add(targetUser.getUsername());
        }
        return followingListUsernames;
    }

    /**
     * Return user's following list as strings
     */
    public String getFollowingListString(User user) {
        String followingList = "";
        for (String username : this.getFollowingListUsernames(user)) {
            if (followingList == "") {
                followingList = username;
            } else {
                followingList = followingList + ", " + username;
            }
        }
        return followingList;
    }

    /**
     * Return user's username
     */
    public String getUsername(User user) {
        return user.getUsername();
    }
}
