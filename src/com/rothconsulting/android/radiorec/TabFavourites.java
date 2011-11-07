package com.rothconsulting.android.radiorec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class TabFavourites extends ListActivity {

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		List<HashMap<String, Object>> stationList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("name", "Radio 32");
		m.put("icon_small", R.drawable.radio_32_small);
		stationList.add(m);
		m = new HashMap<String, Object>();
		m.put("name", "Radio 32 Goldies");
		m.put("icon_small", R.drawable.radio_32_goldies_small);
		stationList.add(m);

		SimpleAdapter adapter = new SimpleAdapter(this, stationList,
				R.layout.station_listitem,
				new String[] { "icon_small", "name" }, new int[] {
						R.id.option_icon, R.id.option_text });

		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		Toast.makeText(this, "You selected: " + keyword, Toast.LENGTH_LONG)
				.show();

	}
}
