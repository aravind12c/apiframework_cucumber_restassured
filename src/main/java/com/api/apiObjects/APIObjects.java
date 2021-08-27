package com.api.apiObjects;

import java.io.File;

import com.api.apiUtilities.PropertyUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIObjects {

	private static final String BASE_URL = PropertyUtils.configProperty("BaseURL");
	private static Response response;
	private static RequestSpecification request;

	public static void setBaseURL() {
		RestAssured.baseURI = BASE_URL;
	}

	public static void setRestRequestforJson(String contentType, String jsonObject) {
		request = RestAssured.given().header("Content-Type", contentType).body(jsonObject);
	}

	public static void setRestRequestforFile(String contentType, File image) {
		request = RestAssured.given().header("Content-Type", contentType)
				.multiPart("additionalMetadata", "Uploaded Image Successfully").multiPart("file", image);
	}

	public static Response apiMethods(String method, String endpoint) {
		if (method.equalsIgnoreCase("Get")) {
			response = request.get(endpoint);
		} else if (method.equalsIgnoreCase("Post")) {
			response = request.post(endpoint);
		} else if (method.equalsIgnoreCase("Put")) {
			response = request.put(endpoint);
		} else if (method.equalsIgnoreCase("Delete")) {
			response = request.delete(endpoint);
		}
		return response;
	}
}
