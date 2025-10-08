package com.automation.tests;

import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class NonBDDRequest {

	RequestSpecification requestSpecification;
	Response response;
	ValidatableResponse validatableResponse;

	@Test
	public void verifyStatusCode() {
		RestAssured.baseURI = "https://reqres.in/api/users/2";

		// Step 1: Create request specification and add header
		requestSpecification = RestAssured.given();
		requestSpecification.header("x-api-key", "reqres-free-v1");

		// Step 2: Send GET request
		response = requestSpecification.get();

		// Step 3: Print response body
		String restString = response.prettyPrint();
		System.out.println("Response Details: " + restString);

		// Step 4: Validate response using response.then()
		validatableResponse = response.then();
		validatableResponse.statusCode(200);
		validatableResponse.statusLine("HTTP/1.1 200 OK");
	}
}