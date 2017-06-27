package com.naylinaung.padc_week3_restaurant.data.vos;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    private Integer totalRatingCount;

    @SerializedName("average-rating-value")
    @Expose
    private Double averageRatingValue;

    @SerializedName("is-ad")
    @Expose
    private Boolean isAd;

    @SerializedName("is-new")
    @Expose
    private Boolean isNew;

    @SerializedName("tags")
    @Expose
    private String[] tags = null;

    @SerializedName("lead-time-in-min")
    @Expose
    private Integer leadTimeInMin;

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

    public Double getAverageRatingValue() {
        return averageRatingValue;
    }

    public void setAverageRatingValue(Double averageRatingValue) {
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

}

