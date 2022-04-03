package com.example.sm4rt.activity;

import static com.example.sm4rt.fragment.QuestionListFragment.QUESTION;

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

public class RandomQuestionActivity extends AppCompatActivity{

    @Inject
    QuestionRepository questionRepository;

    ArrayList<Question> questionsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_question);

        DaggerAppComponent.builder()
                .appModule(new AppModule(getApplication()))
                .roomModule(new RoomModule(getApplication()))
                .build()
                .inject(this);

        new Request().execute();
    }

    private class Request extends AsyncTask<Void, Void, List<Question>> {
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