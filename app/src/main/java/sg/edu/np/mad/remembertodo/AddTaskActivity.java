package sg.edu.np.mad.remembertodo;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ResourceManagerInternal;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

//  static_categorylist to be converted to database
import static sg.edu.np.mad.remembertodo.ViewTaskActivity.static_categorylist;

public class AddTaskActivity extends AppCompatActivity {
    CombinedTaskDatabaseHandler taskcategory_DBhandler = new CombinedTaskDatabaseHandler(this,null,null,1);
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //  Receiving Information From Intent
        Intent intent_extra = getIntent();
        String task_category_name = intent_extra.getStringExtra("TaskCategoryName");
        int task_category_position = intent_extra.getIntExtra("TaskCategoryPosition",0);

        //  UX Message: Subtitle category name
        TextView subtitle_message = findViewById(R.id.AddTaskSubtitle);
        subtitle_message.setText("Create a new task for '" + task_category_name + "'");

        //  EditText TaskName
        EditText new_task_name_edittext = findViewById(R.id.editTextNewTaskName);

        //  Difficulty Seekbar
        SeekBar difficulty_seekbar = findViewById(R.id.addTaskDifficultySeekbar);
        TextView feedback_simple = findViewById(R.id.diffTextSimple);
        TextView feedback_moderate = findViewById(R.id.diffTextModerate);
        TextView feedback_hard = findViewById(R.id.diffTextHard);

        difficulty_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if (seekBar.getProgress() == 0){
                    feedback_simple.setTextColor(getApplicationContext().getResources().getColorStateList(R.color.simple));
                    feedback_moderate.setTextColor(getApplicationContext().getResources().getColorStateList(R.color.gray));
                    feedback_hard.setTextColor(getApplicationContext().getResources().getColorStateList(R.color.gray));
                }
                if (seekBar.getProgress() == 1){
                    feedback_simple.setTextColor(getApplicationContext().getResources().getColorStateList(R.color.gray));
                    feedback_moderate.setTextColor(getApplicationContext().getResources().getColorStateList(R.color.moderate));
                    feedback_hard.setTextColor(getApplicationContext().getResources().getColorStateList(R.color.gray));
                }
                if (seekBar.getProgress() == 2){
                    feedback_simple.setTextColor(getApplicationContext().getResources().getColorStateList(R.color.gray));
                    feedback_moderate.setTextColor(getApplicationContext().getResources().getColorStateList(R.color.gray));
                    feedback_hard.setTextColor(getApplicationContext().getResources().getColorStateList(R.color.hard));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //  Date Picker Button
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        //  dateButton.setText(getTodaysDate());

        //  Create Task Button
        findViewById(R.id.createTaskBtn).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                //  Task Object Parameters
                String TaskName_value = new_task_name_edittext.getText().toString();
                int Difficulty_value = difficulty_seekbar.getProgress();
                String DueDate_value = dateButton.getText().toString();
                if (TaskName_value.matches("")){
                    new_task_name_edittext.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.hard));
                    Toast.makeText(AddTaskActivity.this, "Please give this task a name!", Toast.LENGTH_SHORT).show();
                    ((ScrollView)findViewById(R.id.scrollview_addtask)).fullScroll(ScrollView.FOCUS_UP);
                }
                else{
                    if (DueDate_value.matches("Select A Date")){
                        DueDate_value = "-";
                    }
                    Task NewTaskObject = new Task (TaskName_value, Difficulty_value, DueDate_value,false, getTodaysDate());
                    static_categorylist.get(task_category_position).getTaskList().add(NewTaskObject);
//                    int i = static_categorylist.get(task_category_position).getTaskList().size();

                    taskcategory_DBhandler.storeTaskList(NewTaskObject,task_category_name,task_category_position);

                    //Notify appWidgetManager to update the widget when user add new task
                    Context context = getApplicationContext();
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    ComponentName thisWidget = new ComponentName(context, TasksWidget.class);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetListView);
                    finish();
                }
            }
        });

        //  Cancel Button
        findViewById(R.id.cancelTaskBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //  All Date Picker Functions
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "Jan";
        if(month == 2)
            return "Feb";
        if(month == 3)
            return "Mar";
        if(month == 4)
            return "Apr";
        if(month == 5)
            return "May";
        if(month == 6)
            return "Jun";
        if(month == 7)
            return "Jul";
        if(month == 8)
            return "Aug";
        if(month == 9)
            return "Sep";
        if(month == 10)
            return "Oct";
        if(month == 11)
            return "Nov";
        if(month == 12)
            return "Dec";

        //default should never happen
        return "Jan";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}