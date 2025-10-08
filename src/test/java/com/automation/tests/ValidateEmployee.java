package com.automation.tests;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

public class ValidateEmployee {

	@Test
	public void verifyUser() {
		given()
				.header("x-api-key", "reqres-free-v1")
				.when()
				.get("https://reqres.in/api/users/2")
				.then()
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.body("data.first_name", equalTo("Janet"))
				.body("data.last_name", equalTo("Weaver"))
				.body("data.email", equalTo("janet.weaver@reqres.in"));

	}

}
