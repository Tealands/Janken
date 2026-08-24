package com.example.janken;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.janken.activity.PlayActivity;
import com.example.janken.activity.RecordActivity;
import com.example.janken.activity.SettingActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPlay    = findViewById(R.id.btn_play);
        Button btnRecord  = findViewById(R.id.btn_record);
        Button btnSetting = findViewById(R.id.btn_setting);

        btnPlay.setOnClickListener(v ->
                startActivity(new Intent(this, PlayActivity.class)));
        btnRecord.setOnClickListener(v ->
                startActivity(new Intent(this, RecordActivity.class)));
        btnSetting.setOnClickListener(v ->
                startActivity(new Intent(this, SettingActivity.class)));
    }
}
