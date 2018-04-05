package net.webnetworksolutions.mama.support;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by Benja on 3/28/2018.
 */

public class MyApp extends Application {

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
}