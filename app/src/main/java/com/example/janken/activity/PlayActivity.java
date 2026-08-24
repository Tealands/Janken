package com.example.janken.activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.janken.R;
import com.example.janken.database.GameDatabaseHelper;
import com.example.janken.model.GameRecord;
import com.example.janken.model.JankenGame;
import com.example.janken.model.JankenGame.Hand;
import com.example.janken.model.JankenGame.Result;
import com.example.janken.util.SoundManager;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PlayActivity extends AppCompatActivity {

    private static final int COUNTDOWN_SECONDS = 5;

    private TextView tvCountdown, tvScore, tvResult;
    private ImageView ivPlayerHand, ivCpuHand;
    private Button btnRock, btnScissors, btnPaper;

    private JankenGame game;
    private SoundManager soundManager;
    private GameDatabaseHelper dbHelper;
    private CountDownTimer timer;
    private Hand selectedHand = Hand.UNKNOWN;
    private boolean waiting = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        tvCountdown  = findViewById(R.id.tv_countdown);
        tvScore      = findViewById(R.id.tv_score);
        tvResult     = findViewById(R.id.tv_result);
        ivPlayerHand = findViewById(R.id.iv_player_hand);
        ivCpuHand    = findViewById(R.id.iv_cpu_hand);
        btnRock      = findViewById(R.id.btn_rock);
        btnScissors  = findViewById(R.id.btn_scissors);
        btnPaper     = findViewById(R.id.btn_paper);

        game     = new JankenGame();
        dbHelper = new GameDatabaseHelper(this);

        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        float vol = prefs.getFloat("volume", 1.0f);
        soundManager = new SoundManager(this);
        soundManager.setVolume(vol);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnRock.setOnClickListener(v     -> selectHand(Hand.ROCK));
        btnScissors.setOnClickListener(v -> selectHand(Hand.SCISSORS));
        btnPaper.setOnClickListener(v    -> selectHand(Hand.PAPER));

        startRound();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startRound() {
        selectedHand = Hand.UNKNOWN;
        waiting = true;
        tvResult.setText("");
        tvScore.setText(game.getPlayerScore() + " - " + game.getCpuScore());
        setHandImage(ivPlayerHand, Hand.UNKNOWN);
        setHandImage(ivCpuHand, Hand.UNKNOWN);
        setButtonsEnabled(true);
        startCountdown();
    }

    private void startCountdown() {
        soundManager.playCountdown();
        timer = new CountDownTimer(COUNTDOWN_SECONDS * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCountdown.setText(String.valueOf(millisUntilFinished / 1000 + 1));
            }

            @Override
            public void onFinish() {
                tvCountdown.setText("0");
                waiting = false;
                resolveRound();
            }
        }.start();
    }

    private void selectHand(Hand hand) {
        if (!waiting) return;
        selectedHand = hand;
        setHandImage(ivPlayerHand, hand);
    }

    private void resolveRound() {
        setButtonsEnabled(false);

        if (selectedHand == Hand.UNKNOWN) {
            selectedHand = Hand.ROCK;
        }

        Hand cpuHand = game.decideCpuHand();
        Result result = game.judge(selectedHand, cpuHand);
        game.applyResult(result);

        setHandImage(ivPlayerHand, selectedHand);
        setHandImage(ivCpuHand, cpuHand);

        String resultStr = JankenGame.resultToString(result);
        tvResult.setText(resultStr);
        tvScore.setText(game.getPlayerScore() + " - " + game.getCpuScore());

        switch (result) {
            case WIN:  soundManager.playWin();  break;
            case LOSE: soundManager.playLose(); break;
            default:   soundManager.playDraw(); break;
        }

        String timestamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPAN).format(new Date());
        GameRecord record = new GameRecord(
                JankenGame.handToString(selectedHand),
                JankenGame.handToString(cpuHand),
                resultStr,
                timestamp
        );
        dbHelper.insertRecord(record);

        if (game.isGameOver()) {
            showGameOverDialog();
        } else {
            tvCountdown.postDelayed(this::startRound, 2000);
        }
    }

    private void showGameOverDialog() {
        String message = game.isPlayerWinner() ? "あなたの勝ち！" : "CPUの勝ち！";
        new AlertDialog.Builder(this)
                .setTitle("ゲーム終了")
                .setMessage(message)
                .setPositiveButton("もう一度", (d, w) -> {
                    game.reset();
                    startRound();
                })
                .setNegativeButton("終了", (d, w) -> finish())
                .setCancelable(false)
                .show();
    }

    private void setHandImage(ImageView iv, Hand hand) {
        String fileName;
        switch (hand) {
            case ROCK:     fileName = "image/Rock.jpg";     break;
            case SCISSORS: fileName = "image/Scissors.jpg"; break;
            case PAPER:    fileName = "image/Paper.jpg";    break;
            default:       iv.setImageResource(android.R.color.transparent); return;
        }
        try (InputStream is = getAssets().open(fileName)) {
            Bitmap bmp = BitmapFactory.decodeStream(is);
            iv.setImageBitmap(bmp);
        } catch (IOException e) {
            iv.setImageResource(android.R.color.darker_gray);
        }
    }

    private void setButtonsEnabled(boolean enabled) {
        btnRock.setEnabled(enabled);
        btnScissors.setEnabled(enabled);
        btnPaper.setEnabled(enabled);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
        soundManager.release();
        dbHelper.close();
    }
}
