package sg.edu.np.mad.remembertodo;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class SelectTaskTimerCategoryViewHolder extends RecyclerView.ViewHolder {
    ConstraintLayout Stt_ConstraintLayout;
    TextView task_category_name;
    RecyclerView rv_stt_taskHolder;
    ImageView deleteTask;

    public SelectTaskTimerCategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        Stt_ConstraintLayout = itemView.findViewById(R.id.stt_constraintlayout);
        rv_stt_taskHolder = itemView.findViewById(R.id.rv_stt_taskholder);
        task_category_name = itemView.findViewById(R.id.task_category_name);
        deleteTask = itemView.findViewById(R.id.deleteTask);
    }
}
