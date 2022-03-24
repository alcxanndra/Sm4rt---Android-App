package com.example.sm4rt.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TopicModel implements Parcelable {

    private Integer id;
    private String name;
    private String description;
    private Integer imageId;

    public TopicModel(String name, String description, Integer imageId) {
        this.name = name;
        this.description = description;
        this.imageId = imageId;
    }

    public TopicModel(Integer id, String name, String description, Integer imageId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageId = imageId;
    }

    public TopicModel(Parcel parcel) {
        String[] data = new String[3];
        parcel.readStringArray(data);

        this.name = data[0];
        this.description = data[1];
        this.imageId = Integer.valueOf(data[2]);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.name, this.description, this.imageId.toString()});
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
