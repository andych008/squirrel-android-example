/**
 * Copyright 2014 Vyacheslav Blinov (blinov.vyacheslav@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.dant3.squirrel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
    private CrossLightsController crossLightsController;

    protected ImageView red;
    protected ImageView yellow;
    protected ImageView green;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        red = (ImageView) findViewById(R.id.red);
        yellow = (ImageView) findViewById(R.id.yellow);
        green = (ImageView) findViewById(R.id.green);

        init();

        findViewById(R.id.redBtn).setOnClickListener(view -> {
            crossLightsController.fire(CrossLightsController.Event.TurnRed, Object.class);
        });

        findViewById(R.id.yellowBtn).setOnClickListener(view -> {
            crossLightsController.fire(CrossLightsController.Event.TurnYellow, Object.class);
        });
        findViewById(R.id.greenBtn).setOnClickListener(view -> {
            crossLightsController.fire(CrossLightsController.Event.TurnGreen, Object.class);
        });
    }

    protected void init() {
        crossLightsController = CrossLightsController.create();
        crossLightsController.addObserver(this, "updateView");
        crossLightsController.start();


        updateView();
    }

    public void updateView() {
        Light currentLight = crossLightsController.getCurrentState();
        switch (currentLight) {
            case RED:
                red.setVisibility(View.VISIBLE);
                yellow.setVisibility(View.INVISIBLE);
                green.setVisibility(View.INVISIBLE);
                break;
            case YELLOW:
                red.setVisibility(View.INVISIBLE);
                yellow.setVisibility(View.VISIBLE);
                green.setVisibility(View.INVISIBLE);
                break;
            case GREEN:
                red.setVisibility(View.INVISIBLE);
                yellow.setVisibility(View.INVISIBLE);
                green.setVisibility(View.VISIBLE);
                break;
        }
    }
}
