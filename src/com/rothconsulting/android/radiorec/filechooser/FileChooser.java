package com.rothconsulting.android.radiorec.filechooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;
import com.rothconsulting.android.radiorec.Constants;
import com.rothconsulting.android.radiorec.Donate;
import com.rothconsulting.android.radiorec.R;
import com.rothconsulting.android.radiorec.Settings;
import com.rothconsulting.android.radiorec.Utils;

public class FileChooser extends ListActivity {

	private final String TAG = this.getClass().getName();
	private File currentDir;
	private FileArrayAdapter adapter;

	private Tracker mGaTracker;
	private GoogleAnalytics mGaInstance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Constants.ROTATION_OFF_VALUE) {
			// Prevent from Rotation
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		// Get the GoogleAnalytics singleton. Note that the SDK uses
		// the application context to avoid leaking the current context.
		mGaInstance = GoogleAnalytics.getInstance(this);
		// Use the GoogleAnalytics singleton to get a Tracker.
		mGaTracker = mGaInstance.getTracker(Constants.ANALYTICS_ID);

		if (Constants.SD_CARD_PATH_VALUE == null) {
			Utils.log(TAG, "SDCARD_PATH is null!");
			pathNotValidDialog().show();
		} else {

			try {
				currentDir = new File(Constants.SD_CARD_PATH_VALUE);
				fill(currentDir);
				ListView list = getListView();

				int currentOrient = this.getResources().getConfiguration().orientation;

				if (currentOrient == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
					list.setBackgroundResource(R.drawable.bg_port);
				} else {
					list.setBackgroundResource(R.drawable.bg_land);
				}

				list.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

						LinearLayout linearLayout = (LinearLayout) ((LinearLayout) view).getChildAt(0); // 0 = Das zweite LinearLayout.

						// child 1 = Die TextView.
						final String filename = "" + ((TextView) linearLayout.getChildAt(1)).getText();

						if (isKnownMusicFile(filename)) {
							deleteFileDialog(filename).show();
						}
						// Return true to consume the click event. In this case
						// the
						// onListItemClick listener is not called anymore.
						return true;
					}
				});
			} catch (Exception e) {
				Utils.log(TAG, "Exception! SD_CARD_PATH=" + Constants.SD_CARD_PATH_VALUE + "\nException=\n" + e);
				pathNotValidDialog().show();
			}
		}
	}

	private void fill(File f) {

		List<Option> dir = new ArrayList<Option>();
		List<Option> fls = new ArrayList<Option>();

		try {
			File[] dirs = f.listFiles();
			this.setTitle(getString(R.string.musicBrowser) + ", " + getString(R.string.currentDir) + ": " + f.getName());

			for (File ff : dirs) {
				if (ff.isDirectory() && !ff.getName().startsWith(".")) {
					dir.add(new Option(R.drawable.icon_folder, ff.getName(), getString(R.string.folder), ff.getAbsolutePath()));
				} else if (isKnownMusicFile(ff.getName())) {
					long fileSize = 0;
					if (ff.length() > 0) {
						fileSize = ff.length() / 1000;
					}

					fls.add(new Option(R.drawable.icon_music, ff.getName(), getString(R.string.fileSize) + ": " + fileSize + " KB", ff.getAbsolutePath()));
				}
			}
		} catch (Exception e) {
			pathNotValidDialog().show();
		}
		Collections.sort(dir);
		Collections.sort(fls);
		dir.addAll(fls);
		if (!f.getName().equalsIgnoreCase("sdcard")) {
			dir.add(0, new Option(R.drawable.icon_folder_up, "..", getString(R.string.parentFolder), f.getParent()));
		}

		if (mGaTracker != null) {
			mGaTracker.sendEvent("ui_action", "FileChooser", "currentDir: " + f.getName(), 0L);
			mGaTracker.sendEvent("ui_action", "FileChooser", "No. files: " + fls.size(), 0L);
		}

		adapter = new FileArrayAdapter(FileChooser.this, R.layout.file_view, dir);
		this.setListAdapter(adapter);
	}

	private boolean isKnownMusicFile(String filename) {

		if (filename.endsWith(".mp3")) {
			return true;
		}
		if (filename.endsWith(".MP3")) {
			return true;
		}
		if (filename.endsWith(".aac")) {
			return true;
		}
		if (filename.endsWith(".AAC")) {
			return true;
		}
		if (filename.endsWith(".3ga")) {
			return true;
		}
		if (filename.endsWith(".3GA")) {
			return true;
		}
		if (filename.endsWith(".m4a")) {
			return true;
		}
		if (filename.endsWith(".M4A")) {
			return true;
		}
		return false;
	}

	private AlertDialog deleteFileDialog(final String filename) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.delete) + "?\n").setMessage(filename).setCancelable(true).setIcon(android.R.drawable.ic_delete)
				.setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						File file = new File(currentDir + "/" + filename);
						Utils.log(TAG, "File=" + file.getName());
						file.delete();
						adapter.notifyDataSetChanged();
						fill(currentDir);

						Toast.makeText(FileChooser.this, filename + " " + getString(R.string.deleted), Toast.LENGTH_SHORT).show();
					}
				}).setNegativeButton(getString(R.string.neinDanke), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		return builder.create();
	}

	private AlertDialog pathNotValidDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.recordFolderNotFound)).setMessage(getString(R.string.recordFolderNotFoundMessage)).setCancelable(true)
				.setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton(getString(R.string.settings), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						finish();
						startActivity(new Intent(getApplicationContext(), Settings.class));
					}
				}).setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						finish();
					}
				});
		return builder.create();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		try {
			super.onListItemClick(l, v, position, id);
			Option o = adapter.getItem(position);
			if (o.getData().equalsIgnoreCase(getString(R.string.folder)) || o.getData().equalsIgnoreCase(getString(R.string.parentFolder))) {
				currentDir = new File(o.getPath());
				fill(currentDir);
			} else {
				onFileClick(o);
			}
		} catch (Exception e) {
			pathNotValidDialog().show();
		}
	}

	private void onFileClick(Option o) throws Exception {
		// opens a music player on the device
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Utils.log(TAG, "path=" + o.getPath());
		if (mGaTracker != null) {
			mGaTracker.sendEvent("ui_action", "FileChooser", "Play file: " + o.getPath(), 0L);
		}
		File file = new File(o.getPath());
		intent.setDataAndType(Uri.fromFile(file), "audio/*");
		startActivity(intent);
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

	@Override
	public void onStart() {
		super.onStart();
		// Google Analytics
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		// Google Analytics
		EasyTracker.getInstance().activityStop(this);
	}

}