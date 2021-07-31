package sg.edu.np.mad.remembertodo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class ViewTaskActivity extends AppCompatActivity {

    static ArrayList<TaskCategory> static_categorylist;
    static RecyclerView static_rview_cat_holder;
    static TaskCategoryAdapter taskcategoryadapter;
    SharedPreferences sharedPreferences;
    public String GLOBAL_PREFS = "MyPrefs";
    CombinedTaskDatabaseHandler taskcategory_DBhandler = new CombinedTaskDatabaseHandler(this,null,null,1);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        static_categorylist = taskcategory_DBhandler.getTaskCategoryList();

//
//        if (static_categorylist.size() != 0) {
//            sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE);
//            int num = sharedPreferences.getInt("number",0);
//            Log.v("number",String.valueOf(num));
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("Category", static_categorylist.get(num).getTaskCategoryName());
//            editor.apply();
//        }
//
//        //Notify AppWidgetManager to update whenever app starts
//        Context context = getApplicationContext();
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//        ComponentName thisWidget = new ComponentName(context, TasksWidget.class);
//        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetListView);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        ArrayList<TaskCategory> TaskCategoryList = new ArrayList<>();

        //  START Sample
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        SimpleDateFormat simpledateformat = new SimpleDateFormat("MM dd yyyy");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
        LocalDateTime currentDate = LocalDateTime.now();

        Calendar calendar = Calendar.getInstance();
        String currDate = DateFormat.getDateInstance().format(calendar.getTime());
        //  END Sample


        //  Initial Sample Local Data Creation, DO NOT UNCOMMENT
        TaskCategoryList.add(new TaskCategory("Mobile Application Development",
                new ArrayList<Task>(),
                "Red"));

        TaskCategoryList.add(new TaskCategory("Others",
                new ArrayList<Task>(),
                "Blue"));

        TaskCategoryList.add(new TaskCategory("Sample 3",
                new ArrayList<Task>(), "Green"));

        //  Storing Initial Database Storing, DO NOT UNCOMMENT
//        for (int i = 0; i < TaskCategoryList.size(); i++){
//            taskcategory_DBhandler.storeTaskCategoryList(TaskCategoryList.get(i));
//        }

        //  static_categorylist = TaskCategoryList;



        // Main RecyclerView for displaying TaskCategories
        RecyclerView rview_cat_holder = findViewById(R.id.rview_category_holder);
        rview_cat_holder.setNestedScrollingEnabled(false);
        static_rview_cat_holder = rview_cat_holder;
        //  Change static_categorylist once db is made
        TaskCategoryAdapter adptr = new TaskCategoryAdapter(static_categorylist, getApplicationContext());
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rview_cat_holder.setLayoutManager(lm);
        rview_cat_holder.setItemAnimator(new DefaultItemAnimator());
        rview_cat_holder.setAdapter(adptr);

        taskcategoryadapter = adptr;

        //  Timer Btn
        findViewById(R.id.timer_task_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewTaskActivity.this, SelectTaskTimerActivity.class));
            }
        });

        //  Settings BTN
        findViewById(R.id.setting_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewTaskActivity.this, SettingsPage.class));
            }
        });
        //  Floating Action Button - Displays Create New Category Alert Dialog
        findViewById(R.id.add_cat_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewTaskActivity.this, AddTaskCategoryActivity.class));
            }
        });
    }

    //  Date Functions
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
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

    @Override
    protected void onResume() {
        super.onResume();
        taskcategoryadapter.notifyDataSetChanged();

        if (static_categorylist.size() != 0) {
            sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE);
            int num = sharedPreferences.getInt("number",0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Category", static_categorylist.get(num).getTaskCategoryName());
            editor.apply();
        }
        //Notify AppWidgetManager to update whenever app resumes to ViewTaskActivity

        Context context = getApplicationContext();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        //Calls the method OnUpdate to update widget
        Intent intent = new Intent(ViewTaskActivity.this, TasksWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        ComponentName thisWidget = new ComponentName(context, TasksWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        sendBroadcast(intent);
        //Notify AppWidgetManager to update the listview
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetListView);



    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}