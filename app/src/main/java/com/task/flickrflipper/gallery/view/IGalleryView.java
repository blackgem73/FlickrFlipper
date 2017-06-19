package com.task.flickrflipper.gallery.view;

/**
 * Created by rafi on 16/6/17.
 */

public interface IGalleryView extends IGalleryAdapterView{

    void showProgress();

    void hideProgress();

    void flipPhoto(int position);

    void bookmarkView(int position);

    void removeView(int position);

}
