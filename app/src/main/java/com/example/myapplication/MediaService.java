package com.example.myapplication;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

//Музыка
public class MediaService extends Service {
    MediaPlayer menuMediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        menuMediaPlayer = MediaPlayer.create(this, R.raw.spacemusic);
        menuMediaPlayer.setLooping(true);
    }

    @Override
    public void onDestroy() {
        if (menuMediaPlayer.isPlaying()) {
            menuMediaPlayer.stop();
        }
        menuMediaPlayer.release();
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        menuMediaPlayer.start();
    }
}
