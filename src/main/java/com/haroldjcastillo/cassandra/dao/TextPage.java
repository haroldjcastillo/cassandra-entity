package com.haroldjcastillo.cassandra.dao;

import java.io.Serializable;
import java.util.Arrays;

/**
 * The Class TextPage.
 *
 * @author harold.castillo
 * @since 03-03-2017 04:28:21 PM
 */
public class TextPage extends Page implements Serializable {

	private static final long serialVersionUID = 2682083686482176328L;

	private final String searchText;

	private final String searchBoundText;

	private final String offsetText;
	
	public TextPage(final int limit, final String searchText, final String offsetText, final String idOffset) {
		super(limit, idOffset);
		this.searchText = searchText;
		this.searchBoundText = nextSequence(searchText);
		this.offsetText = offsetText;
	}

	public TextPage(final int limit, final String searchText, final String searchBoundText, final String offsetText, final String idOffSet) {
		super(limit, idOffSet);
		this.searchText = searchText;
		this.searchBoundText = searchBoundText;
		this.offsetText = offsetText;
	}
	
	private static String nextSequence(final String input) {
		if (input != null && input.length() > 0) {
			char[] chars = input.toCharArray();
			int i = chars.length - 1;
			while (i >= 0 && ++chars[i--] == Character.MIN_VALUE)
				if (i == -1 && (chars.length == 0 || chars[0] == Character.MIN_VALUE)) {
					char buf[] = Arrays.copyOf(input.toCharArray(), input.length() + 1);
					buf[buf.length - 1] = Character.MIN_VALUE;
					return new String(buf);
				}
			return new String(chars);
		} else {
			return null;
		}
	}

	public String getSearchText() {
		return searchText;
	}

	public String getSearchBoundText() {
		return searchBoundText;
	}

	public String getOffsetText() {
		return offsetText;
	}

}
