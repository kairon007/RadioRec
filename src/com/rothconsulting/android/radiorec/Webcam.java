package com.rothconsulting.android.radiorec;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
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

public class Webcam extends Activity {

	private final String TAG = this.getClass().getSimpleName();
	ProgressDialog progressDialog;
	AlertDialog alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webcam);
		progressDialog = getProgressDialog();
		alertDialog = new AlertDialog.Builder(this).create();

		Thread threadShowWebcam = new Thread() {
			@Override
			public void run() {
				Looper.myLooper();
				Looper.prepare();
				showWebCam();
				// progressDialog.dismiss();
			}
		};
		threadShowWebcam.start();

		TextView radiostation = (TextView) findViewById(R.id.textViewWebcamRadioStation);
		radiostation.setText(RadioPlayer.SELECTED_STATION);

		final Button zurueckButton = (Button) findViewById(R.id.buttonZurueck);
		zurueckButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

	}

	private void showWebCam() {

		Log.d(TAG, "RadioPlayer.URL_WEBCAM=" + RadioPlayer.URL_WEBCAM);
		if (RadioPlayer.URL_WEBCAM != null
				&& !RadioPlayer.URL_WEBCAM.equals("")
				&& Utils.isNetworkAvailable(this)) {
			WebView myWebView = new WebView(this);
			myWebView = (WebView) findViewById(R.id.webkitWebViewWebCam);
			myWebView.setWebChromeClient(new WebChromeClient());
			myWebView.setScrollbarFadingEnabled(true);
			myWebView.getSettings().setBuiltInZoomControls(true);
			myWebView.setInitialScale(80);
			myWebView.getSettings().setJavaScriptEnabled(true);

			myWebView.loadUrl(RadioPlayer.URL_WEBCAM);

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
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			Log.e(TAG, "Error: " + description);
			Toast.makeText(getApplicationContext(), "Oh no! " + description,
					Toast.LENGTH_SHORT).show();
			alertDialog.setTitle("Error beim laden!");
			alertDialog.setMessage(description);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					return;
				}
			});
			alertDialog.show();
		}
	}

	// ------------------------------------------------------------
	// Menu Stuff
	// ------------------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.infomenu, menu);
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

	private ProgressDialog getProgressDialog() {
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(true);
		progressDialog.setMessage("Loading...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setTitle("Webcam " + RadioPlayer.SELECTED_STATION);
		progressDialog.show();
		return progressDialog;
	}

}
