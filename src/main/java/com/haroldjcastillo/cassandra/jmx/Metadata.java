package com.haroldjcastillo.cassandra.jmx;

/**
 * The MBean object that allows to execute the methods for obtain the metadata
 * information.
 *
 * @author harold.castillo
 * @since 03-14-2017 03:29:18 PM
 */
public class Metadata extends AbstractMBean {

	private String metadata;

	public Metadata(final String name) {
		super(name);
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(final String metadata) {
		this.metadata = metadata;
	}

}
