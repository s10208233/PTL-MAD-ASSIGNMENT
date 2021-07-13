package sg.edu.np.mad.remembertodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class ViewTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        ArrayList<TaskCategory> TaskCategory_List = new ArrayList<>();



        TaskCategory_List.add(new TaskCategory("Mobile Application Development",
                new ArrayList<Task>(
                        Arrays.asList(
                                new Task("Assignment", false),
                                new Task("Sample",false),
                                new Task("Sample",false),
                                new Task("Sample",false),
                                new Task("Sample",false)




                        )
                ),
                ""));

        TaskCategory_List.add(new TaskCategory("Others",
                new ArrayList<Task>(
                        Arrays.asList(
                                new Task("Vaccination", true),
                                new Task("Sample1",false),
                                new Task("Sample",false)

                        )
                ),
                ""));


        // Main RCview
        RecyclerView rview_cat_holder = findViewById(R.id.rview_category_holder);
        TaskCategoryAdapater adptr = new TaskCategoryAdapater(TaskCategory_List, getApplicationContext());
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rview_cat_holder.setLayoutManager(lm);
        rview_cat_holder.setItemAnimator(new DefaultItemAnimator());
        rview_cat_holder.setAdapter(adptr);

    }

}