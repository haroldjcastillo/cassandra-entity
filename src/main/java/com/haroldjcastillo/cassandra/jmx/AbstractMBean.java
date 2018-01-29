package com.haroldjcastillo.cassandra.jmx;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

import com.datastax.driver.core.Configuration;

/**
 * The Class AbstractMBean.
 *
 * @author harold.castillo
 * @since 03-03-2017 04:28:33 PM
 */
public abstract class AbstractMBean {
	
	protected static final Logger LOGGER = Logger.getLogger(Configuration.class);
	
	protected static final MBeanServer MBS = ManagementFactory.getPlatformMBeanServer();

	protected ObjectName objectName;
	
	protected String name;
	
	protected AbstractMBean(final String name) {
		this.name = name;
		this.registerMBean();
	}
	
	protected void registerMBean() {

		try {
			objectName = new ObjectName("hn.com.tigo.cpe.cassandra.entity:type=" + name);
			if(!MBS.isRegistered(objectName)){
				MBS.registerMBean(this, objectName);
			}
		} catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException
				| NotCompliantMBeanException e) {
			LOGGER.error(e.getMessage());
		}

	}

	protected void unregisterMBean() {

		try {
			if(MBS.isRegistered(objectName)){
				MBS.unregisterMBean(objectName);
			}
		} catch (MBeanRegistrationException | InstanceNotFoundException e) {
			LOGGER.error(e.getMessage());
		}

	}
	
	@Override
	protected void finalize() throws Throwable {
		this.unregisterMBean();
		super.finalize();
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
