package com.automation.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class PatchBDDRequest {

	RequestSpecification requestSpecification;
	Response response;
	ValidatableResponse validateResponse;

	@Test
	public void updateUser() {
		Map<String, String> map = new HashMap<>();
		map.put("name", "William");
		
		validateResponse = given()
				.baseUri("https://reqres.in/api/users/2")
				.contentType(ContentType.JSON)
				.header("x-api-key", "reqres-free-v1")
				.body(map)
				.when()
				.patch()
				.then()
				.assertThat().statusCode(200)
				.body("name", equalTo("William"));
	}

}