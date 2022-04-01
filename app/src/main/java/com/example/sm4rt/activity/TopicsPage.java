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
import com.example.sm4rt.database.data.Topic;
import com.example.sm4rt.database.repository.TopicRepository;
import com.example.sm4rt.fragment.QuestionFragment;
import com.example.sm4rt.fragment.TopicListFragment;
import com.example.sm4rt.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TopicsPage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    TopicRepository topicRepository;

    BottomNavigationView bottomNavigationView;

    public static String TOPICS_LIST = "topics";

    ArrayList<Topic> topicList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_page);

        DaggerAppComponent.builder()
                .appModule(new AppModule(getApplication()))
                .roomModule(new RoomModule(getApplication()))
                .build()
                .inject(this);

        new Request().execute();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private class Request extends AsyncTask<Void, Void, List<Topic>> {
        @Override
        protected List<Topic> doInBackground(Void... voids) {
            return topicRepository.findAll();
        }

        @Override
        protected void onPostExecute(List<Topic> topics) {
            topicList.clear();
            topicList.addAll(topics);
            System.out.println("No of topics retrieved: " + topics.size());

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                String shareText = "https://sm4rt.com/download-app";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                Context c = bottomNavigationView.getContext();
                Drawable d = c.getResources().getDrawable(c.getResources().getIdentifier("logo", "drawable", c.getPackageName()));
                BitmapDrawable bitmapDrawable = (BitmapDrawable) d;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                Uri uri = Util.getImageToShare(this, bitmap);

                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                shareIntent.putExtra(Intent.EXTRA_TITLE, "Join me on Sm4rt!");
                shareIntent.setData(uri);
                shareIntent.setType("image/*");
                shareIntent.setClipData(ClipData.newRawUri("", uri));
                shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                startActivity(Intent.createChooser(shareIntent, null));
                return true;

            case R.id.topics:
                Intent topicIntent = new Intent(getApplicationContext(), TopicsPage.class);
                startActivity(topicIntent);
                return true;

            case R.id.facts:
//                Bundle bundle = new Bundle();
//                bundle.putParcelable(QUESTION, getRandomQuestion());
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                QuestionFragment questionFragment = new QuestionFragment();
//                questionFragment.setArguments(bundle);
//                fragmentTransaction.replace(R.id.fragment_container, questionFragment)
//                        .addToBackStack(null);
//                fragmentTransaction.commit();
                return true;
        }
        return false;
    }

//
//    private Question getRandomQuestion() {
//        List<Question> questionList = initializeQuestionList();
//        return questionList.get((int) (Math.random() * questionList.size()));
//    }

    
//
//    private List<Question> initializeQuestionList(){
//        return questionRepository.findAll();
//    }

}