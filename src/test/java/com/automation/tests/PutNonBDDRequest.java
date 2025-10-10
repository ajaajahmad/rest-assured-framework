package com.automation.tests;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class PutNonBDDRequest {

	RequestSpecification request;
	ValidatableResponse validate;
	Response response;

	@Test
	public void updateUser() {

		Map<String, String> jsonString = new HashMap<>();

		jsonString.put("id", "2");
		jsonString.put("employee_name", "Garrett Winters");
		jsonString.put("employee_salary", "90908");
		jsonString.put("employee_age", "46");
		jsonString.put("profile_image", "");

	}

}
