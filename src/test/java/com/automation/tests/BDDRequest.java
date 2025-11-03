package com.automation.tests;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

public class BDDRequest {

	@Test
    public void verifyStatusCode() {
        given()
            .baseUri("https://reqres.in/api")
            .header("x-api-key", "reqres-free-v1")
        .when()
            .get("/users/2")
        .then()
            .statusCode(200)
            .statusLine("HTTP/1.1 200 OK")
            .body("data.first_name", equalTo("Janet"))
            .body("data.last_name", equalTo("Weaver"))
            .body("data.email", equalTo("janet.weaver@reqres.in"));
    }
}