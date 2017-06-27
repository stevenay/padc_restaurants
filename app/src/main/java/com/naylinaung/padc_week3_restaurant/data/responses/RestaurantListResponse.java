package com.naylinaung.padc_week3_restaurant.data.responses;

import com.google.gson.annotations.SerializedName;
import com.naylinaung.padc_week3_restaurant.data.vos.RestaurantVO;

import java.util.ArrayList;

/**
 * Created by Dell on 6/26/2017.
 */

public class RestaurantListResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("timeStamp")
    private String timeStamp;

    @SerializedName("restaurants")
    private ArrayList<RestaurantVO> restaurantList;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public ArrayList<RestaurantVO> getRestaurantList() {
        return restaurantList;
    }
}
