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
	public static final String KEY_STATION_ICON = "station_icon";
	public static final String KEY_STATION_NAME = "station_name";
	public static final String KEY_STATION_URL = "station_url";
	public static final String KEY_CATEGORY = "category";
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
	 * Create a new category. If the category is successfully created return the
	 * new rowId for that note, otherwise return a -1 to indicate failure.
	 */
	public long createCategory(int stationIcon, String stationName) {
		ContentValues initialValues = createContentValues(stationIcon,
				stationName);

		return database.insert(T_STATION, null, initialValues);
	}

	/**
	 * Update the category
	 */
	public boolean updateCategory(long rowId, int stationIcon,
			String stationName) {
		ContentValues updateValues = createContentValues(stationIcon,
				stationName);

		return database.update(T_STATION, updateValues,
				KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Deletes category
	 */
	public boolean deleteCategory(String stationName) {
		return database.delete(T_STATION, KEY_STATION_NAME + "=" + stationName,
				null) > 0;
	}

	/**
	 * Return a Cursor over the list of all categories in the database
	 * 
	 * @return Cursor over all category
	 */
	public Cursor fetchAllCategorys() {
		return database.query(T_STATION, new String[] { KEY_ROWID,
				KEY_STATION_ICON, KEY_STATION_NAME }, null, null, null, null,
				null);
	}

	/**
	 * Return a Cursor positioned at the defined category
	 */
	public Cursor fetchCategory(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, T_STATION, new String[] {
				KEY_ROWID, KEY_STATION_ICON, KEY_STATION_NAME }, KEY_ROWID
				+ "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(int stationIcon,
			String stationName) {
		ContentValues values = new ContentValues();
		values.put(KEY_STATION_ICON, stationIcon);
		values.put(KEY_STATION_NAME, stationName);
		return values;
	}
}
