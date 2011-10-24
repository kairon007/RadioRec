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
	public static final String T_FAVORITES = "favorites";
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
	 * Create a new favorite. If the favorite is successfully created return the
	 * new rowId for that note, otherwise return a -1 to indicate failure.
	 */
	public long createFavorite(int stationIcon, String stationName) {
		ContentValues initialValues = createContentValues(stationIcon,
				stationName);

		return database.insert(T_FAVORITES, null, initialValues);
	}

	/**
	 * Update the favorite
	 */
	public boolean updateFavorite(long rowId, int stationIcon,
			String stationName) {
		ContentValues updateValues = createContentValues(stationIcon,
				stationName);

		return database.update(T_FAVORITES, updateValues, KEY_ROWID + "="
				+ rowId, null) > 0;
	}

	/**
	 * Deletes favorite
	 */
	public boolean deleteFavorite(long rowId) {
		return database.delete(T_FAVORITES, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all favorites in the database
	 * 
	 * @return Cursor over all favorite
	 */
	public Cursor fetchAllFavorites() {
		return database.query(T_FAVORITES, new String[] { KEY_ROWID,
				KEY_STATION_ICON, KEY_STATION_NAME }, null, null, null, null,
				null);
	}

	/**
	 * Return a Cursor positioned at the defined todo
	 */
	public Cursor fetchFavorite(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, T_FAVORITES, new String[] {
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
