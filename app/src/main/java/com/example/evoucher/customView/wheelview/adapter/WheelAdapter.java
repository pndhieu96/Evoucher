package com.example.evoucher.customView.wheelview.adapter;

import android.graphics.drawable.Drawable;

public interface WheelAdapter {

    /**
     * @param position the adapter position, between 0 and {@link #getCount()}.
     * @return the drawable to be drawn on the wheel at this adapter position.
     */
    Drawable getDrawable(int position);

    /**
     * @return the number of items in the adapter.
     */
    int getCount();
}
