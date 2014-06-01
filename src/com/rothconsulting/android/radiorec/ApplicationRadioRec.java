package com.rothconsulting.android.radiorec;

import java.util.HashMap;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class ApplicationRadioRec extends Application {

	private static Context context;

	@Override
	public void onCreate() {
		context = getApplicationContext();
	}

	public static Context getAppContext() {
		return context;
	}

	public static ApplicationRadioRec getApplication() {
		Application application = (Application) ApplicationRadioRec.getAppContext();
		ApplicationRadioRec app = (ApplicationRadioRec) application;
		return app;
	}

	/**
	 * Enum used to identify the tracker that needs to be used for tracking.
	 * 
	 * A single tracker is usually enough for most purposes. In case you do need multiple trackers, storing them all in Application object helps ensure that
	 * they are created only once per application instance.
	 */
	public enum TrackerName {
		APP_TRACKER, // Tracker used only in this app.
		GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
	}

	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

	public synchronized Tracker getTracker(TrackerName trackerId) {
		if (!mTrackers.containsKey(trackerId)) {

			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(Constants.ANALYTICS_PROPERTY_ID)
					: (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker) : analytics.newTracker(R.xml.global_tracker);
			mTrackers.put(trackerId, t);

		}
		return mTrackers.get(trackerId);
	}
}
