package com.rothconsulting.android.radiorec;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
	protected static int SELECTED_STATION_INDEX;
	protected static String SELECTED_STATION_NAME;
	protected static String URL_LIVE_STREAM;
	protected static String URL_HOMEPAGE;
	protected static String URL_WEBCAM;
	protected static String URL_CONTACT;
	protected static String URL_SONGTICKER;

	boolean playing, recording, firstStart;
	Spinner stations;
	ArrayList<HashMap<String, Object>> stationList;
	ImageView logo;
	ImageButton back, fwd;
	RadioPlayer radioPlayer;
	RadioRecorder radioRecorder;
	AsyncTask<URL, Integer, Long> recordTask;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// if (!playing) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		firstStart = true;
		getPreferences();
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
		// logo.setImageBitmap(Images.addReflection(BitmapFactory.decodeResource(
		// getResources(), R.drawable.radio_32), 0));
		if (SELECTED_STATION_INDEX == -1) {
			SELECTED_STATION_INDEX = R.drawable.radio_32;
		}
		Log.d(TAG, "Image=" + SELECTED_STATION_NAME);
		Log.d(TAG, "Index=" + SELECTED_STATION_INDEX);
		logo.setImageBitmap(Images.addReflection(BitmapFactory.decodeResource(
				getResources(), SELECTED_STATION_INDEX), 0));
		// construct list of maps for the spinner (DropDown-Selector)
		stationList = new ArrayList<HashMap<String, Object>>();
		// Get all the sources: name, logo, stream, homepage, webcam, mail.
		String[] names = getResources().getStringArray(R.array.station_names);
		TypedArray logos = getResources().obtainTypedArray(
				R.array.station_logos);
		String[] streams = getResources().getStringArray(
				R.array.station_streams);
		String[] homepages = getResources().getStringArray(
				R.array.station_homepages);
		String[] webcams = getResources().getStringArray(
				R.array.station_webcams);
		String[] contact = getResources().getStringArray(
				R.array.station_contact);

		for (int i = 0; i < names.length; i++) {
			// Shoutcast Streams gehen erst ab Android 2.2 (Level 8)
			if (Build.VERSION.SDK_INT < 8
					&& Constants.getIgnoreListKleinerAndroid22().contains(
							names[i])) {
				continue;
			}
			if (Build.VERSION.SDK_INT == 8
					&& Constants.getIgnoreListGleichAndroid22().contains(
							names[i])) {
				continue;
			}
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("name", names[i]);
			m.put("icon", logos.getResourceId(i, R.id.logo));
			m.put("stream", streams[i]);
			m.put("homepage", homepages[i]);
			m.put("webcam", webcams[i]);
			m.put("email", contact[i]);
			stationList.add(m);

			// apply list to spinner
			SimpleAdapter adapter = new SimpleAdapter(this, stationList,
					R.layout.listitem, new String[] { "icon", "name" },
					new int[] { R.id.option_icon, R.id.option_text });
			stations.setAdapter(adapter);
			stations.setOnItemSelectedListener(this);
			// }
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, -1, 0, "Info").setIcon(R.drawable.ic_menu_info_details);
		menu.add(0, -2, 0, "Beenden").setIcon(R.drawable.ic_menu_exit);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (playing) {
				showDialog(Constants.PRESS_BACK_BUTTON);
				return true;
			} else {
				getRadioPlayer().doStopPlay(this);
				doStopRecording();
				finish();
			}
		}
		Utils.getNotifInstance(this, RadioRecPlus.class)
				.hideStatusBarNotification(
						Constants.NOTIFICATION_ID_ERROR_CONNECTION);
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Constants.PRESS_BACK_BUTTON:
			final AlertDialog.Builder closeWindowBuilder = new AlertDialog.Builder(
					this);
			closeWindowBuilder
					.setMessage(
							this.getResources().getString(
									R.string.willstDuWeiterHoeren))
					.setCancelable(true)
					.setPositiveButton(
							this.getResources().getString(R.string.wegDamit),
							new DialogInterface.OnClickListener() {
								public void onClick(
										final DialogInterface dialog,
										final int id) {
									getRadioPlayer().doStopPlay(
											getApplicationContext());
									doStopRecording();
									finish();
								}
							})
					.setNegativeButton(
							this.getResources()
									.getString(R.string.weiterHoeren),
							new DialogInterface.OnClickListener() {
								public void onClick(
										final DialogInterface dialog,
										final int id) {
									moveTaskToBack(true);
									// Move the task containing this activity to
									// the back of the activity stack. The
									// activity�s order within the task is
									// unchanged.
								}
							});
			return closeWindowBuilder.create();
		}
		return null;
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
			doStopRecording();
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
				back.setEnabled(false);
				stations.setSelection(stations.getSelectedItemPosition() - 1);
				SELECTED_STATION_INDEX = stations.getSelectedItemPosition();
				Log.d(TAG, "back");
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
			if (recording) {
				doStartRecording();
			}
			((ImageButton) v).setImageResource(recording ? R.drawable.record_on
					: R.drawable.record);
			if (!recording) {
				doStopRecording();
			}
			break;
		case R.id.fwd:
			if (stations.getSelectedItemPosition() < stationList.size() - 1) {
				fwd.setEnabled(false);
				stations.setSelection(stations.getSelectedItemPosition() + 1);
				SELECTED_STATION_INDEX = stations.getSelectedItemPosition();
				Log.d(TAG, "fwd");
			}
			break;
		}
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Log.d(TAG, "onItemSelected(" + arg0 + ", " + arg1 + ", " + arg2 + ", "
				+ arg3 + ")");
		Log.d(TAG, "getSelectedItemPosition1=" + arg0.getSelectedItemPosition());
		Log.d(TAG, "SELECTED_STATION_INDEX1=" + SELECTED_STATION_INDEX);
		if (arg0.getSelectedItemPosition() != SELECTED_STATION_INDEX) {
			SELECTED_STATION_INDEX = arg0.getSelectedItemPosition();
		}
		arg0.setSelection(SELECTED_STATION_INDEX);
		Log.d(TAG, "getSelectedItemPosition2=" + arg0.getSelectedItemPosition());
		Log.d(TAG, "SELECTED_STATION_INDEX2=" + SELECTED_STATION_INDEX);

		changeStation();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
	}

	private void changeStation() {
		// int index = stations.getSelectedItemPosition();
		int index = SELECTED_STATION_INDEX;
		HashMap<String, Object> map = stationList.get(index);
		Log.d(TAG, "Image=" + SELECTED_STATION_NAME);
		logo.setImageBitmap(Images.addReflection(
				BitmapFactory.decodeResource(getResources(),
						(Integer) map.get("icon")), 0));
		Log.d(TAG, "*********** Stream=" + map.get("stream"));
		SELECTED_STATION_NAME = "" + map.get("name");
		URL_LIVE_STREAM = "" + map.get("stream");
		URL_HOMEPAGE = "" + map.get("homepage");
		URL_WEBCAM = "" + map.get("webcam");
		final TextView textViewWebcam = (TextView) findViewById(R.id.webcam);
		if (URL_WEBCAM == null || URL_WEBCAM.trim().equals("")) {
			textViewWebcam.setVisibility(View.INVISIBLE);
		} else {
			textViewWebcam.setVisibility(View.VISIBLE);
		}
		URL_CONTACT = "" + map.get("email");
		if (!firstStart && playing) {
			getRadioPlayer().doStartPlay(this);
		} else {
			getRadioPlayer().doStopPlay(this);
		}
		back.setEnabled(index > 0);
		fwd.setEnabled(index < stationList.size() - 1);
		storePreferences();
	}

	private void storePreferences() {
		SharedPreferences settings = getSharedPreferences(
				Constants.PREFERENCES_FILE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(Constants.SELECTED_STATION_INDEX, SELECTED_STATION_INDEX);
		editor.putString(Constants.SELECTED_STATION_NAME, SELECTED_STATION_NAME);
		editor.putString(Constants.SELECTED_STATION_STREAM, URL_LIVE_STREAM);
		editor.putString(Constants.SELECTED_STATION_HOMEPAGE, URL_HOMEPAGE);
		editor.putString(Constants.SELECTED_STATION_WEBCAM, URL_WEBCAM);
		editor.putString(Constants.SELECTED_STATION_CONTACT, URL_CONTACT);
		editor.commit();
	}

	private void getPreferences() {
		// Restore preferences
		SharedPreferences settings = getSharedPreferences(
				Constants.PREFERENCES_FILE, 0);
		Log.d(TAG, "SELECTED_STATION_NAME vorher: " + SELECTED_STATION_NAME);
		Log.d(TAG, "SELECTED_STATION_INDEX vorher: " + SELECTED_STATION_INDEX);
		SELECTED_STATION_INDEX = settings.getInt(
				Constants.SELECTED_STATION_INDEX, -1);
		SELECTED_STATION_NAME = settings.getString(
				Constants.SELECTED_STATION_NAME, SELECTED_STATION_NAME);
		URL_LIVE_STREAM = settings.getString(Constants.SELECTED_STATION_STREAM,
				URL_LIVE_STREAM);
		URL_HOMEPAGE = settings.getString(Constants.SELECTED_STATION_HOMEPAGE,
				URL_HOMEPAGE);
		URL_WEBCAM = settings.getString(Constants.SELECTED_STATION_WEBCAM,
				URL_WEBCAM);
		URL_CONTACT = settings.getString(Constants.SELECTED_STATION_CONTACT,
				URL_CONTACT);
		Log.d(TAG, "SELECTED_STATION_NAME nachher: " + SELECTED_STATION_NAME);
		Log.d(TAG, "SELECTED_STATION_INDEX nachher: " + SELECTED_STATION_INDEX);
	}

	private RadioPlayer getRadioPlayer() {
		if (radioPlayer == null) {
			radioPlayer = new RadioPlayer();
		}
		return radioPlayer;
	}

	private boolean doStartRecording() {
		Log.d(TAG, "startRecording");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String dateTime = formatter.format(new Date());

		URL inputUrl = null;
		URL outputUrl = null;

		try {
			inputUrl = new URL(URL_LIVE_STREAM);
		} catch (MalformedURLException e) {
			Utils.getNotifInstance(this, RadioRecPlus.class)
					.showStatusBarNotificationError(
							R.string.internetadresseNichtErreichbar);
		}

		try {
			outputUrl = new URL("file:///sdcard/RadioRecorder/"
					+ SELECTED_STATION_NAME.replaceAll(" ", "") + "-"
					+ dateTime + ".mp3");
		} catch (MalformedURLException e) {
			Utils.getNotifInstance(this, RadioRecPlus.class)
					.showStatusBarNotificationError(
							R.string.kannNichtAufSdCardSchreiben);
		}

		Utils.getNotifInstance(this, RadioRecPlus.class)
				.showStatusBarNotificationRecording();
		recordTask = new RadioRecorder(this, this.getIntent()).execute(
				inputUrl, outputUrl);
		Log.d(TAG, "*********** isRadioRecording2=" + recording);

		return recording;
	}

	private void doStopRecording() {
		if (recordTask != null) {
			recordTask.cancel(true);
			Utils.getNotifInstance(this, RadioRecPlus.class)
					.hideStatusBarNotification(
							Constants.NOTIFICATION_ID_RECORDING);
			recording = false;
		}
	}

}
