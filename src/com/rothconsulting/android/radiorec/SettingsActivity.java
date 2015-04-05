package com.rothconsulting.android.radiorec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings.SettingNotFoundException;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rothconsulting.android.common.AdMob;
import com.rothconsulting.android.common.AnalyticsUtil;
import com.rothconsulting.android.common.Utils;

public class SettingsActivity extends ActionBarActivity implements RadioGroup.OnCheckedChangeListener {

	private static final String TAG = "Settings";
	private RadioButton radioImmerAn;
	private RadioButton radioImmerAnWennStrom;
	private RadioButton radioAutomatischAus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setLogo(R.drawable.jukebox);
		actionBar.setDisplayShowTitleEnabled(true); // optional

		if (Constants.ROTATION_OFF_VALUE) {
			// Prevent from Rotation
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		setContentView(R.layout.settings);

		this.setTitle(getString(R.string.settings));

		// hide keyboard when opening page
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		AnalyticsUtil.sendScreen("Settings screen");

		AdMob admob = new AdMob();
		admob.showRemoveAds(this);

		final SharedPreferences settings = getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);

		// SD Card
		final EditText edittextSdCardPath = (EditText) findViewById(R.id.editTextSdcardPath);
		edittextSdCardPath.setText(Constants.SD_CARD_PATH_VALUE);
		final Button saveButtonPath = (Button) findViewById(R.id.buttonSavePath);
		saveButtonPath.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Constants.SD_CARD_PATH_VALUE = "" + edittextSdCardPath.getText();
				// SharedPreferences settings = getSharedPreferences(
				// Constants.PREFERENCES_FILE, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString(Constants.SD_CARD_PATH_KEY, Constants.SD_CARD_PATH_VALUE);
				editor.commit();

				AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "Settings", "SD_CARD_PATH=" + Constants.SD_CARD_PATH_VALUE);

				Toast.makeText(SettingsActivity.this, getResources().getString(R.string.save) + " (" + edittextSdCardPath.getText() + ")", Toast.LENGTH_LONG)
						.show();
			}
		});

		// Wrtite to External SD Card
		final CheckBox cbWriteToExtSdCard = (CheckBox) findViewById(R.id.checkboxWriteToExtSdCard);
		cbWriteToExtSdCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Constants.WRITE_TO_EXT_STORAGE_VALUE = ((CheckBox) v).isChecked();
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean(Constants.WRITE_TO_EXT_STORAGE_KEY, Constants.WRITE_TO_EXT_STORAGE_VALUE);
				editor.commit();

				AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "Settings", "WRITE_TO_EXT_STORAGE=" + Constants.WRITE_TO_EXT_STORAGE_VALUE);
			}
		});
		cbWriteToExtSdCard.setChecked(Constants.WRITE_TO_EXT_STORAGE_VALUE);

		// Reset SD Card Path
		final Button resetButtonPath = (Button) findViewById(R.id.buttonResetPath);
		resetButtonPath.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				edittextSdCardPath.setText(Constants.DEFAULT_SD_CARD_PATH);
			}
		});

		// Close app when timer ends
		final CheckBox cbAppOffWhenTimerEnds = (CheckBox) findViewById(R.id.checkboxAppOffAtTimeEnd);
		cbAppOffWhenTimerEnds.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// SharedPreferences settings = getSharedPreferences(
				// Constants.PREFERENCES_FILE, 0);
				Constants.CLOSE_APP_TIMER_END_VALUE = ((CheckBox) v).isChecked();
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean(Constants.CLOSE_APP_TIMER_END_KEY, Constants.CLOSE_APP_TIMER_END_VALUE);
				editor.commit();

				AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "Settings", "CLOSE_APP_TIMER_END=" + Constants.CLOSE_APP_TIMER_END_VALUE);
			}
		});
		cbAppOffWhenTimerEnds.setChecked(Constants.CLOSE_APP_TIMER_END_VALUE);

		// Rotation on/off
		final CheckBox cbRotationOf = (CheckBox) findViewById(R.id.checkboxRotationOnOff);
		cbRotationOf.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// SharedPreferences settings = getSharedPreferences(
				// Constants.PREFERENCES_FILE, 0);
				Constants.ROTATION_OFF_VALUE = ((CheckBox) v).isChecked();
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean(Constants.ROTATION_OFF_KEY, Constants.ROTATION_OFF_VALUE);
				editor.commit();
				if (Constants.ROTATION_OFF_VALUE) {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				} else {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
				}

				AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "Settings", "ROTATION_OFF=" + Constants.ROTATION_OFF_VALUE);
			}
		});

		cbRotationOf.setChecked(Constants.ROTATION_OFF_VALUE);

		// Buffer size
		final EditText editTextBufferSize = (EditText) findViewById(R.id.editTextBuffer);
		editTextBufferSize.setText("" + Constants.BUFFER_VALUE);
		editTextBufferSize.setInputType(InputType.TYPE_CLASS_PHONE);
		final Button saveButtonBuffer = (Button) findViewById(R.id.buttonSaveBuffer);
		saveButtonBuffer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editTextBufferSize.setTextColor(Color.BLACK);
				editTextBufferSize.setBackgroundColor(Color.WHITE);
				try {
					int bufferSize = Integer.valueOf("" + editTextBufferSize.getText());
					if (bufferSize <= 0 || bufferSize > 1000000) {
						throw new NumberFormatException();
					} else {
						Constants.BUFFER_VALUE = bufferSize;
						// SharedPreferences settings = getSharedPreferences(
						// Constants.PREFERENCES_FILE, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.putInt(Constants.BUFFER_KEY, Constants.BUFFER_VALUE);
						editor.commit();
						Toast.makeText(SettingsActivity.this, getResources().getString(R.string.save) + " (" + editTextBufferSize.getText() + ")",
								Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					editTextBufferSize.setTextColor(Color.WHITE);
					editTextBufferSize.setBackgroundColor(Color.RED);
					Toast.makeText(SettingsActivity.this, getResources().getString(R.string.errorZahlEingeben), Toast.LENGTH_LONG).show();
				}

				AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "Settings", "BUFFER_VALUE size=" + Constants.BUFFER_VALUE);
			}
		});

		// Reset buffer
		final Button resetButtonBuffer = (Button) findViewById(R.id.buttonResetBuffer);
		resetButtonBuffer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editTextBufferSize.setTextColor(Color.BLACK);
				editTextBufferSize.setBackgroundColor(Color.WHITE);
				editTextBufferSize.setText("" + Constants.DEFAULT_BUFFER);
			}
		});

		// Back button
		final Button zurueckButton = (Button) findViewById(R.id.buttonZurueck);
		zurueckButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// WiFi settings
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupWifi);
		radioImmerAn = (RadioButton) findViewById(R.id.radioImmerAn);
		radioImmerAnWennStrom = (RadioButton) findViewById(R.id.radioImmerAnWennStrom);
		radioAutomatischAus = (RadioButton) findViewById(R.id.radioAutomatischAus);
		radioGroup.setOnCheckedChangeListener(this);
		final Button buttonWifiSettings = (Button) findViewById(R.id.buttonWifiSettings);
		try {
			if (Utils.isPlatformBelow_4_2()) {

				buttonWifiSettings.setVisibility(View.GONE);

				int wifiSleepPolicy_old = android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.WIFI_SLEEP_POLICY);
				setRadioButtons_Level17Below(wifiSleepPolicy_old);
			} else {

				radioGroup.setVisibility(View.GONE);
				radioGroup.removeAllViews();

				// Newer than Android Platform 4.2 (Level 17)
				buttonWifiSettings.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(android.provider.Settings.ACTION_WIFI_IP_SETTINGS));
					}
				});

			}

		} catch (SettingNotFoundException e) {
			Utils.log(TAG, "SettingNotFoundException: WIFI_SLEEP_POLICY ist noch nicht konfiguriert. Kein Problem!");
			radioAutomatischAus.setChecked(true);
		}

	}

	/**
	 * For Android 4.1 (API Level 16) and older
	 * 
	 * @param wifiSleepPolicy
	 */
	private void setRadioButtons_Level17Below(int wifiSleepPolicy) {
		if (wifiSleepPolicy == android.provider.Settings.System.WIFI_SLEEP_POLICY_NEVER) {
			radioImmerAn.setChecked(true);
		} else if (wifiSleepPolicy == android.provider.Settings.System.WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED) {
			radioImmerAnWennStrom.setChecked(true);
		} else {
			radioAutomatischAus.setChecked(true);
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (checkedId == R.id.radioImmerAn) {
			android.provider.Settings.System.putInt(getContentResolver(), android.provider.Settings.System.WIFI_SLEEP_POLICY,
					android.provider.Settings.System.WIFI_SLEEP_POLICY_NEVER);
			Toast.makeText(this, getResources().getString(R.string.wifiImmerAn), Toast.LENGTH_SHORT).show();
		} else if (checkedId == R.id.radioImmerAnWennStrom) {
			android.provider.Settings.System.putInt(getContentResolver(), android.provider.Settings.System.WIFI_SLEEP_POLICY,
					android.provider.Settings.System.WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED);
			Toast.makeText(this, getResources().getString(R.string.wifiImmerAnWennAmStrom), Toast.LENGTH_SHORT).show();
		} else {
			android.provider.Settings.System.putInt(getContentResolver(), android.provider.Settings.System.WIFI_SLEEP_POLICY,
					android.provider.Settings.System.WIFI_SLEEP_POLICY_DEFAULT);
			Toast.makeText(this, getResources().getString(R.string.wifiAutomatischAus), Toast.LENGTH_SHORT).show();
		}
	}

	// ------------------------------------------------------------
	// Menu Stuff
	// ------------------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.backmenu, menu);
		if (Utils.hasValidKey()) {
			menu.removeItem(R.id.donate_adfree);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.zurueck:
			finish();
			return true;
		case R.id.donate_adfree:
			finish();
			this.startActivity(new Intent(this, DonateActivity.class));
			return true;
		case android.R.id.home:
			onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
