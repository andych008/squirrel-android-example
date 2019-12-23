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

import android.os.Handler;
import android.util.Log;

import com.github.dant3.squirrel.utils.Observable;
import com.github.dant3.squirrel.utils.ObservableSupport;

import org.squirrelframework.foundation.fsm.AnonymousAction;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

import lombok.experimental.Delegate;

public class CrossLightsController extends
        AbstractStateMachine<CrossLightsController, Light, CrossLightsController.Event, Object>
        implements Observable {

    @Delegate
    private final ObservableSupport observableSupport =
            new ObservableSupport();


    public static CrossLightsController create() {
        StateMachineBuilder<CrossLightsController, Light, Event, Object> builder =
                StateMachineBuilderFactory.create(CrossLightsController.class, Light.class, Event.class, Object.class);

        builder.externalTransition().from(Light.RED).to(Light.YELLOW).on(Event.TurnYellow);
        builder.externalTransition().from(Light.GREEN).to(Light.YELLOW).on(Event.TurnYellow);
        builder.externalTransition().from(Light.YELLOW).to(Light.GREEN).on(Event.TurnGreen);
        builder.externalTransition().from(Light.YELLOW).to(Light.RED).on(Event.TurnRed);

        FireEventAfter fireEventAfter = new FireEventAfter();
        builder.onExit(Light.RED).perform(fireEventAfter);
        builder.onExit(Light.YELLOW).perform(fireEventAfter);
        builder.onExit(Light.GREEN).perform(fireEventAfter);


        return builder.newStateMachine(Light.RED);
    }

    private static class FireEventAfter extends AnonymousAction<CrossLightsController, Light, Event, Object> {

        private final Handler handler=new Handler();
        @Override
        public void execute(Light from, Light to, Event event, Object context, CrossLightsController stateMachine) {
            handler.post(() -> {
                stateMachine.notifyX();
                Log.i("CrossLightsController", "After1 execute() called with: from = [" + from + "], to = [" + to + "], event = [" + event + "], context = [" + context + "], stateMachine = [" + stateMachine + "]");
            });
        }
    }


    public static enum Event {
        TurnRed,
        TurnYellow,
        TurnGreen
    }
}
