/*
 * Copyright (C) 2013 Manuel Peinado
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.manuelpeinado.glassactionbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.cyrilmottier.android.translucentactionbar.NotifyingScrollView;
import com.cyrilmottier.android.translucentactionbar.NotifyingScrollView.OnScrollChangedListener;
import com.manuelpeinado.glassactionbar.R;

public class GlassActionBarHelper implements OnGlobalLayoutListener, OnScrollChangedListener {
    private int contentLayout;
    private FrameLayout frame;
    private View content;
    private ImageView blurredOverlay;
    private int actionBarHeight;
    private int width;
    private int height;
    private Bitmap scaled;
    private static final int DOWN_SAMPLING = 3;

    public GlassActionBarHelper contentLayout(int layout) {
        this.contentLayout = layout;
        return this;
    }

    public View createView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        frame = (FrameLayout) inflater.inflate(R.layout.gab__frame, null);
        content = inflater.inflate(contentLayout, (ViewGroup) frame, false);
        frame.addView(content, 0);

        frame.getViewTreeObserver().addOnGlobalLayoutListener(this);
        blurredOverlay = (ImageView) frame.findViewById(R.id.blurredOverlay);

        if (content instanceof NotifyingScrollView) {
            NotifyingScrollView scrollView = (NotifyingScrollView) content;
            scrollView.setOnScrollChangedListener(this);
        }

        actionBarHeight = (int) context.getResources().getDimension(R.dimen.abs__action_bar_default_height);
        return frame;
    }

    @Override
    public void onGlobalLayout() {
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(frame.getWidth(), MeasureSpec.AT_MOST);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.EXACTLY);
        content.measure(widthMeasureSpec, heightMeasureSpec);
        width = frame.getWidth();
        height = content.getMeasuredHeight();
        computeBlurOverlay();
        updateBlurOverlay(0);
    }

    private void computeBlurOverlay() {
        if (scaled == null) {
            content.layout(0, 0, width, height);
            Bitmap original = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(original);
            content.draw(c);
            scaled = Bitmap.createScaledBitmap(original, width / DOWN_SAMPLING, height / DOWN_SAMPLING, true);
            original.recycle();
            frame.requestLayout();
        }
    }

    private void updateBlurOverlay(int top) {
        if (top < 0) {
            top = 0;
        }
        Bitmap actionBarSection = Bitmap.createBitmap(scaled, 0, top / DOWN_SAMPLING, width / DOWN_SAMPLING,
                actionBarHeight / DOWN_SAMPLING);
        Bitmap blurredBitmap = Blur.apply(actionBarSection);
        Bitmap enlarged = Bitmap.createScaledBitmap(blurredBitmap, width, actionBarHeight, false);
        blurredBitmap.recycle();
        actionBarSection.recycle();
        blurredOverlay.setImageBitmap(enlarged);
    }

    @Override
    public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
        updateBlurOverlay(t);
    }

}