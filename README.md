# cassandra-entity
- Builded with ♥ in Java 1.8.
- Allows to reduce code and re-create POJO's and Entities, you need to create one class.
- Reduce the creation of DTO and Entities of independent way.
- Efficient management of connections and sessions.
- Compliance with cassandra-driver mapper.

```
mvn clean install
```

### 1. Create a cassandra session
```
final CassandraSession cassandraSession = CassandraSession.getInstance();
```

### 2. Create configuration

Create multiple configurations for each keyspace

```
private static final String[] keyspaces = { 'test' };

final Configuration configuration = new Configuration("SchemaTest");
configuration.setName("EntityTest");
configuration.setKeyspaces(keyspaces);
```

### 3. Create a connection

This connection it's the same for the keyspace `test` in all the declarations like that.

```
final ConnectionManager connectionManager = CassandraSession.getInstance().getConnectionManager(configuration, 'test');
```

## Done! Execute a insert

```
final TableTest tableTest = new TableTest(connectionManager);
tableTest.setId(UUIDs.timeBased());
tableTest.setName("Some name");
tableTest.setDescription("Some description");
tableTest.execute(tableTest, ConsistencyLevel.ONE);
```
