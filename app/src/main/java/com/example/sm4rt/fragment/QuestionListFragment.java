package com.example.sm4rt.fragment;

import static com.example.sm4rt.activity.QuestionsPage.QUESTIONS_LIST;
import static com.example.sm4rt.activity.TopicsPage.TOPICS_LIST;
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
import com.example.sm4rt.database.data.Question;
import com.example.sm4rt.database.data.Topic;
import com.example.sm4rt.util.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class QuestionListFragment extends Fragment implements OnItemClickListener<Question> {

    private QuestionAdapter adapter;
    private RecyclerView rv;
    private SearchView searchView = null;
    private Button seeAnswerBtn;
    private SearchView.OnQueryTextListener queryTextListener;
    public static String QUESTION = "question";

    public static List<Question> questionList = new ArrayList<>();

    public QuestionListFragment() {
        super(R.layout.fragment_question_list);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question_list, container, false);
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

        if (bundle != null){
            questionList = Arrays.asList((Question[]) bundle.getParcelableArray(QUESTIONS_LIST));
        }

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
        ArrayList<Question> filteredList = new ArrayList<>();

        for (Question item : questionList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this.getActivity(), "No questions found", Toast.LENGTH_SHORT).show();
        }

        adapter.filterList(filteredList);
    }

    @Override
    public void onItemClick(Question item) {
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
