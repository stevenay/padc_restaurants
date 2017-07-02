package com.naylinaung.padc_week3_restaurant.events;

import com.naylinaung.padc_week3_restaurant.data.vos.RestaurantVO;

import java.util.List;

/**
 * Created by NayLinAung on 6/27/2017.
 */

public class DataEvent {

    public static class RestaurantDataLoadedEvent {
        private String extraMessage;
        private List<RestaurantVO> restaurantList;

        public RestaurantDataLoadedEvent(String extraMessage, List<RestaurantVO> restaurantList) {
            this.extraMessage = extraMessage;
            this.restaurantList = restaurantList;
        }

        public String getExtraMessage() {
            return extraMessage;
        }

        public List<RestaurantVO> getRestaurantList() {
            return restaurantList;
        }
    }

    public static class RestaurantDataFailedEvent {
        private String extraMessage;

        public RestaurantDataFailedEvent(String extraMessage) {
            this.extraMessage = extraMessage;
        }

        public String getExtraMessage() {
            return extraMessage;
        }
    }

}
