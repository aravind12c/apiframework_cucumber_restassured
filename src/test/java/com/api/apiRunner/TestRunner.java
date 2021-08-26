package com.api.apiRunner;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.testng.Assert;

import com.api.apiObjects.APIObjects;
import com.api.apiUtilities.PropertyUtils;

import io.cucumber.junit.Cucumber;

import io.cucumber.junit.CucumberOptions;
import io.restassured.response.Response;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/FeatureFile/PetAPI.feature", glue = {
		"classpath:com.api.stepDefinitions", "classpath:com.api.apiInitiation" }, plugin = {
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" }, tags = "@api")
public class TestRunner {

	private static final String USERNAME = PropertyUtils.configProperty("Username");
	private static final String PASSWORD = PropertyUtils.configProperty("Password");
	private static Response response;

	@BeforeClass
	public static void authorization() {
		APIObjects.setBaseURL();
		APIObjects.setRestRequestforJson("application/json",
				"{ \"userName\":\"" + USERNAME + "\", \"password\":\"" + PASSWORD + "\"}");
		response = APIObjects.apiMethods("Post", "/oauth/authorize");
		System.out.println("************************Authorization Status - "+response.getStatusCode()+"************************");
	}

}
