package com.haroldjcastillo.cassandra.common;

import java.io.Closeable;
import java.io.IOException;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;

/**
 * The Class ConnectionManager, administers the session his {@link MappingManager}
 * instance.
 *
 * @author harold.castillo
 * @since 03-15-2017 02:13:09 PM
 */
public class ConnectionManager implements Closeable {
	
	/** The {@link Session} object instance. */
	private Session session;
	
	/** The {@link MappingManager} object instance. */
	private MappingManager mappingManager;

	/**
	 * Instantiates a new connection manager.
	 *
	 * @param session the session
	 */
	public ConnectionManager(final Session session) {
		super();
		this.session = session;
		this.mappingManager = new MappingManager(session);
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * Gets the mapping manager.
	 *
	 * @return the mapping manager
	 */
	public MappingManager getMappingManager() {
		return mappingManager;
	}

	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		if(!this.session.isClosed()) {
			this.session.close();
		}
	}
	
}
