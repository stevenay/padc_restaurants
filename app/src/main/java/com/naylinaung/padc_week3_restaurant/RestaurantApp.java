package com.naylinaung.padc_week3_restaurant;

import android.app.Application;
import android.content.Context;

import com.naylinaung.padc_week3_restaurant.data.models.RestaurantModel;

/**
 * Created by NayLinAung on 6/25/2017.
 */

public class RestaurantApp extends Application {

    public static String TAG = "RestaurantApp";

    public RestaurantApp() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        RestaurantModel.getInstance(getApplicationContext()).loadRestaurants();
    }

}
