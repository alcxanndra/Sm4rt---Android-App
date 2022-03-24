package com.example.sm4rt.activity;

import static com.example.sm4rt.fragment.QuestionListFragment.QUESTION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.sm4rt.R;
import com.example.sm4rt.fragment.QuestionFragment;
import com.example.sm4rt.model.QuestionModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class QuestionsPage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_page);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

            case R.id.topics:
                Intent topicIntent = new Intent(getApplicationContext(), TopicsPage.class);
                startActivity(topicIntent);
                return true;

            case R.id.facts:
                Bundle bundle = new Bundle();
                bundle.putParcelable(QUESTION, getRandomQuestion());
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                QuestionFragment secondFragment = new QuestionFragment();
                secondFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container, secondFragment)
                        .addToBackStack(null);
                fragmentTransaction.commit();
                return true;
        }
        return false;
    }

    private QuestionModel getRandomQuestion() {
        List<QuestionModel> questionList = initializeQuestionList();
        return questionList.get((int) (Math.random() * questionList.size()));
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private List<QuestionModel> initializeQuestionList(){
        List<QuestionModel> questionList = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray array = obj.getJSONArray("questions");

            for (int i = 0; i < array.length(); i++) {
                JSONObject questionJson = array.getJSONObject(i);
                String questionTitle = questionJson.getString("title");
                String questionTopic = questionJson.getString("topic");
                String questionAnswer = questionJson.getString("answer");

                questionList.add(new QuestionModel(questionTitle, questionTopic, questionAnswer));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questionList;
    }
}