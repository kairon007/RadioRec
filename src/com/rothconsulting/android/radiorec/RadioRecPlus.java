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
import android.database.Cursor;
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
import android.widget.ToggleButton;

import com.rothconsulting.android.radiorec.sqlitedb.DbAdapter;

public class RadioRecPlus extends Activity implements OnClickListener,
		OnItemSelectedListener {

	static final String TAG = "RadioRecPlus";

	private boolean playing, recording, firstStart;
	private Spinner stations;

	public Spinner getStations() {
		return stations;
	}

	private ArrayList<HashMap<String, Object>> stationList;
	private ImageView logo;
	private ImageButton back, fwd;
	private RadioPlayer radioPlayer;
	private AsyncTask<URL, Integer, Long> recordTask;
	private String origRT1steam = null;
	private ToggleButton favIcon = null;

	Utils utils = new Utils();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT < 7) {
			getApplicationContext().deleteDatabase("webview.db");
			getApplicationContext().deleteDatabase("webviewCache.db");
		}
		Log.d(TAG, "RadioRePlus");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		firstStart = true;
		Utils utils = new Utils();
		utils.getPreferences(this);
		// get components and register clicks
		stations = (Spinner) findViewById(R.id.stations);
		logo = (ImageView) findViewById(R.id.logo);
		((TextView) findViewById(R.id.homepage)).setOnClickListener(this);
		((TextView) findViewById(R.id.webcam)).setOnClickListener(this);
		((TextView) findViewById(R.id.mail)).setOnClickListener(this);
		favIcon = (ToggleButton) findViewById(R.id.toggleButtonFavorit);
		((ToggleButton) findViewById(R.id.toggleButtonFavorit))
				.setOnClickListener(this);
		back = ((ImageButton) findViewById(R.id.back));
		back.setOnClickListener(this);
		back.setEnabled(false);
		((ImageButton) findViewById(R.id.play)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.rec)).setOnClickListener(this);
		fwd = ((ImageButton) findViewById(R.id.fwd));
		fwd.setOnClickListener(this);
		// set first image
		if (Constants.THE_SELECTED_STATION_ICON == 0x0) {
			Constants.THE_SELECTED_STATION_ICON = R.drawable.radio_32;
		}
		// avoiding OutOfMemory
		// http://stackoverflow.com/questions/477572/android-strange-out-of-memory-issue
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[16 * 1024];
		try {
			logo.setImageBitmap(Images.addReflection(BitmapFactory
					.decodeResource(getResources(),
							Constants.THE_SELECTED_STATION_ICON, options), 0));
		} catch (Exception e) {
			logo.setImageBitmap(Images.addReflection(BitmapFactory
					.decodeResource(getResources(), R.drawable.radio_32,
							options), 0));
		}

		Log.d(TAG, "Icon=" + Constants.THE_SELECTED_STATION_ICON);
		Log.d(TAG, "Name=" + Constants.THE_SELECTED_STATION_NAME);
		Log.d(TAG, "Index=" + Constants.THE_SELECTED_STATION_INDEX);

		// construct list of maps for the spinner (DropDown-Selector)
		stationList = new ArrayList<HashMap<String, Object>>();

		AdMob admob = new AdMob();
		admob.showRemoveAds(this);

		Log.d(TAG, "getAllStations()");
		Stations theStations = new Stations();
		stationList = theStations.getAllStations();

		SimpleAdapter adapter = new SimpleAdapter(this, stationList,
				R.layout.station_listitem,
				new String[] { "icon_small", "name" }, new int[] {
						R.id.option_icon, R.id.option_text });
		stations.setAdapter(adapter);
		stations.setOnItemSelectedListener(this);
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
		utils.getNotifInstance(this, RadioRecPlus.class)
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
					this.getResources().getString(android.R.string.ok),
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
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, -1, 0, this.getResources().getString(R.string.info))
				.setIcon(android.R.drawable.ic_menu_info_details);
		menu.add(0, -2, 0,
				this.getResources().getString(R.string.donate_adfree)).setIcon(
				android.R.drawable.ic_menu_agenda);
		menu.add(0, -3, 0, this.getResources().getString(R.string.settings))
				.setIcon(android.R.drawable.ic_menu_preferences);
		menu.add(0, -4, 0, this.getResources().getString(R.string.ende))
				.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
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
			Log.i(TAG, "spende");
			this.startActivity(new Intent(this, Donate.class));
			break;
		case -3:
			Log.i(TAG, "settings");
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
			if (Constants.THE_URL_HOMEPAGE != null
					&& !Constants.THE_URL_HOMEPAGE.equals("")) {
				intentHomepage.setData(Uri.parse(Constants.THE_URL_HOMEPAGE));
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
			if (Constants.THE_URL_CONTACT.startsWith("http")) {
				Intent emailIntent = new Intent(Intent.ACTION_VIEW);
				emailIntent.setData(Uri.parse(Constants.THE_URL_CONTACT));
				startActivity(emailIntent);
			} else {
				Intent emailIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
						new String[] { Constants.THE_URL_CONTACT });
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Mein Wunsch");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
				startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			}
			break;
		case R.id.toggleButtonFavorit:
			Log.i(TAG, "favorit");
			DbAdapter dbadapter = new DbAdapter(this);
			if (favIcon.isChecked()) {
				favIcon.setButtonDrawable(android.R.drawable.star_big_on);
				Log.d(TAG, "isChecked -> instertStation");
				dbadapter.open();
				dbadapter.insertStation(Constants.THE_SELECTED_STATION_ICON,
						Constants.THE_SELECTED_STATION_NAME);
				dbadapter.close();
			} else {
				favIcon.setButtonDrawable(android.R.drawable.star_big_off);
				Log.d(TAG, "is NOT Checked -> deleteStation");
				dbadapter.open();
				dbadapter.deleteStation(Constants.THE_SELECTED_STATION_NAME);
				dbadapter.close();
			}
			break;
		case R.id.back:
			if (stations.getSelectedItemPosition() > 0) {
				back.setEnabled(false);
				stations.setSelection(stations.getSelectedItemPosition() - 1);
				Constants.THE_SELECTED_STATION_INDEX = stations
						.getSelectedItemPosition();
				Log.d(TAG, "back");
			}
			break;
		case R.id.play:
			playing = !playing;
			((ImageButton) v).setImageResource(playing ? R.drawable.button_stop
					: R.drawable.button_play);
			changeStation();
			break;
		case R.id.rec:
			recording = !recording;
			if (recording) {
				doStartRecording();
			}
			((ImageButton) v)
					.setImageResource(recording ? R.drawable.button_record_on
							: R.drawable.button_record);
			if (!recording) {
				doStopRecording();
			}
			break;
		case R.id.fwd:
			if (stations.getSelectedItemPosition() < stationList.size() - 1) {
				fwd.setEnabled(false);
				stations.setSelection(stations.getSelectedItemPosition() + 1);
				Constants.THE_SELECTED_STATION_INDEX = stations
						.getSelectedItemPosition();
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
		Log.d(TAG, "THE_SELECTED_STATION_INDEX1="
				+ Constants.THE_SELECTED_STATION_INDEX);
		if (firstStart) {
			Log.d(TAG,
					"firstStart -> arg0.setSelection(THE_SELECTED_STATION_INDEX)");
			if (Constants.THE_SELECTED_STATION_INDEX > stationList.size()) {
				Constants.THE_SELECTED_STATION_INDEX = 0;
			}
			arg0.setSelection(Constants.THE_SELECTED_STATION_INDEX);
		} else {
			Constants.THE_SELECTED_STATION_INDEX = arg0
					.getSelectedItemPosition();
		}
		Log.d(TAG, "getSelectedItemPosition2=" + arg0.getSelectedItemPosition());
		Log.d(TAG, "SELECTED_STATION_INDEX2="
				+ Constants.THE_SELECTED_STATION_INDEX);

		changeStation();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
	}

	private void changeStation() {
		int index = Constants.THE_SELECTED_STATION_INDEX;
		if (index < 0 || index > stationList.size()) {
			index = 0;
			Constants.THE_SELECTED_STATION_INDEX = index;
		}

		Log.d(TAG,
				"index=" + index + " / stationList.size()="
						+ stationList.size());

		HashMap<String, Object> map = null;
		map = stationList.get(index);

		Log.d(TAG, "Sender=" + Constants.THE_SELECTED_STATION_NAME);
		Constants.THE_SELECTED_STATION_ICON = (Integer) map.get("icon");
		// avoiding OutOfMemory
		// http://stackoverflow.com/questions/477572/android-strange-out-of-memory-issue
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[16 * 1024];

		logo.setImageBitmap(Images.addReflection(BitmapFactory.decodeResource(
				getResources(), Constants.THE_SELECTED_STATION_ICON, options),
				0));
		Log.d(TAG, "*********** Stream=" + map.get("stream"));

		Constants.THE_SELECTED_STATION_NAME = "" + map.get("name");
		Constants.THE_URL_LIVE_STREAM = "" + map.get("stream");

		Constants.THE_URL_HOMEPAGE = "" + map.get("homepage");
		Constants.THE_URL_WEBCAM = "" + map.get("webcam");
		final TextView textViewWebcam = (TextView) findViewById(R.id.webcam);
		if (Build.VERSION.SDK_INT < 5 || Constants.THE_URL_WEBCAM == null
				|| Constants.THE_URL_WEBCAM.trim().equals("")) {
			textViewWebcam.setVisibility(View.INVISIBLE);
		} else {
			textViewWebcam.setVisibility(View.VISIBLE);
		}
		Constants.THE_URL_CONTACT = "" + map.get("email");

		this.setFavIcon();

		if (!firstStart && playing) {
			if (Constants.getLiveStreamStations().contains(
					Constants.THE_SELECTED_STATION_NAME)) {
				Log.d(TAG, "------ ist Radio Gelb-Schwarz");
				showDialog(Constants.LIVE_STREAM_STATION);
			}
			if (Constants.THE_SELECTED_STATION_NAME
					.equalsIgnoreCase(Constants.RADIO_KINGSTONHOT)) {
				// Kingstonhot.de hat immer Donnerstags eine Live Sendung. Ab
				// Freitag kann man diese als mp3 hören. Daher ist die URL
				// dynamisch.
				Constants.THE_URL_LIVE_STREAM = Constants.THE_URL_LIVE_STREAM
						+ utils.getKingstonHotFileName();
				Log.d(TAG, "*********** new Stream="
						+ Constants.THE_URL_LIVE_STREAM);
			}
			if (Constants.THE_SELECTED_STATION_NAME
					.equalsIgnoreCase(Constants.RADIO_RT1_SUEDSCHWABEN)) {
				WebTool webtool = new WebTool();
				// rt1 ist geschützt und braucht login token damit man den
				// Stream
				// abspielen kann.
				origRT1steam = Constants.THE_URL_LIVE_STREAM;
				Constants.THE_URL_LIVE_STREAM = Constants.THE_URL_LIVE_STREAM
						+ webtool.getRT1token(this);
				Log.d(TAG, "*********** new Stream="
						+ Constants.THE_URL_LIVE_STREAM);
			}
			getRadioPlayer().doStartPlay(this);
		} else {
			getRadioPlayer().doStopPlay(this);
		}
		firstStart = false;
		back.setEnabled(index > 0);
		fwd.setEnabled(index < stationList.size() - 1);
		utils.storePreferences(this);
	}

	protected RadioPlayer getRadioPlayer() {
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

			if (Constants.THE_SELECTED_STATION_NAME
					.equalsIgnoreCase(Constants.RADIO_RT1_SUEDSCHWABEN)) {
				WebTool webtool = new WebTool();
				// rt1 ist geschützt und braucht login token damit man den
				// Stream abspielen kann.
				if (origRT1steam == null) {
					origRT1steam = Constants.THE_URL_LIVE_STREAM;
				}
				Constants.THE_URL_LIVE_STREAM = origRT1steam
						+ webtool.getRT1token(this);
				Log.d(TAG, "*********** new Stream="
						+ Constants.THE_URL_LIVE_STREAM);
			}

			Log.d(TAG, "Constants.THE_URL_LIVE_STREAM="
					+ Constants.THE_URL_LIVE_STREAM);
			inputUrl = new URL(Constants.THE_URL_LIVE_STREAM);
		} catch (MalformedURLException e) {
			utils.getNotifInstance(this, RadioRecPlus.class)
					.showStatusBarNotificationError(
							R.string.internetadresseNichtErreichbar);
		}

		try {
			outputUrl = new URL("file:///" + Constants.THE_SD_CARD_PATH + "/"
					+ Constants.THE_SELECTED_STATION_NAME.replaceAll(" ", "")
					+ "-" + dateTime + ".mp3");
		} catch (MalformedURLException e) {
			utils.getNotifInstance(this, RadioRecPlus.class)
					.showStatusBarNotificationError(
							R.string.kannNichtAufSdCardSchreiben);
		}

		recordTask = new RadioRecorder(this, this.getIntent()).execute(
				inputUrl, outputUrl);
		Log.d(TAG, "*********** isRadioRecording2=" + recording);

		return recording;
	}

	protected void doStopRecording() {
		if (recordTask != null) {
			recordTask.cancel(true);
			utils.getNotifInstance(this, RadioRecPlus.class)
					.hideStatusBarNotification(
							Constants.NOTIFICATION_ID_RECORDING);
			recording = false;
		}
	}

	private void setFavIcon() {
		DbAdapter dbadapter = new DbAdapter(this);
		dbadapter.open();
		Cursor cursor = null;
		cursor = dbadapter.fetchStation(Constants.THE_SELECTED_STATION_NAME);
		if (cursor != null && cursor.getCount() > 0) {
			favIcon.setChecked(true);
			Log.d(TAG, "favIcon.setChecked(true)");
			favIcon.setButtonDrawable(android.R.drawable.star_big_on);
		} else {
			favIcon.setChecked(false);
			Log.d(TAG, "favIcon.setChecked(false)");
			favIcon.setButtonDrawable(android.R.drawable.star_big_off);
		}
		cursor.close();
		dbadapter.close();
	}

	public boolean isPlaying() {
		return playing;
	}
}
