package sg.edu.np.mad.remembertodo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class SelectTaskTimerCategoryViewHolder extends RecyclerView.ViewHolder {
    ConstraintLayout Stt_ConstraintLayout;
    TextView stt_task_category_name;
    RecyclerView rv_stt_taskHolder;

    public SelectTaskTimerCategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        Stt_ConstraintLayout = itemView.findViewById(R.id.stt_constraintlayout);
        rv_stt_taskHolder = itemView.findViewById(R.id.rv_stt_taskholder);
        stt_task_category_name = itemView.findViewById(R.id.stt_task_category_name);
    }
}
