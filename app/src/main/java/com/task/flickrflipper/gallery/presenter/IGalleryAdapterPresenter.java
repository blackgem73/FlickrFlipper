package com.task.flickrflipper.gallery.presenter;

import com.task.flickrflipper.models.IPhoto;

/**
 * Created by rafi on 16/6/17.
 */

public interface IGalleryAdapterPresenter {

    void flipRequested(IPhoto photo);

    void bookmark(IPhoto photo);

}
