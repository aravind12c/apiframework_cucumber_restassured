package com.api.stepDefinitions;

import java.io.File;
import java.util.Map;

import org.testng.Assert;

import com.api.apiObjects.APIObjects;
import com.api.apiUtilities.JSONUtils;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class APIStepDefinition {

	public static Response response;

	public static String endPoint;

	String petID = "10421871";
	String petname = "TestNewDog";
	String status = "sold";

	@Given("I have a Endpoint to add new pet to store")
	public void endpointToAddNewPet() {
		APIObjects.setBaseURL();
	}

	@When("I hit the endpoint using {string} Method to add new pet")
	public void addNewPetusingPost(String method) {
		endPoint = "v2/pet";
		APIObjects.setRestRequestforJson("application/json", "{\"id\":" + petID
				+ ",\"category\":{\"id\":104213,\"name\":\"Dogs\"},\"name\":\"TestDog\",\"photoUrls\":[\"sample\"],\"tags\":[{\"id\":104213,\"name\":\"Animal\"}],\"status\":\"available\"}");
		response = APIObjects.apiMethods(method, endPoint);
		System.out.println("Success Json is: " + response.asString());
	}

	@Then("I Validate the success response")
	public void validateSuccessResponse() {
		Assert.assertEquals(response.statusCode(), 200);
	}

	@And("I verify the pet is available by using {string} Method by PETID")
	public void verifyPetAvailability(String method) {
		endPoint = "v2/pet/" + petID;
		response = APIObjects.apiMethods(method, endPoint);
		Map<String, String> jsonResponse = JSONUtils.jsonReader(response.asString());
		Assert.assertEquals(jsonResponse.get("id"), Integer.parseInt(petID));
		Assert.assertEquals(jsonResponse.get("name"), "TestDog");
	}

	@Given("I have a Endpoint to upload the image of a pet")
	public void endpointToUploadImageofPet() {
		endPoint = "v2/pet/" + petID + "/uploadImage";
		APIObjects.setBaseURL();
	}

	@When("I hit the endpoint using {string} Method to upload the image")
	public void uploadImageofaPet(String method) {
		File avatarFile = new File("src/test/resources/Images/German-shepherd.jpg");
		APIObjects.setRestRequestforFile("multipart/form-data", avatarFile);
		response = APIObjects.apiMethods(method, endPoint);
		System.out.println("Status is: " + response.getStatusLine());
	}

	@And("I verify the response message whether Image got uploaded")
	public void verifyResponseMessageforImage() {
		Map<String, String> jsonResponse = JSONUtils.jsonReader(response.asString());
		Assert.assertTrue(jsonResponse.get("message").contains("German-shepherd"));
	}

	@Given("I have a Endpoint to update the pet details")
	public void endpointToUpdatePetDetails() {
		endPoint = "v2/pet";
		APIObjects.setBaseURL();
	}

	@When("I hit the endpoint using {string} Method to update the pet details")
	public void updatePetDetails(String method) {
		APIObjects.setRestRequestforJson("application/json", "{\"id\":" + petID
				+ ",\"category\":{\"id\":104213,\"name\":\"Dogs\"},\"name\":\"" + petname
				+ "\",\"photoUrls\":[\"sample\"],\"tags\":[{\"id\":104213,\"name\":\"Animal\"}],\"status\":\"available\"}");
		response = APIObjects.apiMethods(method, endPoint);
		System.out.println("Status is: " + response.getStatusLine());
	}

	@And("I verify the updated pet details by using {string} Method by PETID")
	public void verifyUpdatedPetDetailsareReflected(String method) {
		endPoint = "v2/pet/" + petID;
		response = APIObjects.apiMethods(method, endPoint);
		Map<String, String> jsonResponse = JSONUtils.jsonReader(response.asString());
		Assert.assertEquals(jsonResponse.get("id"), Integer.parseInt(petID));
		Assert.assertEquals(jsonResponse.get("name"), petname);
	}

	@Given("I have a Endpoint to delete the pet details")
	public void endpointToDeletePetDetails() {
		endPoint = "v2/pet/" + petID;
		APIObjects.setBaseURL();
	}

	@When("I hit the endpoint using {string} Method to delete the pet details")
	public void deletePetDetails(String method) {
		response = APIObjects.apiMethods(method, endPoint);
		System.out.println("Status is: " + response.getStatusLine());
	}

	@And("I verify the pet details are deleted using {string} method")
	public void verifyPetDetailsgotDeleted(String method) {
		endPoint = "v2/pet/" + petID;
		response = APIObjects.apiMethods(method, endPoint);
		Assert.assertEquals(response.statusCode(), 404);
	}

	@Given("I have a Endpoint to get the Pet details")
	public void endpointToGetPetDeatils() {
		endPoint = "v2/pet/02587892165465";
		APIObjects.setBaseURL();
	}

	@When("I hit the endpoint using {string} Method to get pet details")
	public void getPetDetails(String method) {
		response = APIObjects.apiMethods(method, endPoint);
		System.out.println("Json is: " + response.asString());
	}

	@Then("I recieve pet not found message")
	public void verifyPetNotFoundMsg() {
		Assert.assertEquals(response.statusCode(), 404);
		Map<String, String> jsonResponse = JSONUtils.jsonReader(response.asString());
		Assert.assertEquals(jsonResponse.get("message"), "Pet not found");
	}
}
