package com.haroldjcastillo.cassandra.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.BeforeClass;
import org.junit.Test;

import com.datastax.driver.core.Session;
import com.haroldjcastillo.cassandra.core.CassandraSession;
import com.haroldjcastillo.cassandra.jmx.Configuration;

public class SchemaTest {

	private static Configuration configuration;

	@BeforeClass
	public static void beforeClass() {
		configuration = new Configuration("SchemaTest");
	}

	@Test
	public void createSchema() {
		final Session session = CassandraSession.getInstance().getCluster(configuration).connect();
		final InputStream schemaInput = this.getClass().getClassLoader().getResourceAsStream("schema.cql");
		SchemaTest.executeCqlFile(schemaInput, session);
	}

	private static String executeCqlFile(final InputStream is, final Session session) {

		final StringBuilder sb = new StringBuilder();
		BufferedReader br = null;

		try {

			br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				session.execute(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}

}
