package com.robson.icounts;

import android.app.Application;
import android.provider.Settings;

import com.robson.icounts.utils.AppUtil;

/**
 * Created by robson on 09/02/16.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.CONTEXT = getApplicationContext();
        AppUtil.DEVICE_ID = Settings.Secure.getString(AppUtil.CONTEXT.getContentResolver(), Settings.Secure.ANDROID_ID);

        // FIXME: Alterar o local do IP da API
        AppUtil.URI_SERVER = "http://192.168.1.34:1985";
    }

}
