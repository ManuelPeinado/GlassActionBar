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
package com.manuelpeinado.glassactionbardemo;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.manuelpeinado.glassactionbar.GlassActionBar;
import com.manuelpeinado.glassactionbar.GlassActionBarHelper;

public class SettingsActivity extends SherlockActivity implements OnSeekBarChangeListener {

    private GlassActionBarHelper helper;
    private SeekBar radiusSeekBar;
    private TextView radiusTextView;
    private SeekBar downsamplingSeekBar;
    private TextView downsamplingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper = new GlassActionBarHelper().contentLayout(R.layout.activity_settings);
        setContentView(helper.createView(this));

        radiusSeekBar = (SeekBar) findViewById(R.id.radiusSeekBar);
        radiusSeekBar.setOnSeekBarChangeListener(this);
        radiusTextView = (TextView) findViewById(R.id.radiusTextView);
        initializeRadiusSeekBar();
        updateRadiusTextView();

        downsamplingSeekBar = (SeekBar) findViewById(R.id.downsamplingSeekBar);
        downsamplingSeekBar.setOnSeekBarChangeListener(this);
        downsamplingTextView = (TextView) findViewById(R.id.downsamplingTextView);
        initializeDownsamplingSeekBar();
        updateDownsamplingTextView();
    }

    private void initializeRadiusSeekBar() {
        setSeekBarValue(radiusSeekBar, helper.getBlurRadius(), GlassActionBar.MIN_BLUR_RADIUS, GlassActionBar.MAX_BLUR_RADIUS);
    }

    private void updateRadiusTextView() {
        int radius = helper.getBlurRadius();
        radiusTextView.setText("Blur radius=" + radius);
    }

    private void initializeDownsamplingSeekBar() {
        setSeekBarValue(downsamplingSeekBar, helper.getDownsampling(), GlassActionBar.MIN_DOWNSAMPLING, GlassActionBar.MAX_DOWNSAMPLING);
    }

    private void updateDownsamplingTextView() {
        downsamplingTextView.setText("Downsampling=" + helper.getDownsampling());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == radiusSeekBar) {
            float value = transformSeekBarValue(seekBar, GlassActionBar.MIN_BLUR_RADIUS, GlassActionBar.MAX_BLUR_RADIUS);
            helper.setBlurRadius(Math.round(value));
            updateRadiusTextView();
        } else {
            float value = transformSeekBarValue(seekBar, GlassActionBar.MIN_DOWNSAMPLING, GlassActionBar.MAX_DOWNSAMPLING);
            helper.setDownsampling(Math.round(value));
            updateDownsamplingTextView();
        }
    }

    private static void setSeekBarValue(SeekBar seekBar, float value, float min, float max) {
        float span = max - min;
        value = (value - min) / span;
        seekBar.setProgress(Math.round(value * seekBar.getMax()));
    }

    private static float transformSeekBarValue(SeekBar seekBar, int min, int max) {
        float value = seekBar.getProgress() / (float) seekBar.getMax();
        float span = max - min;
        return min + value * span;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
