package sg.edu.np.mad.remembertodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import static sg.edu.np.mad.remembertodo.ViewTaskActivity.static_categorylist;


public class SelectTaskTimerActivity extends AppCompatActivity {

    private String TaskCategoryName;
    private ArrayList<Task> TaskList;
    private String ColorCode;
    CombinedTaskDatabaseHandler taskcategory_DBhandler = new CombinedTaskDatabaseHandler(this,null,null,1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_task_timer);

        //  Array list only picks categories where getTaskCategoryList().size() > 0
        ArrayList<TaskCategory> temp_select_taskcat_list = new ArrayList<TaskCategory>();
        for (int i = 0; i<static_categorylist.size(); i++){
            if (static_categorylist.get(i).getTaskList().size()>0 && checkIfTaskListChecked(static_categorylist.get(i).getTaskList())==false){
                temp_select_taskcat_list.add(static_categorylist.get(i));
            }
        }

        //  Selection Adapter to select task from task category, task category retrieved from database.
        RecyclerView recyclerView_cat_holder = findViewById(R.id.rv_stt_TaskCategory);
        recyclerView_cat_holder.setNestedScrollingEnabled(false);
        SelectTaskTimerCategoryAdapter sttAdapter = new SelectTaskTimerCategoryAdapter(temp_select_taskcat_list, getApplicationContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView_cat_holder.setLayoutManager(mLayoutManager);
        recyclerView_cat_holder.setItemAnimator(new DefaultItemAnimator());
        recyclerView_cat_holder.setAdapter(sttAdapter);

    }

    private boolean checkIfTaskListChecked(ArrayList<Task> tasklist){
        boolean allChecked = false;
        for (int i=0; i<tasklist.size(); i++){
            if (tasklist.get(i).isCompleted()){
                allChecked=true;
            }
        }
        return allChecked;
    }
}