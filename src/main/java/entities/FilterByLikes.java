package entities;

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

    @Override
    ArrayList<Post> filterFeed() {
        ArrayList<Post> allPosts = this.currentUserFeed.getPosts();
        allPosts.sort(Post.PostLikesComparator);
        return allPosts;
    }
}
