package sg.edu.np.mad.remembertodo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
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
    TaskCategoryAdapater taskcategoryadapter;

    CombinedTaskDatabaseHandler taskcategory_DBhandler = new CombinedTaskDatabaseHandler(this,null,null,1);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                new ArrayList<Task>(
                        Arrays.asList(
                                new Task("Sample", 0, getTodaysDate(), false, getTodaysDate()),
                                new Task("Sample", 0, getTodaysDate(), false, getTodaysDate()),
                                new Task("Sample", 0, getTodaysDate(), false, getTodaysDate()),
                                new Task("Sample", 0, getTodaysDate(), false, getTodaysDate())
                        )
                ),
                "Red"));

        TaskCategoryList.add(new TaskCategory("Others",
                new ArrayList<Task>(
                        Arrays.asList(
                                new Task("Sample", 0, getTodaysDate(), true, getTodaysDate()),
                                new Task("Sample", 0, getTodaysDate(), false, getTodaysDate())
                        )
                ),
                "Blue"));

        TaskCategoryList.add(new TaskCategory("Sample 3",
                new ArrayList<Task>(), "Green"));

        //  Storing Initial Database Storing, DO NOT UNCOMMENT
//        for (int i = 0; i <= TaskCategoryList.size(); i++){
//            taskcategory_DBhandler.storeTaskCategoryList(TaskCategoryList.get(i));
//        }

        //  static_categorylist = TaskCategoryList;
        static_categorylist = taskcategory_DBhandler.getTaskCategoryList();


        // Main RecyclerView for displaying TaskCategories
        RecyclerView rview_cat_holder = findViewById(R.id.rview_category_holder);
        //  Change static_categorylist once db is made
        TaskCategoryAdapater adptr = new TaskCategoryAdapater(static_categorylist, getApplicationContext());
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rview_cat_holder.setLayoutManager(lm);
        rview_cat_holder.setItemAnimator(new DefaultItemAnimator());
        rview_cat_holder.setAdapter(adptr);

        taskcategoryadapter = adptr;

        //  Floating Action Button - Displays Create New Category Alert Dialog
        findViewById(R.id.add_cat_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewTaskActivity.this, AddTaskCategoryActivity.class));
//                AlertDialog.Builder newcategory_alert_dialogbuilder = new AlertDialog.Builder(view.getContext());
//                newcategory_alert_dialogbuilder.setTitle("Create New Task Category");
//                newcategory_alert_dialogbuilder.setMessage("Create");
//
//                newcategory_alert_dialogbuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//
//                newcategory_alert_dialogbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//
//                AlertDialog newcategory_alert_dialog = newcategory_alert_dialogbuilder.create();
//                newcategory_alert_dialog.show();

            }
        });

        //  Timer Activity Btn
        findViewById(R.id.timerActivityBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewTaskActivity.this, TimerActivity.class));
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
    }
}