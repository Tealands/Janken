package com.example.janken.activity;

import android.os.Bundle;

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

        GameDatabaseHelper dbHelper = new GameDatabaseHelper(this);
        List<GameRecord> records = dbHelper.getRecentRecords();
        dbHelper.close();

        RecyclerView rv = findViewById(R.id.rv_records);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new RecordAdapter(records));
    }
}
