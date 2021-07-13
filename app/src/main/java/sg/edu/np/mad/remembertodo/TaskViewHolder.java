package sg.edu.np.mad.remembertodo;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    ConstraintLayout task_constraint_layout;
    TextView Single_Task_Name;
    TextView Single_Task_DueDate;
    CheckBox Single_Task_Checkbox;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        task_constraint_layout = itemView.findViewById(R.id.task_constraint_layout);
        Single_Task_Name = itemView.findViewById(R.id.single_task_name);
        Single_Task_DueDate = itemView.findViewById(R.id.single_task_duedate);
        Single_Task_Checkbox = itemView.findViewById(R.id.single_task_checkbox);
    }

}
