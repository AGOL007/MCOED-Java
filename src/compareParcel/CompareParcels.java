package compareParcel;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import seleniumActions.SeleniumActions;
import testBase.TestBase;
import validations.Validations;

public class CompareParcels extends TestBase{
	int excelRowcount = 0;
	/* Prerequisite before executing the test cases */
	@BeforeMethod
	public void beforeTest() throws Exception{
		excelRowcount++;
		// To initialize the browser and launch the application
		setup(null, excelRowcount, null); 

		// To enter user credentials in login window
		SeleniumActions.userLogin(null, excelRowcount, null);  

		//To close the splash screen
		SeleniumActions.closeSplashScreen(excelRowcount, null);

		//To close the alert window
		SeleniumActions.closeAlertWidnow(excelRowcount, null);
	}

	@Test
	public void compareParcel() throws Exception{
		// To open Compare Parcel panel
		SeleniumActions.openParcelPanel("esriCTHeaderComparison", excelRowcount, null);

		// To select the book marks to compare
		ArrayList<String> bookMarktextList = SeleniumActions.selectBookmarkToCompare(2, excelRowcount);

		// To validate the Compare table
		Assert.assertTrue(Validations.validateCompareTable(driver, bookMarktextList));
	}

	/* Close the browser */
	@AfterMethod
	public void afterTest(){
		TestBase.closeBrowser();
	}
}
