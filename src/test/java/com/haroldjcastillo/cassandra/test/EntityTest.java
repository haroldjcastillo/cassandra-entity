package com.haroldjcastillo.cassandra.test;

import static com.datastax.driver.core.querybuilder.QueryBuilder.delete;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.utils.UUIDs;
import com.github.haroldjcastillo.cassandra.common.Configuration;
import com.github.haroldjcastillo.cassandra.common.ConnectionManager;
import com.github.haroldjcastillo.cassandra.core.CassandraSession;
import com.github.haroldjcastillo.cassandra.dao.EntityBase;

/**
 * The Class EntityTest, probes the functionalities when we need to create a
 * entity and delegate the work to {@link EntityBase} instance.
 *
 * @author harold.castillo
 * @since 03-14-2017 11:15:43 AM
 */
public class EntityTest {

	private static final UUID TABLETEST_ID = UUIDs.timeBased();

	private static final String TABLETEST_NAME = "Name";

	private static final String TABLE = "table_test";

	private static Configuration configuration;

	private static final String TEST = "test";

	private static final String[] keyspaces = { TEST };

	@BeforeClass
	public static void beforeClass() {
		configuration = new Configuration("SchemaTest");
		configuration.setName("EntityTest");
		configuration.setKeyspaces(keyspaces);
	}

	@Test
	public void saveEntityTest() {
		final ConnectionManager connectionManager = CassandraSession.getInstance().getConnectionManager(configuration, TEST);
		final TableTest tableTest = new TableTest(connectionManager);
		tableTest.setId(TABLETEST_ID);
		tableTest.setName(TABLETEST_NAME);
		tableTest.setDescription("Description_" + Math.random());
		tableTest.execute(tableTest, ConsistencyLevel.ONE);
	}

	@Test
	public void selectQueryTest() {
		final ConnectionManager connectionManager = CassandraSession.getInstance().getConnectionManager(configuration, TEST);
		final Select.Where select = select().from(TABLE).where(eq("id", TABLETEST_ID));
		final TableTest tableTestEnity = new TableTest(connectionManager);
		final List<TableTest> tableTestList = tableTestEnity.execute(select, ConsistencyLevel.ONE);
		assertTrue(!tableTestList.isEmpty());
	}

	@Test
	public void deleteQueryTest() {
		final ConnectionManager connectionManager = CassandraSession.getInstance().getConnectionManager(configuration, TEST);
		final Delete.Where delete = delete().all().from(TABLE).where(eq("id", TABLETEST_ID));
		final TableTest tableTestEnity = new TableTest(connectionManager);
		tableTestEnity.execute(delete, ConsistencyLevel.ONE);
	}

}