package com.automation.tests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

public class JsonDemo {

	@Test
	public void passBodyAsJsonArray() {
		// JSON Object for first employee
		JSONObject firstEmployee = new JSONObject();
		firstEmployee.put("employee_name", "ajaaj");
		firstEmployee.put("profile_image", "test.png");
		firstEmployee.put("employee_age", "26");
		firstEmployee.put("employee_salary", "11111");

		// JSON Object for second employee
		JSONObject secondEmployee = new JSONObject();
		secondEmployee.put("employee_name", "ahmad");
		secondEmployee.put("profile_image", "test.png");
		secondEmployee.put("employee_age", "36");
		secondEmployee.put("employee_salary", "99999");

		// Create JSON Array and add both JSON objects
		JSONArray array = new JSONArray();
		array.add(firstEmployee);
		array.add(secondEmployee);

		// Print JSON array (optional)
		System.out.println(array.toJSONString());
	}
}