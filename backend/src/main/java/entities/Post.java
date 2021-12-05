package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

/**
 * A Post that Users make on Foodstagram.
 * Stores a list of Users ids who have liked the post, created time, comments on the post, associated User id, a Recipe, a String which is the category of the post (i.e. “Chinese”, “Indian”, “Italian”)
 * Collaborators: Comment, Recipe, User
 */
public class Post extends PostableItem {
    private final ArrayList<User> likedUsers;
    private final ArrayList<Comment> comments;
    private final Recipe recipe;
    private final String category;

    // constructor
    public Post(String authorId, LocalDateTime postedTime, Recipe recipe, String category, String id) {
        super(authorId, postedTime, id);
        this.likedUsers = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.recipe = recipe;
        this.category = category;
    }

    /**
     * Checks whether two posts objects are equal in terms of their
     * id
     * @param o Post object we are comparing with
     * @return true iff theirs id are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return this.id.equals(post.id);
    }

    /**
     * returns the hashCode of the Post object
     * @return the integer of the hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    /**
     * @return the recipe associated with the Post
     */
    public Recipe getRecipe() {
        return this.recipe;
    }

    /**
     * @return the id we assigned when we first created
     * the Post object
     */
    public String getId() { return this.id; }

    /**
     * @return Return the time Post was created
     */
    public LocalDateTime getTime(){
        return this.createdTime;
    }

    /**
     * Saves the comment to the Post's list of comments
     * @param comment the comment to be added to the post
     */
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    /**
     * Adds the given user to the Post's list of liked users.
     * @param user The user that liked the post
     */
    public void addLike(User user) {
        this.likedUsers.add(user);
    }

    /**
     * @return  All the users that liked the Post
     */
    public ArrayList<User> getLikedUsers() {
        return this.likedUsers;
    }

    /**
     * @return all comments that have been
     * added to this Post
     */
    public ArrayList<Comment> getComments() {
        return this.comments;
    }

    /**
     * @return the total amount of likes given
     * by different users to this Post
     */
    public int getNumLikes() {
        return this.likedUsers.size();
    }

    /**
     * @return the category of this post
     */
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
  