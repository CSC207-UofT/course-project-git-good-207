# Progress Report

## What has worked well so far
Through the implementation of dependency inversion, as well as entity, use case, controller, and user interface classes,
we feel that we have done an effective job in ensuring that our design adheres with both SOLID principles and clean
architecture. The choice of packaging by layers has been a significant contributor to making sure our design is effective
(as mentioned in the packaging section of the design document). Moreover, as in phase0, our design continues to allow us
to easily extend our database and user interface to more complex ones without requiring significant modification of lower level code.   

Additionally, utilizing GitHub tools such as pull requests and GitHub Issues, has ensured that everyone in the group
remains on the same page when it comes to program design, documentation, code style, and code organization.
We were effective in reviewing each other's pull requests, making suggestions, and resolving potential merge conflicts.

## What we have worked on and future plans

### Glen
I Implemented all actions related to User which includes: Following a user, unfollowing a user, browsing a user’s profile, browsing the currently logged-in user’s profile, browsing a user’s posts, browsing the currently logged-in user’s posts. I fixed multiple bugs, one of which was being able to follow a user that’s already followed. Finally, I added javadocs and reduced code smell to follow clean architecture and solid principles. I plan on improving the runtime of several methods in my code in the future, and if there is time, adding features on actions related to the user.

### Justin
I have been working on the functionalities of the feed. These include implementing the post filtering algorithm in a feed using the Template Method design pattern. As of now, we can filter the feed by cuisine type, number of likes, following list, and by recommendation. The filtering process by recommendation was implemented by Shawn. I also implemented the methods relating to what a user can do to a chosen post in the feed, specifically, liking and adding a comment on a post. Both FeedController and PostController were involved in creating these methods. On top of the functionalities, I handled invalid inputs in FeedController and some methods inside PostController and reorganized the methods in the Feed and Filter classes to reduce smelly codes. In the future, I plan to perhaps implement more filtering processes and improve complexity to the existing ones. I also plan to identify more smelly codes in the Feed and Filter classes and remove them.

### Yolanda
I have focused mainly on user interaction with Posts, namely PostController. I implemented methods and helpers related to creating a Post when the user chooses that option and writes a recipe to post. I managed the user input and linked it to the necessary use cases to finish post creation. In the process, I added the RecipeManager class which facilitates recipe and ingredient creation as well as retrieving recipe contents, and I also added several methods to PostManager that I needed for PostController use. I cooperated with FeedController and UserProfileController to allow liking and commenting on posts. I also implemented displaypost which formatted posts and all of its content in a readable form for the user to view when they choose a post. I have been as active as possible when reviewing PRs, communicating with my teammates, and writing Javadocs for my methods. When receiving user input when creating a post, I tried my best to handle invalid inputs and fix bugs. However, in the future I intend to test and handle more corner cases (e.g. I will work on detecting and handing the case where there's no space between numerical measure and unit of measure in an ingredient input). I would also like to improve the post display and implement delete post option.

### Eric
I was very active in the PR review process and checking in with team members to make sure everyone was on the same page about design, best practices, and deadlines. I implemented the abstraction of DatabaseManager and the addition of our MySQLController class which extends DatabaseManager. In MySQLController, I wrote all CRUD methods related to Posts as well as some database methods related to Users. I wrote unit tests where applicable for my classes, but since I have mainly been working on the Database and UI, it was not technically possible for me to write unit tests in most cases without mocking something like the database, which I may look into in the future, time permitting. I investigated whether to make DatabaseManager a Singleton (after investigation, we decided that it did not make sense), and I also fixed several bugs that came up during our quality assurance process. In the future, I plan on looking into the possibility of using Spring Boot and Angular to deploy our app to the web.

### Shawn
Primarily, I worked on implementing functionalities related to LoginManager and LoginController. I added a welcome page for the program, where a user can choose to either signup or login. I then expanded the signup functionality to the controller and UI layer, allowing a new user to actually sign up (which was not possible in phase0). I also implemented sign up/login credential restrictions (stricter limitations on what a valid password/username is) using regex and the Pattern class. Additionally, I implemented the FilterByRecommended class, as part of the Filter class template design pattern introduced by Justin, that filters the feed using recommendations based on weighted random probability and the user’s like history. Moreover, I added unit tests and documentation for the classes that I worked on (where applicable). In the future, I will consider adding a more advanced recommendation system and potentially helping with implementation of a new UI.

## Questions
