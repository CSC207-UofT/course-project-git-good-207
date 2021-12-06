# Accessibility Report

## Principles of Universal Design

### Equitable Use

* In our app, every user is equal and no user has special privileges. We intend for all the app functions to be available for all users such as making and browsing posts. All users have same level of privacy and security in our app.
* Our design is also neutral and as appealing as possible to all users
* In the future, we could make our app comply even more with this principle by implementing a text-to-speech feature which can read out the recipes to the user if user has vision impairments
* * Our web UI (written in Angular) has alt text on browser elements (i.e. images, buttons etc.) and is compatible with software that allows vision-impaired people to browse webpages.

### Flexibility in Use

* Our program follows this principle since it is usable from the commandline and the web UI
* We also allow flexibility in how the user wants to view their feed by allowing them to filter their feed according to their preferences
* Possible way to make our program even more flexible would be to make it accessible on more platforms or make it an Android app
* Our web UI is responsive, meaning the UI is functional in a wide range of browsers (Chrome, Safari, etc.) and browser window sizes (i.e. 960x680, 1440x1024, etc. etc.)

### Simple and Intuitive Use

* All instructions when accessing app features such as creating posts are as clear and straightforward as possible
* We made use of effective prompting as well as feedback such as "Post successfully created!" after task completion
* Options after logging in are clearly indicated
* Our web UI uses simple components that are commonly used UI elements to make the user experience as intuitive as possible.

### Perceptible Information

* In the web UI, we organized the text so that "titles" would appear larger and easier to read on posts such as the subtitle of "Ingredients"
* Text is in a legible font and has a distinct contrast to the background so it's easy to read
* Our web UI has a consistent user experience for a wide range of browser zoom sizes. As a result, the user can set the browser zoom to be perceptible for them without sacrificing user experience.

### Tolerance for Error

* When completing tasks such as creating recipes or logging in, our app warns the user when there's an invalid input, and it gives the user the opportunity to try again
* In the future, to better comply with this principle, we could implement fail safe features such as "locking" the account after a certain number of login failures
* Our web UI is not fully functional as we did not have enough time to expose an API to it from our Java program, but it is designed to validate inputs like post creation with Angular's FormGroup directive.

### Low Physical Effort

* Our app definitely follows this principle because the user needs minimal physical effort to use the app, and we try to require as little unnecessary typing as possible when using it

### Size and Space for Approach and Use

* This principle is not very applicable to our program because the user probably just operates it on an electronic device by themselves, and there is not really an issue of physical posture or usage space that we can control with our app
* However, as previously mentioned, our web UI is compatible with a variety of browser window sizes and browser zooms. As a result, our app is accessible from a variety of devices. For example, a person who does not have enough desk space for a large monitor could still use our web app.

## Marketing the program

If we were to market our program, we would target teenagers, young and older adults because they are the ones who would most likely be interested in cooking and using recipes. Since they cook more, they are likely to contribute to the program by sharing recipes and get the most benefit from using the app. If we wanted to narrow down the target population, we could target people who "like to cook." However, we would most likely want to target a wider audience since it is also hard to determine who exactly "likes to cook," and it would be better to target people who are likely to cook because even those who don't particularly enjoy it have to eat and could benefit from browsing easy-to-follow recipes that cater to their taste.

As suggested by our program name, we tried to brand our program as something similar to Instagram. Seeing the popularity of Instagram, we wanted to make our program as digitally interactive as we can with features such as following other users, liking, commenting on recipe posts, and having your own feed of posts to browse. By making our program more community-based, we believe that it will capture and share the creativity of the various application users, making people's cooking experiences more exciting and worthwhile.

## Program usage by other demographics

Our program is likely to be used by the majority of people since everyone has to eat and probably cook at some point, but the app would not be used as much by very young people and very old people who are unlikely to cook for themselves or be interested in sharing recipes. It would also not be used by disabled people who cannot cook and those who are blind since our application is displayed on a screen.
