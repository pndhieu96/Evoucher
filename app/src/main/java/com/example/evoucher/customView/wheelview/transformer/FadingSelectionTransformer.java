package com.example.evoucher.customView.wheelview.transformer;

import android.graphics.drawable.Drawable;

import com.example.evoucher.customView.wheelview.WheelView;


/**
 * Created by Nguyen Duc Manh on 01/8/17.
 */

public class FadingSelectionTransformer implements WheelSelectionTransformer {

    @Override
    public void transform(Drawable drawable, WheelView.ItemState itemState) {
        float relativePosition = Math.abs(itemState.getRelativePosition());
        int alpha = (int) ((1f - Math.pow(relativePosition, 2.5f)) * 255f);

        //clamp to between 0 and 255
        if (alpha > 255) alpha = 255;
        else if (alpha < 0) alpha = 0;

        drawable.setAlpha(alpha);
    }
}
