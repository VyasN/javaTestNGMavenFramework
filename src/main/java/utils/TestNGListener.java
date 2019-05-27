/**
 * @author nartan.vyas
 */

package utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.IClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.gurock.testrail.APIClient;
import com.gurock.testrail.APIException;

public class TestNGListener extends BaseTest implements ITestListener {
	private boolean testRailIntegration = false;

	// Test rail uri
	private APIClient client = new APIClient("https://domain.testrail.net");

	// Testrail authentication username and password
	private String testRun = System.getenv("TESTRAIL_TESTRUN");

	/**
	 * when test suite start, authenticate with test rail
	 */
	@Override
	public synchronized void onStart(ITestContext context) {
		testrailAuthentication();
	}

	/**
	 * when test suite ends, simply call end test method
	 */
	@Override
	public synchronized void onFinish(ITestContext context) {
		ExtentTestManager.endTest();
	}

	/**
	 * when start test, get method of test and from method get method name.
	 */
	@Override
	public synchronized void onTestStart(ITestResult result) {
		ExtentTestManager.startTest(result.getMethod().getMethodName(), "");
	}

	/**
	 * when test pass update test result in test rail.
	 */
	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		ExtentTestManager.getTest().pass("Test Passed.");
		updateTestrail(result, 1, "Test passed by automation.");
	}

	/**
	 * when test fails update test result in test rail.
	 */
	@Override
	public synchronized void onTestFailure(ITestResult result) {
		MediaEntityModelProvider mediaModel;

		try {
			mediaModel = MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build();
			ExtentTestManager.getTest().fail(result.getThrowable(), mediaModel);
			updateTestrail(result, 5, retrieveStacktrace(result));
		} catch (IOException e) {
			ExtentTestManager.getTest().fail(result.getThrowable());
		}
	}

	/**
	 * when test skips update test result in test rail.
	 */
	@Override
	public synchronized void onTestSkipped(ITestResult result) {
		if (ExtentTestManager.getTest() != null) {
			ExtentTestManager.getTest().skip("Skip");
		}
	}

	/**
	 * when test fails but check last 5 times result and update test result with max
	 * pass/fail result in test rail.
	 */
	@Override
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		updateTestrail(result, 5, retrieveStacktrace(result));

	}

	/**
	 * retrive stacktraces
	 * 
	 * @param result
	 * @return
	 */
	public String retrieveStacktrace(ITestResult result) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		result.getThrowable().printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * test rail authentication method
	 */
	public void testrailAuthentication() {
		String username = System.getenv("TESTRAIL_USERNAME");
		String password = System.getenv("TESTRAIL_PASSWORD");

		if (testRun != null && !username.isEmpty() && !password.isEmpty()) {
			client.setUser(username);
			client.setPassword(password);

			try {
				JSONObject c = (JSONObject) client.sendGet("get_run/" + testRun);
				testRailIntegration = true;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (APIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * update test rail result by following prameters
	 * 
	 * @param result
	 * @param statusID
	 * @param comment
	 */
	public void updateTestrail(ITestResult result, int statusID, String comment) {
		IClass obj = result.getTestClass();
		Class<?> newobj = obj.getRealClass();

		Method testMethod = null;
		try {
			testMethod = newobj.getMethod(result.getName());
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		TestAnnotation useAsTestName = testMethod.getAnnotation(TestAnnotation.class);
		// Get the TestCase ID for Test Rail
		String testID = null;

		try {
			testID = useAsTestName.testCaseID();
		} catch (NullPointerException ex) {
			// Annotation is not defined.
		}

		if (testID == null || testID.isEmpty()) {
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(new Exception("Testrail ID is missing."));
			ExtentTestManager.getTest().fail("Test failed, TestRail ID is missing in script.");
		}

		if (!testRailIntegration || !testMethod.isAnnotationPresent(TestAnnotation.class)) {
			return;
		}

		Map data = new HashMap();
		data.put("status_id", statusID);
		data.put("comment", comment);

		try {
			JSONObject c = (JSONObject) client.sendPost("add_result_for_case/" + testRun + "/" + testID, data);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
