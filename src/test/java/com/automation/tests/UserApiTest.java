package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.config.EndpointConfig;
import com.automation.models.User;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class UserApiTest extends BaseTest {

	@Test(priority = 1, description = "Verify getting current user profile")
	public void testGetCurrentUserProfile() {
		log.info("Test: Get Current User Profile");

		Response response = apiClient.get(EndpointConfig.CURRENT_USER_PROFILE);

		// Validate status code
		assertEquals(response.statusCode(), 200, "Status code should be 200");

		// Validate response body using Hamcrest matchers
		response.then().body("id", notNullValue()).body("type", equalTo("user")).body("uri",
				startsWith("spotify:user:"));

		// Log response details
		log.info("User ID: {}", response.jsonPath().getString("id"));
		log.info("Display Name: {}", response.jsonPath().getString("display_name"));
		log.info("Email: {}", response.jsonPath().getString("email"));
		log.info("Country: {}", response.jsonPath().getString("country"));
	}

	@Test(priority = 2, description = "Verify user profile deserialization to POJO")
	public void testUserProfileDeserialization() {
		log.info("Test: Deserialize User Profile to POJO");

		Response response = apiClient.get(EndpointConfig.CURRENT_USER_PROFILE);

		// Deserialize to User POJO
		User user = response.as(User.class);

		// Validate POJO fields
		assertNotNull(user.getId(), "User ID should not be null");
		assertNotNull(user.getType(), "User type should not be null");
		assertEquals(user.getType(), "user", "Type should be 'user'");

		log.info("Deserialized User: {}", user);
	}

	@Test(priority = 3, description = "Validate user profile JSON schema")
	public void testUserProfileSchema() {
		log.info("Test: Validate User Profile Schema");

		Response response = apiClient.get(EndpointConfig.CURRENT_USER_PROFILE);

		// Validate against JSON schema
		response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));

		log.info("User profile matches expected JSON schema");
	}

	@Test(priority = 4, description = "Verify user profile response time")
	public void testUserProfileResponseTime() {
		log.info("Test: Validate Response Time");

		Response response = apiClient.get(EndpointConfig.CURRENT_USER_PROFILE);

		long responseTime = response.time();
		log.info("Response Time: {} ms", responseTime);

		// Assert response time is under 2 seconds
		assertTrue(responseTime < 2000, "Response time should be less than 2000ms, but was " + responseTime + "ms");
	}

	@Test(priority = 5, description = "Verify user profile headers")
	public void testUserProfileHeaders() {
		log.info("Test: Validate Response Headers");

		Response response = apiClient.get(EndpointConfig.CURRENT_USER_PROFILE);

		// Validate headers
		response.then().header("Content-Type", containsString("application/json")).header("cache-control",
				notNullValue());

		log.info("Content-Type: {}", response.header("Content-Type"));
	}

	@Test(priority = 6, description = "Verify getting user's top artists")
	public void testGetUserTopArtists() {
		log.info("Test: Get User's Top Artists");

		Map<String, String> pathParams = new HashMap<>();
		pathParams.put("type", "artists");

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("limit", 10);
		queryParams.put("time_range", "medium_term");

		Response response = apiClient.getAuthenticatedRequest().pathParams(pathParams).queryParams(queryParams)
				.get(EndpointConfig.USER_TOP_ITEMS);

		// Validate response
		assertEquals(response.statusCode(), 200, "Status code should be 200");
		response.then().body("items", notNullValue()).body("items.size()", greaterThan(0)).body("items[0].type",
				equalTo("artist"));

		log.info("Retrieved {} top artists", response.jsonPath().getList("items").size());
	}

	@Test(priority = 7, description = "Verify getting user's playlists")
	public void testGetUserPlaylists() {
		log.info("Test: Get User's Playlists");

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("limit", 20);

		Response response = apiClient.getWithQueryParams(EndpointConfig.USER_PLAYLISTS, queryParams);

		// Validate response
		assertEquals(response.statusCode(), 200, "Status code should be 200");
		response.then().body("items", notNullValue()).body("total", greaterThanOrEqualTo(0));

		int playlistCount = response.jsonPath().getInt("total");
		log.info("User has {} playlists", playlistCount);
	}

	@Test(priority = 8, description = "Verify unauthorized access without token")
	public void testUnauthorizedAccess() {
		log.info("Test: Unauthorized Access");

		// Make request without authentication
		Response response = apiClient.getAuthenticatedRequest().header("Authorization", "Bearer invalid_token")
				.get(EndpointConfig.CURRENT_USER_PROFILE);

		// Should return 401 Unauthorized
		assertEquals(response.statusCode(), 401, "Status code should be 401 for invalid token");

		log.info("Unauthorized access correctly returned 401");
	}

	@Test(priority = 9, description = "Verify user profile contains expected fields")
	public void testUserProfileFields() {
		log.info("Test: Validate User Profile Fields");

		Response response = apiClient.get(EndpointConfig.CURRENT_USER_PROFILE);

		// Validate all expected fields are present
		response.then().body("$", hasKey("id")).body("$", hasKey("display_name")).body("$", hasKey("email"))
				.body("$", hasKey("country")).body("$", hasKey("product")).body("$", hasKey("type"))
				.body("$", hasKey("uri")).body("$", hasKey("href")).body("$", hasKey("external_urls"))
				.body("$", hasKey("followers"));

		log.info("All expected fields are present in user profile");
	}

	@Test(priority = 10, description = "Verify followers object structure")
	public void testFollowersObject() {
		log.info("Test: Validate Followers Object");

		Response response = apiClient.get(EndpointConfig.CURRENT_USER_PROFILE);

		// Validate followers object
		response.then().body("followers", notNullValue()).body("followers.total", greaterThanOrEqualTo(0));

		int followersCount = response.jsonPath().getInt("followers.total");
		log.info("User has {} followers", followersCount);
	}
}