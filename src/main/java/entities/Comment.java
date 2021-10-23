package entities;

import java.time.LocalDateTime;


public class Comment extends PostableItem {
    private String commentText;
    private LocalDateTime dateTime;

    /**
     * Construct a Comment with text, the user that commented, and
     * the local time the comment was created.
     *
     * @param commentText The text that makes up the Comment
     * @param author The author of the comment
     * @param dateTime The local time the Comment was posted
     */
    public Comment (String commentText, User author, LocalDateTime dateTime) {
        super(author);
        this.commentText = commentText;
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
     * @return Return the local time the Comment was created
     */
    public LocalDateTime getDateTime(){
        return this.dateTime;
    }
}
