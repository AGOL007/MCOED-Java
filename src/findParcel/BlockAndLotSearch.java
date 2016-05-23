package findParcel;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import commonUtilities.CommonUtility;

import seleniumActions.SeleniumActions;
import testBase.TestBase;
import validations.Validations;
import createLog.CreateLog;

public class BlockAndLotSearch extends TestBase {
	String filepath = "E:\\Selenium scripts\\MCOEDTest\\excelData";
	String filename = "McoedData.xlsx";
	String sheetName = "BLockAndLotSearch";
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
			retObjArr = CommonUtility.getExcelData(filepath, filename, sheetName);
			System.out.println("getTestData function executed!!");
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}
		return retObjArr;
	}

	// Test case 4
	// Block & Lot search 
	@Test (dataProvider="searchProvider")
	public void blockAndLotSearch(String municipalityName, String blockValue, String lotInputValue, String infopoupMunicipalityName) throws Exception{
		//lastRow = excelRowcount++;
		System.out.println(" test method row excelRowcount: " + excelRowcount);
		// To open Find Parcel panel
		Assert.assertTrue(SeleniumActions.openParcelPanel("esriCTHeaderAdvanceSearch", excelRowcount, sheetName));

		// To close property class panel
		Assert.assertTrue(SeleniumActions.closePropertyClassPanel(excelRowcount, sheetName));

		// To open Site specific incentives panel
		Assert.assertTrue(SeleniumActions.openFindParcelAccordion("Block & Lot Search", excelRowcount, sheetName));

		// To select Municipality from the drop down
		Assert.assertTrue(SeleniumActions.selectMuncipality(municipalityName, excelRowcount, sheetName));

		/* Enter block and lot values */
		Assert.assertTrue(SeleniumActions.enterBlockAndLotValues(blockValue, lotInputValue, excelRowcount, sheetName));

		// To open Content dGrid
		Assert.assertTrue(SeleniumActions.openContentGrid(excelRowcount, sheetName));

		// To select the record in dGrid
		Assert.assertTrue(SeleniumActions.selectContentGridRecord(1, "field-PARCELID", excelRowcount, sheetName));

		// To expand the dGrid
		Assert.assertTrue(SeleniumActions.expandDgrid(excelRowcount, sheetName));

		// To validate that block and lot values in dgrid
		Assert.assertTrue(Validations.validateBlockAndLotValue(driver, 1, blockValue, lotInputValue, infopoupMunicipalityName, excelRowcount, sheetName));

		// To collapse the dGrid	
		Assert.assertTrue(SeleniumActions.collapseDgrid(excelRowcount, sheetName));

		// To click on polygon feature on map
		Assert.assertTrue(SeleniumActions.clickFeatureOnMap("selectedPolygonLayer_layer", excelRowcount, sheetName));

		// To validate the Grid record details with Info popup
		Assert.assertTrue(Validations.validateGridDetails(driver, 1, "PISCATAWAY", "Parcel Id", excelRowcount, sheetName));

		//Write result status in excel
		CommonUtility.writeResultsInExcel(excelRowcount, "PASS", sheetName, null);

	}

	/* Close the browser */
	@AfterMethod
	public void afterTest(){
		TestBase.closeBrowser();
	}
}
