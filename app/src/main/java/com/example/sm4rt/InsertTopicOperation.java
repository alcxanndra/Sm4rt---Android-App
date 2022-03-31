package com.example.sm4rt;

import android.os.AsyncTask;

class InsertTopicOperation extends AsyncTask<Topic, Void, String> {

    TopicOperations listener;

    InsertTopicOperation(TopicOperations listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Topic... topics) {
        try {
            MyApplication.getAppDatabase().topicDao().insertAll(topics);
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
        listener.insertTopics(result);
    }
}
