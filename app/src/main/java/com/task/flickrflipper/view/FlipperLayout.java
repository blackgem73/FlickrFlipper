package com.task.flickrflipper.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by rafi on 17/6/17.
 */

public class FlipperLayout extends FrameLayout {

    private static final int ANIMATION_DURATION = 250;

    private boolean isFlipping;

    public FlipperLayout(@NonNull Context context) {
        super(context);
    }

    public FlipperLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlipperLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FlipperLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public boolean isFlipping() {
        return isFlipping;
    }

    public void flip(int foregroundViewPosition) {

        int childCount = getChildCount();
        if (childCount != 2)
            throw new IllegalStateException("Layout must have 2 children");

        this.isFlipping = true;
        final View foreground = getChildAt(foregroundViewPosition);
        final View background = getChildAt(foregroundViewPosition == 0 ? 1 : 0);

        foreground.setVisibility(VISIBLE);
        background.setVisibility(INVISIBLE);

        foreground.animate().rotationYBy(-90).setDuration(ANIMATION_DURATION).start();
        background.setRotationY(90);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                foreground.setVisibility(INVISIBLE);
                background.setVisibility(VISIBLE);
                background.animate().rotationYBy(-90).setDuration(ANIMATION_DURATION).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        background.setRotationY(0);
                        foreground.setRotationY(0);
                        isFlipping = false;
                    }
                }).start();
            }
        }, ANIMATION_DURATION);
    }


}
