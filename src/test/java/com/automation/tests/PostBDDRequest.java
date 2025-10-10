package com.automation.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.anyOf;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

public class PostBDDRequest {


    ValidatableResponse validate;

    @Test
    public void createUser() {

        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("name", "ajaaj");
        jsonBody.put("salary", "2000");
        jsonBody.put("age", "26");

        validate = given()
                    .baseUri("https://dummy.restapiexample.com/api")
                    .contentType(ContentType.JSON)
                    .body(jsonBody)
                    .when().post("/v1/create")
                    .then()
                    .assertThat()
                    .statusCode(anyOf(equalTo(200), equalTo(201)))
                    .body("data.name", equalTo("ajaaj"))
                    .body("message", equalTo("Successfully! Record has been added."));

        System.out.println("Response : " + validate.extract().asPrettyString());
    }
}