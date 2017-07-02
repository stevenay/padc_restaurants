package com.naylinaung.padc_week3_restaurant.data.agents.retrofit;

import com.naylinaung.padc_week3_restaurant.data.agents.RestaurantDataAgent;
import com.naylinaung.padc_week3_restaurant.data.responses.RestaurantListResponse;
import com.naylinaung.padc_week3_restaurant.data.utils.RestaurantsConstants;
import com.naylinaung.padc_week3_restaurant.events.DataEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dell on 6/26/2017.
 */

public class RetrofitDataAgent implements RestaurantDataAgent {

    private static RetrofitDataAgent objInstance;

    private final RestaurantApi theApi;

    private RetrofitDataAgent() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestaurantsConstants.RESTAURANT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        this.theApi = retrofit.create(RestaurantApi.class);
    }

    public static RetrofitDataAgent getInstance() {
        if (objInstance == null) {
            objInstance = new RetrofitDataAgent();
        }
        return objInstance;
    }

    @Override
    public void loadRestaurants() {
        Call<RestaurantListResponse> loadRestaurantCall = theApi.loadRestaurants(RestaurantsConstants.ACCESS_TOKEN);
        loadRestaurantCall.enqueue(new Callback<RestaurantListResponse>() {
            @Override
            public void onResponse(Call<RestaurantListResponse> call, Response<RestaurantListResponse> response) {
                RestaurantListResponse restaurantListResponse = response.body();
                if (restaurantListResponse == null) {
                    EventBus.getDefault().post(new DataEvent.RestaurantDataFailedEvent(response.message()));
                } else {
                    EventBus.getDefault().post(new DataEvent.RestaurantDataLoadedEvent("Data Loaded", restaurantListResponse.getRestaurantList()));
                }
            }

            @Override
            public void onFailure(Call<RestaurantListResponse> call, Throwable t) {
                EventBus.getDefault().post(new DataEvent.RestaurantDataFailedEvent(t.getMessage()));
            }
        });
    }

}
