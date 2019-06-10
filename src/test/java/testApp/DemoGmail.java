package testApp;

import org.testng.annotations.Test;

import utils.BaseTest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterMethod;

public class DemoGmail extends BaseTest {

	// before class and after class will tear up and down browser
	// which is already implemented in BaseTest.class
	@Test
	public void checkEmails() {
		// Check you have new email or not
		// Wright test which ever you want
		// As per the best practises to write all methods
		// Inherit all elements from element and method classes
		// Which chould be separatly created under page package as separate class
	}

	@Test
	public void deleteEmail() {
		// Delete email which is older than 30 days
		// Wright test which ever you want
		// As per the best practises to write all methods
		// Inherit all elements from element and method classes
		// Which chould be separatly created under page package as separate class
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
