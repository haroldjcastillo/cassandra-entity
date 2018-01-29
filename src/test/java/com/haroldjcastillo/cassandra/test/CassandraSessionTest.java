package com.haroldjcastillo.cassandra.test;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.datastax.driver.core.Session;
import com.github.haroldjcastillo.cassandra.common.Configuration;
import com.github.haroldjcastillo.cassandra.core.CassandraSession;

public class CassandraSessionTest {

	final CassandraSession cassandraSession = CassandraSession.getInstance();
	
	private static Configuration configuration;
	
	private static final String TEST = "test";
	
	private static final String[] keyspaces = { TEST };

	@BeforeClass
	public static void beforeClass() {
		configuration = new Configuration("SchemaTest");
		configuration.setName("CassandraSessionTest");
		configuration.setKeyspaces(keyspaces);
	}

	@Test
	public void session() {
		try(Session session = cassandraSession.getConnectionManager(configuration, "test").getSession()){
			assertNotNull(session);
		}
	}

}