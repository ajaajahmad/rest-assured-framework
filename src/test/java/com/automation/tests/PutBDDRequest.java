package com.automation.tests;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

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

		jsonString.put("id", "2");
		jsonString.put("employee_name", "Garrett Winters");
		jsonString.put("employee_salary", "170561");
		jsonString.put("employee_age", "63");
		jsonString.put("profile_image", "");
		
		// GET employee details
		validate = given()
					.baseUri("https://dummy.restapiexample.com/api")
					.basePath("/v1/employee/2")
					.contentType(ContentType.JSON)
					.when()
					.get()
					.then()
					.assertThat().statusCode(200);
		
		System.out.println("GET Response:\n" + validate.extract().asPrettyString());
		
		// PUT update employee
		validator = given()
					.baseUri("https://dummy.restapiexample.com/api")
					.basePath("/v1/update/2")
					.contentType(ContentType.JSON)
					.body(jsonString)
					.when()
					.put()
					.then()
					.assertThat().statusCode(200)
					.body("data.employee_name", equalTo("Garrett Winters"))
					.body("message", equalTo("Successfully! Record has been updated."));
		
		System.out.println("PUT Response:\n" + validator.extract().asPrettyString());
	}

}
