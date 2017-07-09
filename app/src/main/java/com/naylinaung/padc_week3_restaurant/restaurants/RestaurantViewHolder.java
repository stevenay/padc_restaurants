package com.naylinaung.padc_week3_restaurant.restaurants;

import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.naylinaung.padc_week3_restaurant.R;
import com.naylinaung.padc_week3_restaurant.base.BaseViewHolder;
import com.naylinaung.padc_week3_restaurant.data.vos.RestaurantVO;

import butterknife.BindView;

/**
 * Created by NayLinAung on 7/2/2017.
 */

public class RestaurantViewHolder extends BaseViewHolder<RestaurantVO> {

    @BindView(R.id.rb_rating)
    RatingBar rbRating;

    @BindView(R.id.tv_restaurant_title)
    TextView tvRestaurantTitle;

    @BindView(R.id.tv_tags)
    TextView tvTags;

    @BindView(R.id.iv_ad)
    ImageView ivAd;

    @BindView(R.id.tv_leading_in_min)
    TextView tvLeadingInMin;

    private RestaurantVO mData;
    private ControllerRestaurantItem mController;

    public RestaurantViewHolder(View itemView, ControllerRestaurantItem controller) {
        super(itemView);
        mController = controller;
    }

    @Override
    public void bind(RestaurantVO data) {
        mData = data;
        rbRating.setRating(data.getAverageRatingValue());
        tvRestaurantTitle.setText(data.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvLeadingInMin.setText(Html.fromHtml("delivers in <b>" + data.getLeadTimeInMin() + " min.</b>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvLeadingInMin.setText(Html.fromHtml("delivers in <b>" + data.getLeadTimeInMin() + " min.</b>"));
        }

        if (data.getTags() != null && data.getTags().length > 0)
            tvTags.setText(TextUtils.join(", ", data.getTags()));

        if (data.getIsAd())
            ivAd.setVisibility(View.VISIBLE);
        else
            ivAd.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        mController.onTapRestaurant();
    }

    public interface ControllerRestaurantItem {
        void onTapRestaurant();
    }
}
