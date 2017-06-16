package com.task.flickrflipper.network.requests;

import com.android.volley.toolbox.RequestFuture;
import com.task.flickrflipper.App;
import com.task.flickrflipper.R;
import com.task.flickrflipper.models.IPhoto;
import com.task.flickrflipper.models.Photo;
import com.task.flickrflipper.network.BusProvider;
import com.task.flickrflipper.network.FFJsonRequest;
import com.task.flickrflipper.network.FlickrConstants;
import com.task.flickrflipper.network.Network;
import com.task.flickrflipper.network.ServerUrl;
import com.task.flickrflipper.network.response.FlickrPhotosResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by rafi on 16/6/17.
 */

public class FlickrPhotosRequest implements Callable<Void> {

    public static final String FORMAT = "json";
    public static final String NO_JSON_CALLBACK_VALUE = "1";
    public static final String DEFAULT_PAGE_SIZE = "20";
    public static final String METHOD_PHOTOS_FROM_GROUP = "flickr.groups.pools.getPhotos";
    public static final String METHOD_SIZE_FOR_PHOTO = "flickr.photos.getSizes";

    private String pageNo;

    private String pageSize;

    public FlickrPhotosRequest(String pageNo) {
        this(pageNo, DEFAULT_PAGE_SIZE);
    }

    public FlickrPhotosRequest(String pageNo, String pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }


    @Override
    public Void call() throws Exception {

        String apiKey = App.getInstance().getResources().getString(R.string.flickr_api_key);
        String groupId = App.getInstance().getResources().getString(R.string.flickr_group_id);
        String pageSize = this.pageSize;
        String pageNo = this.pageNo;

        LinkedHashMap<String, String> pathParams = new LinkedHashMap<>();
        pathParams.put(FlickrConstants.METHOD_KEY, METHOD_PHOTOS_FROM_GROUP);
        pathParams.put(FlickrConstants.FORMAT_KEY, FORMAT);
        pathParams.put(FlickrConstants.API_KEY, apiKey);
        pathParams.put(FlickrConstants.GROUP_KEY, groupId);
        pathParams.put(FlickrConstants.PER_PAGE_KEY, pageSize);
        pathParams.put(FlickrConstants.PAGE_KEY, pageNo);
        pathParams.put(FlickrConstants.NO_JSON_CALLBACK_KEY, NO_JSON_CALLBACK_VALUE);

        String url = new ServerUrl().forPath(R.string.url_flickr_api).setParams(pathParams).getUrl();

        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
        FFJsonRequest request = new FFJsonRequest(url, requestFuture, requestFuture);
        Network.getGeneralRequestQueue().add(request);

        try {
            JSONObject response = requestFuture.get(Network.REQUEST_TIMEOUT, TimeUnit.MILLISECONDS);
            FlickrPhotosResponse parser = new FlickrPhotosResponse(-1, response);

            List<IPhoto> photos = parser.getPhotos();
            if (photos == null)
                throw new Exception("No Photos Found");

            for (IPhoto photo : photos) {
                JSONArray array = getSizesArray(apiKey, photo.getId());
                if (array != null) {
                    ((Photo) photo).setSizes(array);
                }
            }
            BusProvider.getInstance().post(parser);
        } catch (Exception e) {
            e.printStackTrace();
            BusProvider.getInstance().post(new FlickrPhotosResponse(-1, e));
        }
        return null;
    }

    private JSONArray getSizesArray(String apiKey, String id) {


        LinkedHashMap<String, String> pathParams = new LinkedHashMap<>();
        pathParams.put(FlickrConstants.METHOD_KEY, METHOD_SIZE_FOR_PHOTO);
        pathParams.put(FlickrConstants.FORMAT_KEY, FORMAT);
        pathParams.put(FlickrConstants.API_KEY, apiKey);
        pathParams.put(FlickrConstants.PHOTO_ID_KEY, id);
        pathParams.put(FlickrConstants.NO_JSON_CALLBACK_KEY, NO_JSON_CALLBACK_VALUE);

        String url = new ServerUrl().forPath(R.string.url_flickr_api).setParams(pathParams).getUrl();

        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
        FFJsonRequest request = new FFJsonRequest(url, requestFuture, requestFuture);
        Network.getGeneralRequestQueue().add(request);

        try {
            JSONObject response = requestFuture.get(Network.REQUEST_TIMEOUT, TimeUnit.MILLISECONDS);
            response = response.getJSONObject("sizes");
            return response.getJSONArray("size");
        } catch (Exception e) {
        }
        return null;
    }


}
