package com.naylinaung.padc_week3_restaurant.data.models;

import android.content.Context;

import com.naylinaung.padc_week3_restaurant.data.vos.RestaurantVO;
import com.naylinaung.padc_week3_restaurant.events.DataEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by NayLinAung on 6/26/2017.
 */

public class RestaurantModel extends BaseModel {

    private static RestaurantModel objInstance;

    private Context mContext;

    private RestaurantModel() {
        super();
    }

    public static void init(Context context) {
        if (objInstance == null) {
            objInstance = new RestaurantModel();
        }

        objInstance.mContext = context;
    }

    public static RestaurantModel getInstance() {
        if (objInstance == null) {
            throw new RuntimeException("Need to init the model first before uses it.");
        }

        return objInstance;
    }

    public void loadRestaurants() {
        dataAgent.loadRestaurants();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void receiveRestaurantList(DataEvent.RestaurantDataLoadedEvent event) {
        RestaurantVO.saveRestaurants(mContext, event.getRestaurantList());
    }

}
