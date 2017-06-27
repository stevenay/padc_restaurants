package com.naylinaung.padc_week3_restaurant.data.persistence;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by NayLinAung on 6/25/2017.
 * {@link android.content.ContentProvider} that stores {@link RestaurantContract} data.
 * Data is usually inserted by {@link com.naylinaung.padc_week3_restaurant.data.models.RestaurantModel}, and
 * queried using {@link android.app.LoaderManager} pattern.
 */

public class RestaurantProvider extends ContentProvider {

    private static final int RESTAURANT = 100;
    private static final int TAG = 200;
    private static final int RESTAURANT_TAG = 300;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private RestaurantDbHelper mRestaurantDbHelper;

    @Override
    public boolean onCreate() {
        mRestaurantDbHelper = new RestaurantDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor queryCursor;

        int matchUri = sUriMatcher.match(uri);
        switch (matchUri) {
            case RESTAURANT:
                queryCursor = mRestaurantDbHelper.getReadableDatabase().query(RestaurantContract.RestaurantEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case RESTAURANT_TAG:
                queryCursor = mRestaurantDbHelper.getReadableDatabase().query(RestaurantContract.RestaurantTagEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        Context context = getContext();
        if (context != null) {
            queryCursor.setNotificationUri(context.getContentResolver(), uri);
        }

        return queryCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int matchUri = sUriMatcher.match(uri);
        switch (matchUri) {
            case RESTAURANT:
                return RestaurantContract.RestaurantEntry.DIR_TYPE;
            case RESTAURANT_TAG:
                return RestaurantContract.RestaurantTagEntry.DIR_TYPE;
            case TAG:
                return RestaurantContract.TagEntry.ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // First get the Writable Database
        final SQLiteDatabase db = mRestaurantDbHelper.getWritableDatabase();
        final int matchUriCode = sUriMatcher.match(uri);
        Uri insertedUri;

        switch (matchUriCode) {
            case RESTAURANT: {
                long _id = db.insert(RestaurantContract.RestaurantEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = RestaurantContract.RestaurantEntry.buildRestaurantUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case RESTAURANT_TAG: {
                long _id = db.insert(RestaurantContract.RestaurantEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = RestaurantContract.RestaurantTagEntry.buildRestaurantTagUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertedUri;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mRestaurantDbHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        int insertedCount = 0;

        try {
            db.beginTransaction();
            for (ContentValues cv : values) {
                long _id = db.insert(tableName, null, cv);
                if (_id > 0) {
                    insertedCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertedCount;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mRestaurantDbHelper.getWritableDatabase();
        int rowDeleted;
        String tableName = getTableName(uri);

        rowDeleted = db.delete(tableName, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowDeleted > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mRestaurantDbHelper.getWritableDatabase();
        int rowUpdated;
        String tableName = getTableName(uri);

        rowUpdated = db.update(tableName, values, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowUpdated > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return rowUpdated;
    }

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(RestaurantContract.CONTENT_AUTHORITY, RestaurantContract.PATH_RESTAURANTS, RESTAURANT);
        uriMatcher.addURI(RestaurantContract.CONTENT_AUTHORITY, RestaurantContract.PATH_TAGS, TAG);
        uriMatcher.addURI(RestaurantContract.CONTENT_AUTHORITY, RestaurantContract.PATH_RESTAURANT_TAGS, RESTAURANT_TAG);

        return uriMatcher;
    }

    private String getTableName(Uri uri) {
        final int matchUriCode = sUriMatcher.match(uri);

        switch (matchUriCode) {
            case RESTAURANT:
                return RestaurantContract.RestaurantEntry.TABLE_NAME;
            case RESTAURANT_TAG:
                return RestaurantContract.RestaurantTagEntry.TABLE_NAME;
            case TAG:
                return RestaurantContract.RestaurantTagEntry.TABLE_NAME;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }
}
