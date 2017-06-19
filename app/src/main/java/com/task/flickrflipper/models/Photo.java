package com.task.flickrflipper.models;

import android.os.Parcel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by rafi on 16/6/17.
 */

public class Photo implements IPhoto {

    private String id;

    private String title;

    private String owner;

    private String width;

    private String height;

    private List<? extends ISize> sizes;

    private boolean isFlipped;

    private boolean isBookmark;

    public Photo() {
    }

    public Photo(String id) {
        this.id = id;
    }

    public Photo(JSONObject o) throws JSONException {
        this.id = o.getString("id");
        setOwner(o.getString("ownername"));
        setTitle(o.getString("title"));
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUrl() {

        String url = null;
        if (getSizes() == null || getSizes().isEmpty())
            return url;

        return getSizes().get(3).getSource(); // FIXME Assuming there is always a list whose size is greater than 2
    }

    @Override
    public String getSize() {
        ISize size = getSizes().get(3);
        return String.format(Locale.ENGLISH, "%s X %s", size.getWidth(), size.getHeight());
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setSizes(JSONArray array) {
        if (array == null || array.length() == 0)
            return;

        ArrayList<Size> sizes = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                Size s = new Size(array.getJSONObject(i));
                sizes.add(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setSizes(sizes);
    }


    public void setSizes(List<? extends ISize> sizes) {
        this.sizes = sizes;
    }

    @Override
    public List<? extends ISize> getSizes() {
        return this.sizes;
    }

    @Override
    public void setBookmark(boolean b) {
        this.isBookmark = b;
    }

    @Override
    public boolean isBookmarked() {
        return isBookmark;
    }

    @Override
    public void setFlipped(boolean b) {
        this.isFlipped = b;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public static List<IPhoto> filterByTitle(List<IPhoto> photos, String key){

        List<IPhoto> tmp = new ArrayList<>();
        for (IPhoto photo : photos) {
            if (photo.getTitle() != null && photo.getTitle().toLowerCase().contains(key.toLowerCase()))
                tmp.add(photo);
        }
        return tmp;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IPhoto && ((IPhoto) obj).getId().equals(getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.owner);
        dest.writeString(this.width);
        dest.writeString(this.height);
        dest.writeTypedList(this.sizes);
        dest.writeByte(this.isFlipped ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isBookmark ? (byte) 1 : (byte) 0);
    }

    protected Photo(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.owner = in.readString();
        this.width = in.readString();
        this.height = in.readString();
        this.sizes = in.createTypedArrayList(Size.CREATOR);
        this.isFlipped = in.readByte() != 0;
        this.isBookmark = in.readByte() != 0;
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
