package com.task.flickrflipper.models;

import java.util.List;

/**
 * Created by rafi on 16/6/17.
 */

public interface IPhoto {

    String getId();

    String getTitle();

    String getOwner();

    String getUrl();

    String getSize();

    String getWidth();

    String getHeight();

    List<? extends ISize> getSizes();

    void setFlipped(boolean b);

    boolean isFlipped();

}
