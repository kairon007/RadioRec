package com.rothconsulting.android.radiorec;

import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.util.Log;

public class RadioPlayer {

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
		Utils.log(TAG, "doStartPlay()");

		createThread(context);

		Utils.log(TAG, "prepareProgressDialog");
		Utils utils = new Utils();
		progressDialog = utils.prepareProgressDialog(context);
		progressDialog.setTitle(Constants.THE_SELECTED_STATION_NAME);
		Utils.log(TAG, "progressDialog.show()");
		progressDialog.show();
		Utils.log(TAG, "--- threadDoStartPlay.start()");
		threadDoStartPlay.start();
		// if (mediaPlayer != null) {
		// Utils.log(TAG, "*********** isRadioRunning2=" +
		// mediaPlayer.isPlaying());
		// } else {
		// Utils.log(TAG, "*********** isRadioRunning2=false");
		// }
	}

	protected void doStopPlay(Context context) {
		Utils.log(TAG, "doStopPlay()");
		try {
			if (threadDoStartPlay != null && threadDoStartPlay.isAlive()) {
				Utils.log(TAG, "++ threadDoStartPlay.interrupt()");
				threadDoStartPlay.interrupt();
			}
			if (mediaPlayer != null) {
				Utils.log(TAG, "mediaPlayer.isPlaying()=" + mediaPlayer.isPlaying());

				if (mediaPlayer.isPlaying()) {
					Utils.log(TAG, "stop()");
					mediaPlayer.stop();
					Utils.log(TAG, "release()");
					mediaPlayer.release();
					// if (!isRadioRecording) {
					// hideSongTicker();
					// }
				}
			} else {
				Utils.log(TAG,
						"Ich will stoppen aber der mediaPlayer ist null!! Macht nix!");
			}

		} catch (IllegalStateException e) {
			Utils.log(TAG, "Exception at doStopPlay -> IllegalStateException");
			Utils.log(TAG, "cause=" + e.getCause());
			Utils.log(TAG, "message=" + e.getMessage());
			Utils.log(TAG, "e.getStackTrace()[0]=" + e.getStackTrace()[0]);
			Utils.log(TAG, "e.getStackTrace()[1]=" + e.getStackTrace()[1]);
		} finally {
			Utils.log(TAG,
					"hideStatusBarNotification NOTIFICATION_ID_RADIO_IS_PLAYING");
			getNotifInstance(context).hideStatusBarNotification(
					Constants.NOTIFICATION_ID_RADIO_IS_PLAYING);
			Utils.log(TAG,
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
					Utils.log(TAG, "reset()");
					mediaPlayer.reset();
					Utils.log(TAG, "URL: " + Constants.THE_URL_LIVE_STREAM);
					mediaPlayer.setDataSource(Constants.THE_URL_LIVE_STREAM);
					Utils.log(TAG, "THE_URL_LIVE_STREAM="
							+ Constants.THE_URL_LIVE_STREAM);
					mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
					mediaPlayer.setWakeMode(context,
							PowerManager.PARTIAL_WAKE_LOCK);
					Utils.log(TAG, "prepare()");
					mediaPlayer.prepare();
					Utils.log(TAG, "start()");
					mediaPlayer.start();
					getNotifInstance(context)
							.showStatusBarNotificationIsRunning();
					// Utils.log(TAG,
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
					Utils.log(TAG,
							"IllegalArgumentException: "
									+ context
											.getString(R.string.networkNotAvailable));
					getNotifInstance(context).showStatusBarNotificationError(
							R.string.networkNotAvailable);
					doStopPlay(context);
				} catch (IllegalStateException e) {
					Utils.log(TAG,
							"IllegalStateException 1: "
									+ context
											.getString(R.string.internetadresseNichtErreichbar));
					getNotifInstance(context).showStatusBarNotificationError(
							R.string.internetadresseNichtErreichbar);
					Utils.log(TAG, "cause=" + e.getCause());
					if (e.getStackTrace() != null
							&& e.getStackTrace().length >= 2)
						Utils.log(TAG, "getStackTrace[0]" + e.getStackTrace()[0]);
					Utils.log(TAG, "getStackTrace[1]" + e.getStackTrace()[1]);
					doStopPlay(context);
				} catch (IOException e) {
					Utils.log(TAG,
							"doStartPlay - IOException: "
									+ context
											.getString(R.string.internetadresseNichtErreichbar)
									+ "\n---\n"
									+ Utils.getExceptionInfosAsString(e));
					getNotifInstance(context).showStatusBarNotificationError(
							R.string.internetadresseNichtErreichbar);
					doStopPlay(context);
				} finally {
					Utils.log(TAG, "createThread in finally");
					if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
						getNotifInstance(context)
								.showStatusBarNotificationError(
										R.string.internetadresseNichtErreichbar);
					}
				}
				Utils.log(TAG, "progressDialog.dismiss()");
				progressDialog.dismiss();
			}
		};
	}

}