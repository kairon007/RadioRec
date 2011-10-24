package com.rothconsulting.android.radiorec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

public class Tabs extends ActivityGroup implements TabHost.TabContentFactory {

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
		Utils utils = new Utils();
		Drawable resizedImg1 = utils.resizeImage(R.drawable.jukebox, this, 40,
				40);
		tspec1.setIndicator(getString(R.string.app_name), resizedImg1);
		tspec1.setContent(new Intent(this, RadioRecPlus.class));
		tabs.addTab(tspec1);

		TabSpec tspec2 = tabs.newTabSpec("Tab2");
		Drawable resizedImg2 = utils.resizeImage(
				android.R.drawable.ic_menu_agenda, this, 40, 40);
		tspec2.setIndicator(getString(R.string.senderNach), resizedImg2);
		tspec2.setContent(new Intent(this, TabFavourites.class));
		tabs.addTab(tspec2);

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

	private void openFav() {

		List<HashMap<String, Object>> stationList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("icon_small", 0);
		m.put("name", "Radio 32");
		stationList.add(m);
		// apply list to spinner
		SimpleAdapter adapter = new SimpleAdapter(this, stationList,
				R.layout.station_listitem,
				new String[] { "icon_small", "name" }, new int[] {
						R.id.option_icon, R.id.option_text });
		Spinner stations = new Spinner(this);
		stations.setPrompt("Favoriten");
		stations.setAdapter(adapter);
		stations.pointToPosition(1, 1);
		stations.performClick();
	}

	public View createTabContent(String tag) {
		// TODO Auto-generated method stub
		return null;
	}
}
