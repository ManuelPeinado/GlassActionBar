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
import android.util.Log;
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

public class GlassActionBarHelper implements OnGlobalLayoutListener, OnScrollChangedListener, BlurTask.Listener {
    private int contentLayout;
    private FrameLayout frame;
    private View content;
    private ImageView blurredOverlay;
    private int actionBarHeight;
    private int width;
    private int height;
    private Bitmap scaled;
    private BlurTask blurTask;
    private int lastScrollPosition = -1;
    private NotifyingScrollView scrollView;
    private static final int DOWN_SAMPLING = 3;
    private static final String TAG = "GlassActionBarHelper";
    private boolean verbose = true;

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
            scrollView = (NotifyingScrollView) content;
            scrollView.setOnScrollChangedListener(this);
        }

        actionBarHeight = (int) context.getResources().getDimension(R.dimen.abs__action_bar_default_height);
        return frame;
    }

    public void invalidate() {
        if (verbose) Log.v(TAG, "invalidate()");
        scaled = null;
        computeBlurOverlay();
        updateBlurOverlay(lastScrollPosition, true);
    }

    @Override
    public void onGlobalLayout() {
        if (verbose) Log.v(TAG, "onGlobalLayout()");
        if (width != 0) {
            if (verbose) Log.v(TAG, "onGlobalLayout() - returning because not first time it's called");
            return;
        }
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(frame.getWidth(), MeasureSpec.AT_MOST);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.EXACTLY);
        content.measure(widthMeasureSpec, heightMeasureSpec);
        width = frame.getWidth();
        height = content.getMeasuredHeight();
        lastScrollPosition = scrollView != null ? scrollView.getScrollY() : 0; 
        invalidate();
    }

    private void computeBlurOverlay() {
        if (verbose) Log.v(TAG, "computeBlurOverlay()");
        if (scaled != null) {
            if (verbose) Log.v(TAG, "computeBlurOverlay() - returning because scaled is not null");
            return;
        }
        if (verbose) Log.v(TAG, "computeBlurOverlay() - drawing layout to canvas");
        int scrollPosition = 0;
        if (scrollView != null) {
            scrollPosition = scrollView.getScrollY();
            if (verbose) Log.v(TAG, "computeBlurOverlay() - scroll position is " + scrollPosition);
        }
        long start = System.nanoTime();

        scaled = Utils.drawViewToBitmap(content, width, height, DOWN_SAMPLING);

        long delta = System.nanoTime() - start;
        if (verbose) Log.v(TAG, "computeBlurOverlay() - drawing layout to canvas took " + delta/1e6f + " ms");
        if (scrollView != null) {
            if (verbose) Log.v(TAG, "computeBlurOverlay() - starting blur task");
            startBlurTask();
        } else {
            if (verbose) Log.v(TAG, "computeBlurOverlay() - not starting blur task because scrollView is null");
        }
        if (scrollView != null) {
            if (verbose) Log.v(TAG, "computeBlurOverlay() - restoring scroll from " + scrollView.getScrollY() + " to " + scrollPosition);
            scrollView.scrollTo(0, scrollPosition);
        }
    }

    private void startBlurTask() {
        if (blurTask != null) {
            if (verbose) Log.v(TAG, "startBlurTask() - task was already running, canceling it");
            blurTask.cancel();
        }
        blurTask = new BlurTask(frame.getContext(), this, scaled);
    }

    private void updateBlurOverlay(int top, boolean force) {
        if (verbose) Log.v(TAG, "updateBlurOverlay() - top=" + top);

        if (scaled == null) {
            if (verbose) Log.v(TAG, "updateBlurOverlay() - returning because scaled is null");
            return;
        }

        if (top < 0) {
            if (verbose) Log.v(TAG, "updateBlurOverlay() - clamping top to 0");
            top = 0;
        }
        if (!force && lastScrollPosition == top) {
            if (verbose) Log.v(TAG, "updateBlurOverlay() - returning because scroll position hasn't changed");
            return;
        }
        lastScrollPosition = top;
        Bitmap actionBarSection = Bitmap.createBitmap(scaled, 0, top / DOWN_SAMPLING, width / DOWN_SAMPLING,
                actionBarHeight / DOWN_SAMPLING);
        // Blur here until background finished (will make smooth jerky during the first second or so).
        Bitmap blurredBitmap;
        if (isBlurTaskFinished()) {
            if (verbose) Log.v(TAG, "updateBlurOverlay() - blur task finished, no need to blur content under action bar");
            blurredBitmap = actionBarSection;
        } else {
            if (verbose) Log.v(TAG, "updateBlurOverlay() - blur task not finished, blurring content under action bar");
            blurredBitmap = Blur.apply(frame.getContext(), actionBarSection);
        }
        Bitmap enlarged = Bitmap.createScaledBitmap(blurredBitmap, width, actionBarHeight, false);
        blurredBitmap.recycle();
        actionBarSection.recycle();
        blurredOverlay.setImageBitmap(enlarged);
    }

    private boolean isBlurTaskFinished() {
        return blurTask == null;
    }

    @Override
    public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
        if (verbose) Log.v(TAG, "onScrollChanged() - new scroll position is " + t);
        updateBlurOverlay(t, false);
    }

    @Override
    public void onBlurOperationFinished() {
        if (verbose) Log.v(TAG, "onBlurOperationFinished() - blur operation finished");
        blurTask = null;
        updateBlurOverlay(lastScrollPosition, true);
    }

}