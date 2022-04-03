package com.example.sm4rt.fragment;

import static com.example.sm4rt.fragment.QuestionListFragment.QUESTION;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sm4rt.R;
import com.example.sm4rt.activity.RandomQuestionActivity;
import com.example.sm4rt.activity.TopicsPage;
import com.example.sm4rt.database.data.Question;
import com.example.sm4rt.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomMenuFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener  {

    BottomNavigationView bottomNavigationView;

    public BottomMenuFragment() {
        super(R.layout.fragment_bottom_menu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.getMenu().getItem(0).setCheckable(false);
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
                Uri uri = Util.getImageToShare(this.getActivity(), bitmap);

                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                shareIntent.putExtra(Intent.EXTRA_TITLE, "Join me on Sm4rt!");
                shareIntent.setData(uri);
                shareIntent.setType("image/*");
                shareIntent.setClipData(ClipData.newRawUri("", uri));
                shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                startActivity(Intent.createChooser(shareIntent, null));
                return true;

            case R.id.topics:
                Intent topicIntent = new Intent(getActivity().getApplicationContext(), TopicsPage.class);
                startActivity(topicIntent);
                return true;

            case R.id.facts:
                Intent randomQuestionIntent = new Intent(getActivity().getApplicationContext(), RandomQuestionActivity.class);
                startActivity(randomQuestionIntent);
                return true;
        }
        return false;
    }
}
