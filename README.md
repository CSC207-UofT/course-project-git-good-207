## The Recipe App - Git Good 207

Our group, Git Good 207, decided to make a Recipe sharing app. We haven't decided on a name yet, but one possibility is "Foodstagram".

Group Members:
- Eric
- Yolanda
- Shawn
- Sebastian
- Glen
- Justin

## How to setup your MySQL Database

You need to setup a MySQL Database to get Foodstagram running. To do so, follow these steps:  
1. Make sure you git pull from the main branch.
2. Download MAMP here https://www.mamp.info/en/downloads/
3. Install MAMP
4. Open MAMP
5. Click "Preferences" in the top left of the window.
6. Go to the "Ports" tab.
7. Click the "MAMP default" button next to "Set Web & MySQL ports to:"
8. Click OK to confirm your Port options.
9. Now you should be back at the MAMP window main area (where you were after step 3)
10. Click the "Start" button in the top right to start your server.
11. Now click the "WebStart" button in the top right. This should open a tab in your web browser.
12. In the menu of this website, go to "Tools > phpMyAdmin".
13. You will get sent to another page. Here, you should see a toolbar at the top of the window that has "Databases, SQL, Status, User accounts, Export, Import, Settings, etc.". Click on "Import".
14. Click on "Choose File" and go to your Course Project directory (wherever your local copy of our app is), and select the file "setup.sql".
15. Now that "setup.sql" is loaded in, click the "Go" button in the bottom right of the webpage.
16. Open the course project in IntelliJ.
17. In the top menu, go to File > Project Structure. In the window that opens, go to Modules > Course_project > main. Open the dependencies tab on the right.
18. Once in the dependencies view, click the + button right above the word "export". Select the first option: "1 Jars and Directories", and select the mysql-connector.jar file in the "lib" folder of our course project.
19. Click apply, and when the apply button greys out, click OK.
20. Your MySQL server is now running and the Database should work. You can confirm this by running the main method in DatabaseManager.
