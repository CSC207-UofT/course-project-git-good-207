package entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PostableItemTest {
    private final LocalDateTime dateTime = LocalDateTime.now();
    private final PostableItem pItem = new Comment("test", "1234", dateTime, "321" );

    @Test
    void testGetAuthorId() {
        String actual = pItem.getAuthorId();
        String expected = "1234";
        assertEquals(expected, actual);
    }

    @Test
    void testGetId() {
        String actual = pItem.getId();
        String expected = "321";
        assertEquals(expected, actual);
    }

    @Test
    void testGetCreatedTime() {
        LocalDateTime actual = pItem.getCreatedTime();
        assertEquals(this.dateTime, actual);
    }
}