package com.example.sm4rt;

import android.os.AsyncTask;

class InsertQuestionOperation extends AsyncTask<Question, Void, String> {

    QuestionOperations listener;

    InsertQuestionOperation(QuestionOperations listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Question... questions) {
        try {
            MyApplication.getAppDatabase().questionDao().insertAll(questions);
        } catch(Exception e){
            return "error";
        }
        return "success";
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result){
        listener.insertQuestions(result);
    }
}
