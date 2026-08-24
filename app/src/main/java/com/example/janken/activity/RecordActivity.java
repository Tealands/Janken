package com.example.janken.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.janken.R;
import com.example.janken.adapter.RecordAdapter;
import com.example.janken.database.GameDatabaseHelper;
import com.example.janken.model.GameRecord;

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
