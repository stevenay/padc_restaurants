package com.naylinaung.padc_week3_restaurant.restaurants;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.naylinaung.padc_week3_restaurant.R;
import com.naylinaung.padc_week3_restaurant.utils.ActivityUtils;

public class RestaurantsActivity extends AppCompatActivity
    implements RestaurantViewHolder.ControllerRestaurantItem {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurants_act);

        // Setup Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            ActivityUtils
                    .addFragmentToActivity(getSupportFragmentManager(),
                            RestaurantsFragment.newInstance(),
                            R.id.contentFrame);
        }
    }

    @Override
    public void onTapRestaurant() {
        Toast.makeText(this, "Tap on restaurant", Toast.LENGTH_SHORT).show();
    }
}
