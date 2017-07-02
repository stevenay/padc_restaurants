package com.naylinaung.padc_week3_restaurant.restaurants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naylinaung.padc_week3_restaurant.R;
import com.naylinaung.padc_week3_restaurant.base.BaseRecyclerAdapter;
import com.naylinaung.padc_week3_restaurant.data.vos.RestaurantVO;
import com.naylinaung.padc_week3_restaurant.restaurants.RestaurantViewHolder.ControllerRestaurantItem;

/**
 * Created by NayLinAung on 7/2/2017.
 */

public class RestaurantsAdapter extends BaseRecyclerAdapter<RestaurantViewHolder, RestaurantVO> {

    private ControllerRestaurantItem mControllerRestaurantItem;

    public RestaurantsAdapter(Context context, ControllerRestaurantItem controller) {
        super(context);
        mControllerRestaurantItem = controller;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
        return new RestaurantViewHolder(itemView, mControllerRestaurantItem);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

}
