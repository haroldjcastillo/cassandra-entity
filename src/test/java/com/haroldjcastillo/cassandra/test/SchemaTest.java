package com.haroldjcastillo.cassandra.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.BeforeClass;
import org.junit.Test;

import com.datastax.driver.core.Session;
import com.github.haroldjcastillo.cassandra.common.Configuration;
import com.github.haroldjcastillo.cassandra.core.CassandraSession;

/**
 * The Class SchemaTest, probes the creation of code lines from a specific fila.
 *
 * @author harold.castillo
 * @since 03-14-2017 02:19:54 PM
 */
public class SchemaTest {

	private static Configuration configuration;
	
	private static final String TEST = "test";
	
	private static final String[] keyspaces = { TEST };

	@BeforeClass
	public static void beforeClass() {
		configuration = new Configuration("SchemaTest");
		configuration.setName("SchemaTest");
		configuration.setKeyspaces(keyspaces);
	}

	@Test
	public void createSchema() {
		try(Session session = CassandraSession.getInstance().getConnectionManager(configuration, TEST).getSession()) {
			final InputStream schemaInput = this.getClass().getClassLoader().getResourceAsStream("schema.cql");
			SchemaTest.executeCqlFile(schemaInput, session);
		};
	}

	public static String executeCqlFile(final InputStream is, final Session session) {

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
