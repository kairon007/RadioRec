package com.rothconsulting.android.radiorec;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

	int tabHeight = 40;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LinearLayout main = new LinearLayout(this);
		main.setOrientation(LinearLayout.VERTICAL);
		setContentView(main);

		TabHost tabs = new TabHost(this);
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

		TabSpec tspec1 = tabs.newTabSpec("Tab1");
		tspec1.setIndicator(makeTabIndicator(getString(R.string.player)));
		tspec1.setContent(new Intent(this, RadioRecPlus.class));
		tabs.addTab(tspec1);

		TabSpec tspec2 = tabs.newTabSpec("Tab2");
		tspec2.setIndicator(makeTabIndicator(getString(R.string.laender)));
		tspec2.setContent(new Intent(this, TabCountry.class));
		tabs.addTab(tspec2);

		TabSpec tspec3 = tabs.newTabSpec("Tab3");
		tspec3.setIndicator(makeTabIndicator(getString(R.string.favoriten)));
		tspec3.setContent(new Intent(this, TabFavourites.class));
		// tspec3.setContent(new PreExistingViewFactory(content));
		tabs.addTab(tspec3);

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
}
