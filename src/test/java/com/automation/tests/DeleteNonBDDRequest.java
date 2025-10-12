package com.automation.tests;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class DeleteNonBDDRequest {

	RequestSpecification request;
	Response response;
	ValidatableResponse validate;

	@Test
	public void deleteUser() {
		RestAssured.baseURI = "https://dummy.restapiexample.com/api";
		RestAssured.basePath = "/v1/delete/3";
		String stringResponse = response.prettyPrint();
		validate = response.then();
		validate.statusCode(200);
		validate.statusLine("HTTP/1.1 200 OK");
		validate.body("message", equalTo("Successfully! Record has been deleted"));
	}

}
