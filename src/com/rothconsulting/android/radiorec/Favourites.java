package com.rothconsulting.android.radiorec;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rothconsulting.android.radiorec.sqlitedb.DbUtils;

public class Favourites extends ListActivity {

	private final static String TAG = "Favourites";
	private Context context;
	private Button zurueckButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favourites);

		context = this;

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		Utils.getNotifInstance(context, RadioRecPlus.class).hideStatusBarNotification(Constants.NOTIFICATION_ID_ERROR_CONNECTION);

		setTitle(getString(R.string.favoriten));

		// ListView lv = (ListView) findViewById(android.R.id.list); // getListView();
		ListView lv = getListView();
		int[] colors = { 0, Color.RED, 0 }; // red for the example
		lv.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		lv.setDividerHeight(1);
		lv.setTextFilterEnabled(true);

		int currentOrient = this.getResources().getConfiguration().orientation;
		if (currentOrient == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			lv.setBackgroundResource(R.drawable.bg_port);
		} else {
			lv.setBackgroundResource(R.drawable.bg_land);
		}

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView textViewName = (TextView) ((LinearLayout) view).getChildAt(1); // 1 = Die zweite View (name)
				Utils.log(TAG, "name= " + textViewName.getText());

				Intent returnIntent = new Intent();
				returnIntent.putExtra("stationName", textViewName.getText());
				setResult(RESULT_OK, returnIntent);
				finish();
			}
		});

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Utils.log(TAG, "get onItem Click position= " + position);

				TextView textViewName = (TextView) ((LinearLayout) view).getChildAt(1); // 1 = Die zweite View (name)

				String stationName = "" + textViewName.getText();
				Utils.log(TAG, "name= " + stationName);

				Toast.makeText(context, textViewName.getText() + "\n" + getString(R.string.deleted), Toast.LENGTH_LONG).show();
				DbUtils.removeFavourite(context, stationName);
				updateFavList();
				return true;
			}
		});

		zurueckButton = (Button) findViewById(R.id.buttonZurueck);
		zurueckButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		AdMob ads = new AdMob();
		ads.showRemoveAds(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onStop() {
		super.onStop();
		Utils.log(TAG, "*********** onStop");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Utils.log(TAG, "*********** onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Utils.log(TAG, "*********** onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Utils.log(TAG, "*********** onResume");
		updateFavList();
	}

	private void updateFavList() {
		Utils.log(TAG, "context in update=" + context);
		ArrayList<HashMap<String, Object>> stationList = DbUtils.getFavListFromDb(context);
		Utils.log(TAG, "Stations=" + stationList.size());

		SimpleAdapter adapter = new SimpleAdapter(context, stationList, R.layout.favourites_listitem, new String[] { Stations.ICON, Stations.NAME }, new int[] {
				R.id.favourite_icon, R.id.favourite_text });

		((ListActivity) context).setListAdapter(adapter);

		if (stationList == null || stationList.size() == 0) {
			if (zurueckButton != null) {
				zurueckButton.setVisibility(View.VISIBLE);
				Utils.showEmptyFavAlertDialog(context);
			}
		} else if (zurueckButton != null) {
			zurueckButton.setVisibility(View.GONE);
		}

	}

	// ------------------------------------------------------------
	// Menu Stuff
	// ------------------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.backmenu, menu);
		if (Utils.hasValidKey()) {
			menu.removeItem(R.id.donate_adfree);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.zurueck:
			finish();
			return true;
		case R.id.donate_adfree:
			finish();
			this.startActivity(new Intent(this, Donate.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
