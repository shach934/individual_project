# ToDoLy

In this individual project, a todo list application is built. The applicatio let user input a task which is charactized by its **Title**, **Status**, **Due Date**. **Project**, **Description**.

* **Title**: a String define the task, should be unique for each task, if user want to add a task with title already exists in the database, it will show error.
* **Status**: a serial predifined status options, <span style = "color:red"> user should select one of the status</span>.
  * Doing
  * Pending
  * ASAP
  * Done
* **Due Date**: a date in format *yyyy-mm-dd, hh:mm:ss* should be input by user, if the format is uncorrect, it will promte the input again, if the input date has already passed, it will also not accepted by application. <span style="color:red"> User should be able to define the format in future version. </span>
* **Project**: a string defines the project of the task.
* **Description**: a few words to descript the task.

Each task should have a unique *Title*. If user want to add a task that has the same name with existing task, the applicatin will show **Error**. The tasks are saved in an *ArrayList*(task database), it can show user tasks by the time they are added, by their *project*, by the *due date*, only not done tasks, <span style="color:red">single task selected by use</span>. User can add new task to the database, the application will show the field of task to promote the input from user by sequence of *Title, Due date, Status, Project, Description*.

Once the application is initiate, it will look for a file named <ToDoLy.SDA>, which saves the tasks from the previous, in the same folder as the application. If the file is not found, it will create a new file with this name. If the file exists, the application will start to parse the content of the file to tasks and keep them as a task database. If there is any problem while parsing, it will promote alert to user:

*File is corrupted. Remove and create a new database? 1)Yes 2)No*.

User can input **1** or **2** to make a selection. If the user selected yes, the old file will be deleted and a new one be created, if no is selected, the application will be terminated to let user to handle the file format problem manually.

After the application sucessfully initiated. It will displace a menu that user could do: 


1. Add a new task
2. Edit a task
3. View the tasks
4. Save and Quit

The class diagram is as follow:
![Class Diagram](https://github.com/shach934/individual_project/blob/main/Package%20individual_project.png?raw=true)
