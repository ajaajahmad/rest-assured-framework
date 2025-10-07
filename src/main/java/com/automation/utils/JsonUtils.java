package com.automation.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtils {

	private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	/**
	 * Convert object to JSON string
	 */
	public static String toJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error("Failed to convert object to JSON", e);
			throw new RuntimeException("JSON conversion failed", e);
		}
	}

	/**
	 * Convert JSON string to object
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			log.error("Failed to convert JSON to object", e);
			throw new RuntimeException("JSON deserialization failed", e);
		}
	}

	/**
	 * Read JSON from file
	 */
	public static <T> T readJsonFromFile(String filePath, Class<T> clazz) {
		try {
			return objectMapper.readValue(new File(filePath), clazz);
		} catch (IOException e) {
			log.error("Failed to read JSON from file: {}", filePath, e);
			throw new RuntimeException("Failed to read JSON file", e);
		}
	}

	/**
	 * Write object to JSON file
	 */
	public static void writeJsonToFile(Object object, String filePath) {
		try {
			objectMapper.writeValue(new File(filePath), object);
			log.info("Successfully wrote JSON to file: {}", filePath);
		} catch (IOException e) {
			log.error("Failed to write JSON to file: {}", filePath, e);
			throw new RuntimeException("Failed to write JSON file", e);
		}
	}

	/**
	 * Pretty print JSON string
	 */
	public static String prettyPrint(String json) {
		try {
			Object jsonObject = objectMapper.readValue(json, Object.class);
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
		} catch (JsonProcessingException e) {
			log.error("Failed to pretty print JSON", e);
			return json;
		}
	}

	/**
	 * Get JSON node from string
	 */
	public static JsonNode getJsonNode(String json) {
		try {
			return objectMapper.readTree(json);
		} catch (JsonProcessingException e) {
			log.error("Failed to parse JSON", e);
			throw new RuntimeException("JSON parsing failed", e);
		}
	}

	/**
	 * Read JSON file as string
	 */
	public static String readJsonFileAsString(String filePath) {
		try {
			return new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			log.error("Failed to read file: {}", filePath, e);
			throw new RuntimeException("Failed to read file", e);
		}
	}

	/**
	 * Validate JSON string
	 */
	public static boolean isValidJson(String json) {
		try {
			objectMapper.readTree(json);
			return true;
		} catch (JsonProcessingException e) {
			return false;
		}
	}

	/**
	 * Get ObjectMapper instance
	 */
	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}
}