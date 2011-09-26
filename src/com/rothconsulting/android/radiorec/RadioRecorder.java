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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import android.app.ProgressDialog;
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
	private ProgressDialog connectionProgressDialog;

	public RadioRecorder(Context theContext, Intent theIntent) {
		this.context = theContext;
		this.intent = theIntent;
	}

	@Override
	protected Long doInBackground(URL... urls) {

		Log.d(TAG, "startRecording");
		BufferedInputStream buffInputStream = null;
		BufferedOutputStream buffOutputStream = null;
		bytesRead = 0;
		bytesReadTmp = 0;
		this.publishProgress(1);

		try {
			File radioRecorderDirectory = new File("/"
					+ Constants.THE_SD_CARD_PATH + "/");
			radioRecorderDirectory.mkdirs();

			buffInputStream = new BufferedInputStream(urls[0].openStream());
			Log.d(TAG, "url.openStream()");

			buffOutputStream = new BufferedOutputStream(new FileOutputStream(
					urls[1].getFile()));
			Log.d(TAG, "FileOutputStream: " + urls[1].getFile());
			Utils utils = new Utils();
			utils.getNotifInstance(context, RadioRecorder.class)
					.showStatusBarNotificationRecording();

			connectionProgressDialog.dismiss();
			int c = 0;
			while ((c = buffInputStream.read()) != -1 && !isCancelled()) {
				// Log.d(LOG_TAG, "bytesRead=" + bytesRead);
				buffOutputStream.write(c);
				bytesRead++;
				bytesReadTmp++;
				if (bytesReadTmp > 1000) {
					buffOutputStream.flush();
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
				not.hideStatusBarNotification(Constants.NOTIFICATION_ID_RECORDING);
			}
		} catch (FileNotFoundException fnfe) {
			Notifications not = new Notifications(this.context, intent);
			not.showStatusBarNotificationError(R.string.kannNichtAufSdCardSchreiben);
			not.hideStatusBarNotification(Constants.NOTIFICATION_ID_RECORDING);
			fnfe.printStackTrace();
		} catch (IOException e) {
			Notifications not = new Notifications(this.context, intent);
			not.showStatusBarNotificationError(R.string.internetadresseNichtErreichbar);
			not.hideStatusBarNotification(Constants.NOTIFICATION_ID_RECORDING);
			e.printStackTrace();
		} finally {
			try {
				if (buffInputStream != null) {
					buffInputStream.close();
				}
				if (buffOutputStream != null) {
					buffOutputStream.flush();
					buffOutputStream.close();
				}
			} catch (FileNotFoundException fnfe) {
				Notifications not = new Notifications(this.context, intent);
				not.showStatusBarNotificationError(R.string.kannNichtAufSdCardSchreiben);
				not.hideStatusBarNotification(Constants.NOTIFICATION_ID_RECORDING);
				fnfe.printStackTrace();
			} catch (IOException e) {
				Notifications not = new Notifications(this.context, intent);
				not.showStatusBarNotificationError(R.string.internetadresseNichtErreichbar);
				not.hideStatusBarNotification(Constants.NOTIFICATION_ID_RECORDING);
				e.printStackTrace();
			}
			connectionProgressDialog.dismiss();
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

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPreExecute() {
		Log.d(TAG, "prepareProgressDialog");
		Utils utils = new Utils();
		connectionProgressDialog = utils.prepareProgressDialog(context);
		connectionProgressDialog.show();
	}

}