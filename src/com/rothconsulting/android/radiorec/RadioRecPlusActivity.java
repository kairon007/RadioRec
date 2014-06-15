package com.rothconsulting.android.radiorec;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.MediaRouteActionProvider;
import android.support.v7.media.MediaRouter;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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

import com.rothconsulting.android.billing.util.RadioRecBillingHelper;
import com.rothconsulting.android.cast.CastHelper;
import com.rothconsulting.android.common.AdMob;
import com.rothconsulting.android.common.AnalyticsUtil;
import com.rothconsulting.android.common.Utils;
import com.rothconsulting.android.radiorec.filechooser.FileChooserActivity;
import com.rothconsulting.android.radiorec.sqlitedb.DbAdapter;
import com.rothconsulting.android.radiorec.sqlitedb.DbUtils;

public class RadioRecPlusActivity extends ActionBarActivity implements OnClickListener, OnItemSelectedListener, OnSeekBarChangeListener, ActionBar.TabListener {

	private static final String TAG = "RadioRecPlusActivity";

	public static boolean playing;
	public static boolean recording;
	private static boolean showCountdown;
	private Context context;
	// private Activity activity;
	private int origOrientation;
	private boolean isOrientationSensorOn;

	private boolean firstStart;
	private Spinner spnAlphabetisch;
	private Spinner spnAllStations;
	private ArrayList<HashMap<String, Object>> stationList;
	private ArrayList<HashMap<String, Object>> alphabeticList;
	private ImageView logo;
	private TextView favIcon;
	private ImageButton buttonBack, buttonFwd, buttonRec, buttonPlay;
	private static RadioPlayer radioPlayer;
	private static AsyncTask<URL, Integer, Long> recordTask;
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

	private SimpleAdapter allStationAdapter;

	private TelephonyManager tm = null;
	private PhoneStateListener callStateListener;

	// Chromecast
	private CastHelper castHelper;

	public Spinner getStations() {
		return spnAllStations;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Utils.log(TAG, "++++++++++++ onCreate START ++++++++++++");
		Utils.getPreferences(this);

		if (Constants.ROTATION_OFF_VALUE) {
			// Prevent from Rotation
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		context = this;
		// context.deleteDatabase(DBHelper.DATABASE_NAME);
		gcCounter = 0;
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT < 7) {
			context.deleteDatabase("webview.db");
			context.deleteDatabase("webviewCache.db");
		}

		// // Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// For each of the sections in the app, add a tab to the action bar.
		actionBar.addTab(actionBar.newTab().setText(R.string.stations).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.favoriten).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.recordings).setTabListener(this));

		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		RadioRecBillingHelper.isDonator();
		initGui();

		actionBar.setListNavigationCallbacks(allStationAdapter, null);

		if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_SENSOR) {
			isOrientationSensorOn = true;
		} else {
			isOrientationSensorOn = false;
		}

		AnalyticsUtil.sendScreen("Main screen");

		// Detect incoming phone call and register PhoneStateListener
		callStateListener = new CallStateListener();
		tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);

		// Chromecast initialize
		castHelper = new CastHelper(this);

		Utils.log(TAG, "++++++++++++ onCreate END ++++++++++++");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Utils.log(TAG, "--- onResume()");
		castHelper.mMediaRouter.addCallback(castHelper.mMediaRouteSelector, castHelper.mediaRouterCallback, MediaRouter.CALLBACK_FLAG_PERFORM_ACTIVE_SCAN);
		hideSearch();
	}

	@Override
	protected void onPause() {
		Utils.log(TAG, "--- onPause()");
		if (isFinishing()) {
			Utils.log(TAG, "isFinishing -> removeCallback");
			castHelper.mMediaRouter.removeCallback(castHelper.mediaRouterCallback);
		} else {
			Utils.log(TAG, "is NOT Finishing -> doing nothing");
		}
		super.onPause();
	}

	@Override
	protected void onStop() {
		Utils.log(TAG, "--- onStop()");
		super.onStop();
	}

	@Override
	protected void onRestart() {
		Utils.log(TAG, "--- onRestart()");
		super.onRestart();
	}

	private void initGui() {
		Utils.log(TAG, "++++++++++++ START initGui ++++++++++++");
		setContentView(R.layout.main);
		firstStart = true;
		// get components and register clicks
		spnAllStations = (Spinner) findViewById(R.id.stations);
		spnAllStations.setBackgroundColor(getResources().getColor(android.R.color.white));
		spnAlphabetisch = (Spinner) findViewById(R.id.spinnerAlphabetisch);
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
		buttonPlay.setImageResource(playing ? R.drawable.button_stop : R.drawable.button_play);
		buttonRec = (ImageButton) findViewById(R.id.rec);
		buttonRec.setOnClickListener(this);
		buttonRec.setImageResource(recording ? R.drawable.button_record_on : R.drawable.button_record);

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
			logo.setImageBitmap(Images.addReflection(BitmapFactory.decodeResource(getResources(), Constants.SELECTED_STATION_ICON_VALUE, options), 0));
		} catch (Exception e) {
			logo.setImageBitmap(Images.addReflection(BitmapFactory.decodeResource(getResources(), R.drawable.radio_32, options), 0));
		}

		Utils.log(TAG, "Icon=" + Constants.SELECTED_STATION_ICON_VALUE);
		Utils.log(TAG, "Name=" + Constants.SELECTED_STATION_NAME_VALUE);
		Utils.log(TAG, "Index=" + Constants.SELECTED_STATION_INDEX_VALUE);

		AdMob admob = new AdMob();
		admob.showRemoveAds(this);

		Utils.log(TAG, "getAllStations()");
		stationList = Stations.getAllStations();

		setFavIconStar();

		alphabeticList = Utils.sortStationsByName(stationList);
		SimpleAdapter alphabetischAdapter = new SimpleAdapter(this, alphabeticList, R.layout.station_listitem, new String[] { Stations.ICON_SMALL,
				Stations.NAME }, new int[] { R.id.option_icon, R.id.option_text });
		spnAlphabetisch.setAdapter(alphabetischAdapter);

		allStationAdapter = new SimpleAdapter(this, stationList, R.layout.station_listitem, new String[] { Stations.ICON_SMALL, Stations.NAME }, new int[] {
				R.id.option_icon, R.id.option_text });
		spnAllStations.setAdapter(allStationAdapter);

		hideSearch();

		spnAlphabetisch.setOnItemSelectedListener(this);
		spnAllStations.setOnItemSelectedListener(this);

		if (Constants.ROTATION_OFF_VALUE) {
			// Prevent from Rotation
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		}
		Utils.log(TAG, "++++++++++++ END initGui ++++++++++++++");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (playing) {
				showDialog(Constants.PRESS_BACK_BUTTON);
				return true;
			} else {
				stopPlayAndRecord();
				// Unregister PhoneStateListener
				if (tm != null) {
					tm.listen(callStateListener, PhoneStateListener.LISTEN_NONE);
				}
				finish();
			}
		}
		Notifications.getNotifInstance(this, RadioRecPlusActivity.class).hideStatusBarNotification(Constants.NOTIFICATION_ID_ERROR_CONNECTION);
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Constants.PRESS_BACK_BUTTON:
			final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
			alertBuilder.setMessage(this.getResources().getString(R.string.willstDuWeiterHoeren)).setCancelable(true)
					.setPositiveButton(this.getResources().getString(R.string.wegDamit), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog, final int id) {
							stopPlay();
							stopRecord();
							finish();
						}
					}).setNegativeButton(this.getResources().getString(R.string.weiterHoeren), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog, final int id) {
							moveTaskToBack(true);
							// Move the task containing this activity to the back of the activity stack. The
							// activity's order within the task is unchanged.
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
			if (Constants.URL_HOMEPAGE_VALUE != null && !Constants.URL_HOMEPAGE_VALUE.trim().equals("")) {
				AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "click_homepage", "url: " + Constants.URL_HOMEPAGE_VALUE);

				Uri uri = Uri.parse(Constants.URL_HOMEPAGE_VALUE);
				Intent intentHomepage = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intentHomepage);
			}
			break;
		case R.id.webcam:
			Log.i(TAG, "webcam");
			AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "click_webcam", "url: " + Constants.URL_WEBCAM_VALUE);

			Intent intentCam = new Intent(this, WebcamActivity.class);
			startActivity(intentCam);
			break;
		case R.id.mail:
			Log.i(TAG, "mail");
			AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "click_mail", "contact: " + Constants.URL_CONTACT_VALUE);

			if (Constants.URL_CONTACT_VALUE.startsWith("http")) {
				Intent emailIntent = new Intent(Intent.ACTION_VIEW);
				emailIntent.setData(Uri.parse(Constants.URL_CONTACT_VALUE));
				startActivity(emailIntent);
			} else {
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { Constants.URL_CONTACT_VALUE });
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Mein Wunsch");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
				startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			}
			break;
		case R.id.search:
			Log.i(TAG, "search");
			AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "click_search", "selected_station: " + Constants.SELECTED_STATION_NAME_VALUE);

			prepareSearch();
			break;

		case R.id.favoriten:
			Log.i(TAG, "favorit");
			AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "click_favoriten", "station: " + Constants.SELECTED_STATION_NAME_VALUE);
			DbUtils.storeRemoveFav(this, favIcon);
			break;
		case R.id.back:
			if (spnAllStations.getSelectedItemPosition() > 0) {
				buttonBack.setEnabled(false);
				spnAllStations.setSelection(spnAllStations.getSelectedItemPosition() - 1);
				Constants.SELECTED_STATION_INDEX_VALUE = spnAllStations.getSelectedItemPosition();
				Utils.log(TAG, "back");
			}
			break;
		case R.id.play:
			playing = !playing;
			Utils.log(TAG, "playing: " + playing);
			if (playing && !Utils.isNetworkAvailable(this, getIntent(), true)) {
				playing = false;
			}
			((ImageButton) v).setImageResource(playing ? R.drawable.button_stop : R.drawable.button_play);
			origOrientation = Utils.getScrOrientation(this);
			if (!playing && isOrientationSensorOn) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
			}
			Utils.log(TAG, "playing: " + playing);
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

			// Google analytics
			if (recording) {
				AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "start_recording", "station: " + Constants.SELECTED_STATION_NAME_VALUE);
			}
			// Google analytics
			if (!recording) {
				AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "stop_recording", "station: " + Constants.SELECTED_STATION_NAME_VALUE);
			}

			((ImageButton) v).setImageResource(recording ? R.drawable.button_record_on : R.drawable.button_record);
			break;
		case R.id.fwd:
			if (spnAllStations.getSelectedItemPosition() < stationList.size() - 1) {
				buttonFwd.setEnabled(false);
				spnAllStations.setSelection(spnAllStations.getSelectedItemPosition() + 1);
				Constants.SELECTED_STATION_INDEX_VALUE = spnAllStations.getSelectedItemPosition();
				Utils.log(TAG, "fwd");
			}
			break;
		case R.id.imageButtonUhr:
			showCountdown = !showCountdown;
			AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "click_imageButtonUhr", "station: " + Constants.SELECTED_STATION_NAME_VALUE);
			this.showTimerbox(showCountdown);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == Constants.FROM_FAVOURITES) {

			if (resultCode == RESULT_OK) {
				String stationName = data.getStringExtra("stationName");
				// Toast.makeText(context, "Station: " + stationName, Toast.LENGTH_LONG).show();
				Constants.SELECTED_STATION_INDEX_VALUE = Utils.getSpinnerPosition(Stations.getAllStations(), stationName);
				// .setSelection starts the method onItemSelected(...) and starts it all over
				spnAllStations.setSelection(Constants.SELECTED_STATION_INDEX_VALUE);
				// changeStation();
			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
			}
		}
	}// onActivityResult

	@Override
	public void onItemSelected(AdapterView<?> spinner, View linearLayout, int selection, long arg3) {
		Utils.log(TAG, "++ onItemSelected START");

		Utils.log(TAG, "****************");
		Utils.log(TAG, "onItemSelected(AdapterView<?> arg0 = " + spinner);
		Utils.log(TAG, "onItemSelected(View arg1 = " + linearLayout);
		Utils.log(TAG, "onItemSelected(int selection = " + selection);
		Utils.log(TAG, "onItemSelected(long arg3 = " + arg3);
		Utils.log(TAG, "spinner.getSelectedItemPosition1=" + spinner.getSelectedItemPosition());
		Utils.log(TAG, "spnAllStations.getSelectedItemPosition1=" + spnAllStations.getSelectedItemPosition());
		Utils.log(TAG, "** Fav clicked: " + Constants.SPINNER_SELECTION);
		Utils.log(TAG, "SELECTED_STATION_INDEX_VALUE1=" + Constants.SELECTED_STATION_INDEX_VALUE);
		Utils.log(TAG, "****************");

		if (firstStart) {
			Utils.log(TAG, "firstStart -> arg0.setSelection(SELECTED_STATION_INDEX_VALUE)");

			if (Constants.SELECTED_STATION_INDEX_VALUE > spinner.getAdapter().getCount()) {
				Constants.SELECTED_STATION_INDEX_VALUE = 0;
			}

			// if (Constants.SELECTED_STATION_INDEX_VALUE > stationList.size())
			// {
			// Constants.SELECTED_STATION_INDEX_VALUE = 0;
			// }
			spinner.setSelection(Constants.SELECTED_STATION_INDEX_VALUE);
		} else {
			Constants.SELECTED_STATION_INDEX_VALUE = spinner.getSelectedItemPosition();
		}
		Utils.log(TAG, "getSelectedItemPosition2=" + spinner.getSelectedItemPosition());
		Utils.log(TAG, "SELECTED_STATION_INDEX2=" + Constants.SELECTED_STATION_INDEX_VALUE);

		changeStation();

		Utils.log(TAG, "++ onItemSelected STOP");

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		Utils.log(TAG, "+++ onNothingSelected -------------------------------------------------");
	}

	private void changeStation() {
		Utils.log(TAG, "++ changeStation START");
		int index = Constants.SELECTED_STATION_INDEX_VALUE;
		// Check index size
		if (index < 0 || index >= stationList.size()) {
			index = 0;
			Constants.SELECTED_STATION_INDEX_VALUE = index;
		}

		Utils.log(TAG, "index=" + index + " / stationList.size()=" + stationList.size());

		HashMap<String, Object> map = null;
		Utils.log(TAG, "++ changeStation Constants.SPINNER_SELECTION= " + Constants.SPINNER_SELECTION);

		if (Constants.SPINNER_SELECTION == Constants.SPINNER_ALPHABETISCH && alphabeticList != null && alphabeticList.size() >= index) {
			map = alphabeticList.get(index);
			// .setSelection starts the method onItemSelected(...) and starts it all over
			spnAllStations.setSelection(Utils.getSpinnerPosition(stationList, (String) map.get(Stations.NAME)));
			Constants.SPINNER_SELECTION = Constants.SPINNER_ALL_STATIONS;
			return;
		} else {
			map = stationList.get(index);
		}

		spnAllStations.setSelection(Utils.getSpinnerPosition(stationList, (String) map.get(Stations.NAME)));

		Utils.log(TAG, "!!! Sender=" + Constants.SELECTED_STATION_NAME_VALUE);
		Constants.SELECTED_STATION_ICON_VALUE = (Integer) map.get(Stations.ICON);
		Constants.SELECTED_STATION_ICON_SMALL_VALUE = (Integer) map.get(Stations.ICON_SMALL);

		// avoiding OutOfMemory
		if (gcCounter % 2 == 0) { // call gc only at even numbers
			System.gc();
			gcCounter++;
		}
		// http://stackoverflow.com/questions/477572/android-strange-out-of-memory-issue
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[16 * 1024];

		logo.setImageBitmap(Images.addReflection(BitmapFactory.decodeResource(getResources(), Constants.SELECTED_STATION_ICON_VALUE, options), 0));
		Utils.log(TAG, "*********** Stream=" + map.get(Stations.STREAM));

		Constants.SELECTED_STATION_NAME_VALUE = "" + map.get(Stations.NAME);

		AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "playing", "station: " + Constants.SELECTED_STATION_NAME_VALUE);

		Constants.URL_LIVE_STREAM_VALUE = "" + map.get(Stations.STREAM);

		Constants.URL_HOMEPAGE_VALUE = "" + map.get(Stations.HOMEPAGE);
		Constants.URL_WEBCAM_VALUE = "" + map.get(Stations.WEBCAM);
		showHideCam();
		Constants.URL_CONTACT_VALUE = "" + map.get(Stations.EMAIL);

		setFavIconStar();

		Utils.log(TAG, "castHelper.isConnected() = " + castHelper.isConnected());
		if (castHelper.isConnected() && !Constants.SELECTED_STATION_NAME_VALUE.equalsIgnoreCase(Stations.RADIO_JUGGLERZ)) {
			handleCast((Integer) map.get(Stations.ICON));
		} else {
			if (!firstStart && playing) {
				if (Constants.getLiveStreamStations().contains(Constants.SELECTED_STATION_NAME_VALUE)) {
					Utils.log(TAG, "------ ist Fussball/Live Radio");
					showDialog(Constants.LIVE_STREAM_STATION);
				}
				if (Constants.SELECTED_STATION_NAME_VALUE.equalsIgnoreCase(Stations.RADIO_JUGGLERZ)) {
					// jugglerz.de hat immer Donnerstags eine Live Sendung.
					// Ab Freitag kann man diese als mp3 hören. Daher ist die URL dynamisch.
					WebTool webtool = new WebTool();
					Constants.URL_LIVE_STREAM_VALUE = Constants.URL_LIVE_STREAM_VALUE + webtool.getJugglerzFileName(this);
					Utils.log(TAG, "*********** new Stream=" + Constants.URL_LIVE_STREAM_VALUE);
				}
				if (castHelper.isConnected()) {
					handleCast((Integer) map.get(Stations.ICON));
				} else {
					getRadioPlayer().doStartPlay(this);
					// getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				}

			} else {
				this.stopPlay();
			}
		}

		buttonBack.setEnabled(index > 0);
		buttonFwd.setEnabled(index < stationList.size() - 1);

		firstStart = false;
		Constants.SPINNER_SELECTION = Constants.SPINNER_ALL_STATIONS;

		Utils.storePreferences(this);

		Utils.log(TAG, "++ changeStation STOP");
	}

	private void handleCast(int imageResId) {
		Utils.log(TAG, "Playing: " + playing);
		// stop local playing in all cases
		getRadioPlayer().doStopPlay(this);

		if (playing) {
			Utils.log(TAG, "castHelper.play(...)");
			// if local player is playing - stop it
			castHelper.play(Constants.SELECTED_STATION_NAME_VALUE, Constants.URL_LIVE_STREAM_VALUE, Utils.getCastImageUrl(imageResId));

			Utils.log(TAG, "castHelper.play(...) - show statusbar info");
			Intent intent = new Intent(context, DonateActivity.class);
			intent.putExtra(Constants.FROM_NOTIFICATION, Constants.FROM_NOTIFICATION);
			Notifications notification = new Notifications(context, intent);
			notification.hideStatusBarNotification(Constants.NOTIFICATION_ID_ERROR_CONNECTION);
			notification.showStatusBarNotificationIsRunning(true);
		} else {
			Utils.log(TAG, "Cast pause()");
			castHelper.pause();
		}
	}

	protected static RadioPlayer getRadioPlayer() {
		if (radioPlayer == null) {
			radioPlayer = new RadioPlayer();
		}
		return radioPlayer;
	}

	private boolean doStartRecording() {
		Utils.log(TAG, "startRecording");

		if (!Utils.isNetworkAvailable(this, getIntent(), true)) {
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
			Utils.log(TAG, "Constants.URL_LIVE_STREAM_VALUE=" + Constants.URL_LIVE_STREAM_VALUE);
			inputUrl = new URL(Constants.URL_LIVE_STREAM_VALUE);
		} catch (MalformedURLException e) {
			Notifications.getNotifInstance(this, RadioRecPlusActivity.class).showStatusBarNotificationError(R.string.internetadresseNichtErreichbar);
		}

		String filename = "Unknown";
		if (Constants.SELECTED_STATION_NAME_VALUE != null) {
			filename = Constants.SELECTED_STATION_NAME_VALUE.replaceAll(" ", "");
		}
		try {
			outputUrl = new URL("file:///" + Constants.SD_CARD_PATH_VALUE + getSlash() + filename.replaceAll("/", "") + "-" + dateTime + ".mp3");
		} catch (MalformedURLException e) {
			Notifications.getNotifInstance(this, RadioRecPlusActivity.class).showStatusBarNotificationError(R.string.kannNichtAufSdCardSchreiben);
		}

		recordTask = new RadioRecorder(this, this.getIntent()).execute(inputUrl, outputUrl);
		Utils.log(TAG, "*********** isRadioRecording2=" + recording);

		return recording;
	}

	private String getSlash() {
		if (Constants.SD_CARD_PATH_VALUE != null && Constants.SD_CARD_PATH_VALUE.trim().endsWith("/")) {
			return "";
		} else {
			return "/";
		}

	}

	protected static void doStopRecording(Context context) {
		if (recordTask != null) {
			recordTask.cancel(true);
			Notifications.getNotifInstance(context, RadioRecPlusActivity.class).hideStatusBarNotification(Constants.NOTIFICATION_ID_RECORDING);
			recording = false;
		}
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
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
		timerSeekbarText.setText(getString(R.string.sleepTimerEndIn, Utils.getHhMmFromMinutes(progress)));
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
				timerSeekbarText.setText(getString(R.string.sleepTimerEndIn, Utils.getHhMmSs(millisUntilFinished)));
				countDownTimerTick = Integer.valueOf("" + (millisUntilFinished / 60 / 1000));
				setSeekBarProgress(countDownTimerTick);
				Utils.log(TAG, "countDownTimerTick=" + countDownTimerTick);
			}

			@Override
			public void onFinish() {
				Toast.makeText(context, R.string.timerEnd, Toast.LENGTH_LONG).show();
				showCountdown = true;
				timerSeekbarText.setText(getString(R.string.sleepTimerEinstellen));
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
		if (Constants.ROTATION_OFF_VALUE) {
			// Prevent from Rotation
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			return;
		}
		if (!playing) {
			initGui();
			setSeekBarProgress(countDownTimerTick);
			mainScreen.refreshDrawableState();
			showCountdown = false;
		} else {
			if (origOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			} else {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
		}
	}

	public boolean isPlaying() {
		return playing;
	}

	private void stopPlayAndRecord() {
		stopPlay();
		stopRecord();
	}

	public void stopPlay() {
		getRadioPlayer().doStopPlay(this);
		playing = Boolean.FALSE;
		((ImageButton) findViewById(R.id.play)).setImageResource(R.drawable.button_play);
	}

	private void stopRecord() {
		doStopRecording(this);
		recording = Boolean.FALSE;
		((ImageButton) findViewById(R.id.rec)).setImageResource(R.drawable.button_record);
	}

	private void startPlay() {
		getRadioPlayer().doStartPlay(this);
		playing = Boolean.TRUE;
		((ImageButton) findViewById(R.id.play)).setImageResource(R.drawable.button_stop);
	}

	private void showHideCam() {
		final TextView textViewWebcam = (TextView) findViewById(R.id.webcam);
		if (Build.VERSION.SDK_INT < 5) {
			textViewWebcam.setVisibility(View.GONE);
		} else if (Constants.URL_WEBCAM_VALUE == null || Constants.URL_WEBCAM_VALUE.trim().equals("")) {
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
			public void onItemClick(AdapterView<?> arg0, View textView, int arg2, long arg3) {

				imm.hideSoftInputFromWindow(myAutoComplete.getWindowToken(), 0);
				autocomplete.setVisibility(View.GONE);
				spinner.setVisibility(View.VISIBLE);

				AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "click_searched_station", "station: " + ((TextView) textView).getText());

				spnAllStations.setSelection(Utils.getSpinnerPosition(stationList, "" + ((TextView) textView).getText()));
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

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);

			List<String> stationNameList = Utils.getStationNameList(this.stationList, null);
			for (String stationName : stationNameList) {
				adapter.add(stationName);
			}
			myAutoComplete.setAdapter(adapter);
			myAutoComplete.requestFocus();
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		}
	}

	private void hideSearch() {
		Utils.log(TAG, "++ hideSearch START");

		autocomplete.setVisibility(View.GONE);
		spinner.setVisibility(View.VISIBLE);
		// hide keyboard
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		Utils.log(TAG, "++ hideSearch STOP");

	}

	// ------------------------------------------------------------
	// Menu Stuff
	// ------------------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_main, menu);

		// Chromecast
		if (Utils.isGooglePlayServicesAvailable(context)) {
			MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);
			MediaRouteActionProvider mediaRouteActionProvider = (MediaRouteActionProvider) MenuItemCompat.getActionProvider(mediaRouteMenuItem);
			mediaRouteActionProvider.setRouteSelector(castHelper.mMediaRouteSelector);
		}

		if (Utils.hasValidKey()) {
			menu.removeItem(R.id.action_donate);
		}

		return super.onCreateOptionsMenu(menu);
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_info:
			Log.i(TAG, "info");
			this.startActivity(new Intent(this, InfoActivity.class));
			break;
		case R.id.action_alphabetisch:
			Log.i(TAG, "menu alphabetisch");
			Constants.SPINNER_SELECTION = Constants.SPINNER_ALPHABETISCH;
			AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "click_menu_alphabetisch", "actual station: " + Constants.SELECTED_STATION_NAME_VALUE);
			Utils.log(TAG, "Button Alphabetisch pressed. Firts=" + spnAlphabetisch.getFirstVisiblePosition());
			spnAlphabetisch.performClick();
			break;
		case R.id.action_donate:
			Log.i(TAG, "spende");
			this.startActivity(new Intent(this, DonateActivity.class));
			break;
		case R.id.action_settings:
			Log.i(TAG, "settings");
			this.startActivity(new Intent(this, SettingsActivity.class));
			break;
		case R.id.action_end:
			Log.i(TAG, "exit");
			stopPlayAndRecord();
			// Unregister PhoneStateListener
			if (tm != null) {
				tm.listen(callStateListener, PhoneStateListener.LISTEN_NONE);
			}
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Listener to detect incoming calls.
	 */
	private class CallStateListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				// called when someone is ringing to this phone
				Toast.makeText(context, getString(R.string.incomingCall) + " \n" + incomingNumber, Toast.LENGTH_LONG).show();
				// stop playing
				stopPlay();
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				// called when starting a phone call from this device
				Toast.makeText(context, "Stoping RadioRec+", Toast.LENGTH_LONG).show();
				// stop playing
				stopPlay();
				break;
			}
		}
	}

	/**
	 * 
	 */
	private void setFavIconStar() {
		Utils.log(TAG, "setFavIconStar START");
		DbAdapter dbadapter = new DbAdapter(this);
		dbadapter.open();
		Cursor cursor = null;
		cursor = dbadapter.fetchStation(Constants.SELECTED_STATION_NAME_VALUE);
		if (cursor != null && cursor.getCount() > 0) {
			Utils.log(TAG, "favIcon ON");
			favIcon.setTag(Constants.FAV_ON);
			favIcon.setCompoundDrawablesWithIntrinsicBounds(0, android.R.drawable.star_big_on, 0, 0);
		} else {
			Utils.log(TAG, "favIcon OFF");
			favIcon.setTag(Constants.FAV_OFF);
			favIcon.setCompoundDrawablesWithIntrinsicBounds(0, android.R.drawable.star_big_off, 0, 0);
		}
		cursor.close();
		dbadapter.close();
		Utils.log(TAG, "setFavIconStar STOP");
	}

	// ------------------------------------------
	// Tabs
	// ------------------------------------------
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		Utils.log(TAG, "------- onTabReselected: " + (tab.getPosition()));
		performTabClick(tab);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// Toast.makeText(this, "Clicked: " + (tab.getPosition() + 1), Toast.LENGTH_SHORT).show();
		Utils.log(TAG, "------- onTabSelected: " + (tab.getPosition()));
		performTabClick(tab);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		Utils.log(TAG, "------- onTabUnselected: " + (tab.getPosition()));
	}

	private void performTabClick(Tab tab) {
		switch (tab.getPosition()) {
		case 0:
			if (spnAllStations != null) {
				spnAllStations.performClick();
			}
			break;
		case 1:
			AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "click_tab_favourites", "station: " + Constants.SELECTED_STATION_NAME_VALUE);
			this.startActivityForResult(new Intent(this, FavouritesActivity.class), Constants.FROM_FAVOURITES);
			break;
		case 2:
			AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "click_musicBrowser", "station: " + Constants.SELECTED_STATION_NAME_VALUE);
			this.startActivity(new Intent(this, FileChooserActivity.class));

			// Fragments
			// FragmentManager fm = getSupportFragmentManager();
			// FileChooserFragment fileList = new FileChooserFragment();
			// fm.beginTransaction().replace(android.R.id.content, fileList).commit();

			break;
		default:
			break;
		}
	}

	// --------------------------------------------------
	// Chromecast
	// --------------------------------------------------
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (castHelper.isConnected() && playing) {
			int action = event.getAction();
			int keyCode = event.getKeyCode();
			switch (keyCode) {
			case KeyEvent.KEYCODE_VOLUME_UP:
				if (action == KeyEvent.ACTION_UP) {
					castHelper.volumeUp();
				}
				return true;
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				if (action == KeyEvent.ACTION_DOWN) {
					castHelper.volumeDown();
				}
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}
}
