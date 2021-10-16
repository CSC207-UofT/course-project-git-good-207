package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A Post that Users make on Foodstagram.
 */
public class Post {
    private ArrayList<User> likedUsers;
    private ArrayList<Comment> comments;
    private User owner;
    private Recipe recipe;
    private String category;
    private int idPost;
    private LocalDateTime postedTime;

    public Post(User owner, LocalDateTime postedTime, Recipe recipe, String category, int pId) {
        this.likedUsers = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.owner = owner;
        this.recipe = recipe;
        this.category = category;
        this.postedTime = postedTime;
        this.idPost = pId;
    }

    public int getIdPost() {
        return this.idPost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return idPost == post.idPost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPost);
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

    public User getOwner() {
        return this.owner;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * @return Return the local time the Post was created.
     */
    public LocalDateTime getPostedTime() {
        return this.postedTime;
    }
}
  