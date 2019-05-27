package utils;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utils.ExtentManager;
/**
 * test rail authentication methods for extent test manger 
 * @author nartan.vyas
 *
 */
public class ExtentTestManager extends BaseTest {
	private static ExtentReports extent = ExtentManager.createInstance(resultPath + "/extent.html");
	private static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public static synchronized ExtentTest startTest(String testName, String desc) {
		ExtentTest test = extent.createTest(testName, desc);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
		return test;
	}

	public static synchronized void endTest() {
		extent.flush();

	}
}
