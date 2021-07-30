package sg.edu.np.mad.remembertodo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectTaskTimerCategoryAdapter extends RecyclerView.Adapter<SelectTaskTimerCategoryViewHolder> {

    ArrayList<TaskCategory> data;
    Context context;

    public SelectTaskTimerCategoryAdapter(ArrayList<TaskCategory> input, Context inputContext) {
        data = input;
        context = inputContext;
    }

    @NonNull
    @Override
    public SelectTaskTimerCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_stt_category, parent, false);
        return new SelectTaskTimerCategoryViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTaskTimerCategoryViewHolder holder, int position) {
        //  Set Background Color
        holder.Stt_ConstraintLayout.setBackgroundColor(Color.parseColor(colorNameToCode(data.get(position).getColorCode())));

        //Set stt TaskCat Name
        holder.stt_task_category_name.setText(data.get(position).getTaskCategoryName());


        //  Nested Task Recycler View Code
            //  Get only task that are not checked
        ArrayList<Task> onlyUncheckedTasks = new ArrayList<>();
        for (int i = 0; i<data.get(position).getTaskList().size(); i++){
            if (data.get(position).getTaskList().get(i).isCompleted() == false){
                onlyUncheckedTasks.add(data.get(position).getTaskList().get(i));
            }
        }

        SelectTaskTimer_TaskAdapter sttadapter = new SelectTaskTimer_TaskAdapter(data.get(position).getTaskCategoryName(), onlyUncheckedTasks, context);
        LinearLayoutManager tlm = new LinearLayoutManager(context);
        holder.rv_stt_taskHolder.setLayoutManager(tlm);
        holder.rv_stt_taskHolder.setItemAnimator(new DefaultItemAnimator());
        holder.rv_stt_taskHolder.setAdapter(sttadapter);
    }


    @Override
    public int getItemCount() {
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
