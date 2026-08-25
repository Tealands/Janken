package com.example.janken.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

public class SoundManager {

    private final SoundPool soundPool;
    private int soundHand = -1;
    private int soundWin = -1;
    private int soundLose = -1;
    private int soundDraw = -1;
    private int soundCountdown = -1;
    private float volume = 1.0f;

    @SuppressWarnings("deprecation")
    public SoundManager(Context context) {
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        soundHand = loadAsset(context, "sound/JapaneseDram.mp3");
        soundWin = loadAsset(context, "sound/Fanfare.mp3");
        soundLose = loadAsset(context, "sound/Done.mp3");
    }
    public void playHand() {
        if (soundHand >= 0) soundPool.play(soundHand, volume, volume, 1, 0, 1f);
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

    private int loadAsset(Context context, String fileName) {
        try (AssetFileDescriptor descriptor = context.getAssets().openFd(fileName)) {
            return soundPool.load(descriptor, 1);
        } catch (IOException e) {
            return -1;
        }
    }
}
