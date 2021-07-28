package sg.edu.np.mad.remembertodo;

import java.util.ArrayList;
import java.util.Date;

public class Task extends ArrayList<Task> {

    private String TaskName;
    private int Difficulty;
    private String DueDate;
    private boolean Completed;
    private String DateCreated;

    public Task(String TaskName, int Difficulty, String DueDate, boolean Completed, String DateCreated){
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

    public int getDifficulty() {
        return Difficulty;
    }

    public void setDifficulty(int difficulty) {
        Difficulty = difficulty;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public boolean isCompleted() {
        return Completed;
    }

    public void setCompleted(boolean completed) {
        Completed = completed;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }
}
