package com.github.haroldjcastillo.cassandra.core;

import org.apache.log4j.Logger;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.LoadBalancingPolicy;

/**
 * The Class Monitor, allows monitor a session.
 *
 * @author harold.castillo
 * @since 03-03-2017 04:26:39 PM
 */
public class Monitor implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(Monitor.class);

	private final Cluster cluster;

	private final Session session;

	public Monitor(Cluster cluster, Session session) {
		this.cluster = cluster;
		this.session = session;
	}

	@Override
	public void run() {
		final LoadBalancingPolicy loadBalancingPolicy = this.cluster.getConfiguration().getPolicies()
				.getLoadBalancingPolicy();
		final PoolingOptions poolingOptions = this.cluster.getConfiguration().getPoolingOptions();
		final Session.State state = this.session.getState();
		
		for (Host host : state.getConnectedHosts()) {
			final HostDistance distance = loadBalancingPolicy.distance(host);
			final int connections = state.getOpenConnections(host);
			final int inFlightQueries = state.getInFlightQueries(host);
			final String stateResult = String.format("%s connections=%d, current load=%d, maxload=%d%n", host,
					connections, inFlightQueries, connections * poolingOptions.getMaxRequestsPerConnection(distance));
			LOGGER.info(stateResult);
		}
	}

}
