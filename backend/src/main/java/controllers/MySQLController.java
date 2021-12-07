package controllers;

import entities.*;
import use_cases.DatabaseManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;

public class MySQLController extends DatabaseManager {
    public MySQLController() {
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/foodstagram",
                    "admin",
                    "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save a new post to the database.
     * @param newPost The Post to save to the database.
     */
    public void addNewPost(Post newPost) {
        this.insertPostDB(newPost);
        this.insertRecipeDB(newPost.getRecipe());
        this.insertRecipeStepsDB(newPost.getRecipe());
        this.insertRecipeIngredientsDB(newPost.getRecipe());
        this.insertCommentsDB(newPost, newPost.getComments());
        this.insertLikesDB(newPost, newPost.getLikedUsers());
    }

    /**
     * Edit the Post saved in the Database
     * @param newPost The Post to save to the database.
     */
    public void editPost(Post newPost) {
        try {
            if (!postExistsInDB(newPost.getId())) {
                throw new DatabaseException("The given Post with ID " + newPost.getId() +
                        " was not found in the Database.");
            } else {
                this.updatePostsTable(newPost);
                this.updateRecipesTable(newPost.getRecipe());
                this.updateRecipesStepsTable(newPost.getRecipe());
                this.updateRecipeIngredientsTable(newPost.getRecipe());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User getUser(String userId){
        User[] users = this.getAllUsers();
        for (User user: users){
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    private boolean userHasPostsDB(User user) {
        try {
            String query = "SELECT * FROM `posts` WHERE `user_id`= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getId());
            return preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes the post from the user and starts to add new
     * ones from the user
     * @param user user object where we want to delete the
     *             posts
     */
    private void updatePostsFromUser(User user){
        // first I delete the posts from the user
        if (this.userHasPostsDB(user)){
            this.deletePostsFromUser(user);
        }

        // add the new ones
        for (Post post: user.getPosts()){
            this.addNewPost(new Post(user.getId(), post.getTime(), post.getRecipe(),
                    post.getCategory(), post.getId()));
        }
    }

    /**
     * deletes all the rows where a user is following user_id
     * @param user object that stores the information of user_id
     */
    private void deleteFollowersFromUser(User user){
        try {
            String query = "DELETE FROM `follows` WHERE `follower_id`=?";
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
     * @param user user object that stores the id to
     *             update the values
     */
    public boolean updateUser(User user){
        try {
            String query = "UPDATE `user_info` SET `username`= ?, `password`=?, `bio`=? " +
                    "WHERE `username`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getBio());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.execute();
            this.updatePostsFromUser(user);
            // update follows
            this.updateFollowers(user);
            this.updateFollows(user);
            return true;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * deletes all the information except for recipes related
     * to the given user in the database
     * @param user user object that contains information about
     *             the user
     * @return true if it could delete all the information related
     * to the user
     */
    public boolean deleteUser(User user){
        try {
            String query = "DELETE FROM `user_info` WHERE `user_id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getId());
            preparedStatement.execute();

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        this.deletePostsFromUser(user);
        this.deleteCommentsFromUser(user);
        this.deleteFollowsFromUser(user);
        this.deleteFollowersFromUser(user);
        this.deleteLikesFromUser(user);
        return true;


    }

    /**
     * deletes the posts from the table in mysql related to user
     * @param user object user storing id
     */
    private void deletePostsFromUser(User user){
        this.deleteUserDataFromPosts(user);
    }

    /**
     * deletes the likes given by the user from mysql table
     * @param user object user storing id
     */
    private void deleteLikesFromUser(User user){
        this.deleteUserDataFromLikes(user);
    }

    /**
     * deletes the comments given by the user from mysql table
     * @param user object user storing id
     */
    private void deleteCommentsFromUser(User user){
        this.deleteUserDataFromComments(user);
    }

    /**
     * deletes the rows from the mysql table in follows
     * @param user object user storing id
     */
    private void deleteFollowsFromUser(User user){
        this.deleteUserDataFromFollows(user);
    }


    /**
     * This method deletes the rows where
     * the user_id is equal to the one given
     * @param user: object user where we get the user_id
     */
    private void deleteUserDataFromPosts(User user){

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
     * This method deletes the rows where
     * the user_id is equal to the one given
     * @param user: object user where we get the user_id
     */
    private void deleteUserDataFromLikes(User user){

        try {
            String query = "DELETE FROM `likes` WHERE `user_id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getId());
            preparedStatement.execute();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method deletes the rows where
     * the user_id is equal to the one given
     * @param user: object user where we get the user_id
     */
    private void deleteUserDataFromComments(User user){

        try {
            String query = "DELETE FROM `comments` WHERE `user_id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getId());
            preparedStatement.execute();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method deletes the rows where
     * the user_id is equal to the one given
     * @param user: object user where we get the user_id
     */
    private void deleteUserDataFromFollows(User user){

        try {
            String query = "DELETE FROM `follows` WHERE `user_id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getId());
            preparedStatement.execute();

        } catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     * Check if the given post ID exists in the database.
     * @param postID the ID of the Post to check for in the database.
     * @return a boolean which is true if the Post exists.
     */
    public boolean postExistsInDB(String postID) {
        try {
            String query = "SELECT * from posts WHERE post_id=?";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, postID);

            ResultSet result = preparedStmt.executeQuery();
            return result.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete a Post in the database.
     * @param postID The ID of the Post to delete.
     */
    public void deletePost(String postID) {
        try {
            this.deleteFromPostsTable(postID);
            this.deleteFromCommentsTable(postID);
            this.deleteFromLikesTable(postID);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Adds the fact thats new_follower starts following to user
     * @param user user that stores the user id
     * @param newFollowers new follower that is starting to follow user
     */
    public void startFollowingDB(User user, User newFollowers){
        try {
            String query = "INSERT INTO `follows` (`user_id`, `follower_id`) VALUES(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, newFollowers.getId());
            preparedStatement.execute();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * deletes the follows of a user and adds it to the database
     * @param user stores the user_id
     */
    private void updateFollows(User user){
        // given the list from the user list
        this.deleteFollowsFromUser(user);
        for(User followed: user.getFollowing()){
            this.startFollowingDB(followed, user);
        }
    }


    /**
     * deletes the followers of a user and adds it to the database
     * @param user stores the user_id
     */
    private void updateFollowers(User user){
        // given the list from the user list
        this.deleteFollowersFromUser(user);
        for(User follower: user.getFollowers()){
                this.startFollowingDB(user, follower);
        }
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
            this.updateUser(newUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private HashMap<String, User> getAllLikes(){
        try {
            HashMap<String, User> postLike = new HashMap<>();
            String postQuery = "SELECT * FROM `likes` INNER JOIN `user_info` ON user_info.user_id = likes.user_id";
            ResultSet likesResult = this.connection.createStatement().executeQuery(postQuery);
            while (likesResult.next()) {
                // I need to create the user with all the information
                // (String username, String password, String bio, String id)
                String username = likesResult.getString("username");
                String password = likesResult.getString("password");
                String bio = likesResult.getString("bio");
                String userId = likesResult.getString("user_id");
                // now I just need the post ID
                String postId = likesResult.getString("post_id");
                postLike.put(postId, new User(username, password, bio, userId));
            }
            return postLike;

        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<String, User>();
        }
    }


    private HashMap<String, Comment> getAllComments(){
        try {
            HashMap<String, Comment> postComment = new HashMap<>();
            String postQuery = "SELECT * FROM `comments` INNER JOIN `user_info` ON user_info.user_id = " +
                    "comments.user_id";
            ResultSet commentsResult = this.connection.createStatement().executeQuery(postQuery);
            while (commentsResult.next()) {
                // I need to create the user with all the information
                // (String username, String password, String bio, String id)
                String userId = commentsResult.getString("user_id");
                // I need to create comment
                // (String commentText, String authorId, LocalDateTime dateTime, String id)
                String commentText = commentsResult.getString("comment_text");
                String postId = commentsResult.getString("post_id");
                LocalDateTime postedTime = commentsResult.getTimestamp("comment_time").toLocalDateTime();
                String commentId = commentsResult.getString("comment_id");
                postComment.put(postId, new Comment(commentText, userId, postedTime, commentId));
            }
            return postComment;

        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<String, Comment>();
        }
    }

    /**
     * Gets all the posts stored in the database.
     * @return an Array of all the posts stored in the database.
     */
    public Post[] getAllPosts() {
        try {
            HashMap<String, Post> postData = new HashMap<>();
            String postsQuery = "SELECT * FROM `posts` WHERE 1";
            ResultSet postsResult = this.connection.createStatement().executeQuery(postsQuery);
            while (postsResult.next()) {
                String postId = postsResult.getString("post_id");
                String userId = postsResult.getString("user_id");
                String recipeId = postsResult.getString("recipe_id");
                String category = postsResult.getString("category");
                LocalDateTime postedTime = postsResult.getTimestamp("posted_time").toLocalDateTime();
                Recipe associatedRecipe = this.getRecipe(recipeId);
                postData.put(postId, new Post(userId, postedTime, associatedRecipe, category, postId));
            }

            Post[] posts = new Post[postData.size()];
            int postsCounter = 0;
            for (String postId: postData.keySet()) {
                posts[postsCounter] = postData.get(postId);
                postsCounter ++;
            }
            // add all the comments to the post
            HashMap<String, Comment> commentsHashMap = this.getAllComments();
            for (String postId: commentsHashMap.keySet()) {
                for (Post post : posts) {
                    if (post.getId().equals(postId)) {
                        post.addComment(commentsHashMap.get(postId));
                    }
                }
            }
            // add all the likes
            HashMap<String, User> likesHashMap = this.getAllLikes();
            for (String postId: likesHashMap.keySet()) {
                for (Post post : posts) {
                    if (post.getId().equals(postId)) {
                        post.addLike(likesHashMap.get(postId));
                    }
                }
            }


            return posts;
        } catch (Exception e) {
            e.printStackTrace();
            return new Post[0];
        }
    }

    public Recipe getRecipe(String recipeID) throws SQLException, DatabaseException {
        String recipeTitle;
        String query = "SELECT title FROM recipes WHERE recipe_id = ?";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, recipeID);
        ResultSet recipeTitleResult = preparedStmt.executeQuery();
        if (recipeTitleResult.next()) {
            recipeTitle = recipeTitleResult.getString("title");
            ArrayList<String> recipeSteps = this.getRecipeSteps(recipeID);
            ArrayList<Ingredient> ingredients = this.getIngredients(recipeID);
            return new Recipe(recipeTitle, ingredients, recipeSteps, recipeID);
        } else {
            throw new DatabaseException("A recipe with the ID " + recipeID + " was not found.");
        }
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

    private ArrayList<String> getRecipeSteps(String recipeID) throws SQLException {
        String query = "SELECT step_number, step_text from recipes_steps where recipe_id=?";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, recipeID);
        ResultSet recipeStepsResult = preparedStmt.executeQuery();
        TreeMap<Integer, String> stepNumToStepText = new TreeMap<>();
        while (recipeStepsResult.next()) {
            stepNumToStepText.put(
                recipeStepsResult.getInt("step_number"),
                recipeStepsResult.getString("step_text")
            );
        }
        return new ArrayList<>(stepNumToStepText.values());
    }

    private ArrayList<Ingredient> getIngredients(String recipeID) throws SQLException {
        ResultSet recipeIngredientsResult = this.getRecipeIngredients(recipeID);
        return this.assembleIngredientsList(recipeIngredientsResult);
    }

    private ResultSet getRecipeIngredients(String recipeID) throws SQLException {
        String query = "SELECT ingredient_name, ingredient_count, ingredient_amount, ingredient_measurement " +
                "from recipe_ingredients where recipe_id=?";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, recipeID);
        return preparedStmt.executeQuery();
    }

    private ArrayList<Ingredient> assembleIngredientsList(ResultSet recipeIngredientsResult) throws SQLException {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        while (recipeIngredientsResult.next()) {
            String name = recipeIngredientsResult.getString("ingredient_name");
            int count = recipeIngredientsResult.getInt("ingredient_count");
            float amount = recipeIngredientsResult.getFloat("ingredient_amount");
            String measurement = recipeIngredientsResult.getString("ingredient_measurement");
            if (measurement != null && !measurement.isEmpty()) {
                ingredients.add(new MeasurableIngredient(name, amount, measurement));
            } else {
                ingredients.add(new CountableIngredient(name, count));
            }
        } return ingredients;
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
                    preparedStmt.setNull(4, Types.FLOAT);
                    preparedStmt.setNull(5, Types.VARCHAR);
                }
                if (ingredient instanceof MeasurableIngredient) {
                    preparedStmt.setNull(3, Types.FLOAT);
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
     * Saves the like in the database on a given post
     * @param post post object which is being given a like
     * @param user user object that is giving the like
     */
    public void likePost(Post post, User user){
        try {
                String query = "INSERT INTO `likes`(`user_id`, `post_id`, `category`) VALUES (?,?,?)";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, user.getId());
                preparedStmt.setString(2, post.getId());
                preparedStmt.setString(3, post.getCategory());
                preparedStmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the comment in the database of a given post
     * @param post post object which is being commented on
     * @param comment comment that stores the information about it
     */
    public void commentPost(Post post, Comment comment){
        try {
                String query = "INSERT INTO `comments`(`user_id`, `post_id`, `comment_time`, `comment_text`) " +
                        "VALUES (?,?,?,?)";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, comment.getAuthorId());
                preparedStmt.setString(2, post.getId());
                preparedStmt.setTimestamp(3, Timestamp.valueOf(comment.getCreatedTime()));
                preparedStmt.setString(4, comment.getCommentText());
                preparedStmt.execute();
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
                String query = "INSERT INTO `comments`(`user_id`, `post_id`, `comment_time`, `comment_text`, `comment_id`) " +
                        "VALUES (?,?,?,?,?)";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, comment.getAuthorId());
                preparedStmt.setString(2, post.getId());
                preparedStmt.setTimestamp(3, Timestamp.valueOf(comment.getCreatedTime()));
                preparedStmt.setString(4, comment.getCommentText());
                preparedStmt.setString(5, comment.getId());
                preparedStmt.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save recipe associated with a Post ID to the database.
     * @param recipe The recipe associated with the Post.
     */
    private void insertRecipeDB(Recipe recipe) {
        try {
            String query = "INSERT INTO `recipes`(`recipe_id`, `title`)" +
                    "VALUES (?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, recipe.getId());
            preparedStmt.setString(2, recipe.getTitle());

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

    private void updatePostsTable(Post newPost) throws SQLException {
        String query = "UPDATE posts SET user_id=?, recipe_id=?, category=?, posted_time=? WHERE post_id=?";

        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, newPost.getAuthorId());
        preparedStmt.setString(2, newPost.getRecipe().getId());
        preparedStmt.setString(3, newPost.getCategory());
        preparedStmt.setTimestamp(4, Timestamp.valueOf(newPost.getCreatedTime()));
        preparedStmt.setString(5, newPost.getId());

        preparedStmt.execute();
    }

    private void updateRecipesTable(Recipe newRecipe) throws SQLException {
        String query = "UPDATE recipes SET title=? WHERE recipe_id=?";

        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, newRecipe.getTitle());
        preparedStmt.setString(2, newRecipe.getId());

        preparedStmt.execute();
    }

    private void updateRecipesStepsTable(Recipe newRecipe) throws SQLException {
        String deleteQuery = "DELETE from recipes_steps where recipe_id=?";
        PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
        deleteStmt.setString(1, newRecipe.getId());
        deleteStmt.execute();

        ArrayList<String> steps = newRecipe.getSteps();
        for (int i = 0; i < steps.size(); i++) {
            String insertQuery = "INSERT into recipes_steps (recipe_id, step_number, step_text) VALUES (?,?,?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            insertStmt.setString(1, newRecipe.getId());
            insertStmt.setInt(2, i);
            insertStmt.setString(3, steps.get(i));
            insertStmt.execute();
        }
    }

    private void updateRecipeIngredientsTable(Recipe newRecipe) throws SQLException {
        String deleteQuery = "DELETE from recipe_ingredients where recipe_id=?";
        PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
        deleteStmt.setString(1, newRecipe.getId());
        deleteStmt.execute();

        ArrayList<CountableIngredient> countableIngredients = newRecipe.getCountableIngredients();
        ArrayList<MeasurableIngredient> measurableIngredients = newRecipe.getMeasurableIngredients();
        this.updateRecipeCountableIngredients(countableIngredients, newRecipe);
        this.updateRecipeMeasurableIngredients(measurableIngredients, newRecipe);
    }

    private void updateRecipeCountableIngredients(
            ArrayList<CountableIngredient> countableIngredients, Recipe newRecipe) throws SQLException {
        for (CountableIngredient countableIngredient: countableIngredients) {
            String insertQuery = "INSERT into recipe_ingredients" +
                    "(recipe_id, ingredient_name, ingredient_count) VALUES (?,?,?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            insertStmt.setString(1, newRecipe.getId());
            insertStmt.setString(2, countableIngredient.getIngredientName());
            insertStmt.setFloat(3, countableIngredient.getIngredientNumber());
            insertStmt.execute();
        }
    }

    private void updateRecipeMeasurableIngredients(
            ArrayList<MeasurableIngredient> measurableIngredients, Recipe newRecipe) throws SQLException {
        for (MeasurableIngredient measurableIngredient: measurableIngredients) {
            String insertQuery = "INSERT into recipe_ingredients (recipe_id, ingredient_name," +
                    "ingredient_amount, ingredient_measurement) VALUES (?,?,?,?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            insertStmt.setString(1, newRecipe.getId());
            insertStmt.setString(2, measurableIngredient.getIngredientName());
            insertStmt.setFloat(3, measurableIngredient.getIngredientAmount());
            insertStmt.setString(4, measurableIngredient.getIngredientMeasurementType());
            insertStmt.execute();
        }
    }

    private void deleteFromPostsTable(String postID) throws SQLException {
        String deleteQuery = "DELETE from posts where post_id=?";
        PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
        deleteStmt.setString(1, postID);
        deleteStmt.execute();
    }

    private void deleteFromLikesTable(String postID) throws SQLException {
        String deleteQuery = "DELETE from likes where post_id=?";
        PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
        deleteStmt.setString(1, postID);
        deleteStmt.execute();
    }

    private void deleteFromCommentsTable(String postID) throws SQLException {
        String deleteQuery = "DELETE from comments where post_id=?";
        PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
        deleteStmt.setString(1, postID);
        deleteStmt.execute();
    }

    public static void main(String[] arg) {
        // database demo
        DatabaseManager d = new MySQLController();
        Post[] posts = d.getAllPosts();
        for (Post post: posts) {
            System.out.println("post category is: " + post.getCategory());
            System.out.println("post recipe title is: " + post.getRecipe().getTitle());
            if (post.getRecipe().getSteps().size() > 0) {
                System.out.println("first step of post recipe is: " + post.getRecipe().getSteps().get(0));
            }
        }

        User[] users = d.getAllUsers();
        for (User user: users) {
            System.out.println("users username is: " + user.getUsername());
        }
        Post newPost = posts[0];
        Recipe newRecipe = newPost.getRecipe();
        newRecipe.setTitle("My new recipe title!" + LocalDateTime.now());
        d.editPost(posts[0]);
    }
}
