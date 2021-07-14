package sg.edu.np.mad.remembertodo;

import java.util.Date;

public class Task {

    private String TaskName;
    private int Difficulty;
    private Date DueDate;
    private boolean Completed;
    private Date DateCreated;

    public Task(String TaskName, int Difficulty, Date DueDate, boolean Completed, Date DateCreated){
        this.TaskName = TaskName;
        this.Difficulty = Difficulty;
        this.DateCreated = DateCreated;
        this.DueDate = DueDate;
        this.Completed = Completed;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public Date getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        DateCreated = dateCreated;
    }

    public Date getDueDate() {
        return DueDate;
    }

    public void setDueDate(Date dueDate) {
        DueDate = dueDate;
    }

    public boolean isCompleted() {
        return Completed;
    }

    public void setCompleted(boolean completed) {
        Completed = completed;
    }
}
