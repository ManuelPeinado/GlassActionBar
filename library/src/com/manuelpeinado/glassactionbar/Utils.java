package com.manuelpeinado.glassactionbar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class Utils {
    public static Bitmap drawViewToBitmap(View view, int width, int height, int downSampling) {
        int heightCopy = view.getHeight();
        view.layout(0, 0, width, height);
        Bitmap original = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(original);
        if (downSampling > 1) {
            c.scale(1f / downSampling, 1f / downSampling);
        }
        view.draw(c);
        view.layout(0, 0, width, heightCopy);
        return original;
    }
}
