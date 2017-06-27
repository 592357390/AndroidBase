/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.handarui.androidbase;

import android.app.Application;

import com.handarui.baselib.AndroidBase;

/**
 * Android Main Application
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    public MyApplication() {
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String httpPath = "http://47.88.136.40/qy-milier-server-arb/";
        String httpsPath = "http://47.88.136.40/qy-milier-server-arb/";
        AndroidBase.AndroidBaseBuilder androidBaseBuilder = new AndroidBase.AndroidBaseBuilder(this, httpPath, httpsPath);
        androidBaseBuilder.setDebug(BuildConfig.DEBUG);
        androidBaseBuilder.setCerFileName("mycert.cer");
        androidBaseBuilder.createAndroidBase().init();
    }
}
