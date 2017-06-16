package com.task.flickrflipper.models;

import android.os.Parcelable;

/**
 * Created by rafi on 16/6/17.
 */

public interface ISize extends Parcelable{

    String getLabel();

    String getWidth();

    String getHeight();

    String getSource();

}
