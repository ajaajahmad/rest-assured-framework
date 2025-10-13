package com.automation.tests;

import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class PatchNonBDDRequest {

	RequestSpecification requestSpec;
	Response response;
	ValidatableResponse validateResponse;

	@Test
	public void updateUser() {

		Map<String, String> map = new HashMap<>();
		map.put("name", "William");

		RestAssured.baseURI = "https://reqres.in/api/users/2";

		requestSpec = RestAssured.given();
		requestSpec.contentType(ContentType.JSON);
		requestSpec.header("x-api-key", "reqres-free-v1");
		requestSpec.body(map);
		response = requestSpec.patch();

		String responseString = response.prettyPrint();

		validateResponse = response.then();
		validateResponse.statusCode(200);
		validateResponse.statusLine("HTTP/1.1 200 OK");

		validateResponse.body("name", equalTo("William"));

		System.out.println("Response: " + responseString);
	}

}
