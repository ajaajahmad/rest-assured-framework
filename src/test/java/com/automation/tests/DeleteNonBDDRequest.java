package com.automation.tests;

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
		String stringResponse = response.asPrettyString();
	}

}
