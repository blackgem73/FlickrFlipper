package com.task.flickrflipper.network.response;

import android.util.Log;

import com.task.flickrflipper.models.IPhoto;
import com.task.flickrflipper.models.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafi on 16/6/17.
 */

public class FlickrPhotosResponse extends BaseResponse {

    private List<IPhoto> photos;

    public FlickrPhotosResponse(long id, JSONObject response) {
        super(id, response);
        Log.d(TAG, "FlickrPhotosResponse() called with: id = [" + id + "], response = [" + response + "]");

        try {
            JSONObject o = response.getJSONObject("photos");
            JSONArray array = o.getJSONArray("photo");

            this.photos = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                Photo p = new Photo(array.getJSONObject(i));
                this.photos.add(p);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            mIsSuccessful = false;
        }

    }

    public FlickrPhotosResponse(long id, Exception error) {
        super(id, error);
        Log.d(TAG, "FlickrPhotosResponse() called with: id = [" + id + "], error = [" + error + "]");


    }

    public List<IPhoto> getPhotos() {
        return photos;
    }
}
