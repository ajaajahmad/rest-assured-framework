package com.automation.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

	private static final Logger log = LoggerFactory.getLogger(TestListener.class);
	private long startTime;

	@Override
	public void onStart(ITestContext context) {
		startTime = System.currentTimeMillis();
		log.info("========================================");
		log.info("Test Suite Started: {}", context.getName());
		log.info("========================================");
	}

	@Override
	public void onFinish(ITestContext context) {
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime) / 1000;

		log.info("========================================");
		log.info("Test Suite Finished: {}", context.getName());
		log.info("Total Tests Run: {}", context.getAllTestMethods().length);
		log.info("Tests Passed: {}", context.getPassedTests().size());
		log.info("Tests Failed: {}", context.getFailedTests().size());
		log.info("Tests Skipped: {}", context.getSkippedTests().size());
		log.info("Total Duration: {} seconds", duration);
		log.info("========================================");
	}

	@Override
	public void onTestStart(ITestResult result) {
		log.info("------------------------------------------");
		log.info("Starting Test: {}", result.getMethod().getMethodName());
		log.info("Description: {}", result.getMethod().getDescription());
		log.info("------------------------------------------");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		long duration = result.getEndMillis() - result.getStartMillis();
		log.info("✓ Test PASSED: {} (Duration: {} ms)", result.getMethod().getMethodName(), duration);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		long duration = result.getEndMillis() - result.getStartMillis();
		log.error("✗ Test FAILED: {} (Duration: {} ms)", result.getMethod().getMethodName(), duration);
		log.error("Failure Reason: {}", result.getThrowable().getMessage());

		// Print stack trace for debugging
		if (log.isDebugEnabled()) {
			log.debug("Stack Trace:", result.getThrowable());
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		log.warn("⊘ Test SKIPPED: {}", result.getMethod().getMethodName());
		if (result.getThrowable() != null) {
			log.warn("Skip Reason: {}", result.getThrowable().getMessage());
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		log.warn("Test Failed but within success percentage: {}", result.getMethod().getMethodName());
	}
}