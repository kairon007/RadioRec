package com.rothconsulting.android.radiorec;

/*
 * The application needs to have the permission to write to external storage
 * if the output file is written to the external storage, and also the
 * permission to record audio. These permissions must be set in the
 * application's AndroidManifest.xml file, with something like:
 *
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.RECORD_AUDIO" />
 *
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class RadioRecorder extends AsyncTask<URL, Integer, Long> {

	private final String TAG = this.getClass().getSimpleName();

	private long bytesRead;
	private long bytesReadTmp;
	private Context context = null;
	private Intent intent = null;

	public RadioRecorder(Context theContext, Intent theIntent) {
		this.context = theContext;
		this.intent = theIntent;
	}

	@Override
	protected Long doInBackground(URL... urls) {

		Log.d(TAG, "startRecording");
		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;
		bytesRead = 0;
		bytesReadTmp = 0;

		try {
			File radioRecorderDirectory = new File("/sdcard/RadioRecorder/");
			radioRecorderDirectory.mkdirs();

			inputStream = urls[0].openStream();
			Log.d(TAG, "url.openStream()");

			fileOutputStream = new FileOutputStream(urls[1].getFile());
			Log.d(TAG, "FileOutputStream: " + urls[1].getFile());

			int c;

			while ((c = inputStream.read()) != -1 && !isCancelled()) {
				// Log.d(LOG_TAG, "bytesRead=" + bytesRead);
				fileOutputStream.write(c);
				bytesRead++;
				bytesReadTmp++;
				if (bytesReadTmp > 1000) {
					fileOutputStream.flush();
					bytesReadTmp = 0;
				}
			}
			Log.d(TAG, "******* End reading stream. bytesRead=" + bytesRead);
			if (isCancelled()) {
				Log.d(TAG, "******* Task cancelled!!!");
			} else {
				Log.d(TAG, "******* End of inputStream. c=" + c);
				Notifications not = new Notifications(this.context, intent);
				not.showStatusBarNotificationError(R.string.aufnahmeUnterbrochen);
			}

		} catch (IOException e) {
			Notifications not = new Notifications(this.context, intent);
			not.showStatusBarNotificationError(R.string.internetadresseNichtErreichbar);
			e.printStackTrace();

		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (fileOutputStream != null) {
					fileOutputStream.flush();
					fileOutputStream.close();
				}
			} catch (IOException e) {
				Notifications not = new Notifications(this.context, intent);
				not.showStatusBarNotificationError(R.string.internetadresseNichtErreichbar);
				e.printStackTrace();
			}
		}
		return bytesRead;

	}

	// @Override
	// protected void onProgressUpdate(Integer... progress) {
	// super.onProgressUpdate(progress);
	// progressDialog.dismiss();
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// progressDialog = new ProgressDialog(context);
	// progressDialog.setCancelable(true);
	// progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	// progressDialog.setMessage("Connecting to site...");
	// progressDialog.show();
	// }

	@Override
	protected void onPostExecute(Long result) {
		Log.d(TAG, "onPostExecute");
		result = bytesRead;
	}

}