package entities;

import java.util.ArrayList;
import java.util.Collections;

public class FilterByFollowing extends Filter {
    protected User currentUser;
    /**
     * Construct a FilterByFollowing object with the user's feed.
     *
     * @param currentUserFeed The current user's feed
     */
    public FilterByFollowing(Feed currentUserFeed, User currentUser) {
        super(currentUserFeed);
        this.currentUser = currentUser;
    }

    @Override
    ArrayList<Post> filterFeed() {
        ArrayList<String> usersFollowingInStrings = this.getUsersFollowingList();
        ArrayList<Post> allPosts = this.currentUserFeed.getPosts();
        ArrayList<Post> followingPosts = new ArrayList<>();
        for (Post p : allPosts) {
            if (usersFollowingInStrings.contains(p.getAuthorId())) {
                followingPosts.add(p);
            }
        }
        Collections.shuffle(followingPosts);
        return followingPosts;
    }

    private ArrayList<String> getUsersFollowingList() {
        ArrayList<User> currentUsersFollowing = this.currentUser.getFollowing();
        ArrayList<String> usersFollowingInStrings = new ArrayList<>();
        for (User s : currentUsersFollowing) {
            usersFollowingInStrings.add(s.getId());
        }
        return usersFollowingInStrings;
    }
}
