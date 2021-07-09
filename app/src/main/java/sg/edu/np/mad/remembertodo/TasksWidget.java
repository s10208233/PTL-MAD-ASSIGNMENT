package sg.edu.np.mad.remembertodo;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class TasksWidget extends AppWidgetProvider {
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        //Instantiate the RemoteViews object//
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.tasks_widget);
        setRemoteAdapter(context, views);  //Request that the AppWidgetManager updates the application widget//
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widgetListView, new Intent(context, TasksWidgetService.class));
        views.setInt(R.id.widgetContainer,"setBackgroundResource",R.drawable.rounded_corners_for_widget);
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(
                    context.getPackageName(),
                    R.layout.tasks_widget);
            //Update all instances of this widget//
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    @Override
    public void onEnabled(Context context) {
        Toast.makeText(context,"Widget created successfully", Toast.LENGTH_LONG).show();
    }
    @Override public void onDisabled(Context context) {
        Toast.makeText(context,"All widgets deleted", Toast.LENGTH_LONG).show();
    }


}