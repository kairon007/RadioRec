package com.rothconsulting.android.radiorec;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
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

import com.rothconsulting.android.billing.BillingActivity;
import com.rothconsulting.android.common.AdMob;
import com.rothconsulting.android.common.AnalyticsUtil;
import com.rothconsulting.android.common.Utils;

public class DonateActivity extends ActionBarActivity {

	private static final String TAG = "Donate";
	private Activity activity;
	private static final String PAYPAL_URL = "https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=RTHRLLC6NV4NN";
	/** Bitcoin key. */
	private static final String BITCOIN_KEY = "17tqaQXYzg2ANDCLM23X3zaFNH1TZLM35j";

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
		setContentView(R.layout.donate);

		this.setTitle(getString(R.string.donate_adfree));

		activity = this;

		AnalyticsUtil.sendScreen("Donate screen");

		// if it comes from the notification and isDonator go to the main screen
		if (Utils.hasValidKey()) {
			Toast.makeText(this, this.getString(R.string.alreadyDonated), Toast.LENGTH_LONG).show();

			AnalyticsUtil.sendEvent(AnalyticsUtil.KEY_VALIDATION, "hasValidKey", "yes isDonator, closing Donate screen");

			finish();
		}

		AnalyticsUtil.sendEvent(AnalyticsUtil.KEY_VALIDATION, "hasValidKey", "no isNotDonator, showing Donate screen");

		// hide keyboard
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		AdMob admob = new AdMob();
		admob.showRemoveAds(this);

		final ImageButton buttonPayPal = (ImageButton) findViewById(R.id.imageButtonPaypal);
		buttonPayPal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentHomepage = new Intent(Intent.ACTION_VIEW);
				intentHomepage.setData(Uri.parse(PAYPAL_URL));

				AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "clicked imageButtonPaypal", "URL = " + PAYPAL_URL);

				startActivity(intentHomepage);
			}
		});

		final ImageButton buttonBitcoin = (ImageButton) findViewById(R.id.imageButtonBitcoin);
		buttonBitcoin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "clicked imageButtonBitcoin", "Start donateBitcoin");
				donateBitcoin();
			}
		});

		final ImageButton buttonAndroidMarket = (ImageButton) findViewById(R.id.imageButtonAndroidMarket);
		final Intent intentBillingSpende = new Intent(this, BillingActivity.class);
		buttonAndroidMarket.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "clicked imageButtonAndroidMarket", "Start intentSpende");
				startActivity(intentBillingSpende);
			}
		});

		final EditText edittext = (EditText) findViewById(R.id.editTextAntiAdsKey);
		if (Constants.ANTI_ADS_VALUE != null) {
			edittext.setText(Constants.ANTI_ADS_VALUE);
		}
		edittext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String text = "" + edittext.getText();
				if (edittext != null && edittext.getText() != null && text.equals(activity.getString(R.string.antiWerbungKey))) {
					edittext.setText("");
				}
			}
		});
		edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				String text = "" + edittext.getText();
				if (edittext != null && edittext.getText() != null && text.equals(activity.getString(R.string.antiWerbungKey))) {
					edittext.setText("");
				}
			}
		});

		final Button saveButton = (Button) findViewById(R.id.buttonSaveAntiAdsKey);
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (edittext != null && edittext.getText() != null) {
					Constants.ANTI_ADS_VALUE = edittext.getText().toString().trim();
				} else {
					Constants.ANTI_ADS_VALUE = "ERROR";
				}
				if ("".equals(Constants.ANTI_ADS_VALUE) || Constants.ANTI_ADS_VALUE.equals(activity.getString(R.string.antiWerbungKey))) {
					Constants.ANTI_ADS_VALUE = "";
					Toast.makeText(DonateActivity.this, getResources().getString(R.string.keineEingabe), Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(DonateActivity.this, getResources().getString(R.string.danke) + " (" + edittext.getText() + ")", Toast.LENGTH_LONG).show();
				}

				SharedPreferences settings = getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString(Constants.ANTI_ADS_KEY, Constants.ANTI_ADS_VALUE);
				editor.commit();
				editor.clear();

				AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "clicked buttonSaveAntiAdsKey", "Key: " + Constants.ANTI_ADS_VALUE);
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
		b.setNeutralButton(R.string.copy_to_clipboard, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				ClipboardManager cbm = (ClipboardManager) //
				getSystemService(CLIPBOARD_SERVICE);
				cbm.setText(BITCOIN_KEY);
			}
		});
		final Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("bitcoin:" + BITCOIN_KEY));
		if (i.resolveActivity(this.getPackageManager()) != null) {
			b.setNegativeButton(R.string.send_now, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(final DialogInterface dialog, final int which) {
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
		menu.add(0, -2, 0, this.getResources().getString(R.string.stop)).setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.zurueck:
			finish();
			return true;
		case -2:
			RadioRecPlusActivity.getRadioPlayer().doStopPlay(this);
			RadioRecPlusActivity.doStopRecording(this);
			RadioRecPlusActivity.playing = Boolean.FALSE;
			RadioRecPlusActivity.recording = Boolean.FALSE;
			finish();
			break;
		case android.R.id.home:
			onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
