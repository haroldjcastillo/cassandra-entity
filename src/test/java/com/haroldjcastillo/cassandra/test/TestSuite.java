package com.haroldjcastillo.cassandra.test;

import org.cassandraunit.CassandraCQLUnit;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CassandraSessionTest.class,
	SchemaTest.class,
	EntityTest.class
})
public class TestSuite {

	@ClassRule
	public static CassandraCQLUnit cassandraCQLUnit = new CassandraCQLUnit(
			new ClassPathCQLDataSet("schema.cql", "test"), "cassandra-test.yaml");
}
