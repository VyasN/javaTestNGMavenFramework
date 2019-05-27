/**
 * @author nartan.vyas
 */

package utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import page.Credential;


public class BaseTest{
	private static ThreadLocal<WebDriver> threadSafeDriver = new ThreadLocal<WebDriver>();
	
	public static String resultPath = Paths.get(System.getProperty("user.dir"), "results").toString();
	
	// If you are using vm please pass ip of vm instead localhost
	public String url = System.getenv("URL") != null ? System.getenv("URL") : "http://localhost:3000";
	public WebDriver driver;
	public WebDriverWait wait;
	public Credential credential;

	public String currentTime() {
		return Long.toString(System.currentTimeMillis());
	}

	/**
	 * @return the {WebDriver} for the current thread
	 */
	public synchronized static WebDriver getDriver() {
		return threadSafeDriver.get();
	}

	/**
	 * Creates a webdriver that is thread safe
	 * 
	 * @return getDriver()
	 */
	public WebDriver createDriver() {
		String grid = System.getenv("GRID");
		if (grid != null) {
			try {
				//selenium grid url
				URL node = new URL("http://selenium.domain.com/wd/hub");
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setBrowserName("chrome");
				cap.setPlatform(Platform.LINUX);
				threadSafeDriver.set(new RemoteWebDriver(node, cap));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else {
			threadSafeDriver.set(new ChromeDriver());
		}

		return getDriver();
	}

	/**
	 * Kill the browser if exist
	 */
	public void quitBrowser() {
		if (getDriver() != null) {
			getDriver().quit();
		} else {
			driver.quit();
		}
	}

	/**
	 * Take screenshot without providing a filename. Uses current time as filename
	 * 
	 * @return the location of the screenshot
	 */
	public static String takeScreenshot() {
		return takeScreenshot(Long.toString(System.currentTimeMillis()));
	}

	/**
	 * Take screenshot
	 * 
	 * @param filename filename you wanted for the screenshot
	 * @return the location of the screenshot
	 */
	public static String takeScreenshot(String filename) {
		File destFile = Paths.get(resultPath, filename + ".png").toFile();

		try {
			File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {

		}

		return destFile.toString();
	}

	@BeforeClass
	public void callDriver() {
		driver = createDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.MINUTES);
		driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.MINUTES);
		driver.manage().timeouts().setScriptTimeout(3, TimeUnit.MINUTES);
		wait = new WebDriverWait(driver, 30);
		credential = new Credential(driver, wait);
		driver.navigate().to(url);
	}

	@AfterClass(alwaysRun = true)
	public void quitDriver() {
		quitBrowser();
	}

}
