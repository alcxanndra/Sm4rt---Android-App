package com.example.sm4rt.model;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionModel implements Parcelable  {

    private Integer id;
    private String title;
    private String topic;
    private String answer;

    public QuestionModel(String title, String topic, String answer) {
        this.title = title;
        this.topic = topic;
        this.answer = answer;
    }

    public QuestionModel(Integer id, String title, String topic, String answer) {
        this.id = id;
        this.title = title;
        this.topic = topic;
        this.answer = answer;
    }

    public QuestionModel(Parcel parcel) {
        String[] data = new String[3];
        parcel.readStringArray(data);

        this.title = data[0];
        this.topic = data[1];
        this.answer = data[2];
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.title, this.topic, this.answer});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        @Override
        public TopicModel createFromParcel(Parcel parcel) {
            return new TopicModel(parcel);
        }

        @Override
        public TopicModel[] newArray(int i) {
            return new TopicModel[i];
        }
    };
}
