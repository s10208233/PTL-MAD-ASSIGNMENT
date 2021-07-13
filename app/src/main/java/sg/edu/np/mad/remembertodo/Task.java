package sg.edu.np.mad.remembertodo;

import java.util.Date;

public class Task {

    private String TaskName;
    //private Date DateCreated;
    //private Date DueDate;
    private boolean Completed;

    public Task(String TaskName , boolean Completed){
        this.TaskName = TaskName;
//        this.DateCreated = DateCreated;
//        this.DueDate = DueDate;
        this.Completed = Completed;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }
//
//    public Date getDateCreated() {
//        return DateCreated;
//    }

//    public void setDateCreated(Date dateCreated) {
//        DateCreated = dateCreated;
//    }
//
//    public Date getDueDate() {
//        return DueDate;
//    }
//
//    public void setDueDate(Date dueDate) {
//        DueDate = dueDate;
//    }

    public boolean isCompleted() {
        return Completed;
    }

    public void setCompleted(boolean completed) {
        Completed = completed;
    }
}
