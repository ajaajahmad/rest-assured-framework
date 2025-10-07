package com.automation.api;

import com.automation.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ApiClient {

	private static final Logger log = LoggerFactory.getLogger(ApiClient.class);
	private static String accessToken;
	private RequestSpecification requestSpec;

	public ApiClient() {
		RestAssured.baseURI = ConfigReader.getBaseUrl();
		this.requestSpec = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON);
	}

	/**
	 * Get OAuth2 access token using Client Credentials flow
	 */
	public static String getAccessToken() {
		if (accessToken == null || isTokenExpired()) {
			log.info("Generating new access token...");

			String clientId = ConfigReader.getClientId();
			String clientSecret = ConfigReader.getClientSecret();
			String credentials = clientId + ":" + clientSecret;
			String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

			Map<String, String> formParams = new HashMap<>();
			formParams.put("grant_type", "client_credentials");

			Response response = RestAssured.given().baseUri(ConfigReader.getTokenUrl())
					.header("Authorization", "Basic " + encodedCredentials)
					.contentType("application/x-www-form-urlencoded").formParams(formParams).post("/api/token");

			if (response.statusCode() == 200) {
				accessToken = response.jsonPath().getString("access_token");
				log.info("Access token generated successfully");
			} else {
				log.error("Failed to generate access token. Status: {}, Response: {}", response.statusCode(),
						response.asString());
				throw new RuntimeException("Failed to authenticate with Spotify API");
			}
		}
		return accessToken;
	}

	/**
	 * Check if token is expired (simplified - in real scenario, track expiry time)
	 */
	private static boolean isTokenExpired() {
		// In production, you'd track token expiry time and check it here
		return false;
	}

	/**
	 * Get request specification with authentication
	 */
	public RequestSpecification getAuthenticatedRequest() {
		return RestAssured.given().header("Authorization", "Bearer " + getAccessToken()).contentType(ContentType.JSON)
				.accept(ContentType.JSON);
	}

	/**
	 * GET request
	 */
	public Response get(String endpoint) {
		log.info("Sending GET request to: {}", endpoint);
		Response response = getAuthenticatedRequest().get(endpoint);
		logResponse(response);
		return response;
	}

	/**
	 * GET request with path parameters
	 */
	public Response get(String endpoint, Map<String, String> pathParams) {
		log.info("Sending GET request to: {} with path params: {}", endpoint, pathParams);
		Response response = getAuthenticatedRequest().pathParams(pathParams).get(endpoint);
		logResponse(response);
		return response;
	}

	/**
	 * GET request with query parameters
	 */
	public Response getWithQueryParams(String endpoint, Map<String, Object> queryParams) {
		log.info("Sending GET request to: {} with query params: {}", endpoint, queryParams);
		Response response = getAuthenticatedRequest().queryParams(queryParams).get(endpoint);
		logResponse(response);
		return response;
	}

	/**
	 * POST request
	 */
	public Response post(String endpoint, Object body) {
		log.info("Sending POST request to: {}", endpoint);
		Response response = getAuthenticatedRequest().body(body).post(endpoint);
		logResponse(response);
		return response;
	}

	/**
	 * PUT request
	 */
	public Response put(String endpoint, Object body) {
		log.info("Sending PUT request to: {}", endpoint);
		Response response = getAuthenticatedRequest().body(body).put(endpoint);
		logResponse(response);
		return response;
	}

	/**
	 * DELETE request
	 */
	public Response delete(String endpoint) {
		log.info("Sending DELETE request to: {}", endpoint);
		Response response = getAuthenticatedRequest().delete(endpoint);
		logResponse(response);
		return response;
	}

	/**
	 * PATCH request
	 */
	public Response patch(String endpoint, Object body) {
		log.info("Sending PATCH request to: {}", endpoint);
		Response response = getAuthenticatedRequest().body(body).patch(endpoint);
		logResponse(response);
		return response;
	}

	/**
	 * Log response details
	 */
	private void logResponse(Response response) {
		log.info("Response Status: {}", response.statusCode());
		log.debug("Response Body: {}", response.asString());
	}

	/**
	 * Reset access token (useful for testing token refresh)
	 */
	public static void resetAccessToken() {
		accessToken = null;
	}
}