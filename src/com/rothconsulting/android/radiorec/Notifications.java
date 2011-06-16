package com.rothconsulting.android.radiorec;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Notifications {

	private Context context = null;
	private Intent intent = null;

	public Notifications(Context theContext, Intent theIntent) {
		this.context = theContext;
		this.intent = theIntent;
	}

	protected void showStatusBarNotificationError(int errorRessourceString) {

		int icon = R.drawable.status_error;
		CharSequence tickerText = context.getString(errorRessourceString);

		CharSequence contentTitle = context.getString(errorRessourceString);
		CharSequence contentText = context.getString(R.string.verbindeInternet);

		showStatusBarNotification(icon, tickerText, contentTitle, contentText,
				Constants.NOTIFICATION_ID_ERROR_CONNECTION, -1);
	}

	protected void showStatusBarNotificationIsRunning() {

		int icon = R.drawable.jukebox_48x48;
		CharSequence tickerText = context.getString(R.string.onAir) + " "
				+ RadioRecPlus.SELECTED_STATION;

		// int notificationFlags = Notification.FLAG_ONGOING_EVENT;

		CharSequence contentTitle = context.getString(R.string.radiorec);
		CharSequence contentText = context.getString(R.string.onAir) + " "
				+ RadioRecPlus.SELECTED_STATION;

		showStatusBarNotification(icon, tickerText, contentTitle, contentText,
				Constants.NOTIFICATION_ID_RADIO_IS_PLAYING, -1);

	}

	protected void showStatusBarNotificationRecording() {

		int icon = R.drawable.record_on;
		CharSequence tickerText = "Recording... "
				+ RadioRecPlus.SELECTED_STATION;

		// int notificationFlags = Notification.FLAG_ONGOING_EVENT;

		CharSequence contentTitle = "Recording...";
		CharSequence contentText = "Recording " + RadioRecPlus.SELECTED_STATION;

		showStatusBarNotification(icon, tickerText, contentTitle, contentText,
				Constants.NOTIFICATION_ID_RECORDING, -1);
	}

	private void showStatusBarNotification(int icon, CharSequence tickerText,
			CharSequence contentTitle, CharSequence contentText,
			int notificationId, int notificationFlags) {

		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(ns);

		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		if (notificationFlags >= 0) {
			notification.flags += notificationFlags;
		}

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				intent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);

		mNotificationManager.notify(notificationId, notification);
	}

	protected void hideStatusBarNotification(int notificationId) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(ns);
		mNotificationManager.cancel(notificationId);
	}
}
