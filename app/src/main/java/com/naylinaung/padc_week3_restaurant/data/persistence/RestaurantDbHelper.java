package com.naylinaung.padc_week3_restaurant.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.naylinaung.padc_week3_restaurant.data.persistence.RestaurantContract.RestaurantEntry;
import com.naylinaung.padc_week3_restaurant.data.persistence.RestaurantContract.RestaurantTagEntry;
import com.naylinaung.padc_week3_restaurant.data.persistence.RestaurantContract.TagEntry;
import com.naylinaung.padc_week3_restaurant.data.persistence.RestaurantContract.TagsColumns;

/**
 * Created by NayLinAung on 6/25/2017.
 */

public class RestaurantDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "restaurants.db";

    private static final String SQL_CREATE_RESTAURANT_TABLE = "CREATE TABLE " + RestaurantEntry.TABLE_NAME + " (" +
            RestaurantEntry._ID + " Integer Primary Key AutoIncrement, " +
            RestaurantEntry.COLUMN_TITLE + " Text Not Null, " +
            RestaurantEntry.COLUMN_ADDRESS + " Text Not Null, " +
            RestaurantEntry.COLUMN_IMAGE + " Text Not Null, " +
            RestaurantEntry.COLUMN_TOTAL_RATING_COUNT + " Integer Not Null, " +
            RestaurantEntry.COLUMN_AVG_RATING_COUNT + " Integer Not Null, " +
            RestaurantEntry.COLUMN_IS_AD + " Integer Not Null, " +
            RestaurantEntry.COLUMN_IS_NEW + " Integer Not Null, " +
            RestaurantEntry.COLUMN_LEAD_TIME + " Integer Not Null, " +

            " UNIQUE (" + RestaurantEntry.COLUMN_TITLE + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_TAG_TABLE = "CREATE TABLE " + TagEntry.TABLE_NAME + " (" +
            TagEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TagsColumns.COLUMN_TAG_NAME + " TEXT NOT NULL, " +

            " UNIQUE (" + TagsColumns.COLUMN_TAG_NAME + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_RESTAURANT_TAG_TABLE = "CREATE TABLE " + RestaurantTagEntry.TABLE_NAME + " (" +
            RestaurantTagEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            RestaurantTagEntry.COLUMN_TITLE + " TEXT NOT NULL " + RestaurantTagEntry.REF_RESTAURANT_TITLE + "," +
            RestaurantTagEntry.COLUMN_TAG_NAME + " TEXT NOT NULL " + RestaurantTagEntry.REF_TAG_NAME + "," +

            " UNIQUE (" + RestaurantEntry.COLUMN_TITLE + "," +
            TagEntry.COLUMN_TAG_NAME + ") ON CONFLICT REPLACE)";



    public RestaurantDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RESTAURANT_TABLE);
        db.execSQL(SQL_CREATE_TAG_TABLE);
        db.execSQL(SQL_CREATE_RESTAURANT_TAG_TABLE);
    }

    /* will execute when the databsae version is upgraded */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RestaurantEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TagEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RestaurantTagEntry.TABLE_NAME);

        onCreate(db);
    }
}
