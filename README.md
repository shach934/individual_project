# ToDoLy

In this individual project, a todo list application is built.

## Function

The application let user input a task which is charactized by its **Title**, **Status**, **Due Date**. **Project**, **Description**.

* **Title**: a string define the task, be unique for each task, if user adds a task with title already exists, application won't accept. ***It is case insensitive.***
* **Status**: a serial predifined status options, ***TODO: user should select one of the status, instead of input.***
  * Doing, working on right now.
  * Pending, pending for future.
  * ASAP, as soon as possible, urgent to complete.
  * Done, a task completed.
* **Due Date**: a date in format *yyyy-mm-dd, hh:mm:ss* should be input by user, if the format is uncorrect, it will ask the input again, if the input date has already passed, it will also not accepted by application. ***TODO: User should be able to define the Date format in future version.***
* **Project**: a string defines the project of the task.
* **Description**: a few words to descript the task.

After a task is created, it will be saved in a task database(*an ArrayList*). The database can show user all the tasks sorted by the time they are added, by *project*, by the *due date*, only not done tasks, ***TODO: Show single task selected by use***.

User can also fetch a task from the task database, edit tasks and remove tasks. After all the operations on the database, user can save the change to the file and exit the application. ***TODO: seperate the save and exit.***

## Frontend

Once the application is initiate, it will look for a file named <ToDoLy.SDA>, which saves the tasks from the previous, in the same folder as the application. If the file is not found, it will create a new file with this name. If the file exists, the application will start to parse the content of the file to tasks and keep them as a task database. If there is any problem while parsing, it will display alert to user:

*File is corrupted. Remove and create a new database? 1)Yes 2)No*.

User can input **1** or **2** to make a selection. If the user selected yes, the old file will be deleted and a new one be created, if no is selected, the application will be terminated to let user to handle the file format problem manually.

After the application sucessfully initiated. It will display the welcome words and present the options:  ***TODO: add welcome info***

1. View the tasks
2. Add a new task
3. Edit a task
4. Save and Quit

Each option has the function:

1. Show all the tasks. After this option is selected, a submenu will be presented to ask user show the tasks by *due date, project, not done*.

2. Application asks user to input fields of a task. The requirement of each field will be checked at input, if not valid, it will be asked again. User can always input CANCEL to exit.

3. User needs to input the task title to select the task to edit. It will then go over other fields to let user to input the new field values. The new task will be saved.

4. Save the tasks to the task databse file: ToDoLy.SDA. And quit the application.

## Backend

The task database keeps all the tasks, also it could sort its tasks by *due date*, *project*, *not done*. In order to keep all different ordered database, the applicaton will deep copy databases and sort them by different criteria.

***This sort will be O(nlogn) time complexity, current implementation will run it everytime user wants to show it in different order. It would be better to keep several copies in the memory, after read in the task from file and copy it to several copies and sort them. Then everytime add/delete a task, using binary search to insert/delete(O(logn)). The order of all the copies will be kept. This will be time efficient but space consuming (several times memory usage).***

It show the task by order specified by user in a table. ***TODO: user specify the viewing table format.***

Task database can save the tasks to a text file, with each field of task seperated by "|@|" in certain order. This is seperator is chosen arbitrarily.

The class diagram is as follow:
![Class Diagram](https://github.com/shach934/individual_project/blob/main/Package%20individual_project.png?raw=true)
