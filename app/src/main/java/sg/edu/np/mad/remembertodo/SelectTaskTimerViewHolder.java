package sg.edu.np.mad.remembertodo;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class SelectTaskTimerViewHolder extends RecyclerView.ViewHolder {

    ConstraintLayout constraint_layout;
    TextView Task_DiffIndicator;
    TextView Task_Name;
    TextView Task_DueDate;
    View Task_StrikeLine;

    public SelectTaskTimerViewHolder(@NonNull View itemView){
        super(itemView);
        constraint_layout = itemView.findViewById(R.id.constraint_layout);
        Task_DiffIndicator = itemView.findViewById(R.id.task_diff_indicator);
        Task_Name = itemView.findViewById(R.id.task_name);
        Task_DueDate = itemView.findViewById(R.id.task_duedate);
        Task_StrikeLine = itemView.findViewById(R.id.task_strikeline);
    }

}
