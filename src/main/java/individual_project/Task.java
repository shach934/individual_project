package individual_project;

import java.util.Date;

enum Status{
    DOING, PENDING, ASAP, DONE;
}


public class Task{
    /**
     * Task class:
     * Construct a task with @param	 Title, @param Status, @param dueData, @param project, @param description.
     * Function list: set@params,  show(), edit@param,
     */
    private String title;
    private Status status;
    private Date dueDate;
    private String project;
    private String description;

    Task(){}

    Task(Task t){
        this.title = t.getTitle();
        this.status = t.getStatus();
        this.dueDate = t.getDueDate();
        this.project = t.getProject();
        this.description = t.getDescription();
    }

    public void setTitle(String title){ this.title = title; }
    public void setStatus(Status status){  this.status = status; }
    public void setDueDate(Date date){ this.dueDate = date; }
    public void setProject(String project) {  this.project = project; }
    public void setDescription(String description) {  this.description = description; }

    public String getTitle() { return title;}
    public Status getStatus(){  return status;  }
    public Date getDueDate() { return dueDate;}
    public String getProject() { return project;}
    public String getDescription() { return description;}

    @Override
    public String toString(){
        StringBuilder taskInfo = new StringBuilder("Task: " + title + "\n");
        taskInfo.append("Project: " + project + "\n");
        taskInfo.append("Status: " + status.toString().toLowerCase() + "\n");
        taskInfo.append("Due Date: " + dueDate + "\n");
        return taskInfo.toString();
    }

    public void markDone(){  this.status = Status.DONE;  }
    public boolean equals(Task t2){  return title == t2.title; }
}
