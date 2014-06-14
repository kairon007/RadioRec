package com.rothconsulting.android.radiorec;

import com.rothconsulting.android.common.AdMob;
import com.rothconsulting.android.common.AnalyticsUtil;
import com.rothconsulting.android.common.Utils;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set up the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		if (Constants.ROTATION_OFF_VALUE) {
			// Prevent from Rotation
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		AnalyticsUtil.sendScreen("Info screen");

		this.setTitle(getString(R.string.info));
		setContentView(R.layout.info);

		AdMob admob = new AdMob();
		admob.showRemoveAds(this);

		final TextView textViewAppVersion = (TextView) findViewById(R.id.textViewAppVersion);
		Utils utils = new Utils();
		textViewAppVersion.setText("Version " + utils.getAppVersionName(this, InfoActivity.class));

		final Button zurueckButton = (Button) findViewById(R.id.buttonZurueck);
		zurueckButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// final TextView featureText = (TextView)
		// findViewById(R.id.textViewFeatureEmail);
		// featureText.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// startEmailActivity();
		// }
		// });
	}

	// private void startEmailActivity() {
	// final Intent emailIntent = new Intent(
	// android.content.Intent.ACTION_SEND);
	// emailIntent.setType("plain/text");
	// emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
	// new String[] { getString(R.string.email_androider) });
	// emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "RadioRec+");
	// emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
	// "Hallo Koni\n\nMir fehlt folgender Sender: \n\n\n\n");
	// emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	// startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	// }

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
