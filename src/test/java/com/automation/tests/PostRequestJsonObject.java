package com.automation.tests;

import org.json.simple.JSONObject;
import org.junit.Test;

public class PostRequestJsonObject {

	@Test
	public void passBodyAsJsonObject() {

		JSONObject data = new JSONObject();

		data.put("employee_name", "MapTest");
		data.put("profile_image", "test.png");
		data.put("employee_age", "30");
		data.put("employee_salary", "234");

	}

}
