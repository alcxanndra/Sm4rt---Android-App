package com.example.sm4rt;

import android.os.AsyncTask;

import javax.inject.Inject;

public class FindTopicOperation extends AsyncTask<String, Void, Topic> {

    @Inject
    TopicOperations listener;

    @Inject
    FindTopicOperation(){ }

    @Override
    protected Topic doInBackground(String... names) {

        String name = names[0];
        return MyApplication.getAppDatabase().topicDao().findTopicByName(name);
    }

    @Override
    protected void onPostExecute(Topic topic){
        listener.findTopic(topic);
    }
}