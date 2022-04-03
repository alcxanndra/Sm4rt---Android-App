package com.example.sm4rt.activity;

import static com.example.sm4rt.fragment.QuestionListFragment.QUESTION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.os.Parcelable;
import android.view.MenuItem;

import com.example.sm4rt.AppModule;
import com.example.sm4rt.DaggerAppComponent;
import com.example.sm4rt.R;
import com.example.sm4rt.RoomModule;
import com.example.sm4rt.database.data.Question;
import com.example.sm4rt.database.data.Topic;
import com.example.sm4rt.database.repository.QuestionRepository;
import com.example.sm4rt.database.repository.TopicRepository;
import com.example.sm4rt.fragment.QuestionFragment;
import com.example.sm4rt.fragment.TopicListFragment;
import com.example.sm4rt.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TopicsPage extends AppCompatActivity {

    @Inject
    TopicRepository topicRepository;

    @Inject
    QuestionRepository questionRepository;

    public static String TOPICS_LIST = "topics";

    ArrayList<Topic> topicList = new ArrayList<>();
    ArrayList<Question> questionsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_page);

        DaggerAppComponent.builder()
                .appModule(new AppModule(getApplication()))
                .roomModule(new RoomModule(getApplication()))
                .build()
                .inject(this);

        new FindTopicsRequest().execute();
    }

    private class FindTopicsRequest extends AsyncTask<Void, Void, List<Topic>> {
        @Override
        protected List<Topic> doInBackground(Void... voids) {
            return topicRepository.findAll();
        }

        @Override
        protected void onPostExecute(List<Topic> topics) {
            topicList.clear();
            topicList.addAll(topics);

            Bundle bundle = new Bundle();
            bundle.putParcelableArray(TOPICS_LIST, topicList.toArray(new Topic[0]));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            TopicListFragment secondFragment = new TopicListFragment();
            secondFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, secondFragment)
                    .addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class FindQuestionsRequest extends AsyncTask<Void, Void, List<Question>> {
        @Override
        protected List<Question> doInBackground(Void... voids) {
            return questionRepository.findAll();
        }

        @Override
        protected void onPostExecute(List<Question> questions) {
            questionsList.clear();
            questionsList.addAll(questions);

            Bundle bundle = new Bundle();
            bundle.putParcelable(QUESTION, getRandomQuestion());
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            QuestionFragment questionFragment = new QuestionFragment();
            questionFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, questionFragment)
                    .addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private Question getRandomQuestion() {
        return questionsList.get((int) (Math.random() * questionsList.size()));
    }

}