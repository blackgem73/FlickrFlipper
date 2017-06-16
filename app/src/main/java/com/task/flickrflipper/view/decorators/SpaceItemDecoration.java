package com.task.flickrflipper.view.decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by rafi on 17/6/17.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int span;

    public SpaceItemDecoration(int space, int span) {
        this.space = space;
        this.span = span;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildLayoutPosition(view);
        int column = pos % span;

        outRect.left = space - column * space / span;
        outRect.right = (column + 1) * space / span;

        if (pos < span) { // top edge
            outRect.top = space;
        }
        outRect.bottom = space;
    }

}
