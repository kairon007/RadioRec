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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.rothconsulting.android.radiorec.network.icy.IcyGetRequest;

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

	/**
	 * urls[0] = input urls[1] = output
	 */
	@Override
	protected Long doInBackground(URL... urls) {

		Utils.log(TAG, "startRecording");
		BufferedInputStream buffInputStream = null;
		BufferedOutputStream buffOutputStream = null;
		bytesRead = 0;
		bytesReadTmp = 0;
		this.publishProgress(1);

		try {
			File radioRecorderDirectory = new File("/" + Constants.SD_CARD_PATH_VALUE + "/");
			radioRecorderDirectory.mkdirs();
			Utils.log(TAG, "Stream Buffer=" + Constants.BUFFER_VALUE);
			if (Constants.BUFFER_VALUE <= 0) {
				Constants.BUFFER_VALUE = Constants.DEFAULT_BUFFER;
			}
			if (Utils.isPlatformBelow_4_4()) {
				buffInputStream = new BufferedInputStream(urls[0].openStream(), Constants.BUFFER_VALUE);
			} else {
				// Icy source from: https://gist.github.com/toms972/8842217 (Thanks!)
				IcyGetRequest request = new IcyGetRequest(urls[0].toString());
				HttpResponse response = request.get();
				HttpEntity entity = response.getEntity();
				buffInputStream = new BufferedInputStream(entity.getContent(), Constants.BUFFER_VALUE);
			}
			Utils.log(TAG, "url.openStream()");

			buffOutputStream = new BufferedOutputStream(new FileOutputStream(urls[1].getFile()), Constants.BUFFER_VALUE);
			Utils.log(TAG, "FileOutputStream: " + urls[1].getFile());
			Utils.getNotifInstance(context, RadioRecorder.class).showStatusBarNotificationRecording();

			connectionProgressDialog.dismiss();
			int c = 0;
			while ((c = buffInputStream.read()) != -1 && !isCancelled()) {
				// Utils.log(LOG_TAG, "bytesRead=" + bytesRead);
				buffOutputStream.write(c);
				bytesRead++;
				bytesReadTmp++;
				if (bytesReadTmp > 10000) {
					buffOutputStream.flush();
					bytesReadTmp = 0;
				}
			}
			Utils.log(TAG, "******* End reading stream. bytesRead=" + bytesRead);
			if (isCancelled()) {
				Utils.log(TAG, "******* Task cancelled!!!");
			} else {
				Utils.log(TAG, "******* End of inputStream. c=" + c);
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
		Utils.log(TAG, "onPostExecute");
		result = bytesRead;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPreExecute() {
		Utils.log(TAG, "prepareProgressDialog");
		connectionProgressDialog = Utils.prepareProgressDialog(context);
		connectionProgressDialog.show();
	}

}