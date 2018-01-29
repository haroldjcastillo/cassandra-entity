package com.haroldjcastillo.cassandra.dao;

import java.io.Serializable;

/**
 * The Class TimePage.
 *
 * @author harold.castillo
 * @since 03-03-2017 04:28:24 PM
 */
public class TimePage extends Page implements Serializable {

	private static final long serialVersionUID = -8926665973736770809L;

	private final Long startTime;

	private final Long endTime;

	private final boolean asc;

	public TimePage(final int limit, final long startTime, final  long endTime, final boolean asc) {
		super(limit, null);
		this.startTime = startTime;
		this.endTime = endTime;
		this.asc = asc;
	}

	public Long getStartTime() {
		return startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public boolean isAsc() {
		return asc;
	}

}
