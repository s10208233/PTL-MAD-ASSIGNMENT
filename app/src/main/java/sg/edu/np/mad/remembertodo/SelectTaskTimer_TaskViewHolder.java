package sg.edu.np.mad.remembertodo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class SelectTaskTimer_TaskViewHolder extends RecyclerView.ViewHolder {

    ConstraintLayout stt_Task_ConstraintLayout;
    TextView stt_Task_DiffIndicator;
    TextView stt_Task_Name;
    TextView stt_Task_DueDate;

    public SelectTaskTimer_TaskViewHolder(@NonNull View itemView){
        super(itemView);
        stt_Task_ConstraintLayout = itemView.findViewById(R.id.stt_Task_constraintlayout);
        stt_Task_DiffIndicator = itemView.findViewById(R.id.stt_task_diff_indicator);
        stt_Task_Name = itemView.findViewById(R.id.stt_vh_name);
        stt_Task_DueDate = itemView.findViewById(R.id.stt_vh_duedate);
    }

}
