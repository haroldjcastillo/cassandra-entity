# cassandra-entity
* Allows to reduce code and re-create POJO's and Entities, you need to create one class.
* Reduce the creation of DTO and Entities of way independet.

```
mvn clean install
```

### 1. Create a cassandra session
```
final CassandraSession cassandraSession = CassandraSession.getInstance();
```

### 2. Create configuration
```
private static Configuration configuration = new Configuration("ConfigurationName");
```

### 3. Get session
```
final Session session = cassandraSession.getCluster(configuration).connect();
```

## Done! Execute a insert
```
final TableTest tableTest = new TableTest(session);
tableTest.setId(TABLETEST_ID);
tableTest.setName(TABLETEST_NAME);
tableTest.setDescription("Description_" + Math.random());
tableTest.save(tableTest, ConsistencyLevel.ONE);
```
