package com.example.janken.activity;

import android.os.Bundle;
<<<<<<< HEAD
import android.widget.Button;
=======
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
>>>>>>> d3ae9a76517b0a08ede7534b9732b099c415a351
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.janken.R;
import com.example.janken.database.GameDatabaseHelper;
import com.example.janken.model.GameRecord;
import com.example.janken.model.GameStatistics;

import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        GameDatabaseHelper dbHelper = new GameDatabaseHelper(this);
        List<GameRecord> records = dbHelper.getRecentRecords();
        dbHelper.close();

        TextView tvNoData       = findViewById(R.id.tv_no_data);
        TableLayout tableLayout = findViewById(R.id.table_stats);

        if (records.isEmpty()) {
            tvNoData.setVisibility(View.VISIBLE);
            tableLayout.setVisibility(View.GONE);
            return;
        }

        tvNoData.setVisibility(View.GONE);
        tableLayout.setVisibility(View.VISIBLE);

        GameStatistics stats = new GameStatistics(records);

        TextView tvRockRate     = findViewById(R.id.tv_rock_rate);
        TextView tvScissorsRate = findViewById(R.id.tv_scissors_rate);
        TextView tvPaperRate    = findViewById(R.id.tv_paper_rate);
        TextView tvRockWin      = findViewById(R.id.tv_rock_win_rate);
        TextView tvScissorsWin  = findViewById(R.id.tv_scissors_win_rate);
        TextView tvPaperWin     = findViewById(R.id.tv_paper_win_rate);
        TextView tvHandRates    = findViewById(R.id.tv_hand_rates);
        TextView tvResults      = findViewById(R.id.tv_results);

        tvHandRates.setText(String.format("グー %.1f%%  チョキ %.1f%%  パー %.1f%%",
            stats.getRockRate(), stats.getScissorsRate(), stats.getPaperRate()));
        tvResults.setText(String.format("勝ち: %d    負け: %d    引き分け: %d",
            stats.getWins(), stats.getLosses(), stats.getDraws()));
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        tvRockRate.setText(String.format("%.0f%%", stats.getRockRate()));
        tvScissorsRate.setText(String.format("%.0f%%", stats.getScissorsRate()));
        tvPaperRate.setText(String.format("%.0f%%", stats.getPaperRate()));
        tvRockWin.setText(String.format("%.0f%%", stats.getRockWinRate()));
        tvScissorsWin.setText(String.format("%.0f%%", stats.getScissorsWinRate()));
        tvPaperWin.setText(String.format("%.0f%%", stats.getPaperWinRate()));
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
