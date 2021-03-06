package use_cases.Filters;

import entities.Feed;
import entities.Post;
import entities.User;

import java.util.ArrayList;
import java.util.Collections;

public class FilterByFollowing extends Filter {
    protected final User currentUser;
    /**
     * Construct a FilterByFollowing object with the user's feed.
     *
     * @param currentUserFeed The current user's feed
     */
    public FilterByFollowing(Feed currentUserFeed, User currentUser) {
        super(currentUserFeed);
        this.currentUser = currentUser;
    }

    /**
     * Get the list of Posts filtered by the current User's following list from the current User's Feed's Posts.
     * @return an ArrayList of Posts filtered by the current User's following list from the current User's Feed's Posts.
     */
    @Override
    ArrayList<Post> filterFeed() {
        ArrayList<String> usersFollowingInStrings = this.getUsersFollowingList();
        ArrayList<Post> allPosts = this.currentUserFeed.getPosts();
        ArrayList<Post> followingPosts = new ArrayList<>();
        if (usersFollowingInStrings.size() != 0) {
            for (Post p : allPosts) {
                if (usersFollowingInStrings.contains(p.getAuthorId())) {
                    followingPosts.add(p);
                }
            }
            Collections.shuffle(followingPosts);
        }
        return followingPosts;
    }

    /**
     * Get the list of IDs of Users that the current User follow.
     * @return an ArrayList of IDs of Users that the current User follow.
     */
    public ArrayList<String> getUsersFollowingList() {
        ArrayList<User> currentUsersFollowing = this.currentUser.getFollowing();
        ArrayList<String> usersFollowingInStrings = new ArrayList<>();
        if (currentUsersFollowing.size() != 0) {
            for (User s : currentUsersFollowing) {
                usersFollowingInStrings.add(s.getId());
            }
        }
        return usersFollowingInStrings;
    }
}
