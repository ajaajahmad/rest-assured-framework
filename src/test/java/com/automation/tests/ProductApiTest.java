package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.config.EndpointConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class ProductApiTest extends BaseTest {

	// Sample Spotify Track ID (Example: "The Weeknd - Blinding Lights")
	private static final String SAMPLE_TRACK_ID = "0VjIjW4GlUZAMYd2vXMi3b";

	// Sample Spotify Album ID (Example: "After Hours")
	private static final String SAMPLE_ALBUM_ID = "4yP0hdKOZPNshxUOjY0cZj";

	// Sample Spotify Artist ID (Example: "The Weeknd")
	private static final String SAMPLE_ARTIST_ID = "1Xyo4u8uXC1ZmMpatF05PJ";

	@Test(priority = 1, description = "Verify getting track details by ID")
	public void testGetTrackById() {
		log.info("Test: Get Track by ID");

		Map<String, String> pathParams = new HashMap<>();
		pathParams.put("id", SAMPLE_TRACK_ID);

		Response response = apiClient.get(EndpointConfig.TRACK, pathParams);

		// Validate response
		assertEquals(response.statusCode(), 200, "Status code should be 200");

		response.then().body("id", equalTo(SAMPLE_TRACK_ID)).body("type", equalTo("track")).body("name", notNullValue())
				.body("artists", notNullValue()).body("album", notNullValue()).body("duration_ms", greaterThan(0))
				.body("popularity", greaterThanOrEqualTo(0));

		String trackName = response.jsonPath().getString("name");
		log.info("Track Name: {}", trackName);
	}

	@Test(priority = 2, description = "Verify getting multiple tracks")
	public void testGetMultipleTracks() {
		log.info("Test: Get Multiple Tracks");

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("ids", SAMPLE_TRACK_ID + ",3n3Ppam7vgaVa1iaRUc9Lp");

		Response response = apiClient.getWithQueryParams(EndpointConfig.TRACKS, queryParams);

		// Validate response
		assertEquals(response.statusCode(), 200, "Status code should be 200");

		response.then().body("tracks", notNullValue()).body("tracks.size()", greaterThan(0)).body("tracks[0].type",
				equalTo("track"));

		int trackCount = response.jsonPath().getList("tracks").size();
		log.info("Retrieved {} tracks", trackCount);
	}

	@Test(priority = 3, description = "Verify getting track audio features")
	public void testGetTrackAudioFeatures() {
		log.info("Test: Get Track Audio Features");

		Map<String, String> pathParams = new HashMap<>();
		pathParams.put("id", SAMPLE_TRACK_ID);

		Response response = apiClient.get(EndpointConfig.TRACK_AUDIO_FEATURES, pathParams);

		// Validate response
		assertEquals(response.statusCode(), 200, "Status code should be 200");

		response.then().body("id", equalTo(SAMPLE_TRACK_ID)).body("danceability", notNullValue())
				.body("energy", notNullValue()).body("key", notNullValue()).body("loudness", notNullValue())
				.body("mode", notNullValue()).body("speechiness", notNullValue()).body("acousticness", notNullValue())
				.body("instrumentalness", notNullValue()).body("liveness", notNullValue())
				.body("valence", notNullValue()).body("tempo", greaterThan(0f));

		log.info("Danceability: {}", response.jsonPath().getFloat("danceability"));
		log.info("Energy: {}", response.jsonPath().getFloat("energy"));
	}

	@Test(priority = 4, description = "Verify getting album details by ID")
	public void testGetAlbumById() {
		log.info("Test: Get Album by ID");

		Map<String, String> pathParams = new HashMap<>();
		pathParams.put("id", SAMPLE_ALBUM_ID);

		Response response = apiClient.get(EndpointConfig.ALBUM, pathParams);

		// Validate response
		assertEquals(response.statusCode(), 200, "Status code should be 200");

		response.then().body("id", equalTo(SAMPLE_ALBUM_ID)).body("type", equalTo("album")).body("name", notNullValue())
				.body("artists", notNullValue()).body("tracks", notNullValue()).body("release_date", notNullValue())
				.body("total_tracks", greaterThan(0));

		String albumName = response.jsonPath().getString("name");
		int totalTracks = response.jsonPath().getInt("total_tracks");
		log.info("Album: {}, Total Tracks: {}", albumName, totalTracks);
	}

	@Test(priority = 5, description = "Verify getting album tracks")
	public void testGetAlbumTracks() {
		log.info("Test: Get Album Tracks");

		Map<String, String> pathParams = new HashMap<>();
		pathParams.put("id", SAMPLE_ALBUM_ID);

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("limit", 10);

		Response response = apiClient.getAuthenticatedRequest().pathParams(pathParams).queryParams(queryParams)
				.get(EndpointConfig.ALBUM_TRACKS);

		// Validate response
		assertEquals(response.statusCode(), 200, "Status code should be 200");

		response.then().body("items", notNullValue()).body("items.size()", greaterThan(0))
				.body("items[0].type", equalTo("track")).body("total", greaterThan(0));

		log.info("Album contains {} tracks", response.jsonPath().getInt("total"));
	}

	@Test(priority = 6, description = "Verify getting artist details by ID")
	public void testGetArtistById() {
		log.info("Test: Get Artist by ID");

		Map<String, String> pathParams = new HashMap<>();
		pathParams.put("id", SAMPLE_ARTIST_ID);

		Response response = apiClient.get(EndpointConfig.ARTIST, pathParams);

		// Validate response
		assertEquals(response.statusCode(), 200, "Status code should be 200");

		response.then().body("id", equalTo(SAMPLE_ARTIST_ID)).body("type", equalTo("artist"))
				.body("name", notNullValue()).body("genres", notNullValue()).body("popularity", greaterThanOrEqualTo(0))
				.body("followers.total", greaterThanOrEqualTo(0));

		String artistName = response.jsonPath().getString("name");
		int followers = response.jsonPath().getInt("followers.total");
		log.info("Artist: {}, Followers: {}", artistName, followers);
	}

	@Test(priority = 7, description = "Verify getting artist albums")
	public void testGetArtistAlbums() {
		log.info("Test: Get Artist Albums");

		Map<String, String> pathParams = new HashMap<>();
		pathParams.put("id", SAMPLE_ARTIST_ID);

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("limit", 20);
		queryParams.put("include_groups", "album,single");

		Response response = apiClient.getAuthenticatedRequest().pathParams(pathParams).queryParams(queryParams)
				.get(EndpointConfig.ARTIST_ALBUMS);

		// Validate response
		assertEquals(response.statusCode(), 200, "Status code should be 200");

		response.then().body("items", notNullValue()).body("items.size()", greaterThan(0)).body("total",
				greaterThan(0));

		log.info("Artist has {} albums", response.jsonPath().getInt("total"));
	}

	@Test(priority = 8, description = "Verify getting artist top tracks")
	public void testGetArtistTopTracks() {
		log.info("Test: Get Artist Top Tracks");

		Map<String, String> pathParams = new HashMap<>();
		pathParams.put("id", SAMPLE_ARTIST_ID);

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("market", "US");

		Response response = apiClient.getAuthenticatedRequest().pathParams(pathParams).queryParams(queryParams)
				.get(EndpointConfig.ARTIST_TOP_TRACKS);

		// Validate response
		assertEquals(response.statusCode(), 200, "Status code should be 200");

		response.then().body("tracks", notNullValue()).body("tracks.size()", greaterThan(0)).body("tracks[0].type",
				equalTo("track"));

		log.info("Retrieved {} top tracks", response.jsonPath().getList("tracks").size());
	}

	@Test(priority = 9, description = "Verify search functionality")
	public void testSearchTracks() {
		log.info("Test: Search Tracks");

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("q", "the weeknd");
		queryParams.put("type", "track");
		queryParams.put("limit", 10);

		Response response = apiClient.getWithQueryParams(EndpointConfig.SEARCH, queryParams);

		// Validate response
		assertEquals(response.statusCode(), 200, "Status code should be 200");

		response.then().body("tracks", notNullValue()).body("tracks.items", notNullValue()).body("tracks.items.size()",
				greaterThan(0));

		log.info("Search returned {} results", response.jsonPath().getList("tracks.items").size());
	}

	@Test(priority = 10, description = "Verify invalid track ID handling")
	public void testGetTrackWithInvalidId() {
		log.info("Test: Get Track with Invalid ID");

		Map<String, String> pathParams = new HashMap<>();
		pathParams.put("id", "invalid_track_id_12345");

		Response response = apiClient.get(EndpointConfig.TRACK, pathParams);

		// Should return 400 Bad Request
		assertEquals(response.statusCode(), 400, "Invalid track ID should return 400");

		log.info("Invalid track ID correctly rejected");
	}
}