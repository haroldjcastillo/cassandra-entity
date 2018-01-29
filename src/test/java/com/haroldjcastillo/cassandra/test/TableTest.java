package com.haroldjcastillo.cassandra.test;

import java.io.IOException;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.github.haroldjcastillo.cassandra.common.ConnectionManager;
import com.github.haroldjcastillo.cassandra.dao.EntityBase;

@Table(keyspace = "test", name = "table_test")
public class TableTest extends EntityBase<TableTest> {

	@PartitionKey(value = 0)
	@Column(name = "id")
	private UUID id;

	@PartitionKey(value = 1)
	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;
	
	public TableTest() {
		super();
	}

	public TableTest(final ConnectionManager session) {
		super(session, TableTest.class);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		String json = null;

		try {
			final ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(this);
		} catch (IOException e) {
			json = super.toString();
		}

		return json;
	}

}
