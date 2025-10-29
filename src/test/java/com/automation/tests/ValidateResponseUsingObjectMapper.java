package com.automation.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

class UserData {
	public int id;
	public String email;
	public String first_name;
	public String last_name;

}

public class ValidateResponseUsingObjectMapper {

	public static void main(String[] args) throws Exception {

		// Base URI
		RestAssured.baseURI = "https://reqres.in/api";

		// Send GET Request
		Response response = RestAssured.given().when().get("/users/2").then().statusCode(200).extract().response();

		// Convert response JSON to Java object using ObjectMapper
		ObjectMapper mapper = new ObjectMapper();
		UserData user = mapper.readValue(response.jsonPath().getJsonObject("data").toString(), UserData.class);

		// Validate response fields
		if (user.id == 2 && user.first_name.equals("Janet")) {
			System.out.println("Response validated successfully!");
		} else {
			System.out.println("Validation failed!");
		}
	}
}