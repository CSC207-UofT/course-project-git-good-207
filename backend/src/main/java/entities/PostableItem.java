package entities;

import java.time.LocalDateTime;

public abstract class PostableItem {
    protected final LocalDateTime createdTime;
    protected final String id;

    // The ID of the User that is the author of the PostableItem
    private final String authorId;

    /**
     * Construct a PostableItem object.
     * @param authorId author id of the PostableItem
     * @param dateTime time posted of the item
     * @param id unique id of PostableItem
     */
    public PostableItem(String authorId, LocalDateTime dateTime, String id){
        this.authorId = authorId;
        this.createdTime = dateTime;
        this.id = id;
    }

    /**
     *
     * @return author id of this PostableItem
     */
    public String getAuthorId() {
        return this.authorId;
    }

    /**
     *
     * @return id of this PostableItem
     */
    public String getId() { return this.id; }

    /**
     *
     * @return created time of this PostableItem
     */
    public LocalDateTime getCreatedTime() { return this.createdTime; }
}
