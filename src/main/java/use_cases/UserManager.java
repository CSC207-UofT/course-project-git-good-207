package use_cases;

import java.util.ArrayList;

import entities.Post;
import entities.User;

public class UserManager {
    private final DatabaseManager databaseManager;
    private final User[] allUsers;

    /**
     * Create a UserManager with a DatabaseManager and allUsers registered with the database manager
     *
     * @param dbManager DatabaseManager that is used by UserManager
     */
    public UserManager(DatabaseManager dbManager) {
        this.databaseManager = dbManager;
        this.allUsers = this.databaseManager.getAllUsers();
    }

    /**
     * Get the User with the given ID. Return null if no user with
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
     *
     * @param user The user that will get its username updated
     * @param username The new username that the user will take
     */
    public void updateUsername(User user, String username) {
        user.setUsername(username);
    }

    /**
     * Update User's password into username given
     *
     * @param user The user that will get its username updated
     * @param new_password The new password that the user will take
     */
    public void updatePassword(User user, String new_password) {
        user.setPassword(new_password);
    }

    /**
     * Update User's bio into bio given
     *
     * @param user The user that will get its username updated
     * @param bio The new bio that the user will take
     */
    public void updateBio(User user, String bio) {
        user.setBio(bio);
    }

    /**
     * Add 1 like to the cuisine category that User liked
     *
     * @param user The current logged-in user
     * @param cuisineCategory the cuisine category of the post that the user liked
     */
    public void addLike(User user, String cuisineCategory) {
        int currentLikes = user.getLikeHistory().get(cuisineCategory);
        currentLikes = currentLikes + 1;
        user.getLikeHistory().put(cuisineCategory, currentLikes);
    }

    /**
     * Follow a user that the currently logged-in user wishes to follow
     *
     * @param currentUser the current logged-in user
     * @param wantToFollow the user that the current logged-in user wishes to follow
     * @return true if user wantToFollow is successfully added to user's following list, false otherwise
     */
    public boolean followUser(User currentUser, User wantToFollow) {
        // check whether User want_to_follow is already followed by current_user
        if (currentUser.getFollowing().contains(wantToFollow)) {
            return false;
        } else {
            // update current_user's following list: following
            ArrayList<User> updatedFollowingList = new ArrayList<>();
            updatedFollowingList.add(wantToFollow);
            updatedFollowingList.addAll(currentUser.getFollowing());
            currentUser.setFollowing(updatedFollowingList);
            return true;
        }
    }

    /**
     * unfollow User want_to_unfollow, removing want_to_unfollow from the logged-in user's following list
     *
     * @param currentUser The currently logged-in user
     * @param wantToUnfollow The user that the currently logged-in user wishes to unfollow
     * @return false if want_to_unfollow is not followed to begin with, true if action done successfully
     */
    public boolean unfollowUser(User currentUser, User wantToUnfollow) {
        String usernameWantToUnfollow = wantToUnfollow.getUsername();
        ArrayList<String> usernamesFollowed = getFollowingListUsernames(currentUser);
        if (usernamesFollowed.contains(usernameWantToUnfollow)) {
            ArrayList<User> updatedFollowingList = new ArrayList<>();
            for (User user : currentUser.getFollowing()) {
                if (!user.getUsername().equals(usernameWantToUnfollow)) {
                    updatedFollowingList.add(user);
                }
            }
            currentUser.setFollowing(updatedFollowingList);
            return true;
        }
        return false;
    }

    /**
     * Add a new follower to the currently logged-in user
     *
     * @param user The currently logged-in user
     * @param new_follower A user that just followed the currently logged-in user
     * @return true if new_follower is successfully added to user's followers list, false otherwise
     */
    public boolean addFollower(User user, User new_follower) {
        if (user.getFollowers().contains(new_follower)) {
            return false;
        } else {
            user.getFollowers().add(new_follower);
            return true;
        }
    }

    /**
     * Change username into a new username, iff new_username is not the same as the old username
     *
     * @param newUsername The new username that the user wishes to change to
     * @param user The user that wishes to change username
     * @return false if old username is the same as the new one given, true otherwise
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
     * Change password into a new password, iff the new password is not the same as the old one
     *
     * @param newPassword The new password that the user wishes to change to
     * @param user The user that wishes to change passwords
     * @return false if old password is the same as the new one, true otherwise
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
     * Change bio into a new bio, iff the new bio is not the same as the old one
     *
     * @param newBio The new bio that the user wishes to change to
     * @param user The user that wishes to change bios
     * @return false if old bio is the same as new_bio. return true otherwise
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
     * Checks if the username belongs to a user currently registered in the database
     *
     * @param userToBrowse The username to check whether it belongs to a user registered in the database
     * @return true if the username given belongs to a user in the database, false otherwise
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
     * Return the information(attributes) of another user, therefore password is not given
     *
     * @param user The user to return the information of (without the password)
     * @return the information of another user (username, bio)
     */
    public String runBrowseOtherProfile(User user) {
        User updatedUser = this.findUser(user.getUsername());
        String username = updatedUser.getUsername();
        String bio = updatedUser.getBio();
        return ("Username: " + username + "\nBio: " + bio);
    }

    /**
     * Return the User object that is affiliated to the given username, return null otherwise
     *
     * @param targetUser The user to be returned
     * @return the User object that is affiliated to the given username, return null otherwise
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
     * Return user's following list as an array list of usernames
     *
     * @param user The user to return the following list of (as an array list)
     * @return user's following list as usernames
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
     *
     * @param user The user to return the following list of (as a string)
     * @return user's following list as strings
     */
    public String getFollowingListString(User user) {
        StringBuilder followingList = new StringBuilder();
        for (String username : this.getFollowingListUsernames(user)) {
            if (followingList.length() != 0) {
                followingList.append(", ");
            }
            followingList.append(username);
        }
        return followingList.toString();
    }

    /**
     * Return user's posts as a list of posts
     * @param user The user to return the posts of (as an array list)
     * @return user's posts as a list of posts
     */
    public ArrayList<Post> getUserPosts(User user) {
        return user.getPosts();
    }

    /**
     * Return user's username after passing in user's id
     */
    public String getUsernameById(String id) {
        return getUsername(getUserById(id));
    }

    /**
     * Return user's username
     *
     * @param user the user to return the username of (as a string)
     * @return user's username
     */
    public String getUsername(User user) {
        return user.getUsername();
    }
}
