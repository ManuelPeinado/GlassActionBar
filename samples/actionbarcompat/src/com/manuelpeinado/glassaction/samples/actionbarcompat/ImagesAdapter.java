package com.manuelpeinado.glassaction.samples.actionbarcompat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.manuelpeinado.glassaction.samples.actionbarcompat.R;


public class ImagesAdapter extends BaseAdapter {
    private Drawable drawable;

    public ImagesAdapter(Context context) {
        drawable = context.getResources().getDrawable(R.drawable.new_york_city_1);
    }
    

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Drawable getItem(int position) {
        return drawable;
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
