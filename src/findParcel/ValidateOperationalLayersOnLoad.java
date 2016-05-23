package findParcel;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import commonUtilities.CommonUtility;

import createLog.CreateLog;

import seleniumActions.SeleniumActions;
import testBase.TestBase;
import validations.Validations;

public class ValidateOperationalLayersOnLoad extends TestBase {
	int excelRowcount = 0;
	/* Prerequisite before executing the test cases */
	@BeforeMethod
	public void beforeTest() throws Exception{
		excelRowcount++;

		// To initialize the browser and launch the application
		Assert.assertTrue(setup(null, excelRowcount, null)); 

		// To enter user credentials in login window
		Assert.assertTrue(SeleniumActions.userLogin(null, excelRowcount, null));  

		//To close the splash screen
		Assert.assertTrue(SeleniumActions.closeSplashScreen(excelRowcount, null));

		//To close the alert window
		Assert.assertTrue(SeleniumActions.closeAlertWidnow(excelRowcount, null));

	}

	// Test case 1
	//  To validate layers present at initial extent of map 
	@Test
	public void validateOperationalLayers() throws Exception{
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;  //to read the javaScript file
			Long operationalLayersCount = (Long) (js.executeScript("return appGlobals.configData.OperationalLayers.length"));

			// to check the layer names with g tag id
			Assert.assertTrue(Validations.validateOperationalLayersOnLoad(driver, js, operationalLayersCount));
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}
	}

	/* Close the browser */
	@AfterMethod
	public void afterTest(){
		TestBase.closeBrowser();
	}

}
