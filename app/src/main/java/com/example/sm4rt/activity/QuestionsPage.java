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
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sm4rt.AppModule;
import com.example.sm4rt.R;
import com.example.sm4rt.RoomModule;
import com.example.sm4rt.database.data.Question;
import com.example.sm4rt.database.repository.QuestionRepository;
import com.example.sm4rt.fragment.QuestionFragment;
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

public class QuestionsPage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    QuestionRepository questionRepository;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_page);

//        DaggerAppComponent.builder()
//                .appModule(new AppModule(getApplication()))
//                .roomModule(new RoomModule(getApplication()))
//                .build()
//                .inject(this);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.share:
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
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

    private Question getRandomQuestion() {
        List<Question> questionList = questionRepository.findAll();
        return questionList.get((int) (Math.random() * questionList.size()));
    }

}