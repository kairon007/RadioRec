package com.rothconsulting.android.radiorec;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.support.v7.media.MediaRouter.RouteInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.images.WebImage;

public class CastHelper {

	private final String TAG = "CastHelper";

	// private static final String CAST_APP_ID = CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID;
	private static final String CAST_APP_ID = "55A3A008"; // Styled Media Receiver

	private static final String CAST_NAMESPACE = "urn:x-cast:com.google.cast.media";
	private static final String IMAGE_BASE_DIR = "http://koni.mobi/radio/chromecast/images/";
	private static final double VOLUME_INCREMENT = 0.1;
	private static final String PREFS_KEY_SESSION_ID = "cast-session-id";
	private static final String PREFS_KEY_ROUTE_ID = "cast-route-id";

	protected final MediaRouter mMediaRouter;
	protected final MediaRouteSelector mMediaRouteSelector;

	private final Context context;

	private RemoteMediaPlayer mRemoteMediaPlayer;
	private GoogleApiClient mApiClient;
	private CastDevice selectedDevice;
	private boolean castApplicationStarted;
	private AsyncTask<Void, Integer, Integer> mReconnectionTask;
	private ReconnectionStatus mReconnectionStatus;

	private Hashtable<Integer, String> allDrawables;

	/**
	 * Enumerates various stages during a session recovery
	 */
	private static enum ReconnectionStatus {
		STARTED, IN_PROGRESS, FINALIZE, INACTIVE;
	}

	// *****************************************
	// Public
	// *****************************************
	// Constructor
	public CastHelper(Context context) {
		Utils.log(TAG, "--- START Constructor CastHelper(): mediaRouter and mediaRouteSelector and attachMediaChannel");
		this.context = context;
		mMediaRouter = MediaRouter.getInstance(getContext());
		mMediaRouteSelector = new MediaRouteSelector.Builder().addControlCategory(CastMediaControlIntent.categoryForCast(CAST_APP_ID)).build();
		// reconnectSessionIfPossible(context, true, 10);
		Utils.log(TAG, "--- END  Constructor CastHelper()");
	}

	public void play(String stationName, String stationUrl, int imageResId) {
		Utils.log(TAG, "--- START play(...)");

		Toast.makeText(getContext(), "Casting " + stationName, Toast.LENGTH_LONG).show();

		AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "CastHelper.play", "cast play: " + stationName);

		attachMediaChannel();

		if (allDrawables == null) {
			allDrawables = Utils.getAllDrawables();
		}

		Utils.log(TAG, "imageResId=" + imageResId + " / stationName=" + stationName);
		String imageUrl = IMAGE_BASE_DIR + allDrawables.get(imageResId) + ".png";
		Utils.log(TAG, "imageUrl  =" + imageUrl);
		Utils.log(TAG, "stationUrl=" + stationUrl);
		MediaMetadata mediaMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MUSIC_TRACK);
		mediaMetadata.putString(MediaMetadata.KEY_TITLE, stationName);
		mediaMetadata.addImage(new WebImage(Uri.parse(imageUrl)));
		MediaInfo mediaInfo = new MediaInfo.Builder(stationUrl).setContentType("audio/mp3").setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
				.setMetadata(mediaMetadata).build();

		try {
			mRemoteMediaPlayer.load(mApiClient, mediaInfo, true).setResultCallback(new ResultCallback<RemoteMediaPlayer.MediaChannelResult>() {
				@Override
				public void onResult(MediaChannelResult result) {
					if (result.getStatus().isSuccess()) {
						Utils.log(TAG, "Media loaded successfully: StatusCode=" + result.getStatus().getStatusCode());
					} else {
						Utils.log(TAG, "Media loaded NOT successfully StatusCode=" + result.getStatus().getStatusCode());
					}
				}
			});
		} catch (IllegalStateException e) {
			Log.e(TAG, "Problem occurred with media during loading", e);
		} catch (Exception e) {
			Log.e(TAG, "Problem opening media during loading", e);
		}
		Utils.log(TAG, "--- END  play(...)");
	}

	public void pause() {
		Utils.log(TAG, "--- START pause()");
		try {
			mRemoteMediaPlayer.requestStatus(mApiClient).setResultCallback(new ResultCallback<RemoteMediaPlayer.MediaChannelResult>() {

				@Override
				public void onResult(RemoteMediaPlayer.MediaChannelResult mediaChannelResult) {
					Status status = mediaChannelResult.getStatus();
					Utils.log(TAG, "RemoteMediaPlayer requestStatus: StatusCode=" + status.getStatusCode());
					try {
						mRemoteMediaPlayer.pause(mApiClient);
					} catch (Exception e) {
						Log.e(TAG, "Exception while stopping GoogleApiClient. ", e);
					}
				}
			});
		} catch (IllegalStateException e) {
			Log.e(TAG, "Problem occurred while geting requestStatus", e);
		} catch (Exception e) {
			Log.e(TAG, "Exception while geting requestStatus. ", e);
		}
		Utils.log(TAG, "--- END  pause()");
	}

	public final MediaRouter.Callback mediaRouterCallback = new MediaRouter.Callback() {
		@Override
		public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo routeInfo) {
			Utils.log(TAG, "mediaRouterCallback onRouteSelected: router=" + router + " / route=" + routeInfo + "/ routeId=" + routeInfo.getId());
			storeToDefaultSharedPreferences(context, PREFS_KEY_ROUTE_ID, routeInfo.getId());
			CastDevice device = CastDevice.getFromBundle(routeInfo.getExtras());
			setSelectedDevice(device);
		}

		@Override
		public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo route) {
			Utils.log(TAG, "mediaRouterCallback onRouteUnselected: router=" + router + " / route=" + route);
			setSelectedDevice(null);
		}
	};

	public boolean isConnected() {
		boolean isConnected = mApiClient != null && mApiClient.isConnected();
		Utils.log(TAG, "isConnected=" + isConnected);
		return isConnected;
	}

	public void volumeUp() {
		if (mRemoteMediaPlayer != null) {
			double currentVolume = Cast.CastApi.getVolume(mApiClient);
			if (currentVolume < 1.0) {
				try {
					Cast.CastApi.setVolume(mApiClient, Math.min(currentVolume + VOLUME_INCREMENT, 1.0));
				} catch (Exception e) {
					Log.e(TAG, "unable to set volume", e);
				}
			}
		} else {
			Log.e(TAG, "dispatchKeyEvent - volume up");
		}

	}

	public void volumeDown() {
		if (mRemoteMediaPlayer != null) {
			double currentVolume = Cast.CastApi.getVolume(mApiClient);
			if (currentVolume > 0.0) {
				try {
					Cast.CastApi.setVolume(mApiClient, Math.max(currentVolume - VOLUME_INCREMENT, 0.0));
				} catch (Exception e) {
					Log.e(TAG, "unable to set volume", e);
				}
			}
		} else {
			Log.e(TAG, "dispatchKeyEvent - volume down");
		}
	}

	// *****************************************
	// Private
	// *****************************************
	private void attachMediaChannel() {

		mRemoteMediaPlayer = new RemoteMediaPlayer();
		mRemoteMediaPlayer.setOnStatusUpdatedListener(new RemoteMediaPlayer.OnStatusUpdatedListener() {
			@Override
			public void onStatusUpdated() {
				MediaStatus mediaStatus = mRemoteMediaPlayer.getMediaStatus();
				Utils.log(TAG, "OnStatusUpdatedListener onStatusUpdated mediaStatus=" + mediaStatus.getPlayerState());
				boolean isPlaying = mediaStatus.getPlayerState() == MediaStatus.PLAYER_STATE_PLAYING;
				Utils.log(TAG, "isPlaying=" + isPlaying);
			}
		});

		mRemoteMediaPlayer.setOnMetadataUpdatedListener(new RemoteMediaPlayer.OnMetadataUpdatedListener() {
			@Override
			public void onMetadataUpdated() {
				MediaInfo mediaInfo = mRemoteMediaPlayer.getMediaInfo();
				if (mediaInfo != null) {
					MediaMetadata metadata = mediaInfo.getMetadata();
					Utils.log(TAG, "setOnMetadataUpdatedListener onMetadataUpdated : metadata Title=" + metadata.getString(MediaMetadata.KEY_TITLE));
				} else {
					Utils.log(TAG, "setOnMetadataUpdatedListener onMetadataUpdated : mediaInfo = null");
				}

			}
		});

		try {
			Utils.log(TAG, "Registering MediaChannel namespace: " + mRemoteMediaPlayer.getNamespace());
			Cast.CastApi.setMessageReceivedCallbacks(mApiClient, mRemoteMediaPlayer.getNamespace(), mRemoteMediaPlayer);
		} catch (Exception e) {
			Log.e(TAG, "Failed to set up media channel", e);
		}
	}

	private void setSelectedDevice(CastDevice device) {
		Utils.log(TAG, "setSelectedDevice: " + device);

		selectedDevice = device;

		if (selectedDevice != null) {
			try {
				stopApplication();
				disconnectApiClient();
				connectApiClient();
			} catch (IllegalStateException e) {
				Log.w(TAG, "Exception while connecting API client", e);
				disconnectApiClient();
			}
		} else {
			if (mApiClient != null) {
				disconnectApiClient();
			}
			mMediaRouter.selectRoute(mMediaRouter.getDefaultRoute());
		}
	}

	private final Cast.Listener castClientListener = new Cast.Listener() {
		@Override
		public void onApplicationDisconnected(int statusCode) {
			// statusCode at: http://developer.android.com/reference/com/google/android/gms/cast/CastStatusCodes.html
			Utils.log(TAG, "----- Cast.Listener - onApplicationDisconnected - statusCode = " + statusCode);
			try {
				Utils.log(TAG, "----- Cast.Listener - onApplicationDisconnected - removeMessageReceivedCallbacks - namespace = " + CAST_NAMESPACE);
				Cast.CastApi.removeMessageReceivedCallbacks(mApiClient, CAST_NAMESPACE);
			} catch (IOException e) {
				Log.w(TAG, "Exception while launching application", e);
			}
			setSelectedDevice(null);
		}

		@Override
		public void onVolumeChanged() {
			if (mApiClient != null) {
				Utils.log(TAG, "----- onVolumeChanged: " + Cast.CastApi.getVolume(mApiClient));
			}
		}
	};

	private final ResultCallback<Cast.ApplicationConnectionResult> connectionResultCallback = new ResultCallback<Cast.ApplicationConnectionResult>() {
		@Override
		public void onResult(Cast.ApplicationConnectionResult result) {
			Status status = result.getStatus();
			Utils.log(TAG, "----- ResultCallback - onResult - statusCode = " + status.getStatusCode());
			Utils.log(TAG, "----- ResultCallback - onResult - status.isSuccess() = " + status.isSuccess());
			if (status.isSuccess()) {
				castApplicationStarted = true;

				try {
					Utils.log(TAG, "----- try setMessageReceivedCallbacks. StatusCode=" + status.getStatusCode() + " / namespace=" + CAST_NAMESPACE);
					storeToDefaultSharedPreferences(context, PREFS_KEY_SESSION_ID, result.getSessionId());
					Cast.CastApi.setMessageReceivedCallbacks(mApiClient, CAST_NAMESPACE, incomingMsgHandler);
				} catch (IOException e) {
					Log.e(TAG, "Exception while creating channel", e);
				}
			}
		}
	};

	private final GoogleApiClient.ConnectionCallbacks connectionCallback = new GoogleApiClient.ConnectionCallbacks() {
		@Override
		public void onConnected(Bundle bundle) {
			Utils.log(TAG, "----- GoogleApiClient.ConnectionCallbacks - onConnected - bundle = " + bundle);
			try {
				Cast.CastApi.launchApplication(mApiClient, CAST_APP_ID, false).setResultCallback(connectionResultCallback);
			} catch (Exception e) {
				Log.e(TAG, "Failed to launch application", e);
			}
		}

		@Override
		public void onConnectionSuspended(int i) {
			Utils.log(TAG, "----- GoogleApiClient.ConnectionCallbacks - onConnectionSuspended - int = " + i);
		}
	};

	private final GoogleApiClient.OnConnectionFailedListener connectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
		@Override
		public void onConnectionFailed(ConnectionResult connectionResult) {
			Utils.log(TAG, "----- GoogleApiClient.OnConnectionFailedListener - connectionResult = " + connectionResult);
			setSelectedDevice(null);
		}
	};

	private void connectApiClient() {
		Utils.log(TAG, "----- connectApiClient()");
		Cast.CastOptions apiOptions = Cast.CastOptions.builder(selectedDevice, castClientListener).build();
		mApiClient = new GoogleApiClient.Builder(getContext()).addApi(Cast.API, apiOptions).addConnectionCallbacks(connectionCallback)
				.addOnConnectionFailedListener(connectionFailedListener).build();
		Utils.log(TAG, "----- connectApiClient() - apiClient: " + mApiClient);
		mApiClient.connect();
	}

	private void disconnectApiClient() {
		Utils.log(TAG, "----- disconnectApiClient - apiClient: " + mApiClient);
		if (mApiClient != null) {
			mApiClient.disconnect();
			mApiClient = null;
		}
		castApplicationStarted = false;
	}

	private void stopApplication() {
		Utils.log(TAG, "----- stopApplication");
		if (mApiClient == null)
			return;

		Utils.log(TAG, "----- applicationStarted = " + castApplicationStarted);
		if (castApplicationStarted) {
			Cast.CastApi.stopApplication(mApiClient);
			castApplicationStarted = false;
		}
	}

	private final Cast.MessageReceivedCallback incomingMsgHandler = new Cast.MessageReceivedCallback() {
		@Override
		public void onMessageReceived(CastDevice castDevice, String namespace, String message) {
			Utils.log(TAG, "----- onMessageReceived castDevice: " + castDevice);
			Utils.log(TAG, "----- onMessageReceived namespace : " + namespace);
			Utils.log(TAG, "----- onMessageReceived message   : " + message);
		}
	};

	// ----------------------
	// Session restore
	// ----------------------
	private void reconnectSessionIfPossible(final Context context, final boolean showDialog, final int timeoutInSeconds) {
		if (isConnected()) {
			return;
		}
		Utils.log(TAG, "reconnectSessionIfPossible()");
		String routeId = getFromDefaultSharedPreferences(context, PREFS_KEY_ROUTE_ID);

		if (canConsiderSessionRecovery(context)) {
			List<RouteInfo> routes = mMediaRouter.getRoutes();
			RouteInfo theRoute = null;
			if (null != routes && !routes.isEmpty()) {
				for (RouteInfo route : routes) {
					if (route.getId().equals(routeId)) {
						theRoute = route;
						break;
					}
				}
			}
			if (null != theRoute) {
				// route has already been discovered, so lets just get the
				// device, etc
				reconnectSessionIfPossibleInternal(theRoute);
			} else {
				// we set a flag so if the route is discovered within a short
				// period, we let onRouteAdded callback of
				// CastMediaRouterCallback take
				// care of that
				mReconnectionStatus = ReconnectionStatus.STARTED;
			}

			// we may need to reconnect to an existing session
			mReconnectionTask = new AsyncTask<Void, Integer, Integer>() {
				private ProgressDialog dlg;
				private final int SUCCESS = 1;
				private final int FAILED = 2;

				@Override
				protected void onCancelled() {
					if (null != dlg) {
						dlg.dismiss();
					}
					super.onCancelled();
				}

				@Override
				protected void onPreExecute() {
					if (!showDialog) {
						return;
					}
					dlg = new ProgressDialog(context);
					dlg.setMessage("Reconnecting");
					dlg.setIndeterminate(true);
					dlg.setCancelable(true);
					dlg.setOnCancelListener(new DialogInterface.OnCancelListener() {

						@Override
						public void onCancel(DialogInterface dialog) {
							switch (mReconnectionStatus) {
							case STARTED:
							case IN_PROGRESS:
							case FINALIZE:
								mReconnectionStatus = ReconnectionStatus.INACTIVE;
								setSelectedDevice(null);
								break;
							default:
								break;
							}
							mReconnectionStatus = ReconnectionStatus.INACTIVE;
							if (null != dlg) {
								dlg.dismiss();
							}
							mReconnectionTask.cancel(true);
						}
					});
					dlg.setButton(ProgressDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							switch (mReconnectionStatus) {
							case STARTED:
							case IN_PROGRESS:
							case FINALIZE:
								mReconnectionStatus = ReconnectionStatus.INACTIVE;
								setSelectedDevice(null);
								break;
							default:
								break;
							}
							mReconnectionStatus = ReconnectionStatus.INACTIVE;
							if (null != dlg) {
								dlg.cancel();
							}
							mReconnectionTask.cancel(true);
						}
					});
					dlg.show();
				}

				@Override
				protected Integer doInBackground(Void... params) {
					for (int i = 0; i < timeoutInSeconds; i++) {
						if (mReconnectionTask.isCancelled()) {
							if (null != dlg) {
								dlg.dismiss();
							}
							return SUCCESS;
						}
						try {
							if (isConnected()) {
								cancel(true);
							}
							Thread.sleep(1000);
						} catch (Exception e) {
							// ignore
						}
					}
					return FAILED;
				}

				@Override
				protected void onPostExecute(Integer result) {
					if (showDialog && null != dlg) {
						dlg.dismiss();
					}
					if (null != result) {
						if (result == FAILED) {
							mReconnectionStatus = ReconnectionStatus.INACTIVE;
							setSelectedDevice(null);
						}
					}
				}

			};
			mReconnectionTask.execute();
		}
	}

	private void reconnectSessionIfPossibleInternal(RouteInfo theRoute) {
		if (isConnected()) {
			return;
		}
		String sessionId = getFromDefaultSharedPreferences(context, PREFS_KEY_SESSION_ID);
		String routeId = getFromDefaultSharedPreferences(getContext(), PREFS_KEY_ROUTE_ID);
		Utils.log(TAG, "----------- reconnectSessionIfPossible() Retrieved from preferences: " + "sessionId=" + sessionId + ", routeId=" + routeId);
		if (null == sessionId || null == routeId) {
			return;
		}
		mReconnectionStatus = ReconnectionStatus.IN_PROGRESS;
		CastDevice device = CastDevice.getFromBundle(theRoute.getExtras());

		if (null != device) {
			Utils.log(TAG, "trying to acquire Cast Client for " + device);
			setSelectedDevice(device);
		}
	}

	private final boolean canConsiderSessionRecovery(Context context) {
		String sessionId = getFromDefaultSharedPreferences(context, PREFS_KEY_SESSION_ID);
		String routeId = getFromDefaultSharedPreferences(context, PREFS_KEY_ROUTE_ID);
		if (null == sessionId || null == routeId) {
			return false;
		}
		Utils.log(TAG, "Found session info in the preferences, so proceed with an attempt to reconnect if possible");
		return true;
	}

	// ----------------------
	// Cast preferences
	// ----------------------
	@SuppressLint("NewApi")
	private void storeToDefaultSharedPreferences(Context context, String key, String value) {
		Utils.log(TAG, "----------- storeToDefaultSharedPreferences, key=" + key + " / value=" + value);
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		if (null == value) {
			// we want to remove
			pref.edit().remove(key).apply();
		} else {
			pref.edit().putString(key, value).apply();
		}
	}

	private String getFromDefaultSharedPreferences(Context context, String key) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		String value = pref.getString(key, "DefaultCastPrefs");
		Utils.log(TAG, "----------- getFromDefaultSharedPreferences, key=" + key + " / value=" + value);
		return value;
	}

	// ----------------------
	// Cast utils
	// ----------------------
	private Context getContext() {
		if (context != null) {
			return context;
		} else {
			return ApplicationRadioRec.getAppContext();
		}
	}

}
