package com.automation.utils;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;

public class ResponseSpecBuilderUtil {

	/**
	 * Build basic response specification for successful response
	 */
	public static ResponseSpecification buildSuccessResponseSpec() {
		return new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).log(LogDetail.ALL)
				.build();
	}

	/**
	 * Build response specification for created resource
	 */
	public static ResponseSpecification buildCreatedResponseSpec() {
		return new ResponseSpecBuilder().expectStatusCode(201).expectContentType(ContentType.JSON).log(LogDetail.ALL)
				.build();
	}

	/**
	 * Build response specification for no content
	 */
	public static ResponseSpecification buildNoContentResponseSpec() {
		return new ResponseSpecBuilder().expectStatusCode(204).log(LogDetail.ALL).build();
	}

	/**
	 * Build response specification for bad request
	 */
	public static ResponseSpecification buildBadRequestResponseSpec() {
		return new ResponseSpecBuilder().expectStatusCode(400).expectContentType(ContentType.JSON).log(LogDetail.ALL)
				.build();
	}

	/**
	 * Build response specification for unauthorized
	 */
	public static ResponseSpecification buildUnauthorizedResponseSpec() {
		return new ResponseSpecBuilder().expectStatusCode(401).log(LogDetail.ALL).build();
	}

	/**
	 * Build response specification for forbidden
	 */
	public static ResponseSpecification buildForbiddenResponseSpec() {
		return new ResponseSpecBuilder().expectStatusCode(403).expectContentType(ContentType.JSON).log(LogDetail.ALL)
				.build();
	}

	/**
	 * Build response specification for not found
	 */
	public static ResponseSpecification buildNotFoundResponseSpec() {
		return new ResponseSpecBuilder().expectStatusCode(404).expectContentType(ContentType.JSON).log(LogDetail.ALL)
				.build();
	}

	/**
	 * Build response specification with custom status code
	 */
	public static ResponseSpecification buildResponseSpec(int statusCode) {
		return new ResponseSpecBuilder().expectStatusCode(statusCode).expectContentType(ContentType.JSON)
				.log(LogDetail.ALL).build();
	}

	/**
	 * Build response specification with response time validation
	 */
	public static ResponseSpecification buildResponseSpecWithTime(int statusCode, long maxTimeInMs) {
		return new ResponseSpecBuilder().expectStatusCode(statusCode).expectContentType(ContentType.JSON)
				.expectResponseTime(lessThan(maxTimeInMs)).log(LogDetail.ALL).build();
	}

	/**
	 * Build response specification with body validation
	 */
	public static ResponseSpecification buildResponseSpecWithBody(int statusCode, String bodyKey) {
		return new ResponseSpecBuilder().expectStatusCode(statusCode).expectContentType(ContentType.JSON)
				.expectBody(bodyKey, notNullValue()).log(LogDetail.ALL).build();
	}

	/**
	 * Build response specification with header validation
	 */
	public static ResponseSpecification buildResponseSpecWithHeader(int statusCode, String headerName) {
		return new ResponseSpecBuilder().expectStatusCode(statusCode).expectBody(headerName, notNullValue())
				.log(LogDetail.ALL).build();
	}

	/**
	 * Build comprehensive response specification
	 */
	public static ResponseSpecification buildComprehensiveResponseSpec() {
		return new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON)
				.expectResponseTime(lessThan(5000L)).expectHeader("Content-Type", containsString("application/json"))
				.log(LogDetail.ALL).build();
	}
}