package com.haroldjcastillo.cassandra.dao;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Transient;
import com.haroldjcastillo.cassandra.common.ConnectionManager;

/**
 * The Class EntityBase.
 *
 * @author harold.castillo
 * @param <E> the entity type
 * @since 03-03-2017 04:26:45 PM
 */
public abstract class EntityBase<E extends EntityBase<?>> {

	/**
	 * The Constant LOGGER defines the {@link Logger} instance for creating
	 * messages type in the server log.
	 */
	@Transient
	private static final Logger LOGGER = Logger.getLogger(EntityBase.class);

	/** Holds connections to a Cassandra cluster, allowing it to be queried. */
	@Transient
	private Session session;

	/** The {@link MappingManager} object instance. */
	@Transient
	private MappingManager mappingManager;

	/** Allows to encapsulate the class instance. */
	@Transient
	private Class<E> mapperClass;

	/**
	 * Instantiates a new entity base.
	 */
	protected EntityBase() {

	}

	/**
	 * Instantiates a new entity base.
	 *
	 * @param session the session
	 * @param mapperClass the mapper class
	 */
	public EntityBase(final ConnectionManager connectionManager, final Class<E> mapperClass) {
		this.mapperClass = mapperClass;
		this.session = connectionManager.getSession();
		this.mappingManager = connectionManager.getMappingManager();
	}

	/**
	 * Method that allows executes a specific cassandra query language.
	 *
	 * @param cql
	 *            the cassandra query language
	 * @param consistencyLevel
	 *            the consistency level
	 * @param params
	 *            the query parameters
	 * @return the list
	 */
	public List<E> execute(final String cql, final ConsistencyLevel consistencyLevel, final Object... params) {

		List<E> list = Collections.emptyList();

		try {
			if (!session.isClosed()) {
				final PreparedStatement statement = session.prepare(cql);
				statement.setConsistencyLevel(consistencyLevel);
				final BoundStatement bind = statement.bind(params);
				final ResultSet resultSet = session.execute(bind);
				final Result<E> result = mappingManager.mapper(mapperClass).map(resultSet);
				if (result != null) {
					list = result.all();
				}
			} else {
				session.init();
			}
		} catch (NoHostAvailableException e) {
			LOGGER.error(e.getMessage());
		}

		return list;
	}

	/**
	 * Method that allows executes a specific cassandra statement.
	 *
	 * @param statement
	 *            the cassandra statement
	 * @param consistencyLevel
	 *            the consistency level
	 * @return the list
	 */
	public List<E> execute(final Statement statement, final ConsistencyLevel consistencyLevel) {

		List<E> list = Collections.emptyList();

		try {
			statement.setConsistencyLevel(consistencyLevel);
			final ResultSet resultSet = session.execute(statement);
			final Result<E> result = mappingManager.mapper(mapperClass).map(resultSet);
			if (result != null) {
				list = result.all();
			}
		} catch (NoHostAvailableException e) {
			LOGGER.error(e.getMessage());
		}

		return list;
	}

	/**
	 * Method that allows executes a specific prepared statement.
	 *
	 * @param statement
	 *            the prepared statement
	 * @param consistencyLevel
	 *            the consistency level
	 * @param params
	 *            the parameters to binding
	 * @return the list of executed query
	 */
	public List<E> execute(final PreparedStatement statement, final ConsistencyLevel consistencyLevel, final Object... params) {

		List<E> list = Collections.emptyList();

		try {
			if (!session.isClosed()) {
				statement.setConsistencyLevel(consistencyLevel);
				final BoundStatement bind = statement.bind(params);
				final ResultSet resultSet = session.execute(bind);
				final Result<E> result = mappingManager.mapper(mapperClass).map(resultSet);
				if (result != null) {
					list = result.all();
				}
			} else {
				session.init();
			}
		} catch (NoHostAvailableException e) {
			LOGGER.error(e.getMessage());
		}

		return list;
	}

	/**
	 *  Method that allows executes a specific entity.
	 *
	 * @param entity the entity
	 * @param consistencyLevel the consistency level
	 * @return the list
	 */
	public List<E> execute(final E entity, final ConsistencyLevel consistencyLevel) {
		final Statement statement = mappingManager.mapper(mapperClass).saveQuery(entity);
		return this.execute(statement, consistencyLevel);
	}

}
