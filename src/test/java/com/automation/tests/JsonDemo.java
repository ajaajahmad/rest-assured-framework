package com.automation.tests;

import org.json.simple.JSONObject;

public class JsonDemo {
	
	public void passBodyAsJsonArray() {
		//JSON Object for first employee
		JSONObject firstEmployee = new JSONObject();
		
		firstEmployee.put("employee_name", "ajaaj");
		firstEmployee.put("profile_image", "test.png");
		firstEmployee.put("employee_age", "26");
		firstEmployee.put("employee_salary", "11111");
		
		
	}

}
