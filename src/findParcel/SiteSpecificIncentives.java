package findParcel;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import seleniumActions.SeleniumActions;
import testBase.TestBase;
import validations.Validations;

import commonUtilities.CommonUtility;
import createLog.CreateLog;

public class SiteSpecificIncentives extends TestBase {
	String filepath = "E:\\Selenium scripts\\MCOEDTest\\excelData";
	String filename = "McoedData.xlsx";
	String sheetName = "SiteSpecificIncentives";
	int excelRowcount = 0;
	
	@BeforeMethod
	public void beforeTest() throws Exception{
		excelRowcount++;

		// To initialize the browser and launch the application
		Assert.assertTrue(setup(null, excelRowcount, sheetName)); 

		// To enter user credentials in login window
		Assert.assertTrue(SeleniumActions.userLogin(null, excelRowcount, sheetName));  

		//To close the splash screen
		Assert.assertTrue(SeleniumActions.closeSplashScreen(excelRowcount, sheetName));

		//To close the alert window
		Assert.assertTrue(SeleniumActions.closeAlertWidnow(excelRowcount, sheetName));
	}

	@DataProvider(name="searchProvider")
	public String[][] getTestData() throws Exception {
		String [][] retObjArr = null;
		try {
			//Object[][] retObjArr = getExcelData(sheetPath, "Sheet1", SheetName);
			retObjArr = CommonUtility.getExcelData(filepath, filename, sheetName);
			System.out.println("getTestData function executed!!");
			//return retObjArr;
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}
		return retObjArr;
	}

	//Test case 3
	// To validate that Site specific Incentives layers are present on map
	@Test (dataProvider="searchProvider")
	public void siteSpecificIncentives(String incentiveName, String incentiveZone, String dGridColumnName) throws Exception{
		// To open Find Parcel panel
		Assert.assertTrue(SeleniumActions.openParcelPanel("esriCTHeaderAdvanceSearch", excelRowcount, sheetName));

		// To close property class panel
		Assert.assertTrue(SeleniumActions.closePropertyClassPanel(excelRowcount, sheetName));

		// To open Site specific incentives panel
		Assert.assertTrue(SeleniumActions.openFindParcelAccordion("Site Specific Incentives", excelRowcount, sheetName));

		//To Uncheck the all the check boxes
		Assert.assertTrue(SeleniumActions.deSelectCheckBoxes(excelRowcount, sheetName));

		//To close the alert window
		Assert.assertTrue(SeleniumActions.closeAlertWidnow(excelRowcount, sheetName));

		// To check the Incentive zone check box
		Assert.assertTrue(SeleniumActions.checkIncentiveCheckBox(incentiveName, excelRowcount, sheetName));

		// To open Content grid
		Assert.assertTrue(SeleniumActions.openContentGrid(excelRowcount, sheetName));

		// To select record from the Content grid
		Assert.assertTrue(SeleniumActions.selectContentGridRecord(4, "field-PARCELID", excelRowcount, sheetName));

		// To click on polygon feature on map
		Assert.assertTrue(SeleniumActions.clickFeatureOnMap("selectedPolygonLayer_layer", excelRowcount, sheetName));

		// To validate the Grid record details with Info popup
		Assert.assertTrue(Validations.validateGridDetails(driver, 4, incentiveZone, dGridColumnName, excelRowcount, sheetName));

		//Write result status in excel
		CommonUtility.writeResultsInExcel(excelRowcount, "PASS", sheetName, null);
	}

	/* Close the browser */
	@AfterMethod
	public void afterTest(){
		TestBase.closeBrowser();
	}
}



