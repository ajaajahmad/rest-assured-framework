package com.automation.tests;

import static org.hamcrest.CoreMatchers.equalTo;

import org.json.simple.JSONObject;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PostRequestJsonObject {

	@Test
	public void passBodyAsJsonObject() {

		JSONObject data = new JSONObject();

		data.put("employee_name", "MapTest");
		data.put("profile_image", "test.png");
		data.put("employee_age", "30");
		data.put("employee_salary", "234");
		
		RestAssured.given()
			.contentType(ContentType.JSON)
			.body(data.toString())
			.log().all()
			.when()
			.post("https://dummy.restapiexample.com/api/v1/create")
			.then()
			.assertThat().statusCode(200)
			.body("message", equalTo("Successfully! Record has been added."))
			.log().all();
	}

}
