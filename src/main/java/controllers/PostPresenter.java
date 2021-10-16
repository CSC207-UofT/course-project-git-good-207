package controllers;

import entities.InOut;
import entities.Post;
import entities.ShellAction;
//import use_cases.PostManager;

public class PostPresenter {
    private InOut inOut;

    public PostPresenter(InOut inOut) {
        this.inOut = inOut;
    }

    public void run(ShellAction action) {
        // TODO: get user inputs/display info based on the ShellAction
    }

    public void createPost(String steps, String ingredient, String category) {
//        PostManager.createPost(steps, ingredient, category);
    }

    public void displayPost(Post post) { /* return formatted post */ }
}
