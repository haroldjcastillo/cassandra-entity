CREATE KEYSPACE IF NOT EXISTS test WITH replication = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
CREATE TABLE IF NOT EXISTS test.table_test (id timeuuid, name text, description text, PRIMARY KEY (id, name));
CREATE MATERIALIZED VIEW IF NOT EXISTS test.table_test_by_name AS SELECT * FROM test.table_test WHERE name IS NOT NULL PRIMARY KEY (name, id);