package com.haroldjcastillo.cassandra.test;

import static com.datastax.driver.core.querybuilder.QueryBuilder.delete;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.utils.UUIDs;
import com.haroldjcastillo.cassandra.core.CassandraSession;
import com.haroldjcastillo.cassandra.jmx.Configuration;

public class EntityTest {

	private static final UUID TABLETEST_ID = UUIDs.timeBased();

	private static final String TABLETEST_NAME = "Name";

	private static final String TABLE = "table_test";

	private static final String TEST = "test";
	
	private static Cluster cluster;
	
	private static Configuration configuration;
	
	@BeforeClass
	public static void beforeClass() {
		configuration = new Configuration("EntityTest");
		cluster = CassandraSession.getInstance().getCluster(configuration);
	}

	@Before
	public void saveEntityTest() {
		final Session session = cluster.connect(TEST);
		final TableTest tableTest = new TableTest(session);
		tableTest.setId(TABLETEST_ID);
		tableTest.setName(TABLETEST_NAME);
		tableTest.setDescription("Description_" + Math.random());
		tableTest.save(tableTest, ConsistencyLevel.ONE);
	}

	@Test
	public void selectQueryTest() {
		final Session session = cluster.connect(TEST);
		final Select.Where select = select().from(TABLE).where(eq("id", TABLETEST_ID));
		final TableTest tableTestEnity = new TableTest(session);
		final List<TableTest> tableTestList = tableTestEnity.find(select, ConsistencyLevel.ONE);
		assertTrue(!tableTestList.isEmpty());
	}

	@Test
	public void deleteQueryTest() {
		final Session session = cluster.connect(TEST);
		final Delete.Where delete = delete().all().from(TABLE).where(eq("id", TABLETEST_ID));
		final TableTest tableTestEnity = new TableTest(session);
		tableTestEnity.delete(delete, ConsistencyLevel.ONE);
	}

}