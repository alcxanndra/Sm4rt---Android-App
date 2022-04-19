package com.example.sm4rt.fragment;

import static com.example.sm4rt.fragment.QuestionListFragment.QUESTION;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sm4rt.R;
import com.example.sm4rt.database.data.Question;

import retrofit.QuestionApiModel;

public class QuestionFragment extends Fragment {

    public static String QUESTION = "question";

    public QuestionFragment() {
        super(R.layout.fragment_question);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null){
            QuestionApiModel question = bundle.getParcelable(QUESTION);
            ((TextView) view.findViewById(R.id.title)).setText(question.getQuestion().replace("&#039;","'").replace("&quot;","'"));
            ((TextView) view.findViewById(R.id.answer)).setText(question.getCorrectAnswer().replace("&#039;","'").replace("&quot;","'"));
        } else {
            System.out.println("Bundle is null");
        }
    }

}
