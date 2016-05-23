package findParcel;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import commonUtilities.CommonUtility;
import createLog.CreateLog;

import seleniumActions.SeleniumActions;
import testBase.TestBase;
import validations.Validations;

public class ContentsOfLayers extends TestBase{

	String filepath = "E:\\Selenium scripts\\MCOEDTest\\excelData";
	String filename = "McoedData.xlsx";
	String sheetName = "RangeSearch";
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

	/*  Range search */
	@Test (dataProvider = "searchProvider")
	public void contentsOfAcreage(String accordionName, String fromRangeValue, String toRangeValue, String dGridColumnName) throws Exception{
		// To open Find Parcel panel
		Assert.assertTrue(SeleniumActions.openParcelPanel("esriCTHeaderAdvanceSearch", excelRowcount, sheetName));

		// To close property class panel
		Assert.assertTrue(SeleniumActions.closePropertyClassPanel(excelRowcount, sheetName));

		// To open Site specific incentives panel
		Assert.assertTrue(SeleniumActions.openFindParcelAccordion(accordionName, excelRowcount, sheetName));

		// To enter the minimum and maximum values in the text box
		Assert.assertTrue(SeleniumActions.enterMinMaxRange(fromRangeValue, toRangeValue, excelRowcount, sheetName));

		// To open Content dGrid
		Assert.assertTrue(SeleniumActions.openContentGrid(excelRowcount, sheetName));

		// To expand the dGrid
		Assert.assertTrue(SeleniumActions.expandDgrid(excelRowcount, sheetName));

		// To select the record in dGrid
		Assert.assertTrue(SeleniumActions.selectContentGridRecord(1, "field-PARCELID", excelRowcount, sheetName));

		// To validate the Acreage value in dGrid
		Assert.assertTrue(Validations.validateAcreageRange(driver, 1, fromRangeValue, toRangeValue, dGridColumnName, excelRowcount, sheetName));

		//Write result status in excel
		CommonUtility.writeResultsInExcel(excelRowcount, "PASS", sheetName, null);
	}

	/* Close the browser */
	@AfterMethod
	public void afterTest(){
		TestBase.closeBrowser();
	}
}
