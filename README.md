Jira link: https://roiesu3.atlassian.net/jira/software/projects/BIS/boards/2/timeline

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
5. For now on, to run the program type "npm start" at the command line to start the application. To get to the web app go to "http://localhost:8080/.

## Workflow

First we created a model for each data collection. We used the model to have each controllers to interact with the database, and to send queries to it. Moreover the controllers are made to handle requests sent from the routers, and send them a response. Those routers are made to arrange the the requests sent to the api.

After that, we connected the server to the React code we wrote in the first part of the project. To do this, we moved all local JSON data files into the mongodb collections, and converted each request to the local JSON files throughout the program into server requests. Most of these actions were CRUD operations that helped us achieve the required functionality.

During this part of the project, new requirements were added, such as the ability to edit user details, the option to delete a user from the program, and more. Therefore, we also implemented these changes by adding new pages to the application to support the required operations.

Our overall workflow was similar to the first part of the project. We defined tasks in Jira and then assigned them. At regular intervals, we held Zoom meetings where each team member updated on their progress. We consulted with each other, updated the task list and the work distribution accordingly, and continued working until this part of the project was completed.
