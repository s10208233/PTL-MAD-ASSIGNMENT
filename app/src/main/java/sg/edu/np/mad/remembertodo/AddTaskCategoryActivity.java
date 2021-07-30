package sg.edu.np.mad.remembertodo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

//  static_categorylist to be converted to database
import java.util.ArrayList;

import static sg.edu.np.mad.remembertodo.ViewTaskActivity.static_categorylist;

public class AddTaskCategoryActivity extends AppCompatActivity {
    CombinedTaskDatabaseHandler taskcategory_DBhandler = new CombinedTaskDatabaseHandler(this,null,null,1);
    String colorcode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_category);

        //  Scrollview
        //ScrollView

        //  EditText
        EditText new_category_name_edittext = findViewById(R.id.editTextNewCategoryName);

        //  RadioGroup
        RadioGroup colsel_radiogroup = (RadioGroup) findViewById(R.id.color_select_radiogroup);
        colsel_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton selected_radio_button = (RadioButton)findViewById(i);
                Toast.makeText(AddTaskCategoryActivity.this, selected_radio_button.getText().toString(), Toast.LENGTH_SHORT).show();
                colorcode = selected_radio_button.getText().toString();
            }
        });


        //  Create Category Button
        findViewById(R.id.createCategoryBtn).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                String new_category_name_input = new_category_name_edittext.getText().toString();


                if (new_category_name_input.matches("")){
                    new_category_name_edittext.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.hard));
                    Toast.makeText(AddTaskCategoryActivity.this, "Please give this category a name!", Toast.LENGTH_SHORT).show();
                    ((ScrollView)findViewById(R.id.scrollview_addtaskcategory)).fullScroll(ScrollView.FOCUS_UP);
                }
                else{
                    static_categorylist.add(new TaskCategory(new_category_name_input, new ArrayList<Task>(), colorcode));
                    taskcategory_DBhandler.storeTaskCategoryList(new TaskCategory(new_category_name_input, new ArrayList<Task>(), colorcode));
                    //Notify appWidgetManager to update the widget when user add new cat
                    Context context = getApplicationContext();
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    ComponentName thisWidget = new ComponentName(context, TasksWidget.class);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetListView);
//                    if (DueDate_value.matches("Select A Date")){
//                        DueDate_value = "-";
//                    }
//                    Task NewTaskObject = new Task(TaskName_value, Difficulty_value, DueDate_value,false, getTodaysDate());
//                    static_categorylist.get(task_category_position).getTaskList().add(NewTaskObject);
                    finish();
                }
            }
        });

        //  Cancel Button
        findViewById(R.id.cancelCategoryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    //

}