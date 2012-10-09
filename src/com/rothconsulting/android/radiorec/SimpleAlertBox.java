package com.rothconsulting.android.radiorec;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class SimpleAlertBox {

	public static void showAlert(Context context, int resTitle, int resMessage) {
		String title = context.getResources().getString(resTitle);
		String message = context.getResources().getString(resMessage);
		showAlert(context, title, message);
	}

	public static void showAlert(Context context, String title, String message) {
		final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
				context);
		// set the title to display
		alertBuilder.setTitle(title);
		// set the message to display
		alertBuilder.setMessage(message);
		// set a positive/yes button and create a listener
		alertBuilder.setPositiveButton(
				context.getResources().getString(android.R.string.ok),
				new DialogInterface.OnClickListener() {
					// do something when the button is clicked
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						return;
					}
				});
		// display box
		alertBuilder.show();
	}
}
