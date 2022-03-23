package com.example.sm4rt;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    public static List<TopicModel> topicList = new ArrayList<>();

    public FirstFragment() {
        super(R.layout.fragment_first);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeTopicList();

        TopicAdapter adapter = new TopicAdapter(topicList);
        RecyclerView rv = view.findViewById(R.id.recycler_view);
        rv.setAdapter(adapter);
    }

    private void initializeTopicList(){
        topicList.add(new TopicModel("History", "Test your knowledge in history", R.drawable.history));
        topicList.add(new TopicModel("Literature", "Test your knowledge in literature", R.drawable.literature));
    }
}
