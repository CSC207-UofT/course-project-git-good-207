# Progress Report

### What our group did well for Phase 2

### What our group did not do well for Phase 2

## Individual Progress Report

### Glen
In Phase 2, I focused on bug extermination and increasing testing coverage. With the help of Shawn's inOut dummy I was able to test my UserProfileController. Additionally, minor refactoring was done in UserManager. With the help of the team I also made the presentation according to what is expected on quercus. 
This PR fixed a bug related to viewing a user with 0 posts: https://github.com/CSC207-UofT/course-project-git-good-207/pull/132
This PR added tests for the UserProfileController class: https://github.com/CSC207-UofT/course-project-git-good-207/pull/130 

### Yolanda
In Phase 2, I improved post creation in Post Controller by taking care of more invalid input such as multiple-word ingredients and lack of spaces in ingredients.
I also refactored my code to reduce repetition using a helper. I edited and polished the Design Document in accordance with the TA feedback.

### Justin
With regards to Phase 2, I implemented unit testings for all the Filter and Feed classes. With the creation of the DummyInOut interface by Shawn, I was able to conduct testings for FeedController and tested for invalid inputs too. I increased the complexity of unit tests for Feed and FeedManager as suggested by the TA. Furthermore, I created our presentation slides with the help of some others based on our design document. 

### Eric
My main responsibility for phase 2 was creating our web UI using Angular. This was a large chunk of work, but I was able to get the UI nearly fully "functional" (i.e. I implemented all of the UI, but without any real business logic or real data, since we did not have enough time to expose a REST API from our Java app. I implemented a Feed with likeable posts, a post creation menu, a login/logout system, a user search bar with instant search (like Instagram's UI), a user profile page, and more.

### Sebastian
With respect with Phase 1 the changes I did for Phase 2 were involving the PostManager, Post, TestPostManager and MysqlController classes. In the first one having read the review from the TA on the phase 1, I notice that I could add more javadocs and improve the exceptions handling that was on the class. Similarly for TestPostManager I add more testcases in order to increase the coverage by trying to test each method at least once. On the other hand, for Post it was mostly related to javadocs likewise with MysqlController.

### Shawn
My initial focus for phase 2 was to expose a REST API from our Java app, so that we could implement a working web UI for our project.
Unfortunately, this was more complex and time-consuming than originally predicted when planning for phase two (particularly the creation of HTTP mapping and refactoring our controllers to implement a REST API).
So, though I spent a significant amount of time researching and trying to figure this out, it ended up not being implemented due to time constraints.  

Thus, the rest of my work in phase 2 was focused on testing and bug fixing the program as per TA feedback.
I created the DummyInOut class which implements our InOut interface and uses Strings arrays to keep track of input and output instead of System.out/System.in. This made it possible for us to thoroughly test, through unit test creation, the controller classes which utilized our InOut interface.
Additionally, I made unit tests for LoginController, PostController, CountableIngredient, MeasurableIngredient, PostableItem, and Recipe and worked to fix any bugs found during testing such as those in LoginController.
