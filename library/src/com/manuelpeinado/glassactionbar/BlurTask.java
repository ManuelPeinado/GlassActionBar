package com.manuelpeinado.glassactionbar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;

public class BlurTask {
    protected static final String TAG = "BlurTask";
    private Bitmap source;
    private Canvas canvas;
    private AsyncTask<Void, Void, Void> task;
    private Bitmap blurred;
    private Listener listener;
    private boolean finished;

    public interface Listener {
        void onBlurOperationFinished();
    }

    public BlurTask(Listener listener, Bitmap source) {
        this.listener = listener;
        this.source = source;
        canvas = new Canvas(source);
        startTask();
    }

    private void startTask() {
        task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... args) {
                blurSourceBitmap();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                canvas.drawBitmap(blurred, 0, 0, null);
                blurred.recycle();
                finished = true;
                listener.onBlurOperationFinished();
            }
        };
        task.execute();
    }

    private void blurSourceBitmap() {
        Bitmap section = source;
        if (section == null) {
            // Probably indicates we've reached the end.
            return;
        }
        blurred = Blur.apply(source);
    }

    public boolean isFinished() {
        return finished;
    }
}
