package com.rothconsulting.android.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.rothconsulting.android.radiorec.R;
import com.samsung.android.sdk.multiwindow.SMultiWindow;
import com.samsung.android.sdk.multiwindow.SMultiWindowActivity;

public class MultiWindowFunction {

	private SMultiWindow mMultiWindow = null;
	private SMultiWindowActivity mMultiWindowActivity = null;
	private List<ResolveInfo> mMultiWindowAppList = null;
	private final Activity activity;

	public MultiWindowFunction(Activity activity) {
		this.activity = activity;
		mMultiWindow = new SMultiWindow();
		mMultiWindowActivity = new SMultiWindowActivity(activity);
	}

	public boolean isMultiWindowSupported() {
		if (mMultiWindow != null && mMultiWindow.isFeatureEnabled(SMultiWindow.MULTIWINDOW)) {
			return true;
		}
		return false;
	}

	public boolean isInMultiWindowMode() {
		if (mMultiWindowActivity != null && mMultiWindowActivity.isMultiWindow()) {
			return true;
		}
		return false;
	}

	public void displayAppList() {
		mMultiWindowAppList = new ArrayList<ResolveInfo>();
		Intent intent = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> resolveInfos = activity.getPackageManager().queryIntentActivities(intent,
				PackageManager.GET_RESOLVED_FILTER | PackageManager.GET_META_DATA);

		for (ResolveInfo r : resolveInfos) {
			if (r.activityInfo != null && r.activityInfo.applicationInfo.metaData != null) {
				if (r.activityInfo.applicationInfo.metaData.getBoolean("com.sec.android.support.multiwindow")
						|| r.activityInfo.applicationInfo.metaData.getBoolean("com.samsung.android.sdk.multiwindow.enable")) {
					boolean bUnSupportedMultiWinodw = false;
					if (r.activityInfo.metaData != null) {
						String activityWindowStyle = r.activityInfo.metaData.getString("com.sec.android.multiwindow.activity.STYLE");
						if (activityWindowStyle != null) {
							ArrayList<String> activityWindowStyles = new ArrayList<String>(Arrays.asList(activityWindowStyle.split("\\|")));
							if (!activityWindowStyles.isEmpty()) {
								if (activityWindowStyles.contains("fullscreenOnly")) {
									bUnSupportedMultiWinodw = true;
								}
							}
						}
					}

					if (!bUnSupportedMultiWinodw && !r.activityInfo.applicationInfo.packageName.equals("com.rothconsulting.android.radiorec")) {
						mMultiWindowAppList.add(r);
					}
				}
			}
		}

		ArrayList<String> appListLabels = new ArrayList<String>();
		if (mMultiWindowAppList != null) {
			int appListCount = mMultiWindowAppList.size();
			for (int i = 0; i < appListCount; i++) {
				appListLabels.add((String) mMultiWindowAppList.get(i).loadLabel(activity.getPackageManager()));
			}
		}

		String[] listItems = new String[0];
		listItems = appListLabels.toArray(listItems);
		AlertDialog.Builder appListDialog = new AlertDialog.Builder(activity);
		appListDialog.setTitle(activity.getString(R.string.multi_window)).setItems(listItems, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				displayLaunchType(which);
			}
		}).show();
	}

	private void displayLaunchType(final int appPosition) {
		final ArrayList<String> launchTypes = new ArrayList<String>();
		final String zoneA = "Zone A";
		final String zoneB = "Zone B";

		launchTypes.add(zoneA);
		launchTypes.add(zoneB);

		String[] listItems = new String[0];
		listItems = launchTypes.toArray(listItems);
		AlertDialog.Builder launchTypeDialog = new AlertDialog.Builder(activity);
		launchTypeDialog.setTitle(activity.getString(R.string.position)).setItems(listItems, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String launchType = launchTypes.get(which);
				ResolveInfo selectApp = mMultiWindowAppList.get(appPosition);
				ComponentInfo selectAppInfo = selectApp.activityInfo != null ? selectApp.activityInfo : selectApp.serviceInfo;
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setComponent(new ComponentName(selectAppInfo.packageName, selectAppInfo.name));
				if (zoneA.equals(launchType)) {
					SMultiWindowActivity.makeMultiWindowIntent(intent, SMultiWindowActivity.ZONE_A);
				} else if (zoneB.equals(launchType)) {
					SMultiWindowActivity.makeMultiWindowIntent(intent, SMultiWindowActivity.ZONE_B);
				} else {
					return;
				}
				activity.startActivity(intent);
			}
		}).show();
	}

}
