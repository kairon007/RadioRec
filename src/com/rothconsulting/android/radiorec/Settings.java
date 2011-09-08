package com.rothconsulting.android.radiorec;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity {

	private static final String TAG = "Donate";

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

		final CheckBox checkBoxWifiOnOff = (CheckBox) findViewById(R.id.checkBoxWifiEin);
		checkBoxWifiOnOff.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				WifiManager.WifiLock wifiLock = null;
				WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
				if (wifiManager != null) {
					wifiLock = wifiManager
							.createWifiLock("RadioRec+ wifi lock");
				}
				doWifiStuff(checkBoxWifiOnOff, wifiManager, wifiLock);
			}
		});

	}

	private void doWifiStuff(CheckBox checkBoxWifiOnOff,
			WifiManager wifiManager, WifiManager.WifiLock wifiLock) {
		if (checkBoxWifiOnOff.isChecked()) {
			if (wifiManager != null) {
				wifiLock.acquire();
				Toast.makeText(this,
						getResources().getString(R.string.wifiLockOn),
						Toast.LENGTH_LONG).show();
			}
		} else {
			if (wifiManager != null) {
				wifiLock.release();
				Toast.makeText(this,
						getResources().getString(R.string.wifiLockOff),
						Toast.LENGTH_LONG).show();
			}
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
