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
	protected static int SELECTED_STATION_ICON;
	protected static String SELECTED_STATION_NAME;
	protected static String URL_LIVE_STREAM;
	protected static String URL_HOMEPAGE;
	protected static String URL_WEBCAM;
	protected static String URL_CONTACT;
	protected static String URL_SONGTICKER;
	protected static String ANTI_ADS_KEY;

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
		if (Build.VERSION.SDK_INT < 7) {
			getApplicationContext().deleteDatabase("webview.db");
			getApplicationContext().deleteDatabase("webviewCache.db");
		}
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
		if (SELECTED_STATION_ICON == 0x0) {
			SELECTED_STATION_ICON = R.drawable.radio_32;
		}
		// avoiding OutOfMemory
		// http://stackoverflow.com/questions/477572/android-strange-out-of-memory-issue
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[16 * 1024];
		try {
			logo.setImageBitmap(Images.addReflection(BitmapFactory
					.decodeResource(getResources(), SELECTED_STATION_ICON,
							options), 0));
		} catch (Exception e) {
			logo.setImageBitmap(Images.addReflection(BitmapFactory
					.decodeResource(getResources(), R.drawable.radio_32,
							options), 0));
		}
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

		AdMob.showRemoveAds(this);

		Log.d(TAG, "Icon=" + SELECTED_STATION_ICON);
		Log.d(TAG, "Name=" + SELECTED_STATION_NAME);
		Log.d(TAG, "Index=" + SELECTED_STATION_INDEX);

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
		menu.add(0, -2, 0, "Spende").setIcon(R.drawable.ic_menu_agenda);
		menu.add(0, -3, 0, "Settings").setIcon(R.drawable.ic_menu_preferences);
		menu.add(0, -4, 0, "Beenden").setIcon(R.drawable.ic_menu_exit);
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
									// activity’s order within the task is
									// unchanged.
								}
							});
			return closeWindowBuilder.create();

		case Constants.LIVE_STREAM_STATION:

			// prepare the alert box
			AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
			// set the title to display
			alertbox.setTitle("Info");
			// set the message to display
			alertbox.setMessage(this.getResources().getString(R.string.nurLive));
			// set a positive/yes button and create a listener
			alertbox.setPositiveButton(
					this.getResources().getString(R.string.ok),
					new DialogInterface.OnClickListener() {
						// do something when the button is clicked
						public void onClick(DialogInterface arg0, int arg1) {
							return;
						}
					});
			// display box
			alertbox.show();
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
			Log.i(TAG, "spende");
			this.startActivity(new Intent(this, Donate.class));
			break;
		case -3:
			Log.i(TAG, "einstellungen");
			this.startActivity(new Intent(this, Settings.class));
			break;
		case -4:
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
		if (firstStart) {
			Log.d(TAG,
					"firstStart -> arg0.setSelection(SELECTED_STATION_INDEX)");
			arg0.setSelection(SELECTED_STATION_INDEX);
		} else {
			SELECTED_STATION_INDEX = arg0.getSelectedItemPosition();
		}
		Log.d(TAG, "getSelectedItemPosition2=" + arg0.getSelectedItemPosition());
		Log.d(TAG, "SELECTED_STATION_INDEX2=" + SELECTED_STATION_INDEX);

		changeStation();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
	}

	private void changeStation() {
		int index = SELECTED_STATION_INDEX;
		if (index < 0) {
			index = 0;
		}
		HashMap<String, Object> map = null;
		try {
			map = stationList.get(index);
		} catch (Exception e) {
			index = 0;
			map = stationList.get(index);
		}
		Log.d(TAG, "Image=" + SELECTED_STATION_NAME);
		SELECTED_STATION_ICON = (Integer) map.get("icon");
		// avoiding OutOfMemory
		// http://stackoverflow.com/questions/477572/android-strange-out-of-memory-issue
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[16 * 1024];

		logo.setImageBitmap(Images.addReflection(BitmapFactory.decodeResource(
				getResources(), SELECTED_STATION_ICON, options), 0));
		Log.d(TAG, "*********** Stream=" + map.get("stream"));

		SELECTED_STATION_NAME = "" + map.get("name");
		URL_LIVE_STREAM = "" + map.get("stream");
		URL_HOMEPAGE = "" + map.get("homepage");
		URL_WEBCAM = "" + map.get("webcam");
		final TextView textViewWebcam = (TextView) findViewById(R.id.webcam);
		if (Build.VERSION.SDK_INT < 5 || URL_WEBCAM == null
				|| URL_WEBCAM.trim().equals("")) {
			textViewWebcam.setVisibility(View.INVISIBLE);
		} else {
			textViewWebcam.setVisibility(View.VISIBLE);
		}
		URL_CONTACT = "" + map.get("email");

		if (!firstStart && playing) {
			if (Constants.getLiveStreamStations().contains(
					SELECTED_STATION_NAME)) {
				Log.d(TAG, "------ ist Radio Gelb-Schwarz");
				showDialog(Constants.LIVE_STREAM_STATION);
			}
			getRadioPlayer().doStartPlay(this);
		} else {
			getRadioPlayer().doStopPlay(this);
		}
		firstStart = false;
		back.setEnabled(index > 0);
		fwd.setEnabled(index < stationList.size() - 1);
		storePreferences();
	}

	private void storePreferences() {
		SharedPreferences settings = getSharedPreferences(
				Constants.PREFERENCES_FILE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(Constants.SELECTED_STATION_INDEX, SELECTED_STATION_INDEX);
		editor.putInt(Constants.SELECTED_STATION_ICON, SELECTED_STATION_ICON);
		editor.putString(Constants.SELECTED_STATION_NAME, SELECTED_STATION_NAME);
		editor.putString(Constants.SELECTED_STATION_STREAM, URL_LIVE_STREAM);
		editor.putString(Constants.SELECTED_STATION_HOMEPAGE, URL_HOMEPAGE);
		editor.putString(Constants.SELECTED_STATION_WEBCAM, URL_WEBCAM);
		editor.putString(Constants.SELECTED_STATION_CONTACT, URL_CONTACT);
		editor.putString(Constants.ANTI_ADS_KEY, ANTI_ADS_KEY);
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
		ANTI_ADS_KEY = settings.getString(Constants.ANTI_ADS_KEY, ANTI_ADS_KEY);
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
