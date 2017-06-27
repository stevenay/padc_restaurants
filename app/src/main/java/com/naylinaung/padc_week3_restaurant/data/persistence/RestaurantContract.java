package com.naylinaung.padc_week3_restaurant.data.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.naylinaung.padc_week3_restaurant.RestaurantApp;

/**
 * Created by NayLinAung on 6/25/2017.
 *
 * Contract class for interacting with {@link RestaurantProvider}.
 * The backing {@link android.content.ContentProvider} assumes that {@link android.net.Uri}
 * are generated using stronger {@link java.lang.String} identifiers, instead of
 * {@code int} {@link android.provider.BaseColumns#_ID} values, which are prone to shuffle during sync.
 */

public class RestaurantContract {

    public static final String CONTENT_AUTHORITY = RestaurantApp.class.getPackage().getName();
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_RESTAURANTS = "restaurants";
    public static final String PATH_TAGS = "tags";
    public static final String PATH_RESTAURANT_TAGS = "restaurant_tags";

    interface TagsColumns {
        String COLUMN_TAG_NAME = "tag_name";
    }

    public static final class RestaurantEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RESTAURANTS).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESTAURANTS;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESTAURANTS;

        public static final String TABLE_NAME = "restaurants";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_TOTAL_RATING_COUNT = "total_rating_count";
        public static final String COLUMN_AVG_RATING_COUNT = "average_rating_count";
        public static final String COLUMN_IS_AD = "is_ad";
        public static final String COLUMN_IS_NEW = "is_new";
        public static final String COLUMN_LEAD_TIME = "lead_time_in_min";

        public static Uri buildRestaurantUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildRestaurantUriWithTitle(String title) {
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_TITLE, title)
                    .build();
        }

        public static String getTitleFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_TITLE);
        }

    }

    public static final class TagEntry implements TagsColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TAGS).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TAGS;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TAGS;

        public static final String TABLE_NAME = "tags";

        public static Uri buildTagsUri() {
            return CONTENT_URI;
        }

        public static Uri buildTagUri(long tagId) {
            return ContentUris.withAppendedId(CONTENT_URI, tagId);
        }

        public static String getTagId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class RestaurantTagEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RESTAURANT_TAGS).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESTAURANT_TAGS;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESTAURANT_TAGS;

        public static final String TABLE_NAME = "restaurant_tags";

        public static final String COLUMN_TITLE = RestaurantEntry.COLUMN_TITLE;
        public static final String COLUMN_TAG_NAME = TagEntry.COLUMN_TAG_NAME;

        public static final String REF_RESTAURANT_TITLE = "REFERENCES " +
                RestaurantEntry.TABLE_NAME + "(" +
                RestaurantEntry.COLUMN_TITLE + ")";

        public static final String REF_TAG_NAME = "REFERENCES " +
                TagEntry.TABLE_NAME + "(" +
                TagEntry.COLUMN_TAG_NAME + ")";

        public static Uri buildRestaurantTagUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }


}
