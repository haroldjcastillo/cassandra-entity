package com.haroldjcastillo.cassandra.jmx;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.haroldjcastillo.cassandra.common.Configuration;

public class ConfigurationBean extends Metadata implements ConfigurationMXBean {

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
	
	private String[] keyspaces;

	public ConfigurationBean(final Configuration configuration) {
		super(configuration.getName());
		this.executionBlockcount = new AtomicLong(configuration.getExecutionBlockcount());
		this.corePoolSize = new AtomicLong(configuration.getCorePoolSize());
		this.maximumPoolSize = new AtomicLong(configuration.getMaximumPoolSize());
		this.keepAliveTime = new AtomicLong(configuration.getKeepAliveTime());
		this.threadSleep = new AtomicLong(configuration.getThreadSleep());
		this.connectionsPerHostLocalMin = new AtomicInteger(configuration.getConnectionsPerHostLocalMin());
		this.connectionsPerHostLocalMax = new AtomicInteger(configuration.getConnectionsPerHostRemoteMax());
		this.connectionsPerHostRemoteMin = new AtomicInteger(configuration.getConnectionsPerHostRemoteMin());
		this.connectionsPerHostRemoteMax = new AtomicInteger(configuration.getConnectionsPerHostRemoteMax());
		this.maxRequestsPerConnectionLocal = new AtomicInteger(configuration.getMaxRequestsPerConnectionLocal());
		this.maxRequestsPerConnectionRemote = new AtomicInteger(configuration.getMaxRequestsPerConnectionRemote());
		this.hostnames = configuration.getHostnames();
		this.username = configuration.getUsername();
		this.password = configuration.getPassword();
		this.port = new AtomicInteger(configuration.getPort());
		this.localDc = configuration.getPolicyLocalDc();
		this.usedHostsPerRemoteDc = new AtomicInteger(configuration.getPolicyUsedHostsPerRemoteDc());
		this.keyspaces = configuration.getKeyspaces();
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
	public String[] getKeyspaces() {
		return keyspaces;
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
	public void setCorePoolSize(final long value) {
		this.corePoolSize.set(value);
	}

	@Override
	public void setMaximumPoolSize(final long value) {
		this.maximumPoolSize.set(value);
	}

	@Override
	public void setKeepAliveTime(final long value) {
		this.keepAliveTime.set(value);
	}

	@Override
	public void setThreadSleep(final long value) {
		this.threadSleep.set(value);
	}

	@Override
	public void setConnectionsPerHostLocalMin(final int value) {
		this.connectionsPerHostLocalMin.set(value);
	}

	@Override
	public void setConnectionsPerHostLocalMax(final int value) {
		this.connectionsPerHostLocalMax.set(value);
	}

	@Override
	public void setConnectionsPerHostRemoteMin(final int value) {
		this.connectionsPerHostRemoteMin.set(value);
	}

	@Override
	public void setConnectionsPerHostRemoteMax(final int value) {
		this.connectionsPerHostRemoteMax.set(value);
	}

	@Override
	public void setMaxRequestsPerConnectionLocal(final int value) {
		this.maxRequestsPerConnectionLocal.set(value);
	}

	@Override
	public void setMaxRequestsPerConnectioRemote(final int value) {
		this.maxRequestsPerConnectionLocal.set(value);
	}

	@Override
	public void setHostnames(final String[] value) {
		this.hostnames = value;
	}
	
	@Override
	public void setKeyspaces(final String[] value) {
		this.keyspaces = value;
	}

	@Override
	public void setUsername(final String value) {
		this.username = value;
	}

	@Override
	public void setPassword(final String value) {
		this.password = value;
	}

	@Override
	public void setPort(final int value) {
		this.port.set(value);
	}

	@Override
	public void setPolicyLocalDc(final String value) {
		this.localDc = value;
	}

	@Override
	public void setPolicyUsedHostsPerRemoteDc(final int value) {
		this.usedHostsPerRemoteDc.set(value);
	}
	
}
