package com.automation.tests;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import io.restassured.response.ValidatableResponse;

public class DeleteBDDRequest {
	
	ValidatableResponse validateResponse;
	
	@Test
	public void deleteUser() {
		validateResponse = given()
				.baseUri("https://dummy.restapiexample.com/api")
				.basePath("/v1/delete/3")
				.when()
				.delete()
				.then()
				.assertThat().statusCode(200)
				.body("message", equalTo("Successfully! Record has been deleted"));
		System.out.println("Response: " + validateResponse.extract().asPrettyString());
		
	}

}
