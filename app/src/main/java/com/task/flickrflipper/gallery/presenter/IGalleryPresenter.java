package com.task.flickrflipper.gallery.presenter;

import com.task.flickrflipper.models.IPhoto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafi on 16/6/17.
 */

public interface IGalleryPresenter extends IGalleryAdapterPresenter{

    void registerBus();

    void unregisterBus();

    void fetchPhotos();

    void resetData();

    void filterByTitle(String key);

    void setData(List<IPhoto> photos);

    ArrayList<IPhoto> getBookmarkPhotos();

    void updateBookmarks(ArrayList<IPhoto> photos);

}
