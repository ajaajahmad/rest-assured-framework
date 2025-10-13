package com.automation.tests;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class PatchBDDRequest {

	RequestSpecification requestSpecification;
	Response response;
	ValidatableResponse validateResponse;

	@Test
	public void updateUser() {
		Map<String, String> map = new HashMap<>();
		map.put("name", "William");
	}

}
