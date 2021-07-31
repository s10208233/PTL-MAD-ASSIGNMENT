package sg.edu.np.mad.remembertodo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Timer;

public class SelectTaskTimer_TaskAdapter extends RecyclerView.Adapter<SelectTaskTimer_TaskViewHolder> {

    ArrayList<Task> data;
    String TaskCatName;
    int TaskCatPos;
    Context context;

    public SelectTaskTimer_TaskAdapter(int position,String task_category_name, ArrayList<Task> input, Context con) {
        data = input;
        TaskCatName = task_category_name;
        TaskCatPos = position;
        context = con;
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
        //  Tapping a task row will select the task to countdown for
        holder.stt_Task_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogCustom);
                builder.setMessage("Confirm Selection");
                builder.setTitle("You have selected the task '" + data.get(position).getTaskName() + "', time yourself to finish this task?");

                builder.setPositiveButton("Time This Task", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Gson gson = new Gson();

                        Intent tasktimer = new Intent(v.getContext(), TimerActivity.class);
                        tasktimer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        //  Passing data & Starting AddTaskActivity
                        Bundle databundle = new Bundle();
                        databundle.putString("TaskCategoryName", TaskCatName);
                        databundle.putString("SelectedJSONTask", gson.toJson(data.get(position)));
                        databundle.putInt("position",TaskCatPos);


                        //  Start AddTaskActivity
                        tasktimer.putExtras(databundle);
                        v.getContext().startActivity(tasktimer);
                        ((Activity)context).finish();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                //  Initiating the alert dialog
                AlertDialog select_task_confirm_dialog = builder.create();
                select_task_confirm_dialog.show();
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
