package sg.edu.np.mad.remembertodo;

import android.content.Intent;
import android.widget.RemoteViewsService;
public class TasksWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new DataProvider(this, intent);
    }
}
