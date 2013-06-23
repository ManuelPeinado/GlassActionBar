package com.manuelpeinado.glassactionbar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class Utils {
    public static Bitmap drawViewToBitmap(View view, int width, int height, int downSampling) {
        
        // TODO use ony one bitmap if downSampling=1
        
        int heightCopy = view.getHeight();
        view.layout(0, 0, width, height);
        Bitmap original = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(original);
        view.draw(c);
        Bitmap result = Bitmap.createScaledBitmap(original, width / downSampling, height / downSampling, true);
        view.layout(0, 0, width, heightCopy);
        original.recycle();
        return result;
    }

}
