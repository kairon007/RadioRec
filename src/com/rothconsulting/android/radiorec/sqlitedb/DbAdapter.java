package com.rothconsulting.android.radiorec.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbAdapter {

	private static final String TAG = "DbAdapter";
	// Database fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_STATION_ICON = "icon";
	public static final String KEY_STATION_ICON_SMALL = "icon_small";
	public static final String KEY_STATION_NAME = "name";
	public static final String KEY_STATION_HOMEPAGE = "homepage";
	public static final String KEY_STATION_STEAM = "stream";
	public static final String KEY_STATION_WEBCAM = "webcam";
	public static final String KEY_STATION_CONTACT = "contact";
	public static final String KEY_LANGUAGE = "language";
	public static final String KEY_COUNTRY = "country";
	public static final String KEY_GENRE = "genre";
	public static final String KEY_FAVORITE = "favorite";
	public static final String T_STATION = "station";
	private final Context context;
	private SQLiteDatabase database;
	private DBHelper dbHelper;

	public DbAdapter(Context context) {
		this.context = context;
	}

	public DbAdapter open() throws SQLException {
		dbHelper = new DBHelper(context);
		Log.d(TAG, "dbHelper=" + dbHelper);
		database = dbHelper.getWritableDatabase();
		Log.d(TAG, "database=" + database);
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	/**
	 * Create a new station. If the station is successfully created return the
	 * new rowId for that note, otherwise return a -1 to indicate failure.
	 */
	public long insertStation(int icon, int iconSmall, String stationName,
			String stream, String homepage, String webcam, String contact,
			boolean favorite, String country, String language, String genre) {

		ContentValues values = new ContentValues();
		values.put(KEY_STATION_ICON, icon);
		values.put(KEY_STATION_ICON_SMALL, iconSmall);
		values.put(KEY_STATION_NAME, stationName);
		values.put(KEY_STATION_STEAM, stream);
		values.put(KEY_STATION_HOMEPAGE, homepage);
		values.put(KEY_STATION_WEBCAM, webcam);
		values.put(KEY_STATION_CONTACT, contact);
		values.put(KEY_FAVORITE, favorite);
		values.put(KEY_COUNTRY, country);
		values.put(KEY_LANGUAGE, language);
		values.put(KEY_GENRE, genre);

		return database.insert(T_STATION, null, values);
	}

	/**
	 * Deletes station
	 */
	public int deleteStation(String stationName) {
		Log.d(TAG, "delete stationName=" + stationName);
		int affected = database.delete(T_STATION, KEY_STATION_NAME + "='"
				+ stationName + "'", null);
		Log.d(TAG, "rows deleted=" + affected);

		return affected;
	}

	/**
	 * Return a Cursor over the list of all stations in the database
	 * 
	 * @return Cursor over all station
	 */
	public Cursor fetchAllStations() {
		return database.rawQuery("SELECT * FROM " + T_STATION, null);
	}

	/**
	 * Return a Cursor positioned at the defined station
	 */
	public Cursor fetchStation(String stationName) throws SQLException {
		Cursor mCursor = null;
		mCursor = database.query(true, T_STATION, new String[] {
				KEY_STATION_ICON, KEY_STATION_NAME }, KEY_STATION_NAME + "= '"
				+ stationName + "'", null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
			Log.d(TAG, "cursor.getCount()=" + mCursor.getCount());
		}
		Log.d(TAG, "cursor=" + mCursor);
		return mCursor;
	}

}
