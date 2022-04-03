package com.example.sm4rt.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sm4rt.AppModule;
import com.example.sm4rt.DaggerAppComponent;
import com.example.sm4rt.Executor;
import com.example.sm4rt.R;
import com.example.sm4rt.RoomModule;
import com.example.sm4rt.database.data.Question;
import com.example.sm4rt.database.data.Topic;
import com.example.sm4rt.database.repository.QuestionRepository;
import com.example.sm4rt.database.repository.TopicRepository;
import com.example.sm4rt.util.Util;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.ListenableFuture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    TopicRepository topicRepository;

    @Inject
    QuestionRepository questionRepository;

    private Button playButton;

    private static final int NOTIFICATION_ID = 12345;
    private static final String MY_CHANNEL = "";
    private int notifyCount = 0;
    private NotificationManager notificationManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerAppComponent.builder()
                .appModule(new AppModule(getApplication()))
                .roomModule(new RoomModule(getApplication()))
                .build()
                .inject(this);


        playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                raiseNotification(v);
                Intent intent = new Intent(getApplicationContext(), TopicsPage.class);
                startActivity(intent);

            }
        });

        new InsertTopicRequest().execute(readTopics());
        new InsertQuestionRequest().execute(readQuestions());
    }

    private class InsertTopicRequest extends AsyncTask<Topic, Void, String> {
        @Override
        protected String doInBackground(Topic... topics) {
            try {
                if (topicRepository.count() == 0)
                    topicRepository.insert(topics);
            } catch(Exception e){
                return "error";
            }
            return "success";
        }
//        @Override
//        protected void onPostExecute(String result) {
//            System.out.println(result);
//        }
    }

    private class InsertQuestionRequest extends AsyncTask<Question, Void, String> {
        @Override
        protected String doInBackground(Question... questions) {
            try {
                if (questionRepository.count() == 0)
                    questionRepository.insert(questions);
            } catch(Exception e){
                return "error";
            }
            return "success";
        }

//        @Override
//        protected void onPostExecute(String result) {
//            System.out.println(result);
//        }
    }

    public void raiseNotification(View view) {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
                notificationManager.getNotificationChannel(MY_CHANNEL) == null) {
            notificationManager.createNotificationChannel(new NotificationChannel(MY_CHANNEL,
                    "My Channel", NotificationManager.IMPORTANCE_DEFAULT));
        }

        String deeplink = "sm4rt.me://discover";
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(deeplink));

//        Intent myIntent = new Intent(this, RandomQuestionActivity.class);
        PendingIntent myPendingIntent = PendingIntent.getActivity(MainActivity.this, 1, myIntent, 0);
        NotificationCompat.Builder myNotifyBuilder = new NotificationCompat.Builder(MainActivity.this, MY_CHANNEL);
        myNotifyBuilder.setAutoCancel(false);
        myNotifyBuilder.setTicker("Remember to be Sm4rt!");
        myNotifyBuilder.setContentTitle("Remember to be Sm4rt!");
        myNotifyBuilder.setContentText("Learn a quick fact today in less than a minute to train your brain!\n");
        myNotifyBuilder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Learn a quick fact today in less than a minute to train your brain!\n"));
        myNotifyBuilder.setSmallIcon(R.drawable.ic_bulb);
        myNotifyBuilder.setContentIntent(myPendingIntent);
        myNotifyBuilder.build();
        Notification myNotification = myNotifyBuilder.getNotification();
        notificationManager.notify(NOTIFICATION_ID, myNotification);
    }

    Topic[] readTopics() {
        List<Topic> topicList = new ArrayList<>();
        try {
            JSONArray array = Util.loadJSONFromAsset(this, "topics.json");

            for (int i = 0; i < array.length(); i++) {
                JSONObject topicJson = array.getJSONObject(i);
                String topicName = topicJson.getString("name");
                String topicDescription = topicJson.getString("description");
                String topicImage = topicJson.getString("image");
                topicList.add(new Topic(topicName, topicDescription, Util.getImage(this, topicImage)));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return topicList.toArray(new Topic[0]);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    Question[] readQuestions(){
        List<Question> questionList = new ArrayList<>();

        try {
            JSONArray array = Util.loadJSONFromAsset(this, "questions.json");

            for (int i = 0; i < array.length(); i++) {
                JSONObject questionJson = array.getJSONObject(i);
                String questionTitle = questionJson.getString("title");
                String questionTopic = questionJson.getString("topic");
                String questionAnswer = questionJson.getString("answer");
                questionList.add(new Question(questionTopic, questionTitle, questionAnswer));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        System.out.println("No of questions inserted: " + questionList.size());
        questionList.forEach(question -> System.out.println("Question topic: " + question.getTopic()));
        return questionList.toArray(new Question[0]);
    }
}