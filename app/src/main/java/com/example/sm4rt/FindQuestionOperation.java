package com.example.sm4rt;

import android.os.AsyncTask;

import java.util.List;

import javax.inject.Inject;

public class FindQuestionOperation extends AsyncTask<String, Void, List<Question>>  {

    @Inject
    QuestionOperations listener;

    @Inject
    FindQuestionOperation(){ }

    protected List<Question> doInBackground(String... names) {

        String name = names[0];
        return MyApplication.getAppDatabase().questionDao().findQuestionsContainingKeyword(name);
    }

    @Override
    protected void onPostExecute(List<Question> questions){
        listener.findQuestions(questions);
    }
}