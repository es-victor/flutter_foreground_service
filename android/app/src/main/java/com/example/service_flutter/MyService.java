package com.example.service_flutter;

import static com.example.service_flutter.MyApplication.CHANNEL_ID;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.app.Notification;
import android.app.PendingIntent;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Date;

public class MyService extends Service {
    private MediaPlayer player;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();

            if(player.isPlaying()){
                player.pause();
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            if(!player.isPlaying()) {
                player.setLooping(true);
                player.start();
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        player = MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI);

        Date currentTime = Calendar.getInstance().getTime();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent,0);
        Notification  notification = new NotificationCompat.Builder(this, CHANNEL_ID )
                .setContentTitle("Example Service Title")
                .setContentText( currentTime.toString())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
               .setAutoCancel(true)
                .build();

        startForeground(1,notification);
        return START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }
}