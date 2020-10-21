package individual_project;

import java.util.Date;

enum Status {

    DOING("Doing"),
    PENDING("Pending"),
    ASAP("ASAP"),
    DONE("Done");
    private String text;

    Status(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static Status fromString(String text) {
        for (Status b : Status.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        System.out.println("\u001B[31m" + "No Status with text " + "\u001B[32m" + text + "\u001B[31m" + " found" + "\u001B[0m");
        return null;
    }
}

public class Task {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
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
    private String title;

    Task() {
    }

    Task(String title, Date dueDate, Status status, String project, String description) {
        this.title = title;
        this.dueDate = dueDate;
        this.status = status;
        this.project = project;
        this.description = description;
    }

    Task(Task t) {
        this.title = t.getTitle();
        this.status = Status.fromString(t.getStatus());
        this.dueDate = t.getDueDate();
        this.project = t.getProject();
        this.description = t.getDescription();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDueDate(Date date) {
        this.dueDate = date;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status.getText();
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getProject() {
        return project;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        StringBuilder taskInfo = new StringBuilder("Task: " + title + "\n");
        taskInfo.append("Project: " + project + "\n");
        taskInfo.append("Status: " + status.toString().toLowerCase() + "\n");
        taskInfo.append("Due Date: " + dueDate + "\n");
        return taskInfo.toString();
    }

    public void markDone() {
        this.status = Status.DONE;
    }

    public boolean equals(Task t2) {
        return title == t2.title;
    }
}
