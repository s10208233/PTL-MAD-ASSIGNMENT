package sg.edu.np.mad.remembertodo;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import static android.R.id.text1;
import static android.R.layout.simple_list_item_1;
public class DataProvider implements RemoteViewsService.RemoteViewsFactory {
    List<String> myListView = new ArrayList<>();
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
            return myListView.size();
        }
        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews view = new RemoteViews(mContext.getPackageName(), R.layout.list_taskswidget);
            view.setTextViewText(R.id.widgetItemTaskNameLabel, myListView.get(position));
            view.setInt(R.id.widgetItemTaskNameLabel,"setBackgroundResource",R.drawable.rounded_corners);
            return view;
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
        myListView.clear();
        for (int i = 1; i <= 15; i++) {
            myListView.add("Task " + i);
        }
    }
}
