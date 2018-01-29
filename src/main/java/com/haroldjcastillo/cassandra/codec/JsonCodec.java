package com.haroldjcastillo.cassandra.codec;

import com.datastax.driver.extras.codecs.json.JacksonJsonCodec;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * The Class JsonCodec.
 *
 * @author harold.castillo
 * @since 03-03-2017 04:26:14 PM
 */
public class JsonCodec extends JacksonJsonCodec<JsonNode> {

	public JsonCodec() {
		super(JsonNode.class);
	}

}
