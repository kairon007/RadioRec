package com.rothconsulting.android.radiorec;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class Utils {

	private static final String TAG = Utils.class.getSimpleName();

	protected static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						Log.d(TAG, "Wir sind connected!");
						return true;
					}
				}
			}
		}
		Log.d(TAG, "Keine Connectivity");
		return false;
	}

	protected static void resumeProgressBarAnimation(ProgressBar pb) {
		if (pb.getVisibility() == View.VISIBLE) {
			pb.setVisibility(View.INVISIBLE);
			pb.setVisibility(View.VISIBLE);
		}
	}

	protected static ProgressDialog prepareProgressDialog(Context context) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(true);
		progressDialog.setMessage("Loading...");
		progressDialog.setTitle(R.string.app_name);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		return progressDialog;
	}

	protected static Notifications getNotifInstance(Context context,
			Class<?> clss) {
		return new Notifications(context, new Intent(context, clss));
	}

	protected static boolean hasValidKey() {
		String key = RadioRecPlus.ANTI_ADS_KEY;
		if (key != null && key.startsWith("rR+") && key.endsWith("so@p")) {
			return true;
		}
		return false;
	}

}
