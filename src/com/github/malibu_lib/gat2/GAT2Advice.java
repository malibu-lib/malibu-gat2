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

import android.Manifest.permission;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.github.malibu_lib.BaseAdvice;
import com.github.malibu_lib.Pointcut;
import com.github.malibu_lib.pointcuts.activity.OnCreateActivityAdvice;
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
        OnStartActivityAdvice, OnStopActivityAdvice, OnCreateActivityAdvice {

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

    @Override
    public void onCreate(Activity activity, Bundle savedInstanceState) {
        // Get the intent that started this Activity.
        Intent intent = activity.getIntent();
        Uri uri = intent.getData();

        if (uri != null) {
            if (uri.getQueryParameter("utm_source") != null) {
                // Use campaign parameters if avaialble.
                EasyTracker.getTracker().setCampaign(uri.getPath());
            } else if (uri.getQueryParameter("referrer") != null) {
                // Otherwise, try to find a referrer parameter.
                EasyTracker.getTracker().setReferrer(
                        uri.getQueryParameter("referrer"));
            }
        }

        if (BuildConfig.DEBUG) {

            if (activity.getPackageManager().checkPermission(permission.ACCESS_NETWORK_STATE,
                    activity.getPackageName()) == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(activity, "ACCESS_NETWORK_STATE permission not granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }

            if (activity.getPackageManager().checkPermission(permission.INTERNET,
                    activity.getPackageName()) == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(activity, "INTERNET permission not granted", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

}
