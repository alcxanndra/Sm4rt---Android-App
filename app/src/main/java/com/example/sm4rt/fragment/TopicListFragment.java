package com.example.sm4rt.fragment;

import static com.example.sm4rt.activity.TopicsPage.TOPICS_LIST;
import static com.example.sm4rt.fragment.QuestionListFragment.QUESTION;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sm4rt.database.data.Question;
import com.example.sm4rt.database.data.Topic;
import com.example.sm4rt.util.OnItemClickListener;
import com.example.sm4rt.R;
import com.example.sm4rt.adapter.TopicAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TopicListFragment extends Fragment implements OnItemClickListener<Topic> {

    private TopicAdapter adapter;
    private RecyclerView rv;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    public static String TOPIC_NAME = "topic name";

    public static List<Topic> topicList = new ArrayList<>();

    public TopicListFragment() {
        super(R.layout.fragment_topic_list);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_list, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null){
            topicList = Arrays.asList((Topic[]) bundle.getParcelableArray(TOPICS_LIST));
        }

        adapter = new TopicAdapter(topicList, this);
        rv = view.findViewById(R.id.recycler_view);
        rv.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String text) {
        ArrayList<Topic> filteredList = new ArrayList<>();

        for (Topic item : topicList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this.getActivity(), "No topics found", Toast.LENGTH_SHORT).show();
        }

        adapter.filterList(filteredList);
    }

    public static Integer getImage(Context c, String ImageName) {
        return c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName());
    }

    private void initializeTopicList(){
        topicList.clear();
//        topicList = getArguments().getParcelableArrayList(TOPICS_LIST);
    }

    @Override
    public void onItemClick(Topic item) {
        Bundle bundle = new Bundle();

        bundle.putString(TOPIC_NAME, item.getName());

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        QuestionListFragment questionListFragment = new QuestionListFragment();
        questionListFragment.setArguments(bundle);

//        Give id of container to be replaced and fragment to replace with
        fragmentTransaction.replace(R.id.fragment_container, questionListFragment)
                .addToBackStack(null);
        fragmentTransaction.commit();
    }

}
