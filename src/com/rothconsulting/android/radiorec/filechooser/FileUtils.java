package com.rothconsulting.android.radiorec.filechooser;

import java.io.File;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.rothconsulting.android.common.Utils;
import com.rothconsulting.android.radiorec.Constants;

public class FileUtils {

	private static final String TAG = "FileUtils";

	public static final String RECORDS_DIR = "RadioRec";

	@SuppressLint("NewApi")
	public static File getRadioRecordingDirectory(Context context) {
		File radioRecorderDirectory;
		if (!Utils.isPlatformBelow_4_4() && Constants.WRITE_TO_EXT_STORAGE_VALUE) {
			if (!isExternalStorageWritable()) {
				Utils.log(TAG, "ERROR: External SD Card is not writable!!!");
				Toast.makeText(context, "ERROR: External SD Card not writable!!!", Toast.LENGTH_LONG).show();
				return null;
			} else {
				Utils.log(TAG, "OK: External SD Card is writable!!!");
			}
			File[] externalFilesDirs = ContextCompat.getExternalFilesDirs(context, RECORDS_DIR);
			Utils.log(TAG, "ContextCompat.getExternalFilesDirs(context, " + RECORDS_DIR + "): " + Arrays.asList(externalFilesDirs));
			Utils.log(TAG, "ContextCompat.getExternalCacheDirs(context): " + Arrays.asList(ContextCompat.getExternalCacheDirs(context)));
			Utils.log(TAG, "ContextCompat.getObbDirs(context): " + Arrays.asList(ContextCompat.getObbDirs(context)));

			if (externalFilesDirs != null && externalFilesDirs.length > 0) {
				Utils.log(
						TAG,
						"getExternalFilesDirs(context, RECORDS_DIR)[" + (externalFilesDirs.length - 1) + "]: "
								+ ContextCompat.getExternalFilesDirs(context, RECORDS_DIR)[externalFilesDirs.length - 1]);
				radioRecorderDirectory = ContextCompat.getExternalFilesDirs(context, RECORDS_DIR)[externalFilesDirs.length - 1];
			} else {
				Utils.log(TAG, "Environment.getExternalStoragePublicDirectory(RECORDS_DIR): "
						+ Environment.getExternalStoragePublicDirectory(RECORDS_DIR).getName());
				radioRecorderDirectory = Environment.getExternalStoragePublicDirectory(RECORDS_DIR);
			}
		} else {
			radioRecorderDirectory = new File("/" + Constants.SD_CARD_PATH_VALUE + "/");
		}
		return radioRecorderDirectory;
	}

	/* Checks if external storage is available for read and write */
	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		Utils.log(TAG, "getExternalStorageState: " + state);
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		Utils.log(TAG, "getExternalStorageState: " + state);
		if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

}
