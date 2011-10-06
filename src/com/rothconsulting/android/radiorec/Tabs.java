package com.rothconsulting.android.radiorec;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class Tabs extends TabActivity implements OnTabChangeListener {

	TabHost tabHost;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tabs);
		Resources res = getResources(); // Resource object to get Drawables

		/** TabHost will have Tabs */
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setOnTabChangedListener(this);
		/**
		 * TabSpec used to create a new tab. By using TabSpec only we can able
		 * to setContent to the tab. By using TabSpec setIndicator() we can set
		 * name to tab.
		 */

		/** tid1 is firstTabSpec Id. Its used to access outside. */
		TabSpec tabSpecCountry = tabHost.newTabSpec("tag");
		TabSpec tabSpecGenre = tabHost.newTabSpec("tag");
		TabSpec tabSpecFavourites = tabHost.newTabSpec("tag");

		/** TabSpec setIndicator() is used to set name for the tab. */
		/** TabSpec setContent() is used to set content for a particular tab. */
		tabSpecCountry.setIndicator("Country",
				res.getDrawable(android.R.drawable.ic_menu_compass))
				.setContent(new Intent(this, TabCountry.class));
		tabSpecGenre.setIndicator("Genre",
				res.getDrawable(android.R.drawable.ic_menu_agenda)).setContent(
				new Intent(this, TabGenre.class));
		tabSpecFavourites.setIndicator("Favourites",
				res.getDrawable(android.R.drawable.star_big_on)).setContent(
				new Intent(this, RadioRecPlus.class));

		/** Add tabSpec to the TabHost to display. */
		tabHost.addTab(tabSpecCountry);
		tabHost.addTab(tabSpecGenre);
		tabHost.addTab(tabSpecFavourites);

		// setTabColor(tabHost);
	}

	public static void setTabColor(TabHost tabhost) {
		for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
			tabhost.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.parseColor("#B5CDE7")); // unselected
		}
		tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab())
				.setBackgroundColor(Color.parseColor("#3278B3")); // selected
	}

	public void onTabChanged(String tabId) {
		// setTabColor(tabHost);
	}

}
