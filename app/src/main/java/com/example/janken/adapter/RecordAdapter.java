package com.example.janken.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.janken.R;
import com.example.janken.model.GameRecord;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private final List<GameRecord> records;

    public RecordAdapter(List<GameRecord> records) {
        this.records = records;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_record, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameRecord r = records.get(position);
        holder.tvTimestamp.setText(r.getTimestamp());
        holder.tvPlayerHand.setText(r.getPlayerHand());
        holder.tvCpuHand.setText(r.getCpuHand());
        holder.tvResult.setText(r.getResult());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTimestamp, tvPlayerHand, tvCpuHand, tvResult;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimestamp  = itemView.findViewById(R.id.tv_timestamp);
            tvPlayerHand = itemView.findViewById(R.id.tv_player_hand);
            tvCpuHand    = itemView.findViewById(R.id.tv_cpu_hand);
            tvResult     = itemView.findViewById(R.id.tv_result);
        }
    }
}
