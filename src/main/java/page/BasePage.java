/**
 * @author nartan.vyas
 */

package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	public WebDriver driver;
	public Actions mouseHover;
	public WebDriverWait wait;

	public BasePage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.mouseHover = new Actions(driver);
		PageFactory.initElements(driver, this);
	}

	/**
	 * Select options from drop down by visible text
	 * 
	 * @param visibleTextAsOption
	 */
	public void selectDDOptionByVisibleText(WebElement element, String visibleTextAsOption) {
		Select select = new Select(element);
		select.selectByVisibleText(visibleTextAsOption);
	}

	/**
	 * Select options from drop down by value
	 * 
	 * @param element
	 * @param value
	 */
	public void selectDDOptionByValue(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);
	}

	/**
	 * Select options from drop down by index
	 * 
	 * @param element
	 * @param indexNumber
	 */
	public void selectDDOptionByIndex(WebElement element, int indexNumber) {
		Select select = new Select(element);
		select.selectByIndex(indexNumber);
	}

	/**
	 * Wait for element to be present/visible
	 * 
	 * @param element WebElement
	 * @throws InterruptedException
	 */
	public void waitTillElementIsVisible(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Wait for element to be click-able
	 * 
	 * @param element WebElement
	 * @throws InterruptedException
	 */
	public void waitTillElementIsClickable(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Check element is disaplay or enabled if yes then return else wait for 500
	 * milliseconds, again it will check as above.
	 * 
	 * @param element
	 * @throws InterruptedException
	 */
	public void waitTillelementIsDisplayedOrEnabled(WebElement element) throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			Thread.sleep(500);
			if (element.isDisplayed() || element.isEnabled()) {
				break;
			}
		}

	}

	/**
	 * Find an element by link text present on UI
	 * 
	 * @param lnkTxt
	 * @return element
	 */
	public WebElement getEleOfLinkText(String lnkTxt) {
		String query = "//a[contains(text(),'" + lnkTxt + "')]";
		WebElement ele;
		ele = driver.findElement(By.xpath(query));
		return ele;
	}
}
