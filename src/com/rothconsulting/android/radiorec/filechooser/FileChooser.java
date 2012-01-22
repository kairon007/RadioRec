package com.rothconsulting.android.radiorec.filechooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.rothconsulting.android.radiorec.Constants;
import com.rothconsulting.android.radiorec.Donate;
import com.rothconsulting.android.radiorec.R;

public class FileChooser extends ListActivity {

	private final String TAG = this.getClass().getName();
	private File currentDir;
	private FileArrayAdapter adapter;

	private boolean isAllowedMusicfile(String filename) {

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
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentDir = new File(Constants.THE_SD_CARD_PATH);
		fill(currentDir);
	}

	private void fill(File f) {
		File[] dirs = f.listFiles();
		this.setTitle(getString(R.string.musicBrowser) + ", "
				+ getString(R.string.currentDir) + ": " + f.getName());
		List<Option> dir = new ArrayList<Option>();
		List<Option> fls = new ArrayList<Option>();
		try {
			for (File ff : dirs) {
				if (ff.isDirectory() && !ff.getName().startsWith(".")) {
					dir.add(new Option(R.drawable.icon_folder, ff.getName(),
							getString(R.string.folder), ff.getAbsolutePath()));
				} else if (isAllowedMusicfile(ff.getName())) {
					long fileSize = 0;
					if (ff.length() > 0) {
						fileSize = ff.length() / 1000;
					}

					fls.add(new Option(R.drawable.icon_music, ff.getName(),
							getString(R.string.fileSize) + ": " + fileSize
									+ " KB", ff.getAbsolutePath()));
				}
			}
		} catch (Exception e) {

		}
		Collections.sort(dir);
		Collections.sort(fls);
		dir.addAll(fls);
		if (!f.getName().equalsIgnoreCase("sdcard")) {
			dir.add(0, new Option(R.drawable.icon_folder_up, "..",
					getString(R.string.parentFolder), f.getParent()));
		}

		adapter = new FileArrayAdapter(FileChooser.this, R.layout.file_view,
				dir);
		this.setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Option o = adapter.getItem(position);
		if (o.getData().equalsIgnoreCase(getString(R.string.folder))
				|| o.getData().equalsIgnoreCase(
						getString(R.string.parentFolder))) {
			currentDir = new File(o.getPath());
			fill(currentDir);
		} else {
			onFileClick(o);
		}
	}

	private void onFileClick(Option o) {
		// opens a music player on the device
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Log.d(TAG, "path=" + o.getPath());
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