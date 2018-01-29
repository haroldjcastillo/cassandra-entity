package com.haroldjcastillo.cassandra.common;

import java.math.BigInteger;

/**
 * The Class Configuration.
 *
 * @author harold.castillo
 * @since 03-03-2017 04:28:51 PM
 */
public class Configuration implements ConfigurationDefult {
	
	private String name;

	private long executionBlockcount;

	private long corePoolSize;

	private long maximumPoolSize;

	private long keepAliveTime;

	private long threadSleep;

	private int connectionsPerHostLocalMin;

	private int connectionsPerHostLocalMax;

	private int connectionsPerHostRemoteMin;

	private int connectionsPerHostRemoteMax;

	private int maxRequestsPerConnectionLocal;

	private int maxRequestsPerConnectionRemote;

	private String[] hostnames;

	private String username;

	private String password;

	private int port;

	private String localDc;

	private int usedHostsPerRemoteDc;

	private String[] keyspaces;
	
	public Configuration(final String name) {
		this.name = name;
		executionBlockcount = EXECUTIONBLOCKCOUNT;
		corePoolSize = COREPOOLSIZE;
		maximumPoolSize = MAXIMUMPOOLSIZE;
		keepAliveTime = KEEPALIVETIME;
		threadSleep = THREADSLEEP;
		connectionsPerHostLocalMin = CONNECTIONSPERHOSTLOCALMIN;
		connectionsPerHostLocalMax = CONNECTIONSPERHOSTLOCALMAX;
		connectionsPerHostRemoteMin = CONNECTIONSPERHOSTREMOTEMIN;
		connectionsPerHostRemoteMax = CONNECTIONSPERHOSTREMOTEMAX;
		maxRequestsPerConnectionLocal = MAXREQUESTSPERCONNECTIONLOCAL;
		maxRequestsPerConnectionRemote = MAXREQUESTSPERCONNECTIONREMOTE;
		hostnames = HOSTNAMES;
		keyspaces = new String[BigInteger.ZERO.intValue()];
		username = USERNAME;
		password = PASSWORD;
		port = PORT;
		localDc = LOCALDC;
		usedHostsPerRemoteDc = USEDHOSTSPERREMOTEDC;
	}

	public Configuration(final String name, final long executionBlockcount, final int corePoolSize,
			final int maximumPoolSize, final int keepAliveTime, final int threadSleep,
			final int connectionsPerHostLocalMin, final int connectionsPerHostLocalMax,
			final int connectionsPerHostRemoteMin, final int connectionsPerHostRemoteMax,
			final int maxRequestsPerConnectionLocal, final int maxRequestsPerConnectionRemote, final String[] hostnames,
			final String username, final String password, final int port, final String localDc,
			final int usedHostsPerRemoteDc, final String[] keyspaces) {
		this.name = name;
		this.executionBlockcount = executionBlockcount;
		this.corePoolSize = corePoolSize;
		this.maximumPoolSize = maximumPoolSize;
		this.keepAliveTime = keepAliveTime;
		this.threadSleep = threadSleep;
		this.connectionsPerHostLocalMin = connectionsPerHostLocalMin;
		this.connectionsPerHostLocalMax = connectionsPerHostRemoteMax;
		this.connectionsPerHostRemoteMin = connectionsPerHostRemoteMin;
		this.connectionsPerHostRemoteMax = connectionsPerHostRemoteMax;
		this.maxRequestsPerConnectionLocal = maxRequestsPerConnectionLocal;
		this.maxRequestsPerConnectionRemote = maxRequestsPerConnectionRemote;
		this.hostnames = hostnames;
		this.username = username;
		this.password = password;
		this.port = port;
		this.localDc = localDc;
		this.usedHostsPerRemoteDc = usedHostsPerRemoteDc;
		this.keyspaces = keyspaces;
	}
	
	public String getName() {
		return name;
	}

	public long getExecutionBlockcount() {
		return executionBlockcount;
	}

	public long getCorePoolSize() {
		return corePoolSize;
	}

	public long getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public long getKeepAliveTime() {
		return keepAliveTime;
	}

	public long getThreadSleep() {
		return threadSleep;
	}

	public int getConnectionsPerHostLocalMin() {
		return connectionsPerHostLocalMin;
	}

	public int getConnectionsPerHostLocalMax() {
		return connectionsPerHostLocalMax;
	}

	public int getConnectionsPerHostRemoteMin() {
		return connectionsPerHostRemoteMin;
	}

	public int getConnectionsPerHostRemoteMax() {
		return connectionsPerHostRemoteMax;
	}

	public int getMaxRequestsPerConnectionLocal() {
		return maxRequestsPerConnectionLocal;
	}

	public int getMaxRequestsPerConnectionRemote() {
		return maxRequestsPerConnectionRemote;
	}

	public String[] getHostnames() {
		return hostnames;
	}

	public String[] getKeyspaces() {
		return keyspaces;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	public String getPolicyLocalDc() {
		return localDc;
	}

	public int getPolicyUsedHostsPerRemoteDc() {
		return usedHostsPerRemoteDc;
	}
	
	public void setName(final String name) {
		this.name = name;
	}

	public void setExecutionBlockcount(final long value) {
		this.executionBlockcount = value;
	}

	public void setCorePoolSize(final long value) {
		this.corePoolSize = value;
	}

	public void setMaximumPoolSize(final long value) {
		this.maximumPoolSize = value;
	}

	public void setKeepAliveTime(final long value) {
		this.keepAliveTime = value;
	}

	public void setThreadSleep(final long value) {
		this.threadSleep = value;
	}

	public void setConnectionsPerHostLocalMin(final int value) {
		this.connectionsPerHostLocalMin = value;
	}

	public void setConnectionsPerHostLocalMax(final int value) {
		this.connectionsPerHostLocalMax = value;
	}

	public void setConnectionsPerHostRemoteMin(final int value) {
		this.connectionsPerHostRemoteMin = value;
	}

	public void setConnectionsPerHostRemoteMax(final int value) {
		this.connectionsPerHostRemoteMax = value;
	}

	public void setMaxRequestsPerConnectionLocal(final int value) {
		this.maxRequestsPerConnectionLocal = value;
	}

	public void setMaxRequestsPerConnectioRemote(final int value) {
		this.maxRequestsPerConnectionLocal = value;
	}

	public void setHostnames(final String[] value) {
		this.hostnames = value;
	}

	public void setKeyspaces(final String[] value) {
		this.keyspaces = value;
	}

	public void setUsername(final String value) {
		this.username = value;
	}

	public void setPassword(final String value) {
		this.password = value;
	}

	public void setPort(final int value) {
		this.port = value;
	}

	public void setPolicyLocalDc(final String value) {
		this.localDc = value;
	}

	public void setPolicyUsedHostsPerRemoteDc(final int value) {
		this.usedHostsPerRemoteDc = value;
	}

}
