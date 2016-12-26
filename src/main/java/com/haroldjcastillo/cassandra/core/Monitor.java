package com.haroldjcastillo.cassandra.core;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.LoadBalancingPolicy;

public class Monitor implements Runnable {

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

		Session.State state = this.session.getState();
		for (Host host : state.getConnectedHosts()) {
			HostDistance distance = loadBalancingPolicy.distance(host);
			int connections = state.getOpenConnections(host);
			int inFlightQueries = state.getInFlightQueries(host);
			System.out.printf("%s connections=%d, current load=%d, maxload=%d%n", host, connections, inFlightQueries,
					connections * poolingOptions.getMaxRequestsPerConnection(distance));
		}
	}

}
