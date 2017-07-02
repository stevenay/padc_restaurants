package com.naylinaung.padc_week3_restaurant.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.naylinaung.padc_week3_restaurant.RestaurantApp;
import com.naylinaung.padc_week3_restaurant.data.persistence.RestaurantContract;

public class RestaurantVO {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("addr-short")
    @Expose
    private String addrShort;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("total-rating-count")
    @Expose
    private int totalRatingCount;

    @SerializedName("average-rating-value")
    @Expose
    private float averageRatingValue;

    @SerializedName("is-ad")
    @Expose
    private boolean isAd;

    @SerializedName("is-new")
    @Expose
    private boolean isNew;

    @SerializedName("tags")
    @Expose
    private String[] tags = null;

    @SerializedName("lead-time-in-min")
    @Expose
    private int leadTimeInMin;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddrShort() {
        return addrShort;
    }

    public void setAddrShort(String addrShort) {
        this.addrShort = addrShort;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getTotalRatingCount() {
        return totalRatingCount;
    }

    public void setTotalRatingCount(Integer totalRatingCount) {
        this.totalRatingCount = totalRatingCount;
    }

    public float getAverageRatingValue() {
        return averageRatingValue;
    }

    public void setAverageRatingValue(float averageRatingValue) {
        this.averageRatingValue = averageRatingValue;
    }

    public Boolean getIsAd() {
        return isAd;
    }

    public void setIsAd(Boolean isAd) {
        this.isAd = isAd;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Integer getLeadTimeInMin() {
        return leadTimeInMin;
    }

    public void setLeadTimeInMin(Integer leadTimeInMin) {
        this.leadTimeInMin = leadTimeInMin;
    }

    public static void saveRestaurants(Context context, List<RestaurantVO> restaurantList) {
        ContentValues[] restaurantCVs = new ContentValues[restaurantList.size()];
        for (int index = 0; index < restaurantList.size(); index++) {
            RestaurantVO restaurant = restaurantList.get(index);
            restaurantCVs[index] = restaurant.parseToContentValues();
        }

        // Bulk insert into attractions.
        int insertedCount = context.getContentResolver().bulkInsert(RestaurantContract.RestaurantEntry.CONTENT_URI, restaurantCVs);

        Log.d(RestaurantApp.TAG, "Bulk inserted into restaurant table : " + insertedCount);
    }

    private ContentValues parseToContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(RestaurantContract.RestaurantEntry.COLUMN_TITLE, title);
        cv.put(RestaurantContract.RestaurantEntry.COLUMN_ADDRESS, addrShort);
        cv.put(RestaurantContract.RestaurantEntry.COLUMN_IMAGE, image);
        cv.put(RestaurantContract.RestaurantEntry.COLUMN_TOTAL_RATING_COUNT, totalRatingCount);
        cv.put(RestaurantContract.RestaurantEntry.COLUMN_AVG_RATING_COUNT, averageRatingValue);
        cv.put(RestaurantContract.RestaurantEntry.COLUMN_IS_AD, isAd);
        cv.put(RestaurantContract.RestaurantEntry.COLUMN_IS_NEW, isNew);
        cv.put(RestaurantContract.RestaurantEntry.COLUMN_LEAD_TIME, leadTimeInMin);

        return cv;
    }

    private static RestaurantVO parseFromCursor(Cursor data) {
        RestaurantVO restaurant = new RestaurantVO();
        restaurant.title = data.getString(data.getColumnIndex(RestaurantContract.RestaurantEntry.COLUMN_TITLE));
        restaurant.addrShort = data.getString(data.getColumnIndex(RestaurantContract.RestaurantEntry.COLUMN_ADDRESS));
        restaurant.image = data.getString(data.getColumnIndex(RestaurantContract.RestaurantEntry.COLUMN_IMAGE));
        restaurant.totalRatingCount = data.getInt(data.getColumnIndex(RestaurantContract.RestaurantEntry.COLUMN_TOTAL_RATING_COUNT));
        restaurant.averageRatingValue = data.getFloat(data.getColumnIndex(RestaurantContract.RestaurantEntry.COLUMN_AVG_RATING_COUNT));
        restaurant.isAd = data.getInt(data.getColumnIndex(RestaurantContract.RestaurantEntry.COLUMN_IS_AD)) > 0;
        restaurant.isNew = data.getInt(data.getColumnIndex(RestaurantContract.RestaurantEntry.COLUMN_IS_NEW)) > 0;
        restaurant.leadTimeInMin = data.getInt(data.getColumnIndex(RestaurantContract.RestaurantEntry.COLUMN_LEAD_TIME));

        return restaurant;
    }

}

