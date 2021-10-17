# Progress Report

## Specification Summary
Our project is a recipe saving and sharing app where the user can create and share their recipes. Users can follow others, as well as comment or like their recipe posts. Additionally, the program consists of a feed which will display other users’ recipes. Moreover, each user has a user profile consisting of a user’s bio and created recipes.

## CRC Card Summary
Firstly, the CRC model consists of entities such as User, Recipe, Ingredient, Feed, Post, and Comment. It also contains use cases such as UserManager, LoginManager, FeedManager, PostManager, and DatabaseManager which use the entities to handle and manage the main functionalities of the program. For example, PostManager creates Recipes and Posts, and it provides the comment/like function on Posts. Furthermore, we have controllers such as LoginController, UserProfileController, FeedController, and PostController which receives user input through the InOut interface which the RecipeAppInOut UI implements.


## Walkthrough/Skeleton Program Summary
The scenario walkthrough acts as a guide to demonstrate the major functionalities of our skeleton program and the ways in which it follows Clean Architecture. The walkthrough begins by running the skeleton program using the main method of RecipeAppMain. The skeleton program will then prompt you to login, which you should do using the username and password provided by the written walkthrough. The next step of the walkthrough will be to follow the main menu prompts in the skeleton program and successfully post a recipe. The walkthrough will then instruct you to use the main menu prompts to navigate to another user’s profile and follow them. You will then be brought back to the skeleton program’s main menu prompt where, as per the walkthrough, you will choose to logout.

## Summary of What Has Been Done + Future Plans
Glen has implemented various user related functionalities through his work on classes such as User, UserProfileController, and UserManager. This includes functionalities such as customizing the user profile, searching for a user, and following a user. Some of his plans for next time include implementing functionalities such as unfollowing a user and working to integrate his code with an actual database.

Yolanda implemented the Post and Recipe classes and worked on the PostController class. This includes working on the design of Post and Recipe structure and methods, as well as post creation functionalities on the controller level. Some of her plans for next time include working on developing more presenter functionalities, including the formatting and displaying of posts.

Eric mainly worked on the implementation of classes such DatabaseManager, RecipeAppController, InOut, and RecipeAppInOut. This includes working on functionalities such as accessing and updating the skeleton code database, action handling, and development of a command line user interface. Some of his plans for the future include utilizing SQLite to implement an official database system and implementing the associated controllers and payload to ensure it follows clean architecture.


Shawn worked on implementing the Comment, LoginManager, and LoginController classes. This includes working on functionalities such as logging the user in, signing a new user up, and verification of login/signup credentials through collaboration with the DatabaseManager. Some of his plans for next time include adding a user signup functionality on the user interface and controller level, adding some restrictions for what constitutes a valid password, and helping with potential feed recommendation functionalities.

Justin has implemented the Ingredient class, which includes its subclasses, MeasurableIngredient and CountableIngredient. He also worked on the Feed and FeedController classes. This involves implementing functionalities such as displaying a user’s feed with posts, setting a filter to only show posts with a certain cuisine category, as well as allowing the user to like one of the displayed posts. Some of his plans for the future include implementing functionalities such as refreshing a feed to see new posts, better formatting how a feed should be presented, and deciding which posts should be displayed at once.

Sebastian primarily worked on implementing the PostManager class. This includes working on functionalities such as creating posts, managing interactions with the post through liking/commenting, and the instantiation of measurable/countable ingredients to be included in a recipe. Some of his plans for the future include working on implementing some database functionalities as we change the skeleton code database to a different one, as well as helping with the development of a potential recommendation system in the feed.


## What Has Worked Well So Far With Our Design
We feel that as we have been implementing our code so far we have done a good job at maintaining a program design that effectively follows clean architecture and works in accordance with the SOLID principle, through the use of entity, use case, controller, user interface classes, and dependency inversion interfaces. Furthermore, when designing our program we worked to reduce coupling and maintain cohesion and were able to do so effectively. This is evident as our design allows us to easily switch from our current skeleton code database and user interface to more complex, official ones in the future without changing much code (especially on lower dependency levels), which is a major benefit of following clean architecture dependency rules.


## Questions
Our InOut interface acts as a dependency inverter. Would it
be appropriate to have it under any of the four layers (We currently
have it as an Entity), or is it a separate "in between layer"?
How should we manage this with our directories which are grouped by 
clean architecture layer?