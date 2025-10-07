package com.automation.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

	private static final Logger log = LoggerFactory.getLogger(ConfigReader.class);
	private static Properties properties;
	private static String environment;

	static {
		environment = System.getProperty("env", "qa");
		loadProperties();
	}

	private static void loadProperties() {
		properties = new Properties();
		String configFile = String.format("src/test/resources/configs/%s.properties", environment);

		try (InputStream input = new FileInputStream(configFile)) {
			properties.load(input);
			log.info("Loaded configuration for environment: {}", environment);
		} catch (IOException e) {
			log.error("Failed to load configuration file: {}", configFile, e);
			throw new RuntimeException("Configuration file not found: " + configFile);
		}
	}

	public static String getProperty(String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			log.warn("Property '{}' not found in configuration", key);
		}
		return value;
	}

	public static String getBaseUrl() {
		return getProperty("base.url");
	}

	public static String getClientId() {
		return getProperty("client.id");
	}

	public static String getClientSecret() {
		return getProperty("client.secret");
	}

	public static String getTokenUrl() {
		return getProperty("token.url");
	}

	public static int getTimeout() {
		return Integer.parseInt(getProperty("timeout"));
	}

	public static String getEnvironment() {
		return environment;
	}

	public static void reloadProperties() {
		loadProperties();
	}
}