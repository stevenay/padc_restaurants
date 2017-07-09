package com.naylinaung.padc_week3_restaurant.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.naylinaung.padc_week3_restaurant.data.persistence.RestaurantsContract.RestaurantsEntry;
import com.naylinaung.padc_week3_restaurant.data.persistence.RestaurantsContract.TagsEntry;
import com.naylinaung.padc_week3_restaurant.data.persistence.RestaurantsContract.TagsColumns;

/**
 * Created by NayLinAung on 6/25/2017.
 */

public class RestaurantDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "restaurants.db";

    private static final String SQL_CREATE_RESTAURANT_TABLE =
            "CREATE TABLE " + RestaurantsEntry.TABLE_NAME + " (" +
            RestaurantsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            RestaurantsEntry.COLUMN_TITLE + " TEXT NOT NULL," +
            RestaurantsEntry.COLUMN_ADDRESS + " TEXT," +
            RestaurantsEntry.COLUMN_IMAGE + " TEXT NOT NULL," +
            RestaurantsEntry.COLUMN_TOTAL_RATING_COUNT + " INTEGER NOT NULL, " +
            RestaurantsEntry.COLUMN_AVG_RATING_COUNT + " REAL NOT NULL, " +
            RestaurantsEntry.COLUMN_IS_AD + " INTEGER NOT NULL, " +
            RestaurantsEntry.COLUMN_IS_NEW + " INTEGER NOT NULL, " +
            RestaurantsEntry.COLUMN_LEAD_TIME + " INTEGER NOT NULL, " +

            " UNIQUE (" + RestaurantsEntry.COLUMN_TITLE + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_TAG_TABLE =
            "CREATE TABLE " + TagsEntry.TABLE_NAME + " (" +
            TagsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TagsColumns.COLUMN_RESTAURANT_TITLE + " TEXT NOT NULL," +
            TagsColumns.COLUMN_TAG_NAME + " TEXT NOT NULL," +

            " UNIQUE (" + TagsColumns.COLUMN_TAG_NAME + ", " +
            TagsColumns.COLUMN_RESTAURANT_TITLE + ") ON CONFLICT IGNORE" +
            " );";

   /* private static final String SQL_CREATE_RESTAURANT_TAG_TABLE =
            "CREATE TABLE " + RestaurantTagEntry.TABLE_NAME + " (" +
            RestaurantTagEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            RestaurantTagEntry.COLUMN_TITLE + " TEXT NOT NULL " + RestaurantTagEntry.REFERENCES_RESTAURANT_TITLE + "," +
            RestaurantTagEntry.COLUMN_TAG_NAME + " TEXT NOT NULL " + RestaurantTagEntry.REFERENCES_TAG_NAME + "," +

            " UNIQUE (" + RestaurantTagEntry.COLUMN_TITLE + "," +
                    RestaurantTagEntry.COLUMN_TAG_NAME + ") ON CONFLICT REPLACE)";*/



    public RestaurantDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RESTAURANT_TABLE);
        db.execSQL(SQL_CREATE_TAG_TABLE);
    }

    /* will execute when the databsae version is upgraded */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RestaurantsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TagsEntry.TABLE_NAME);

        onCreate(db);
    }
}
