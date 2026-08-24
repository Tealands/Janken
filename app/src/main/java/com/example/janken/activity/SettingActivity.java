package com.example.janken.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.janken.R;

public class SettingActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "settings";
    private static final String KEY_VOLUME = "volume";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        float currentVol = prefs.getFloat(KEY_VOLUME, 1.0f);

        SeekBar seekBar   = findViewById(R.id.seekbar_volume);
        TextView tvVolume = findViewById(R.id.tv_volume_value);

        seekBar.setMax(100);
        int progress = Math.round(currentVol * 100);
        seekBar.setProgress(progress);
        tvVolume.setText(progress + "%");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar sb, int p, boolean fromUser) {
                tvVolume.setText(p + "%");
                prefs.edit().putFloat(KEY_VOLUME, p / 100f).apply();
            }

            @Override public void onStartTrackingTouch(SeekBar sb) {}
            @Override public void onStopTrackingTouch(SeekBar sb) {}
        });
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
