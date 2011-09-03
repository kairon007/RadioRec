package com.rothconsulting.android.radiorec;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings);

		final EditText edittext = (EditText) findViewById(R.id.editTextAntAdsKey);

		final Button saveButton = (Button) findViewById(R.id.buttonSave);
		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioRecPlus.ANTI_ADS_KEY = "" + edittext.getText();
				SharedPreferences settings = getSharedPreferences(
						Constants.PREFERENCES_FILE, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString(Constants.ANTI_ADS_KEY,
						RadioRecPlus.ANTI_ADS_KEY);
				editor.commit();
				Toast.makeText(Settings.this, "DANKE!!! " + edittext.getText(),
						Toast.LENGTH_LONG).show();
			}
		});

		final Button zurueckButton = (Button) findViewById(R.id.buttonZurueck);
		zurueckButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
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
