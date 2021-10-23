package entities;

import java.util.UUID;

public abstract class PostableItem {
    private String text;
    private User postAuthor;
    protected UUID id = UUID.randomUUID();

    public PostableItem(User author){
        this.text = "";
        this.postAuthor = author;
    }

    public User getAuthor() {
        return this.postAuthor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
