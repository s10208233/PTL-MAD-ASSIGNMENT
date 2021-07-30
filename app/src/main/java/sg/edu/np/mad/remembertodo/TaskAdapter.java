package sg.edu.np.mad.remembertodo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.widget.CompoundButtonCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    ArrayList<Task> data;
    Context con;

    public TaskAdapter(ArrayList<Task> input, Context context) {
        data = input;
        con = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_task, parent, false);
        return new TaskViewHolder(item);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        
        //  Task Difficult Indicator
        if (data.get(position).getDifficulty() == 1){
            holder.Single_Task_DiffIndicator.setTextColor(Color.parseColor("#FFDD00"));
        }
        else if (data.get(position).getDifficulty() == 2){
            holder.Single_Task_DiffIndicator.setTextColor(Color.parseColor("#ff0000"));
        }
        else {
            holder.Single_Task_DiffIndicator.setTextColor(Color.parseColor("#08fa00"));
        }

        //  Task Name
        holder.Single_Task_Name.setText(data.get(position).getTaskName());

        //  Task Due Date
        holder.Single_Task_DueDate.setText(data.get(position).getDueDate());

        //  Checkbox
        if (data.get(position).isCompleted()) {
            holder.Single_Task_Checkbox.setChecked(true);
            holder.Single_Task_StrikeLine.setVisibility(View.VISIBLE);
            CompoundButtonCompat.setButtonTintList(holder.Single_Task_Checkbox, ColorStateList.valueOf(Color.parseColor("#08fa00")));
        }
        else {
            holder.Single_Task_StrikeLine.setVisibility(View.INVISIBLE);
        }

        holder.Single_Task_Checkbox.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (holder.Single_Task_Checkbox.isChecked()) {
                    holder.Single_Task_StrikeLine.setVisibility(View.VISIBLE);
                    holder.Single_Task_Name.setTextColor(Color.parseColor("#08fa00"));
                    holder.Single_Task_DueDate.setTextColor(Color.parseColor("#08fa00"));
                    CompoundButtonCompat.setButtonTintList(holder.Single_Task_Checkbox, ColorStateList.valueOf(Color.parseColor("#08fa00")));

                    //  SHARED PREFERENCE THIS SHIT WITH IF ELSE THANKS KS
                    //  Apply shared pref here pls thanks ks
                    Toast.makeText(con, "Tip! You may hold down on a task to delete!", Toast.LENGTH_SHORT).show();
                    
                }
                else
                {
                    holder.Single_Task_StrikeLine.setVisibility(View.INVISIBLE);
                    holder.Single_Task_Name.setTextColor(Color.parseColor("#ffffff"));
                    holder.Single_Task_DueDate.setTextColor(Color.parseColor("#ffffff"));
                    holder.Single_Task_Checkbox.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                    CompoundButtonCompat.setButtonTintList(holder.Single_Task_Checkbox, ColorStateList.valueOf(Color.parseColor("#ffffff")));
                }
            }
        });



        //  Alternating Background Color
        if (position%2 == 0){
            holder.task_constraint_layout.setBackgroundColor(Color.parseColor("#66000000"));
        }
        
        //  On Hold Delete
        holder.task_constraint_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //  Vibrate on Hold
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

                //  Alert Dialog Prompt Delete
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogCustom);
                builder.setTitle("Task Delete Confirmation");
                builder.setMessage("Would you like to remove '" + data.get(position).getTaskName() + "' task?");
                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog createdDialog = builder.create();
                createdDialog.show();



                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data == null){
            return 0;
        }
        return data.size(); }
}
