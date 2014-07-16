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

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.manuelpeinado.glassactionbar.GlassActionBarHelper;
import com.manuelpeinado.glassactionbar.samples.stock.R;

public class ChangingContentActivity extends Activity {

    private GlassActionBarHelper helper;
    private ImageView imageView;
    private static final int[] IMAGES = {
        R.drawable.new_york_city_1,
        R.drawable.new_york_city_2,
        R.drawable.new_york_city_3
    };
    private int currentImage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper = new GlassActionBarHelper().contentLayout(R.layout.activity_changing_content);
        setContentView(helper.createView(this));
        imageView = (ImageView) findViewById(R.id.imageView);
    }
    
    public void changeImage(View view) {
        imageView.setImageResource(IMAGES[++currentImage % IMAGES.length]);
        helper.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
