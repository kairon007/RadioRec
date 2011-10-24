package com.rothconsulting.android.radiorec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.rothconsulting.android.radiorec.sqlitedb.DBHelper;
import com.rothconsulting.android.radiorec.sqlitedb.DbAdapter;

public class TabFavourites extends ListActivity {
	private DbAdapter mDbAdapter;
	private final int mTodoNumber = 1;
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;
	public static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;
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
		mDbAdapter.createFavorite(0, "Radio 32");
		fillData();
		registerForContextMenu(getListView());

		// showFavList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_ID, 0, R.string.save);
		return result;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case INSERT_ID:
			createTodo();
			return true;
		}

		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case INSERT_ID:
			createTodo();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();
			mDbAdapter.deleteFavorite(info.id);
			fillData();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	private void createTodo() {
		Intent i = new Intent(this, Tabs.class);
		startActivityForResult(i, ACTIVITY_CREATE);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		Bundle extras = intent.getExtras();

		switch (requestCode) {
		case ACTIVITY_CREATE:
			int icon = extras.getInt(DbAdapter.KEY_STATION_ICON);
			String name = extras.getString(DbAdapter.KEY_STATION_NAME);
			mDbAdapter.createFavorite(icon, name);
			fillData();
			break;
		case ACTIVITY_EDIT:
			Long mRowId = extras.getLong(DbAdapter.KEY_ROWID);
			if (mRowId != null) {
				icon = extras.getInt(DbAdapter.KEY_STATION_ICON);
				name = extras.getString(DbAdapter.KEY_STATION_NAME);
				mDbAdapter.updateFavorite(0, icon, name);
			}
			fillData();
			break;
		}

	}

	private void fillData() {
		cursor = mDbAdapter.fetchAllFavorites();
		startManagingCursor(cursor);

		String[] from = new String[] { DbAdapter.KEY_STATION_NAME };
		int[] to = new int[] { R.id.category };

		// Now create an array adapter and set it to display using our row
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
				R.layout.categories_listitem, cursor, from, to);
		setListAdapter(notes);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.reset);
	}

	private void showFavList() {
		List<HashMap<String, Object>> stationList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("icon_small", 0);
		m.put("name", "Radio 32");
		stationList.add(m);
		m.put("icon_small", 1);
		m.put("name", "Radio 30");
		stationList.add(m);
		m.put("icon_small", 2);
		m.put("name", "Radio 33");
		stationList.add(m);

		Utils utils = new Utils();
		// m = utils.sortValuesInHashMap(m);
		stationList.add(m);
		// apply list to spinner
		SimpleAdapter adapter = new SimpleAdapter(this, stationList,
				R.layout.station_listitem,
				new String[] { "icon_small", "name" }, new int[] {
						R.id.option_icon, R.id.option_text });

		Spinner stations = new Spinner(this);
		stations.setPrompt("Favoriten");
		stations.setAdapter(adapter);
		stations.pointToPosition(1, 1);
		stations.performClick();

	}
}
