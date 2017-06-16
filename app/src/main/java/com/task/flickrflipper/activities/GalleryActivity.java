package com.task.flickrflipper.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.task.flickrflipper.R;
import com.task.flickrflipper.adapters.GalleryAdapter;
import com.task.flickrflipper.gallery.presenter.GalleryPresenter;
import com.task.flickrflipper.gallery.view.IGalleryView;
import com.task.flickrflipper.models.IPhoto;
import com.task.flickrflipper.view.decorators.SpaceItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity implements IGalleryView {

    public static final int GRID_SPAN = 2;

    @BindView(R.id.recycler_view)
    RecyclerView mGalleryRecycler;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private GalleryPresenter mPresenter;
    private GalleryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.GONE);
        mGalleryRecycler.setVisibility(View.GONE);

        mPresenter = new GalleryPresenter(this);
        initializeRecycler();

        mPresenter.fetchPhotos();
    }

    private void initializeRecycler() {

        int space = getResources().getDimensionPixelOffset(R.dimen.gallery_grid_space);
        mGalleryRecycler.setLayoutManager(new GridLayoutManager(this, GRID_SPAN));
        mGalleryRecycler.addItemDecoration(new SpaceItemDecoration(space, GRID_SPAN));
        mAdapter = new GalleryAdapter(mPresenter);
        mGalleryRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.registerBus();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unregisterBus();
    }

    @Override
    public void showProgress() {
        mGalleryRecycler.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        mGalleryRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void flipPhoto(int position) {
        mAdapter.flipPosition(position);
    }

    @Override
    public void setData(List<IPhoto> photos) {
        mAdapter.setData(photos);
    }
}
