package com.automation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

	// Common date formats
	public static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";

	private DateUtil() {
		// Private constructor to prevent instantiation
	}

	/**
	 * Get current date in specified format
	 */
	public static String getCurrentDate(String format) {
		return LocalDate.now().format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * Get current date in default format (yyyy-MM-dd)
	 */
	public static String getCurrentDate() {
		return getCurrentDate(DATE_FORMAT);
	}

	/**
	 * Get current date-time in specified format
	 */
	public static String getCurrentDateTime(String format) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * Get current date-time in default format
	 */
	public static String getCurrentDateTime() {
		return getCurrentDateTime(DATE_TIME_FORMAT);
	}

	/**
	 * Get current timestamp for file naming
	 */
	public static String getTimestamp() {
		return getCurrentDateTime(TIMESTAMP_FORMAT);
	}

	/**
	 * Format date to ISO 8601 format
	 */
	public static String formatToISO8601(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(ISO_8601_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(date);
	}

	/**
	 * Parse ISO 8601 date string to Date
	 */
	public static Date parseISO8601(String dateString) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(ISO_8601_FORMAT);
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			return sdf.parse(dateString);
		} catch (ParseException e) {
			log.error("Failed to parse ISO 8601 date: {}", dateString, e);
			return null;
		}
	}

	/**
	 * Format date with custom pattern
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * Parse date string with custom pattern
	 */
	public static Date parseDate(String dateString, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(dateString);
		} catch (ParseException e) {
			log.error("Failed to parse date: {} with pattern: {}", dateString, pattern, e);
			return null;
		}
	}

	/**
	 * Add days to current date
	 */
	public static String addDays(int days, String format) {
		return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * Subtract days from current date
	 */
	public static String subtractDays(int days, String format) {
		return LocalDate.now().minusDays(days).format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * Convert LocalDateTime to Date
	 */
	public static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Convert Date to LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	/**
	 * Get epoch time in milliseconds
	 */
	public static long getEpochTime() {
		return System.currentTimeMillis();
	}

	/**
	 * Get epoch time in seconds
	 */
	public static long getEpochTimeInSeconds() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * Check if date string is valid for given format
	 */
	public static boolean isValidDate(String dateString, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setLenient(false);
			sdf.parse(dateString);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}