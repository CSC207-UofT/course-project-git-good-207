# CRC Cards

## Entities

Class Name: User  
Parent Class: N/A  
Responsibilities: stores the username, password, HashMap mapping cuisine to number of likes user put for that cuisine, a String bio, a list of followers, a list of Users the User is following, a list of Posts the User has Posted  
Collaborators: UserProfilePresenter, UserManager

Class Name: Recipe  
Parent Class: N/A  
Responsibilities: stores a list of steps, stores a list of ingredients, stores the title of the recipe  
Collaborators: User, Post, Ingredient  

Class Name: Ingredient  
Parent Class: N/A  
Responsibilities: stores the name of the Ingredient  
Collaborators: Recipe, MeasurableIngredient, CountableIngredient  

Class Name: MeasurableIngredient  
Parent Class: Ingredient  
Responsibilities: stores amount of ingredient, stores type of measurement  
Collaborators: Ingredient

Class Name: CountableIngredient  
Parent Class: Ingredient  
Responsibilities: stores number of ingredient  
Collaborators: Ingredient

Class Name: Feed  
Parent Class: N/A  
Responsibilities: Stores a list of posts, get/set the current displayed posts,  
Collaborators: Post

Class Name: Post  
Parent Class: N/A  
Responsibilities: Stores a list of Users who have liked the post, Stores comments on the post, Has an associated User object, has a Recipe, stores a String which is the category of the post (i.e. “Chinese”, “Indian”, “Italian”)  
Collaborators: Comment, Recipe, User  

Class Name: Comment  
Parent Class: N/A  
Responsibilities: Stores the User who wrote the comment, the time posted, and a string which is the contents of the comment  
Collaborators: Post, User  

## Use Cases
Class Name: UserManager  
Parent Class: N/A  
Responsibilities: Change a User’s username and password, updating a User’s following or follower count  
Collaborators: User  

Class Name: LoginManager  
Parent Class: N/A  
Responsibilities: verifies User credentials; if credentials are correct, it stores the currently logged in User; clears the current User on logout,
handles new User signup;  
Collaborators: User, UserManager, DatabaseManager  

Class Name: FeedManager  
Parent Class: N/A  
Responsibilities: handle post recommendations/obtain posts from people user follows, set the filter on the Feed  
Collaborators: Post, User, Feed  

Class Name: PostManager  
Parent Class: N/A  
Responsibilities: store all the posts existing in the app, add a new post, comment/like a Post  
Collaborators: Post, FeedManager  

Class Name: DatabaseManager  
Parent Class: N/A  
Responsibilities: connects to the database at the start of execution and after log out edits the database with the changes made by the user.  
Collaborators: LoginManager, UserManager, FeedManager  

## Controllers
Class Name: LoginPresenter  
Parent Class: N/A  
Responsibilities: Receive login/logout/signup input, send input to LoginManager  
Collaborators: LoginManager

Class Name: UserProfilePresenter  
Parent Class:N/A  
Responsibilities: gets user profile related inputs (writing bio, following a User, liking a post on their profile), gets target User’s profile info  
Collaborators: UserProfile, UserManager 

Class Name: FeedPresenter  
Parent Class:N/A  
Responsibilities: gets feed related inputs (setting the filter, liking a post, commenting on a post)  
Collaborators: FeedManager, PostManager  

Class Name: PostPresenter  
Parent Class:N/A  
Responsibilities: gets post creation related inputs (recipe steps, ingredients, category)  
Collaborators: Recipe, PostManager, LoginManager  

## User Interface
Class Name: RecipeAppShell  
Parent Class:N/A  
Responsibilities: provides a command line user interface for input/output  
Collaborators: all the Presenters  
