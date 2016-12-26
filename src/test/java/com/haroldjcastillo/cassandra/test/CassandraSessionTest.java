package com.haroldjcastillo.cassandra.test;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.datastax.driver.core.Session;
import com.haroldjcastillo.cassandra.core.CassandraSession;
import com.haroldjcastillo.cassandra.jmx.Configuration;

public class CassandraSessionTest {

	final CassandraSession cassandraSession = CassandraSession.getInstance();
	
	private static Configuration configuration;
	
	@BeforeClass
	public static void beforeClass() {
		configuration = new Configuration("CassandraSessionTest");
	}

	@Test
	public void session() {
		final Session session = cassandraSession.getCluster(configuration).connect();
		assertNotNull(session);
	}

}
