package com.demo.notificationdemo;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String channelId;

    private String channelName;

    private final int level1 = NotificationManager.IMPORTANCE_HIGH;

    private final int level2 = NotificationManager.IMPORTANCE_DEFAULT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel(String channelId, String channelName, int level){
        NotificationChannel channel = new NotificationChannel(channelId,channelName,level);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }
}
