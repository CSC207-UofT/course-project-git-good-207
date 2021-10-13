package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A Post that Users make on Foodstagram.
 */
public class Post {
    private ArrayList<User> likedUsers;
    private ArrayList<Comment> comments;
    private User owner;
    private Recipe recipe;
    private String category;
    private LocalDateTime dateTime;

    public Post(User owner, LocalDateTime dateTime, Recipe recipe, String category){
        this.likedUsers = new ArrayList<User>();
        this.comments = new ArrayList<Comment>();
        this.owner = owner;
        this.recipe = recipe;
        this.category = category;
        this.dateTime = dateTime;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public void addLike(User user) {
        this.likedUsers.add(user);
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

    /**
     *
     * @return Return the local time the Post was created.
     */
    public LocalDateTime getDateTime(){
        return this.dateTime;
    }
}
