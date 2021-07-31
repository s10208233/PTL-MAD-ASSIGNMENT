package sg.edu.np.mad.remembertodo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

import static sg.edu.np.mad.remembertodo.ViewTaskActivity.taskcategoryadapter;
import static sg.edu.np.mad.remembertodo.ViewTaskActivity.static_rview_cat_holder;



public class TaskCategoryAdapter extends RecyclerView.Adapter<TaskCategoryViewHolder> {
    static RecyclerView static_inner_task_rv;
    SharedPreferences sharedPreferences;
    public String GLOBAL_PREFS = "MyPrefs";
    private Context Mcontext;
    ArrayList<TaskCategory> data;
    Context context;



    public TaskCategoryAdapter(ArrayList<TaskCategory> input, Context inputContext) {
        data = input;
        context = inputContext;
    }

    @NonNull
    @Override
    public TaskCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_task_category, parent, false);
        Mcontext = parent.getContext();
        return new TaskCategoryViewHolder(item);
    }

    @Override
    public void onBindViewHolder(TaskCategoryViewHolder holder, int position) {

        //  Set Background Color
        holder.Single_TaskCategory_ConstraintLayout.setBackgroundColor(Color.parseColor(colorNameToCode(data.get(position).getColorCode())));

        //  Set Name of Task
        holder.ViewTaskCategory_CategoryName_text_view.setText(data.get(position).getTaskCategoryName());

        //  Set Delete Function
        holder.ViewTaskCategory_Delete_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Alert Dialog Prompt Delete TaskCategory
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogCustom);
                builder.setTitle("Task Category Delete Confirmation");
                builder.setMessage("Would you like to remove the task category, '" + data.get(position).getTaskCategoryName() + "'?");
                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sharedPreferences = Mcontext.getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        CombinedTaskDatabaseHandler taskcategory_DBhandler = new CombinedTaskDatabaseHandler(Mcontext,null,null,1);
                        taskcategory_DBhandler.deleteTaskCategory(data.get(position).getTaskCategoryName());
                        editor.putInt("number",0);
                        editor.apply();
                        data.remove(position);
                        static_rview_cat_holder.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, data.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog createdDialog = builder.create();
                createdDialog.show();
            }
        });

        //  Add_New_Task_Button bind
        holder.ViewTaskCategory_add_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addtask = new Intent(context, AddTaskActivity.class);
                addtask.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                //  Passing data & Starting AddTaskActivity
                  Bundle databundle = new Bundle();
                  databundle.putInt("TaskCategoryPosition", position);
                  databundle.putString("TaskCategoryName", data.get(position).getTaskCategoryName());

                //  Start AddTaskActivity
                addtask.putExtras(databundle);
                context.startActivity(addtask);
            }
        });

        //  Nested Task Recycler View Code
        TaskAdapter tadapter = new TaskAdapter(data.get(position).getTaskList(), context);
        LinearLayoutManager tlm = new LinearLayoutManager(context);
        holder.TaskCategory_Inner_rview.setLayoutManager(tlm);
        holder.TaskCategory_Inner_rview.setItemAnimator(new DefaultItemAnimator());
        holder.TaskCategory_Inner_rview.setAdapter(tadapter);
        static_inner_task_rv = holder.TaskCategory_Inner_rview;

    }

    @Override
    public int getItemCount() {
        if (data == null){
            return 0;
        }
        return data.size();
    }

    public String colorNameToCode(String sel){
        if(sel.matches("Red"))      {return "#850000";}
        if(sel.matches("Green"))    {return "#4F9300";}
        if(sel.matches("Blue"))     {return "#0057B5";}
        if(sel.matches("Purple"))   {return "#5A2DA8";}
        if(sel.matches("Yellow"))   {return "#D3A20B";}
        if(sel.matches("Orange"))   {return "#D67806";}
        if(sel.matches("Black"))    {return "#000000";}
        return "#404040";
    }
}
