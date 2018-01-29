package com.github.haroldjcastillo.cassandra.common;

/**
 * The Interface ConfigurationDefult.
 *
 * @author harold.castillo
 * @since 03-03-2017 04:26:23 PM
 */
public interface ConfigurationDefult {
	
	long EXECUTIONBLOCKCOUNT = 2000;
	
	long COREPOOLSIZE = 100;

	long MAXIMUMPOOLSIZE = 200;
	
	long KEEPALIVETIME = 200;
	
	long THREADSLEEP = 1000;
	
	int CONNECTIONSPERHOSTLOCALMIN = 4;

	int CONNECTIONSPERHOSTLOCALMAX = 10;

	int CONNECTIONSPERHOSTREMOTEMIN = 2;

	int CONNECTIONSPERHOSTREMOTEMAX = 4;

	int MAXREQUESTSPERCONNECTIONLOCAL = 32768;

	int MAXREQUESTSPERCONNECTIONREMOTE = 2000;

	String[] HOSTNAMES = {"localhost"};

	String USERNAME = "";

	String PASSWORD = "";

	int PORT = 9042;

	String LOCALDC = "dc1";

	int USEDHOSTSPERREMOTEDC = 2;

}
