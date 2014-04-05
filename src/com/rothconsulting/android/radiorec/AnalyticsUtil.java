package com.rothconsulting.android.radiorec;

import android.app.Activity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rothconsulting.android.radiorec.ApplicationRadioRec.TrackerName;

public class AnalyticsUtil {
	/**
	 * Build and send Analytics Event.
	 * 
	 * @param activity
	 */
	public static void sendEvent(Activity activity, String category, String action, String label) {

		if (!Utils.isPlatformBelow_2_3_0()) {
			// Build and send Analytics Event.
			getTracker(activity).send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
		}

	}

	/**
	 * Build and send Analytics Screen.
	 * 
	 * @param activity
	 * @param screenName
	 */
	public static void sendScreen(Activity activity, String screenName) {

		if (!Utils.isPlatformBelow_2_3_0()) {
			// Set screen name.
			// Where path is a String representing the screen name.
			getTracker(activity).setScreenName(screenName);
			// Send a screen view.
			getTracker(activity).send(new HitBuilders.AppViewBuilder().build());
		}
	}

	/**
	 * Get Analytics Tracker
	 * 
	 * @param activity
	 * @return
	 */
	public static Tracker getTracker(Activity activity) {
		// Get GoogleAnalytics tracker
		return ((ApplicationRadioRec) activity.getApplication()).getTracker(TrackerName.APP_TRACKER);
	}
}
