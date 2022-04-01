package com.example.sm4rt.database.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.inject.Inject;

@Entity
public class Topic implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String description;

    private Integer image;

    public Topic(String name, String description, Integer image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Topic(Parcel parcel) {
        String[] data = new String[3];
        parcel.readStringArray(data);

        this.name = data[0];
        this.description = data[1];
        this.image = Integer.valueOf(data[2]);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.name, this.description, this.image.toString()});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        @Override
        public Topic createFromParcel(Parcel parcel) {
            return new Topic(parcel);
        }

        @Override
        public Topic[] newArray(int i) {
            return new Topic[i];
        }
    };
}
