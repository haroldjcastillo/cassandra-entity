package com.haroldjcastillo.cassandra.dao;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.datastax.driver.core.BatchStatement;
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

public abstract class EntityBase<E extends EntityBase<?>> {

	@Transient
	private static final Logger LOGGER = Logger.getLogger(EntityBase.class);

	@Transient
	private Session session;

	@Transient
	private MappingManager mappingManager;

	@Transient
	private Class<E> mapperClass;

	protected EntityBase() {

	}

	public EntityBase(final Session session, final Class<E> mapperClass) {
		this.mapperClass = mapperClass;
		this.session = session;
		this.mappingManager = new MappingManager(session);
	}

	protected List<E> execute(final String cql, final ConsistencyLevel consistencyLevel, final Object... params) {

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
	
	public ResultSet insertBatch(final String cql, final ConsistencyLevel consistencyLevel, final List<Object[]> params) {

		ResultSet resultSet = null;

		try {
			final PreparedStatement statement = session.prepare(cql);
			statement.setConsistencyLevel(consistencyLevel);
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

	protected List<E> execute(final Statement statement, final ConsistencyLevel consistencyLevel) {

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

	public void save(final E entity, final ConsistencyLevel consistencyLevel) {
		final Statement statement = mappingManager.mapper(mapperClass).saveQuery(entity);
		this.execute(statement, consistencyLevel);
	}

	public List<E> find(final Statement statement, final ConsistencyLevel consistencyLevel) {
		return this.execute(statement, consistencyLevel);
	}
	
	public List<E> delete(final Statement statement, final ConsistencyLevel consistencyLevel) {
		return this.execute(statement, consistencyLevel);
	}

}
