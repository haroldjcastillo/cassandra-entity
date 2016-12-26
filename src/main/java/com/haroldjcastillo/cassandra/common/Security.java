package com.haroldjcastillo.cassandra.common;

public enum Security {

	AES("AES"),

	CIPHER_AES("AES/CBC/PKCS5PADDING"),

	RANDOM_INIT_VECTOR("RandomInitVector"),

	KEY("dde1ac64-1c1f-4c");

	private String value;

	Security(final String value) {
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}