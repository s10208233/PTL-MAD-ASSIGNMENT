package sg.edu.np.mad.remembertodo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.os.IResultReceiver;
import android.util.Log;
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

import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static sg.edu.np.mad.remembertodo.TaskCategoryAdapter.static_inner_task_rv;
import static sg.edu.np.mad.remembertodo.ViewTaskActivity.static_categorylist;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    SharedPreferences sharedPreferences;
    String TaskCategoryName;
    int TaskCategoryPos;
    ArrayList<Task> data;
    Context con;
    public String GLOBAL_PREFS = "MyPrefs";

    private Context Mcontext;
    Gson gson = new Gson();

    public TaskAdapter(ArrayList<Task> input, Context context) {
        data = input;
        con = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_task, parent, false);
        Mcontext = parent.getContext();
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
            CompoundButtonCompat.setButtonTintList(holder.Single_Task_Checkbox, ColorStateList.valueOf(Color.parseColor("#80FFFFFF")));
            holder.Single_Task_Name.setTextColor(Color.parseColor("#80FFFFFF"));
            holder.Single_Task_DueDate.setTextColor(Color.parseColor("#80FFFFFF"));
            holder.Single_Task_DiffIndicator.setTextColor(Color.parseColor("#80FFFFFF"));
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
                    holder.Single_Task_Name.setTextColor(Color.parseColor("#80FFFFFF"));
                    holder.Single_Task_DueDate.setTextColor(Color.parseColor("#80FFFFFF"));
                    CompoundButtonCompat.setButtonTintList(holder.Single_Task_Checkbox, ColorStateList.valueOf(Color.parseColor("#80FFFFFF")));
                    holder.Single_Task_DiffIndicator.setTextColor(Color.parseColor("#80FFFFFF"));


                    for (int j = 0; j < static_categorylist.size();j++){
                        if (static_categorylist.get(j).getTaskList() == data){
                            TaskCategoryName = static_categorylist.get(j).getTaskCategoryName();
                            TaskCategoryPos = j;
                        }
                    }
                    CombinedTaskDatabaseHandler taskcategory_DBhandler = new CombinedTaskDatabaseHandler(Mcontext,null,null,1);
                    String notChecked = gson.toJson(data.get(position));
                    data.get(position).setCompleted(true);
                    //change back to gson string
                    String checkedTask = gson.toJson(data.get(position));
                    //replace string to the updated data
                    String result = gson.toJson(static_categorylist.get(TaskCategoryPos).getTaskList()).replace(notChecked,checkedTask);
                    //update database
                    taskcategory_DBhandler.CheckedTaskTimer(result,TaskCategoryName);

                    //  SHARED PREFERENCE THIS SHIT WITH IF ELSE THANKS KS
                    //  Apply shared pref here pls thanks ks
                    sharedPreferences = Mcontext.getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE);
                    String check = sharedPreferences.getString("check","");
                    if (check.equals("true")){
                        Toast.makeText(con, "Tip! You may hold down on a task to delete!", Toast.LENGTH_SHORT).show();
                    }

                    
                }
                else
                {
                    holder.Single_Task_StrikeLine.setVisibility(View.INVISIBLE);
                    holder.Single_Task_Name.setTextColor(Color.parseColor("#ffffff"));
                    holder.Single_Task_DueDate.setTextColor(Color.parseColor("#ffffff"));
                    holder.Single_Task_Checkbox.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                    CompoundButtonCompat.setButtonTintList(holder.Single_Task_Checkbox, ColorStateList.valueOf(Color.parseColor("#ffffff")));
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
                        String result;
                        if (position == 0){
                            result = gson.toJson(data).replace(gson.toJson(data.get(position))+",","");
                            if (data.size() == 1){
                                result = " ";
                            }
                        }
                        else{
                            result = gson.toJson(data).replace(","+gson.toJson(data.get(position)),"");
                        }


                        for (int j = 0; j < static_categorylist.size();j++){
                            if (static_categorylist.get(j).getTaskList() == data){
                                TaskCategoryName = static_categorylist.get(j).getTaskCategoryName();
                            }
                        }

                        CombinedTaskDatabaseHandler taskcategory_DBhandler = new CombinedTaskDatabaseHandler(Mcontext,null,null,1);
                        taskcategory_DBhandler.deleteSingleTask(result,TaskCategoryName);
                        data.remove(position);
                        static_inner_task_rv.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, data.size());
                        notifyDataSetChanged();

//                        if (position == 0){
//                            taskcategory_DBhandler.deleteSingleTask(task,TaskCategoryName);
//                            static_inner_task_rv.removeViewAt(position);
//                            notifyItemRemoved(position);
//                            notifyItemRangeChanged(position, data.size());
//                            notifyDataSetChanged();
//                        }
//                        else{
//                            taskcategory_DBhandler.deleteSingleTask(data.remove(position-1),TaskCategoryName);
////                            data.remove(position);
////                            static_inner_task_rv.removeViewAt(position-1);
//
//
//                            notifyDataSetChanged();
//                        }


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
