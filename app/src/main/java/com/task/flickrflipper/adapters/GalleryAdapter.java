package com.task.flickrflipper.adapters;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.squareup.picasso.Picasso;
import com.task.flickrflipper.R;
import com.task.flickrflipper.gallery.presenter.IGalleryAdapterPresenter;
import com.task.flickrflipper.models.IPhoto;
import com.task.flickrflipper.view.FlipperLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rafi on 16/6/17.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryHolder> {

    private List<IPhoto> photos;
    private IGalleryAdapterPresenter mPresenter;
    private int flipPosition = -1;

    public GalleryAdapter(IGalleryAdapterPresenter presenter) {
        this.mPresenter = presenter;
        this.photos = new ArrayList<>();
    }

    public void setData(List<IPhoto> photos) {
        if (photos == null || photos.isEmpty())
            return;
        this.photos.clear();
        this.photos.addAll(photos);
        notifyDataSetChanged();
    }

    @Override
    public GalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new GalleryHolder(v);
    }

    @Override
    public void onBindViewHolder(final GalleryHolder holder, int position) {

        IPhoto photo = photos.get(position);
        Picasso.with(holder.itemView.getContext()).load(photo.getUrl()).placeholder(R.drawable.ic_image_placeholder).into(holder.mPhotoIv);

        holder.mTitleTv.setText(photo.getTitle());
        holder.mOwnerTv.setText(photo.getOwner());
        holder.mSizeIv.setText(photo.getSize());

        if (position == flipPosition) {
            holder.flip();
            this.flipPosition = -1;
        } else {
            if (photo.isFlipped()) {
                holder.mPhotoIv.setVisibility(View.INVISIBLE);
                holder.mMetaDataLayout.setVisibility(View.VISIBLE);
            } else {
                holder.mPhotoIv.setVisibility(View.VISIBLE);
                holder.mMetaDataLayout.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.photos.size();
    }

    public void flipPosition(int position) {
        this.flipPosition = position;
        notifyItemChanged(position);
    }

    public class GalleryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.root_layout)
        FlipperLayout mFlipperLayout;

        @BindView(R.id.meta_data_info)
        FrameLayout mMetaDataLayout;

        @BindView(R.id.photo_iv)
        AppCompatImageView mPhotoIv;

        @BindView(R.id.title_tv)
        AppCompatTextView mTitleTv;

        @BindView(R.id.owner_name_tv)
        AppCompatTextView mOwnerTv;

        @BindView(R.id.size_tv)
        AppCompatTextView mSizeIv;

        public GalleryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.root_layout)
        public void onFlipRequest(FlipperLayout layout) {
            if (layout.isFlipping())
                return;
            mPresenter.flipRequested(photos.get(getAdapterPosition()));
        }

        public void flip() {
            IPhoto p = photos.get(getAdapterPosition());
            mFlipperLayout.flip(p.isFlipped() ? 0 : 1);
        }

    }

}
