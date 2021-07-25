package sg.edu.np.mad.remembertodo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingsPage extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    static ArrayList<TaskCategory> static_categorylist;
    ListView listView;
    CombinedTaskDatabaseHandler taskcategory_DBhandler = new CombinedTaskDatabaseHandler(this,null,null,1);
    public String GLOBAL_PREFS = "MyPrefs";
    protected void onCreate(Bundle savedInstanceState) {

        static_categorylist = taskcategory_DBhandler.getTaskCategoryList();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        findViewById(R.id.customDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsPage.this);
                builder.setTitle("Choose a category");

                // add a radio button list

                ArrayList<String> values = new ArrayList<String>();
                for (int i = 0; i < static_categorylist.size(); i++){
                    values.add(static_categorylist.get(i).getTaskCategoryName());

                }
                String[] category = values.toArray(new String[0]);

                int checkedItem = -1; //-1 because i do not want default checked button
                builder.setSingleChoiceItems(category , checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user checked an item
                        int count = 0;
                        String colour = "";
                        if (static_categorylist != null){
                            count = static_categorylist.size();
                        }
                        for (int i = 0; i < count;i++) {
                            if (static_categorylist.get(i).getTaskCategoryName() == category[which]) {
                                colour = static_categorylist.get(i).getColorCode();
                            }
                        }
                        editor.putString("Category", category[which]);
                        editor.putString("Color", colour);
                        editor.apply();
                    }
                });

                // add OK and Cancel buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        // user clicked OK


                        //Notify AppWidgetManager to update when user clicks ok
                        Context context = getApplicationContext();
                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                        Intent intent = new Intent(SettingsPage.this, TasksWidget.class);
                        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
                        // since it seems the onUpdate() is only fired on that:
                        ComponentName thisWidget = new ComponentName(context, TasksWidget.class);
                        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
                        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
                        sendBroadcast(intent);

                    }
                });
                builder.setNegativeButton("Cancel", null);

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}