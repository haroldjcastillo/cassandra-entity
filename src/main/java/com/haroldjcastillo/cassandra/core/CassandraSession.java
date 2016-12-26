package com.haroldjcastillo.cassandra.core;

import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.policies.ConstantReconnectionPolicy;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.haroldjcastillo.cassandra.jmx.Configuration;

public final class CassandraSession {

	/**
	 * This attribute will store an instance of log4j for CassandraSession
	 * class.
	 */
	private static final Logger LOGGER = Logger.getLogger(CassandraSession.class);

	private static CassandraSession cassandraSession = new CassandraSession();

	private static Map<String, Configuration> configurationMap;
	
	private CassandraSession() {
		configurationMap = new Hashtable<>();
	}

	public static CassandraSession getInstance() {
		return cassandraSession;
	}

	private Cluster createCluster(final Configuration configuration) {

		// Configuring the connection pool
		final PoolingOptions poolingOptions = new PoolingOptions();		
		poolingOptions.setConnectionsPerHost(HostDistance.LOCAL, configuration.getConnectionsPerHostLocalMin(), configuration.getConnectionsPerHostLocalMax());
		poolingOptions.setConnectionsPerHost(HostDistance.REMOTE, configuration.getConnectionsPerHostRemoteMin(), configuration.getConnectionsPerHostRemoteMax());
		poolingOptions.setMaxRequestsPerConnection(HostDistance.LOCAL, configuration.getMaxRequestsPerConnectionLocal());
		poolingOptions.setMaxRequestsPerConnection(HostDistance.REMOTE, configuration.getMaxRequestsPerConnectionLocal());

		// Datacenter aware round robin policy
		final DCAwareRoundRobinPolicy dcAwareRoundRobinPolicy =  DCAwareRoundRobinPolicy.builder()
          .withLocalDc(configuration.getPolicyLocalDc())
          .withUsedHostsPerRemoteDc(configuration.getPolicyUsedHostsPerRemoteDc())
          .allowRemoteDCsForLocalConsistencyLevel()
          .build();

		// Configuring the cluster
		final Builder clusterBuilder = Cluster.builder();
		clusterBuilder.addContactPoints(configuration.getHostnames());
		clusterBuilder.withPort(configuration.getPort());
		clusterBuilder.withCredentials(configuration.getUsername(), configuration.getPassword());
		clusterBuilder.withReconnectionPolicy(new ConstantReconnectionPolicy(100L));
		clusterBuilder.withLoadBalancingPolicy(dcAwareRoundRobinPolicy);

		final Cluster cluster = clusterBuilder.build();
		this.printMetadata(cluster);
		
		return cluster;
	}

	private void printMetadata(final Cluster cluster) {

		final Metadata metadata = cluster.getMetadata();
		final StringBuilder metadataStr = new StringBuilder();
		metadataStr.append(String.format("\nCluster\t\t: [%s]\n", metadata.getClusterName()));
		for (Host host : metadata.getAllHosts()) {
			metadataStr.append(String.format("Datatacenter\t: [%s] Host: [%s] Rack: [%s]\n", host.getDatacenter(),
					host.getAddress(), host.getRack()));
		}

		final ProtocolVersion protocolVersion = cluster.getConfiguration().getProtocolOptions().getProtocolVersion();
		metadataStr.append(String.format("Protocol version: [%s]\n", protocolVersion));

		LOGGER.info(metadataStr);
	}

	public Cluster getCluster(final Configuration configuration) {
		
		if(!configurationMap.containsKey(configuration.getName())) {
			configurationMap.put(configuration.getName(), configuration);
		}

		return createCluster(configurationMap.get(configuration.getName()));
	}

}
