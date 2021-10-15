package entities;

public abstract class PostableItem {
    private String text;
    private String author;

    public PostableItem(String pAuthor){
        this.text = "";
        this.author = pAuthor;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
