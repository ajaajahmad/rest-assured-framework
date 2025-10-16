package com.automation.tests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

public class JsonArrayDemo {

	@Test
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
		data2.put("salary", "74233798");

		JSONArray array1 = new JSONArray();
		array1.add(data1);

		JSONArray array2 = new JSONArray();
		array2.add(data2);

		JSONObject data3 = new JSONObject();
		data3.put("employee1", array1);
		data3.put("employee2", array2);

		System.out.println(data3.toString());

	}
}
