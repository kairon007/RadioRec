package com.rothconsulting.android.radiorec;

import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;

public class RadioPlayer {

	private final static String TAG = "RadioPlayer";

	MediaPlayer mediaPlayer;
	Thread threadDoStartPlay;
	ProgressDialog progressDialog;
	boolean isRunning = false;

	// For Testing only
	public boolean isRunning() {
		return isRunning;
	}

	private Notifications getNotifInstance(Context context) {
		Intent intent = new Intent(context, Donate.class);
		intent.putExtra(Constants.FROM_NOTIFICATION, Constants.FROM_NOTIFICATION);
		return new Notifications(context, intent);
	}

	public boolean doStartPlay(final Context context) {
		Utils.log(TAG, "doStartPlay()");

		Utils.log(TAG, "hideStatusBarNotification NOTIFICATION_ID_ERROR_CONNECTION");
		getNotifInstance(context).hideStatusBarNotification(Constants.NOTIFICATION_ID_ERROR_CONNECTION);

		createThread(context);

		Utils.log(TAG, "prepareProgressDialog");
		progressDialog = Utils.prepareProgressDialog(context);
		progressDialog.setTitle(Constants.SELECTED_STATION_NAME_VALUE);
		Utils.log(TAG, "progressDialog.show()");
		// disable for JUnit
		if (!Constants.JUNIT_TEST) {
			progressDialog.show();
		}
		Utils.log(TAG, "--- threadDoStartPlay.start()");
		threadDoStartPlay.start();
		return isRunning;
	}

	public void doStopPlay(Context context) {
		Utils.log(TAG, "doStopPlay() START");
		try {
			if (threadDoStartPlay != null && threadDoStartPlay.isAlive()) {
				Utils.log(TAG, "++ threadDoStartPlay.interrupt()");
				threadDoStartPlay.interrupt();
			}
			if (mediaPlayer != null) {
				Utils.log(TAG, "mediaPlayer.isPlaying=" + isPlayerPlaying(mediaPlayer));

				if (isPlayerPlaying(mediaPlayer)) {
					Utils.log(TAG, "stop()");
					mediaPlayer.stop();
					Utils.log(TAG, "release()");
					mediaPlayer.release();
					// if (!isRadioRecording) {
					// hideSongTicker();
					// }
				}
			} else {
				Utils.log(TAG, "Ich will stoppen aber der mediaPlayer ist null!! Macht nix!");
			}

		} catch (IllegalStateException e) {
			Utils.log(TAG, "Exception at doStopPlay -> IllegalStateException");
			Utils.log(TAG, "cause=" + e.getCause());
			Utils.log(TAG, "message=" + e.getMessage());
			Utils.log(TAG, "e.getStackTrace()[0]=" + e.getStackTrace()[0]);
			Utils.log(TAG, "e.getStackTrace()[1]=" + e.getStackTrace()[1]);
		} finally {
			Utils.log(TAG, "hideStatusBarNotification NOTIFICATION_ID_RADIO_IS_PLAYING");
			getNotifInstance(context).hideStatusBarNotification(Constants.NOTIFICATION_ID_RADIO_IS_PLAYING);
			Utils.log(TAG, "hideStatusBarNotification NOTIFICATION_ID_ERROR_CONNECTION");
			getNotifInstance(context).hideStatusBarNotification(Constants.NOTIFICATION_ID_ERROR_CONNECTION);
		}
		Utils.log(TAG, "doStopPlay() STOP");
	}

	private void createThread(final Context context) {

		threadDoStartPlay = new Thread() {

			@Override
			public void run() {

				try {
					doStopPlay(context);
					mediaPlayer = new MediaPlayer();
					mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
						@Override
						public void onPrepared(MediaPlayer mp) {
							Utils.log(TAG, "start()");
							mediaPlayer.start();
						}
					});
					Utils.log(TAG, "reset()");
					mediaPlayer.reset();
					Utils.log(TAG, "URL: " + Constants.URL_LIVE_STREAM_VALUE);
					mediaPlayer.setDataSource(Constants.URL_LIVE_STREAM_VALUE);
					Utils.log(TAG, "URL_LIVE_STREAM_VALUE=" + Constants.URL_LIVE_STREAM_VALUE);
					mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
					mediaPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);
					Utils.log(TAG, "prepare()");
					mediaPlayer.prepare();
					// Utils.log(TAG, "start()");
					// mediaPlayer.start();
					getNotifInstance(context).showStatusBarNotificationIsRunning();

					// Thread threadSongTicker = new Thread() {
					// @Override
					// public void run() {
					// showSongTicker();
					// }
					// };
					// threadSongTicker.start();

					getNotifInstance(context).hideStatusBarNotification(Constants.NOTIFICATION_ID_ERROR_CONNECTION);

				} catch (IllegalArgumentException e) {
					Utils.log(TAG, "IllegalArgumentException: " + context.getString(R.string.networkNotAvailable));
					getNotifInstance(context).showStatusBarNotificationError(R.string.networkNotAvailable);
					doStopPlay(context);
				} catch (IllegalStateException e) {
					Utils.log(TAG, "IllegalStateException 1: " + context.getString(R.string.internetadresseNichtErreichbar));
					getNotifInstance(context).showStatusBarNotificationError(R.string.internetadresseNichtErreichbar);
					Utils.log(TAG, "cause=" + e.getCause());
					if (e.getStackTrace() != null && e.getStackTrace().length >= 2)
						Utils.log(TAG, "getStackTrace[0]" + e.getStackTrace()[0]);
					Utils.log(TAG, "getStackTrace[1]" + e.getStackTrace()[1]);
					doStopPlay(context);
				} catch (IOException e) {
					Utils.log(
							TAG,
							"doStartPlay - IOException: " + context.getString(R.string.internetadresseNichtErreichbar) + "\n---\n"
									+ Utils.getExceptionInfosAsString(e));
					getNotifInstance(context).showStatusBarNotificationError(R.string.internetadresseNichtErreichbar);
					doStopPlay(context);
				} finally {
					Utils.log(TAG, "createThread in finally");
					if (!isPlayerPlaying(mediaPlayer)) {
						// getNotifInstance(context).showStatusBarNotificationError(R.string.internetadresseNichtErreichbar);
						isRunning = false;
					} else {
						isRunning = true;
					}
				}
				Utils.log(TAG, "progressDialog.dismiss()");
				progressDialog.dismiss();
			}
		};
	}

	private boolean isPlayerPlaying(MediaPlayer mediaPlayer) {
		boolean result = false;
		try {
			if (mediaPlayer != null && mediaPlayer.isPlaying()) {
				result = true;
			}
		} catch (Exception e) {
			// do nothing because we do not know what to do...
			return result;
		}
		return result;
	}
}