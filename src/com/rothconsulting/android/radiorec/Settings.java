package com.rothconsulting.android.radiorec;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings.SettingNotFoundException;
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

public class Settings extends Activity implements
		RadioGroup.OnCheckedChangeListener {

	private static final String TAG = "Settings";

	RadioButton radioImmerAn;
	RadioButton radioImmerAnWennStrom;
	RadioButton radioAutomatischAus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		// hide keyboard when opening page
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		AdMob admob = new AdMob();
		admob.showRemoveAds(this);

		final EditText edittextSdCardPath = (EditText) findViewById(R.id.editTextSdcardPath);
		edittextSdCardPath.setText(Constants.SD_CARD_PATH_VALUE);
		final Button saveButtonPath = (Button) findViewById(R.id.buttonSavePath);
		saveButtonPath.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Constants.SD_CARD_PATH_VALUE = ""
						+ edittextSdCardPath.getText();
				SharedPreferences settings = getSharedPreferences(
						Constants.PREFERENCES_FILE, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString(Constants.SD_CARD_PATH_KEY,
						Constants.SD_CARD_PATH_VALUE);
				editor.commit();
				Toast.makeText(
						Settings.this,
						getResources().getString(R.string.save) + " ("
								+ edittextSdCardPath.getText() + ")",
						Toast.LENGTH_LONG).show();
			}
		});

		final Button resetButtonPath = (Button) findViewById(R.id.buttonResetPath);
		resetButtonPath.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				edittextSdCardPath.setText(Constants.DEFAULT_SD_CARD_PATH);
			}
		});

		final CheckBox cbAppOffWhenTimerEnds = (CheckBox) findViewById(R.id.checkboxAppOffAtTimeEnd);
		cbAppOffWhenTimerEnds.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences settings = getSharedPreferences(
						Constants.PREFERENCES_FILE, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean(Constants.CLOSE_APP_TIMER_END_KEY,
						((CheckBox) v).isChecked());
				editor.commit();
			}
		});

		cbAppOffWhenTimerEnds.setChecked(Constants.CLOSE_APP_TIMER_END_VALUE);

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
					int bufferSize = Integer.valueOf(""
							+ editTextBufferSize.getText());
					if (bufferSize <= 0 || bufferSize > 1000000) {
						throw new NumberFormatException();
					} else {
						Constants.BUFFER_VALUE = bufferSize;
						SharedPreferences settings = getSharedPreferences(
								Constants.PREFERENCES_FILE, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.putInt(Constants.BUFFER_KEY,
								Constants.BUFFER_VALUE);
						editor.commit();
						Toast.makeText(
								Settings.this,
								getResources().getString(R.string.save) + " ("
										+ editTextBufferSize.getText() + ")",
								Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					editTextBufferSize.setTextColor(Color.WHITE);
					editTextBufferSize.setBackgroundColor(Color.RED);
					Toast.makeText(
							Settings.this,
							getResources()
									.getString(R.string.errorZahlEingeben),
							Toast.LENGTH_LONG).show();
				}
			}
		});

		final Button resetButtonBuffer = (Button) findViewById(R.id.buttonResetBuffer);
		resetButtonBuffer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editTextBufferSize.setTextColor(Color.BLACK);
				editTextBufferSize.setBackgroundColor(Color.WHITE);
				editTextBufferSize.setText("" + Constants.DEFAULT_BUFFER);
			}
		});

		final Button zurueckButton = (Button) findViewById(R.id.buttonZurueck);
		zurueckButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupWifi);
		radioImmerAn = (RadioButton) findViewById(R.id.radioImmerAn);
		radioImmerAnWennStrom = (RadioButton) findViewById(R.id.radioImmerAnWennStrom);
		radioAutomatischAus = (RadioButton) findViewById(R.id.radioAutomatischAus);
		radioGroup.setOnCheckedChangeListener(this);
		try {
			int wifiSleepPolicy = android.provider.Settings.System.getInt(
					getContentResolver(),
					android.provider.Settings.System.WIFI_SLEEP_POLICY);

			setRadioButtons(wifiSleepPolicy);

		} catch (SettingNotFoundException e) {
			Utils.log(
					TAG,
					"SettingNotFoundException: WIFI_SLEEP_POLICY ist noch nicht konfiguriert. Kein Problem!");
			radioAutomatischAus.setChecked(true);
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

	@Override
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
		case R.id.donate_adfree:
			finish();
			this.startActivity(new Intent(this, Donate.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
