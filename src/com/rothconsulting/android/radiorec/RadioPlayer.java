package com.rothconsulting.android.radiorec;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

public class RadioPlayer extends Activity {

	private final static String TAG = "RadioPlayer";

	MediaPlayer mediaPlayer;
	Thread threadDoStartPlay;
	ProgressDialog progressDialog;

	private Notifications getNotifInstance(Context context) {
		Intent intent = new Intent(context, Donate.class);
		intent.putExtra(Constants.FROM_NOTIFICATION,
				Constants.FROM_NOTIFICATION);
		return new Notifications(context, intent);
	}

	protected void doStartPlay(final Context context) {
		Log.d(TAG, "doStartPlay()");

		createThread(context);
		Log.d(TAG, "prepareProgressDialog");
		Utils utils = new Utils();
		progressDialog = utils.prepareProgressDialog(context);
		progressDialog.setTitle(Constants.THE_SELECTED_STATION_NAME);
		Log.d(TAG, "progressDialog.show()");
		progressDialog.show();
		Log.d(TAG, "--- threadDoStartPlay.start()");
		threadDoStartPlay.start();
		// if (mediaPlayer != null) {
		// Log.d(TAG, "*********** isRadioRunning2=" +
		// mediaPlayer.isPlaying());
		// } else {
		// Log.d(TAG, "*********** isRadioRunning2=false");
		// }
	}

	protected void doStopPlay(Context context) {
		Log.d(TAG, "doStopPlay()");
		try {
			if (threadDoStartPlay != null && threadDoStartPlay.isAlive()) {
				Log.d(TAG, "++ threadDoStartPlay.interrupt()");
				threadDoStartPlay.interrupt();
			}
			if (mediaPlayer != null) {
				Log.d(TAG, "mediaPlayer.isPlaying()=" + mediaPlayer.isPlaying());

				if (mediaPlayer.isPlaying()) {
					Log.d(TAG, "stop()");
					mediaPlayer.stop();
					Log.d(TAG, "release()");
					mediaPlayer.release();
					// if (!isRadioRecording) {
					// hideSongTicker();
					// }
				}
			} else {
				Log.d(TAG,
						"Ich will stoppen aber der mediaPlayer ist null!! Macht nix!");
			}

		} catch (IllegalStateException e) {
			Log.d(TAG, "Exception at doStopPlay -> IllegalStateException");
			Log.d(TAG, "cause=" + e.getCause());
			Log.d(TAG, "message=" + e.getMessage());
			Log.d(TAG, "e.getStackTrace()[0]=" + e.getStackTrace()[0]);
			Log.d(TAG, "e.getStackTrace()[1]=" + e.getStackTrace()[1]);
		} finally {
			Log.d(TAG,
					"hideStatusBarNotification NOTIFICATION_ID_RADIO_IS_PLAYING");
			getNotifInstance(context).hideStatusBarNotification(
					Constants.NOTIFICATION_ID_RADIO_IS_PLAYING);
			Log.d(TAG,
					"hideStatusBarNotification NOTIFICATION_ID_ERROR_CONNECTION");
			getNotifInstance(context).hideStatusBarNotification(
					Constants.NOTIFICATION_ID_ERROR_CONNECTION);
		}
	}

	private void createThread(final Context context) {

		threadDoStartPlay = new Thread() {

			@Override
			public void run() {

				try {
					doStopPlay(context);
					mediaPlayer = new MediaPlayer();
					Log.d(TAG, "reset()");
					mediaPlayer.reset();
					Log.d(TAG, "URL: " + Constants.THE_URL_LIVE_STREAM);
					mediaPlayer.setDataSource(Constants.THE_URL_LIVE_STREAM);
					mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
					Log.d(TAG, "prepare()");
					mediaPlayer.prepare();
					Log.d(TAG, "start()");
					mediaPlayer.start();
					getNotifInstance(context)
							.showStatusBarNotificationIsRunning();
					// Log.d(TAG,
					// "*********** isRadioRunning1="
					// + mediaPlayer.isPlaying());

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
											.getString(R.string.internetadresseNichtErreichbar));
					getNotifInstance(context).showStatusBarNotificationError(
							R.string.internetadresseNichtErreichbar);
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
					Log.d(TAG, "createThread in finally");
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
	}
}