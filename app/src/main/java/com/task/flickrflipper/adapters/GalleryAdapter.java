package com.task.flickrflipper.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.task.flickrflipper.R;
import com.task.flickrflipper.gallery.presenter.IGalleryAdapterPresenter;
import com.task.flickrflipper.models.IPhoto;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by rafi on 16/6/17.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryHolder> {

    private List<IPhoto> photos;
    private IGalleryAdapterPresenter mPresenter;
    private int flipPosition = -1;

    public GalleryAdapter(IGalleryAdapterPresenter presenter) {
        this.mPresenter = presenter;
    }

    public void setData(List<IPhoto> photos) {
        this.photos = photos;
    }

    @Override
    public GalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new GalleryHolder(v);
    }

    @Override
    public void onBindViewHolder(GalleryHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return this.photos.size();
    }

    public void flipPosition(int position) {
        this.flipPosition = position;
        notifyItemChanged(position);
        this.flipPosition = -1;
    }

    public class GalleryHolder extends RecyclerView.ViewHolder {

        public GalleryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }



    }

}
