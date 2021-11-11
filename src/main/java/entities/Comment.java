package entities;

import java.time.LocalDateTime;


public class Comment extends PostableItem {
    private String text;

    /**
     * Construct a Comment with text, the user that commented, and
     * the local time the comment was created.
     *
     * @param commentText The text that makes up the Comment
     * @param authorId The ID of the User who is the author of the comment
     * @param dateTime The local time the Comment was posted
     */
    public Comment (String commentText, String authorId, LocalDateTime dateTime, String id) {
        super(authorId, dateTime, id);
        this.text = commentText;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return Return the Comment's text
     */
    public String getCommentText(){
        return this.text;
    }
}
