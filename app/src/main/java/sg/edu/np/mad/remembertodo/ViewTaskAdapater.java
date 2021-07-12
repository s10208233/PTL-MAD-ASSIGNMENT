package sg.edu.np.mad.remembertodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewTaskAdapater extends RecyclerView.Adapter<ViewTaskRecyclerViewHolder> {

    ArrayList<TaskCategory> data;
    Context context;

    public ViewTaskAdapater(ArrayList<TaskCategory> input, Context inputContext) {
        data = input;
        context = inputContext;
    }

    @NonNull
    @Override
    public ViewTaskRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_single_category, parent, false);
        return new ViewTaskRecyclerViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewTaskRecyclerViewHolder holder, int position) {
        //  GET THE INNER VIEWHOLDER WORKING HERE
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
