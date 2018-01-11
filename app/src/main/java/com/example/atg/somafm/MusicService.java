package com.example.atg.somafm;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by atg on 06.01.18.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    private MediaPlayer player = null;
    private final IBinder musicBind = new MusicBinder();

    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent){
        if(player != null) {
            player.stop();
            player.release();
        }
        return false;
    }

    public void onCreate() {
        super.onCreate();

        player = new MediaPlayer();
        initMusicPlayer();
    };

    @Override
    public void onDestroy() {
        stop();
        super.onDestroy();
    }

    public void initMusicPlayer(){
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        player.setOnPreparedListener(this);
        player.setOnErrorListener(this);
    }

    public void play(String url) {
        stop();
        try {
            player.setDataSource(url);
        }
        catch(Exception e) {
            Log.e("Music Service", "Error setting data source ", e);
        }
        player.prepareAsync();
    }

    public void stop() {
        player.stop();
        player.reset();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        Toast.makeText(this, "MediaPlayer ERROR", Toast.LENGTH_LONG);
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        player.start();
    }
}
