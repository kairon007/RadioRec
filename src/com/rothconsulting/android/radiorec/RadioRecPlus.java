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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
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

public class RadioRecPlus extends Activity implements OnClickListener,
		OnItemSelectedListener, OnSeekBarChangeListener {

	static final String TAG = "RadioRecPlus";

	static boolean playing;
	static boolean recording;
	static boolean showCountdown;
	private Context context;

	private boolean firstStart;
	private Spinner spnAllStations;
	// private Spinner spnFavoriten, spnLaender, spnStil;
	private ArrayList<HashMap<String, Object>> stationList;
	// private ArrayList<HashMap<String, Object>> favList, landList, stilList;
	private ImageView logo;
	private ImageButton back, fwd;
	private static RadioPlayer radioPlayer;
	private static AsyncTask<URL, Integer, Long> recordTask;
	private String origPlanetradioSteam = null;
	// private ToggleButton favIcon = null;
	private LinearLayout mainScreen;
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

	private void initGui() {
		Utils.log(TAG, "RadioRePlus");
		setContentView(R.layout.main);
		firstStart = true;
		Utils utils = new Utils();
		utils.getPreferences(this);
		// get components and register clicks
		spnAllStations = (Spinner) findViewById(R.id.stations);
		Constants.SPINNER_ALL_STATIONS = spnAllStations.getId();
		// spnFavoriten = (Spinner) findViewById(R.id.spinnerFav);
		// Constants.SPINNER_FAVORITEN = spnFavoriten.getId();
		// spnLaender = (Spinner) findViewById(R.id.spinnerLand);
		// Constants.SPINNER_LAENDER = spnLaender.getId();
		// spnStil = (Spinner) findViewById(R.id.spinnerStil);
		// Constants.SPINNER_STIL = spnStil.getId();
		// ((Button) findViewById(R.id.buttonFav)).setOnClickListener(this);
		// ((Button) findViewById(R.id.buttonLand)).setOnClickListener(this);
		// ((Button) findViewById(R.id.buttonStil)).setOnClickListener(this);
		// favIcon = (ToggleButton) findViewById(R.id.toggleButtonFavorit);
		// favIcon.setOnClickListener(this);
		mainScreen = (LinearLayout) findViewById(R.id.mainScreen);
		timerLayoutWhole = (RelativeLayout) findViewById(R.id.timerLayoutWhole);
		timerSeekbarLayout = (RelativeLayout) findViewById(R.id.timerSeekbarLayout);
		timerSeekbar = (SeekBar) findViewById(R.id.timerSeekbar);
		timerSeekbar.setOnSeekBarChangeListener(this);
		timerSeekbarText = (TextView) findViewById(R.id.timerSeekbarText);
		imageButtonUhr = (ImageButton) findViewById(R.id.imageButtonUhr);
		imageButtonUhr.setOnClickListener(this);

		logo = (ImageView) findViewById(R.id.logo);
		((TextView) findViewById(R.id.homepage)).setOnClickListener(this);
		((TextView) findViewById(R.id.webcam)).setOnClickListener(this);
		((TextView) findViewById(R.id.mail)).setOnClickListener(this);
		back = (ImageButton) findViewById(R.id.back);
		back.setOnClickListener(this);
		back.setEnabled(false);
		((ImageButton) findViewById(R.id.play)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.rec)).setOnClickListener(this);
		fwd = (ImageButton) findViewById(R.id.fwd);
		fwd.setOnClickListener(this);
		// set first image
		if (Constants.THE_SELECTED_STATION_ICON == 0x0) {
			Constants.THE_SELECTED_STATION_ICON = R.drawable.radio_32;
		}
		if (Constants.THE_SELECTED_STATION_ICON_SMALL == 0x0) {
			Constants.THE_SELECTED_STATION_ICON_SMALL = R.drawable.radio_32_small;
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

		Utils.log(TAG, "Icon=" + Constants.THE_SELECTED_STATION_ICON);
		Utils.log(TAG, "Name=" + Constants.THE_SELECTED_STATION_NAME);
		Utils.log(TAG, "Index=" + Constants.THE_SELECTED_STATION_INDEX);

		AdMob admob = new AdMob();
		admob.showRemoveAds(this);

		Utils.log(TAG, "getAllStations()");
		Stations theStations = new Stations();
		stationList = theStations.getAllStations();
		// landList = theStations.getLandListCh();
		// landList.addAll(theStations.getLandListDe());
		// landList.addAll(theStations.getLandListAt());
		// landList.addAll(theStations.getLandListDiv());
		//
		// stilList = theStations.getStilPop();
		// stilList.addAll(theStations.getStilPop());
		//
		// SimpleAdapter laenderAdapter = new SimpleAdapter(this, landList,
		// R.layout.station_listitem,
		// new String[] { "icon_small", "name" }, new int[] {
		// R.id.option_icon, R.id.option_text });
		// spnLaender.setAdapter(laenderAdapter);
		// spnLaender.setOnItemSelectedListener(this);
		//
		// SimpleAdapter stilAdapter = new SimpleAdapter(this, stilList,
		// R.layout.station_listitem,
		// new String[] { "icon_small", "name" }, new int[] {
		// R.id.option_icon, R.id.option_text });
		// spnStil.setAdapter(stilAdapter);
		// spnStil.setOnItemSelectedListener(this);

		SimpleAdapter adapter = new SimpleAdapter(this, stationList,
				R.layout.station_listitem,
				new String[] { "icon_small", "name" }, new int[] {
						R.id.option_icon, R.id.option_text });
		// spnAllStations.setBackgroundColor(Color.LTGRAY);
		spnAllStations.setAdapter(adapter);
		spnAllStations.setOnItemSelectedListener(this);

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
						@Override
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

	@Override
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
		// case R.id.toggleButtonFavorit:
		// Log.i(TAG, "favorit");
		// DbAdapter dbadapter = new DbAdapter(this);
		// if (favIcon.isChecked()) {
		// favIcon.setButtonDrawable(android.R.drawable.star_big_on);
		// Utils.log(TAG, "isChecked -> instertStation");
		// dbadapter.open();
		// dbadapter.insertStation(Constants.THE_SELECTED_STATION_ICON,
		// Constants.THE_SELECTED_STATION_ICON_SMALL,
		// Constants.THE_SELECTED_STATION_NAME,
		// Constants.THE_URL_LIVE_STREAM,
		// Constants.THE_URL_HOMEPAGE, Constants.THE_URL_WEBCAM,
		// Constants.THE_URL_CONTACT, true, Stations.LAND_CH,
		// Stations.SPRACHE_DE, Stations.STIL_POP);
		// dbadapter.close();
		// } else {
		// favIcon.setButtonDrawable(android.R.drawable.star_big_off);
		// Utils.log(TAG, "is NOT Checked -> deleteStation");
		// dbadapter.open();
		// dbadapter.deleteStation(Constants.THE_SELECTED_STATION_NAME);
		// dbadapter.close();
		// }
		// break;
		case R.id.back:
			if (spnAllStations.getSelectedItemPosition() > 0) {
				back.setEnabled(false);
				spnAllStations.setSelection(spnAllStations
						.getSelectedItemPosition() - 1);
				Constants.THE_SELECTED_STATION_INDEX = spnAllStations
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
				fwd.setEnabled(false);
				spnAllStations.setSelection(spnAllStations
						.getSelectedItemPosition() + 1);
				Constants.THE_SELECTED_STATION_INDEX = spnAllStations
						.getSelectedItemPosition();
				Utils.log(TAG, "fwd");
			}
			break;
		case R.id.imageButtonUhr:
			showCountdown = !showCountdown;
			this.showTimerbox(showCountdown);
			break;
		// case R.id.buttonFav:
		// Utils.log(TAG,
		// "Button Favoriten pressed. First="
		// + spnFavoriten.getFirstVisiblePosition());
		//
		// dbadapter = new DbAdapter(this);
		// dbadapter.open();
		// Cursor cursor = null;
		// cursor = dbadapter.fetchAllStations();
		// favList = new ArrayList<HashMap<String, Object>>();
		//
		// if (cursor != null && cursor.getCount() > 0) {
		//
		// cursor.moveToFirst();
		//
		// do {
		//
		// String name = cursor.getString(cursor
		// .getColumnIndex(DbAdapter.KEY_STATION_NAME));
		// int icon = cursor.getInt(cursor
		// .getColumnIndex(DbAdapter.KEY_STATION_ICON));
		// int iconSmall = cursor.getInt(cursor
		// .getColumnIndex(DbAdapter.KEY_STATION_ICON_SMALL));
		// String stream = cursor.getString(cursor
		// .getColumnIndex(DbAdapter.KEY_STATION_STEAM));
		// String homepage = cursor.getString(cursor
		// .getColumnIndex(DbAdapter.KEY_STATION_HOMEPAGE));
		// String webcam = cursor.getString(cursor
		// .getColumnIndex(DbAdapter.KEY_STATION_WEBCAM));
		// String contact = cursor.getString(cursor
		// .getColumnIndex(DbAdapter.KEY_STATION_CONTACT));
		// String sprache = cursor.getString(cursor
		// .getColumnIndex(DbAdapter.KEY_LANGUAGE));
		// String land = cursor.getString(cursor
		// .getColumnIndex(DbAdapter.KEY_COUNTRY));
		// String stil = cursor.getString(cursor
		// .getColumnIndex(DbAdapter.KEY_GENRE));
		//
		// HashMap<String, Object> map = utils.fillStationHashMap(
		// name, icon, iconSmall, stream, homepage, webcam,
		// contact, sprache, land, stil);
		//
		// favList.add(map);
		// } while (cursor.moveToNext());
		//
		// }
		// cursor.close();
		// dbadapter.close();
		//
		// SimpleAdapter favoritesAdapter = new SimpleAdapter(this, favList,
		// R.layout.station_listitem, new String[] { "icon_small",
		// "name" }, new int[] { R.id.option_icon,
		// R.id.option_text });
		// spnFavoriten.setAdapter(favoritesAdapter);
		// spnFavoriten.setOnItemSelectedListener(this);
		// spnFavoriten.performClick();
		//
		// break;
		// case R.id.buttonLand:
		// Utils.log(TAG,
		// "Button Land pressed. Firts="
		// + spnLaender.getFirstVisiblePosition());
		//
		// spnLaender.performClick();
		// break;
		// case R.id.buttonStil:
		// Utils.log(TAG,
		// "Button Stil pressed. Firts="
		// + spnStil.getFirstVisiblePosition());
		//
		// spnStil.performClick();
		// break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Utils.log(TAG, "****************");
		Utils.log(TAG, "onItemSelected(AdapterView<?> arg0 = " + arg0);
		Constants.SPINNER_SELECTION = arg0.getId();
		Utils.log(TAG, "onItemSelected(View arg1 = " + arg1);
		Utils.log(TAG, "onItemSelected(int arg2 = " + arg2);
		Utils.log(TAG, "onItemSelected(long arg0 = " + arg3);
		Utils.log(TAG,
				"getSelectedItemPosition1=" + arg0.getSelectedItemPosition());
		Utils.log(TAG, "THE_SELECTED_STATION_INDEX1="
				+ Constants.THE_SELECTED_STATION_INDEX);
		Utils.log(TAG, "****************");

		if (firstStart) {
			Utils.log(TAG,
					"firstStart -> arg0.setSelection(THE_SELECTED_STATION_INDEX)");
			if (Constants.THE_SELECTED_STATION_INDEX > stationList.size()) {
				Constants.THE_SELECTED_STATION_INDEX = 0;
			}
			arg0.setSelection(Constants.THE_SELECTED_STATION_INDEX);
		} else {
			Constants.THE_SELECTED_STATION_INDEX = arg0
					.getSelectedItemPosition();
		}
		Utils.log(TAG,
				"getSelectedItemPosition2=" + arg0.getSelectedItemPosition());
		Utils.log(TAG, "SELECTED_STATION_INDEX2="
				+ Constants.THE_SELECTED_STATION_INDEX);

		changeStation();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	private void changeStation() {
		int index = Constants.THE_SELECTED_STATION_INDEX;
		if (index < 0 || index >= stationList.size()) {
			index = 0;
			Constants.THE_SELECTED_STATION_INDEX = index;
		}

		Utils.log(TAG, "index=" + index + " / stationList.size()="
				+ stationList.size());

		HashMap<String, Object> map = stationList.get(index);

		// HashMap<String, Object> map = null;
		// if (Constants.SPINNER_SELECTION == Constants.SPINNER_ALL_STATIONS) {
		// map = stationList.get(index);
		// } else if (Constants.SPINNER_SELECTION ==
		// Constants.SPINNER_FAVORITEN) {
		// map = favList.get(index);
		// } else if (Constants.SPINNER_SELECTION == Constants.SPINNER_LAENDER)
		// {
		// map = landList.get(index);
		// } else if (Constants.SPINNER_SELECTION == Constants.SPINNER_STIL) {
		// map = stilList.get(index);
		// }

		Utils.log(TAG, "!!! Sender=" + Constants.THE_SELECTED_STATION_NAME);
		Constants.THE_SELECTED_STATION_ICON = (Integer) map.get("icon");
		Constants.THE_SELECTED_STATION_ICON_SMALL = (Integer) map
				.get("icon_small");

		// avoiding OutOfMemory
		if (gcCounter % 2 == 0) { // call gc only at even numbers
			System.gc();
			gcCounter++;
		}
		// http://stackoverflow.com/questions/477572/android-strange-out-of-memory-issue
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[16 * 1024];

		logo.setImageBitmap(Images.addReflection(BitmapFactory.decodeResource(
				getResources(), Constants.THE_SELECTED_STATION_ICON, options),
				0));
		Utils.log(TAG, "*********** Stream=" + map.get("stream"));

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

		// this.setFavIcon();

		if (!firstStart && playing) {
			if (Constants.getLiveStreamStations().contains(
					Constants.THE_SELECTED_STATION_NAME)) {
				Utils.log(TAG, "------ ist Radio Gelb-Schwarz");
				showDialog(Constants.LIVE_STREAM_STATION);
			}
			if (Constants.THE_SELECTED_STATION_NAME
					.equalsIgnoreCase(Stations.RADIO_JUGGLERZ)) {
				// jugglerz.de hat immer Donnerstags eine Live Sendung. Ab
				// Freitag kann man diese als mp3 hören. Daher ist die URL
				// dynamisch.
				WebTool webtool = new WebTool();
				Constants.THE_URL_LIVE_STREAM = Constants.THE_URL_LIVE_STREAM
						+ webtool.getJugglerzFileName(this);
				Utils.log(TAG, "*********** new Stream="
						+ Constants.THE_URL_LIVE_STREAM);
			}
			if (Constants.THE_SELECTED_STATION_NAME
					.equalsIgnoreCase(Stations.RADIO_PLANET_RADIO)) {
				WebTool webtool = new WebTool();
				// planet radio ist geschützt und braucht login token damit man
				// den Stream abspielen kann.
				origPlanetradioSteam = Constants.THE_URL_LIVE_STREAM;
				Constants.THE_URL_LIVE_STREAM = Constants.THE_URL_LIVE_STREAM
						+ webtool.getPlanetradioToken(this);
				Utils.log(TAG, "*********** new Stream="
						+ Constants.THE_URL_LIVE_STREAM);
			}
			// if (Constants.THE_SELECTED_STATION_NAME
			// .equalsIgnoreCase(Stations.RADIO_EUSKIRCHEN)) {
			// WebTool webtool = new WebTool();
			// // planet radio ist geschützt und braucht login token damit man
			// // den Stream abspielen kann.
			// origRadioEuskirchen = Constants.THE_URL_LIVE_STREAM;
			// Constants.THE_URL_LIVE_STREAM = Constants.THE_URL_LIVE_STREAM
			// + webtool.getRadioEuskirchen(this);
			// Utils.log(TAG, "*********** new Stream="
			// + Constants.THE_URL_LIVE_STREAM);
			// }
			getRadioPlayer().doStartPlay(this);
			getWindow()
					.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		} else {
			this.stopPlay();
		}
		firstStart = false;
		back.setEnabled(index > 0);
		fwd.setEnabled(index < stationList.size() - 1);
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
			if (Constants.THE_SELECTED_STATION_NAME
					.equalsIgnoreCase(Stations.RADIO_PLANET_RADIO)) {
				WebTool webtool = new WebTool();
				// rt1 ist geschützt und braucht login token damit man den
				// Stream abspielen kann.
				if (origPlanetradioSteam == null) {
					origPlanetradioSteam = Constants.THE_URL_LIVE_STREAM;
				}
				Constants.THE_URL_LIVE_STREAM = origPlanetradioSteam
						+ webtool.getPlanetradioToken(this);
				Utils.log(TAG, "*********** new Stream="
						+ Constants.THE_URL_LIVE_STREAM);
			}

			// if (Constants.THE_SELECTED_STATION_NAME
			// .equalsIgnoreCase(Stations.RADIO_EUSKIRCHEN)) {
			// WebTool webtool = new WebTool();
			// // euskirchen ist geschützt und braucht login token damit man
			// // den
			// // Stream abspielen kann.
			// if (origRadioEuskirchen == null) {
			// origRadioEuskirchen = Constants.THE_URL_LIVE_STREAM;
			// }
			// Constants.THE_URL_LIVE_STREAM = origRadioEuskirchen
			// + webtool.getRadioEuskirchen(this);
			// Utils.log(TAG, "*********** new Stream="
			// + Constants.THE_URL_LIVE_STREAM);
			// }

			Utils.log(TAG, "Constants.THE_URL_LIVE_STREAM="
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

	// private void setFavIcon() {
	// DbAdapter dbadapter = new DbAdapter(this);
	// dbadapter.open();
	// Cursor cursor = null;
	// cursor = dbadapter.fetchStation(Constants.THE_SELECTED_STATION_NAME);
	// if (cursor != null && cursor.getCount() > 0) {
	// favIcon.setChecked(true);
	// Utils.log(TAG, "favIcon.setChecked(true)");
	// favIcon.setButtonDrawable(android.R.drawable.star_big_on);
	// } else {
	// favIcon.setChecked(false);
	// Utils.log(TAG, "favIcon.setChecked(false)");
	// favIcon.setButtonDrawable(android.R.drawable.star_big_off);
	// }
	// cursor.close();
	// dbadapter.close();
	// }

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
		timerSeekbarText.setText(getString(R.string.sleepTimerEndIn, progress));
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
						(millisUntilFinished / 60 / 1000) + 1));
				countDownTimerTick = Integer.valueOf(""
						+ (millisUntilFinished / 60 / 1000 + 1));
				setSeekBarProgress();
				Utils.log(TAG, "countDownTimerTick=" + countDownTimerTick);
			}

			@Override
			public void onFinish() {
				Toast.makeText(context, R.string.timerEnd, Toast.LENGTH_LONG)
						.show();
				timerSeekbarLayout.setVisibility(View.GONE);
				showCountdown = true;
				stopPlayAndRecord();
			}
		};
		countDownTimer.start();
	}

	private void setSeekBarProgress() {
		if (timerSeekbar != null) {
			timerSeekbar.setProgress(countDownTimerTick);
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

	@Override
	public Object onRetainNonConfigurationInstance() {
		Boolean wasPlaying = playing;
		return wasPlaying;
	}

	private void checkWasPlayinBeforeOrientationChange() {
		final Object data = getLastNonConfigurationInstance();
		Utils.log(TAG, "*************************************playing=" + data);
		if (data == null || !((Boolean) data)) {
			initGui();
			setSeekBarProgress();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration _newConfig) {
		super.onConfigurationChanged(_newConfig);

		Utils.log(TAG, "**+*+*+*+*+*+* playing=" + playing);
		if (!playing) {
			initGui();
			setSeekBarProgress();
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

}
