package com.rothconsulting.android.radiorec;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class AdMob {

	private final static String TAG = "AdMob";

	public void showRemoveAds(Activity context) {
		Utils utils = new Utils();
		if (utils.hasValidKey()) {
			LinearLayout adsLayout = (LinearLayout) context
					.findViewById(R.id.adsLayout);
			adsLayout.removeAllViews();
			adsLayout.setVisibility(View.GONE);
		} else {
			// Create the adView
			AdView adView = new AdView(context, AdSize.BANNER,
					Constants.ADMOB_PUBLISHER_ID);
			Log.d(TAG, "adView=" + adView);
			// Lookup your LinearLayout assuming it’s been given
			// the attribute android:id="@+id/mainLayout"
			LinearLayout layout = (LinearLayout) context
					.findViewById(R.id.adsLayout);
			// Add the adView to it
			layout.addView(adView);
			// Initiate a generic request to load it with an ad
			adView.loadAd(new AdRequest());
		}
	}
}