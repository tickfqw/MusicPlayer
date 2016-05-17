package com.example.tick.tick;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Button;

import java.io.File;

/**
 * Created by tick on 2016/5/16.
 */
public class MyService extends Service {

    private mBinder binder = new mBinder();
    private MediaPlayer mediaPlayer = new MediaPlayer();

    private void initMediaPlayer() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initMediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

    }

    class mBinder extends Binder{
        public void start(){
            if(!mediaPlayer.isPlaying()){
                mediaPlayer.start();
            }else{
                mediaPlayer.pause();
            }
        }
        public void stop(){
            mediaPlayer.reset();
            initMediaPlayer();
        }
        public void pause(){
            mediaPlayer.pause();
        }
        public boolean isPlaying(){
            return mediaPlayer.isPlaying();
        }
    }
}
