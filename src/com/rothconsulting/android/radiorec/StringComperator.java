package com.rothconsulting.android.radiorec;

import java.util.Comparator;

public class StringComperator implements Comparator<String> {

	@Override
	public int compare(String s1, String s2) {
		int i = prepareForCompare(s1).compareTo(prepareForCompare(s2));
		return (0 != i) ? i : s1.compareTo(s2);
	}

	private String prepareForCompare(Object o) {
		return ((String) o).toLowerCase().replace('ä', 'a').replace('ö', 'o')
				.replace('ü', 'u').replace('ß', 's').replace('é', 'e')
				.replace('è', 'e').replace('à', 'a');
	}

}
