package sg.edu.np.mad.remembertodo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import static sg.edu.np.mad.remembertodo.ViewTaskActivity.static_categorylist;
import java.util.ArrayList;
import java.util.List;

public class DataProvider implements RemoteViewsService.RemoteViewsFactory {
    List<TaskCategory> myListView = new ArrayList<>();
    Context mContext = null;
    public DataProvider(Context context, Intent intent) {
        mContext = context; }
        @Override
        public void onCreate() {
            initData();
        }
        @Override
        public void onDataSetChanged() {
            initData();
        }
        @Override
        public void onDestroy() {

        }
        @Override
        public int getCount() {
            if (myListView == null) {
                return 0;
            }
        return myListView.size();
        }
        @Override
        public RemoteViews getViewAt(int position) {
            String str = "";
            RemoteViews view_category = new RemoteViews(mContext.getPackageName(), R.layout.category_list_widget);
            view_category.setInt(R.id.widgetItemContainer,"setBackgroundColor", Color.parseColor(colorNameToCode(myListView.get(position).getColorCode())));
            view_category.setTextViewText(R.id.widgetTitleLabel, myListView.get(position).getTaskCategoryName());
            for (int i = 0; i < myListView.get(position).getTaskList().size(); i++){
                str += myListView.get(position).getTaskList().get(i).getTaskName() + " by " + myListView.get(position).getTaskList().get(i).getDueDate() + "\n";
            }
            view_category.setTextViewText(R.id.task_list, str);

//            String str ="MOther,MOther,Mother";
//            view_category.setTextViewText(R.id.task_list, str.replaceAll(",","\n"));

//            LinearLayout linearLayout = findViewById(R.id.linearContainer) ;
//            for (int i = 0; i <= 3; i++){
//                TextView textView = new TextView(this);
//                textView.setText("mother");
//                linearLayout.addView(textView);
//            }


            return view_category;
        }
        public String colorNameToCode(String sel){
            if(sel.matches("Red"))      {return "#850000";}
            if(sel.matches("Green"))    {return "#4F9300";}
            if(sel.matches("Blue"))     {return "#0057B5";}
            if(sel.matches("Purple"))   {return "#5A2DA8";}
            if(sel.matches("Yellow"))   {return "#D3A20B";}
            if(sel.matches("Orange"))   {return "#D67806";}
            if(sel.matches("Black"))    {return "#000000";}
            return "#404040";
        }
        @Override
        public RemoteViews getLoadingView() {
        return null;
        }
        @Override
        public int getViewTypeCount() {
        return 1;
        }
        @Override
        public long getItemId(int position) {
        return position;
        }
        @Override
        public boolean hasStableIds() {
        return true;
        }
        private void initData() {
        myListView = static_categorylist;
    }
}
