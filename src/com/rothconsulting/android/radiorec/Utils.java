package com.rothconsulting.android.radiorec;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class Utils {

	private static final String TAG = Utils.class.getSimpleName();

	protected static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						Log.d(TAG, "Wir sind connected!");
						return true;
					}
				}
			}
		}
		Log.d(TAG, "Keine Connectivity");
		return false;
	}

	protected static void resumeProgressBarAnimation(ProgressBar pb) {
		if (pb.getVisibility() == View.VISIBLE) {
			pb.setVisibility(View.INVISIBLE);
			pb.setVisibility(View.VISIBLE);
		}
	}

	protected static ProgressDialog prepareProgressDialog(Context context) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(true);
		progressDialog.setMessage("Loading...");
		progressDialog.setTitle(R.string.app_name);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		return progressDialog;
	}

	protected static Notifications getNotifInstance(Context context,
			Class<?> clss) {
		return new Notifications(context, new Intent(context, clss));
	}

	protected static boolean hasValidKey() {
		String key = RadioRecPlus.ANTI_ADS_KEY;
		if (key != null && key.startsWith("rR+") && key.endsWith("so@p")) {
			return true;
		}
		return false;
	}

	protected static void storePreferences(Context context) {
		SharedPreferences settings = context.getSharedPreferences(
				Constants.PREFERENCES_FILE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(Constants.SELECTED_STATION_INDEX,
				RadioRecPlus.SELECTED_STATION_INDEX);
		editor.putInt(Constants.SELECTED_STATION_ICON,
				RadioRecPlus.SELECTED_STATION_ICON);
		editor.putString(Constants.SELECTED_STATION_NAME,
				RadioRecPlus.SELECTED_STATION_NAME);
		editor.putString(Constants.SELECTED_STATION_STREAM,
				RadioRecPlus.URL_LIVE_STREAM);
		editor.putString(Constants.SELECTED_STATION_HOMEPAGE,
				RadioRecPlus.URL_HOMEPAGE);
		editor.putString(Constants.SELECTED_STATION_WEBCAM,
				RadioRecPlus.URL_WEBCAM);
		editor.putString(Constants.SELECTED_STATION_CONTACT,
				RadioRecPlus.URL_CONTACT);
		editor.putString(Constants.ANTI_ADS_KEY, RadioRecPlus.ANTI_ADS_KEY);
		editor.commit();
	}

	protected static void getPreferences(Context context) {
		// Restore preferences
		SharedPreferences settings = context.getSharedPreferences(
				Constants.PREFERENCES_FILE, 0);
		Log.d(TAG, "SELECTED_STATION_NAME vorher: "
				+ RadioRecPlus.SELECTED_STATION_NAME);
		Log.d(TAG, "SELECTED_STATION_INDEX vorher: "
				+ RadioRecPlus.SELECTED_STATION_INDEX);
		RadioRecPlus.SELECTED_STATION_INDEX = settings.getInt(
				Constants.SELECTED_STATION_INDEX, -1);
		RadioRecPlus.SELECTED_STATION_NAME = settings.getString(
				Constants.SELECTED_STATION_NAME,
				RadioRecPlus.SELECTED_STATION_NAME);
		RadioRecPlus.URL_LIVE_STREAM = settings
				.getString(Constants.SELECTED_STATION_STREAM,
						RadioRecPlus.URL_LIVE_STREAM);
		RadioRecPlus.URL_HOMEPAGE = settings.getString(
				Constants.SELECTED_STATION_HOMEPAGE, RadioRecPlus.URL_HOMEPAGE);
		RadioRecPlus.URL_WEBCAM = settings.getString(
				Constants.SELECTED_STATION_WEBCAM, RadioRecPlus.URL_WEBCAM);
		RadioRecPlus.URL_CONTACT = settings.getString(
				Constants.SELECTED_STATION_CONTACT, RadioRecPlus.URL_CONTACT);
		RadioRecPlus.ANTI_ADS_KEY = settings.getString(Constants.ANTI_ADS_KEY,
				RadioRecPlus.ANTI_ADS_KEY);
		Log.d(TAG, "SELECTED_STATION_NAME nachher: "
				+ RadioRecPlus.SELECTED_STATION_NAME);
		Log.d(TAG, "SELECTED_STATION_INDEX nachher: "
				+ RadioRecPlus.SELECTED_STATION_INDEX);
	}

}
