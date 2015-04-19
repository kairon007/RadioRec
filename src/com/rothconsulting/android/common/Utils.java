package com.rothconsulting.android.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.rothconsulting.android.cast.CastHelper;
import com.rothconsulting.android.radiorec.Constants;
import com.rothconsulting.android.radiorec.Notifications;
import com.rothconsulting.android.radiorec.R;
import com.rothconsulting.android.radiorec.Stations;
import com.rothconsulting.android.radiorec.StringComperator;

public class Utils {

	private static final String TAG = Utils.class.getSimpleName();
	private static Hashtable<Integer, String> allDrawables;

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
						log(TAG, "Wir sind connected!");
						return true;
					}
				}
			}
		}
		log(TAG, "Keine Connectivity");

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

	public static void clearPlayingNotification(Context context) {
		Notifications.getNotifInstance(context, null).hideStatusBarNotification(Constants.NOTIFICATION_ID_ERROR_CONNECTION);
		Notifications.getNotifInstance(context, null).hideStatusBarNotification(Constants.NOTIFICATION_ID_RADIO_IS_PLAYING);
	}

	public static boolean hasValidKey() {
		SharedPreferences settings = CustomApplication.getAppContext().getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);
		String key = settings.getString(Constants.ANTI_ADS_KEY, Constants.ANTI_ADS_VALUE);
		if (key != null && ((key.trim().startsWith("rR+") && key.trim().endsWith("so@p")) || (key.trim().startsWith("rr") && key.trim().endsWith("so")))) {
			return true;
		}
		return false;
	}

	public static void storePreferences(Context context) {
		SharedPreferences settings = context.getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);
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
		editor.putBoolean(Constants.WRITE_TO_EXT_STORAGE_KEY, Constants.WRITE_TO_EXT_STORAGE_VALUE);
		editor.commit();
	}

	public static void getPreferences(Context context) {
		// Restore preferences
		SharedPreferences settings = context.getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);
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
		Constants.WRITE_TO_EXT_STORAGE_VALUE = settings.getBoolean(Constants.WRITE_TO_EXT_STORAGE_KEY, Constants.WRITE_TO_EXT_STORAGE_VALUE);
	}

	public static String getAppVersionName(Context context, Class<?> cls) {
		try {
			ComponentName comp = new ComponentName(context, cls);
			PackageInfo pinfo = context.getPackageManager().getPackageInfo(comp.getPackageName(), 0);
			return pinfo.versionName;
		} catch (NameNotFoundException e) {
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	protected Drawable resizeImage(int resId, Context context, int width, int height) {
		Bitmap origBitmap = BitmapFactory.decodeResource(context.getResources(), resId);
		Bitmap resizedBitmap = Bitmap.createScaledBitmap(origBitmap, width, height, false);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), resizedBitmap);
		return bitmapDrawable;
	}

	public static void log(String tag, String message) {
		Log.d(tag, message);
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
		return getStationAttributList(stationList, searchName, Stations.NAME);
	}

	public static List<String> getStationStreamList(ArrayList<HashMap<String, Object>> stationList, String searchName) {
		return getStationAttributList(stationList, searchName, Stations.STREAM);
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
			sortedMap.put((String) station.get(Stations.NAME), station);
		}
		return new ArrayList<HashMap<String, Object>>(sortedMap.values());
	}

	public static HashMap<String, Object> getFullStation(ArrayList<HashMap<String, Object>> stationList, String searchName) {

		for (HashMap<String, Object> station : stationList) {
			if (searchName == null || ((String) station.get(Stations.NAME)).toUpperCase().contains(searchName.toUpperCase())) {

				return station;
			}
		}
		return null;
	}

	public static int getSpinnerPosition(Activity activity, ArrayList<HashMap<String, Object>> stationList, String searchName) {

		int initPosition = 0;
		try {
			for (int i = 0; i < stationList.size(); i++) {
				HashMap<String, Object> stationMap = stationList.get(i);
				String stationName = (String) stationMap.get(Stations.NAME);
				// debug
				if (stationName == null || searchName == null) {
					AnalyticsUtil.sendEvent(AnalyticsUtil.ERROR, "Utils.getSpinnerPosition", "stationName=" + stationName + "searchName=" + searchName);
				}
				if (stationName.toUpperCase().contains(searchName.toUpperCase())) {
					return i;
				}
			}
			Toast.makeText(activity, activity.getString(R.string.clearCannotFindStation), Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(CustomApplication.getAppContext(), "Sorry cannot find station. Please delete your data and try again", Toast.LENGTH_LONG).show();
			return initPosition;
		}
		return initPosition;
	}

	/**
	 * Android Platform 2.3.0 = Level 9
	 */
	public static boolean isPlatformBelow_2_3_0() {
		if (Build.VERSION.SDK_INT < 9) {
			return true;
		}
		return false;
	}

	/**
	 * Android Platform 3.0 = Level 11
	 */
	public static boolean isPlatformBelow_3_0() {
		if (Build.VERSION.SDK_INT < 11) {
			return true;
		}
		return false;
	}

	/**
	 * Android Platform 4.0 = Level 14
	 */
	public static boolean isPlatformBelow_4_0() {
		if (Build.VERSION.SDK_INT < 14) {
			return true;
		}
		return false;
	}

	/**
	 * Android Platform 4.1 = Level 16
	 */
	public static boolean isPlatformBelow_4_1() {
		if (Build.VERSION.SDK_INT < 16) {
			return true;
		}
		return false;
	}

	/**
	 * Android Platform 4.2 = Level 17
	 */
	public static boolean isPlatformBelow_4_2() {
		if (Build.VERSION.SDK_INT < 17) {
			return true;
		}
		return false;
	}

	/**
	 * Android Platform 4.4 = Level 19
	 */
	public static boolean isPlatformBelow_4_4() {
		if (Build.VERSION.SDK_INT < 19) {
			return true;
		}
		return false;
	}

	// public static boolean isAtLeastHoneycomb() {
	// // Can use static final constants like HONEYCOMB, declared in later
	// // versions
	// // of the OS since they are inlined at compile time. This is guaranteed
	// // behavior.
	// return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	// }

	// public static boolean isTablet(Context context) {
	// return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	// }
	//
	// public static boolean isAtLeastHoneycombAndTablet(Context context) {
	// return isAtLeastHoneycomb() && isTablet(context);
	// }

	public static boolean isLandscape(Context context) {
		if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			return true;
		} else {
			return false;
		}
	}

	public static void showAlertDialog(final Context context, final int resIdTitle, final int resIdMessage) {
		final Builder b = new AlertDialog.Builder(context);
		b.setCancelable(true);
		b.setTitle(resIdTitle);
		String text = context.getString(resIdMessage);
		b.setMessage(text);
		b.setPositiveButton(android.R.string.ok, null);
		b.show();
	}

	public static int getScrOrientation(Activity activity) {

		Display display = activity.getWindowManager().getDefaultDisplay();
		int orientation = display.getOrientation();

		// Sometimes you may get undefined orientation Value is 0
		// simple logic solves the problem compare the screen
		// X,Y Co-ordinates and determine the Orientation in such cases
		if (orientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE && orientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {

			Configuration config = activity.getResources().getConfiguration();
			orientation = config.orientation;

			if (orientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE && orientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
				if (display.getWidth() <= display.getHeight()) {
					orientation = Configuration.ORIENTATION_PORTRAIT;
				} else { // if it is not any of the above it will defineitly be landscape
					orientation = Configuration.ORIENTATION_LANDSCAPE;
				}
			}
		}
		return orientation; // return value 1 is portrait and 2 is Landscape Mode
	}

	/**
	 * @return ID and NAME of the drawables
	 */
	private static Hashtable<Integer, String> getAllDrawables() {
		Hashtable<Integer, String> dl = new Hashtable<Integer, String>();
		Field[] drawables = R.drawable.class.getFields();
		for (Field f : drawables) {
			try {
				String drawableName = f.getName();
				if (drawableName.startsWith("radio_") && !drawableName.endsWith("small")) {
					log(TAG, "R.drawable: ID=" + f.getInt(f.getName()) + " / NAME=" + f.getName());
					dl.put(f.getInt(f.getName()), f.getName());
				}
			} catch (Exception e) {
				Log.e(TAG, "Cannot get drawables ! ", e);
			}
		}
		return dl;
	}

	public static String getCastImageUrl(int imageResId) {
		if (allDrawables == null) {
			allDrawables = getAllDrawables();
		}

		log(TAG, "imageResId=" + imageResId);
		String imageUrl = CastHelper.IMAGE_BASE_DIR + allDrawables.get(imageResId) + ".png";
		log(TAG, "imageUrl=" + imageUrl);
		return imageUrl;
	}

	public static boolean isGooglePlayServicesAvailable(Context context) {
		if (Utils.isPlatformBelow_2_3_0()) {
			return false;
		} else {
			final int connectionStatusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
			if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
				return false;
			}
			return true;
		}
	}

	public static boolean isSamsungDevice() {
		String manufacturer = android.os.Build.MANUFACTURER;
		if (manufacturer != null && (manufacturer.toUpperCase().contains("SAMSUNG"))) {
			return true;
		}
		return false;
	}

	public static final Date today = new Date();
	// GregorianCalendar(2013, 8, 10) = 10.9.2013
	public static final GregorianCalendar border = new GregorianCalendar(2013, 8, 27);

	public static boolean isBorderOver() {
		// return true;
		if (today.getTime() > border.getTimeInMillis()) {
			return true;

		} else {
			return false;
		}
	}

}
