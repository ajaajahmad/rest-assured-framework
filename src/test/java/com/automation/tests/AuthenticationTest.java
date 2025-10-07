package com.automation.tests;

import com.automation.api.ApiClient;
import com.automation.base.BaseTest;
import com.automation.config.ConfigReader;
import com.automation.config.EndpointConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class AuthenticationTest extends BaseTest {

	@Test(priority = 1, description = "Verify successful OAuth2 token generation")
	public void testSuccessfulTokenGeneration() {
		log.info("Test: Verify OAuth2 Token Generation");

		String clientId = ConfigReader.getClientId();
		String clientSecret = ConfigReader.getClientSecret();
		String credentials = clientId + ":" + clientSecret;
		String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

		Map<String, String> formParams = new HashMap<>();
		formParams.put("grant_type", "client_credentials");

		Response response = RestAssured.given().baseUri(ConfigReader.getTokenUrl())
				.header("Authorization", "Basic " + encodedCredentials).contentType("application/x-www-form-urlencoded")
				.formParams(formParams).post("/api/token");

		// Validate response
		assertEquals(response.statusCode(), 200, "Token generation should return 200");

		response.then().body("access_token", notNullValue()).body("token_type", equalTo("Bearer")).body("expires_in",
				greaterThan(0));

		String accessToken = response.jsonPath().getString("access_token");
		assertNotNull(accessToken, "Access token should not be null");
		assertTrue(accessToken.length() > 0, "Access token should not be empty");

		log.info("Successfully generated access token");
	}

	@Test(priority = 2, description = "Verify token generation with invalid credentials")
	public void testInvalidCredentials() {
		log.info("Test: Token Generation with Invalid Credentials");

		String invalidCredentials = Base64.getEncoder().encodeToString("invalid:credentials".getBytes());

		Map<String, String> formParams = new HashMap<>();
		formParams.put("grant_type", "client_credentials");

		Response response = RestAssured.given().baseUri(ConfigReader.getTokenUrl())
				.header("Authorization", "Basic " + invalidCredentials).contentType("application/x-www-form-urlencoded")
				.formParams(formParams).post("/api/token");

		// Should return 401 Unauthorized
		assertEquals(response.statusCode(), 401, "Invalid credentials should return 401");

		response.then().body("error", notNullValue());

		log.info("Invalid credentials correctly rejected");
	}

	@Test(priority = 3, description = "Verify API call with valid token")
	public void testApiCallWithValidToken() {
		log.info("Test: API Call with Valid Token");

		String token = ApiClient.getAccessToken();
		assertNotNull(token, "Token should be generated");

		Response response = RestAssured.given().baseUri(ConfigReader.getBaseUrl())
				.header("Authorization", "Bearer " + token).contentType("application/json")
				.get(EndpointConfig.CURRENT_USER_PROFILE);

		// Should return 200 OK
		assertEquals(response.statusCode(), 200, "Valid token should allow API access");

		log.info("API call successful with valid token");
	}

	@Test(priority = 4, description = "Verify API call with invalid token")
	public void testApiCallWithInvalidToken() {
		log.info("Test: API Call with Invalid Token");

		String invalidToken = "invalid_token_12345";

		Response response = RestAssured.given().baseUri(ConfigReader.getBaseUrl())
				.header("Authorization", "Bearer " + invalidToken).contentType("application/json")
				.get(EndpointConfig.CURRENT_USER_PROFILE);

		// Should return 401 Unauthorized
		assertEquals(response.statusCode(), 401, "Invalid token should return 401");

		log.info("Invalid token correctly rejected");
	}

	@Test(priority = 5, description = "Verify API call without authorization header")
	public void testApiCallWithoutAuthHeader() {
		log.info("Test: API Call without Authorization Header");

		Response response = RestAssured.given().baseUri(ConfigReader.getBaseUrl()).contentType("application/json")
				.get(EndpointConfig.CURRENT_USER_PROFILE);

		// Should return 401 Unauthorized
		assertEquals(response.statusCode(), 401, "Missing auth header should return 401");

		log.info("Missing authorization header correctly rejected");
	}

	@Test(priority = 6, description = "Verify token expiration handling")
	public void testTokenRegeneration() {
		log.info("Test: Token Regeneration");

		// Reset token to force regeneration
		ApiClient.resetAccessToken();

		// Get new token
		String newToken = ApiClient.getAccessToken();
		assertNotNull(newToken, "New token should be generated");

		// Verify new token works
		Response response = apiClient.get(EndpointConfig.CURRENT_USER_PROFILE);
		assertEquals(response.statusCode(), 200, "New token should work");

		log.info("Token regeneration successful");
	}

	@Test(priority = 7, description = "Verify token format and structure")
	public void testTokenFormat() {
		log.info("Test: Verify Token Format");

		String token = ApiClient.getAccessToken();

		assertNotNull(token, "Token should not be null");
		assertTrue(token.length() > 100, "Token should have reasonable length");
		assertFalse(token.contains(" "), "Token should not contain spaces");

		log.info("Token format validation successful");
	}
}