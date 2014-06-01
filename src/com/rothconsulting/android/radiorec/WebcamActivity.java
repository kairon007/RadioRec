package com.rothconsulting.android.radiorec;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WebcamActivity extends ActionBarActivity {

	private final String TAG = this.getClass().getSimpleName();
	private ProgressDialog progressDialog;
	private AlertDialog alertDialog;
	private WebView myWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set up the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		if (Constants.ROTATION_OFF_VALUE) {
			// Prevent from Rotation
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		setContentView(R.layout.webcam);
		if (!Utils.isNetworkAvailable(this, getIntent(), true)) {
			finish();
			return;
		}
		progressDialog = Utils.prepareProgressDialog(this);
		progressDialog.setTitle("Webcam " + Constants.SELECTED_STATION_NAME_VALUE);
		progressDialog.show();
		alertDialog = new AlertDialog.Builder(this).create();

		myWebView = new WebView(this);

		myWebView.post(new Runnable() {
			@Override
			public void run() {
				showWebCam();
			}
		});

		// Thread threadShowWebcam = new Thread() {
		// @Override
		// public void run() {
		// Looper.myLooper();
		// Looper.prepare();
		// showWebCam();
		// // progressDialog.dismiss();
		// }
		// };
		// threadShowWebcam.start();

		TextView radiostation = (TextView) findViewById(R.id.textViewWebcamRadioStation);
		radiostation.setText(Constants.SELECTED_STATION_NAME_VALUE);

		final Button zurueckButton = (Button) findViewById(R.id.buttonZurueck);
		zurueckButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		AnalyticsUtil.sendScreen("Webcam screen");
	}

	private void showWebCam() {
		Utils.log(TAG, "RadioPlayer.URL_WEBCAM=" + Constants.URL_WEBCAM_VALUE);
		if (Constants.URL_WEBCAM_VALUE != null && !Constants.URL_WEBCAM_VALUE.equals("")) {
			myWebView.clearCache(Boolean.TRUE);
			myWebView = (WebView) findViewById(R.id.webkitWebViewWebCam);
			myWebView.setWebChromeClient(new WebChromeClient());
			if (Build.VERSION.SDK_INT >= 5) {
				myWebView.setScrollbarFadingEnabled(true);
			}
			myWebView.getSettings().setBuiltInZoomControls(true);
			myWebView.setInitialScale(80);
			myWebView.getSettings().setJavaScriptEnabled(true);

			myWebView.loadUrl(Constants.URL_WEBCAM_VALUE);

			myWebView.setBackgroundColor(0);
			myWebView.setWebViewClient(new MyWebViewClient());
		}
	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			Log.i(TAG, "Finished loading URL: " + url);
			try {
				if (view != null && progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
			} catch (Exception e) {
				// do nothing to avoid error:
				// java.lang.IllegalArgumentException: View not attached to
				// window manager
			}
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			Log.e(TAG, "Error: " + description);
			Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_LONG).show();
		}
	}

	// Rotation
	@Override
	public void onConfigurationChanged(Configuration _newConfig) {
		super.onConfigurationChanged(_newConfig);
		// mach nix!
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
