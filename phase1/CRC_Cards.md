# CRC Cards

## Entities

Class Name: User  
Parent Class: N/A  
Responsibilities: stores the user id, username, password, HashMap mapping cuisine to number of likes user put for that cuisine, a String bio, a list of followers, a list of Users the User is following, a list of Posts the User has Posted. Contains user actions such as addFollower.  
Collaborators: UserProfilePresenter, UserManager

Class Name: Recipe  
Parent Class: N/A  
Responsibilities: stores a recipe id, list of steps, a list of ingredients, and the title of the recipe. Basic functions like deleteStep.  
Collaborators: User, Post, Ingredient, CountableIngredient, MeasurableIngredient  

Class Name: Ingredient  
Parent Class: N/A  
Responsibilities: stores the name of the Ingredient  
Collaborators: Recipe, MeasurableIngredient, CountableIngredient

Class Name: MeasurableIngredient  
Parent Class: Ingredient  
Responsibilities: stores amount of ingredient, stores type of measurement, getter functions for the amount of ingredient and the type of measurement  
Collaborators: Ingredient

Class Name: CountableIngredient  
Parent Class: Ingredient  
Responsibilities: stores number of ingredient, gets the number of ingredient  
Collaborators: Ingredient

Class Name: Feed  
Parent Class: N/A  
Responsibilities: Stores a list of posts and a list of displayed posts, get/set the current displayed posts, sets a filter for posts based on cuisine category  
Collaborators: Post

Class Name: PostableItem (Abstract)  
Parent Class: N/A  
Responsibilities: Abstract class for all PostableItems. Stores and returns id of the post,
the time the post was created, and the id of the post author.  
Collaborators: Post, Comment

Class Name: Post  
Parent Class: PostableItem  
Responsibilities: Stores a list of Users ids who have liked the post, created time, comments on the post, associated User id, a Recipe, a String which is the category of the post (i.e. “Chinese”, “Indian”, “Italian”)
Collaborators: PostableItem, Comment, Recipe, User

Class Name: Comment  
Parent Class: PostableItem
Responsibilities: Stores the User id who wrote the comment, the time posted, and a string which is the contents of the comment  
Collaborators: PostableItem, Post, User



## Use Cases
Class Name: UserManager  
Parent Class: N/A  
Responsibilities: Updating a User’s following or follower list, run browse profile, change a User’s username, bio and password.  
Collaborators: User, DatabaseManager

Class Name: LoginManager  
Parent Class: N/A  
Responsibilities: verifies User credentials; if credentials are correct, it stores the currently logged in User; allows user signup, clears the current User on logout,
handles new User signup, validates username/password input    
Collaborators: User, DatabaseManager

Class Name: FeedManager  
Parent Class: N/A  
Responsibilities: handle post recommendations/obtain posts from people user follows, set the filter on the Feed  
Collaborators: Post, User, Feed

Class Name: PostManager  
Parent Class: N/A  
Responsibilities: Create new Ingredients, create a new Post, create a new Recipe, comment/like a Post  
Collaborators: Post, FeedManager, PostPresenter

Class Name: RecipeManager  
Parent Class: N/A
Responsibilities: Create new Ingredients, create new Recipes, return recipe components (steps, ingredients, title)  
Collaborators: Recipe, CountableIngredient, MeasurableIngredient  

Class Name: DatabaseManager (Abstract)  
Parent Class: N/A  
Responsibilities: declares all the CRUD methods needed for data stored in the database. Is abstract so that other classes do not need to depend on the implementation of the database (dependency inversion).    
Collaborators: LoginManager, UserManager, FeedManager, PostManager

Class Name: Filter  
Parent Class: N/A  
Responsibilities: Implements default filtering algorithm, limits the number of displayed posts in a feed, sorts displayed posts in feed from most recent  
Collaborators: FeedManager, FilterByCuisine, FilterByFollowing, FilterByLikes

Class Name: FilterByCuisine  
Parent Class: Filter  
Responsibilities: Implements a filtering algorithm that categorizes posts in a feed by cuisine type  
Collaborators: FeedManager, Filter

Class Name: FilterByFollowing  
Parent Class: Filter  
Responsibilities: Implements a filtering algorithm that categorizes posts by the current user's following list  
Collaborators: FeedManager, Filter

Class Name: FilterByLikes  
Parent Class: Filter  
Responsibilities: Implements a filtering algorithm that categorizes posts by number of likes  
Collaborators: FeedManager, Filter

Class Name: FilterByRecommended
Parent Class: Filter
Responsibilities: Implements a filtering algorithm that categorizes posts using user's like history
Collaborators: FeedManager, Filter, User

## Controllers
Class Name: LoginController  
Parent Class: N/A  
Responsibilities: Receive login/signup input, display login/signup related output  
Collaborators: LoginManager, RecipeAppController

Class Name: ShellAction (enum)  
Parent Class: N/A  
Responsibilities: defines possible user actions given in the RecipeAppController, which are passed to the appropriate controller for action handling  
Collaborators: RecipeAppController, basically all the controllers

Class Name: UserProfileController  
Parent Class:N/A  
Responsibilities: gets user profile related inputs (writing bio, following a User, liking a post on their profile), displays appropriate outputs  
Collaborators: UserProfile, UserManager, RecipeAppController

Class Name: FeedController  
Parent Class:N/A  
Responsibilities: gets Feed related inputs (setting the filter, liking a post, commenting on a post), stores a FeedManager which handles Feed related actions, convert Feed to a displayable output type, sends Feed related output  
Collaborators: FeedManager, PostManager, Feed, User, RecipeAppController

Class Name: PostController  
Parent Class:N/A  
Responsibilities: gets post creation related inputs (recipe steps, ingredients, category) and displays the appropriate outputs  
Collaborators: Recipe, PostManager, LoginManager, RecipeAppController

Class Name: RecipeAppController  
Parent Class: N/A  
Responsibilities: Is the “master” controller that calls upon the appropriate controllers (like LoginController, FeedController, etc.) based on user commands.  
Collaborators: All other Controllers

Class Name: MySQLController  
Parent Class: DatabaseManager  
Responsibilities: performs CRUD operations on data stored in the database (i.e. getAllPosts, editPost, deletePost, etc),
contains the actual database connection.  
Collaborators: DatabaseManager

Class Name: InOut (Interface)  
Parent Class: N/A  
Responsibilities: Describes the getInput and setOutput methods which allows sending typed inputs and String outputs between the UI and the controllers  
Collaborators: InOut, all the controllers

Class Name: DatabaseException (Exception)   
Parent Class: Exception  
Responsibilities: extends Exception to allow us to handle database errors.     
Collaborators: DatabaseManager, MySQLController  

## User Interface

Class Name: RecipeAppInOut  
Parent Class: InOut (implements)  
Responsibilities: Implements the InOut interface to work with the console. We are using an interface because we may create a UI later (and get rid of the shell UI).  
Collaborators: RecipeAppShell, InOut, all the controllers

Class Name: RecipeAppMain  
Parent Class: N/A  
Responsibilities: contains a main method that instantiates a RecipeAppInOut and RecipeAppController and runs the controller  
Collaborators: RecipeAppController, RecipeAppInOut  
