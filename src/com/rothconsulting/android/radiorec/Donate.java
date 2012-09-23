package com.rothconsulting.android.radiorec;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.rothconsulting.android.marketbilling.MarketSpende;

public class Donate extends Activity {

	private static final String TAG = "Donate";

	/** Bitcoin key. */
	private static final String BITCOIN_KEY = "1ErTn1kvjprJ9pC6AZKDyDoYLLWtWjaKWR";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.donate);
		Intent fromIntent = getIntent();
		Utils.log(TAG, "fromIntent=" + fromIntent);
		Utils utils = new Utils();
		// if it comes from the notification and isDonator go to the main screen
		if (fromIntent != null) {
			Bundle extraBundle = fromIntent.getExtras();
			if (extraBundle != null
					&& extraBundle.getString(Constants.FROM_NOTIFICATION) != null
					&& extraBundle.getString(Constants.FROM_NOTIFICATION)
							.equals(Constants.FROM_NOTIFICATION)
					&& utils.hasValidKey()) {
				Utils.log(TAG, "*** finish (fromNotification und validKey)");
				finish();
			} else {
				Utils.log(TAG,
						"*** not fromNotification or not validKey) validKey="
								+ utils.hasValidKey());
			}
		}

		// hide keyboard
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		AdMob admob = new AdMob();
		admob.showRemoveAds(this);

		final ImageButton buttonPayPal = (ImageButton) findViewById(R.id.imageButtonPaypal);
		buttonPayPal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentHomepage = new Intent(Intent.ACTION_VIEW);
				intentHomepage.setData(Uri
						.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=RTHRLLC6NV4NN"));
				startActivity(intentHomepage);
			}
		});

		final ImageButton buttonBitcoin = (ImageButton) findViewById(R.id.imageButtonBitcoin);
		buttonBitcoin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				donateBitcoin();
			}
		});

		final ImageButton buttonAndroidMarket = (ImageButton) findViewById(R.id.imageButtonAndroidMarket);
		final Intent intentSpende = new Intent(this, MarketSpende.class);
		buttonAndroidMarket.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(intentSpende);
			}
		});

		final EditText edittext = (EditText) findViewById(R.id.editTextAntiAdsKey);
		if (Constants.ANTI_ADS_VALUE != null) {
			edittext.setText(Constants.ANTI_ADS_VALUE);
		}

		final Button saveButton = (Button) findViewById(R.id.buttonSaveAntiAdsKey);
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Constants.ANTI_ADS_VALUE = "" + edittext.getText();
				SharedPreferences settings = getSharedPreferences(
						Constants.PREFERENCES_FILE, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString(Constants.ANTI_ADS_KEY,
						Constants.ANTI_ADS_VALUE);
				editor.commit();
				if ("".equals(Constants.ANTI_ADS_VALUE)) {
					Toast.makeText(Donate.this,
							getResources().getString(R.string.keineEingabe),
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(
							Donate.this,
							getResources().getString(R.string.danke) + " ("
									+ edittext.getText() + ")",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		final Button zurueckButton = (Button) findViewById(R.id.buttonZurueck);
		zurueckButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void donateBitcoin() {
		final Builder b = new Builder(this);
		b.setCancelable(true);
		b.setTitle(R.string.donate_bitcoin);
		String s = this.getString(R.string.donate_bitcoin_text);
		s += "\n\n" + BITCOIN_KEY;
		b.setMessage(s);
		b.setPositiveButton(R.string.neinDanke, null);
		b.setNeutralButton(R.string.copy_to_clipboard,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog,
							final int which) {
						ClipboardManager cbm = (ClipboardManager) //
						getSystemService(CLIPBOARD_SERVICE);
						cbm.setText(BITCOIN_KEY);
					}
				});
		final Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("bitcoin:"
				+ BITCOIN_KEY));
		if (i.resolveActivity(this.getPackageManager()) != null) {
			b.setNegativeButton(R.string.send_now,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog,
								final int which) {
							startActivity(i);
						}
					});
		}
		b.show();
	}

	// ------------------------------------------------------------
	// Menu Stuff
	// ------------------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.backmenu, menu);
		menu.removeItem(R.id.donate_adfree);
		menu.add(0, -2, 0, this.getResources().getString(R.string.stop))
				.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.zurueck:
			finish();
			return true;
		case -2:
			RadioRecPlus.getRadioPlayer().doStopPlay(this);
			RadioRecPlus.doStopRecording(this);
			RadioRecPlus.playing = Boolean.FALSE;
			RadioRecPlus.recording = Boolean.FALSE;
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
