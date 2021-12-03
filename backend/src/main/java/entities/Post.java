package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

/**
 * A Post that Users make on Foodstagram.
 */
public class Post extends PostableItem {
    private final ArrayList<User> likedUsers;
    private final ArrayList<Comment> comments;
    private final Recipe recipe;
    private final String category;

    public Post(String authorId, LocalDateTime postedTime, Recipe recipe, String category, String id) {
        super(authorId, postedTime, id);
        this.likedUsers = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.recipe = recipe;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return this.id.equals(post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public String getId() { return this.id; }

    public LocalDateTime getTime(){
        return this.createdTime;
    }
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void addLike(User user) {
        this.likedUsers.add(user);
    }

    public ArrayList<User> getLikedUsers() {
        return this.likedUsers;
    }

    public ArrayList<Comment> getComments() {
        return this.comments;
    }

    public int getNumLikes() {
        return this.likedUsers.size();
    }

    public String getCategory() {
        return this.category;
    }

    /*Comparator for sorting the list by the number of likes of a post*/
    public static final Comparator<Post> PostLikesComparator = (p1, p2) -> {
        int p1Likes = p1.getNumLikes();
        int p2Likes = p2.getNumLikes();

        //descending order
        return p2Likes - p1Likes;

    };
}
  