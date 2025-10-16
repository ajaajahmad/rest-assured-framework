package com.automation.tests;

import org.json.simple.JSONObject;

public class JsonArrayDemo {

	public void passBodyAsJsonArray() {
		JSONObject data1 = new JSONObject();

		data1.put("firstname", "Ajaaj");
		data1.put("lastname", "Ahmad");
		data1.put("age", "25");
		data1.put("salary", "116328");

	}
}
