package com.rothconsulting.android.common;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.rothconsulting.android.radiorec.R;

public class AdMob {

	private final static String TAG = "AdMob";
	private AdView adView;

	public AdView showRemoveAds(Activity activity) {
		adView = new AdView(activity);

		if (Utils.isPlatformBelow_2_3_0() || Utils.hasValidKey() || !Utils.isNetworkAvailable(activity, null, false)) {
			LinearLayout adsLayout = (LinearLayout) activity.findViewById(R.id.adsLayout);
			adsLayout.removeAllViews();
			adsLayout.setVisibility(View.GONE);
		} else {
			return showGoogleAdMobAds(activity, adView);
		}
		return adView;
	}

	private AdView showGoogleAdMobAds(Activity activity, AdView adView) {

		adView.setAdUnitId("ca-app-pub-5619114666968507/6860732934");
		adView.setAdSize(AdSize.BANNER);

		LinearLayout layout = (LinearLayout) activity.findViewById(R.id.adsLayout);
		// Add the adView to it
		layout.addView(adView);
		// Initiate a generic request.
		AdRequest adRequest = new AdRequest.Builder().build();
		// Load the adView with the ad request.
		adView.loadAd(adRequest);

		return adView;
	}

}