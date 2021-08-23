package com.contact.tracing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.contact.tracing.R;

import org.jetbrains.annotations.NotNull;


public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {

    private Context context;
    public DashboardAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new DashboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DashboardViewHolder holder, int position) {
        holder.textView.setText(String.format("Patient #%d", position + 1));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class DashboardViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public DashboardViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.patient_list);

        }
    }
}
