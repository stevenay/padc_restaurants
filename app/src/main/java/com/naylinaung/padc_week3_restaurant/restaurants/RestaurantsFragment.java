package com.naylinaung.padc_week3_restaurant.restaurants;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naylinaung.padc_week3_restaurant.R;
import com.naylinaung.padc_week3_restaurant.data.models.RestaurantModel;
import com.naylinaung.padc_week3_restaurant.data.persistence.RestaurantsContract;
import com.naylinaung.padc_week3_restaurant.data.utils.RestaurantsConstants;
import com.naylinaung.padc_week3_restaurant.data.vos.RestaurantVO;
import com.naylinaung.padc_week3_restaurant.events.DataEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsFragment extends Fragment
    implements LoaderManager.LoaderCallbacks<Cursor> {

    private RestaurantViewHolder.ControllerRestaurantItem mController;
    private RestaurantsAdapter mRestaurantsAdapter;

    private RecyclerView mRvRestaurantList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mTvRestaurantCount;

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
        else
            throw new RuntimeException("Wrong type of host activity");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(RestaurantsConstants.RESTAURANT_LIST_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.restaurants_frag, container, false);
        mRestaurantsAdapter = new RestaurantsAdapter(getContext(), mController);

        mRvRestaurantList = (RecyclerView) v.findViewById(R.id.rv_restaurant_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        mTvRestaurantCount = (TextView) v.findViewById(R.id.tv_restaurant_count);

        // Set up progress indicator
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.theme_primary),
                ContextCompat.getColor(getActivity(), R.color.theme_accent),
                ContextCompat.getColor(getActivity(), R.color.theme_primary)
        );

        // SwipeRefreshLayout onRefresh
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RestaurantModel.getInstance().loadRestaurants();
            }
        });

        // Set up recycler view and add Adapter
        mRvRestaurantList.setAdapter(mRestaurantsAdapter);
        mRvRestaurantList.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRvRestaurantList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return v;
    }

    private void bindData(List<RestaurantVO> restaurantList) {
        mRestaurantsAdapter.setNewData(restaurantList);

        String countTitle = restaurantList.size() + " restaurants deliver to you";
        mTvRestaurantCount.setText(countTitle);
    }

    //region
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                RestaurantsContract.RestaurantsEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<RestaurantVO> restaurantList = new ArrayList<>();
        if (data != null && data.moveToFirst()) {
            do {
                RestaurantVO restaurant = RestaurantVO.parseFromCursor(data);
                restaurant.setTags(RestaurantVO.loadTagsByTitle(getContext(), restaurant.getTitle()));
                restaurantList.add(restaurant);
            } while (data.moveToNext());
        }

        bindData(restaurantList);

        // Make sure setRefreshing() is called
        // after the layout is done with everything else.
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    //endregion
}
