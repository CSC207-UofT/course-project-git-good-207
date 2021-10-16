package use_cases;

import entities.Comment;
import entities.Post;
import entities.Recipe;
import entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

/*
store all the posts existing in the app,
add a new post, comment/like a Post
 */
public class PostManager {
    private ArrayList<Post> posts;

    public PostManager(){
        this.posts = new ArrayList<Post>();
    }
    //in case
    /*
        Returns true if the interaction could be done, false otherwise
        if the type is true, then the interaction is a comment
     */
    private boolean interactPost(Post post, Object obj, boolean type){
        // it looks into all the post in the stored structure
        // in order to work, the comparison of post should be
        // implemented
        for(int i = 0; i < this.posts.size(); i++)
        {
            if(this.posts.get(i).equals(post))
            {
                if(type)
                {
                    this.posts.get(i).addComment((Comment) obj);
                }
                else
                {
                    this.posts.get(i).addLike((User) obj);
                }
                return true;

            }
        }
        return false;
    }

    public void createPost(User owner, LocalDateTime postedTime, Recipe recipe, String category)
    {

        Post p = new Post(owner, postedTime, recipe, category, this.posts.size());
        this.addPostToList(p);
    }
    public boolean commentPost(Post post, Comment comment){
        return interactPost(post, comment, true);
    }

    public boolean likePost(Post post, User user) {
        return interactPost(post, user, false);
    }
    public void addPostToList(Post post){
        this.posts.add(post);
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }


}
