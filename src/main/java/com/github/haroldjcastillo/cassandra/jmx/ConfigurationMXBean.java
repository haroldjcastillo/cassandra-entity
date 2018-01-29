package com.github.haroldjcastillo.cassandra.jmx;

/**
 * The Interface ConfigurationMXBean.
 *
 * @author harold.castillo
 * @since 03-03-2017 04:28:55 PM
 */
public interface ConfigurationMXBean {
	
	String getMetadata();

	long getExecutionBlockcount();

	long getCorePoolSize();
	
	long getMaximumPoolSize();
	
	long getKeepAliveTime();
	
	long getThreadSleep();
	
	String[] getKeyspaces();

	int getConnectionsPerHostLocalMin();
	
	int getConnectionsPerHostLocalMax();
	
	int getConnectionsPerHostRemoteMin();
	
	int getConnectionsPerHostRemoteMax();
	
	int getMaxRequestsPerConnectionLocal();
	
	int getMaxRequestsPerConnectioRemote();

	String[] getHostnames();
	
	String getUsername();
	
	String getPassword();
	
	int getPort();

	String getPolicyLocalDc();
	
	int getPolicyUsedHostsPerRemoteDc();
	
	void setExecutionBlockcount(long value);

	void setCorePoolSize(long value);
	
	void setMaximumPoolSize(long value);
	
	void setKeepAliveTime(long value);
	
	void setThreadSleep(long value);

	void setConnectionsPerHostLocalMin(int value);
	
	void setConnectionsPerHostLocalMax(int value);
	
	void setConnectionsPerHostRemoteMin(int value);
	
	void setConnectionsPerHostRemoteMax(int value);
	
	void setMaxRequestsPerConnectionLocal(int value);
	
	void setMaxRequestsPerConnectioRemote(int value);

	void setHostnames(String[] value);
	
	void setKeyspaces(String[] value);
	
	void setUsername(String value);
	
	void setPassword(String value);
	
	void setPort(int value);

	void setPolicyLocalDc(String value);
	
	void setPolicyUsedHostsPerRemoteDc(int value);	
	
}
