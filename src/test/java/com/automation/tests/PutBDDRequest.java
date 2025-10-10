package com.automation.tests;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PutBDDRequest {

	RequestSpecification request;
	ResponseSpecification response;
	ValidatableResponse validate, validator;

	@Test
	public void updateUser() {

		Map<String, String> jsonString = new HashMap<>();

		jsonString.put("id", "501");
		jsonString.put("employee_name", "ajaaj");
		jsonString.put("emplayee_salary", "2983");
		jsonString.put("employee_age", "26");
		jsonString.put("profile_image", "");
		
		validate = given()
					.baseUri("https://dummy.restapiexample.com/api/v1/update/2")
					.contentType(ContentType.JSON)
					.when()
					.get()
					.then()
					.assertThat().statusCode(200);
		
		System.out.println(response.extract().asPrettyString());

	}

}
