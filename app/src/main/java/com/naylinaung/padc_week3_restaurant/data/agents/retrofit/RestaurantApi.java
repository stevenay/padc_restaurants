package com.naylinaung.padc_week3_restaurant.data.agents.retrofit;

import com.naylinaung.padc_week3_restaurant.data.responses.RestaurantListResponse;
import com.naylinaung.padc_week3_restaurant.data.utils.RestaurantsConstants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by NayLinAung on 6/26/2017.
 */

public interface RestaurantApi {

    @FormUrlEncoded
    @POST(RestaurantsConstants.API_GET_RESTAURANT_LIST)
    Call<RestaurantListResponse> loadRestaurants(
        @Field(RestaurantsConstants.PARAM_ACCESS_TOKEN) String param);

}
