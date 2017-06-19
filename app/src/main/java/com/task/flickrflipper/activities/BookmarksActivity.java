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

import com.task.flickrflipper.R;
import com.task.flickrflipper.adapters.GalleryAdapter;
import com.task.flickrflipper.gallery.presenter.BookmarkPresenter;
import com.task.flickrflipper.gallery.view.IGalleryView;
import com.task.flickrflipper.models.IPhoto;
import com.task.flickrflipper.view.decorators.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rafi on 19/6/17.
 */

public class BookmarksActivity extends AppCompatActivity implements IGalleryView, SearchView.OnQueryTextListener {

    public static final int GRID_SPAN = 2;

    public static final String KEY_BOOKMARKS = "key_bookmarks";

    @BindView(R.id.recycler_view)
    RecyclerView mGalleryRecycler;

    private BookmarkPresenter mPresenter;
    private GalleryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        ButterKnife.bind(this);

        mGalleryRecycler.setVisibility(View.VISIBLE);

        mPresenter = new BookmarkPresenter(this);
        initializeRecycler();

        ArrayList<IPhoto> photos = getIntent().getParcelableArrayListExtra(KEY_BOOKMARKS);
        mPresenter.setData(photos);
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
        getMenuInflater().inflate(R.menu.menu_bookmarks, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

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
        if (newText.isEmpty()){
            mPresenter.resetData();
            return false;
        }
        mPresenter.filterByTitle(newText);
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(KEY_BOOKMARKS, mPresenter.getBookmarkPhotos());
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
