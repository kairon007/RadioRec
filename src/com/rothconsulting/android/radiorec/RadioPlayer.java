package com.rothconsulting.android.radiorec;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;

public class RadioPlayer extends Activity {

	private final String TAG = this.getClass().getSimpleName();

	// Default Constants
	protected static int SELECTED_STATION_ICON = R.drawable.logo_radio32_100x36;
	protected static String SELECTED_STATION = Constants.RADIO_32;
	protected static String URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_32;
	protected static String URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_32;
	protected static String URL_WEBCAM = Constants.URL_WEBCAM_RADIO_32;
	protected static String URL_CONTACT = Constants.URL_CONTACT_RADIO_32;
	protected static String URL_SONGTICKER = "";

	protected static boolean isRadioRunning;
	private static boolean isRadioRecording;

	ImageView imageViewHomepage;
	ImageView imageViewWebcam;
	ImageView imageViewMailContact;
	ToggleButton toggleButtonRecordStop = null;
	ToggleButton toggleButtonPlayStop = null;
	ProgressDialog progressDialog;

	MediaPlayer mediaPlayer;
	WebView myWebView;
	AsyncTask<URL, Integer, Long> recordTask;
	Notifications notifications = new Notifications(this, this.getIntent());

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		if (!isRadioRunning && !isRadioRecording) {

			getPreferences();
			prepareProgressDialog();

			Log.d(TAG, "isRadioRunning=" + isRadioRunning);
			Log.d(TAG, "isRadioRecording=" + isRadioRecording);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			hideSongTicker();
			showHomepageLogo();
			notifications = new Notifications(this, this.getIntent());

			AdMob.showAd(this);

			toggleButtonPlayStop = (ToggleButton) findViewById(R.id.toggleButtonPlayStop);
			Log.d(TAG, "ToggleButton!");
			toggleButtonPlayStop.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Log.d(TAG, "ToggleButton - onClick!");

					if (toggleButtonPlayStop.isChecked()) {

						if (Utils.isNetworkAvailable(RadioPlayer.this)) {
							Log.d(TAG, "Pressed Play");

							doStartPlay();
							toggleButtonPlayStop.setChecked(true);
							toggleButtonPlayStop
									.setBackgroundDrawable(getResources()
											.getDrawable(
													R.drawable.button_blue_stop_play));
						} else {
							progressDialog.dismiss();
							notifications
									.showStatusBarNotificationError(R.string.networkNotAvailable);
						}

					} else {
						Log.d(TAG, "Pressed Stop!");
						doStopPlay();
						toggleButtonPlayStop.setChecked(false);
						toggleButtonPlayStop
								.setBackgroundDrawable(getResources()
										.getDrawable(
												R.drawable.button_blue_play));
					}
				}
			});

			toggleButtonRecordStop = (ToggleButton) findViewById(R.id.toggleButtonRecordStop);
			Log.d(TAG, "toggleButtonRecordStop!");
			toggleButtonRecordStop
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							Log.d(TAG, "ToggleButtonRecord - onClick!");

							if (toggleButtonRecordStop.isChecked()) {

								if (Utils
										.isNetworkAvailable(getApplicationContext())) {

									// progressDialog.show();

									Log.d(TAG, "Recording...");
									toggleButtonRecordStop
											.setBackgroundDrawable(getResources()
													.getDrawable(
															R.drawable.button_blue_stop_record));
									toggleButtonRecordStop.setChecked(true);

									isRadioRecording = doStartRecording();

								} else {
									notifications
											.showStatusBarNotificationError(R.string.networkNotAvailable);
								}

							} else {
								Log.d(TAG, "doStop!");
								toggleButtonRecordStop.setChecked(false);
								toggleButtonRecordStop
										.setBackgroundDrawable(getResources()
												.getDrawable(
														R.drawable.button_blue_record));
								doStopRecording();
							}
						}
					});

			imageViewHomepage = (ImageView) findViewById(R.id.ImageViewSelectedStation);
			imageViewHomepage.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					if (URL_HOMEPAGE != null && !URL_HOMEPAGE.equals("")) {
						i.setData(Uri.parse(URL_HOMEPAGE));
						startActivity(i);
					}
				}
			});

			showHideWebcamIcon();
			showHideMailIcon();
			// ImageView buttonReload = (ImageView)
			// findViewById(R.id.imageViewReload);
			// buttonReload.setOnClickListener(new View.OnClickListener() {
			// public void onClick(View v) {
			// showSongTicker();
			// }
			// });

			TextView textHomepage = (TextView) findViewById(R.id.textView_Homepage);
			textHomepage.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					if (URL_HOMEPAGE != null && !URL_HOMEPAGE.equals("")) {
						i.setData(Uri.parse(URL_HOMEPAGE));
						startActivity(i);
					}
				}
			});

		}

	}

	private void doStartPlay() {

		progressDialog.setTitle(SELECTED_STATION);
		progressDialog.show();

		Thread threadDoStartPlay = new Thread() {
			@Override
			public void run() {

				mediaPlayer = new MediaPlayer();
				try {

					mediaPlayer.setDataSource(URL_LIVE_STREAM);
					Log.d(TAG, "URL: " + URL_LIVE_STREAM);
					mediaPlayer.prepare();
					mediaPlayer.start();
					notifications.showStatusBarNotificationIsRunning();
					isRadioRunning = true;
					Log.d(TAG, "*********** isRadioRunning1=" + isRadioRunning);
					showHomepageLogo();
					// Thread threadSongTicker = new Thread() {
					// @Override
					// public void run() {
					// showSongTicker();
					// }
					// };
					// threadSongTicker.start();

					notifications
							.hideStatusBarNotification(Constants.NOTIFICATION_ID_ERROR_CONNECTION);

				} catch (IllegalArgumentException e) {
					Log.d(TAG,
							"IllegalArgumentException: "
									+ RadioPlayer.this
											.getString(R.string.networkNotAvailable));
					notifications
							.showStatusBarNotificationError(R.string.networkNotAvailable);
				} catch (IllegalStateException e) {
					Log.d(TAG,
							"IllegalStateException 1: "
									+ RadioPlayer.this
											.getString(R.string.networkNotAvailable));
					notifications
							.showStatusBarNotificationError(R.string.networkNotAvailable);
				} catch (IOException e) {
					Log.d(TAG,
							"doStartPlay - IOException: "
									+ RadioPlayer.this
											.getString(R.string.internetadresseNichtErreichbar));
					notifications
							.showStatusBarNotificationError(R.string.internetadresseNichtErreichbar);

				} finally {

					if (!isRadioRunning) {
						notifications
								.showStatusBarNotificationError(R.string.internetadresseNichtErreichbar);
					}
				}
				progressDialog.dismiss();
			}
		};
		threadDoStartPlay.start();
		Log.d(TAG, "*********** isRadioRunning2=" + isRadioRunning);
	}

	private final Handler uiCallback = new Handler() {

		@Override
		public void handleMessage(Message msg) {

		}

	};

	private void doStopPlay() {

		try {
			if (mediaPlayer != null
					&& (mediaPlayer.isPlaying() || mediaPlayer.isLooping())) {
				mediaPlayer.stop();
				mediaPlayer.release();
				notifications
						.hideStatusBarNotification(Constants.NOTIFICATION_ID_RADIO_IS_PLAYING);
				notifications
						.hideStatusBarNotification(Constants.NOTIFICATION_ID_ERROR_CONNECTION);
				isRadioRunning = false;
				// if (!isRadioRecording) {
				// hideSongTicker();
				// }
			}
		} catch (IllegalStateException e) {
			Log.d(TAG, "doStopPlay - IllegalStateException");
		}
	}

	protected void doRestartPlay() {
		showHomepageLogo();
		if (isRadioRunning) {
			doStopPlay();
			doStartPlay();
		}
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
			notifications
					.showStatusBarNotificationError(R.string.internetadresseNichtErreichbar);
		}

		try {
			outputUrl = new URL("file:///sdcard/RadioRecorder/"
					+ SELECTED_STATION.replaceAll(" ", "") + "-" + dateTime
					+ ".mp3");
		} catch (MalformedURLException e) {
			notifications
					.showStatusBarNotificationError(R.string.kannNichtAufSdCardSchreiben);
		}

		notifications.showStatusBarNotificationRecording();
		recordTask = new RadioRecorder(this, this.getIntent()).execute(
				inputUrl, outputUrl);
		isRadioRecording = true;
		Log.d(TAG, "*********** isRadioRecording2=" + isRadioRecording);

		return isRadioRecording;
	}

	private void doStopRecording() {
		if (recordTask != null) {
			recordTask.cancel(true);
			notifications
					.hideStatusBarNotification(Constants.NOTIFICATION_ID_RECORDING);
			isRadioRecording = false;
		}
	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	private void showHomepageLogo() {
		if (URL_HOMEPAGE != null && !URL_HOMEPAGE.equals("")) {
			((ViewSwitcher) findViewById(R.id.viewSwitcher_Homepage_Logo))
					.setVisibility(View.VISIBLE);

			if (imageViewHomepage == null) {
				imageViewHomepage = (ImageView) findViewById(R.id.ImageViewSelectedStation);
			}
			imageViewHomepage.findViewById(SELECTED_STATION_ICON);
			imageViewHomepage.setImageResource(SELECTED_STATION_ICON);
		}
	}

	private void showWebcamLogo() {
		if (URL_WEBCAM != null && !URL_WEBCAM.equals("")) {
			((ViewSwitcher) findViewById(R.id.viewSwitcher_Webcam))
					.setVisibility(View.VISIBLE);

			if (imageViewWebcam == null) {
				imageViewWebcam = (ImageView) findViewById(R.id.ImageViewWebcam);
			}
			imageViewWebcam.findViewById(SELECTED_STATION_ICON);
			imageViewWebcam.setImageResource(SELECTED_STATION_ICON);
		}
	}

	// private void showSongTicker() {
	//
	// Log.d(TAG, "SELECTED_STATION=" + SELECTED_STATION);
	// URL_SONGTICKER = WebTool.getSongticker(SELECTED_STATION);
	// Log.d(TAG, "URL_SONGTICKER=" + URL_SONGTICKER);
	//
	// if (URL_SONGTICKER != null && !URL_SONGTICKER.equals("")
	// && Utils.isNetworkAvailable(this) && isRadioRunning) {
	// myWebView = null;
	// ((ViewSwitcher) findViewById(R.id.viewSwitcher_SongTicker))
	// .setVisibility(View.VISIBLE);
	// ((ImageView) findViewById(R.id.imageViewReload))
	// .setVisibility(View.VISIBLE);
	//
	// myWebView = (WebView) findViewById(R.id.webkitWebViewRadioProgramm);
	// myWebView.setWebChromeClient(new WebChromeClient());
	// myWebView.setScrollbarFadingEnabled(true);
	// myWebView.getSettings().setJavaScriptEnabled(true);
	//
	// if (URL_SONGTICKER.startsWith("http")) {
	// myWebView.loadUrl(URL_SONGTICKER);
	// } else {
	// myWebView.loadData(URL_SONGTICKER, "text/html", "UTF-8");
	// }
	//
	// myWebView.setBackgroundColor(0);
	// myWebView.setWebViewClient(new MyWebViewClient());
	// } else {
	// hideSongTicker();
	// }
	// }

	private void hideSongTicker() {
		((ViewSwitcher) findViewById(R.id.viewSwitcher_SongTicker))
				.setVisibility(View.INVISIBLE);
		((ImageView) findViewById(R.id.imageViewReload))
				.setVisibility(View.INVISIBLE);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isRadioRunning) {
				showDialog(Constants.PRESS_BACK_BUTTON);
				return true;
			} else {
				doStopPlay();
				doStopRecording();
				finish();
			}
		}
		notifications
				.hideStatusBarNotification(Constants.NOTIFICATION_ID_ERROR_CONNECTION);
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
									doStopPlay();
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
		}
		return null;
	}

	// ------------------------------------------------------------
	// Menu Stuff
	// ------------------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		// MenuItem itemSettings = menu.getItem(0);
		// Log.d(TAG, "Menu=" + itemSettings.getTitle());
		// SubMenu submenu = itemSettings.getSubMenu();
		// MenuItem itemRadio32 = submenu.getItem(0);
		// itemRadio32.setChecked(true);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		item.setCheckable(true);
		switch (item.getItemId()) {

		case R.id.settings:
			Log.d(TAG, "isRadioRunning 1 = " + isRadioRunning);
			this.startActivityForResult(new Intent(this, RadioChooser.class),
					Constants.RETURN_FROM_CHOOSER);
			return true;

		case R.id.ende:
			doStopPlay();
			doStopRecording();
			notifications
					.hideStatusBarNotification(Constants.NOTIFICATION_ID_ERROR_CONNECTION);
			finish();
			return true;
		case R.id.infos:
			this.startActivity(new Intent(this, Info.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Constants.RETURN_FROM_CHOOSER
				&& resultCode == Activity.RESULT_OK) {
			Log.d(TAG, "RESTART! isRadioRunning return = " + isRadioRunning);
			showHideWebcamIcon();
			showHideMailIcon();
			doRestartPlay();
		}
	}

	private void showHideWebcamIcon() {
		imageViewWebcam = (ImageView) findViewById(R.id.ImageViewWebcam);
		TextView textWebcam = (TextView) findViewById(R.id.textView_Webcam);
		if (URL_WEBCAM != null && !URL_WEBCAM.equals("")) {
			imageViewWebcam.setVisibility(View.VISIBLE);
			textWebcam.setVisibility(View.VISIBLE);
			final Intent intentCam = new Intent(this, Webcam.class);
			imageViewWebcam.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					startActivity(intentCam);

				}
			});
		} else {
			imageViewWebcam.setVisibility(View.INVISIBLE);
			textWebcam.setVisibility(View.INVISIBLE);
		}

	}

	private void showHideMailIcon() {
		imageViewMailContact = (ImageView) findViewById(R.id.ImageViewMail);
		TextView textMail = (TextView) findViewById(R.id.textViewMail);
		Log.d(TAG, "***** URL_CONTACT=" + URL_CONTACT);
		if (URL_CONTACT != null && !URL_CONTACT.equals("")) {
			imageViewMailContact.setVisibility(View.VISIBLE);
			textMail.setVisibility(View.VISIBLE);
			imageViewMailContact.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					if (URL_CONTACT.startsWith("http")) {
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(URL_CONTACT));
						startActivity(i);
					} else {
						final Intent emailIntent = new Intent(
								android.content.Intent.ACTION_SEND);
						emailIntent.setType("plain/text");
						emailIntent.putExtra(
								android.content.Intent.EXTRA_EMAIL,
								new String[] { RadioPlayer.URL_CONTACT });
						emailIntent.putExtra(
								android.content.Intent.EXTRA_SUBJECT,
								"Mein Wunsch");
						emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
								"");
						startActivity(Intent.createChooser(emailIntent,
								"Send mail..."));
					}
				}
			});
		} else {
			imageViewMailContact.setVisibility(View.INVISIBLE);
			textMail.setVisibility(View.INVISIBLE);
		}

	}

	private void getPreferences() {
		// Restore preferences
		SharedPreferences settings = getSharedPreferences(
				Constants.PREFERENCES_FILE, 0);
		Log.d(TAG, "SELECTED_STATION vorher: " + SELECTED_STATION);
		Log.d(TAG, "URL_CONTACT vorher: " + URL_CONTACT);
		SELECTED_STATION = settings.getString(Constants.SELECTED_STATION,
				RadioPlayer.SELECTED_STATION);
		SELECTED_STATION_ICON = settings.getInt(
				Constants.SELECTED_STATION_ICON,
				RadioPlayer.SELECTED_STATION_ICON);
		URL_LIVE_STREAM = settings.getString(Constants.SELECTED_STATION_STREAM,
				RadioPlayer.URL_LIVE_STREAM);
		URL_HOMEPAGE = settings.getString(Constants.SELECTED_STATION_HOMEPAGE,
				RadioPlayer.URL_HOMEPAGE);
		URL_WEBCAM = settings.getString(Constants.SELECTED_STATION_WEBCAM,
				RadioPlayer.URL_WEBCAM);
		URL_CONTACT = settings.getString(Constants.SELECTED_STATION_CONTACT,
				RadioPlayer.URL_CONTACT);
		Log.d(TAG, "SELECTED_STATION nachher: " + SELECTED_STATION);
		Log.d(TAG, "URL_CONTACT nachher: " + URL_CONTACT);
	}

	private ProgressDialog prepareProgressDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(true);
		progressDialog.setMessage("Loading...");
		progressDialog.setTitle(R.string.app_name);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		return progressDialog;
	}
}