package com.automation.base;

import com.automation.api.ApiClient;
import com.automation.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.lang.reflect.Method;

public class BaseTest {

	protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);
	protected ApiClient apiClient;

	@BeforeSuite
	public void setupSuite() {
		log.info("========================================");
		log.info("Starting Test Suite Execution");
		log.info("Environment: {}", ConfigReader.getEnvironment());
		log.info("Base URL: {}", ConfigReader.getBaseUrl());
		log.info("========================================");

		// Create necessary directories
		createDirectories();

		// Configure RestAssured
		configureRestAssured();
	}

	@BeforeClass
	public void setupClass() {
		log.info("Initializing test class: {}", this.getClass().getSimpleName());
		apiClient = new ApiClient();

		// Get access token once per test class
		try {
			ApiClient.getAccessToken();
			log.info("Successfully authenticated with Spotify API");
		} catch (Exception e) {
			log.error("Failed to authenticate with Spotify API", e);
			throw new RuntimeException("Authentication failed", e);
		}
	}

	@BeforeMethod
	public void setupMethod(Method method) {
		log.info("========================================");
		log.info("Starting Test: {}", method.getName());
		log.info("========================================");
	}

	/**
	 * Configure RestAssured global settings
	 */
	private void configureRestAssured() {
		RestAssured.baseURI = ConfigReader.getBaseUrl();
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		// Add filters for logging (optional - can be toggled)
		if (isDebugMode()) {
			RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
		}
	}

	/**
	 * Create necessary directories for reports and logs
	 */
	private void createDirectories() {
		String[] directories = { "logs", "reports", "target" };

		for (String dir : directories) {
			File directory = new File(dir);
			if (!directory.exists()) {
				if (directory.mkdirs()) {
					log.info("Created directory: {}", dir);
				}
			}
		}
	}

	/**
	 * Check if debug mode is enabled
	 */
	private boolean isDebugMode() {
		return Boolean.parseBoolean(System.getProperty("debug", "false"));
	}

	/**
	 * Get API client instance
	 */
	protected ApiClient getApiClient() {
		return apiClient;
	}

	/**
	 * Sleep for specified milliseconds
	 */
	protected void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.warn("Sleep interrupted", e);
		}
	}
}