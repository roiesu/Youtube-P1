# Youtube project
This project was made as a final project for a advancded programming course. Its goal was to create an application resembeling youtube in android and react.
Jira link: https://roiesu3.atlassian.net/jira/software/projects/BIS/boards/2/timeline

For further information check the wiki pages

[setup the environment](./wiki/setup.md)

[android app demo](./wiki/react_app.md)

[react app demo](./wiki/android_app.md)

## Important note

At first, we started working only Oren and Roie. Yuval was assigned to another group that resigned from the course so she joined us later on. Yuval is also in active reserve duty
therefore during the work process, there were times where she was not available and she couldn't work as much as we did. Therefore, part of the merges and commits were done without her "agreement". When she joined us, we discussed with Hemi and according to his recommendation we gave her parts of the projects which were on her own responsibility, so she can learn and contribute to the projects in her own time.
Regarding the late submit, we tried our best to balance the progress in the project, letting Yuval close the gap and learn while in reserve duty, and also Roie who was in reserve during last semester's exam period and therefore had to take exams at the beginning of the current semester.
Thanks for your understanding.

# Part 1

Github link: https://github.com/roiesu/Youtube-P1

## Running the application

### React

To run the react project, first you need to go to the "client" directory. When running in the first time, write in the terminal "npm i". When that finishes, write "npm start" to run the react app. For later uses you can skip "npm i".

### Android

For the android, you need to either open the android emulator or install the application in your mobile device, and press the start button in the android studio work frame.

## Workflow

First we started by creating a scrum sprint for the react part, where we created the basic tasks needed for the entire react part.
We split the job equally so both of us worked on the initial part of the project.
From the second day on, we tried to follow the following work pattern:

1. Short zoom meeting in the start of every work day, where we discussed yesterday's advancements and defined missions for the upcoming day.
2. During the day, we discussed on changing times about issues and problems that came up.
3. For every page we :
   a. first layed down the basic foundation on the page and made sure the functionality works.
   b. Added minimal styling.
   c. Finished up the functionality.
   d. Added tasks to the jira for each problem that came up.
   e. Testing how the page fits the entire application.
   f. Finishing touches on the styling.
   When finished all the pages, we ran some tests, and made changes according to the needs.
   When we finished with the react part completely, we started working on the android part, on the same way.

# Part 2

Github link: https://github.com/roiesu/Youtube-P1/tree/main-part-two

## Running the application

1. Clone the repository locally to your computer.
2. Write cd (enter the cloned repo path here) in the command line.
3. At the first time running the application write "npm i" in the command line to install all the needed dependencies.
4. create a file named ".env" in the server directory and add the given lines:
   1. JWT_SECRET = "(any text you want)"
   2. MONGODB_CONNECTION_URL = "(the connection url to your mongodb server)"
   3. MONGODB_DATABASE = "(the name of your mongodb database)"
5. In the directory "db" you can find a copy of the data exported from our database. Import it to your mongodb database for your convenience.
6. For now on, to run the program type "npm start" at the command line to start the application. To get to the web app go to "http://localhost:8080/.

## Workflow

First we created a model for each data collection. We used the model to have each controllers to interact with the database, and to send queries to it. Moreover the controllers are made to handle requests sent from the routers, and send them a response. Those routers are made to arrange the the requests sent to the api.

After that, we connected the server to the React code we wrote in the first part of the project. To do this, we moved all local JSON data files into the mongodb collections, and converted each request to the local JSON files throughout the program into server requests. Most of these actions were CRUD operations that helped us achieve the required functionality.

During this part of the project, new requirements were added, such as the ability to edit user details, the option to delete a user from the program, and more. Therefore, we also implemented these changes by adding new pages to the application to support the required operations.

Our overall workflow was similar to the first part of the project. We defined tasks in Jira and then assigned them. At regular intervals, we held Zoom meetings where each team member updated on their progress. We consulted with each other, updated the task list and the work distribution accordingly, and continued working until this part of the project was completed.

# part 3

## Running the apllication

The first steps are the same as for part2.
After performing these steps, open your cmd and write ipconfig. In the second section, you can find your IPv4 Address. copy it.
Open the android_client directory and go to this file android_client\app\src\main\res\values\strings.xml
In there you will find two string tags named BaseUrlApi and BaseUrlMedia. replace [Enter Your Ip Here] with the IPv4 Address you copied.
Now open your emulator and run the application. make sure that your nodejs server is running.

## workflow

First, since two of the three team members are active reservists, we received an extension for this part until the end of October.
Second, we began work on the third part of the project together from a single computer in order to create a shared foundation. As a result, the initial commits for this section were all made from the same computer. After establishing this foundation, we returned to our usual workflow, which includes task division, each team member working on their assigned tasks, status updates through Zoom meetings, and repeating the process.

# part 4

## Running the application

The first steps are the same as for part3.
After performing these steps, open a workspace of your choosing that can run and compile c++ files. Either a linux workspace with a VM or WSL, or even with CLion. We were working with a VM, so this guide will be suited to a linux environment.

1. Inside the linux environment, open the terminal, write hostname -I and copy the result.
2. In the server directory open the .env file and add "TCP_PORT=5555" and "TCO_IP={the result from the previous step}"
3. Now, go back to the linux environment and open the TCP server directory from there.
4. Open a terminal in that directory and compile the server.cpp file with the command "g++ -o server.out server.cpp".
5. From now on, you will be able to run the tcp server only with the server.out file

After all the steps are completed, you will need to first start the tcp server with the command "./server.out" inside the linux environment and later start the NodeJS server with the command "npm start" inside the server directory. After both servers are up, you will be able to open the react application by opening the url "http://localhost:8080", or opening the android application using the android studio.

## workflow

For the most parts of initializing the tcp server we worked together on the same computer. We had to brainstorm the idea of the algorithm and then putting it into action.
For the later parts, after the tcp server was complete, we added the new functionalities to the react app and later to the android app.
