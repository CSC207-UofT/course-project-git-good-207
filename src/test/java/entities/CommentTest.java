package entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {
    private final Comment comment = new Comment("Comment text", "id", LocalDateTime.now(), "id");

    @Test
    void testGetCommentText() {
        String actual = comment.getCommentText();
        String expected = "Comment text";

        assertEquals(expected, actual);
    }

    @Test
    void testSetCommentText() {
        comment.setCommentText("New text");
        String actual = comment.getCommentText();
        String expected = "New text";

        assertEquals(expected, actual);
    }
}