package com.example.janken.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.janken.R;

public class SoundManager {

    private final SoundPool soundPool;
    private int soundWin = -1;
    private int soundLose = -1;
    private int soundDraw = -1;
    private int soundCountdown = -1;
    private float volume = 1.0f;

    @SuppressWarnings("deprecation")
    public SoundManager(Context context) {
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        // Load sounds if they exist in res/raw
        // Files: win.mp3, lose.mp3, draw.mp3, countdown.mp3
        // Uncomment when audio files are added:
        // soundWin      = soundPool.load(context, R.raw.win, 1);
        // soundLose     = soundPool.load(context, R.raw.lose, 1);
        // soundDraw     = soundPool.load(context, R.raw.draw, 1);
        // soundCountdown= soundPool.load(context, R.raw.countdown, 1);
    }

    public void setVolume(float volume) {
        this.volume = Math.max(0f, Math.min(1f, volume));
    }

    public void playWin() {
        if (soundWin >= 0) soundPool.play(soundWin, volume, volume, 1, 0, 1f);
    }

    public void playLose() {
        if (soundLose >= 0) soundPool.play(soundLose, volume, volume, 1, 0, 1f);
    }

    public void playDraw() {
        if (soundDraw >= 0) soundPool.play(soundDraw, volume, volume, 1, 0, 1f);
    }

    public void playCountdown() {
        if (soundCountdown >= 0) soundPool.play(soundCountdown, volume, volume, 1, 0, 1f);
    }

    public void release() {
        soundPool.release();
    }
}
