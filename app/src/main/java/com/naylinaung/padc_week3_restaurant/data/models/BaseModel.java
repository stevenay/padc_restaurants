package com.naylinaung.padc_week3_restaurant.data.models;

import com.naylinaung.padc_week3_restaurant.data.agents.RestaurantDataAgent;
import com.naylinaung.padc_week3_restaurant.data.agents.retrofit.RetrofitDataAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by naylinaung on 7/15/16.
 */
public abstract class BaseModel {

    private static final int INIT_DATA_AGENT_OFFLINE = 1;
    private static final int INIT_DATA_AGENT_HTTP_URL_CONNECTION = 2;
    private static final int INIT_DATA_AGENT_OK_HTTP = 3;
    private static final int INIT_DATA_AGENT_RETROFIT = 4;

    protected RestaurantDataAgent dataAgent;

    public BaseModel() {
        initDataAgent(INIT_DATA_AGENT_RETROFIT);

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    private void initDataAgent(int initType) {
        switch (initType) {
//            case INIT_DATA_AGENT_OFFLINE:
//                dataAgent = OfflineDataAgent.getInstance();
//                break;
//            case INIT_DATA_AGENT_HTTP_URL_CONNECTION:
//                dataAgent = HttpUrlConnectionDataAgent.getInstance();
//                break;
//            case INIT_DATA_AGENT_OK_HTTP:
//                dataAgent = OkHttpDataAgent.getInstance();
//                break;
            case INIT_DATA_AGENT_RETROFIT:
                dataAgent = RetrofitDataAgent.getInstance();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Object obj) {

    }
}
