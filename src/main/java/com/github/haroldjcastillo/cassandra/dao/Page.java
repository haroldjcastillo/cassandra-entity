/*
 * Copyright 2017 (C) Tigo Honduras
 */
package com.github.haroldjcastillo.cassandra.dao;

import java.util.UUID;

/**
 * The Class Page.
 *
 * @author harold.castillo
 * @since 03-03-2017 04:26:50 PM
 */
public class Page {
	
	protected final int limit;
	
	protected final UUID idOffset;

	public Page(int limit, String idOffset) {
		this.limit = limit;
		this.idOffset = idOffset != null && !idOffset.isEmpty() ? UUID.fromString(idOffset) : null;
	}

	public int getLimit() {
		return limit;
	}

	public UUID getIdOffset() {
		return idOffset;
	}

}
