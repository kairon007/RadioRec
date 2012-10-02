package com.rothconsulting.android.radiorec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeSet;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.ProgressBar;

public class Utils {

	private static final String TAG = Utils.class.getSimpleName();

	/**
	 * Wenn showNotification false, kann der Intent null sein!
	 * 
	 * @param context
	 * @param intent
	 * @param showNotification
	 * @return
	 */
	protected boolean isNetworkAvailable(Context context, Intent intent,
			boolean showNotification) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] infos = connectivity.getAllNetworkInfo();
			if (infos != null) {
				for (NetworkInfo ni : infos) {
					if (ni.getState() == NetworkInfo.State.CONNECTED) {
						Utils.log(TAG, "Wir sind connected!");
						return true;
					}
				}
			}
		}
		Utils.log(TAG, "Keine Connectivity");

		if (showNotification) {
			Notifications notifications = new Notifications(context, intent);
			notifications
					.showStatusBarNotificationError(R.string.networkNotAvailable);
		}
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
		String key = Constants.ANTI_ADS_VALUE;
		if (key != null && key.startsWith("rR+") && key.endsWith("so@p")) {
			return true;
		}
		return false;
	}

	protected void storePreferences(Context context) {
		SharedPreferences settings = context.getSharedPreferences(
				Constants.PREFERENCES_FILE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(Constants.SELECTED_STATION_INDEX_KEY,
				Constants.SELECTED_STATION_INDEX_VALUE);
		editor.putInt(Constants.SELECTED_STATION_ICON_KEY,
				Constants.SELECTED_STATION_ICON_VALUE);
		editor.putString(Constants.SELECTED_STATION_NAME_KEY,
				Constants.SELECTED_STATION_NAME_VALUE);
		editor.putString(Constants.SELECTED_STATION_STREAM_KEY,
				Constants.URL_LIVE_STREAM_VALUE);
		editor.putString(Constants.SELECTED_STATION_HOMEPAGE_KEY,
				Constants.URL_HOMEPAGE_VALUE);
		editor.putString(Constants.SELECTED_STATION_WEBCAM_KEY,
				Constants.URL_WEBCAM_VALUE);
		editor.putString(Constants.SELECTED_STATION_CONTACT_KEY,
				Constants.URL_CONTACT_VALUE);
		editor.putString(Constants.ANTI_ADS_KEY, Constants.ANTI_ADS_VALUE);
		editor.putString(Constants.SD_CARD_PATH_KEY,
				Constants.SD_CARD_PATH_VALUE);
		editor.putInt(Constants.BUFFER_KEY, Constants.BUFFER_VALUE);
		editor.putBoolean(Constants.CLOSE_APP_TIMER_END_KEY,
				Constants.CLOSE_APP_TIMER_END_VALUE);
		editor.commit();
	}

	protected void getPreferences(Context context) {
		// Restore preferences
		SharedPreferences settings = context.getSharedPreferences(
				Constants.PREFERENCES_FILE, 0);
		Constants.SELECTED_STATION_INDEX_VALUE = settings.getInt(
				Constants.SELECTED_STATION_INDEX_KEY, -1);
		Constants.SELECTED_STATION_NAME_VALUE = settings.getString(
				Constants.SELECTED_STATION_NAME_KEY,
				Constants.SELECTED_STATION_NAME_VALUE);
		Constants.URL_LIVE_STREAM_VALUE = settings.getString(
				Constants.SELECTED_STATION_STREAM_KEY,
				Constants.URL_LIVE_STREAM_VALUE);
		Constants.URL_HOMEPAGE_VALUE = settings.getString(
				Constants.SELECTED_STATION_HOMEPAGE_KEY,
				Constants.URL_HOMEPAGE_VALUE);
		Constants.URL_WEBCAM_VALUE = settings.getString(
				Constants.SELECTED_STATION_WEBCAM_KEY,
				Constants.URL_WEBCAM_VALUE);
		Constants.URL_CONTACT_VALUE = settings.getString(
				Constants.SELECTED_STATION_CONTACT_KEY,
				Constants.URL_CONTACT_VALUE);
		Constants.ANTI_ADS_VALUE = settings.getString(Constants.ANTI_ADS_KEY,
				Constants.ANTI_ADS_VALUE);
		Constants.SD_CARD_PATH_VALUE = settings.getString(
				Constants.SD_CARD_PATH_KEY, Constants.DEFAULT_SD_CARD_PATH);
		Constants.BUFFER_VALUE = settings.getInt(Constants.BUFFER_KEY,
				Constants.DEFAULT_BUFFER);
		Constants.CLOSE_APP_TIMER_END_VALUE = settings.getBoolean(
				Constants.CLOSE_APP_TIMER_END_KEY,
				Constants.CLOSE_APP_TIMER_END_VALUE);
	}

	protected String getAppVersionName(Context context, Class<?> cls) {
		try {
			ComponentName comp = new ComponentName(context, cls);
			PackageInfo pinfo = context.getPackageManager().getPackageInfo(
					comp.getPackageName(), 0);
			return pinfo.versionName;
		} catch (android.content.pm.PackageManager.NameNotFoundException e) {
			return "";
		}
	}

	// /**
	// * Radio Kingstonhot.de hat immer Donnerstags eine Live Sendung. Ab
	// Freitag
	// * kann man diese als mp3 hören.
	// *
	// * @return Dateiname 'KingstonHotRadioYYMMDD.mp3'
	// */
	// protected String getKingstonHotFileName() {
	// // 1:SO 2:MO 3:DI 4:MI 5:DO 6:FR 7:SA
	// String dateiName = "KingstonHotRadio";
	// Calendar cal = Calendar.getInstance();
	// int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	// Utils.log(TAG, "Calendar.DAY_OF_WEEK=" + dayOfWeek);
	// if (dayOfWeek > 5) {
	// cal.add(Calendar.DAY_OF_WEEK, -(dayOfWeek - 5));
	// } else if (dayOfWeek <= 5) {
	// cal.add(Calendar.DAY_OF_WEEK, -(dayOfWeek + 2));
	// }
	//
	// int year = cal.get(Calendar.YEAR) - 2000;
	// String month = "" + (cal.get(Calendar.MONTH) + 1);
	// if (month.length() == 1) {
	// month = "0" + month;
	// }
	// String day = "" + cal.get(Calendar.DATE);
	// if (day.length() == 1) {
	// day = "0" + day;
	// }
	// Utils.log(TAG, year + "-" + month + "-" + day);
	// return dateiName + year + month + day + ".mp3";
	// }

	protected HashMap sortValuesInHashMap(HashMap hashMap) {

		// to hold the result
		HashMap sortedMap = new LinkedHashMap();

		List yourMapKeys = new ArrayList(hashMap.keySet());
		List yourMapValues = new ArrayList(hashMap.values());
		TreeSet sortedSet = new TreeSet(yourMapValues);
		Object[] sortedArray = sortedSet.toArray();
		int size = sortedArray.length;

		for (int i = 0; i < size; i++) {
			sortedMap.put(
					yourMapKeys.get(yourMapValues.indexOf(sortedArray[i])),
					sortedArray[i]);
		}

		return sortedMap;
	}

	protected Drawable resizeImage(int resId, Context context, int width,
			int height) {
		Bitmap origBitmap = BitmapFactory.decodeResource(
				context.getResources(), resId);
		Bitmap resizedBitmap = Bitmap.createScaledBitmap(origBitmap, width,
				height, false);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(resizedBitmap);
		return bitmapDrawable;
	}

	protected HashMap<String, Object> fillStationHashMap(String stationName,
			int icon, int iconSmall, String stream, String homepage,
			String webcam, String contact, String sprache, String land,
			String stil) {

		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("name", stationName);
		m.put("icon", icon);
		m.put("icon_small", iconSmall);
		m.put("stream", stream);
		m.put("homepage", homepage);
		m.put("webcam", webcam);
		m.put("email", contact);
		m.put("sprache", sprache);
		m.put("land", land);
		m.put("stil", stil);

		return m;
	}

	protected static String getExceptionInfosAsString(Exception e) {
		String result = "***** Exception-Info Start *****\n";
		if (e != null && e.getStackTrace() != null) {
			result += "--- Stacktrace Start ---\n";
			for (StackTraceElement ste : e.getStackTrace()) {
				result += "\n  " + ste;
			}
			result += "\n--- Stacktrace End ---\n";
		}
		result += "\n--- ";
		result += "Message=" + e.getMessage();
		result += "\n--- ";
		result += "LocalizedMessage=" + e.getLocalizedMessage();
		result += "\n--- ";
		result += "Cause=" + e.getCause();
		result += "\n--- ";
		result += "toString=" + e.toString();
		result += "\n--- ";
		result += "\n***** Exception-Info End *****\n";

		return result;
	}

	public static void log(String tag, String message) {
		// Log.d(tag, message);
	}

	public static String getHhMmFromMinutes(int minutes) {
		int hours = minutes / 60;
		int min = minutes % 60;
		return hours + ":" + getFormatedMinSec(min);
	}

	public static String getHhMmSs(long millis) {
		int seconds = (int) (millis / 1000) % 60;
		int minutes = (int) ((millis / (1000 * 60)) % 60);
		int hours = (int) ((millis / (1000 * 60 * 60)) % 24);
		return hours + ":" + getFormatedMinSec(minutes) + ":"
				+ getFormatedMinSec(seconds);
	}

	public static String getFormatedMinSec(int minSec) {
		if (minSec < 10) {
			return "0" + minSec;
		} else {
			return "" + minSec;
		}
	}

	public static List<String> getStationNameList(
			ArrayList<HashMap<String, Object>> stationList, String searchName) {

		List<String> result = new ArrayList<String>();

		for (HashMap<String, Object> station : stationList) {
			if (searchName == null
					|| ((String) station.get("name")).toUpperCase().contains(
							searchName.toUpperCase())) {
				result.add("" + station.get("name"));
			}
		}

		return result;
	}

	public static int getSpinnerPosition(
			ArrayList<HashMap<String, Object>> stationList, String searchName) {

		int position = 0;
		for (int i = 0; i < stationList.size(); i++) {
			HashMap<String, Object> stationMap = stationList.get(i);
			String stationName = (String) stationMap.get("name");
			if (stationName.toUpperCase().contains(searchName.toUpperCase())) {
				return i;
			}
		}
		return position;
	}
}
