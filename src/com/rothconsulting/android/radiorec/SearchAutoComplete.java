package com.rothconsulting.android.radiorec;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class SearchAutoComplete extends AutoCompleteTextView {

	// http://techdroid.kbeanie.com/2010/04/custom-autocomplete-for-android.html

	Context context;

	public SearchAutoComplete(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		this.setThreshold(0);
	}

	public SearchAutoComplete(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.setThreshold(0);
	}

	public SearchAutoComplete(final Context context) {
		super(context);
		this.context = context;
		this.setThreshold(0);
	}

	/**
	 * This method filters out the existing text till the separator and launched
	 * the filtering process again
	 */
	@Override
	protected void performFiltering(final CharSequence text, final int keyCode) {
		if (!TextUtils.isEmpty("" + text)) {
			super.performFiltering(text, keyCode);
		}
	}

	/**
	 * After a selection, capture the new value and append to the existing text
	 */
	@Override
	protected void replaceText(final CharSequence text) {
		super.replaceText(text);
	}
}