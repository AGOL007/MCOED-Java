package analyzeParcels;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
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

public class VerifyReports extends TestBase {
	String filepath = "E:\\Selenium scripts\\MCOEDTest\\excelData";
	String filename = "McoedData.xlsx";
	String sheetName = "AnalyzeReports";
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

	// Test case 5
	// To Verify Parcel Block and Lot report
	@Test(dataProvider="searchProvider")
	public void verifyParcelBlockAndLotReport(String referenceLayerName) throws Exception{
		// To open Analyze Parcel panel
		Assert.assertTrue(SeleniumActions.openParcelPanel("esriCTHeaderAnalysis", excelRowcount, sheetName));

		// To open Reports panel
		Assert.assertTrue(SeleniumActions.openAnalyzeParcelWidget("esriCTBookMarkLogo", excelRowcount, sheetName));

		// To select parcel from book mark section
		String bookmarkText = SeleniumActions.selectParcelFromBookMark(excelRowcount, sheetName);

		// To select report
		Assert.assertTrue(SeleniumActions.selectReport(referenceLayerName, excelRowcount, sheetName));

		// To close alert window
		Assert.assertTrue(SeleniumActions.closeAlertWidnow(excelRowcount, sheetName));

		if(driver instanceof ChromeDriver || driver instanceof InternetExplorerDriver){
			Assert.assertTrue(SeleniumActions.waitUntilLoadingIndicatorIsdispalyed("downloadingStatus", excelRowcount, sheetName));
		}

		// To validate pdf report text
		Assert.assertTrue(Validations.validateReport(driver, bookmarkText, excelRowcount, sheetName));

		//Write result status in excel
		CommonUtility.writeResultsInExcel(excelRowcount, "PASS", sheetName, null);
	}

	/* Close the browser */
	@AfterMethod
	public void afterTest(){
		TestBase.closeBrowser();
	}
}
