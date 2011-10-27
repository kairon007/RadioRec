package com.rothconsulting.android.radiorec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.rothconsulting.android.radiorec.sqlitedb.DBHelper;
import com.rothconsulting.android.radiorec.sqlitedb.DbAdapter;

public class TabCategories extends ListActivity {
	private DbAdapter mDbAdapter;
	private static final int DELETE_ID = 1;
	private Cursor cursor;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getApplicationContext().deleteDatabase(DBHelper.DATABASE_NAME);
		getApplicationContext().deleteDatabase("RadioRecPlus");

		setContentView(R.layout.categories_list);
		mDbAdapter = new DbAdapter(this);
		mDbAdapter.open();
		mDbAdapter.insertStation(0, "Meine Favoriten");
		mDbAdapter.insertStation(1, "Alphabetisch A-Z");
		mDbAdapter.insertStation(2, "Alphabetisch Z-A");
		mDbAdapter.insertStation(4, "Land: Schweiz");
		mDbAdapter.insertStation(5, "Land: Deutschland");
		mDbAdapter.insertStation(6, "Land: Österreich");
		mDbAdapter.insertStation(7, "Sprache: Deutsch");
		mDbAdapter.insertStation(8, "Sprache: Französisch");
		mDbAdapter.insertStation(9, "Sprache: Englisch");
		mDbAdapter.insertStation(10, "Musikstil: Pop");
		mDbAdapter.insertStation(11, "Musikstil: Rock");
		mDbAdapter.insertStation(12, "Musikstil: Swiss");
		mDbAdapter.insertStation(13, "Musikstil: Chillout");
		mDbAdapter.insertStation(14, "Musikstil: Volkstümlich");
		fillData();

		// showFavList();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor c = cursor;
		c.moveToPosition(position);
		int icon = c
				.getInt(c.getColumnIndexOrThrow(DbAdapter.KEY_STATION_ICON));
		String name = c.getString(c
				.getColumnIndexOrThrow(DbAdapter.KEY_STATION_NAME));

		showFavList();
		// Intent i = new Intent(this, Tabs.class);
		// i.putExtra(DbAdapter.KEY_ROWID, id);
		// i.putExtra(DbAdapter.KEY_STATION_ICON, icon);
		// i.putExtra(DbAdapter.KEY_STATION_NAME, name);
		// Constants.THE_SELECTED_STATION_ICON = icon;
		// Constants.THE_SELECTED_STATION_NAME = name;
		// startActivityForResult(i, ACTIVITY_EDIT);
	}

	private void fillData() {
		cursor = mDbAdapter.fetchAllStations();
		startManagingCursor(cursor);

		String[] from = new String[] { DbAdapter.KEY_STATION_NAME };
		int[] to = new int[] { R.id.category };

		// Now create an array adapter and set it to display using our row
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
				R.layout.categories_listitem, cursor, from, to);
		setListAdapter(notes);
	}

	private void showFavList() {
		List<HashMap<String, Object>> stationList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("icon_small", R.drawable.radio_32_small);
		m.put("name", "Radio 32");
		stationList.add(m);
		m = new HashMap<String, Object>();
		m.put("icon_small", R.drawable.radio_32_goldies_small);
		m.put("name", "Radio 32 Goldies");
		stationList.add(m);
		m = new HashMap<String, Object>();
		m.put("icon_small", 2);
		m.put("name", "Radio 33");
		stationList.add(m);

		SimpleAdapter adapter = new SimpleAdapter(this, stationList,
				R.layout.station_listitem,
				new String[] { "icon_small", "name" }, new int[] {
						R.id.option_icon, R.id.option_text });

		Spinner stations = new Spinner(this);
		stations.setPrompt("Favoriten");
		stations.setAdapter(adapter);
		stations.performClick();
	}
}
