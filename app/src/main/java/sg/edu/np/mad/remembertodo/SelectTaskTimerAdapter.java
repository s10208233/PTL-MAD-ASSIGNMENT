package sg.edu.np.mad.remembertodo;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectTaskTimerAdapter extends RecyclerView.Adapter<SelectTaskTimerViewHolder> {

    ArrayList<TaskCategory> data;
    Context context;

    public SelectTaskTimerAdapter(ArrayList<TaskCategory> input, Context inputContext) {
        data = input;
        context = inputContext;
    }

    @NonNull
    @Override
    public SelectTaskTimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTaskTimerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
