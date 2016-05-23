package powerTools;

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

// Test case 1 for Query builder tool
public class QueryBuilderTool extends TestBase {
	String filepath = "E:\\Selenium scripts\\MCOEDTest\\excelData";
	String filename = "McoedData.xlsx";
	String sheetName = "QueryBuilder";
	int excelRowcount = 0;
	
	/* Prerequisite before executing the test cases */
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
			retObjArr = CommonUtility.getExcelData(filepath, filename, sheetName);
			System.out.println("getTestData function executed!!");
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}
		return retObjArr;
	}


	@Test(dataProvider = "searchProvider")
	public void verifySearchQuery(String layerNamefromExcel, String fieldValuefromExcel, String operatorValuefromExcel, 
			String queryValuefromExcel) throws Exception{

		// To open Power tool panel
		Assert.assertTrue(SeleniumActions.openParcelPanel("esriCTHeaderPowerTool", excelRowcount, sheetName));

		// To open Query builder tool
		Assert.assertTrue(SeleniumActions.openQueryBuilderWidget("esriCTQueryBuilderLogo", excelRowcount, sheetName));

		// To enter values in query builder text box
		Assert.assertTrue(SeleniumActions.enterValuesInQueryBuilder(layerNamefromExcel, fieldValuefromExcel, operatorValuefromExcel, queryValuefromExcel, excelRowcount, sheetName));

		Assert.assertTrue(Validations.validateQueryBuilderGrid(driver, layerNamefromExcel, fieldValuefromExcel, queryValuefromExcel, excelRowcount, sheetName));

		//Write result status in excel
		CommonUtility.writeResultsInExcel(excelRowcount, "PASS", sheetName, null);
	}

	/* Close the browser */
	@AfterMethod
	public void afterTest(){
		TestBase.closeBrowser();
	}

}

