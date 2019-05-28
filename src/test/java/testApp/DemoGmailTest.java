package testApp;

import org.testng.annotations.Test;

import utils.BaseTest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class DemoGmailTest extends BaseTest {

// before class and after class will tear up and down browser
// which is already implemented in BaseTest.class
	@Test
	public void checkEmails() {
		// check you have new email or not
		// delete email which is older than 30 days
		// right test which ever you want
		// but it is good idea to write all methods
		// Inherit all elements from element and method classes
		// which chould be separatly created under page package as separate class
	}

	@BeforeMethod
	public void logInToGmail() {
		credential.logInGmail();
	}

	@AfterMethod
	public void logOutFromGmail() {
		credential.logOutGmail();
	}

}
