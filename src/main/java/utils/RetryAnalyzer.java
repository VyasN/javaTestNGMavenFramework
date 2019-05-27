/**
 * @author nartan.vyas
 */

package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer extends BaseTest implements IRetryAnalyzer {

	private int count = 0;
	private int maxCount = 1;

	@Override
	public boolean retry(ITestResult result) {
		if (count < maxCount) {
			getDriver().get(url);
			count++;
			return true;
		}

		return false;
	}
}
