package com.rothconsulting.android.radiorec;

import java.util.Calendar;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class Utils {

	private static final String TAG = Utils.class.getSimpleName();

	protected boolean isNetworkAvailable(Context context) {
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

	protected void resumeProgressBarAnimation(ProgressBar pb) {
		if (pb.getVisibility() == View.VISIBLE) {
			pb.setVisibility(View.INVISIBLE);
			pb.setVisibility(View.VISIBLE);
		}
	}

	protected ProgressDialog prepareProgressDialog(Context context) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(true);
		progressDialog.setMessage(context.getText(R.string.verbinde));
		progressDialog.setTitle(R.string.app_name);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		return progressDialog;
	}

	protected Notifications getNotifInstance(Context context, Class<?> clss) {
		return new Notifications(context, new Intent(context, clss));
	}

	protected boolean hasValidKey() {
		String key = Constants.THE_ANTI_ADS_KEY;
		if (key != null && key.startsWith("rR+") && key.endsWith("so@p")) {
			return true;
		}
		return false;
	}

	protected void storePreferences(Context context) {
		SharedPreferences settings = context.getSharedPreferences(
				Constants.PREFERENCES_FILE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(Constants.SELECTED_STATION_INDEX,
				Constants.THE_SELECTED_STATION_INDEX);
		editor.putInt(Constants.SELECTED_STATION_ICON,
				Constants.THE_SELECTED_STATION_ICON);
		editor.putString(Constants.SELECTED_STATION_NAME,
				Constants.THE_SELECTED_STATION_NAME);
		editor.putString(Constants.SELECTED_STATION_STREAM,
				Constants.THE_URL_LIVE_STREAM);
		editor.putString(Constants.SELECTED_STATION_HOMEPAGE,
				Constants.THE_URL_HOMEPAGE);
		editor.putString(Constants.SELECTED_STATION_WEBCAM,
				Constants.THE_URL_WEBCAM);
		editor.putString(Constants.SELECTED_STATION_CONTACT,
				Constants.THE_URL_CONTACT);
		editor.putString(Constants.ANTI_ADS_KEY, Constants.THE_ANTI_ADS_KEY);
		editor.putString(Constants.SD_CARD_PATH, Constants.THE_SD_CARD_PATH);
		editor.putInt(Constants.BUFFER, Constants.THE_BUFFER);
		editor.commit();
	}

	protected void getPreferences(Context context) {
		// Restore preferences
		SharedPreferences settings = context.getSharedPreferences(
				Constants.PREFERENCES_FILE, 0);
		Constants.THE_SELECTED_STATION_INDEX = settings.getInt(
				Constants.SELECTED_STATION_INDEX, -1);
		Constants.THE_SELECTED_STATION_NAME = settings.getString(
				Constants.SELECTED_STATION_NAME,
				Constants.THE_SELECTED_STATION_NAME);
		Constants.THE_URL_LIVE_STREAM = settings.getString(
				Constants.SELECTED_STATION_STREAM,
				Constants.THE_URL_LIVE_STREAM);
		Constants.THE_URL_HOMEPAGE = settings
				.getString(Constants.SELECTED_STATION_HOMEPAGE,
						Constants.THE_URL_HOMEPAGE);
		Constants.THE_URL_WEBCAM = settings.getString(
				Constants.SELECTED_STATION_WEBCAM, Constants.THE_URL_WEBCAM);
		Constants.THE_URL_CONTACT = settings.getString(
				Constants.SELECTED_STATION_CONTACT, Constants.THE_URL_CONTACT);
		Constants.THE_ANTI_ADS_KEY = settings.getString(Constants.ANTI_ADS_KEY,
				Constants.THE_ANTI_ADS_KEY);
		Constants.THE_SD_CARD_PATH = settings.getString(Constants.SD_CARD_PATH,
				Constants.DEFAULT_SD_CARD_PATH);
		Constants.THE_BUFFER = settings.getInt(Constants.BUFFER,
				Constants.DEFAULT_BUFFER);
	}

	public String getAppVersionName(Context context, Class<?> cls) {
		try {
			ComponentName comp = new ComponentName(context, cls);
			PackageInfo pinfo = context.getPackageManager().getPackageInfo(
					comp.getPackageName(), 0);
			return pinfo.versionName;
		} catch (android.content.pm.PackageManager.NameNotFoundException e) {
			return "";
		}
	}

	/**
	 * Radio Kingstonhot.de hat immer Donnerstags eine Live Sendung. Ab Freitag
	 * kann man diese als mp3 hören.
	 * 
	 * @return Dateiname 'KingstonHotRadioYYMMDD.mp3'
	 */
	protected String getKingstonHotFileName() {
		// 1:SO 2:MO 3:DI 4:MI 5:DO 6:FR 7:SA
		String dateiName = "KingstonHotRadio";
		Calendar cal = Calendar.getInstance();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		Log.d(TAG, "Calendar.DAY_OF_WEEK=" + dayOfWeek);
		if (dayOfWeek > 5) {
			cal.add(Calendar.DAY_OF_WEEK, -(dayOfWeek - 5));
		} else if (dayOfWeek <= 5) {
			cal.add(Calendar.DAY_OF_WEEK, -(dayOfWeek + 2));
		}

		int year = cal.get(Calendar.YEAR) - 2000;
		String month = "" + (cal.get(Calendar.MONTH) + 1);
		if (month.length() == 1) {
			month = "0" + month;
		}
		String day = "" + cal.get(Calendar.DATE);
		if (day.length() == 1) {
			day = "0" + day;
		}
		Log.d(TAG, year + "-" + month + "-" + day);
		return dateiName + year + month + day + ".mp3";
	}
}
