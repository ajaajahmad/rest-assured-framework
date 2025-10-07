package com.automation.utils;

import com.automation.config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RequestSpecBuilderUtil {

	/**
	 * Build basic request specification
	 */
	public static RequestSpecification buildRequestSpec() {
		return new RequestSpecBuilder().setBaseUri(ConfigReader.getBaseUrl()).setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON).log(LogDetail.ALL).build();
	}

	/**
	 * Build request specification with authentication
	 */
	public static RequestSpecification buildAuthenticatedRequestSpec(String token) {
		return new RequestSpecBuilder().setBaseUri(ConfigReader.getBaseUrl())
				.addHeader("Authorization", "Bearer " + token).setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON).log(LogDetail.ALL).build();
	}

	/**
	 * Build request specification with custom headers
	 */
	public static RequestSpecification buildRequestSpecWithHeaders(Map<String, String> headers) {
		RequestSpecBuilder builder = new RequestSpecBuilder().setBaseUri(ConfigReader.getBaseUrl())
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).log(LogDetail.ALL);

		if (headers != null && !headers.isEmpty()) {
			builder.addHeaders(headers);
		}

		return builder.build();
	}

	/**
	 * Build request specification with query parameters
	 */
	public static RequestSpecification buildRequestSpecWithQueryParams(Map<String, Object> queryParams) {
		RequestSpecBuilder builder = new RequestSpecBuilder().setBaseUri(ConfigReader.getBaseUrl())
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).log(LogDetail.ALL);

		if (queryParams != null && !queryParams.isEmpty()) {
			builder.addQueryParams(queryParams);
		}

		return builder.build();
	}

	/**
	 * Build request specification with path parameters
	 */
	public static RequestSpecification buildRequestSpecWithPathParams(Map<String, String> pathParams) {
		RequestSpecBuilder builder = new RequestSpecBuilder().setBaseUri(ConfigReader.getBaseUrl())
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).log(LogDetail.ALL);

		if (pathParams != null && !pathParams.isEmpty()) {
			builder.addPathParams(pathParams);
		}

		return builder.build();
	}

	/**
	 * Build request specification for OAuth token
	 */
	public static RequestSpecification buildTokenRequestSpec() {
		return new RequestSpecBuilder().setBaseUri(ConfigReader.getTokenUrl())
				.setContentType("application/x-www-form-urlencoded").setAccept(ContentType.JSON).log(LogDetail.ALL)
				.build();
	}

	/**
	 * Build request specification with timeout
	 */
	public static RequestSpecification buildRequestSpecWithTimeout(int timeoutMillis) {
		return new RequestSpecBuilder().setBaseUri(ConfigReader.getBaseUrl()).setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON)
				.setConfig(io.restassured.config.RestAssuredConfig.config()
						.httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
								.setParam("http.connection.timeout", timeoutMillis)
								.setParam("http.socket.timeout", timeoutMillis)))
				.log(LogDetail.ALL).build();
	}

	/**
	 * Build relaxed HTTPS request specification
	 */
	public static RequestSpecification buildRelaxedHTTPSRequestSpec() {
		return new RequestSpecBuilder().setBaseUri(ConfigReader.getBaseUrl()).setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON).setRelaxedHTTPSValidation().log(LogDetail.ALL).build();
	}
}