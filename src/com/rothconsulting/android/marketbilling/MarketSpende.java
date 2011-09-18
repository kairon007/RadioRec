/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rothconsulting.android.marketbilling;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rothconsulting.android.marketbilling.BillingService.RequestPurchase;
import com.rothconsulting.android.marketbilling.BillingService.RestoreTransactions;
import com.rothconsulting.android.marketbilling.Constants.PurchaseState;
import com.rothconsulting.android.marketbilling.Constants.ResponseCode;
import com.rothconsulting.android.radiorec.AdMob;
import com.rothconsulting.android.radiorec.R;

/**
 * A sample application that demonstrates in-app billing.
 */
public class MarketSpende extends Activity implements OnClickListener,
		OnItemSelectedListener {
	private static final String TAG = "RadioRecMarketSpende";

	/**
	 * Used for storing the log text.
	 */
	private static final String LOG_TEXT_KEY = "SPENDE_LOG_TEXT";

	/**
	 * The SharedPreferences key for recording whether we initialized the
	 * database. If false, then we perform a RestoreTransactions request to get
	 * all the purchases for this user.
	 */
	private static final String DB_INITIALIZED = "db_initialized";

	private SpendePurchaseObserver mSpendePurchaseObserver;
	private Handler mHandler;

	private BillingService mBillingService;
	private Button mBuyButton;
	private TextView mLogTextView;
	private Spinner mSelectItemSpinner;
	private ListView mOwnedItemsTable;
	private SimpleCursorAdapter mOwnedItemsAdapter;
	private PurchaseDatabase mPurchaseDatabase;
	private Cursor mOwnedItemsCursor;
	private final Set<String> mOwnedItems = new HashSet<String>();

	/**
	 * The developer payload that is sent with subsequent purchase requests.
	 */
	private final String mPayloadContents = null;

	private static final int DIALOG_CANNOT_CONNECT_ID = 1;
	private static final int DIALOG_BILLING_NOT_SUPPORTED_ID = 2;

	/**
	 * Each product in the catalog is either MANAGED or UNMANAGED. MANAGED means
	 * that the product can be purchased only once per user (such as a new level
	 * in a game). The purchase is remembered by Android Market and can be
	 * restored if this application is uninstalled and then re-installed.
	 * UNMANAGED is used for products that can be used up and purchased multiple
	 * times (such as poker chips). It is up to the application to keep track of
	 * UNMANAGED products for the user.
	 */
	private enum Managed {
		MANAGED, UNMANAGED
	}

	/**
	 * A {@link PurchaseObserver} is used to get callbacks when Android Market
	 * sends messages to this application so that we can update the UI.
	 */
	private class SpendePurchaseObserver extends PurchaseObserver {
		public SpendePurchaseObserver(Handler handler) {
			super(MarketSpende.this, handler);
		}

		@Override
		public void onBillingSupported(boolean supported) {
			if (Constants.DEBUG) {
				Log.i(TAG, "supported: " + supported);
			}
			if (supported) {
				restoreDatabase();
				mBuyButton.setEnabled(true);
			} else {
				showDialog(DIALOG_BILLING_NOT_SUPPORTED_ID);
			}
		}

		@Override
		public void onPurchaseStateChange(PurchaseState purchaseState,
				String itemId, int quantity, long purchaseTime,
				String developerPayload) {
			if (Constants.DEBUG) {
				Log.i(TAG, "onPurchaseStateChange() itemId: " + itemId + " "
						+ purchaseState);
			}

			if (developerPayload == null) {
				logProductActivity(itemId, purchaseState.toString());
			} else {
				logProductActivity(itemId, purchaseState + "\n\t"
						+ developerPayload);
			}

			if (purchaseState == PurchaseState.PURCHASED) {
				mOwnedItems.add(itemId);
			}
			mCatalogAdapter.setOwnedItems(mOwnedItems);
			mOwnedItemsCursor.requery();
		}

		@Override
		public void onRequestPurchaseResponse(RequestPurchase request,
				ResponseCode responseCode) {
			if (Constants.DEBUG) {
				Log.d(TAG, request.mProductId + ": " + responseCode);
			}
			if (responseCode == ResponseCode.RESULT_OK) {
				if (Constants.DEBUG) {
					Log.i(TAG, "purchase was successfully sent to server");
				}
				logProductActivity(request.mProductId,
						"sending purchase request");
			} else if (responseCode == ResponseCode.RESULT_USER_CANCELED) {
				if (Constants.DEBUG) {
					Log.i(TAG, "user canceled purchase");
				}
				logProductActivity(request.mProductId,
						"dismissed purchase dialog");
			} else {
				if (Constants.DEBUG) {
					Log.i(TAG, "purchase failed");
				}
				logProductActivity(request.mProductId,
						"request purchase returned " + responseCode);
			}
		}

		@Override
		public void onRestoreTransactionsResponse(RestoreTransactions request,
				ResponseCode responseCode) {
			if (responseCode == ResponseCode.RESULT_OK) {
				if (Constants.DEBUG) {
					Log.d(TAG, "completed RestoreTransactions request");
				}
				// Update the shared preferences so that we don't perform
				// a RestoreTransactions again.
				SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
				SharedPreferences.Editor edit = prefs.edit();
				edit.putBoolean(DB_INITIALIZED, true);
				edit.commit();
			} else {
				if (Constants.DEBUG) {
					Log.d(TAG, "RestoreTransactions error: " + responseCode);
				}
			}
		}
	}

	private static class CatalogEntry {
		public String sku;
		public int nameId;
		public Managed managed;

		public CatalogEntry(String sku, int nameId, Managed managed) {
			this.sku = sku;
			this.nameId = nameId;
			this.managed = managed;
		}
	}

	/** An array of product list entries for the products that can be purchased. */
	private static final CatalogEntry[] CATALOG = new CatalogEntry[] {
			new CatalogEntry("rr_donate_basic", R.string.donate_basic,
					Managed.UNMANAGED),
			new CatalogEntry("rr_donate_basic_plus",
					R.string.donate_basic_plus, Managed.UNMANAGED),
			new CatalogEntry("rr_donate_bronze", R.string.donate_bronze,
					Managed.UNMANAGED),
			new CatalogEntry("rr_donate_silver", R.string.donate_silver,
					Managed.UNMANAGED),
			new CatalogEntry("rr_donate_gold", R.string.donate_gold,
					Managed.UNMANAGED) };
	private String mItemName;
	private String mSku;
	private CatalogAdapter mCatalogAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.donate_market);

		final Button zurueckButton = (Button) findViewById(R.id.buttonZurueck);
		zurueckButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		AdMob.showRemoveAds(this);

		mHandler = new Handler();
		mSpendePurchaseObserver = new SpendePurchaseObserver(mHandler);
		mBillingService = new BillingService();
		mBillingService.setContext(this);

		mPurchaseDatabase = new PurchaseDatabase(this);
		setupWidgets();

		// Check if billing is supported.
		ResponseHandler.register(mSpendePurchaseObserver);
		if (!mBillingService.checkBillingSupported()) {
			showDialog(DIALOG_CANNOT_CONNECT_ID);
		}
	}

	/**
	 * Called when this activity becomes visible.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		ResponseHandler.register(mSpendePurchaseObserver);
		initializeOwnedItems();
	}

	/**
	 * Called when this activity is no longer visible.
	 */
	@Override
	protected void onStop() {
		super.onStop();
		ResponseHandler.unregister(mSpendePurchaseObserver);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPurchaseDatabase.close();
		mBillingService.unbind();
	}

	/**
	 * Save the context of the log so simple things like rotation will not
	 * result in the log being cleared.
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(LOG_TEXT_KEY,
				Html.toHtml((Spanned) mLogTextView.getText()));
	}

	/**
	 * Restore the contents of the log if it has previously been saved.
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null) {
			mLogTextView.setText(Html.fromHtml(savedInstanceState
					.getString(LOG_TEXT_KEY)));
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_CANNOT_CONNECT_ID:
			return createDialog(R.string.cannot_connect_title,
					R.string.cannot_connect_message);
		case DIALOG_BILLING_NOT_SUPPORTED_ID:
			return createDialog(R.string.billing_not_supported_title,
					R.string.billing_not_supported_message);
		default:
			return null;
		}
	}

	private Dialog createDialog(int titleId, int messageId) {
		String helpUrl = replaceLanguageAndRegion(getString(R.string.help_url));
		if (Constants.DEBUG) {
			Log.i(TAG, helpUrl);
		}
		final Uri helpUri = Uri.parse(helpUrl);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(titleId)
				.setIcon(android.R.drawable.stat_sys_warning)
				.setMessage(messageId)
				.setCancelable(false)
				.setPositiveButton(android.R.string.ok, null)
				.setNegativeButton(R.string.learn_more,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(Intent.ACTION_VIEW,
										helpUri);
								startActivity(intent);
							}
						});
		return builder.create();
	}

	/**
	 * Replaces the language and/or country of the device into the given string.
	 * The pattern "%lang%" will be replaced by the device's language code and
	 * the pattern "%region%" will be replaced with the device's country code.
	 * 
	 * @param str
	 *            the string to replace the language/country within
	 * @return a string containing the local language and region codes
	 */
	private String replaceLanguageAndRegion(String str) {
		// Substitute language and or region if present in string
		if (str.contains("%lang%") || str.contains("%region%")) {
			Locale locale = Locale.getDefault();
			str = str.replace("%lang%", locale.getLanguage().toLowerCase());
			str = str.replace("%region%", locale.getCountry().toLowerCase());
		}
		return str;
	}

	/**
	 * Sets up the UI.
	 */
	private void setupWidgets() {
		mLogTextView = (TextView) findViewById(R.id.log);

		mBuyButton = (Button) findViewById(R.id.donate_button);
		mBuyButton.setEnabled(false);
		mBuyButton.setOnClickListener(this);

		mSelectItemSpinner = (Spinner) findViewById(R.id.item_choices);
		mCatalogAdapter = new CatalogAdapter(this, CATALOG);
		mSelectItemSpinner.setAdapter(mCatalogAdapter);
		mSelectItemSpinner.setSelection(1);
		mSelectItemSpinner.setOnItemSelectedListener(this);

		mOwnedItemsCursor = mPurchaseDatabase.queryAllPurchasedItems();
		startManagingCursor(mOwnedItemsCursor);
		String[] from = new String[] {
				PurchaseDatabase.PURCHASED_PRODUCT_ID_COL,
				PurchaseDatabase.PURCHASED_QUANTITY_COL };
		int[] to = new int[] { R.id.item_name, R.id.item_quantity };
		mOwnedItemsAdapter = new SimpleCursorAdapter(this, R.layout.item_row,
				mOwnedItemsCursor, from, to);
	}

	private void prependLogEntry(CharSequence cs) {
		SpannableStringBuilder contents = new SpannableStringBuilder(cs);
		contents.append('\n');
		contents.append(mLogTextView.getText());
		mLogTextView.setText(contents);
	}

	private void logProductActivity(String product, String activity) {
		SpannableStringBuilder contents = new SpannableStringBuilder();
		contents.append(Html.fromHtml("<b>" + product + "</b>: "));
		contents.append(activity);
		prependLogEntry(contents);
	}

	/**
	 * If the database has not been initialized, we send a RESTORE_TRANSACTIONS
	 * request to Android Market to get the list of purchased items for this
	 * user. This happens if the application has just been installed or the user
	 * wiped data. We do not want to do this on every startup, rather, we want
	 * to do only when the database needs to be initialized.
	 */
	private void restoreDatabase() {
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		boolean initialized = prefs.getBoolean(DB_INITIALIZED, false);
		if (!initialized) {
			mBillingService.restoreTransactions();
			Toast.makeText(this, R.string.restoring_transactions,
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Creates a background thread that reads the database and initializes the
	 * set of owned items.
	 */
	private void initializeOwnedItems() {
		new Thread(new Runnable() {
			public void run() {
				doInitializeOwnedItems();
			}
		}).start();
	}

	/**
	 * Reads the set of purchased items from the database in a background thread
	 * and then adds those items to the set of owned items in the main UI
	 * thread.
	 */
	private void doInitializeOwnedItems() {
		Cursor cursor = mPurchaseDatabase.queryAllPurchasedItems();
		if (cursor == null) {
			return;
		}

		final Set<String> ownedItems = new HashSet<String>();
		try {
			int productIdCol = cursor
					.getColumnIndexOrThrow(PurchaseDatabase.PURCHASED_PRODUCT_ID_COL);
			while (cursor.moveToNext()) {
				String productId = cursor.getString(productIdCol);
				ownedItems.add(productId);
			}
		} finally {
			cursor.close();
		}

		// We will add the set of owned items in a new Runnable that runs on
		// the UI thread so that we don't need to synchronize access to
		// mOwnedItems.
		mHandler.post(new Runnable() {
			public void run() {
				mOwnedItems.addAll(ownedItems);
				mCatalogAdapter.setOwnedItems(mOwnedItems);
			}
		});
	}

	/**
	 * Called when a button is pressed.
	 */
	public void onClick(View v) {

		if (Constants.DEBUG) {
			Log.d(TAG, "buying: " + mItemName + " sku: " + mSku);
		}
		if (!mBillingService.requestPurchase(mSku, mPayloadContents)) {
			showDialog(DIALOG_BILLING_NOT_SUPPORTED_ID);
		}
	}

	/**
	 * Called when an item in the spinner is selected.
	 */
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		mItemName = getString(CATALOG[position].nameId);
		mSku = CATALOG[position].sku;
	}

	public void onNothingSelected(AdapterView<?> arg0) {
	}

	/**
	 * An adapter used for displaying a catalog of products. If a product is
	 * managed by Android Market and already purchased, then it will be
	 * "grayed-out" in the list and not selectable.
	 */
	private static class CatalogAdapter extends ArrayAdapter<String> {
		private final CatalogEntry[] mCatalog;
		private Set<String> mOwnedItems = new HashSet<String>();

		public CatalogAdapter(Context context, CatalogEntry[] catalog) {
			super(context, android.R.layout.simple_spinner_item);
			mCatalog = catalog;
			for (CatalogEntry element : catalog) {
				add(context.getString(element.nameId));
			}
			setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		}

		public void setOwnedItems(Set<String> ownedItems) {
			mOwnedItems = ownedItems;
			notifyDataSetChanged();
		}

		@Override
		public boolean areAllItemsEnabled() {
			// Return false to have the adapter call isEnabled()
			return false;
		}

		@Override
		public boolean isEnabled(int position) {
			// If the item at the given list position is not purchasable,
			// then prevent the list item from being selected.
			CatalogEntry entry = mCatalog[position];
			if (entry.managed == Managed.MANAGED
					&& mOwnedItems.contains(entry.sku)) {
				return false;
			}
			return true;
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			// If the item at the given list position is not purchasable, then
			// "gray out" the list item.
			View view = super.getDropDownView(position, convertView, parent);
			view.setEnabled(isEnabled(position));
			return view;
		}
	}
}
