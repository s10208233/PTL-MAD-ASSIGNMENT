package sg.edu.np.mad.remembertodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ViewTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        ArrayList<TaskCategory> TaskCategory_List = new ArrayList<>();

        TaskCategory_List.add(new TaskCategory("Sample1",
                new ArrayList<Task>(),
                ""));

        // Main RCview
        RecyclerView rview_cat_holder = findViewById(R.id.rview_category_holder);
        ViewTaskAdapater adptr = new ViewTaskAdapater(TaskCategory_List, getApplicationContext());
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rview_cat_holder.setLayoutManager(lm);
        rview_cat_holder.setItemAnimator(new DefaultItemAnimator());
        rview_cat_holder.setAdapter(adptr);

    }

}