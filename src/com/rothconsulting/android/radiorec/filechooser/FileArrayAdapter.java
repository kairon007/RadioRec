package com.rothconsulting.android.radiorec.filechooser;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rothconsulting.android.common.Utils;
import com.rothconsulting.android.radiorec.R;

public class FileArrayAdapter extends ArrayAdapter<Option> {

	private final String TAG = this.getClass().getName();

	private final Context c;
	private final int id;
	private final List<Option> items;

	public FileArrayAdapter(Context context, int textViewResourceId,
			List<Option> objects) {
		super(context, textViewResourceId, objects);
		c = context;
		id = textViewResourceId;
		items = objects;
	}

	@Override
	public Option getItem(int i) {
		return items.get(i);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) c
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(id, null);
		}
		final Option o = items.get(position);
		if (o != null) {
			ImageView i1 = (ImageView) v.findViewById(R.id.ImageViewFileicon);
			TextView t1 = (TextView) v.findViewById(R.id.TextViewFilename);
			TextView t2 = (TextView) v.findViewById(R.id.TextViewFiletypeSize);

			if (i1 != null)
				i1.setImageResource(o.getIcon());
			if (t1 != null)
				t1.setText(o.getName());
			Utils.log(TAG, "Name=" + o.getName());
			if (t2 != null)
				t2.setText(o.getData());

		}
		return v;
	}
}
