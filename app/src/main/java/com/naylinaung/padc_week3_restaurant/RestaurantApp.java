package com.naylinaung.padc_week3_restaurant;

import android.app.Application;
import android.content.Context;

/**
 * Created by NayLinAung on 6/25/2017.
 */

public class RestaurantApp extends Application {

    private static Context context;

    public RestaurantApp() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
