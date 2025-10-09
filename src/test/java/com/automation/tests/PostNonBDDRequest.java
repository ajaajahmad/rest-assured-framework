package com.automation.tests;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class PostNonBDDRequest {

	RequestSpecification requestSpec;
	Response response;
	ValidatableResponse validatableResp;

	@Test
	public void verifyStatusCode() {

		String jsonString = "{\"name\":\"newapitest\",\"salary\":\"4000\",\"age\":\"29\"}";
		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1/create";

		// Create a request specification.
		requestSpec = RestAssured.given();

		// Setting content type to specify format in which request payload will be sent.
		requestSpec.contentType(ContentType.JSON);

		// Adding body as string.
		requestSpec.body(jsonString);

		// Calling post methond.
		response = requestSpec.post();

		String responseString = response.prettyPrint();

		validatableResp = response.then();

		// Check status code.
		validatableResp.statusCode(200);

		// Check status line.
		validatableResp.statusLine("HTTP/1.1 200 OK");

		validatableResp.body("data.name", equalTo("newapitest"));
		validatableResp.body("message", equalTo("Successfully! Record has been added."));

		System.out.println(responseString);

	}
}
