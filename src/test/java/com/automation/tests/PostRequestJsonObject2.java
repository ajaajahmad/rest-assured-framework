package com.automation.tests;

import static org.hamcrest.CoreMatchers.equalTo;

import org.json.simple.JSONObject;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PostRequestJsonObject2 {

    @Test
    public void passBodyAsJsonObject() {
        
    	JSONObject data = new JSONObject();
    	
    	data.put("profile_image", "test.png");
        
    	JSONObject details = new JSONObject();
    	
    	details.put("updated_message", "Details of New Resource");
        details.put("employee_age", "30");
        
        details.put("employee_details", details);
        
        details.put("employee_name", "MapTest");
        details.put("employee_salary", "11111");
        

        RestAssured
        
        .given()
            .contentType(ContentType.JSON)
            .body(data.toString())
        .when()
            .post("https://dummy.restapiexample.com/api/v1/create")
        .then()
            .assertThat().statusCode(200)
            .body("data.employee_name", equalTo("MapTest"))
            .body("data.employee_details.employee_age", equalTo("30"))
            .body("data.employee_details.updated_message", equalTo("Details of New Resource"))
            .body("data.employee_salary", equalTo("11111"))
            .body("data.profile_image", equalTo("test.png"))
            .body("message", equalTo("Successfully! Record has been added."))
            .log().all();
    }
}