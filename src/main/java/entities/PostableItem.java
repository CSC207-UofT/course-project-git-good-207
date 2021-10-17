package entities;

public abstract class PostableItem {
    private String text;
    private String postAuthor;

    public PostableItem(String pAuthor){
        this.text = "";
        this.postAuthor = pAuthor;
    }

    public String getAuthor() {
        return this.postAuthor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
