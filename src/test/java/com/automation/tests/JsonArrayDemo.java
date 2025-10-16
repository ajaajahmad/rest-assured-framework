package com.automation.tests;

import org.json.simple.JSONObject;

public class JsonArrayDemo {

	public void passBodyAsJsonArray() {
		JSONObject data1 = new JSONObject();

		data1.put("firstname", "Ajaaj");
		data1.put("lastname", "Ahmad");
		data1.put("age", "25");
		data1.put("salary", "116328");
		
		JSONObject data2 = new JSONObject();
		
		data2.put("firstname", "Shahrukh");
		data2.put("lastname", "Khan");
		data2.put("age", "54");
		data2.put("salary", "742337");

	}
}
