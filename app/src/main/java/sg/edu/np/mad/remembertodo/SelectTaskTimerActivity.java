package sg.edu.np.mad.remembertodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;


public class SelectTaskTimerActivity extends AppCompatActivity {

    private String TaskCategoryName;
    private ArrayList<Task> TaskList;
    private String ColorCode;
    CombinedTaskDatabaseHandler taskcategory_DBhandler = new CombinedTaskDatabaseHandler(this,null,null,1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_task_timer);

        //  Selection Adapter to select task from task category, task category retrieved from database.
        RecyclerView recyclerView_cat_holder = findViewById(R.id.rv_stt_TaskCategory);
        recyclerView_cat_holder.setNestedScrollingEnabled(false);
        SelectTaskTimerCategoryAdapter sttAdapter = new SelectTaskTimerCategoryAdapter(taskcategory_DBhandler.getTaskCategoryList(), getApplicationContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView_cat_holder.setLayoutManager(mLayoutManager);
        recyclerView_cat_holder.setItemAnimator(new DefaultItemAnimator());
        recyclerView_cat_holder.setAdapter(sttAdapter);

    }
}