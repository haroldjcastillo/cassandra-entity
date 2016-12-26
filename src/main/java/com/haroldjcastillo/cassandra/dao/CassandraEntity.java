package com.haroldjcastillo.cassandra.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.NoHostAvailableException;

public class CassandraEntity {

	private static final Logger LOGGER = Logger.getLogger(CassandraEntity.class);

	public static ResultSet insert(Session session, final String cql, final Object... params) {

		ResultSet resultSet = null;

		try {

			if(!session.isClosed()) {
				final PreparedStatement statement = session.prepare(cql);
				statement.setConsistencyLevel(ConsistencyLevel.ONE);
				final BoundStatement bind = statement.bind(params);
				resultSet = session.execute(bind);
			} else {
				session.init();
			}

		} catch (NoHostAvailableException e) {
			LOGGER.error(e.getMessage());
		}

		return resultSet;
	}
	
	public static ResultSet insertBatch(final Session session, final String cql, final List<Object[]> params) {

		ResultSet resultSet = null;

		try {
			final PreparedStatement statement = session.prepare(cql);
			statement.setConsistencyLevel(ConsistencyLevel.ONE);
			final BatchStatement batch = new BatchStatement();

			for (Object[] param : params) {
				final BoundStatement bind = statement.bind(param);
				batch.add(bind);
			}

			resultSet = session.execute(batch);
		} catch (NoHostAvailableException e) {
			LOGGER.error(e.getMessage());
		}

		return resultSet;
	}

}