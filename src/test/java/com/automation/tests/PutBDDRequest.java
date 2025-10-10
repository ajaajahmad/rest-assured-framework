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
		
		
		// get details of employee
		validate = given()
					.baseUri("https://dummy.restapiexample.com/api/v1/employee/2")
					.contentType(ContentType.JSON)
					.when()
					.get()
					.then()
					.assertThat().statusCode(200);
		
		System.out.println(validate.extract().asPrettyString());
		
		// update employee salary
		validator = given()
					.basePath("https://dummy.restapiexample.com/api")
					.basePath("/v1/update/2")
					.contentType(ContentType.JSON)
					.when()
					.put()
					.then()
					.assertThat().statusCode(200)
					.body("data.employee_name", equalTo(""))
					.body("message", equalTo("Successfully! Record has been updated."));
		
		System.out.println(validator.extract().asPrettyString());
	}

}
