package com.rothconsulting.android.radiorec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.rothconsulting.android.radiorec.sqlitedb.DbAdapter;

public class TabFavourites extends ListActivity {
	private static final String TAG = "TabFavourites";

	@Override
	public void onCreate(Bundle bundle) {
		Utils.log(TAG, "*********** TabFavourites onCreate ***************");
		super.onCreate(bundle);

		// getApplicationContext().deleteDatabase(DBHelper.DATABASE_NAME);

		List<HashMap<String, Object>> stationList = new ArrayList<HashMap<String, Object>>();
		DbAdapter dbAdapter = new DbAdapter(this);
		dbAdapter.open();
		Cursor cursor = dbAdapter.fetchAllStations();
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			int colIcon = cursor
					.getColumnIndex(DbAdapter.KEY_STATION_ICON_SMALL);
			int colName = cursor.getColumnIndex(DbAdapter.KEY_STATION_NAME);
			for (int i = 0; i < cursor.getCount(); i++) {
				HashMap<String, Object> m = new HashMap<String, Object>();
				m.put("name", cursor.getString(colName));
				m.put("icon_small", cursor.getInt(colIcon));
				stationList.add(m);
				cursor.moveToNext();
			}
		} else {
			Toast.makeText(
					this,
					"Noch keine Favoriten vorhanden.\nDrücke Stern neben den Sendern.",
					Toast.LENGTH_LONG).show();
		}
		cursor.close();
		dbAdapter.close();

		SimpleAdapter adapter = new SimpleAdapter(this, stationList,
				R.layout.favourites_listitem, new String[] { "icon_small",
						"name" }, new int[] { R.id.option_icon,
						R.id.option_text });

		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		Toast.makeText(this, "You selected: " + keyword, Toast.LENGTH_SHORT)
				.show();
		Intent i = new Intent(this, RadioRecPlus.class);
		startActivityForResult(i, Constants.INTENT_REQUEST_FROM_FAV);
	}
}
