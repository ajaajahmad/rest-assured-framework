package com.automation.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProviderUtil {

	private static final Logger log = LoggerFactory.getLogger(DataProviderUtil.class);

	/**
	 * Data provider for reading CSV files
	 */
	@DataProvider(name = "csvDataProvider")
	public static Iterator<Object[]> csvDataProvider() {
		return readCSV("src/test/resources/testdata/users.csv");
	}

	/**
	 * Read CSV file and return data
	 */
	public static Iterator<Object[]> readCSV(String filePath) {
		List<Object[]> data = new ArrayList<>();

		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			List<String[]> allRows = reader.readAll();

			// Skip header row
			for (int i = 1; i < allRows.size(); i++) {
				data.add(allRows.get(i));
			}

			log.info("Successfully loaded {} rows from CSV: {}", data.size(), filePath);
		} catch (IOException | CsvException e) {
			log.error("Failed to read CSV file: {}", filePath, e);
		}

		return data.iterator();
	}

	/**
	 * Data provider for reading JSON files
	 */
	@DataProvider(name = "jsonDataProvider")
	public static Object[][] jsonDataProvider() {
		String filePath = "src/test/resources/testdata/users.json";
		return readJSON(filePath);
	}

	/**
	 * Read JSON file and return data
	 */
	public static Object[][] readJSON(String filePath) {
		try {
			String jsonContent = JsonUtil.readJsonFileAsString(filePath);

			// Parse JSON array
			List<Object> dataList = JsonUtil.fromJson(jsonContent, List.class);

			Object[][] data = new Object[dataList.size()][1];
			for (int i = 0; i < dataList.size(); i++) {
				data[i][0] = dataList.get(i);
			}

			log.info("Successfully loaded {} rows from JSON: {}", data.length, filePath);
			return data;

		} catch (Exception e) {
			log.error("Failed to read JSON file: {}", filePath, e);
			return new Object[0][0];
		}
	}

	/**
	 * Custom data provider for user types
	 */
	@DataProvider(name = "userTypes")
	public static Object[][] userTypes() {
		return new Object[][] { { "premium" }, { "free" }, { "open" } };
	}

	/**
	 * Custom data provider for time ranges
	 */
	@DataProvider(name = "timeRanges")
	public static Object[][] timeRanges() {
		return new Object[][] { { "short_term", "Last 4 weeks" }, { "medium_term", "Last 6 months" },
				{ "long_term", "All time" } };
	}

	/**
	 * Custom data provider for market codes
	 */
	@DataProvider(name = "marketCodes")
	public static Object[][] marketCodes() {
		return new Object[][] { { "US" }, { "GB" }, { "IN" }, { "CA" }, { "AU" } };
	}

	/**
	 * Custom data provider for limit values
	 */
	@DataProvider(name = "limitValues")
	public static Object[][] limitValues() {
		return new Object[][] { { 1 }, { 10 }, { 20 }, { 50 } };
	}

	/**
	 * Data provider for invalid authentication scenarios
	 */
	@DataProvider(name = "invalidAuth")
	public static Object[][] invalidAuth() {
		return new Object[][] { { "", "Empty token" }, { "invalid_token", "Invalid token" },
				{ "Bearer invalid", "Malformed token" }, { null, "Null token" } };
	}

	/**
	 * Data provider for HTTP status codes
	 */
	@DataProvider(name = "httpStatusCodes")
	public static Object[][] httpStatusCodes() {
		return new Object[][] { { 200, "OK" }, { 201, "Created" }, { 204, "No Content" }, { 400, "Bad Request" },
				{ 401, "Unauthorized" }, { 403, "Forbidden" }, { 404, "Not Found" }, { 429, "Too Many Requests" },
				{ 500, "Internal Server Error" } };
	}

	/**
	 * Generic method to read CSV with custom delimiter
	 */
	public static Iterator<Object[]> readCSVWithDelimiter(String filePath, char delimiter) {
		List<Object[]> data = new ArrayList<>();

		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			List<String[]> allRows = reader.readAll();

			for (int i = 1; i < allRows.size(); i++) {
				data.add(allRows.get(i));
			}

			log.info("Successfully loaded {} rows from CSV: {}", data.size(), filePath);
		} catch (IOException | CsvException e) {
			log.error("Failed to read CSV file: {}", filePath, e);
		}

		return data.iterator();
	}

	/**
	 * Get specific column from CSV
	 */
	public static List<String> getColumnFromCSV(String filePath, int columnIndex) {
		List<String> columnData = new ArrayList<>();

		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			List<String[]> allRows = reader.readAll();

			for (int i = 1; i < allRows.size(); i++) {
				if (allRows.get(i).length > columnIndex) {
					columnData.add(allRows.get(i)[columnIndex]);
				}
			}

			log.info("Successfully extracted column {} with {} values", columnIndex, columnData.size());
		} catch (IOException | CsvException e) {
			log.error("Failed to read CSV column: {}", filePath, e);
		}

		return columnData;
	}
}