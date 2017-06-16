package com.task.flickrflipper.network;

import com.task.flickrflipper.App;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rafi on 23/12/15.
 */
public class ServerUrl {

    public static final String BASE_URL = "https://api.flickr.com";

    int pathId = -1;
    Object[] pathParams = null;
    Map<String, String> params = new HashMap<>();


    public String getBaseUrl() {
        return BASE_URL;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public ServerUrl setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public ServerUrl withPathParams(Object... objects) {
        this.pathParams = objects;
        return this;
    }

    public ServerUrl forPath(int pathId) {
        this.pathId = pathId;
        return this;
    }

    public String getUrl() {
        String baseUrl = getBaseUrl();

        baseUrl = baseUrl + App.getInstance().getResources().getString(pathId, pathParams);

        if (this.params == null || this.params.size() == 0) {
            return baseUrl;
        } else {
            StringBuilder builder = new StringBuilder(baseUrl);
            builder.append("?");
            for (Map.Entry<String, String> kv : this.getParams().entrySet()) {
                try {
                    builder.append(URLEncoder.encode(kv.getKey(), "utf-8"));
                    builder.append("=");
                    builder.append(URLEncoder.encode(kv.getValue(), "utf-8"));
                    builder.append("&");
                } catch (UnsupportedEncodingException ignore) {
                }
            }
            return builder.toString();
        }
    }

}
