package com.automation.tests;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

public class ValidateEmployee {

	@Test
	public void verifyUser() {
		given().when()
				.get("http://dummy.restapiexample.com/api/v1/employee/2")
				.then()
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.body("data.empployee_name", equalTo("Garrett Winters"))
				.body("message", equalTo("Successfully! Record has been fetched."));

	}

}
