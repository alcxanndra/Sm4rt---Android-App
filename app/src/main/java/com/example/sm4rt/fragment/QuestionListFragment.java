package com.example.sm4rt.fragment;

import static com.example.sm4rt.fragment.TopicListFragment.TOPIC_NAME;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sm4rt.R;
import com.example.sm4rt.adapter.QuestionAdapter;
import com.example.sm4rt.model.QuestionModel;
import com.example.sm4rt.util.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuestionListFragment extends Fragment implements OnItemClickListener<QuestionModel> {

    private QuestionAdapter adapter;
    private RecyclerView rv;
    private SearchView searchView = null;
    private Button seeAnswerBtn;
    private SearchView.OnQueryTextListener queryTextListener;
    public static String QUESTION = "question";

    public static List<QuestionModel> questionList = new ArrayList<>();

    public QuestionListFragment() {
        super(R.layout.fragment_question_list);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String topicName = null;

        if (bundle != null){
            topicName = bundle.getString(TOPIC_NAME);
        }

        initializeQuestionList(topicName);

        adapter = new QuestionAdapter(questionList, this);
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
        ArrayList<QuestionModel> filteredList = new ArrayList<>();

        for (QuestionModel item : questionList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this.getActivity(), "No questions found", Toast.LENGTH_SHORT).show();
        }

        adapter.filterList(filteredList);
    }
    
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("questions.json");
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

    private void initializeQuestionList(String topicName){
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray array = obj.getJSONArray("questions");

            for (int i = 0; i < array.length(); i++) {
                JSONObject questionJson = array.getJSONObject(i);
                String questionTitle = questionJson.getString("title");
                String questionTopic = questionJson.getString("topic");
                String questionAnswer = questionJson.getString("answer");

                if (topicName == null || (topicName != null && questionTopic.toLowerCase(Locale.ROOT).equals(topicName.toLowerCase()))) {
                    questionList.add(new QuestionModel(questionTitle, questionTopic, questionAnswer));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(QuestionModel item) {
        Bundle bundle = new Bundle();

        bundle.putParcelable(QUESTION, item);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setArguments(bundle);

//        Give id of container to be replaced and fragment to replace with
        fragmentTransaction.replace(R.id.fragment_container, questionFragment)
                .addToBackStack(null);
        fragmentTransaction.commit();
    }
}
