package com.demo.notificationdemo;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private String channelId;

    private String channelName;

    private int sum = 0;

    private final int level1 = NotificationManager.IMPORTANCE_HIGH;

    private final int level2 = NotificationManager.IMPORTANCE_DEFAULT;

    @OnClick({R.id.button1, R.id.button2})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                sum += 1;
                sendPopUpMsg(view, sum);
                break;
            case R.id.button2:
                sum += 1;
                sendNonPopUpMsg(view, sum);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = "popUpMsg";
            channelName = "弹出式消息";
            createChannel(channelId, channelName, level1);

            channelId = "nonPopUpMsg";
            channelName = "非弹出式消息";
            createChannel(channelId, channelName, level2);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel(String channelId, String channelName, int level) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, level);
        channel.setShowBadge(true);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }

    private void sendPopUpMsg(View view, int num) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            yzMsg(manager.getNotificationChannel("popUpMsg"));
        }
        Notification notification = new NotificationCompat.Builder(this, "popUpMsg")
                .setContentTitle("测试弹出消息")
                .setContentText("这是一条弹出式的消息框")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setNumber(num)
                .build();
        Log.e("TAG", "sendPopUpMsg: " + num );
        manager.notify(1, notification);
    }

    private void sendNonPopUpMsg(View view, int num) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            yzMsg(manager.getNotificationChannel("nonPopUpMsg"));
        }
        Notification notification = new NotificationCompat.Builder(this, "nonPopUpMsg")
                .setContentTitle("测试非弹出消息")
                .setContentText("这是一条非弹出式消息")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setNumber(num)
                .build();
        Log.e("TAG", "sendNonPopUpMsg: " + num );
        manager.notify(2, notification);
    }

    private void yzMsg(NotificationChannel channel) {
        if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
            Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
            startActivity(intent);
        }
    }
}
