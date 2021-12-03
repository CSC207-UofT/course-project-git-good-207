# Design Document

## Specification – Foodstagram

Our project is a recipe saving and sharing app where the user can create recipes and share their recipes to the world or their friends. The program home page is the user’s feed containing other people’s posted recipes that the current user follows, as well as recipes suggested by the app based on past recipes that the user liked. The user can also filter their feed by categories such as cuisine, only people they follow, or likes etc. The user can “like” and “comment” on recipe posts. “Liking” the post saves that recipe to the user’s “Liked” list of recipes, which the app uses in its filter feature. The user can customize their personal profile where others can see their bio, created recipes, and saved recipes.

## Major Design Decisions and Clean Architecture

* Every Post, Recipe, and User (entities) have a unique ID. This will allow us to identify them uniquely within the program.  


* We strived to adhere to the Dependency Rule, which means that our source code dependencies can only point inwards. Nothing in the inner layers can know or depend on anything from something in the outer layers. For example, we abstracted DatabaseManager (use case) and extended it with MySQLController (controller) so that our Use Case classes would not have to depend on our database implementation. We made sure that outer layers only called on the same layer or the layer just below them without skipping layers.


* We discussed making DatabaseManager a Singleton class, so we wouldn’t have to pass it in everywhere we used it. However, we realized that this would not make sense. Since we had abstracted DatabaseManager, we realized that we could not make DatabaseManager a Singleton, since a Singleton is a final class. This conflicted with the abstract definition of DatabaseManager (a class cannot be both final and abstract).


* We also discussed making LoginManager a Singleton class. In the current state of our project, we only ever use a single instance of LoginManager as only one user can be logged in at a time, and we must keep track of who the current user is. This led us to think that a singleton LoginManager would make sense. Nevertheless, after further discussion and consideration, we decided that this would not be an effective idea. Making LoginManager a Singleton class would limit us to only being able to have one instance of LoginManager. This would be very limiting if, in a hypothetical future, we wanted to expand the program to be able to have multiple users logging in at the same time (like in a real social media app), as that would require multiple instances of LoginManager to keep track of multiple current users. Thus, we decided against making LoginManager a Singleton class.


* Our codebase is definitely modular and testable without the UI, Database, et cetera. By using an abstracted InOut interface in our Controllers, this allowed us to use Spring Boot when creating a web UI while making minimal modifications to our original code. 


## SOLID Design

### Single Responsibility Principle

We made sure to remain in line with the single responsibility principle by keeping our classes single function specific,
such that they only have one major responsibility. For example, creating a distinction between “managers” and “controllers” 
not only adheres to clean architecture, but also ensures that no class is responsible for both use-case logic and adapter functionality 
(Eg. FeedManager vs FeedController). Additionally, within each layer of clean architecture we split the functionality of classes even further.
This is done so based on their specific major responsibility. For example, LoginManager is responsible for solely managing 
the login/signup logic, while UserManager is responsible for managing user specific actions such as following, liking a 
post, or changes to the user profile.

### Open-Close Principle

We ensured compliance with the open-close principle through implementation of dependency inverters. A primary example of 
this is the interface InOut, which ensures that the classes on the User Interface layer are not being depended on by 
other classes in lower layers. This keeps our user interface open for extension (through addition of a more advanced/different UI)
while also keeping the rest of the program closed for modification, as we do not need to make too many changes for it if 
we want to extend UI capabilities. 

Another example is the database classes. Through the introduction of MySQLController 
the use case classes are no longer dependent on an actual database implementation. This allows us to make changes to the 
database we use (i.e. extend it) without needing to make modifications to use case classes.

Our Ingredient class is also a great example of this principle. We have so far extended it with MeasurableIngredient and 
CountableIngredient, but our design leaves open the possibility of adding more types of ingredients in the future.

### Liskov Substitution Principle

Our consistency with the Liskov substitution principle is apparent with the implementation of the Ingredient superclass 
and the MeasurableIngredient and CountableIngredient subclasses. This relationship between the classes allows us to pass 
either MeasurableIngredients or CountableIngredients anywhere where the superclass Ingredient is mentioned (such as in 
methods/fields of Recipe or RecipeManager). 

### Interface Segregation Principle

We are effective in ensuring that no client is forced to depend on methods it doesn’t use. This is primarily evident 
through our implementation of interfaces such as InOut where we keep the contents of the interface very simple and 
responsibility specific to ensure that any class that implements it does not get unnecessary methods.

### Dependency Inversion Principle

We really took the Dependency Inversion principle to heart, and tried to abstract out dependencies. Our explanations
above regarding Clean Architecture demonstrates how our design decisions such as InOut and DatabaseManager contribute
to the DIP.

## Packaging Strategy

We decided on the strategy of packaging by Layer. Following clean architecture and SOLID principles, we decided to
divide our programs into packages based on the various layers of the program. Namely, we distributed our files into 
folders called entities, use cases, controllers, and user interface.  

Packaging by layer makes it significantly easier to ensure that we are adhering to clean architecture and forces us to
always consider which layer each class belongs to. It also allows the packaging to reflect our CRC cards.

## Design Patterns

We decided to adopt the Template Method design pattern for our feed filtering algorithm. Since we
implemented various ways (number of likes, following list, cuisine type, recommendations) to filter a user’s feed with a
default algorithm, the Template Method is appropriate to organize our code into a structure that is
easy to follow and clean without reusing the same codes in different parts of the filter classes. For any of the
filters, the following methods are common: putting a cap to the number of posts that we will show in a user’s feed and
sorting the posts from the most recent to least.

## GitHub Features

### GitHub Issues

We made use of GitHub issues as a platform for us to keep track of what each team member is working on.
At the same time, the issues allow those who need to work on the same aspect of our program to collaborate and discuss
directly and precisely on each other’s implementation plans. The issues also serve as a history of our progress should 
we need to refer back to previous implementations.

### Pull Requests

We required everyone to create a pull request for new code and we never committed code directly to our main branch.
This way, we could ensure that code worked, met our style/best practices requirements, and was designed correctly
before it could be merged into our main branch. Before merging, at least one other team member would review and approve
the pull request.

## Code Style

* We agreed on specific code style guidelines and made sure that we followed them throughout our code implementation
process. For example, we agreed to put curly braces on the same line as methods or try blocks, with a space in between 
(i.e. methodName() {).


* We made sure that all our code was well documented and easy to understand by adding Javadocs for every method, as well as additional comments within certain methods to describe specific lines of code where needed.


* We agreed on naming conventions like camelCase and descriptive variable and method names. Here is an example PR where peer feedback resulted in clearer method naming: https://github.com/CSC207-UofT/course-project-git-good-207/pull/43

## Testing

Our Phase 0 submission had only one unit test file, just to make sure our JUnit was working. In Phase 1, we aimed to
cover all methods across our codebase that made sense to test (i.e. simple, limited dependencies). We did not use a 
Mock framework like Mockito, as this was more advanced, but we may look into this later on, time permitting.

## Refactoring

* We refactored class names to be more descriptive. For example, we renamed our Presenter classes to Controllers in this PR:
https://github.com/CSC207-UofT/course-project-git-good-207/pull/24/files


* Originally, we had classes generate a unique ID for themselves when constructed, but we realized this was problematic when reconstructing Java objects based on information stored in the database - we were not able to recreate an object with an existing ID. We refactored our approach and modified many of our classes to take in an ID as a parameter in the constructor. For example, we made this change in our User object, and you can see our refactored changes in this PR: https://github.com/CSC207-UofT/course-project-git-good-207/pull/72


* We relocated some classes based on TA feedback. This is discussed further in the Code Organization section.

## Code Organization

* As mentioned previously, we decided to use the layer package strategy. This makes it a lot easier to find specific
classes within the package structure. To do so, you simply need to know what layer of clean architecture the class
belongs to, or you can reference the CRC cards (as they directly reflect our package structure).


* Based on TA feedback, we relocated some classes to better reflect their use. For example, InOut and ShellAction were originally in the Entities package, but we moved them to the Controllers package, as they were more related to user interaction logic, which we handle in our Controllers.


## Functionality

Our program does everything the specifications say it should do. We feel that our functionality is sufficiently
ambitious, as was reflected through TA feedback.

Our program can store and load state, allowing the state to persist across runs of the program. This is done through the 
use of the MySQL database which stores all significant program data, such as user info, posts, recipes, etc.
All this information is stored during program runtime and loaded when necessary. This means that if a user makes a post, 
they can access it during a different run of the program.  

Additionally, based on TA feedback, we added the option to perform no action in certain scenarios. For example, when viewing a Post,
we decided that it would make sense to allow the user to return to the previous menu without liking, commenting, etc. on the Post. 
We also tried our best to detect and deal with invalid input from the user. In phase 2, we addressed improving the flow of our program
when interacting with posts.

