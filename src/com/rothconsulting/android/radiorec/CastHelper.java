package com.rothconsulting.android.radiorec;

import java.io.IOException;

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

	private static final String CAST_APP_ID = CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID;
	private static final String CAST_NAMESPACE = "urn:x-cast:koni.mobi.radiorec";

	private static RemoteMediaPlayer mRemoteMediaPlayer;

	// *****************************************
	// Public
	// *****************************************
	public static void init() {
		RadioRecPlusActivity.mediaRouter = MediaRouter.getInstance(ApplicationRadioRec.getCustomAppContext());
		RadioRecPlusActivity.mediaRouteSelector = new MediaRouteSelector.Builder().addControlCategory(CastMediaControlIntent.categoryForCast(CAST_APP_ID))
				.build();
		initRemoteMediaPlayer();
	}

	public static void play(String stationName, String stationUrl, String imageUrl) {
		imageUrl = "https://lh3.ggpht.com/2ivJrbp65Ke0SvS9yW4PpWYBiGSHglNMDQXSuI-GjVH13Bsw4GR_FuR8i9mcOizY5ys=w300";
		MediaMetadata mediaMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MUSIC_TRACK);
		mediaMetadata.putString(MediaMetadata.KEY_TITLE, stationName);
		mediaMetadata.addImage(new WebImage(Uri.parse(imageUrl)));
		MediaInfo mediaInfo = new MediaInfo.Builder(stationUrl).setContentType("audio/mp3").setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
				.setMetadata(mediaMetadata).build();
		try {
			mRemoteMediaPlayer.load(RadioRecPlusActivity.apiClient, mediaInfo, true).setResultCallback(
					new ResultCallback<RemoteMediaPlayer.MediaChannelResult>() {
						@Override
						public void onResult(MediaChannelResult result) {
							if (result.getStatus().isSuccess()) {
								Log.d(TAG, "Media loaded successfully");
							}
						}
					});
		} catch (IllegalStateException e) {
			Log.e(TAG, "Problem occurred with media during loading", e);
		} catch (Exception e) {
			Log.e(TAG, "Problem opening media during loading", e);
		}
	}

	public static void stop() {
		try {
			mRemoteMediaPlayer.requestStatus(RadioRecPlusActivity.apiClient).setResultCallback(new ResultCallback<RemoteMediaPlayer.MediaChannelResult>() {
				@Override
				public void onResult(RemoteMediaPlayer.MediaChannelResult mediaChannelResult) {
					Status stat = mediaChannelResult.getStatus();
					if (stat.isSuccess()) {
						Log.d("----Chromecast----", "mMediaPlayer getMediaStatus success");

						mRemoteMediaPlayer.stop(RadioRecPlusActivity.apiClient).setResultCallback(new ResultCallback<MediaChannelResult>() {
							@Override
							public void onResult(MediaChannelResult result) {
								Status status = result.getStatus();
								if (!status.isSuccess()) {
									Log.w(TAG, "Unable to stop: " + status.getStatusCode());
								}
							}
						});

					} else {
						Log.d("----Chromecast----", "mMediaPlayer getMediaStatus failure");
						// Disable controls and handle failure
					}
				}
			});
			// mRemoteMediaPlayer.stop(RadioRecPlusActivity.apiClient).setResultCallback(new ResultCallback<MediaChannelResult>() {
			// @Override
			// public void onResult(MediaChannelResult result) {
			// Status status = result.getStatus();
			// if (!status.isSuccess()) {
			// Log.w(TAG, "Unable to stop: " + status.getStatusCode());
			// }
			// }
			// });
		} catch (Exception e) {
			Utils.log(TAG, "cannot stop: " + e);
		}
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
		if (RadioRecPlusActivity.apiClient != null && RadioRecPlusActivity.apiClient.isConnected()) {
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
				Utils.log(TAG, "mediaStatus=" + mediaStatus);
				boolean isPlaying = mediaStatus.getPlayerState() == MediaStatus.PLAYER_STATE_PLAYING;
			}
		});

		mRemoteMediaPlayer.setOnMetadataUpdatedListener(new RemoteMediaPlayer.OnMetadataUpdatedListener() {
			@Override
			public void onMetadataUpdated() {
				MediaInfo mediaInfo = mRemoteMediaPlayer.getMediaInfo();
				MediaMetadata metadata = mediaInfo.getMetadata();
			}
		});
	}

	private static void setSelectedDevice(CastDevice device) {
		Log.d(TAG, "setSelectedDevice: " + device);

		RadioRecPlusActivity.selectedDevice = device;

		if (RadioRecPlusActivity.selectedDevice != null) {
			try {
				stopApplication();
				disconnectApiClient();
				connectApiClient();
			} catch (IllegalStateException e) {
				Log.w(TAG, "Exception while connecting API client", e);
				disconnectApiClient();
			}
		} else {
			if (RadioRecPlusActivity.apiClient != null) {
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
				Cast.CastApi.removeMessageReceivedCallbacks(RadioRecPlusActivity.apiClient, CastHelper.CAST_NAMESPACE);
			} catch (IOException e) {
				Log.w(TAG, "Exception while launching application", e);
			}
			setSelectedDevice(null);
			setSessionStarted(false);
		}

		@Override
		public void onVolumeChanged() {
			if (RadioRecPlusActivity.apiClient != null) {
				Utils.log(TAG, "----- onVolumeChanged: " + Cast.CastApi.getVolume(RadioRecPlusActivity.apiClient));
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
				RadioRecPlusActivity.castApplicationStarted = true;

				try {
					Utils.log(TAG, "----- try setMessageReceivedCallbacks= " + status);
					Cast.CastApi.setMessageReceivedCallbacks(RadioRecPlusActivity.apiClient, CastHelper.CAST_NAMESPACE, incomingMsgHandler);
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
				Cast.CastApi.launchApplication(RadioRecPlusActivity.apiClient, CastHelper.CAST_APP_ID, false).setResultCallback(connectionResultCallback);
			} catch (Exception e) {
				Log.e(TAG, "Failed to launch application", e);
			}
		}

		@Override
		public void onConnectionSuspended(int i) {
			Utils.log(TAG, "----- GoogleApiClient.ConnectionCallbacks - onConnectionSuspended - int = " + i);
		}
	};

	private static void connectApiClient() {
		Utils.log(TAG, "----- connectApiClient()");
		Cast.CastOptions apiOptions = Cast.CastOptions.builder(RadioRecPlusActivity.selectedDevice, castClientListener).build();
		RadioRecPlusActivity.apiClient = new GoogleApiClient.Builder(ApplicationRadioRec.getCustomAppContext()).addApi(Cast.API, apiOptions)
				.addConnectionCallbacks(connectionCallback).addOnConnectionFailedListener(connectionFailedListener).build();
		RadioRecPlusActivity.apiClient.connect();
	}

	private final static GoogleApiClient.OnConnectionFailedListener connectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
		@Override
		public void onConnectionFailed(ConnectionResult connectionResult) {
			Utils.log(TAG, "----- GoogleApiClient.OnConnectionFailedListener - connectionResult = " + connectionResult);
			setSelectedDevice(null);
			setSessionStarted(false);
		}
	};

	private static void disconnectApiClient() {
		Utils.log(TAG, "----- disconnectApiClient - apiClient: " + RadioRecPlusActivity.apiClient);
		if (RadioRecPlusActivity.apiClient != null) {
			RadioRecPlusActivity.apiClient.disconnect();
			RadioRecPlusActivity.apiClient = null;
		}
		RadioRecPlusActivity.castApplicationStarted = false;
	}

	private static void stopApplication() {
		Utils.log(TAG, "----- stopApplication");
		if (RadioRecPlusActivity.apiClient == null)
			return;

		Utils.log(TAG, "----- applicationStarted = " + RadioRecPlusActivity.castApplicationStarted);
		if (RadioRecPlusActivity.castApplicationStarted) {
			Cast.CastApi.stopApplication(RadioRecPlusActivity.apiClient);
			RadioRecPlusActivity.castApplicationStarted = false;
		}
	}

	private static void setSessionStarted(boolean enabled) {
		Toast.makeText(ApplicationRadioRec.getCustomAppContext(), "setSessionStarted = " + enabled, Toast.LENGTH_LONG).show();
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
