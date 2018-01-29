package com.haroldjcastillo.cassandra.core;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.exceptions.CodecNotFoundException;
import com.datastax.driver.core.policies.ConstantReconnectionPolicy;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.haroldjcastillo.cassandra.codec.JsonCodec;
import com.haroldjcastillo.cassandra.common.Configuration;
import com.haroldjcastillo.cassandra.common.ConnectionManager;
import com.haroldjcastillo.cassandra.jmx.ConfigurationBean;

/**
 * The Class CassandraSession.
 *
 * @author harold.castillo
 * @since 03-03-2017 04:26:34 PM
 */
public final class CassandraSession {

	/**
	 * This attribute will store an instance of log4j for CassandraSession
	 * class.
	 */
	private static final Logger LOGGER = Logger.getLogger(CassandraSession.class);

	private static CassandraSession cassandraSession = new CassandraSession();

	private static Map<String, Configuration> configurationMap;

	private static Map<String, Cluster> clusterMap;
	
	private static Map<String, ConnectionManager> sessionMap;

	/**
	 * Instantiates a new cassandra session.
	 */
	private CassandraSession() {
		configurationMap = new Hashtable<>();
		clusterMap = new Hashtable<>();
		sessionMap = new Hashtable<>();
	}

	/**
	 * Gets the single instance of CassandraSession.
	 *
	 * @return single instance of CassandraSession
	 */
	public static CassandraSession getInstance() {
		return cassandraSession;
	}

	/**
	 * Gets the cluster instance from a specific configuration, this is a
	 * convenience method for new Cluster.Builder().
	 *
	 * @param configuration
	 *            the configuration
	 * @return the cluster
	 */
	private Cluster getCluster(final Configuration configuration) {

		LOGGER.info("Creating cluster " + configuration.getUsername());

		// Configuring the connection pool
		final PoolingOptions poolingOptions = new PoolingOptions();
		poolingOptions.setConnectionsPerHost(HostDistance.LOCAL, configuration.getConnectionsPerHostLocalMin(),
				configuration.getConnectionsPerHostLocalMax());
		poolingOptions.setConnectionsPerHost(HostDistance.REMOTE, configuration.getConnectionsPerHostRemoteMin(),
				configuration.getConnectionsPerHostRemoteMax());
		poolingOptions.setMaxRequestsPerConnection(HostDistance.LOCAL,
				configuration.getMaxRequestsPerConnectionLocal());
		poolingOptions.setMaxRequestsPerConnection(HostDistance.REMOTE,
				configuration.getMaxRequestsPerConnectionRemote());

		// Datacenter aware round robin policy
		final DCAwareRoundRobinPolicy dcAwareRoundRobinPolicy = DCAwareRoundRobinPolicy.builder()
				.withLocalDc(configuration.getPolicyLocalDc())
				.withUsedHostsPerRemoteDc(configuration.getPolicyUsedHostsPerRemoteDc())
				.allowRemoteDCsForLocalConsistencyLevel().build();

		// Configuring the cluster
		final Builder clusterBuilder = Cluster.builder();
		clusterBuilder.addContactPoints(configuration.getHostnames());
		clusterBuilder.withPort(configuration.getPort());
		clusterBuilder.withCredentials(configuration.getUsername(), configuration.getPassword());
		clusterBuilder.withReconnectionPolicy(new ConstantReconnectionPolicy(100L));
		clusterBuilder.withLoadBalancingPolicy(dcAwareRoundRobinPolicy);

		final Cluster cluster = clusterBuilder.build();
		final CodecRegistry registry = cluster.getConfiguration().getCodecRegistry();
		registerCodecIfNotFound(registry, new JsonCodec());

		// Register metadata information
		this.registerMBean(configuration, cluster);

		return cluster;
	}

	/**
	 * A registry for TypeCodecs. When the driver needs to serialize or
	 * deserialize a Java type to/from CQL, it will lookup in the registry for a
	 * suitable codec. The registry is initialized with default codecs that
	 * handle basic conversions (e.g. CQL text to java.lang.String), and users
	 * can add their own. Complex codecs can also be generated on-the-fly from
	 * simpler ones (more details below).
	 * 
	 * @see {@docRoot CodecRegistry}
	 *
	 * @param registry
	 *            the {@link CodecRegistry} instance
	 * @param codec
	 *            the codec instance type
	 */
	private void registerCodecIfNotFound(CodecRegistry registry, TypeCodec<?> codec) {
		try {
			registry.codecFor(codec.getCqlType(), codec.getJavaType());
		} catch (CodecNotFoundException e) {
			registry.register(codec);
		}
	}

	/**
	 * Method that allows register the Mbean information about a cluster
	 * configuration.
	 *
	 * @param configuration
	 *            the configuration
	 * @param cluster
	 *            the cluster
	 */
	private void registerMBean(final Configuration configuration, final Cluster cluster) {

		final Metadata metadata = cluster.getMetadata();
		final StringBuilder metadataStr = new StringBuilder();

		metadataStr.append(String.format("Cluster: %s\n", metadata.getClusterName()));
		for (Host host : metadata.getAllHosts()) {
			metadataStr.append(String.format("Datatacenter: [%s] Host: [%s] Rack: [%s]\n", host.getDatacenter(),
					host.getAddress(), host.getRack()));
		}

		final ProtocolVersion protocolVersion = cluster.getConfiguration().getProtocolOptions().getProtocolVersion();
		metadataStr.append(String.format("Protocol version: [%s]", protocolVersion));

		final ConfigurationBean configurationBean = new ConfigurationBean(configuration);
		configurationBean.setMetadata(metadataStr.toString());
	}

	/**
	 * Gets the session from a specific configuration and keyspace.
	 *
	 * @param configuration the {@link Configuration} instance
	 * @param keyspace the keyspace name
	 * @return the session for a specific keyspace.
	 * 
	 * 	<pre>
	 * 		null: when there isn't any keyspace in the {@link Configuration} instance.
	 * 	</pre>
	 */
	public ConnectionManager getConnectionManager(final Configuration configuration, final String keyspace) {

		final StringBuilder keyName = new StringBuilder();
		keyName.append(configuration.getName());
		keyName.append(File.pathSeparatorChar);
		keyName.append(keyspace);

		if (!configurationMap.containsKey(configuration.getName())) {
			configurationMap.put(configuration.getName(), configuration);
		}
		
		if (!clusterMap.containsKey(configuration.getName()) || clusterMap.get(configuration.getName()).isClosed()) {
			final Cluster cluster = getCluster(configurationMap.get(configuration.getName()));
			clusterMap.put(configuration.getName(), cluster);
		}

		if (!sessionMap.containsKey(keyName.toString()) || sessionMap.get(keyName.toString()).getSession().isClosed()) {

			for (int i = 0; i < configuration.getKeyspaces().length; i++) {
				// If other session exist but there is a new session in the
				// keyspaces array or is closed the current, validates for not create
				// another of the same type again.
				if (!sessionMap.containsKey(keyName.toString())
						|| sessionMap.get(keyName.toString()).getSession().isClosed()) {
					final ConnectionManager connectionManager = new ConnectionManager(
							clusterMap.get(configuration.getName()).connect(configuration.getKeyspaces()[i]));
					sessionMap.put(keyName.toString(), connectionManager);
				}
			}
		}

		return sessionMap.get(keyName.toString());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {

		// Finalize the sessions that has been opened in the JVM.
		sessionMap.forEach((name, cluster) -> {
			if (!cluster.getSession().isClosed()) {
				cluster.getSession().close();
			}
		});

		super.finalize();
	}

}
