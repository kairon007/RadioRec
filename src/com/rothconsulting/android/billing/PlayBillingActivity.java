/* Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rothconsulting.android.billing;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.rothconsulting.android.billing.util.IabHelper;
import com.rothconsulting.android.billing.util.IabResult;
import com.rothconsulting.android.billing.util.Inventory;
import com.rothconsulting.android.billing.util.Purchase;
import com.rothconsulting.android.radiorec.R;

public class PlayBillingActivity extends Activity implements OnItemSelectedListener {

	private final Context context = this;
	private static final String TAG = "PlayBillingActivity";
	public static boolean isDonator = false;
	boolean mBillingServiceReady = false;
	Spinner mSelectItemSpinner;
	IabHelper mHelper = null;

	private static class CatalogEntry {
		public String sku;
		public int nameId;

		public CatalogEntry(String sku, int nameId) {
			this.sku = sku;
			this.nameId = nameId;
		}
	}

	/** An array of product list entries for the products that can be purchased. */
	private static final CatalogEntry[] CATALOG = new CatalogEntry[] { //
	new CatalogEntry(BillingHelper.RR_DONATE_BASIC, R.string.donate_basic),//
			new CatalogEntry(BillingHelper.RR_DONATE_BASIC_PLUS, R.string.donate_basic_plus),//
			new CatalogEntry(BillingHelper.RR_DONATE_BRONZE, R.string.donate_bronze),//
			new CatalogEntry(BillingHelper.RR_DONATE_SILVER, R.string.donate_silver),//
			new CatalogEntry(BillingHelper.RR_DONATE_GOLD, R.string.donate_gold) };

	private String mItemName;
	private String mSku;
	private CatalogAdapter mCatalogAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.donate_play_billing);

		// Button Donate
		Button btnDonate = (Button) findViewById(R.id.buttonDonate);
		btnDonate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBillingServiceReady) {
					Toast.makeText(context, "Starte Spende !!!", Toast.LENGTH_LONG).show();
					mHelper.launchPurchaseFlow(PlayBillingActivity.this, mSku, 10001, mPurchaseFinishedListener, mSku);
				} else {
					Toast.makeText(context, "Play Billing Service NOT ready !!!", Toast.LENGTH_LONG).show();
					return;
				}
			}
		});

		// Button Back
		Button btnBack = (Button) findViewById(R.id.buttonZurueck);
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// Fill Spinner
		mSelectItemSpinner = (Spinner) findViewById(R.id.item_choices);
		mCatalogAdapter = new CatalogAdapter(this, CATALOG);
		mSelectItemSpinner.setAdapter(mCatalogAdapter);
		mSelectItemSpinner.setSelection(1);
		mSelectItemSpinner.setOnItemSelectedListener(this);

		// Check Play Store
		initialiseBilling();

		// Button Test Billing -> isDonator
		Button btnDonator = (Button) findViewById(R.id.buttonDonator);
		btnDonator.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BillingHelper bh = new BillingHelper();
				boolean isDonator = bh.isDonator();
				Toast.makeText(context, "isDonator = " + isDonator, Toast.LENGTH_LONG).show();
			}
		});

	}

	private void initialiseBilling() {
		if (mHelper != null) {
			return;
		}

		// Create the helper, passing it our context and the public key to verify signatures with
		mHelper = new IabHelper(this, BillingHelper.base64_1 + BillingHelper.base64_2 + BillingHelper.base64_4 + BillingHelper.base64_3);

		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			@Override
			public void onIabSetupFinished(IabResult result) {
				// Have we been disposed of in the meantime? If so, quit.
				if (mHelper == null) {
					return;
				}

				if (!result.isSuccess()) {
					Log.d(TAG, "In-app Billing setup failed: " + result);
				} else {
					Log.d(TAG, "In-app Billing is set up OK");
				}

				// IAB is fully set up.
				mBillingServiceReady = true;
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (mHelper != null && !mHelper.handleActivityResult(requestCode, resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		@Override
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			if (result.isFailure()) {
				// Error
				Toast.makeText(context, "Purchase NOT successful!", Toast.LENGTH_LONG).show();
				return;
			} else if (purchase.getSku().equals(mSku)) {
				consumeItem();
			}

		}
	};

	public void consumeItem() {
		mHelper.queryInventoryAsync(mReceivedInventoryListener);
	}

	IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		@Override
		public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

			if (result.isFailure()) {
				// Error
				Toast.makeText(context, "QueryInventory Failure!", Toast.LENGTH_LONG).show();
			} else {
				mHelper.consumeAsync(inventory.getPurchase(mSku), mConsumeFinishedListener);
				PlayBillingActivity.isDonator = true;
			}
		}
	};

	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
		@Override
		public void onConsumeFinished(Purchase purchase, IabResult result) {

			if (result.isSuccess()) {
				Toast.makeText(context, "Thank you !!", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(context, "ConsumeFinished NOT successful!", Toast.LENGTH_LONG).show();
				return;
			}
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mHelper != null)
			mHelper.dispose();
		mHelper = null;
	}

	/**
	 * Called when an item in the spinner is selected.
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		mItemName = getString(CATALOG[position].nameId);
		mSku = CATALOG[position].sku;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// Do nothing

	}

	/**
	 * An adapter used for displaying a catalog of products. If a product is managed by Android Market and already purchased, then it will be "grayed-out" in
	 * the list and not selectable.
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
			if (mOwnedItems.contains(entry.sku)) {
				return false;
			}
			return true;
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			// If the item at the given list position is not purchasable, then
			// "gray out" the list item.
			View view = super.getDropDownView(position, convertView, parent);
			view.setEnabled(isEnabled(position));
			return view;
		}
	}

}
