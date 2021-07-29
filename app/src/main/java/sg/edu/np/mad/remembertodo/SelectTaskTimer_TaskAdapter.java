package sg.edu.np.mad.remembertodo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectTaskTimer_TaskAdapter extends RecyclerView.Adapter<SelectTaskTimer_TaskViewHolder> {

    ArrayList<Task> data;

    public SelectTaskTimer_TaskAdapter(ArrayList<Task> input) {
        data = input;
    }

    @NonNull
    @Override
    public SelectTaskTimer_TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_stt_task, parent, false);
        return new SelectTaskTimer_TaskViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTaskTimer_TaskViewHolder holder, int position) {
        //  Bind Constraint View onTouch
        holder.stt_Task_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  CONTINUE HERE CONTINUE HERE CONTINUE HERE CONTINUE HERE CONTINUE HERE
            }
        });

        //  Task Difficult Indicator
        if (data.get(position).getDifficulty() == 1){
            holder.stt_Task_DiffIndicator.setTextColor(Color.parseColor("#FFDD00"));
        }
        else if (data.get(position).getDifficulty() == 2){
            holder.stt_Task_DiffIndicator.setTextColor(Color.parseColor("#ff0000"));
        }
        else {
            holder.stt_Task_DiffIndicator.setTextColor(Color.parseColor("#08fa00"));
        }

        //  Task Name
        holder.stt_Task_Name.setText(data.get(position).getTaskName());

        //  Task Due Date
        holder.stt_Task_DueDate.setText(data.get(position).getDueDate());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
