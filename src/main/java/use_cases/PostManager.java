package use_cases;

import java.util.ArrayList;

/*
store all the posts existing in the app,
add a new post, comment/like a Post
 */
public class PostManager {
    // TODO: Change String to Post when the entity is created
    private ArrayList<String> posts;

    public PostManager(){
        this.posts = new ArrayList<String>();
    }
    //in case
    public void commentPost(String post){
        // TODO: Implement comment post
    }

    public void likePost(String post) {
        // TODO: implement like post
    }
    public void addPostToList(String post){
        this.posts.add(post);
    }

    public ArrayList<String> getPosts() {
        return posts;
    }


}
