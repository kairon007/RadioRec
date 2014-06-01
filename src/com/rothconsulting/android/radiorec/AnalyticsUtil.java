package com.rothconsulting.android.radiorec;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rothconsulting.android.radiorec.ApplicationRadioRec.TrackerName;

public class AnalyticsUtil {

	public static final String UI_ACTION = "ui_action";
	public static final String KEY_VALIDATION = "key_validation";

	/**
	 * Build and send Analytics Event.
	 */
	public static void sendEvent(String category, String action, String label) {

		if (!Utils.isPlatformBelow_2_3_0()) {
			// Build and send Analytics Event.
			getTracker().send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
		}

	}

	/**
	 * Build and send Analytics Screen.
	 * 
	 * @param screenName
	 */
	public static void sendScreen(String screenName) {

		if (!Utils.isPlatformBelow_2_3_0()) {
			// Set screen name.
			// Where path is a String representing the screen name.
			getTracker().setScreenName(screenName);
			// Send a screen view.
			getTracker().send(new HitBuilders.AppViewBuilder().build());
		}
	}

	/**
	 * Get Analytics Tracker
	 * 
	 * @return
	 */
	private static Tracker getTracker() {
		// Get GoogleAnalytics tracker
		return ApplicationRadioRec.getApplication().getTracker(TrackerName.APP_TRACKER);
	}
}
