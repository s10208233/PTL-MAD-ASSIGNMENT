package sg.edu.np.mad.remembertodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskCategoryAdapater extends RecyclerView.Adapter<TaskCategoryViewHolder> {

    ArrayList<TaskCategory> data;
    Context context;

    public TaskCategoryAdapater(ArrayList<TaskCategory> input, Context inputContext) {
        data = input;
        context = inputContext;
    }

    @NonNull
    @Override
    public TaskCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_task_category, parent, false);
        return new TaskCategoryViewHolder(item);
    }

    @Override
    public void onBindViewHolder(TaskCategoryViewHolder holder, int position) {
        //  Set Background Color
        //  holder.Single_TaskCategory_ConstraintLayout.setBackgroundColor();

        //  Set Name of Task
        holder.ViewTaskCategory_CategoryName_text_view.setText(data.get(position).getTaskCategoryName());

        //  Add_New_Task_Button bind
        holder.ViewTaskCategory_add_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Add_New_Task_Button OnClick
            }
        });

        //  Nested Task Recycler View Code
        TaskAdapter tadapter = new TaskAdapter(data.get(position).getTaskList());
        LinearLayoutManager tlm = new LinearLayoutManager(context);
        holder.TaskCategory_Inner_rview.setLayoutManager(tlm);
        holder.TaskCategory_Inner_rview.setItemAnimator(new DefaultItemAnimator());
        holder.TaskCategory_Inner_rview.setAdapter(tadapter);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
