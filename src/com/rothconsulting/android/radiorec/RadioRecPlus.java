package com.rothconsulting.android.radiorec;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class RadioRecPlus extends Activity implements OnClickListener,
		OnItemSelectedListener {

	static final String TAG = "RadioRecPlus";

	// Default Constants
	protected static String SELECTED_STATION = Constants.RADIO_32;
	protected static String URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_32;
	protected static String URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_32;
	protected static String URL_WEBCAM = Constants.URL_WEBCAM_RADIO_32;
	protected static String URL_CONTACT = Constants.URL_CONTACT_RADIO_32;
	protected static String URL_SONGTICKER = "";

	boolean playing, recording, firstStart;
	Spinner stations;
	ArrayList<HashMap<String, Object>> list;
	ImageView logo;
	ImageButton back, fwd;
	RadioPlayer radioPlayer;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		firstStart = true;
		// get components and register clicks
		stations = (Spinner) findViewById(R.id.stations);
		logo = (ImageView) findViewById(R.id.logo);
		((TextView) findViewById(R.id.homepage)).setOnClickListener(this);
		((TextView) findViewById(R.id.webcam)).setOnClickListener(this);
		((TextView) findViewById(R.id.mail)).setOnClickListener(this);
		back = ((ImageButton) findViewById(R.id.back));
		back.setOnClickListener(this);
		back.setEnabled(false);
		((ImageButton) findViewById(R.id.play)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.rec)).setOnClickListener(this);
		fwd = ((ImageButton) findViewById(R.id.fwd));
		fwd.setOnClickListener(this);
		// set first image
		logo.setImageBitmap(Images.addReflection(BitmapFactory.decodeResource(
				getResources(), R.drawable.radio_32), 0));
		// construct list of maps for the spinner (DropDown-Selector)
		list = new ArrayList<HashMap<String, Object>>();
		// Get all the sources: name, logo, stream, homepage, webcam, mail.
		String[] names = getResources().getStringArray(R.array.station_names);
		TypedArray logos = getResources().obtainTypedArray(
				R.array.station_logos);
		String[] streams = getResources().getStringArray(
				R.array.station_streams);

		for (int i = 0; i < names.length; i++) {
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("name", names[i]);
			m.put("icon", logos.getResourceId(i, R.id.logo));
			m.put("stream", streams[i]);
			m.put("homepage", "");
			m.put("webcam", "");
			m.put("mail", "");
			list.add(m);
		}
		// apply list to spinner
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.listitem, new String[] { "icon", "name" }, new int[] {
						R.id.option_icon, R.id.option_text });
		stations.setAdapter(adapter);
		stations.setOnItemSelectedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, -1, 0, "Info").setIcon(R.drawable.ic_menu_info_details);
		menu.add(0, -2, 0, "Beenden").setIcon(R.drawable.ic_menu_exit);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case -1:
			Log.i(TAG, "info");
			this.startActivity(new Intent(this, Info.class));
			break;
		case -2:
			Log.i(TAG, "exit");
			getRadioPlayer().doStopPlay(this);
			// doStopRecording();
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClick(View v) {
		firstStart = false;

		switch (v.getId()) {
		case R.id.homepage:
			Log.i(TAG, "homepage");
			Intent intentHomepage = new Intent(Intent.ACTION_VIEW);
			if (URL_HOMEPAGE != null && !URL_HOMEPAGE.equals("")) {
				intentHomepage.setData(Uri.parse(URL_HOMEPAGE));
				startActivity(intentHomepage);
			}
			break;
		case R.id.webcam:
			Log.i(TAG, "webcam");
			Intent intentCam = new Intent(this, Webcam.class);
			startActivity(intentCam);
			break;
		case R.id.mail:
			Log.i(TAG, "mail");
			if (URL_CONTACT.startsWith("http")) {
				Intent emailIntent = new Intent(Intent.ACTION_VIEW);
				emailIntent.setData(Uri.parse(URL_CONTACT));
				startActivity(emailIntent);
			} else {
				Intent emailIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
						new String[] { URL_CONTACT });
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Mein Wunsch");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
				startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			}
			break;
		case R.id.back:
			if (stations.getSelectedItemPosition() > 0) {
				stations.setSelection(stations.getSelectedItemPosition() - 1);
				Log.d(TAG, "back");
				changeStation();
			}
			break;
		case R.id.play:
			playing = !playing;
			((ImageButton) v).setImageResource(playing ? R.drawable.stop
					: R.drawable.play);
			changeStation();
			break;
		case R.id.rec:
			recording = !recording;
			((ImageButton) v).setImageResource(recording ? R.drawable.record_on
					: R.drawable.record);
			break;
		case R.id.fwd:
			if (stations.getSelectedItemPosition() < list.size() - 1) {
				stations.setSelection(stations.getSelectedItemPosition() + 1);
				Log.d(TAG, "fwd");
				onItemSelected(null, null, stations.getSelectedItemPosition(),
						stations.getSelectedItemPosition());
				// changeStation();
			}
			break;
		}
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Log.d(TAG, "onItemSelected(" + arg0 + ", " + arg1 + ", " + arg2 + ", "
				+ arg3 + ")");
		changeStation();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
	}

	private void changeStation() {
		int index = stations.getSelectedItemPosition();
		HashMap<String, Object> map = list.get(index);
		logo.setImageBitmap(Images.addReflection(
				BitmapFactory.decodeResource(getResources(),
						(Integer) map.get("icon")), 0));
		Log.d(TAG, "*********** Stream=" + map.get("stream"));
		SELECTED_STATION = "" + map.get("name");
		URL_LIVE_STREAM = "" + map.get("stream");
		if (!firstStart && playing) {
			getRadioPlayer().doStartPlay(this);
		} else {
			getRadioPlayer().doStopPlay(this);
		}
		back.setEnabled(index > 0);
		fwd.setEnabled(index < list.size() - 1);
		storePreferences();
	}

	private void storePreferences() {
		SharedPreferences settings = getSharedPreferences(
				Constants.PREFERENCES_FILE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Constants.SELECTED_STATION, SELECTED_STATION);
		editor.putString(Constants.SELECTED_STATION_STREAM, URL_LIVE_STREAM);
		editor.putString(Constants.SELECTED_STATION_HOMEPAGE, URL_HOMEPAGE);
		editor.putString(Constants.SELECTED_STATION_WEBCAM, URL_WEBCAM);
		editor.putString(Constants.SELECTED_STATION_CONTACT, URL_CONTACT);
		editor.commit();
	}

	private RadioPlayer getRadioPlayer() {
		if (radioPlayer == null) {
			radioPlayer = new RadioPlayer();
		}
		return radioPlayer;
	}
}