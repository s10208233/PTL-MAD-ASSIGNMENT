package sg.edu.np.mad.remembertodo;

import java.util.ArrayList;

public class TaskCategory {

    private String TaskCategoryName;
    private ArrayList<Task> TaskList;
    private String ColorCode;

    public TaskCategory(String TaskCategoryName, ArrayList<Task> TaskList, String ColorCode){
        this.TaskCategoryName = TaskCategoryName;
        this.TaskList = TaskList;
        this.ColorCode = ColorCode;
    }

    public String getTaskCategoryName() {
        return TaskCategoryName;
    }

    public void setTaskCategoryName(String taskCategoryName) {
        TaskCategoryName = taskCategoryName;
    }

    public ArrayList<Task> getTaskList() {
        return TaskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        TaskList = taskList;
    }

    public String getColorCode() {
        return ColorCode;
    }

    public void setColorCode(String colorCode) {
        ColorCode = colorCode;
    }
}
