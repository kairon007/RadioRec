package com.rothconsulting.android.radiorec;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class AdMob {

	private final static String TAG = "AdMob";

	public void showRemoveAds(Activity context) {
		if (Utils.hasValidKey()) {
			LinearLayout adsLayout = (LinearLayout) context.findViewById(R.id.adsLayout);
			adsLayout.removeAllViews();
			adsLayout.setVisibility(View.GONE);
		} else {
			showGoogleAdMobAds(context);
			// showAmazonAds(context);
		}
	}

	private void showGoogleAdMobAds(Activity context) {
		// Create the adView
		AdView adView = new AdView(context, AdSize.BANNER, Constants.ADMOB_PUBLISHER_ID);
		Utils.log(TAG, "adView=" + adView);
		// Lookup your LinearLayout assuming it's been given
		// the attribute android:id="@+id/mainLayout"
		LinearLayout layout = (LinearLayout) context.findViewById(R.id.adsLayout);
		// Add the adView to it
		layout.addView(adView);
		// Initiate a generic request to load it with an ad
		adView.loadAd(new AdRequest());
	}

	// private void showAmazonAds(Activity context) {
	// AdRegistration.setAppKey(Constants.AMAZON_APPLICATION_KEY);
	// // Programmatically create the AmazonAdLayout
	// AdLayout adView = new AdLayout(context);
	// LinearLayout layout = (LinearLayout) context.findViewById(R.id.adsLayout);
	// // Set the correct width and height of the ad.
	// LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
	// layout.addView(adView, lp);
	// // If you declared AdLayout in your xml you would instead
	// // replace the 3 lines above with the following line
	// // this.adView = (AdLayout) findViewById( R.id.adview );
	//
	// }
}