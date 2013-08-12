package com.rothconsulting.android.radiorec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

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
	public static boolean isNetworkAvailable(Context context, Intent intent, boolean showNotification) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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
			notifications.showStatusBarNotificationError(R.string.networkNotAvailable);
		}
		return false;
	}

	public static ProgressDialog prepareProgressDialog(Context context) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(true);
		progressDialog.setMessage(context.getText(R.string.verbinde));
		progressDialog.setTitle(R.string.app_name);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		return progressDialog;
	}

	public static Notifications getNotifInstance(Context context, Class<?> clss) {
		return new Notifications(context, new Intent(context, clss));
	}

	public static boolean hasValidKey() {
		String key = Constants.ANTI_ADS_VALUE;
		if (key != null && ((key.trim().startsWith("rR+") && key.trim().endsWith("so@p")) || (key.trim().startsWith("rr") && key.trim().endsWith("so")))) {
			return true;
		}
		return false;
	}

	public static void storePreferences(Context context) {
		SharedPreferences settings = context.getSharedPreferences(Constants.PREFERENCES_FILE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(Constants.SELECTED_STATION_INDEX_KEY, Constants.SELECTED_STATION_INDEX_VALUE);
		editor.putInt(Constants.SELECTED_STATION_ICON_KEY, Constants.SELECTED_STATION_ICON_VALUE);
		editor.putString(Constants.SELECTED_STATION_NAME_KEY, Constants.SELECTED_STATION_NAME_VALUE);
		editor.putString(Constants.SELECTED_STATION_STREAM_KEY, Constants.URL_LIVE_STREAM_VALUE);
		editor.putString(Constants.SELECTED_STATION_HOMEPAGE_KEY, Constants.URL_HOMEPAGE_VALUE);
		editor.putString(Constants.SELECTED_STATION_WEBCAM_KEY, Constants.URL_WEBCAM_VALUE);
		editor.putString(Constants.SELECTED_STATION_CONTACT_KEY, Constants.URL_CONTACT_VALUE);
		editor.putString(Constants.ANTI_ADS_KEY, Constants.ANTI_ADS_VALUE);
		editor.putString(Constants.SD_CARD_PATH_KEY, Constants.SD_CARD_PATH_VALUE);
		editor.putInt(Constants.BUFFER_KEY, Constants.BUFFER_VALUE);
		editor.putBoolean(Constants.CLOSE_APP_TIMER_END_KEY, Constants.CLOSE_APP_TIMER_END_VALUE);
		editor.commit();
	}

	public static void getPreferences(Context context) {
		// Restore preferences
		SharedPreferences settings = context.getSharedPreferences(Constants.PREFERENCES_FILE, 0);
		Constants.SELECTED_STATION_INDEX_VALUE = settings.getInt(Constants.SELECTED_STATION_INDEX_KEY, -1);
		Constants.SELECTED_STATION_NAME_VALUE = settings.getString(Constants.SELECTED_STATION_NAME_KEY, Constants.SELECTED_STATION_NAME_VALUE);
		Constants.URL_LIVE_STREAM_VALUE = settings.getString(Constants.SELECTED_STATION_STREAM_KEY, Constants.URL_LIVE_STREAM_VALUE);
		Constants.URL_HOMEPAGE_VALUE = settings.getString(Constants.SELECTED_STATION_HOMEPAGE_KEY, Constants.URL_HOMEPAGE_VALUE);
		Constants.URL_WEBCAM_VALUE = settings.getString(Constants.SELECTED_STATION_WEBCAM_KEY, Constants.URL_WEBCAM_VALUE);
		Constants.URL_CONTACT_VALUE = settings.getString(Constants.SELECTED_STATION_CONTACT_KEY, Constants.URL_CONTACT_VALUE);
		Constants.ANTI_ADS_VALUE = settings.getString(Constants.ANTI_ADS_KEY, Constants.ANTI_ADS_VALUE);
		Constants.SD_CARD_PATH_VALUE = settings.getString(Constants.SD_CARD_PATH_KEY, Constants.DEFAULT_SD_CARD_PATH);
		Constants.BUFFER_VALUE = settings.getInt(Constants.BUFFER_KEY, Constants.DEFAULT_BUFFER);
		Constants.CLOSE_APP_TIMER_END_VALUE = settings.getBoolean(Constants.CLOSE_APP_TIMER_END_KEY, Constants.CLOSE_APP_TIMER_END_VALUE);
		Constants.ROTATION_OFF_VALUE = settings.getBoolean(Constants.ROTATION_OFF_KEY, Constants.ROTATION_OFF_VALUE);
	}

	public String getAppVersionName(Context context, Class<?> cls) {
		try {
			ComponentName comp = new ComponentName(context, cls);
			PackageInfo pinfo = context.getPackageManager().getPackageInfo(comp.getPackageName(), 0);
			return pinfo.versionName;
		} catch (android.content.pm.PackageManager.NameNotFoundException e) {
			return "";
		}
	}

	protected Drawable resizeImage(int resId, Context context, int width, int height) {
		Bitmap origBitmap = BitmapFactory.decodeResource(context.getResources(), resId);
		Bitmap resizedBitmap = Bitmap.createScaledBitmap(origBitmap, width, height, false);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(resizedBitmap);
		return bitmapDrawable;
	}

	protected HashMap<String, Object> fillStationHashMap(String stationName, int icon, int iconSmall, String stream, String homepage, String webcam,
			String contact, String sprache, String land, String stil) {

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

	public static String getExceptionInfosAsString(Exception e) {
		// StringWriter sw = new StringWriter();
		// e.printStackTrace(new PrintWriter(sw));
		// String exceptionAsString = sw.toString();

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
		return hours + ":" + getFormatedMinSec(minutes) + ":" + getFormatedMinSec(seconds);
	}

	public static String getFormatedMinSec(int minSec) {
		if (minSec < 10) {
			return "0" + minSec;
		} else {
			return "" + minSec;
		}
	}

	public static List<String> getStationNameList(ArrayList<HashMap<String, Object>> stationList, String searchName) {
		return getStationAttributList(stationList, searchName, "name");
	}

	public static List<String> getStationStreamList(ArrayList<HashMap<String, Object>> stationList, String searchName) {
		return getStationAttributList(stationList, searchName, "stream");
	}

	private static List<String> getStationAttributList(ArrayList<HashMap<String, Object>> stationList, String searchName, String stationAttribut) {

		List<String> result = new ArrayList<String>();

		for (HashMap<String, Object> station : stationList) {
			if (searchName == null || ((String) station.get(stationAttribut)).toUpperCase().contains(searchName.toUpperCase())) {
				result.add("" + station.get(stationAttribut));
			}
		}

		return result;
	}

	public static ArrayList<HashMap<String, Object>> sortStationsByName(ArrayList<HashMap<String, Object>> stationList) {

		TreeMap<String, HashMap<String, Object>> sortedMap = new TreeMap<String, HashMap<String, Object>>(new StringComperator());
		for (HashMap<String, Object> station : stationList) {
			sortedMap.put((String) station.get("name"), station);
		}
		return new ArrayList<HashMap<String, Object>>(sortedMap.values());
	}

	public static HashMap<String, Object> getFullStation(ArrayList<HashMap<String, Object>> stationList, String searchName) {

		for (HashMap<String, Object> station : stationList) {
			if (searchName == null || ((String) station.get("name")).toUpperCase().contains(searchName.toUpperCase())) {

				return station;
			}
		}
		return null;
	}

	public static int getSpinnerPosition(ArrayList<HashMap<String, Object>> stationList, String searchName) {

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

	public static boolean isAtLeastHoneycomb() {
		// Can use static final constants like HONEYCOMB, declared in later
		// versions
		// of the OS since they are inlined at compile time. This is guaranteed
		// behavior.
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	public static boolean isAtLeastHoneycombAndTablet(Context context) {
		return isAtLeastHoneycomb() && isTablet(context);
	}

	public static boolean isLandscape(Context context) {
		if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			return true;
		} else {
			return false;
		}
	}

	public static void showEmptyFavAlertDialog(final Context context) {
		final Builder b = new AlertDialog.Builder(context);
		b.setCancelable(true);
		b.setTitle(R.string.info);
		String text = context.getString(R.string.nochKeineFavoriten);
		b.setMessage(text);
		b.setPositiveButton(android.R.string.ok, null);
		b.show();
	}

}
