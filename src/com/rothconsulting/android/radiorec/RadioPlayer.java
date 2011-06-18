package com.rothconsulting.android.radiorec;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

public class RadioPlayer extends Activity {

	private final static String TAG = "RadioPlayer";

	MediaPlayer mediaPlayer;

	private Notifications getNotifInstance(Context context) {
		return new Notifications(context,
				new Intent(context, RadioPlayer.class));
	}

	protected void doStartPlay(final Context context) {
		Log.d(TAG, "doStartPlay()");
		Log.d(TAG, "prepareProgressDialog");
		final ProgressDialog progressDialog = Utils
				.prepareProgressDialog(context);
		Log.d(TAG, "setTitle");
		progressDialog.setTitle(RadioRecPlus.SELECTED_STATION);
		Log.d(TAG, "show");
		progressDialog.show();

		Thread threadDoStartPlay = new Thread() {
			@Override
			public void run() {

				try {
					doStopPlay(context);
					if (mediaPlayer == null) {
						mediaPlayer = new MediaPlayer();
					}
					mediaPlayer.setDataSource(RadioRecPlus.URL_LIVE_STREAM);
					Log.d(TAG, "URL: " + RadioRecPlus.URL_LIVE_STREAM);
					// mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
					mediaPlayer.prepare();
					Log.d(TAG, "prepare()");
					mediaPlayer.start();
					Log.d(TAG, "start()");
					getNotifInstance(context)
							.showStatusBarNotificationIsRunning();
					Log.d(TAG,
							"*********** isRadioRunning1="
									+ mediaPlayer.isPlaying());

					// Thread threadSongTicker = new Thread() {
					// @Override
					// public void run() {
					// showSongTicker();
					// }
					// };
					// threadSongTicker.start();

					getNotifInstance(context).hideStatusBarNotification(
							Constants.NOTIFICATION_ID_ERROR_CONNECTION);

				} catch (IllegalArgumentException e) {
					Log.d(TAG,
							"IllegalArgumentException: "
									+ context
											.getString(R.string.networkNotAvailable));
					getNotifInstance(context).showStatusBarNotificationError(
							R.string.networkNotAvailable);
					doStopPlay(context);
				} catch (IllegalStateException e) {
					Log.d(TAG,
							"IllegalStateException 1: "
									+ context
											.getString(R.string.networkNotAvailable));
					getNotifInstance(context).showStatusBarNotificationError(
							R.string.networkNotAvailable);
					Log.d(TAG, "cause=" + e.getCause());
					if (e.getStackTrace() != null
							&& e.getStackTrace().length >= 2)
						Log.d(TAG, "getStackTrace[0]" + e.getStackTrace()[0]);
					Log.d(TAG, "getStackTrace[1]" + e.getStackTrace()[1]);

					doStopPlay(context);
				} catch (IOException e) {
					Log.d(TAG,
							"doStartPlay - IOException: "
									+ context
											.getString(R.string.internetadresseNichtErreichbar));
					getNotifInstance(context).showStatusBarNotificationError(
							R.string.internetadresseNichtErreichbar);
					doStopPlay(context);
				} finally {

					if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
						getNotifInstance(context)
								.showStatusBarNotificationError(
										R.string.internetadresseNichtErreichbar);
					}
				}
				Log.d(TAG, "progressDialog.dismiss()");

				progressDialog.dismiss();
			}
		};
		Log.d(TAG, "threadDoStartPlay.start()");
		threadDoStartPlay.start();
		if (mediaPlayer != null) {
			Log.d(TAG, "*********** isRadioRunning2=" + mediaPlayer.isPlaying());
		} else {
			Log.d(TAG, "*********** isRadioRunning2=false");
		}
	}

	protected void doStopPlay(Context context) {
		Log.d(TAG, "doStopPlay()");
		try {
			Log.d(TAG, "try");
			if (mediaPlayer != null) {
				Log.d(TAG, "mediaPlayer.isPlaying()=" + mediaPlayer.isPlaying());
				Log.d(TAG, "mediaPlayer.isLooping()=" + mediaPlayer.isLooping());
				// if (mediaPlayer.isPlaying() || mediaPlayer.isLooping()) {
				Log.d(TAG, "stop()");
				mediaPlayer.stop();
				Log.d(TAG, "reset()");
				mediaPlayer.reset();
				Log.d(TAG, "release()");
				mediaPlayer.release();
				Log.d(TAG, "mediaPlayer = null");
				mediaPlayer = null;
				// if (!isRadioRecording) {
				// hideSongTicker();
				// }
				// }
			}

			Log.d(TAG,
					"hideStatusBarNotification NOTIFICATION_ID_RADIO_IS_PLAYING");
			getNotifInstance(context).hideStatusBarNotification(
					Constants.NOTIFICATION_ID_RADIO_IS_PLAYING);
			Log.d(TAG,
					"hideStatusBarNotification NOTIFICATION_ID_ERROR_CONNECTION");
			getNotifInstance(context).hideStatusBarNotification(
					Constants.NOTIFICATION_ID_ERROR_CONNECTION);
		} catch (IllegalStateException e) {

			Log.d(TAG, "doStopPlay - IllegalStateException");
			Log.d(TAG, "cause=" + e.getCause());
			Log.d(TAG, "message=" + e.getMessage());
			Log.d(TAG, "e.getStackTrace()[0]=" + e.getStackTrace()[0]);
			Log.d(TAG, "e.getStackTrace()[1]=" + e.getStackTrace()[1]);

		}
	}
}