package com.task.flickrflipper.gallery.presenter;

import com.squareup.otto.Subscribe;
import com.task.flickrflipper.gallery.view.IGalleryView;
import com.task.flickrflipper.models.IPhoto;
import com.task.flickrflipper.models.Photo;
import com.task.flickrflipper.network.BusProvider;
import com.task.flickrflipper.network.requests.FlickrPhotosRequest;
import com.task.flickrflipper.network.response.FlickrPhotosResponse;
import com.task.flickrflipper.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafi on 16/6/17.
 */

public class GalleryPresenter implements IGalleryPresenter {

    private IGalleryView mGalleryView;
    private List<IPhoto> mPhotos;

    public GalleryPresenter(IGalleryView galleryView) {
        this.mGalleryView = galleryView;
    }

    @Override
    public void registerBus() {
        BusProvider.getInstance().register(this);
    }

    @Override
    public void unregisterBus() {
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void fetchPhotos() {
        mGalleryView.showProgress();
        ThreadUtils.getDefaultExecutorService().submit(new FlickrPhotosRequest("1"));
    }

    @Override
    public void resetData() {
        if (mPhotos != null && !mPhotos.isEmpty())
            mGalleryView.setData(mPhotos);
    }

    @Override
    public void filterByTitle(String key) {
        if (mPhotos == null || mPhotos.isEmpty())
            return;

        mGalleryView.setData(Photo.filterByTitle(mPhotos, key));
    }

    @Override
    public void setData(List<IPhoto> photos) {
        this.mPhotos = photos;
        mGalleryView.setData(this.mPhotos);
    }

    @Override
    public ArrayList<IPhoto> getBookmarkPhotos() {

        if (mPhotos == null)
            return null;

        ArrayList<IPhoto> photos = new ArrayList<>();
        for (IPhoto mPhoto : mPhotos) {
            if (mPhoto.isBookmarked())
                photos.add(mPhoto);
        }
        return photos;
    }

    @Override
    public void updateBookmarks(ArrayList<IPhoto> photos) {
        for (IPhoto mPhoto : mPhotos) {
            mPhoto.setBookmark(photos.contains(mPhoto));
        }
        setData(mPhotos);
    }

    @Subscribe
    public void onFlickrPhotosResponse(FlickrPhotosResponse response) {
        mGalleryView.hideProgress();
        setData(response.getPhotos());
    }


    @Override
    public void flipRequested(IPhoto photo) {
        photo.setFlipped(!photo.isFlipped());
        mGalleryView.flipPhoto(this.mPhotos.indexOf(photo));
    }

    @Override
    public void bookmark(IPhoto photo) {
        photo.setBookmark(!photo.isBookmarked());
        mGalleryView.bookmarkView(this.mPhotos.indexOf(photo));
    }
}
