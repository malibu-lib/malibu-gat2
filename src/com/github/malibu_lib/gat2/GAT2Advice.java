/*******************************************************************************
 * Copyright (c) 2012 MASConsult Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.github.malibu_lib.gat2;

import android.app.Activity;
import android.app.Application;

import com.github.malibu_lib.BaseAdvice;
import com.github.malibu_lib.Pointcut;
import com.github.malibu_lib.pointcuts.activity.OnStartActivityAdvice;
import com.github.malibu_lib.pointcuts.activity.OnStopActivityAdvice;
import com.github.malibu_lib.pointcuts.application.OnCreateApplicationAdvice;
import com.google.analytics.tracking.android.EasyTracker;

/**
 * The advice can be used for {@link Application} and {@link Activity}. On
 * Application sets the context to EasyTracker so it can be used. On Activity it
 * tracks start and stop.
 * 
 * @author Geno Roupsky &lt;geno&#064;masconsult.eu&gt;
 */
public class GAT2Advice extends BaseAdvice implements OnCreateApplicationAdvice,
        OnStartActivityAdvice, OnStopActivityAdvice {

    public GAT2Advice(Pointcut pointcut) {
        super(pointcut);
    }

    @Override
    public void onCreate(Application application) {
        EasyTracker.getInstance().setContext(application);
    }

    @Override
    public void onStart(Activity activity) {
        EasyTracker.getInstance().activityStart(activity);
    }

    @Override
    public void onStop(Activity activity) {
        EasyTracker.getInstance().activityStop(activity);
    }

}
