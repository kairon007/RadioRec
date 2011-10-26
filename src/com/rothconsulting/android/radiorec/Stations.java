package com.rothconsulting.android.radiorec;

import java.util.ArrayList;
import java.util.HashMap;

public class Stations {

	private HashMap<String, Object> m;
	private ArrayList<HashMap<String, Object>> stationList;

	// 0 Name
	// 1 Icon (int)
	// 2 Stream URL
	// 3 Webcam URL
	// 4 Contact
	// 5 Sprache
	// 6 Land
	// 7 Stil

	public ArrayList<HashMap<String, Object>> getAllStations() {
		stationList = new ArrayList<HashMap<String, Object>>();

		m = new HashMap<String, Object>();
		m.put("name", "Radio 32");
		m.put("icon_small", R.drawable.radio_32);
		m.put("icon", R.drawable.radio_32_small);
		m.put("stream", "");
		m.put("homepage", "http://www.radio32.ch");
		m.put("webcam", "");
		m.put("email", "");
		m.put("sprache", "");
		m.put("land", "");
		m.put("stil", "");
		stationList.add(m);

		return stationList;
	}

}
