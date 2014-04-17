package com.rothconsulting.android.billing;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.rothconsulting.android.billing.util.IabHelper;
import com.rothconsulting.android.billing.util.IabResult;
import com.rothconsulting.android.billing.util.Inventory;
import com.rothconsulting.android.radiorec.ApplicationRadioRec;
import com.rothconsulting.android.radiorec.Constants;
import com.rothconsulting.android.radiorec.Utils;

public class BillingHelper {

	private static final String TAG = "BillingHelper";

	// 1243
	public static final String base64_1 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj5ydTsCcbpYVn74hpYwjrBYIY+6HRJAIIPG3kWZ8eZh8uznBasLAlrL";
	public static final String base64_2 = "UFCYQQKlMMD8TyWIRhvXHJ9dSANNs0LflOcOMOXEOKGcKLpvqST1ND5xj6Froq67DB2ta4cm39U0vZk9KbsbMZr44GkpP9CVfbn";
	public static final String base64_3 = "a8lNarO/eOxT2kCZFAk3BLwAPKVGG1MqICqKcqPQp47VTpaS5Q6VsPDktAg5Sp6bul564LAHnEUSw2ZtLLRVcNHkwIDAQAB";
	public static final String base64_4 = "SKW0ETM/3SK/BzzbvEsjCBspQ8YWPFMJlCsmE4puyVn4WXeWpsnb2OZ96gbMTxBRA52sw3zqiQOQ3B0SvURFeL/AXUojkavS2NL";

	// SKU
	public static final String RR_DONATE_BASIC = "rr_donate_basic";
	public static final String RR_DONATE_BASIC_PLUS = "rr_donate_basic_plus";
	public static final String RR_DONATE_BRONZE = "rr_donate_bronze";
	public static final String RR_DONATE_SILVER = "rr_donate_silver";
	public static final String RR_DONATE_GOLD = "rr_donate_gold";

	private List<String> getAllSku() {
		List<String> suks = new ArrayList<String>();
		suks.add(RR_DONATE_BASIC);
		suks.add(RR_DONATE_BASIC_PLUS);
		suks.add(RR_DONATE_BRONZE);
		suks.add(RR_DONATE_SILVER);
		suks.add(RR_DONATE_GOLD);
		return suks;
	}

	static IabHelper iabHelper;
	static Context context;

	public static boolean isDonator() {
		if (Constants.IS_DONATOR_VALUE) {
			return true;
		}
		context = ApplicationRadioRec.getCustomAppContext();
		fillIsDonatorValue(context, iabHelper);
		return Constants.IS_DONATOR_VALUE;
	}

	private static void fillIsDonatorValue(Context context, final IabHelper mHelper) {

		if (mHelper != null) {
			return;
		}

		// Create the helper, passing it our context and the public key to verify signatures with
		iabHelper = new IabHelper(context, BillingHelper.base64_1 + BillingHelper.base64_2 + BillingHelper.base64_4 + BillingHelper.base64_3);

		// Enable debug logging (for a production application, you should set this to false).
		// mHelper.enableDebugLogging(true);

		// Start setup. This is asynchronous and the specified listener will be called once setup completes.
		iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			@Override
			public void onIabSetupFinished(IabResult result) {
				// Something went wrong
				if (!result.isSuccess()) {
					Log.e(TAG, "Problem setting up in-app billing: " + result.getMessage());
					Toast.makeText(BillingHelper.context, "Problem setting up in-app billing: " + result.getMessage(), Toast.LENGTH_LONG).show();
					return;
				}

				// IAB is fully set up. Now, let's get an inventory of stuff we own.
				iabHelper.queryInventoryAsync(iabInventoryListener());
				// iabHelper.queryInventoryAsync(false, getAllSku(), iabInventoryListener());
			}
		});
	}

	/**
	 * Listener that's called when we finish querying the items and subscriptions we own
	 */
	private static IabHelper.QueryInventoryFinishedListener iabInventoryListener() {
		return new IabHelper.QueryInventoryFinishedListener() {
			@Override
			public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
				// Have we been disposed of in the meantime? If so, quit.
				if (iabHelper == null) {
					return;
				}

				// Something went wrong
				if (!result.isSuccess()) {
					return;
				}

				Utils.log(TAG, "result = " + result.getMessage());
				Toast.makeText(context, "result = " + result.getMessage(), Toast.LENGTH_LONG).show();

				if (inventory.hasPurchase(BillingHelper.RR_DONATE_BASIC) || //
						inventory.hasPurchase(BillingHelper.RR_DONATE_BASIC_PLUS) || //
						inventory.hasPurchase(BillingHelper.RR_DONATE_BRONZE) || //
						inventory.hasPurchase(BillingHelper.RR_DONATE_SILVER) || //
						inventory.hasPurchase(BillingHelper.RR_DONATE_GOLD)) {

					Constants.IS_DONATOR_VALUE = true;
					storeIsDonatorInSharedPrefs();
				}
				Utils.log(TAG, "inventory = " + inventory);
				Toast.makeText(context, "inventory = " + inventory, Toast.LENGTH_LONG).show();
				Toast.makeText(context, "Purchase Basic = " + inventory.getPurchase(RR_DONATE_BASIC), Toast.LENGTH_LONG).show();
				Toast.makeText(context, "isDonator = " + Constants.IS_DONATOR_VALUE, Toast.LENGTH_LONG).show();
			}
		};
	}

	public static void storeIsDonatorInSharedPrefs() {
		// Save in Shared Prefs
		Constants.IS_DONATOR_VALUE = true;
		SharedPreferences settings = context.getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(Constants.IS_DONATOR_KEY, Constants.IS_DONATOR_VALUE);
		editor.commit();
	}
}
