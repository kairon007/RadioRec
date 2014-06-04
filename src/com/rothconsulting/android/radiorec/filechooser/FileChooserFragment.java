package com.rothconsulting.android.radiorec.filechooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rothconsulting.android.radiorec.AdMob;
import com.rothconsulting.android.radiorec.AnalyticsUtil;
import com.rothconsulting.android.radiorec.ApplicationRadioRec;
import com.rothconsulting.android.radiorec.Constants;
import com.rothconsulting.android.radiorec.R;
import com.rothconsulting.android.radiorec.SettingsActivity;
import com.rothconsulting.android.radiorec.Utils;

public class FileChooserFragment extends ListFragment {

	private static final String TAG = "FileChooserFragment";
	private File currentDir;
	private FileArrayAdapter adapter;
	private String fileToDelete;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AnalyticsUtil.sendScreen("FileChooser screen");

		AdMob admob = new AdMob();
		admob.showRemoveAds(getActivity());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		currentDir = new File(Constants.SD_CARD_PATH_VALUE);
		getFileList(currentDir);

		getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

				Utils.log(TAG, "-------------- onItemLongClick ---------------------");
				LinearLayout linearLayout = (LinearLayout) ((LinearLayout) view).getChildAt(0); // 0 = Das zweite LinearLayout.

				// child 1 = Die TextView.
				fileToDelete = "" + ((TextView) linearLayout.getChildAt(1)).getText();

				if (isKnownMusicFile(fileToDelete)) {
					Utils.log(TAG, "FileUtils.isKnownMusicFile: " + fileToDelete);
					DeleteFileDialogFragment deleteDialog = new DeleteFileDialogFragment();
					deleteDialog.show(getFragmentManager(), "DeleteFileDialog");
				}
				// Return true to consume the click event. In this case
				// the
				// onListItemClick listener is not called anymore.
				return true;
			}
		});

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
				Option o = adapter.getItem(position);
				try {
					onFileClick(o);
				} catch (Exception e) {
					Utils.log(TAG, "onListItemClick - Exception! SD_CARD_PATH=" + Constants.SD_CARD_PATH_VALUE + "\nException=\n" + e);
					PathNotValidDialogFragment pathNotValidDialog = new PathNotValidDialogFragment();
					pathNotValidDialog.show(getFragmentManager(), "PathNotValidDialog");
				}
			}
		});

		super.onActivityCreated(savedInstanceState);
	}

	public void deleteFile(String filename) {
		File file = new File(currentDir + "/" + filename);
		Utils.log(TAG, "File=" + file.getName());
		file.delete();
		adapter.notifyDataSetChanged();
		getFileList(currentDir);

		Toast.makeText(ApplicationRadioRec.getAppContext(), filename + " " + getString(R.string.deleted), Toast.LENGTH_SHORT).show();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.file_list, container, false);
	}

	private void getFileList(File f) {

		List<Option> dir = new ArrayList<Option>();
		List<Option> fls = new ArrayList<Option>();

		try {
			File[] dirs = f.listFiles();

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
			Utils.log(TAG, "fill - Exception! SD_CARD_PATH=" + Constants.SD_CARD_PATH_VALUE + "\nException=\n" + e);
			PathNotValidDialogFragment pathNotValidDialog = new PathNotValidDialogFragment();
			pathNotValidDialog.show(getFragmentManager(), "PathNotValidDialog");

		}
		Collections.sort(dir);
		Collections.sort(fls);
		dir.addAll(fls);
		// if (!f.getName().equalsIgnoreCase("sdcard")) {
		// dir.add(0, new Option(R.drawable.icon_folder_up, "..", getString(R.string.parentFolder), f.getParent()));
		// }

		AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "FileChooser", "currentDir: " + f.getName());
		AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "FileChooser", "No. files: " + fls.size());

		adapter = new FileArrayAdapter(ApplicationRadioRec.getAppContext(), R.layout.file_list_item, dir);
		setListAdapter(adapter);
	}

	@SuppressLint("ValidFragment")
	public class DeleteFileDialogFragment extends DialogFragment {

		private static final String TAG = "DeleteFileDialogFragment";

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(getString(R.string.delete) + "?\n").setMessage(fileToDelete).setCancelable(true).setIcon(android.R.drawable.ic_delete)
					.setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							deleteFile(fileToDelete);
							Toast.makeText(ApplicationRadioRec.getAppContext(), fileToDelete + " " + getString(R.string.deleted), Toast.LENGTH_SHORT).show();
							Utils.log(TAG, "Delete file: " + fileToDelete);
						}
					}).setNegativeButton(getString(R.string.neinDanke), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
						}
					});
			return builder.create();
		}
	}

	public static class PathNotValidDialogFragment extends DialogFragment {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(getString(R.string.recordFolderNotFound)).setMessage(getString(R.string.recordFolderNotFoundMessage)).setCancelable(true)
					.setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton(getString(R.string.settings), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							startActivity(new Intent(ApplicationRadioRec.getAppContext(), SettingsActivity.class));
						}
					}).setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			return builder.create();
		}
	}

	private void onFileClick(Option o) throws Exception {
		// opens a music player on the device
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Utils.log(TAG, "path=" + o.getPath());

		AnalyticsUtil.sendEvent(AnalyticsUtil.UI_ACTION, "FileChooser", "Play file: " + o.getPath());

		File file = new File(o.getPath());
		intent.setDataAndType(Uri.fromFile(file), "audio/*");
		startActivity(intent);
	}

	private static boolean isKnownMusicFile(String filename) {

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

}