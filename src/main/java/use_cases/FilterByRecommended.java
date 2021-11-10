package use_cases;

import entities.Feed;
import entities.Post;
import entities.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class FilterByRecommended extends Filter {

    private User user;
    /**
     * Construct a Filter object and the user's feed.
     *
     * @param currentUserFeed The current user's feed
     */
    public FilterByRecommended(Feed currentUserFeed, User user) {
        super(currentUserFeed);
        this.user = user;
    }

    private void getRecommendedPost(String category) {

    }

    private String getRecCategory() {
        HashMap<String, Integer> likeHistory = user.getLikeHistory();
        int totalLikes = 0;
        for (int likes : likeHistory.values()) {
            totalLikes += likes;
        }

        int rndBound = new Random().nextInt(totalLikes);
        int sum = 0;
        for (String category : likeHistory.keySet()) {
            sum += likeHistory.get(category);
            if (rndBound < sum) {
                return category;
            }
        }
        return "";
    }
}
