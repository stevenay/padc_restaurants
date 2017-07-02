package com.naylinaung.padc_week3_restaurant.restaurants;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.naylinaung.padc_week3_restaurant.R;
import com.naylinaung.padc_week3_restaurant.data.models.RestaurantModel;
import com.naylinaung.padc_week3_restaurant.events.DataEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsFragment extends Fragment {

    private RestaurantViewHolder.ControllerRestaurantItem mController;
    private RestaurantsAdapter mRestaurantsAdapter;

    private RecyclerView rvRestaurantList;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static RestaurantsFragment newInstance() {
        return new RestaurantsFragment();
    }

    public RestaurantsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RestaurantViewHolder.ControllerRestaurantItem)
            mController = (RestaurantViewHolder.ControllerRestaurantItem) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.restaurants_frag, container, false);
        mRestaurantsAdapter = new RestaurantsAdapter(getContext(), mController);

        rvRestaurantList = (RecyclerView) v.findViewById(R.id.rv_restaurant_list);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);

        // Set up progress indicator
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.theme_primary),
                ContextCompat.getColor(getActivity(), R.color.theme_accent),
                ContextCompat.getColor(getActivity(), R.color.theme_primary)
        );

        // SwipeRefreshLayout onRefresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RestaurantModel.getInstance(getContext()).loadRestaurants();
            }
        });

        // Set up recycler view and add Adapter
        rvRestaurantList.setAdapter(mRestaurantsAdapter);
        rvRestaurantList.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rvRestaurantList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return v;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveRestaurantList(DataEvent.RestaurantDataLoadedEvent event) {
        mRestaurantsAdapter.setNewData(event.getRestaurantList());

        // Make sure setRefreshing() is called
        // after the layout is done with everything else.
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
