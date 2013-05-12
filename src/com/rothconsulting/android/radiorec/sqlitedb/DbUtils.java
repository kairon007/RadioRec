package com.rothconsulting.android.radiorec.sqlitedb;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.widget.TextView;

import com.rothconsulting.android.radiorec.Constants;
import com.rothconsulting.android.radiorec.Stations;
import com.rothconsulting.android.radiorec.Utils;

public class DbUtils {

	private static final String TAG = "DbUtil";

	public static void addFavourite(Context context, String stationName) {
		Utils.log(TAG, "addFavourite START");

		DbAdapter dbadapter = new DbAdapter(context);

		dbadapter.open();
		Cursor cursor = dbadapter.fetchStation(stationName);

		if (cursor == null || cursor.getCount() == 0) {
			// insert new
			Utils.log(TAG, "instertStation: " + stationName);
			dbadapter.insertStation(stationName);
		}
		dbadapter.close();

		Utils.log(TAG, "addFavourite STOP");
	}

	public static void removeFavourite(Context context, String stationName) {
		Utils.log(TAG, "removeFavourite START");

		DbAdapter dbadapter = new DbAdapter(context);

		dbadapter.open();
		Cursor cursor = dbadapter.fetchStation(stationName);

		if (cursor != null && cursor.getCount() > 0) {
			// delete
			Utils.log(TAG, "deleteStation: " + stationName);
			dbadapter.deleteStation(stationName);
		}
		dbadapter.close();
		Utils.log(TAG, "removeFavourite STOP");
	}

	public static ArrayList<HashMap<String, Object>> getFavListFromDb(Context context) {
		Utils.log(TAG, "fillFavSpinner START");

		DbAdapter dbadapter = new DbAdapter(context);
		dbadapter.open();
		Cursor cursor = null;
		cursor = dbadapter.fetchAllStations();
		ArrayList<HashMap<String, Object>> favList = new ArrayList<HashMap<String, Object>>();

		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				String name = cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_STATION_NAME));
				Utils.log(TAG, "get Name: " + name);
				HashMap<String, Object> map = Utils.getFullStation(Stations.getAllStations(), name);
				favList.add(map);
			} while (cursor.moveToNext());

		}
		cursor.close();
		dbadapter.close();

		Utils.log(TAG, "fillFavSpinner STOP");

		return favList;
	}

	public static void storeRemoveFav(Context context, TextView favIcon) {
		Utils.log(TAG, "storeRemoveFav START");

		DbAdapter dbadapter = new DbAdapter(context);

		if (favIcon.getTag().equals(Constants.FAV_OFF)) {
			favIcon.setTag(Constants.FAV_ON);
			favIcon.setCompoundDrawablesWithIntrinsicBounds(0, android.R.drawable.star_big_on, 0, 0);
			Utils.log(TAG, "favOn -> instertStation: " + Constants.SELECTED_STATION_NAME_VALUE);
			dbadapter.open();
			dbadapter.insertStation(Constants.SELECTED_STATION_NAME_VALUE);
			dbadapter.close();
		} else {
			favIcon.setTag(Constants.FAV_OFF);
			favIcon.setCompoundDrawablesWithIntrinsicBounds(0, android.R.drawable.star_big_off, 0, 0);
			Utils.log(TAG, "favOff -> deleteStation: " + Constants.SELECTED_STATION_NAME_VALUE);
			dbadapter.open();
			dbadapter.deleteStation(Constants.SELECTED_STATION_NAME_VALUE);
			dbadapter.close();
		}
		Utils.log(TAG, "storeRemoveFav STOP");
	}

}
