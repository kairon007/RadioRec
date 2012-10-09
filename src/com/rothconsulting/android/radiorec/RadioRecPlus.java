package com.rothconsulting.android.radiorec;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rothconsulting.android.radiorec.filechooser.FileChooser;
import com.rothconsulting.android.radiorec.sqlitedb.DBHelper;
import com.rothconsulting.android.radiorec.sqlitedb.DbAdapter;

public class RadioRecPlus extends Activity implements OnClickListener,
		OnItemSelectedListener, OnSeekBarChangeListener {

	static final String TAG = "RadioRecPlus";
	static final String FAV_ON = "favOn";
	static final String FAV_OFF = "favOff";

	static boolean playing;
	static boolean recording;
	static boolean showCountdown;
	private Context context;

	private boolean firstStart;
	// private Spinner spnLaender;
	private Spinner spnFavoriten, spnAllStations, spnAlphabetisch;
	// ivate ArrayList<HashMap<String, Object>> landList;
	private ArrayList<HashMap<String, Object>> favList, stationList,
			alphabeticList;
	private ImageView logo;
	private TextView favIcon;
	private ImageButton buttonBack, buttonFwd, buttonRec, buttonPlay;
	private static RadioPlayer radioPlayer;
	private static AsyncTask<URL, Integer, Long> recordTask;
	private String origPlanetradioSteam = null;
	private LinearLayout mainScreen;
	private LinearLayout autocomplete;
	private LinearLayout spinner;

	private RelativeLayout timerSeekbarLayout;
	private RelativeLayout timerLayoutWhole;
	private SeekBar timerSeekbar;
	private TextView timerSeekbarText;
	private ImageButton imageButtonUhr;
	private CountDownTimer countDownTimer;
	private int countDownTimerTick = 0;
	private int gcCounter;

	static Utils utils = new Utils();

	public Spinner getStations() {
		return spnAllStations;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// setContentView(R.layout.main);
		Utils utils = new Utils();
		utils.getPreferences(this);

		context = this;
		context.deleteDatabase(DBHelper.DATABASE_NAME);
		gcCounter = 0;
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT < 7) {
			context.deleteDatabase("webview.db");
			context.deleteDatabase("webviewCache.db");
		}
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initGui();
	}

	@Override
	protected void onResume() {
		super.onResume();
		hideSearch();
	}

	private void initGui() {
		Utils.log(TAG, "RadioRePlus");
		setContentView(R.layout.main);
		firstStart = true;
		// get components and register clicks
		spnAllStations = (Spinner) findViewById(R.id.stations);
		Constants.SPINNER_ALL_STATIONS = spnAllStations.getId();
		spnFavoriten = (Spinner) findViewById(R.id.spinnerFav);
		Constants.SPINNER_FAVORITEN = spnFavoriten.getId();
		// spnLaender = (Spinner) findViewById(R.id.spinnerLand);
		// Constants.SPINNER_LAENDER = spnLaender.getId();
		spnAlphabetisch = (Spinner) findViewById(R.id.spinnerAlphabetisch);
		Constants.SPINNER_ALPHABETISCH = spnAlphabetisch.getId();
		((Button) findViewById(R.id.buttonFav)).setOnClickListener(this);
		// ((Button) findViewById(R.id.buttonLand)).setOnClickListener(this);
		((Button) findViewById(R.id.buttonAlphabetisch))
				.setOnClickListener(this);
		mainScreen = (LinearLayout) findViewById(R.id.mainScreen);
		autocomplete = (LinearLayout) findViewById(R.id.linearLayoutAutocomplete);
		spinner = (LinearLayout) findViewById(R.id.linearLayoutSpinner);

		timerLayoutWhole = (RelativeLayout) findViewById(R.id.timerLayoutWhole);
		timerSeekbarLayout = (RelativeLayout) findViewById(R.id.timerSeekbarLayout);
		timerSeekbar = (SeekBar) findViewById(R.id.timerSeekbar);
		timerSeekbar.setOnSeekBarChangeListener(this);
		timerSeekbarText = (TextView) findViewById(R.id.timerSeekbarText);
		imageButtonUhr = (ImageButton) findViewById(R.id.imageButtonUhr);
		imageButtonUhr.setOnClickListener(this);

		logo = (ImageView) findViewById(R.id.logo);
		((TextView) findViewById(R.id.webcam)).setOnClickListener(this);
		((TextView) findViewById(R.id.mail)).setOnClickListener(this);
		((TextView) findViewById(R.id.homepage)).setOnClickListener(this);
		favIcon = ((TextView) findViewById(R.id.favoriten));
		favIcon.setOnClickListener(this);
		((TextView) findViewById(R.id.search)).setOnClickListener(this);
		buttonBack = (ImageButton) findViewById(R.id.back);
		buttonBack.setOnClickListener(this);
		buttonBack.setEnabled(false);
		buttonFwd = (ImageButton) findViewById(R.id.fwd);
		buttonFwd.setOnClickListener(this);
		buttonPlay = (ImageButton) findViewById(R.id.play);
		buttonPlay.setOnClickListener(this);
		buttonPlay.setImageResource(playing ? R.drawable.button_stop
				: R.drawable.button_play);
		buttonRec = (ImageButton) findViewById(R.id.rec);
		buttonRec.setOnClickListener(this);
		buttonRec.setImageResource(recording ? R.drawable.button_record_on
				: R.drawable.button_record);

		// set first image
		if (Constants.SELECTED_STATION_ICON_VALUE == 0x0) {
			Constants.SELECTED_STATION_ICON_VALUE = R.drawable.radio_32;
		}
		if (Constants.SELECTED_STATION_ICON_SMALL_VALUE == 0x0) {
			Constants.SELECTED_STATION_ICON_SMALL_VALUE = R.drawable.radio_32_small;
		}
		// avoiding OutOfMemory
		// http://stackoverflow.com/questions/477572/android-strange-out-of-memory-issue
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[16 * 1024];
		try {
			logo.setImageBitmap(Images.addReflection(BitmapFactory
					.decodeResource(getResources(),
							Constants.SELECTED_STATION_ICON_VALUE, options), 0));
		} catch (Exception e) {
			logo.setImageBitmap(Images.addReflection(BitmapFactory
					.decodeResource(getResources(), R.drawable.radio_32,
							options), 0));
		}

		Utils.log(TAG, "Icon=" + Constants.SELECTED_STATION_ICON_VALUE);
		Utils.log(TAG, "Name=" + Constants.SELECTED_STATION_NAME_VALUE);
		Utils.log(TAG, "Index=" + Constants.SELECTED_STATION_INDEX_VALUE);

		AdMob admob = new AdMob();
		admob.showRemoveAds(this);

		Utils.log(TAG, "getAllStations()");
		Stations theStations = new Stations();
		stationList = theStations.getAllStations();
		// landList = theStations.getLandListCh();
		// landList.addAll(theStations.getLandListDe());
		// landList.addAll(theStations.getLandListAt());
		// landList.addAll(theStations.getLandListDiv());

		alphabeticList = Utils.sortStationsByName(stationList);

		// SimpleAdapter laenderAdapter = new SimpleAdapter(this, landList,
		// R.layout.station_listitem,
		// new String[] { "icon_small", "name" }, new int[] {
		// R.id.option_icon, R.id.option_text });
		// spnLaender.setAdapter(laenderAdapter);
		// spnLaender.setOnItemSelectedListener(this);

		SimpleAdapter alphabetischAdapter = new SimpleAdapter(this,
				alphabeticList, R.layout.station_listitem, new String[] {
						"icon_small", "name" }, new int[] { R.id.option_icon,
						R.id.option_text });
		spnAlphabetisch.setAdapter(alphabetischAdapter);
		spnAlphabetisch.setOnItemSelectedListener(this);

		SimpleAdapter adapter = new SimpleAdapter(this, stationList,
				R.layout.station_listitem,
				new String[] { "icon_small", "name" }, new int[] {
						R.id.option_icon, R.id.option_text });
		spnAllStations.setAdapter(adapter);
		spnAllStations.setOnItemSelectedListener(this);
		hideSearch();
		setFavIcon();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (playing) {
				showDialog(Constants.PRESS_BACK_BUTTON);
				return true;
			} else {
				stopPlayAndRecord();
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
			final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
					context);
			alertBuilder
					.setMessage(
							this.getResources().getString(
									R.string.willstDuWeiterHoeren))
					.setCancelable(true)
					.setPositiveButton(
							this.getResources().getString(R.string.wegDamit),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										final DialogInterface dialog,
										final int id) {
									stopPlay();
									stopRecord();
									finish();
								}
							})
					.setNegativeButton(
							this.getResources()
									.getString(R.string.weiterHoeren),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										final DialogInterface dialog,
										final int id) {
									moveTaskToBack(true);
									// Move the task containing this activity to
									// the back of the activity stack. The
									// activity's order within the task is
									// unchanged.
								}
							});
			return alertBuilder.create();
		case Constants.LIVE_STREAM_STATION:
			String message1 = this.getResources().getString(R.string.nurLive);
			SimpleAlertBox.showAlert(this, "Info", message1);
			break;
		}
		return null;
	}

	@Override
	public void onClick(View v) {
		firstStart = false;
		// always hide search
		if (v.getId() != R.id.search) {
			hideSearch();
		}

		switch (v.getId()) {
		case R.id.homepage:
			Log.i(TAG, "homepage");
			if (Constants.URL_HOMEPAGE_VALUE != null
					&& !Constants.URL_HOMEPAGE_VALUE.trim().equals("")) {
				Uri uri = Uri.parse(Constants.URL_HOMEPAGE_VALUE);
				Intent intentHomepage = new Intent(Intent.ACTION_VIEW, uri);
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
			if (Constants.URL_CONTACT_VALUE.startsWith("http")) {
				Intent emailIntent = new Intent(Intent.ACTION_VIEW);
				emailIntent.setData(Uri.parse(Constants.URL_CONTACT_VALUE));
				startActivity(emailIntent);
			} else {
				Intent emailIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
						new String[] { Constants.URL_CONTACT_VALUE });
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Mein Wunsch");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
				startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			}
			break;
		case R.id.search:
			Log.i(TAG, "search");
			prepareSearch();
			break;

		case R.id.favoriten:
			Log.i(TAG, "favorit");
			DbAdapter dbadapter = new DbAdapter(this);

			if (favIcon.getTag().equals(FAV_OFF)) {
				favIcon.setTag(FAV_ON);
				favIcon.setCompoundDrawablesWithIntrinsicBounds(0,
						android.R.drawable.star_big_on, 0, 0);
				Utils.log(TAG, "favOn -> instertStation");
				dbadapter.open();
				dbadapter.insertStation(
						Constants.SELECTED_STATION_ICON_SMALL_VALUE,
						Constants.SELECTED_STATION_NAME_VALUE);
				dbadapter.close();
			} else {
				favIcon.setTag(FAV_OFF);
				favIcon.setCompoundDrawablesWithIntrinsicBounds(0,
						android.R.drawable.star_big_off, 0, 0);
				Utils.log(TAG, "favOff -> deleteStation");
				dbadapter.open();
				dbadapter.deleteStation(Constants.SELECTED_STATION_NAME_VALUE);
				dbadapter.close();
			}
			break;
		case R.id.back:
			if (spnAllStations.getSelectedItemPosition() > 0) {
				buttonBack.setEnabled(false);
				spnAllStations.setSelection(spnAllStations
						.getSelectedItemPosition() - 1);
				Constants.SELECTED_STATION_INDEX_VALUE = spnAllStations
						.getSelectedItemPosition();
				Utils.log(TAG, "back");
			}
			break;
		case R.id.play:
			playing = !playing;
			if (playing && !utils.isNetworkAvailable(this, getIntent(), true)) {
				playing = false;
			}
			((ImageButton) v).setImageResource(playing ? R.drawable.button_stop
					: R.drawable.button_play);
			changeStation();
			break;
		case R.id.rec:
			recording = !recording;
			if (recording) {
				recording = doStartRecording();
			}
			// recording can change in doStartRecording()
			if (!recording) {
				stopRecord();
			}
			((ImageButton) v)
					.setImageResource(recording ? R.drawable.button_record_on
							: R.drawable.button_record);
			break;
		case R.id.fwd:
			if (spnAllStations.getSelectedItemPosition() < stationList.size() - 1) {
				buttonFwd.setEnabled(false);
				spnAllStations.setSelection(spnAllStations
						.getSelectedItemPosition() + 1);
				Constants.SELECTED_STATION_INDEX_VALUE = spnAllStations
						.getSelectedItemPosition();
				Utils.log(TAG, "fwd");
			}
			break;
		case R.id.imageButtonUhr:
			showCountdown = !showCountdown;
			this.showTimerbox(showCountdown);
			break;
		case R.id.buttonFav:
			Constants.SPINNER_SELECTION = Constants.SPINNER_FAVORITEN;
			Utils.log(
					TAG,
					"Button Favoriten pressed. First="
							+ spnFavoriten.getFirstVisiblePosition());

			dbadapter = new DbAdapter(this);
			dbadapter.open();
			Cursor cursor = null;
			cursor = dbadapter.fetchAllStations();
			favList = new ArrayList<HashMap<String, Object>>();

			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				do {
					String name = cursor.getString(cursor
							.getColumnIndex(DbAdapter.KEY_STATION_NAME));

					HashMap<String, Object> map = Utils.getFullStation(
							stationList, name);

					favList.add(map);
				} while (cursor.moveToNext());

			}
			cursor.close();
			dbadapter.close();

			if (isEmptyFavorites()) {
				return;
			}

			SimpleAdapter favoritesAdapter = new SimpleAdapter(this, favList,
					R.layout.station_listitem, new String[] { "icon_small",
							"name" }, new int[] { R.id.option_icon,
							R.id.option_text });
			spnFavoriten.setAdapter(favoritesAdapter);
			spnFavoriten.setOnItemSelectedListener(this);
			// spnFavoriten.performItemClick(v, -1, -1);
			spnFavoriten.performClick();
			break;
		// case R.id.buttonLand:
		// Constants.SPINNER_SELECTION = Constants.SPINNER_LAENDER;
		//
		// Utils.log(
		// TAG,
		// "Button Land pressed. Firts="
		// + spnLaender.getFirstVisiblePosition());
		//
		// spnLaender.performClick();
		// break;
		case R.id.buttonAlphabetisch:
			Constants.SPINNER_SELECTION = Constants.SPINNER_ALPHABETISCH;

			Utils.log(
					TAG,
					"Button Stil pressed. Firts="
							+ spnAlphabetisch.getFirstVisiblePosition());

			spnAlphabetisch.performClick();
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> spinner, View linearLayout,
			int arg2, long arg3) {

		// empty favorites will be ignored
		if (isEmptyFavorites()) {
			return;
		}

		Utils.log(TAG, "****************");
		Utils.log(TAG, "onItemSelected(AdapterView<?> arg0 = " + spinner);
		Utils.log(TAG, "onItemSelected(View arg1 = " + linearLayout);
		Utils.log(TAG, "onItemSelected(int arg2 = " + arg2);
		Utils.log(TAG, "onItemSelected(long arg0 = " + arg3);
		Utils.log(TAG,
				"getSelectedItemPosition1=" + spinner.getSelectedItemPosition());
		Utils.log(TAG, "SELECTED_STATION_INDEX_VALUE1="
				+ Constants.SELECTED_STATION_INDEX_VALUE);
		Utils.log(TAG, "****************");

		if (firstStart) {
			Utils.log(TAG,
					"firstStart -> arg0.setSelection(SELECTED_STATION_INDEX_VALUE)");
			if (Constants.SELECTED_STATION_INDEX_VALUE > stationList.size()) {
				Constants.SELECTED_STATION_INDEX_VALUE = 0;
			}
			spinner.setSelection(Constants.SELECTED_STATION_INDEX_VALUE);
		} else {
			Constants.SELECTED_STATION_INDEX_VALUE = spinner
					.getSelectedItemPosition();
		}
		Utils.log(TAG,
				"getSelectedItemPosition2=" + spinner.getSelectedItemPosition());
		Utils.log(TAG, "SELECTED_STATION_INDEX2="
				+ Constants.SELECTED_STATION_INDEX_VALUE);

		changeStation();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	private void changeStation() {
		int index = Constants.SELECTED_STATION_INDEX_VALUE;
		// Check inex size
		if (index < 0 || index >= stationList.size()) {
			index = 0;
			Constants.SELECTED_STATION_INDEX_VALUE = index;
		}
		// empty favorites will be ignored
		if (isEmptyFavorites()) {
			return;
		}

		Utils.log(TAG, "index=" + index + " / stationList.size()="
				+ stationList.size());

		// HashMap<String, Object> map = stationList.get(index);

		HashMap<String, Object> map = null;
		if (Constants.SPINNER_SELECTION == Constants.SPINNER_FAVORITEN) {
			map = favList.get(index);
		} else if (Constants.SPINNER_SELECTION == Constants.SPINNER_ALPHABETISCH) {
			map = alphabeticList.get(index);
		} else {
			map = stationList.get(index);
		}

		// reset Constants.SPINNER_SELECTION
		Constants.SPINNER_SELECTION = Constants.SPINNER_ALL_STATIONS;

		spnAllStations.setSelection(Utils.getSpinnerPosition(stationList,
				(String) map.get("name")));

		Utils.log(TAG, "!!! Sender=" + Constants.SELECTED_STATION_NAME_VALUE);
		Constants.SELECTED_STATION_ICON_VALUE = (Integer) map.get("icon");
		Constants.SELECTED_STATION_ICON_SMALL_VALUE = (Integer) map
				.get("icon_small");

		// avoiding OutOfMemory
		if (gcCounter % 2 == 0) { // call gc only at even numbers
			System.gc();
			gcCounter++;
		}
		// http://stackoverflow.com/questions/477572/android-strange-out-of-memory-issue
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[16 * 1024];

		logo.setImageBitmap(Images.addReflection(
				BitmapFactory.decodeResource(getResources(),
						Constants.SELECTED_STATION_ICON_VALUE, options), 0));
		Utils.log(TAG, "*********** Stream=" + map.get("stream"));

		Constants.SELECTED_STATION_NAME_VALUE = "" + map.get("name");
		Constants.URL_LIVE_STREAM_VALUE = "" + map.get("stream");

		Constants.URL_HOMEPAGE_VALUE = "" + map.get("homepage");
		Constants.URL_WEBCAM_VALUE = "" + map.get("webcam");
		showHideCam();
		Constants.URL_CONTACT_VALUE = "" + map.get("email");

		this.setFavIcon();

		if (!firstStart && playing) {
			if (Constants.getLiveStreamStations().contains(
					Constants.SELECTED_STATION_NAME_VALUE)) {
				Utils.log(TAG, "------ ist Fussball Radio");
				showDialog(Constants.LIVE_STREAM_STATION);
			}
			if (Constants.SELECTED_STATION_NAME_VALUE
					.equalsIgnoreCase(Stations.RADIO_JUGGLERZ)) {
				// jugglerz.de hat immer Donnerstags eine Live Sendung. Ab
				// Freitag kann man diese als mp3 h�ren. Daher ist die URL
				// dynamisch.
				WebTool webtool = new WebTool();
				Constants.URL_LIVE_STREAM_VALUE = Constants.URL_LIVE_STREAM_VALUE
						+ webtool.getJugglerzFileName(this);
				Utils.log(TAG, "*********** new Stream="
						+ Constants.URL_LIVE_STREAM_VALUE);
			}
			if (Constants.SELECTED_STATION_NAME_VALUE
					.equalsIgnoreCase(Stations.RADIO_PLANET_RADIO)) {
				WebTool webtool = new WebTool();
				// planet radio ist gesch�tzt und braucht login token damit man
				// den Stream abspielen kann.
				origPlanetradioSteam = Constants.URL_LIVE_STREAM_VALUE;
				Constants.URL_LIVE_STREAM_VALUE = Constants.URL_LIVE_STREAM_VALUE
						+ webtool.getPlanetradioToken(this);
				Utils.log(TAG, "*********** new Stream="
						+ Constants.URL_LIVE_STREAM_VALUE);
			}

			getRadioPlayer().doStartPlay(this);
			getWindow()
					.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		} else {
			this.stopPlay();
		}
		firstStart = false;
		buttonBack.setEnabled(index > 0);
		buttonFwd.setEnabled(index < stationList.size() - 1);
		utils.storePreferences(this);
	}

	protected static RadioPlayer getRadioPlayer() {
		if (radioPlayer == null) {
			radioPlayer = new RadioPlayer();
		}
		return radioPlayer;
	}

	private boolean doStartRecording() {
		Utils.log(TAG, "startRecording");

		if (!utils.isNetworkAvailable(this, getIntent(), true)) {
			return false;
		} else {
			Notifications noti = new Notifications(this, getIntent());
			noti.hideStatusBarNotification(Constants.NOTIFICATION_ID_ERROR_CONNECTION);
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String dateTime = formatter.format(new Date());

		URL inputUrl = null;
		URL outputUrl = null;

		try {
			if (Constants.SELECTED_STATION_NAME_VALUE
					.equalsIgnoreCase(Stations.RADIO_PLANET_RADIO)) {
				WebTool webtool = new WebTool();
				// rt1 ist gesch�tzt und braucht login token damit man den
				// Stream abspielen kann.
				if (origPlanetradioSteam == null) {
					origPlanetradioSteam = Constants.URL_LIVE_STREAM_VALUE;
				}
				Constants.URL_LIVE_STREAM_VALUE = origPlanetradioSteam
						+ webtool.getPlanetradioToken(this);
				Utils.log(TAG, "*********** new Stream="
						+ Constants.URL_LIVE_STREAM_VALUE);
			}

			Utils.log(TAG, "Constants.URL_LIVE_STREAM_VALUE="
					+ Constants.URL_LIVE_STREAM_VALUE);
			inputUrl = new URL(Constants.URL_LIVE_STREAM_VALUE);
		} catch (MalformedURLException e) {
			utils.getNotifInstance(this, RadioRecPlus.class)
					.showStatusBarNotificationError(
							R.string.internetadresseNichtErreichbar);
		}

		try {
			outputUrl = new URL("file:///" + Constants.SD_CARD_PATH_VALUE + "/"
					+ Constants.SELECTED_STATION_NAME_VALUE.replaceAll(" ", "")
					+ "-" + dateTime + ".mp3");
		} catch (MalformedURLException e) {
			utils.getNotifInstance(this, RadioRecPlus.class)
					.showStatusBarNotificationError(
							R.string.kannNichtAufSdCardSchreiben);
		}

		recordTask = new RadioRecorder(this, this.getIntent()).execute(
				inputUrl, outputUrl);
		Utils.log(TAG, "*********** isRadioRecording2=" + recording);

		return recording;
	}

	protected static void doStopRecording(Context context) {
		if (recordTask != null) {
			recordTask.cancel(true);
			utils.getNotifInstance(context, RadioRecPlus.class)
					.hideStatusBarNotification(
							Constants.NOTIFICATION_ID_RECORDING);
			recording = false;
		}
	}

	private void setFavIcon() {
		DbAdapter dbadapter = new DbAdapter(this);
		dbadapter.open();
		Cursor cursor = null;
		cursor = dbadapter.fetchStation(Constants.SELECTED_STATION_NAME_VALUE);
		if (cursor != null && cursor.getCount() > 0) {
			Utils.log(TAG, "favIcon ON");
			favIcon.setTag(FAV_ON);
			favIcon.setCompoundDrawablesWithIntrinsicBounds(0,
					android.R.drawable.star_big_on, 0, 0);
		} else {
			Utils.log(TAG, "favIcon OFF");
			favIcon.setTag(FAV_OFF);
			favIcon.setCompoundDrawablesWithIntrinsicBounds(0,
					android.R.drawable.star_big_off, 0, 0);
		}
		cursor.close();
		dbadapter.close();
	}

	private void showTimerbox(boolean on) {
		if (on) {
			timerLayoutWhole.setVisibility(View.VISIBLE);
			timerSeekbarLayout.setVisibility(View.VISIBLE);
			timerSeekbarText.setVisibility(View.VISIBLE);
			timerSeekbar.setVisibility(View.VISIBLE);
		} else {
			timerSeekbarLayout.setVisibility(View.GONE);
			showCountdown = false;
			// mainScreen.refreshDrawableState();
		}
	}

	// Timer SeekBar
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromTouch) {
		timerSeekbarText.setText(getString(R.string.sleepTimerEndIn,
				Utils.getHhMmFromMinutes(progress)));
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		stopTimer();
		showTimerbox(true);
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		if (seekBar.getProgress() > 0) {
			this.startTimer(seekBar);
		} else {
			timerSeekbarLayout.setVisibility(View.GONE);
		}
	}

	private void startTimer(final SeekBar seekBar) {
		long millisInFuture = seekBar.getProgress() * 60 * 1000;

		countDownTimer = new CountDownTimer(millisInFuture, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				timerSeekbarText.setText(getString(R.string.sleepTimerEndIn,
						Utils.getHhMmSs(millisUntilFinished)));
				countDownTimerTick = Integer.valueOf(""
						+ (millisUntilFinished / 60 / 1000));
				setSeekBarProgress(countDownTimerTick);
				Utils.log(TAG, "countDownTimerTick=" + countDownTimerTick);
			}

			@Override
			public void onFinish() {
				Toast.makeText(context, R.string.timerEnd, Toast.LENGTH_LONG)
						.show();
				showCountdown = true;
				timerSeekbarText
						.setText(getString(R.string.sleepTimerEinstellen));
				seekBar.setProgress(0);
				setSeekBarProgress(0);
				timerSeekbarLayout.setVisibility(View.GONE);
				stopPlayAndRecord();
				if (Constants.CLOSE_APP_TIMER_END_VALUE) {
					finish();
				}
			}
		};
		countDownTimer.start();
	}

	private void setSeekBarProgress(int progress) {
		if (timerSeekbar != null) {
			timerSeekbar.setProgress(progress);
		}
	}

	private void stopTimer() {
		if (countDownTimer != null) {
			countDownTimer.cancel();
		}
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopPlayAndRecord();
	}

	// Rotation
	@Override
	public void onConfigurationChanged(Configuration _newConfig) {
		super.onConfigurationChanged(_newConfig);
		Utils.log(TAG, "**+*+*+*+*+*+* playing=" + playing);
		if (!playing) {
			// Constants.SPINNER_SELECTION = Constants.SPINNER_ALL_STATIONS;
			initGui();
			setSeekBarProgress(countDownTimerTick);
			mainScreen.refreshDrawableState();
			showCountdown = false;
		}
	}

	public boolean isPlaying() {
		return playing;
	}

	private void stopPlayAndRecord() {
		stopPlay();
		stopRecord();
	}

	private void stopPlay() {
		getRadioPlayer().doStopPlay(this);
		playing = Boolean.FALSE;
		((ImageButton) findViewById(R.id.play))
				.setImageResource(R.drawable.button_play);
	}

	private void stopRecord() {
		doStopRecording(this);
		recording = Boolean.FALSE;
		((ImageButton) findViewById(R.id.rec))
				.setImageResource(R.drawable.button_record);
	}

	private void startPlay() {
		getRadioPlayer().doStartPlay(this);
		playing = Boolean.TRUE;
		((ImageButton) findViewById(R.id.play))
				.setImageResource(R.drawable.button_stop);
	}

	private void showHideCam() {
		final TextView textViewWebcam = (TextView) findViewById(R.id.webcam);
		if (Build.VERSION.SDK_INT < 5) {
			textViewWebcam.setVisibility(View.GONE);
		} else if (Constants.URL_WEBCAM_VALUE == null
				|| Constants.URL_WEBCAM_VALUE.trim().equals("")) {
			textViewWebcam.setVisibility(View.INVISIBLE);
		} else {
			textViewWebcam.setVisibility(View.VISIBLE);
		}
	}

	private void prepareSearch() {
		final SearchAutoCompleteTextView myAutoComplete = (SearchAutoCompleteTextView) findViewById(R.id.autocomplete);
		myAutoComplete.setText("");
		final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		myAutoComplete.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View textView,
					int arg2, long arg3) {

				imm.hideSoftInputFromWindow(myAutoComplete.getWindowToken(), 0);
				autocomplete.setVisibility(View.GONE);
				spinner.setVisibility(View.VISIBLE);

				spnAllStations.setSelection(Utils.getSpinnerPosition(
						stationList, "" + ((TextView) textView).getText()));
			}
		});

		if (autocomplete.getVisibility() == View.VISIBLE) {
			imm.hideSoftInputFromWindow(myAutoComplete.getWindowToken(), 0);
			hideSearch();
		} else {
			autocomplete.setVisibility(View.VISIBLE);
			spinner.setVisibility(View.INVISIBLE);

			// getWindow().setSoftInputMode(
			// WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line);

			List<String> stationNameList = Utils.getStationNameList(
					this.stationList, null);
			for (String stationName : stationNameList) {
				adapter.add(stationName);
			}
			myAutoComplete.setAdapter(adapter);
			myAutoComplete.requestFocus();
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		}
	}

	private void hideSearch() {
		autocomplete.setVisibility(View.GONE);
		spinner.setVisibility(View.VISIBLE);
		// hide keyboard
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	private boolean isEmptyFavorites() {
		// empty favorites will be ignored
		if (Constants.SPINNER_SELECTION == Constants.SPINNER_FAVORITEN
				&& (favList == null || favList.size() == 0)) {
			String message = this.getResources().getString(
					R.string.nochKeineFavoriten);
			SimpleAlertBox.showAlert(context, "Info", message);
			Constants.SPINNER_SELECTION = Constants.SPINNER_ALL_STATIONS;
			return true;
		}
		return false;
	}

	// ------------------------------------------------------------
	// Menu Stuff
	// ------------------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, -1, 0, this.getResources().getString(R.string.info))
				.setIcon(android.R.drawable.ic_menu_info_details);
		menu.add(0, -2, 0, this.getResources().getString(R.string.musicBrowser))
				.setIcon(android.R.drawable.ic_menu_slideshow);
		menu.add(0, -3, 0,
				this.getResources().getString(R.string.donate_adfree)).setIcon(
				android.R.drawable.ic_menu_agenda);
		menu.add(0, -4, 0, this.getResources().getString(R.string.settings))
				.setIcon(android.R.drawable.ic_menu_preferences);
		menu.add(0, -5, 0, this.getResources().getString(R.string.ende))
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
			Log.i(TAG, "file chooser");
			this.startActivity(new Intent(this, FileChooser.class));
			break;
		case -3:
			Log.i(TAG, "spende");
			this.startActivity(new Intent(this, Donate.class));
			break;
		case -4:
			Log.i(TAG, "settings");
			this.startActivity(new Intent(this, Settings.class));
			break;
		case -5:
			Log.i(TAG, "exit");
			stopPlayAndRecord();
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
