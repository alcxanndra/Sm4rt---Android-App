package com.example.sm4rt.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sm4rt.NotificationActivity;
import com.example.sm4rt.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button playButton;
    private static final int NOTIFICATION_ID = 12345;
    private static final String MYCHANNEL = "";
    private int notifyCount = 0;
    private NotificationManager myNotifyMgr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_launcher_background);
        mBuilder.setContentTitle("Notification Alert, Click Me!");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");

        Intent resultIntent = new Intent(this, NotificationActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        myNotifyMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O &&
                myNotifyMgr.getNotificationChannel(MYCHANNEL)==null)
        { myNotifyMgr.createNotificationChannel(new NotificationChannel(MYCHANNEL,
                "My Channel", NotificationManager.IMPORTANCE_DEFAULT));
        }


        playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                raiseNotification(v);
                Intent intent = new Intent(getApplicationContext(), TopicsPage.class);
                startActivity(intent);

            }
        });

    }

    public void raiseNotification(View view) {
        Intent myIntent = new Intent(this, NotificationActivity.class);
        PendingIntent myPendingIntent = PendingIntent.getActivity(MainActivity.this, 1, myIntent, 0);
        NotificationCompat.Builder myNotifyBuilder = new NotificationCompat.Builder(MainActivity.this, MYCHANNEL);
        myNotifyBuilder.setAutoCancel(false);
        myNotifyBuilder.setTicker("Here is your ticker text");
        myNotifyBuilder.setContentTitle("Remember to be Sm4rt!");
        myNotifyBuilder.setContentText("Learn a quick fact today in less than a minute to train your brain!");
        myNotifyBuilder.setSmallIcon(R.drawable.ic_bulb);
        myNotifyBuilder.setContentIntent(myPendingIntent);
        myNotifyBuilder.build();
        Notification myNotification = myNotifyBuilder.getNotification();
        myNotifyMgr.notify(NOTIFICATION_ID, myNotification);
    }



}