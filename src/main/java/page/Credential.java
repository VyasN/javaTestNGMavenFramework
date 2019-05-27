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
	private final static String SUPERADMIN_EMAIL = "";
	private final static String PASSWORD = "password";

	public Credential(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "user_email")
	public WebElement txtUsername;
	@FindBy(id = "user_password")
	public WebElement txtPassword;
	@FindBy(name = "commit")
	public WebElement btnLogin;
	@FindBy(linkText = "Logout")
	public WebElement lnkLogout;

	/**
	 * Method to use to login as any Users
	 * 
	 * @param username
	 * @param password
	 */
	public void loginAs(String username, String password) {
		txtUsername.sendKeys(username);
		txtPassword.sendKeys(password);
		btnLogin.click();
	}

	/**
	 * login Login as SuperAdmin
	 */
	public void loginAsSuperadmin() {
		loginAs(SUPERADMIN_EMAIL, PASSWORD);
	}

}
