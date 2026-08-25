package com.example.janken.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.janken.R;
import com.example.janken.adapter.RecordAdapter;
import com.example.janken.database.GameDatabaseHelper;
import com.example.janken.model.GameRecord;
import com.example.janken.model.GameStatistics;

import java.util.List;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        GameDatabaseHelper dbHelper = new GameDatabaseHelper(this);
        List<GameRecord> records = dbHelper.getRecentRecords();
        dbHelper.close();

        GameStatistics stats = new GameStatistics(records);
        TextView tvHandRates = findViewById(R.id.tv_hand_rates);
        TextView tvResults = findViewById(R.id.tv_results);
        TextView tvHandBreakdown = findViewById(R.id.tv_hand_breakdown);
        tvHandRates.setText(String.format("グー %.1f%%  チョキ %.1f%%  パー %.1f%%",
            stats.getRockRate(), stats.getScissorsRate(), stats.getPaperRate()));
        tvResults.setText(String.format("勝ち: %d    負け: %d    引き分け: %d",
            stats.getWins(), stats.getLosses(), stats.getDraws()));
        tvHandBreakdown.setText(String.format("グー勝率: %.1f%%\nチョキ勝率: %.1f%%\nパー勝率: %.1f%%",
            stats.getRockWinRate(), stats.getScissorsWinRate(), stats.getPaperWinRate()));

        RecyclerView rv = findViewById(R.id.rv_records);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new RecordAdapter(records));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
