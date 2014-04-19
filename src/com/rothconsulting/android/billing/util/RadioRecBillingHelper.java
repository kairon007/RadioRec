package com.rothconsulting.android.billing.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.rothconsulting.android.radiorec.ApplicationRadioRec;
import com.rothconsulting.android.radiorec.Constants;
import com.rothconsulting.android.radiorec.Utils;

public class RadioRecBillingHelper {

	private static final String TAG = "RadioRecBillingHelper";

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
	static boolean isDonator = false;

	/**
	 * If it is a Donator it will be stored in the SharedPreferences
	 * 
	 * @return
	 */
	public static boolean isDonator() {
		iabHelper = null;
		context = ApplicationRadioRec.getCustomAppContext();
		if (Utils.hasValidKey()) {
			isDonator = true;
		}
		fillIsDonatorValue();
		return isDonator;
	}

	private static void fillIsDonatorValue() {

		Utils.log(TAG, "fillIsDonatorValue");

		if (iabHelper != null) {
			Utils.log(TAG, "iabHelper != null");
			return;
		}

		// Create the helper, passing it our context and the public key to verify signatures with
		iabHelper = new IabHelper(context, RadioRecBillingHelper.base64_1 + RadioRecBillingHelper.base64_2 + RadioRecBillingHelper.base64_4
				+ RadioRecBillingHelper.base64_3);

		// Enable debug logging (for a production application, you should set this to false).
		iabHelper.enableDebugLogging(true);

		iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			@Override
			public void onIabSetupFinished(IabResult result) {
				// Have we been disposed of in the meantime? If so, quit.
				if (iabHelper == null) {
					Utils.log(TAG, "iabHelper == null");
					return;
				}

				if (!result.isSuccess()) {
					Log.d(TAG, "In-app Billing setup failed: " + result);
				} else {
					Log.d(TAG, "In-app Billing is set up OK");
					// IAB is fully set up.
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
					Utils.log(TAG, "iabHelper == null -> return");
					return;
				}

				// Something went wrong
				if (!result.isSuccess()) {
					return;
				}

				if (inventory.hasPurchase(RadioRecBillingHelper.RR_DONATE_BASIC) || //
						inventory.hasPurchase(RadioRecBillingHelper.RR_DONATE_BASIC_PLUS) || //
						inventory.hasPurchase(RadioRecBillingHelper.RR_DONATE_BRONZE) || //
						inventory.hasPurchase(RadioRecBillingHelper.RR_DONATE_SILVER) || //
						inventory.hasPurchase(RadioRecBillingHelper.RR_DONATE_GOLD)) {

					storeIsDonatorInSharedPrefs();
				}

				debug(result, inventory);
			}
		};
	}

	public static void storeIsDonatorInSharedPrefs() {
		// Save in Shared Prefs
		SharedPreferences settings = context.getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		Constants.ANTI_ADS_VALUE = Constants.ANTI_ADS_PLAY_DONATOR_VALUE;
		editor.putString(Constants.ANTI_ADS_KEY, Constants.ANTI_ADS_VALUE);
		editor.commit();
	}

	private static void debug(IabResult result, Inventory inventory) {

		Utils.log(TAG, "result = " + result.getMessage());
		Toast.makeText(context, "result = " + result.getMessage(), Toast.LENGTH_LONG).show();

		Utils.log(TAG, "inventory = " + inventory);
		Toast.makeText(context, "Inventory getPurchase BASIC = " + inventory.getPurchase(RR_DONATE_BASIC), Toast.LENGTH_LONG).show();

		List<String> allOwnedSkus = inventory.getAllOwnedSkus();
		List<String> allOwnedSkusInapp = inventory.getAllOwnedSkus(IabHelper.ITEM_TYPE_INAPP);
		List<Purchase> allPurchases = inventory.getAllPurchases();
		int allOwnedSkusSize = 0;
		if (allOwnedSkus != null) {
			allOwnedSkusSize = allOwnedSkus.size();
		}
		int allOwnedSkusInappSize = 0;
		if (allOwnedSkusInapp != null) {
			allOwnedSkusInappSize = allOwnedSkusInapp.size();
		}
		int allPurchasesSize = 0;
		if (allPurchases != null) {
			allPurchasesSize = allPurchases.size();
		}

		Utils.log(TAG, "AllOwnedSkus (" + allOwnedSkusSize + ") = " + inventory.getAllOwnedSkus());
		Toast.makeText(context, "AllOwnedSkus (" + allOwnedSkusSize + ") = " + inventory.getAllOwnedSkus(), Toast.LENGTH_LONG).show();

		Utils.log(TAG, "AllPurchases (" + allPurchasesSize + ")= " + inventory.getAllPurchases());
		Toast.makeText(context, "AllPurchases (" + allPurchasesSize + ")= " + inventory.getAllPurchases(), Toast.LENGTH_LONG).show();

		Utils.log(TAG, "AllOwnedSkus of inapp (" + allOwnedSkusInappSize + ") = " + inventory.getAllOwnedSkus(IabHelper.ITEM_TYPE_INAPP));
		Toast.makeText(context, "AllOwnedSkus of inapp (" + allOwnedSkusInappSize + ") = " + inventory.getAllOwnedSkus(IabHelper.ITEM_TYPE_INAPP),
				Toast.LENGTH_LONG).show();

		Utils.log(TAG, "isDonator = " + Constants.ANTI_ADS_VALUE);
		Toast.makeText(context, "isDonator = " + Constants.ANTI_ADS_VALUE, Toast.LENGTH_LONG).show();
	}
}
