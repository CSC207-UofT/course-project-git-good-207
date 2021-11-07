package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * A Post that Users make on Foodstagram.
 */
public class Post extends PostableItem {
    private ArrayList<User> likedUsers;
    private ArrayList<Comment> comments;
    private Recipe recipe;
    private final String category;
    private final String id;

    public Post(String authorId, LocalDateTime postedTime, Recipe recipe, String category) {
        super(authorId, postedTime);
        this.likedUsers = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.recipe = recipe;
        this.category = category;
        this.id = UUID.randomUUID().toString();
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

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
  