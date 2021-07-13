package sg.edu.np.mad.remembertodo;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class TaskCategoryViewHolder extends RecyclerView.ViewHolder {

    ConstraintLayout Single_TaskCategory_ConstraintLayout;
    TextView ViewTaskCategory_CategoryName_text_view;
    RecyclerView TaskCategory_Inner_rview;
    ImageButton ViewTaskCategory_add_task_button;

    public TaskCategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        Single_TaskCategory_ConstraintLayout = itemView.findViewById(R.id.single_taskcategory_constraintlayout);
        TaskCategory_Inner_rview = itemView.findViewById(R.id.rview_task_holder);
        ViewTaskCategory_add_task_button = itemView.findViewById(R.id.task_category_add_task_button);
        ViewTaskCategory_CategoryName_text_view = itemView.findViewById(R.id.task_category_name);
    }
}
