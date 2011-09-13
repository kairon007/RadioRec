package com.rothconsulting.android.radiorec;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends Activity implements
		RadioGroup.OnCheckedChangeListener {

	private static final String TAG = "Donate";

	RadioButton radioImmerAn;
	RadioButton radioImmerAnWennStrom;
	RadioButton radioAutomatischAus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		// hide keyboard
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		AdMob.showRemoveAds(this);

		final EditText edittextSdCardPath = (EditText) findViewById(R.id.editTextSdcardPath);
		edittextSdCardPath.setText(Constants.THE_SD_CARD_PATH);
		final Button saveButton = (Button) findViewById(R.id.buttonSave);
		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Constants.THE_SD_CARD_PATH = "" + edittextSdCardPath.getText();
				SharedPreferences settings = getSharedPreferences(
						Constants.PREFERENCES_FILE, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString(Constants.SD_CARD_PATH,
						Constants.THE_SD_CARD_PATH);
				editor.commit();
				Toast.makeText(
						Settings.this,
						getResources().getString(R.string.save) + " ("
								+ edittextSdCardPath.getText() + ")",
						Toast.LENGTH_LONG).show();
			}
		});

		final Button resetButton = (Button) findViewById(R.id.buttonReset);
		resetButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				edittextSdCardPath.setText(Constants.DEFAULT_SD_CARD_PATH);
			}
		});

		final Button zurueckButton = (Button) findViewById(R.id.buttonZurueck);
		zurueckButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupWifi);
		radioImmerAn = (RadioButton) findViewById(R.id.radioImmerAn);
		radioImmerAnWennStrom = (RadioButton) findViewById(R.id.radioImmerAnWennStrom);
		radioAutomatischAus = (RadioButton) findViewById(R.id.radioAutomatischAus);
		final TextView textViewWifiVorsicht = (TextView) findViewById(R.id.textViewWifiVorsicht);
		radioGroup.setOnCheckedChangeListener(this);
		try {
			int wifiSleepPolicy = android.provider.Settings.System.getInt(
					getContentResolver(),
					android.provider.Settings.System.WIFI_SLEEP_POLICY);

			setRadioButtons(wifiSleepPolicy);

		} catch (SettingNotFoundException e) {
			Log.d(TAG,
					"SettingNotFoundException: Kann System Settings nicht finden!");
			Toast.makeText(this,
					getResources().getString(R.string.settingsNotFound),
					Toast.LENGTH_SHORT).show();
			radioGroup.setVisibility(View.INVISIBLE);
			textViewWifiVorsicht.setVisibility(View.INVISIBLE);
		}
	}

	private void setRadioButtons(int wifiSleepPolicy) {
		if (wifiSleepPolicy == android.provider.Settings.System.WIFI_SLEEP_POLICY_NEVER) {
			radioImmerAn.setChecked(true);
		} else if (wifiSleepPolicy == android.provider.Settings.System.WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED) {
			radioImmerAnWennStrom.setChecked(true);
		} else {
			radioAutomatischAus.setChecked(true);
		}
	}

	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (checkedId == R.id.radioImmerAn) {
			android.provider.Settings.System.putInt(getContentResolver(),
					android.provider.Settings.System.WIFI_SLEEP_POLICY,
					android.provider.Settings.System.WIFI_SLEEP_POLICY_NEVER);
			Toast.makeText(this,
					getResources().getString(R.string.wifiImmerAn),
					Toast.LENGTH_SHORT).show();
		} else if (checkedId == R.id.radioImmerAnWennStrom) {
			android.provider.Settings.System
					.putInt(getContentResolver(),
							android.provider.Settings.System.WIFI_SLEEP_POLICY,
							android.provider.Settings.System.WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED);
			Toast.makeText(this,
					getResources().getString(R.string.wifiImmerAnWennAmStrom),
					Toast.LENGTH_SHORT).show();
		} else {
			android.provider.Settings.System.putInt(getContentResolver(),
					android.provider.Settings.System.WIFI_SLEEP_POLICY,
					android.provider.Settings.System.WIFI_SLEEP_POLICY_DEFAULT);
			Toast.makeText(this,
					getResources().getString(R.string.wifiAutomatischAus),
					Toast.LENGTH_SHORT).show();
		}
	}

	// ------------------------------------------------------------
	// Menu Stuff
	// ------------------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.backmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.zurueck:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
