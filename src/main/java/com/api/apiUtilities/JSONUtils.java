package com.api.apiUtilities;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

	@SuppressWarnings("unchecked")
	public static Map<String, String> jsonReader(String json) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = null;
		try {
			map = mapper.readValue(json, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
