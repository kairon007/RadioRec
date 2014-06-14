package com.rothconsulting.android.radiorec.filechooser;

public class Option implements Comparable<Option> {
	private final String name;
	private final String data;
	private final String path;
	private final int icon;

	public Option(int i, String n, String d, String p) {
		name = n;
		data = d;
		path = p;
		icon = i;
	}

	public String getName() {
		return name;
	}

	public String getData() {
		return data;
	}

	public String getPath() {
		return path;
	}

	public int getIcon() {
		return icon;
	}

	@Override
	public int compareTo(Option o) {
		if (this.name != null)
			return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
		else
			throw new IllegalArgumentException();
	}
}
