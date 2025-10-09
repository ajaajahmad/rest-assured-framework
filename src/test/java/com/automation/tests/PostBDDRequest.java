package com.automation.tests;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;

import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import io.restassured.response.ValidatableResponse;

public class PostBDDRequest {

	ValidatableResponse validate;

	@Test
	public void createUser() {

		String jsonString = "{\"name\":\"ajaaj\",\"salary\":\"2000\",\"age\":\"26\"}";
		
		validate = given()
					.baseUri("https://dummy.restapiexample.com/api")
					.contentType(ContentType.JSON)
					.body(jsonString)
					.when().post()
					.then()
					.assertThat().statusCode(200).body("data.name", equalTo("ajaaj"))
					.body("message", equalTo("Successfully! Record has been added."));
		
		System.out.println("Response : " + validate.extract().asPrettyString());
	}
}
