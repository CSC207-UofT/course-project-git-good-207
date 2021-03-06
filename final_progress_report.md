# Progress Report

## Individual Progress Report

### Glen
In Phase 2, I focused on bug extermination and increasing testing coverage. With the help of Shawn's inOut dummy I was able to test my UserProfileController. Additionally, minor refactoring was done in UserManager. With the help of the team I also made the presentation according to what is expected on quercus. 
In this PR I recoded my branch into Clean-architecture, added the actons: different user, follow them, check our following list and added the 2 functions in databasemanager to return dummyusers: https://github.com/CSC207-UofT/course-project-git-good-207/pull/17
In this PR I Added unit tests for UserManager and User. I decreased smell in: UserProfilePresenter, UserManager. Additionally, I added the Option to return to main menu and implemented the actions: Following a user, unfollowing a user, browsing a user, viewing a user's posts using the PostController. I also added Javadocs for all the methods that were present: https://github.com/CSC207-UofT/course-project-git-good-207/pull/67/files

### Yolanda
In Phase 2, I improved post creation in Post Controller by taking care of more invalid input such as multiple-word ingredients and lack of spaces in ingredients. I also refactored my code to reduce repetition using a helper. I helped discover more bugs in our program and fixed the problem of encountering an error when entering invalid input when choosing a post. I edited and polished the Design Document in accordance with the TA feedback and wrote out the Accessibility Report. I also implemented tests for the Post class, wrote Javadocs for various entities classes, and resolved warnings. An important pull request I made was https://github.com/CSC207-UofT/course-project-git-good-207/pull/43 because I completed PostController for creating posts and recipes which is a major feature of the program; moreover, I introduced a new RecipeManager class to facilitate this process.

### Justin
With regards to Phase 2, I implemented unit testings for all the Filter and Feed classes. With the creation of the DummyInOut interface by Shawn, I was able to conduct testings for FeedController and tested for invalid inputs too. I increased the complexity of unit tests for Feed and FeedManager as suggested by the TA. Furthermore, I created our presentation slides with the help of some others based on our design document. 
A significant PR that I made for this project is this: https://github.com/CSC207-UofT/course-project-git-good-207/pull/50. I managed to implement the Filter classes that are required for the program's Feed using the Template Method design pattern. I also implemented methods that get the user inputs that allow them to interact with the feed. I also wrote unit tests for the logic of the Feed, which confirms that the methods such as the filtering algorithms are working properly.

### Eric
My main responsibility for phase 2 was creating our web UI using Angular. This was a large chunk of work, but I was able to get the UI nearly fully "functional" (i.e. I implemented all of the UI, but without any real business logic or real data, since we did not have enough time to expose a REST API from our Java app. I implemented a Feed with likeable posts, a post creation menu, a login/logout system, a user search bar with instant search (like Instagram's UI), a user profile page, and more. A major PR that I made is https://github.com/CSC207-UofT/course-project-git-good-207/pull/109, where I bootstrapped the Angular application and created major components of the UI, such as the navbar, router, content area, and so on.

### Sebastian
With respect with Phase 1 the changes I did for Phase 2 were involving the PostManager, Post, TestPostManager and MysqlController classes. In the first one having read the review from the TA on the phase 1, I notice that I could add more javadocs and improve the exceptions handling that was on the class. Similarly for TestPostManager I add more testcases in order to increase the coverage by trying to test each method at least once. On the other hand, for Post it was mostly related to javadocs likewise with MysqlController.
On the other hand, for Phase 2 I solved the bug that didn't allowed the user to save the likes and comments across sessions. That involved working with the database (researching how to do join querys) plus the controllers in order to achieve what we think is a great feature for users: https://github.com/CSC207-UofT/course-project-git-good-207/pull/137 https://github.com/CSC207-UofT/course-project-git-good-207/pull/139

### Shawn
My initial focus for phase 2 was to expose a REST API from our Java app, so that we could implement a working web UI for our project.
Unfortunately, this was more complex and time-consuming than originally predicted when planning for phase two (particularly the creation of HTTP mapping and refactoring our controllers to implement a REST API).
So, though I spent a significant amount of time researching and trying to figure this out, it ended up not being implemented due to time constraints.  

Thus, the rest of my work in phase 2 was focused on testing and bug fixing the program as per TA feedback.
I created the DummyInOut class which implements our InOut interface and uses Strings arrays to keep track of input and output instead of System.out/System.in. This made it possible for us to thoroughly test, through unit test creation, the controller classes which utilized our InOut interface.
Additionally, I made unit tests for LoginController, PostController, CountableIngredient, MeasurableIngredient, PostableItem, and Recipe and worked to fix any bugs found during testing such as those in LoginController.  

A significant PR I made was: https://github.com/CSC207-UofT/course-project-git-good-207/pull/39  
In this PR I worked with both the use case and controller layer of clean architecture to implement a sign up functionality for the program.
I integrated the LoginManager with the DatabaseManager to check for invalid input and verify user credentials with those in the database.
Moreover, I added a welcome page which allows users to choose between logging in and signing up, increasing program usability.
In this PR: https://github.com/CSC207-UofT/course-project-git-good-207/pull/122 I also did a significant about out unit testing, ensuring our
program is free of any obvious bugs in those classes and increased test coverage.
