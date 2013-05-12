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

	public void showStatusBarNotificationError(int errorRessourceString) {

		// mögliche Notification löschen und danach neu anzeigen
		this.hideStatusBarNotification(Constants.NOTIFICATION_ID_ERROR_CONNECTION);

		int icon = android.R.drawable.stat_sys_warning;
		CharSequence tickerText = context.getString(errorRessourceString);

		CharSequence contentTitle = context.getString(errorRessourceString);
		CharSequence contentText = "";

		if (errorRessourceString == R.string.kannNichtAufSdCardSchreiben) {
			contentText = context.getString(R.string.isSdCardKorrekt);
		} else {
			contentText = context.getString(R.string.verbindeInternet);
		}

		showStatusBarNotification(icon, tickerText, contentTitle, contentText,
				Constants.NOTIFICATION_ID_ERROR_CONNECTION, -1);
	}

	public void showStatusBarNotificationIsRunning() {

		int icon = R.drawable.jukebox;
		CharSequence tickerText = context.getString(R.string.onAir) + " "
				+ Constants.SELECTED_STATION_NAME_VALUE;

		// int notificationFlags = Notification.FLAG_ONGOING_EVENT;

		CharSequence contentTitle = context.getString(R.string.app_name);
		CharSequence contentText = context.getString(R.string.onAir) + " "
				+ Constants.SELECTED_STATION_NAME_VALUE;

		showStatusBarNotification(icon, tickerText, contentTitle, contentText,
				Constants.NOTIFICATION_ID_RADIO_IS_PLAYING, -1);

	}

	public void showStatusBarNotificationRecording() {

		int icon = R.drawable.button_record_on;
		CharSequence tickerText = "Recording... "
				+ Constants.SELECTED_STATION_NAME_VALUE;

		// int notificationFlags = Notification.FLAG_ONGOING_EVENT;

		CharSequence contentTitle = "Recording...";
		CharSequence contentText = "Recording "
				+ Constants.SELECTED_STATION_NAME_VALUE;

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

	public void hideStatusBarNotification(int notificationId) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(ns);
		mNotificationManager.cancel(notificationId);
	}
}
