package com.manuelpeinado.glassactionbardemo;

import java.util.Arrays;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class ImagesAdapter extends BaseAdapter {
    private Drawable[] drawables;

    public ImagesAdapter(Context context) {
        drawables = new Drawable[] {
                context.getResources().getDrawable(R.drawable.new_york_city_1),
                context.getResources().getDrawable(R.drawable.new_york_city_2),
                context.getResources().getDrawable(R.drawable.new_york_city_3)
        };
    }
    

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Drawable getItem(int position) {
        return drawables[position % drawables.length];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new ImageView(parent.getContext());
        }
        ImageView imageView = (ImageView) convertView;
        imageView.setImageDrawable(getItem(position));
        return imageView;
    }

}
