package entities;

import java.time.LocalDateTime;

public abstract class PostableItem {
    protected final LocalDateTime createdTime;
    protected final String id;

    // The ID of the User that is the author of the PostableItem
    private final String authorId;

    public PostableItem(String authorId, LocalDateTime dateTime, String id){
        this.authorId = authorId;
        this.createdTime = dateTime;
        this.id = id;
    }

    public String getAuthorId() {
        return this.authorId;
    }

    public String getId() { return this.id; }

    public LocalDateTime getCreatedTime() { return this.createdTime; }
}
