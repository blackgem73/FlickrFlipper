package com.task.flickrflipper.network.response;

import com.android.volley.VolleyError;

import org.json.JSONObject;


public abstract class BaseResponse {

    protected static final String TAG = "BaseResponse";

    protected boolean mIsSuccessful;
    protected String mMessage;
    protected String serverMessage;

    private VolleyError mError;

    private JSONObject mResponse;

    private long id;

    public BaseResponse(long id, JSONObject response) {
        // TODO check necessary condition
    }

    public BaseResponse(long id, Exception error) {
        // TODO check error type
        mIsSuccessful = false;
        this.id = id;

        try {
            mError = (VolleyError) error.getCause();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public String getErrorMessage() {
        if (mError == null || mError.networkResponse == null)
            return null;

        return new String(mError.networkResponse.data);

    }

    public boolean isSuccessful() {
        return mIsSuccessful;
    }

    public JSONObject getResponse() {
        return mResponse;
    }

    public String getMessage() {
        return mMessage;
    }

    public String optString(JSONObject o, String key) {

        String s = o.optString(key);
        if (s.toLowerCase().equals("null"))
            return null;
        return s;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    public long getActivityId() {
        return id;
    }

}
