package com.haroldjcastillo.cassandra.jmx;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.haroldjcastillo.cassandra.common.ConfigurationDefult;

public class Configuration extends AbstractMBean implements ConfigurationMXBean, ConfigurationDefult {

	private AtomicLong executionBlockcount;

	private AtomicLong corePoolSize;

	private AtomicLong maximumPoolSize;

	private AtomicLong keepAliveTime;

	private AtomicLong threadSleep;

	private AtomicInteger connectionsPerHostLocalMin;

	private AtomicInteger connectionsPerHostLocalMax;

	private AtomicInteger connectionsPerHostRemoteMin;

	private AtomicInteger connectionsPerHostRemoteMax;

	private AtomicInteger maxRequestsPerConnectionLocal;

	private AtomicInteger maxRequestsPerConnectionRemote;

	private String[] hostnames;

	private String username;

	private String password;

	private AtomicInteger port;

	private String localDc;

	private AtomicInteger usedHostsPerRemoteDc;

	public Configuration(final String name) {
		super(name);
		executionBlockcount = new AtomicLong(EXECUTIONBLOCKCOUNT);
		corePoolSize = new AtomicLong(COREPOOLSIZE);
		maximumPoolSize = new AtomicLong(MAXIMUMPOOLSIZE);
		keepAliveTime = new AtomicLong(KEEPALIVETIME);
		threadSleep = new AtomicLong(THREADSLEEP);
		connectionsPerHostLocalMin = new AtomicInteger(CONNECTIONSPERHOSTLOCALMIN);
		connectionsPerHostLocalMax = new AtomicInteger(CONNECTIONSPERHOSTLOCALMAX);
		connectionsPerHostRemoteMin = new AtomicInteger(CONNECTIONSPERHOSTREMOTEMIN);
		connectionsPerHostRemoteMax = new AtomicInteger(CONNECTIONSPERHOSTREMOTEMAX);
		maxRequestsPerConnectionLocal = new AtomicInteger(MAXREQUESTSPERCONNECTIONLOCAL);
		maxRequestsPerConnectionRemote = new AtomicInteger(MAXREQUESTSPERCONNECTIONREMOTE);
		hostnames = HOSTNAMES;
		username = USERNAME;
		password = PASSWORD;
		port = new AtomicInteger(PORT);
		localDc = LOCALDC;
		usedHostsPerRemoteDc = new AtomicInteger(USEDHOSTSPERREMOTEDC);
	}
	
	public Configuration(final String name, final long executionBlockcount, final int corePoolSize, final int maximumPoolSize,
			final int keepAliveTime, final int threadSleep, final int connectionsPerHostLocalMin,
			final int connectionsPerHostLocalMax, final int connectionsPerHostRemoteMin,
			final int connectionsPerHostRemoteMax, final int maxRequestsPerConnectionLocal,
			final int maxRequestsPerConnectionRemote, final String[] hostnames, final String username, final String password,
			final int port, final String localDc, final int usedHostsPerRemoteDc) {
		super(name);
		this.executionBlockcount = new AtomicLong(executionBlockcount);
		this.corePoolSize = new AtomicLong(corePoolSize);
		this.maximumPoolSize = new AtomicLong(maximumPoolSize);
		this.keepAliveTime = new AtomicLong(keepAliveTime);
		this.threadSleep = new AtomicLong(threadSleep);
		this.connectionsPerHostLocalMin = new AtomicInteger(connectionsPerHostLocalMin);
		this.connectionsPerHostLocalMax = new AtomicInteger(connectionsPerHostRemoteMax);
		this.connectionsPerHostRemoteMin = new AtomicInteger(connectionsPerHostRemoteMin);
		this.connectionsPerHostRemoteMax = new AtomicInteger(connectionsPerHostRemoteMax);
		this.maxRequestsPerConnectionLocal = new AtomicInteger(maxRequestsPerConnectionLocal);
		this.maxRequestsPerConnectionRemote = new AtomicInteger(maxRequestsPerConnectionRemote);
		this.hostnames = hostnames;
		this.username = username;
		this.password = password;
		this.port = new AtomicInteger(port);
		this.localDc = localDc;
		this.usedHostsPerRemoteDc = new AtomicInteger(usedHostsPerRemoteDc);
	}

	@Override
	public long getExecutionBlockcount() {
		return executionBlockcount.get();
	}

	@Override
	public long getCorePoolSize() {
		return corePoolSize.get();
	}

	@Override
	public long getMaximumPoolSize() {
		return maximumPoolSize.get();
	}

	@Override
	public long getKeepAliveTime() {
		return keepAliveTime.get();
	}

	@Override
	public long getThreadSleep() {
		return threadSleep.get();
	}

	@Override
	public int getConnectionsPerHostLocalMin() {
		return connectionsPerHostLocalMin.get();
	}

	@Override
	public int getConnectionsPerHostLocalMax() {
		return connectionsPerHostLocalMax.get();
	}

	@Override
	public int getConnectionsPerHostRemoteMin() {
		return connectionsPerHostRemoteMin.get();
	}

	@Override
	public int getConnectionsPerHostRemoteMax() {
		return connectionsPerHostRemoteMax.get();
	}

	@Override
	public int getMaxRequestsPerConnectionLocal() {
		return maxRequestsPerConnectionLocal.get();
	}

	@Override
	public int getMaxRequestsPerConnectioRemote() {
		return maxRequestsPerConnectionRemote.get();
	}

	@Override
	public String[] getHostnames() {
		return hostnames;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public int getPort() {
		return port.get();
	}

	@Override
	public String getPolicyLocalDc() {
		return localDc;
	}

	@Override
	public int getPolicyUsedHostsPerRemoteDc() {
		return usedHostsPerRemoteDc.get();
	}

	@Override
	public void setExecutionBlockcount(final long value) {
		this.executionBlockcount.set(value);
	}

	@Override
	public void setCorePoolSize(long value) {
		this.corePoolSize.set(value);
	}

	@Override
	public void setMaximumPoolSize(long value) {
		this.maximumPoolSize.set(value);
	}

	@Override
	public void setKeepAliveTime(long value) {
		this.keepAliveTime.set(value);
	}

	@Override
	public void setThreadSleep(long value) {
		this.threadSleep.set(value);
	}

	@Override
	public void setConnectionsPerHostLocalMin(int value) {
		this.connectionsPerHostLocalMin.set(value);
	}

	@Override
	public void setConnectionsPerHostLocalMax(int value) {
		this.connectionsPerHostLocalMax.set(value);
	}

	@Override
	public void setConnectionsPerHostRemoteMin(int value) {
		this.connectionsPerHostRemoteMin.set(value);
	}

	@Override
	public void setConnectionsPerHostRemoteMax(int value) {
		this.connectionsPerHostRemoteMax.set(value);
	}

	@Override
	public void setMaxRequestsPerConnectionLocal(int value) {
		this.maxRequestsPerConnectionLocal.set(value);
	}

	@Override
	public void setMaxRequestsPerConnectioRemote(int value) {
		this.maxRequestsPerConnectionLocal.set(value);
	}

	@Override
	public void setHostnames(String[] value) {
		this.hostnames = value;
	}

	@Override
	public void setUsername(String value) {
		this.username = value;
	}

	@Override
	public void setPassword(String value) {
		this.password = value;
	}

	@Override
	public void setPort(int value) {
		this.port.set(value);
	}

	@Override
	public void setPolicyLocalDc(String value) {
		this.localDc = value;
	}

	@Override
	public void setPolicyUsedHostsPerRemoteDc(int value) {
		this.usedHostsPerRemoteDc.set(value);
	}

}
