package com.example.sm4rt;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.inject.Inject;

@Entity
public class Question {

    @PrimaryKey
    private int id;

    private String topic;

    private String title;

    private String answer;

    @Inject
    public Question(){ }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
