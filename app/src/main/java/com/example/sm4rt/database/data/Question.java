package com.example.sm4rt.database.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.inject.Inject;

@Entity
public class Question implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String topic;

    private String title;

    private String answer;

    public Question(String topic, String title, String answer) {
        this.topic = topic;
        this.title = title;
        this.answer = answer;
    }

    public Question(Parcel parcel) {
        String[] data = new String[3];
        parcel.readStringArray(data);

        this.topic = data[0];
        this.title = data[1];
        this.answer = data[2];
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.topic, this.title, this.answer});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        @Override
        public Question createFromParcel(Parcel parcel) {
            return new Question(parcel);
        }

        @Override
        public Question[] newArray(int i) {
            return new Question[i];
        }
    };
}
