package com.rothconsulting.android.radiorec;

import java.io.IOException;
import java.util.Hashtable;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
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

	private static final String TAG = "CastHelper";

	// private static final String CAST_APP_ID = CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID;
	private static final String CAST_APP_ID = "55A3A008";

	private static final String CAST_NAMESPACE = "urn:x-cast:koni.mobi.radiorec";
	private static final String IMAGE_BASE_DIR = "http://koni.mobi/radio/chromecast/images/";

	private static RemoteMediaPlayer mRemoteMediaPlayer;
	private static GoogleApiClient apiClient;
	private static CastDevice selectedDevice;
	private static boolean castApplicationStarted;

	public static Hashtable<Integer, String> allDrawables;

	// *****************************************
	// Public
	// *****************************************
	public static void init() {
		RadioRecPlusActivity.mediaRouter = MediaRouter.getInstance(ApplicationRadioRec.getCustomAppContext());
		RadioRecPlusActivity.mediaRouteSelector = new MediaRouteSelector.Builder().addControlCategory(CastMediaControlIntent.categoryForCast(CAST_APP_ID))
				.build();
		initRemoteMediaPlayer();
	}

	public static void play(String stationName, String stationUrl, int imageResId) {
		Utils.log(TAG, "START play(...)");

		if (allDrawables == null) {
			allDrawables = Utils.getAllDrawables();
		}
		Utils.log(TAG, "imageResId=" + imageResId + " / stationName=" + stationName);
		String imageUrl = IMAGE_BASE_DIR + allDrawables.get(imageResId) + ".png";
		Utils.log(TAG, "imageUrl=" + imageUrl);
		MediaMetadata mediaMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MUSIC_TRACK);
		mediaMetadata.putString(MediaMetadata.KEY_TITLE, stationName);
		mediaMetadata.addImage(new WebImage(Uri.parse(imageUrl)));
		final MediaInfo mediaInfo = new MediaInfo.Builder(stationUrl).setContentType("audio/mp3").setStreamType(MediaInfo.STREAM_TYPE_LIVE)
				.setMetadata(mediaMetadata).build();
		try {
			mRemoteMediaPlayer.load(apiClient, mediaInfo, true).setResultCallback(new ResultCallback<RemoteMediaPlayer.MediaChannelResult>() {
				@Override
				public void onResult(MediaChannelResult result) {
					if (result.getStatus().isSuccess()) {
						Utils.log(TAG, "Media loaded successfully: " + result.getStatus());
					} else {
						Utils.log(TAG, "Media loaded NOT successfully: " + result.getStatus());
					}
				}
			});
		} catch (IllegalStateException e) {
			Log.e(TAG, "Problem occurred with media during loading", e);
		} catch (Exception e) {
			Log.e(TAG, "Problem opening media during loading", e);
		}
		Utils.log(TAG, "END play(...)");
	}

	public static void stop() {
		Utils.log(TAG, "START stop()");
		try {
			mRemoteMediaPlayer.requestStatus(apiClient).setResultCallback(new ResultCallback<RemoteMediaPlayer.MediaChannelResult>() {

				@Override
				public void onResult(RemoteMediaPlayer.MediaChannelResult mediaChannelResult) {
					Status status = mediaChannelResult.getStatus();
					Utils.log(TAG, "RemoteMediaPlayer requestStatus: Status=" + status.getStatus());
					try {
						mRemoteMediaPlayer.stop(apiClient);
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
		Utils.log(TAG, "END stop()");
	}

	public static final MediaRouter.Callback mediaRouterCallback = new MediaRouter.Callback() {
		@Override
		public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo route) {
			CastDevice device = CastDevice.getFromBundle(route.getExtras());
			setSelectedDevice(device);
		}

		@Override
		public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo route) {
			setSelectedDevice(null);
		}
	};

	public static boolean isClientConnected() {
		if (apiClient != null && apiClient.isConnected()) {
			return true;
		}
		return false;

	}

	// *****************************************
	// Private
	// *****************************************
	private static void initRemoteMediaPlayer() {
		mRemoteMediaPlayer = new RemoteMediaPlayer();
		mRemoteMediaPlayer.setOnStatusUpdatedListener(new RemoteMediaPlayer.OnStatusUpdatedListener() {
			@Override
			public void onStatusUpdated() {
				MediaStatus mediaStatus = mRemoteMediaPlayer.getMediaStatus();
				Utils.log(TAG, "OnStatusUpdatedListener onStatusUpdated mediaStatus=" + mediaStatus);
				boolean isPlaying = mediaStatus.getPlayerState() == MediaStatus.PLAYER_STATE_PLAYING;
				Utils.log(TAG, "isPlaying=" + isPlaying);
			}
		});

		mRemoteMediaPlayer.setOnMetadataUpdatedListener(new RemoteMediaPlayer.OnMetadataUpdatedListener() {
			@Override
			public void onMetadataUpdated() {
				MediaInfo mediaInfo = mRemoteMediaPlayer.getMediaInfo();
				MediaMetadata metadata = mediaInfo.getMetadata();
				Utils.log(TAG, "setOnMetadataUpdatedListener onMetadataUpdated metadata=" + metadata);
			}
		});
	}

	private static void setSelectedDevice(CastDevice device) {
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
			if (apiClient != null) {
				disconnectApiClient();
			}
			RadioRecPlusActivity.mediaRouter.selectRoute(RadioRecPlusActivity.mediaRouter.getDefaultRoute());
		}
	}

	private final static Cast.Listener castClientListener = new Cast.Listener() {
		@Override
		public void onApplicationDisconnected(int statusCode) {
			// http://developer.android.com/reference/com/google/android/gms/cast/CastStatusCodes.html
			Utils.log(TAG, "----- Cast.Listener - onApplicationDisconnected - statusCode = " + statusCode);

			try {
				Cast.CastApi.removeMessageReceivedCallbacks(apiClient, CastHelper.CAST_NAMESPACE);
			} catch (IOException e) {
				Log.w(TAG, "Exception while launching application", e);
			}
			setSelectedDevice(null);
			setSessionStarted(false);
		}

		@Override
		public void onVolumeChanged() {
			if (apiClient != null) {
				Utils.log(TAG, "----- onVolumeChanged: " + Cast.CastApi.getVolume(apiClient));
			}
		}
	};

	private final static ResultCallback<Cast.ApplicationConnectionResult> connectionResultCallback = new ResultCallback<Cast.ApplicationConnectionResult>() {
		@Override
		public void onResult(Cast.ApplicationConnectionResult result) {
			Status status = result.getStatus();
			Utils.log(TAG, "----- ResultCallback - onResult - status = " + status);
			Utils.log(TAG, "----- ResultCallback - onResult - status.isSuccess() = " + status.isSuccess());
			if (status.isSuccess()) {
				castApplicationStarted = true;

				try {
					Utils.log(TAG, "----- try setMessageReceivedCallbacks= " + status);
					Cast.CastApi.setMessageReceivedCallbacks(apiClient, CastHelper.CAST_NAMESPACE, incomingMsgHandler);
				} catch (IOException e) {
					Log.e(TAG, "Exception while creating channel", e);
				}

				setSessionStarted(true);
			} else {
				setSessionStarted(false);
			}
		}
	};

	private final static GoogleApiClient.ConnectionCallbacks connectionCallback = new GoogleApiClient.ConnectionCallbacks() {
		@Override
		public void onConnected(Bundle bundle) {
			Utils.log(TAG, "----- GoogleApiClient.ConnectionCallbacks - onConnected - bundle = " + bundle);
			try {
				Cast.CastApi.launchApplication(apiClient, CastHelper.CAST_APP_ID, false).setResultCallback(connectionResultCallback);
			} catch (Exception e) {
				Log.e(TAG, "Failed to launch application", e);
			}
		}

		@Override
		public void onConnectionSuspended(int i) {
			Utils.log(TAG, "----- GoogleApiClient.ConnectionCallbacks - onConnectionSuspended - int = " + i);
		}
	};

	private final static GoogleApiClient.OnConnectionFailedListener connectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
		@Override
		public void onConnectionFailed(ConnectionResult connectionResult) {
			Utils.log(TAG, "----- GoogleApiClient.OnConnectionFailedListener - connectionResult = " + connectionResult);
			setSelectedDevice(null);
			setSessionStarted(false);
		}
	};

	private static void connectApiClient() {
		Utils.log(TAG, "----- connectApiClient()");
		Cast.CastOptions apiOptions = Cast.CastOptions.builder(selectedDevice, castClientListener).build();
		apiClient = new GoogleApiClient.Builder(ApplicationRadioRec.getCustomAppContext()).addApi(Cast.API, apiOptions)
				.addConnectionCallbacks(connectionCallback).addOnConnectionFailedListener(connectionFailedListener).build();
		apiClient.connect();
	}

	private static void disconnectApiClient() {
		Utils.log(TAG, "----- disconnectApiClient - apiClient: " + apiClient);
		if (apiClient != null) {
			apiClient.disconnect();
			apiClient = null;
		}
		castApplicationStarted = false;
	}

	private static void stopApplication() {
		Utils.log(TAG, "----- stopApplication");
		if (apiClient == null)
			return;

		Utils.log(TAG, "----- applicationStarted = " + castApplicationStarted);
		if (castApplicationStarted) {
			Cast.CastApi.stopApplication(apiClient);
			castApplicationStarted = false;
		}
	}

	private static void setSessionStarted(boolean enabled) {
		Toast.makeText(ApplicationRadioRec.getCustomAppContext(), "Cast session started = " + enabled, Toast.LENGTH_SHORT).show();
	}

	private final static Cast.MessageReceivedCallback incomingMsgHandler = new Cast.MessageReceivedCallback() {
		@Override
		public void onMessageReceived(CastDevice castDevice, String namespace, String message) {
			Utils.log(TAG, "----- onMessageReceived castDevice: " + castDevice);
			Utils.log(TAG, "----- onMessageReceived namespace : " + namespace);
			Utils.log(TAG, "----- onMessageReceived message   : " + message);
		}
	};

}
