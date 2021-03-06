package com.task.flickrflipper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.task.flickrflipper.R;
import com.task.flickrflipper.adapters.GalleryAdapter;
import com.task.flickrflipper.gallery.presenter.GalleryPresenter;
import com.task.flickrflipper.gallery.view.IGalleryView;
import com.task.flickrflipper.models.IPhoto;
import com.task.flickrflipper.view.decorators.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity implements IGalleryView, SearchView.OnQueryTextListener {

    public static final int GRID_SPAN = 2;

    public static final int RC_BOOKMARKS = 122;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gallery, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bookmark:
                ArrayList<IPhoto> photos = mPresenter.getBookmarkPhotos();
                if (photos == null || photos.isEmpty()) {
                    Toast.makeText(this, R.string.no_bookmarks_msg, Toast.LENGTH_SHORT).show();
                    return super.onOptionsItemSelected(item);
                }
                Intent i = new Intent(this, BookmarksActivity.class);
                i.putExtra(BookmarksActivity.KEY_BOOKMARKS, photos);
                startActivityForResult(i, RC_BOOKMARKS);
                break;
        }
        return true;
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
    public void bookmarkView(int position) {
        mAdapter.bookmarkView(position);
    }

    @Override
    public void removeView(int position) {
        mAdapter.removeItem(position);
    }

    @Override
    public void setData(List<IPhoto> photos) {
        mAdapter.setData(photos);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.isEmpty())
            return false;
        mPresenter.filterByTitle(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty()) {
            mPresenter.resetData();
            return false;
        }
        mPresenter.filterByTitle(newText);
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == RC_BOOKMARKS) {
            if (data != null){
                ArrayList<IPhoto> photos = data.getParcelableArrayListExtra(BookmarksActivity.KEY_BOOKMARKS);
                mPresenter.updateBookmarks(photos);
            }
        }
    }
}
