package com.example.sm4rt.fragment;

import static com.example.sm4rt.fragment.FirstFragment.TOPIC;
import static com.example.sm4rt.fragment.FirstFragment.TOPIC_NAME;
import static com.example.sm4rt.fragment.ThirdFragment.QUESTION;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sm4rt.R;
import com.example.sm4rt.model.QuestionModel;
import com.example.sm4rt.model.TopicModel;

public class SecondFragment extends Fragment {

    public SecondFragment() {
        super(R.layout.fragment_second);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null){
            QuestionModel question = bundle.getParcelable(QUESTION);
            ((TextView) view.findViewById(R.id.title)).setText(question.getTitle());
            ((TextView) view.findViewById(R.id.answer)).setText(question.getAnswer());
        }
    }
}
