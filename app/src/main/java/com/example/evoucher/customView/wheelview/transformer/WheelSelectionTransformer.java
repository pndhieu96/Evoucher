package com.example.evoucher.customView.wheelview.transformer;

import android.graphics.drawable.Drawable;

import com.example.evoucher.customView.wheelview.WheelView;


public interface WheelSelectionTransformer {
    void transform(Drawable drawable, WheelView.ItemState itemState);
}
