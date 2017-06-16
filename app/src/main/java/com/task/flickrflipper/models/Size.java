package com.task.flickrflipper.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by rafi on 16/6/17.
 */

public class Size implements ISize, Parcelable {

    private String label;

    private String source;

    private String width;

    private String height;

    public Size(){

    }

    public Size(JSONObject o){

    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @Override
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.label);
        dest.writeString(this.source);
        dest.writeString(this.width);
        dest.writeString(this.height);
    }

    protected Size(Parcel in) {
        this.label = in.readString();
        this.source = in.readString();
        this.width = in.readString();
        this.height = in.readString();
    }

    public static final Parcelable.Creator<Size> CREATOR = new Parcelable.Creator<Size>() {
        @Override
        public Size createFromParcel(Parcel source) {
            return new Size(source);
        }

        @Override
        public Size[] newArray(int size) {
            return new Size[size];
        }
    };
}
