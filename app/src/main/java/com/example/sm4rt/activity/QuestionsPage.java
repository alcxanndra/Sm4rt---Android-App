package com.example.sm4rt.activity;

import static com.example.sm4rt.fragment.QuestionListFragment.QUESTION;
import static com.example.sm4rt.fragment.TopicListFragment.TOPIC_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sm4rt.AppModule;
import com.example.sm4rt.DaggerAppComponent;
import com.example.sm4rt.R;
import com.example.sm4rt.RoomModule;
import com.example.sm4rt.database.data.Question;
import com.example.sm4rt.database.data.Topic;
import com.example.sm4rt.database.repository.QuestionRepository;
import com.example.sm4rt.fragment.QuestionFragment;
import com.example.sm4rt.fragment.QuestionListFragment;
import com.example.sm4rt.fragment.TopicListFragment;
import com.example.sm4rt.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class QuestionsPage extends AppCompatActivity {

    @Inject
    QuestionRepository questionRepository;

    public static String QUESTIONS_LIST = "questions";

    ArrayList<Question> questionsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_page);

        String topicName = getIntent().getStringExtra(TOPIC_NAME);

        DaggerAppComponent.builder()
                .appModule(new AppModule(getApplication()))
                .roomModule(new RoomModule(getApplication()))
                .build()
                .inject(this);

        new FindQuestionsRequest().execute(topicName);

    }

    private class FindQuestionsRequest extends AsyncTask<String, Void, List<Question>> {
        @Override
        protected List<Question> doInBackground(String... topics) {
            String topicName = topics[0];
            return questionRepository.findByTopic(topicName);
        }

        @Override
        protected void onPostExecute(List<Question> questions) {
            questionsList.clear();
            questionsList.addAll(questions);

            Bundle bundle = new Bundle();
            bundle.putParcelableArray(QUESTIONS_LIST, questionsList.toArray(new Question[0]));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            QuestionListFragment secondFragment = new QuestionListFragment();
            secondFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, secondFragment)
                    .addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}