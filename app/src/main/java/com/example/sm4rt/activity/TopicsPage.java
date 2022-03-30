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

import com.example.sm4rt.R;
import com.example.sm4rt.fragment.QuestionFragment;
import com.example.sm4rt.model.QuestionModel;
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

public class TopicsPage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_page);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().getItem(0).setCheckable(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                String shareText = "https://sm4rt.com/get_latest_version";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                Context c = bottomNavigationView.getContext();
                Drawable d = c.getResources().getDrawable(c.getResources().getIdentifier("logo", "drawable", c.getPackageName()));
                BitmapDrawable bitmapDrawable = (BitmapDrawable) d;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                Uri uri = getImageToShare(bitmap);

                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                shareIntent.putExtra(Intent.EXTRA_TITLE, "Dear friend, join me on Sm4rt!");
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
                QuestionFragment questionFragment = new QuestionFragment();
                questionFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container, questionFragment)
                        .addToBackStack(null);
                fragmentTransaction.commit();
                return true;
        }
        return false;
    }

    // Retrieving the url to share
    private Uri getImageToShare(Bitmap bitmap) {
        File imagefolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "shared_image.png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(this, "com.example.sm4rt.shareimage.fileprovider", file);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
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