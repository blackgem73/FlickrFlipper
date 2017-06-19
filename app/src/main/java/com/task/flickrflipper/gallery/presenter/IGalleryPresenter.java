package com.task.flickrflipper.gallery.presenter;

/**
 * Created by rafi on 16/6/17.
 */

public interface IGalleryPresenter extends IGalleryAdapterPresenter{

    void registerBus();

    void unregisterBus();

    void fetchPhotos();

    void resetData();

    void filterByTitle(String key);

}
