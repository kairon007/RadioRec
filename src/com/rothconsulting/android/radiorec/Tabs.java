package com.rothconsulting.android.radiorec;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

public class Tabs extends ActivityGroup {
	private final static String TAG = "Tabs";
	int tabHeight = 40;
	TabHost tabs = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LinearLayout main = new LinearLayout(this);
		main.setBackgroundResource(R.drawable.bg_port);
		main.setOrientation(LinearLayout.VERTICAL);
		setContentView(main);

		tabs = new TabHost(this);
		// tabs.setOnTabChangedListener(this);
		tabs.setId(android.R.id.tabhost);
		main.addView(tabs);

		TabWidget tabWidget = new TabWidget(this);
		tabWidget.setId(android.R.id.tabs);
		tabs.addView(tabWidget);

		FrameLayout tabContent = new FrameLayout(this);
		tabContent.setId(android.R.id.tabcontent);
		tabContent.setPadding(0, tabHeight, 0, 0);
		tabs.addView(tabContent);

		// TextView content = new TextView(this);
		// content.setText("This is the Frame Content");
		// content.setId(100);
		tabs.setup(getLocalActivityManager());

		Log.d(TAG, "Tabs Tab1");
		TabSpec tspec1 = tabs.newTabSpec("Tab1");
		// Utils utils = new Utils();
		// Drawable resizedImg1 = utils.resizeImage(R.drawable.jukebox, this,
		// 40,
		// 40);
		tspec1.setIndicator(makeTabIndicator(getString(R.string.app_name)));
		tspec1.setContent(new Intent(this, RadioRecPlus.class));
		Log.d(TAG, "Tabs after Intent");
		tabs.addTab(tspec1);

		Log.d(TAG, "Tabs Tab2");
		TabSpec tspec2 = tabs.newTabSpec("Tab2");
		// Drawable resizedImg2 = utils.resizeImage(
		// android.R.drawable.ic_menu_agenda, this, 40, 40);
		tspec2.setIndicator(makeTabIndicator(getString(R.string.favoriten)));
		tspec2.setContent(new Intent(this, TabFavourites.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		tabs.addTab(tspec2);
		tabs.setCurrentTab(0);

		// TabSpec tspec3 = tabs.newTabSpec("Tab3");
		// tspec3.setIndicator(makeTabIndicator(getString(R.string.favoriten)));
		// tspec3.setContent(new Intent(this, TabFavourites.class));
		// // tspec3.setContent(new PreExistingViewFactory(content));
		// tabs.addTab(tspec3);

	}

	private TextView makeTabIndicator(String text) {

		TextView tabView = new TextView(this);
		LayoutParams lp3 = new LayoutParams(LayoutParams.WRAP_CONTENT,
				tabHeight, 1);
		lp3.setMargins(1, 0, 1, 0);
		tabView.setLayoutParams(lp3);
		tabView.setText(text);
		tabView.setTextColor(Color.WHITE);
		tabView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		tabView.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.tab_indicator));
		tabView.setPadding(13, 0, 13, 0);
		return tabView;

	}

	class PreExistingViewFactory implements TabContentFactory {

		private final View preExisting;

		protected PreExistingViewFactory(View view) {
			preExisting = view;
		}

		public View createTabContent(String tag) {
			return preExisting;
		}

	}

	// public void onTabChanged(String arg0) {
	// Log.d(TAG, "Tab changed -------------------------");
	// if (tabs.getCurrentTab() == 1) {
	// startActivity(new Intent(this, TabFavourites.class));
	// }
	// }
}
