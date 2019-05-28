/**
 * @author nartan.vyas
 */

package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Credential extends BasePage {
	private final static String EMAIL = "username";
	private final static String PASSWORD = "password";

	public Credential(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
		PageFactory.initElements(driver, this);
	}

	// txt prefix is indicating text box tag
	@FindBy(id = "identifierId")
	public WebElement txtEmail;
	@FindBy(xpath = "//input[@type='password']")
	public WebElement txtPassword;
	// btn is indicating Button tag
	@FindBy(xpath = "//span[.='Next' and contains(@class,'snByac')]")
	public WebElement btnNext;
	// lbl prefix is for label or logo tag
	@FindBy(css = ".gb_xa.gbii")
	public WebElement lblNameIcon;
	// lnk prefix is for link tag
	@FindBy(linkText = "Sign out")
	public WebElement lnkLogout;

	/**
	 * Method to use to login as any Users
	 * 
	 * @param username
	 * @param password
	 */
	public void loginAs(String username, String password) {
		// clear text box before entering text to avoid errors
		txtEmail.clear();
		txtEmail.sendKeys(username);
		btnNext.click();
		txtPassword.clear();
		txtPassword.sendKeys(password);
		lnkLogout.click();
	}
	/**
	 * Method to use to logout as any Users
	 */
	public void logOutGmail() {
		waitTillElementIsClickable(lblNameIcon);
		lblNameIcon.click();
		try {
			waitTillelementIsDisplayedOrEnabled(lnkLogout);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lnkLogout.click();
	}

	/**
	 * login Login as SuperAdmin
	 */
	public void logInGmail() {
		loginAs(EMAIL, PASSWORD);
	}

}
