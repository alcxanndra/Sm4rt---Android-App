package com.example.sm4rt.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sm4rt.R;

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

        myNotifyMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
                myNotifyMgr.getNotificationChannel(MYCHANNEL) == null) {
            myNotifyMgr.createNotificationChannel(new NotificationChannel(MYCHANNEL,
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
        String deeplink = "sm4rt.me://discover";
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(deeplink));

//        Intent myIntent = new Intent(this, RandomQuestionActivity.class);
        PendingIntent myPendingIntent = PendingIntent.getActivity(MainActivity.this, 1, myIntent, 0);
        NotificationCompat.Builder myNotifyBuilder = new NotificationCompat.Builder(MainActivity.this, MYCHANNEL);
        myNotifyBuilder.setAutoCancel(false);
        myNotifyBuilder.setTicker("Remember to be Sm4rt!");
        myNotifyBuilder.setContentTitle("Remember to be Sm4rt!");
//        myNotifyBuilder.setContentText("Learn a quick fact today in less than a minute to train your brain!");
        myNotifyBuilder.setContentText("Learn a quick fact today in less than a minute to train your brain!\n" +  deeplink);
        myNotifyBuilder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Learn a quick fact today in less than a minute to train your brain!\n" +  deeplink));
        myNotifyBuilder.setSmallIcon(R.drawable.ic_bulb);
        myNotifyBuilder.setContentIntent(myPendingIntent);
        myNotifyBuilder.build();
        Notification myNotification = myNotifyBuilder.getNotification();
        myNotifyMgr.notify(NOTIFICATION_ID, myNotification);
    }



}