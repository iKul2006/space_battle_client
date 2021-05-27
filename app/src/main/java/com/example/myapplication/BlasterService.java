package com.example.myapplication;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

//Класс для звука бластера (сейчас он не работает)
public class BlasterService extends Service {
    MediaPlayer blaster;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        blaster = MediaPlayer.create(this, R.raw.blaster);
        blaster.setLooping(true);
    }

    @Override
    public void onDestroy() {
        if (blaster.isPlaying()) {
            blaster.stop();
        }
        blaster.release();
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        blaster.start();
    }
}
