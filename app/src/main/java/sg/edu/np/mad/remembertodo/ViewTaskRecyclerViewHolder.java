package sg.edu.np.mad.remembertodo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewTaskRecyclerViewHolder extends RecyclerView.ViewHolder {

    RecyclerView ViewCategory_rview;

    public ViewTaskRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        ViewCategory_rview = itemView.findViewById(R.id.rview_category_holder);
    }
}
