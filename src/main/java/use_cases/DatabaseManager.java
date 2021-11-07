package use_cases;

import entities.*;

import java.lang.reflect.Executable;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseManager {
    //Temp user storage for loginManager testing
    private HashMap<String, String> loginInfo = new HashMap<>();
    private Connection connection;

    public DatabaseManager(){
        try {
            this.connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:8889/foodstagram",
                "admin",
                "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.loginInfo.put("eric", "123");
        this.loginInfo.put("shawn", "1234");
    }

    public HashMap<String, String> getLoginInfo(){
        return loginInfo;
    }


    /**
     * Save a new post to the database.
     * @param newPost The Post to save to the database.
     */
    public void addNewPost(Post newPost) {
        this.insertPostDB(newPost);
        this.insertRecipeDB(newPost, newPost.getRecipe());
        this.insertRecipeStepsDB(newPost.getRecipe());
        this.insertRecipeIngredientsDB(newPost.getRecipe());
        this.insertCommentsDB(newPost, newPost.getComments());
        this.insertLikesDB(newPost, newPost.getLikedUsers());
    }

    /**
     * Save a new user to the database.
     * @param newUser The new User to save to the database.
     * @return A boolean which is true if the user was successfully
     * added (there was no user with the same username). False if
     * unsuccessful.
     */
    public boolean addNewUser(User newUser) {
        try {
            String query = "INSERT INTO `user_info`(`user_id`, `username`, `password`, `bio`) " +
                    "VALUES (?,?,?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, newUser.getId());
            preparedStmt.setString(2, newUser.getUsername());
            preparedStmt.setString(3, newUser.getPassword());
            preparedStmt.setString(4, newUser.getBio());

            preparedStmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets all the posts stored in the database.
     * @return an Array of all the posts stored in the database.
     */
    public Post[] getAllPosts() {
        try {
            HashMap<String, Post> postData = new HashMap<>();
            HashMap<String, String> postIdsToRecipeIds = new HashMap<>();
            String postsQuery = "SELECT * FROM `posts` WHERE 1";
            ResultSet postsResult = this.connection.createStatement().executeQuery(postsQuery);
            while(postsResult.next()) {
                String postId = postsResult.getString("post_id");
                String userId = postsResult.getString("user_id");
                String recipeId = postsResult.getString("recipe_id");
                String category = postsResult.getString("category");
                LocalDateTime postedTime = postsResult.getTimestamp("posted_time").toLocalDateTime();
                postData.put(
                        postId,
                        new Post(userId,
                            postedTime,
                            new Recipe("", new ArrayList<>(), new ArrayList<>()),
                            category)
                    );
            }

            // TODO: add recipe data

            Post[] posts = new Post[postData.size()];
            int postsCounter = 0;
            for (String postId: postData.keySet()) {
                posts[postsCounter] = postData.get(postId);
                postsCounter ++;
            }
            return posts;
        } catch (Exception e) {
            e.printStackTrace();
            return new Post[0];
        }
    }

    public boolean updatePost(Post updatedPost) {
        return true;
    }

    public boolean updateUser(User updatedUser) {
        return true;
    }

    /**
     * Gets an array of all the Users saved in the database.
     * @return an Array of all the Users registered in the app.
     */
    public User[] getAllUsers() {
        try {
            HashMap<String, User> usersData = new HashMap<>();
            this.populateUsersWithUsersInfo(usersData);
            this.populateUsersWithLikesInfo(usersData);
            this.populateUsersWithFollowsInfo(usersData);
            this.populateUsersWithPostsInfo(usersData);

            User[] users = new User[usersData.size()];
            int usersCounter = 0;
            for (String id: usersData.keySet()) {
                users[usersCounter] = usersData.get(id);
                usersCounter ++;
            }

            return users;

        } catch (Exception e) {
            e.printStackTrace();
            return new User[0];
        }
    }

    /**
     * Populates a given HashMap of User data with Post info.
     * @param usersData The users data that needs to be populated.
     */
    private void populateUsersWithPostsInfo(HashMap<String, User> usersData) {
        Post[] allPosts = this.getAllPosts();
        for (Post post: allPosts) {
            if (usersData.containsKey(post.getAuthorId())) {
                User user = usersData.get(post.getAuthorId());
                user.addPost(post);
            }
        }
    }

    /**
     * Populates a given HashMap of User data with follower/following info from the database.
     * @param usersData The user data to populate.
     * @throws SQLException An exception that throws when there is an issue with the database.
     */
    private void populateUsersWithFollowsInfo(HashMap<String, User> usersData) throws SQLException {
        String userFollowsQuery = "SELECT * FROM `follows` WHERE 1";
        ResultSet userFollowsResult = this.connection.createStatement().executeQuery(userFollowsQuery);
        while(userFollowsResult.next()) {
            String userId = userFollowsResult.getString("user_id");
            String followerId = userFollowsResult.getString("follower_id");
            User user = usersData.get(userId);
            User follower = usersData.get(followerId);
            user.addFollower(follower);
            follower.addFollowing(user);
        }
    }

    /**
     * Populates a given HashMap of userData with likes info from the database.
     * @param usersData The users data to be populated.
     * @throws SQLException An exception thrown when there is an issue with the database.
     */
    private void populateUsersWithLikesInfo(HashMap<String, User> usersData) throws SQLException {
        String userLikesQuery = "SELECT * FROM `likes` WHERE 1";
        ResultSet userLikesResult = this.connection.createStatement().executeQuery(userLikesQuery);
        while(userLikesResult.next()) {
            String userId = userLikesResult.getString("user_id");
            String category = userLikesResult.getString("category");
            usersData.get(userId).setLike(category);
        }
    }

    /**
     * Populates a given HashMap of userData with user info from the database.
     * @param usersData The users data to be populated.
     * @throws SQLException An exception thrown when there is an issue with the database.
     */
    private void populateUsersWithUsersInfo(HashMap<String, User> usersData) throws SQLException {
        String userInfoQuery = "SELECT * FROM `user_info` WHERE 1";
        ResultSet userInfoResult = this.connection.createStatement().executeQuery(userInfoQuery);
        while(userInfoResult.next()) {
            String userId = userInfoResult.getString("user_id");
            String username = userInfoResult.getString("username");
            String password = userInfoResult.getString("password");
            String bio = userInfoResult.getString("bio");
            usersData.put(userId, new User(username, password, bio, userId));
        }
    }

    /**
     * Save the ingredients in a recipe to the database.
     * @param recipe The recipe which contains the ingredients to save.
     */
    private void insertRecipeIngredientsDB(Recipe recipe) {
        try {
            Iterable<Ingredient> ingredients = recipe.getIngredients();
            for (Ingredient ingredient: ingredients) {
                String query = "INSERT INTO `recipe_ingredients`(`recipe_id`, `ingredient_name`, `ingredient_count`, " +
                        "`ingredient_amount`, `ingredient_measurement`) " +
                        "VALUES (?,?,?,?,?)";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString (1, recipe.getId());
                preparedStmt.setString (2, ingredient.getIngredientName());
                if (ingredient instanceof CountableIngredient) {
                    preparedStmt.setFloat(3, ((CountableIngredient) ingredient).getIngredientNumber());
                }
                if (ingredient instanceof MeasurableIngredient) {
                    preparedStmt.setFloat(4, ((MeasurableIngredient) ingredient).getIngredientAmount());
                    preparedStmt.setString(5, (
                            (MeasurableIngredient) ingredient).getIngredientMeasurementType());
                }
                preparedStmt.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the steps in a recipe to the database.
     * @param recipe The recipe which contains the steps to save.
     */
    private void insertRecipeStepsDB(Recipe recipe) {
        try {
            ArrayList<String> steps = recipe.getSteps();
            for (int i = 0; i < steps.size(); i++) {
                String query = "INSERT INTO `recipes_steps`(`recipe_id`, `step_number`, `step_text`) " +
                        "VALUES (?,?,?)";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, recipe.getId());
                preparedStmt.setInt(2, i + 1);
                preparedStmt.setString(3, steps.get(i));
                preparedStmt.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the likes associated with the post in the database.
     * @param post The Post associated with the likes.
     * @param userLikeList A List of users who liked the Post.
     */
    private void insertLikesDB(Post post, Iterable<User> userLikeList) {
        try {
            for (User user: userLikeList) {
                String query = "INSERT INTO `likes`(`user_id`, `post_id`, `category`) VALUES (?,?,?)";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, user.getId());
                preparedStmt.setString(2, post.getId());
                preparedStmt.setString(3, post.getCategory());
                preparedStmt.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save comments associated with a Post to the database.
     * @param post The Post associated with the comments.
     * @param comments A List of comments associated with the Post.
     */
    private void insertCommentsDB(Post post, Iterable<Comment> comments) {
        try {
            for (Comment comment: comments) {
                String query = "INSERT INTO `comments`(`user_id`, `post_id`, `comment_time`, `comment_text`) " +
                        "VALUES (?,?,?,?)";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, comment.getAuthorId());
                preparedStmt.setString(2, comment.getId());
                preparedStmt.setTimestamp(3, Timestamp.valueOf(comment.getCreatedTime()));
                preparedStmt.setString(4, comment.getCommentText());
                preparedStmt.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save recipe associated with a Post ID to the database.
     * @param post The post associated with the Recipe.
     * @param recipe The recipe associated with the Post.
     */
    private void insertRecipeDB(Post post, Recipe recipe) {
        try {
            String query = "INSERT INTO `recipes`(`recipe_id`, `title`)" +
                    "VALUES (?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, recipe.getId());
            preparedStmt.setString(2, post.getId());

            preparedStmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save recipe associated with a Post ID to the database.
     * @param post The post to save to the database
     */
    private void insertPostDB(Post post) {
        try {
            String query = "INSERT INTO `posts`(`post_id`, `user_id`, `recipe_id`, `category`, `posted_time`)" +
                    "VALUES (?,?,?,?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, post.getId());
            preparedStmt.setString(2, post.getAuthorId());
            preparedStmt.setString(3, post.getRecipe().getId());
            preparedStmt.setString(4, post.getCategory());
            preparedStmt.setTimestamp(5, Timestamp.valueOf(post.getCreatedTime()));

            preparedStmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the user from the database if the user exists
     * @param user
     */
    public void deleteUser(User user){
        try {
            String query = "DELETE FROM `user_info` WHERE `user_id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getId());
            preparedStatement.execute();

        } catch (Exception e){
            e.printStackTrace();
        }
        // delete the posts the user had made
        this.deletePostsFromUser(user);
        //
    }

    private void deletePostsFromUser(User user){
        try {
            String query = "DELETE FROM `posts` WHERE `user_id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getId());
            preparedStatement.execute();

       } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void deleteLikesFromUser(User user){
        try {
            String query = "DELETE FROM `posts` WHERE `user_id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getId());
            preparedStatement.execute();

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Given an user objects changes all the attributes
     * such that match the ones given in the parameter
     * except the id
     * @param user
     */
    public void editUser(User user){
        try {
            String query = "UPDATE FROM `user_info` SET `username`= ?, `password`=?, `bio`=? " +
                    "WHERE `username`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getBio());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.execute();


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        // database demo
        DatabaseManager d = new DatabaseManager();
        Post[] posts = d.getAllPosts();
        for (Post post: posts) {
            System.out.println("post category is: " + post.getCategory());
        }

        User[] users = d.getAllUsers();
        for (User user: users) {
            System.out.println("users username is: " + user.getUsername());
        }
    }
}
