package com.automation.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportListener implements ITestListener {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	private static String reportPath;

	@Override
	public void onStart(ITestContext context) {
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		reportPath = "reports/ExtentReport_" + timestamp + ".html";

		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

		// Configure report
		sparkReporter.config().setDocumentTitle("API Automation Test Report");
		sparkReporter.config().setReportName("Spotify API Test Results");
		sparkReporter.config().setTheme(Theme.STANDARD);
		sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
		sparkReporter.config().setEncoding("utf-8");

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		// System information
		extent.setSystemInfo("Application", "Spotify API");
		extent.setSystemInfo("Environment", System.getProperty("env", "QA"));
		extent.setSystemInfo("User", System.getProperty("user.name"));
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
	}

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest test = extent.createTest(result.getMethod().getMethodName());
		test.assignCategory(result.getTestClass().getRealClass().getSimpleName());

		String description = result.getMethod().getDescription();
		if (description != null && !description.isEmpty()) {
			test.info(description);
		}

		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS,
				MarkupHelper.createLabel("Test Passed: " + result.getMethod().getMethodName(), ExtentColor.GREEN));
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extentTest.get().log(Status.FAIL,
				MarkupHelper.createLabel("Test Failed: " + result.getMethod().getMethodName(), ExtentColor.RED));

		extentTest.get().fail(result.getThrowable());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.get().log(Status.SKIP,
				MarkupHelper.createLabel("Test Skipped: " + result.getMethod().getMethodName(), ExtentColor.YELLOW));

		extentTest.get().skip(result.getThrowable());
	}

	@Override
	public void onFinish(ITestContext context) {
		if (extent != null) {
			extent.flush();
		}

		System.out.println("===============================================");
		System.out.println("Extent Report generated at: " + new File(reportPath).getAbsolutePath());
		System.out.println("===============================================");
	}

	/**
	 * Get current extent test instance
	 */
	public static ExtentTest getTest() {
		return extentTest.get();
	}

	/**
	 * Log info message to report
	 */
	public static void logInfo(String message) {
		if (extentTest.get() != null) {
			extentTest.get().info(message);
		}
	}

	/**
	 * Log pass message to report
	 */
	public static void logPass(String message) {
		if (extentTest.get() != null) {
			extentTest.get().pass(message);
		}
	}

	/**
	 * Log fail message to report
	 */
	public static void logFail(String message) {
		if (extentTest.get() != null) {
			extentTest.get().fail(message);
		}
	}

	/**
	 * Log warning message to report
	 */
	public static void logWarning(String message) {
		if (extentTest.get() != null) {
			extentTest.get().warning(message);
		}
	}
}