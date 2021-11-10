package use_cases;

import entities.Feed;
import entities.Post;
import entities.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class FilterByRecommended extends Filter {

    private final User user;

    /**
     * Construct a FilterByRecommended object
     * @param currentUserFeed The current user's feed
     * @param user The current user
     */
    public FilterByRecommended(Feed currentUserFeed, User user) {
        super(currentUserFeed);
        this.user = user;
    }

    @Override
    ArrayList<Post> filterFeed() {
        ArrayList<Post> recommendablePosts = getRecommendablePosts();
        ArrayList<Post> recommendedPosts = new ArrayList<>();
        while (!recommendablePosts.isEmpty()) {
            String recCategory = getRecCategory();
            Post recPost = getRecommendedPost(recommendablePosts, recCategory);

            if (recPost != null) {
                recommendedPosts.add(recPost);
                recommendablePosts.remove(recPost);
            }

        }
        return recommendedPosts;
    }

    /**
     * Return an ArrayList of Posts that can be recommended
     * Meaning they have not been previously liked/posted by user,
     * and they are part of a category that the user has liked.
     *
     * @return ArrayList of Posts that can be recommended
     */
    private ArrayList<Post> getRecommendablePosts() {
        ArrayList<Post> allPosts = this.currentUserFeed.getPosts();
        ArrayList<String> likedCategories = new ArrayList<>(this.user.getLikeHistory().keySet());

        ArrayList<Post> recommendablePosts = new ArrayList<>();
        for (Post post : allPosts) {
            if (likedCategories.contains(post.getCategory())
                    && !post.getLikedUsers().contains(this.user)
                    && !this.user.getPosts().contains(post)) {

                recommendablePosts.add(post);
            }
        }
        return recommendablePosts;
    }

    /**
     * Recommend a random post that is in recommendablePosts and is part of category
     * @param recommendablePosts ArrayList of posts that can be recommended
     * @param category String representing a post category
     * @return a Post that is recommendable and in the correct category, null if it doesn't exist
     */
    private Post getRecommendedPost(ArrayList<Post> recommendablePosts, String category) {
        ArrayList<Post> postsInCategory = new ArrayList<>();
        for (Post post : recommendablePosts) {
            if (post.getCategory().equalsIgnoreCase(category)) {
                postsInCategory.add(post);
            }
        }

        if (!postsInCategory.isEmpty()) {
            Collections.shuffle(postsInCategory);
            return postsInCategory.get(0);
        }

        return null;

    }

    /**
     * Recommend a category from user's like history using
     * a weighted random probability based on likes
     * @return the recommended category as a String
     */
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
