package use_cases;

import entities.Feed;
import entities.Post;
import use_cases.Filter;

import java.util.ArrayList;

public class FilterByLikes extends Filter {

    /**
     * Construct a FilterByLikes object with the user's feed.
     *
     * @param currentUserFeed The current user's feed
     */
    public FilterByLikes(Feed currentUserFeed) {
        super(currentUserFeed);
    }

    /**
     * Get the list of Posts filtered by a Post's number of likes from the current User's Feed's Posts.
     * @return an ArrayList of Posts filtered by the number of likes from the current User's Feed's Posts.
     */
    @Override
    ArrayList<Post> filterFeed() {
        ArrayList<Post> allPosts = this.currentUserFeed.getPosts();
        allPosts.sort(Post.PostLikesComparator);
        return allPosts;
    }
}
