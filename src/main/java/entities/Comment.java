package entities;

import java.time.LocalDateTime;


public class Comment {
    private String commentText;
    private User commenter;
    private LocalDateTime dateTime;

    /**
     * Construct a Comment with text, the user that commented, and
     * the local time the comment was created.
     *
     * @param commentText The text that makes up the Comment
     * @param commenter The user that commented
     * @param dateTime The local time the Comment was posted
     */
    public Comment (String commentText, User commenter, LocalDateTime dateTime){
        this.commentText = commentText;
        this.commenter = commenter;
        this.dateTime = dateTime;
    }

    /**
     *
     * @return Return the Comment's text
     */
    public String getCommentText(){
        return this.commentText;
    }

    /**
     *
     * @return Return the User that made the Comment
     */
    public User getCommenter(){
        return this.commenter;
    }

    /**
     *
     * @return Return the local time the Comment was created
     */
    public LocalDateTime getDateTime(){
        return this.dateTime;
    }
}
