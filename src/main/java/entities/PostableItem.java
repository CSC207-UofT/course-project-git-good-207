package entities;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class PostableItem {
    protected String text;
    protected LocalDateTime createdTime;
    protected String id = UUID.randomUUID().toString();

    // The ID of the User that is the author of the PostableItem
    private String authorId;

    public PostableItem(String authorId, LocalDateTime dateTime){
        this.text = "";
        this.authorId = authorId;
        this.createdTime = dateTime;
    }

    public String getAuthorId() {
        return this.authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() { return this.id; }

    public LocalDateTime getCreatedTime() { return this.createdTime; }
}
