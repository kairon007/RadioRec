package com.rothconsulting.android.radiorec;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Info extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.info);

		AdMob.showRemoveAds(this);

		final Button zurueckButton = (Button) findViewById(R.id.buttonZurueck);
		zurueckButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		final TextView featureText = (TextView) findViewById(R.id.textViewFeatureEmail);
		featureText.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startEmailActivity();
			}
		});
	}

	private void startEmailActivity() {
		final Intent emailIntent = new Intent(
				android.content.Intent.ACTION_SEND);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
				new String[] { getString(R.string.email_androider) });
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "RadioRec+");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
				"Hallo Koni\n\nMir fehlt folgender Sender: \n\n\n\n");
		emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(emailIntent, "Send mail..."));
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
