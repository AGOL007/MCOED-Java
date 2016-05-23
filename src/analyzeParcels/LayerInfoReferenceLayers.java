package analyzeParcels;

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

public class LayerInfoReferenceLayers extends TestBase {
	String filepath = "E:\\Selenium scripts\\MCOEDTest\\excelData";
	String filename = "McoedData.xlsx";
	String sheetName = "AnalyzeReferenceLayer";
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
		Assert.assertTrue(SeleniumActions.closeAlertWidnow(excelRowcount,sheetName));
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

	/* Test case 4 */
	/* To verify the display of Reference layer on map. */
	@Test (dataProvider="searchProvider")
	public void displayReferenceLayers(String referenceLayerName, String layerId, String layerTag, String infoPoupTitle, 
			String radioBtnClassName, String zoomInLevel) throws Exception{
		// To open Find Parcel panel
		Assert.assertTrue(SeleniumActions.openParcelPanel("esriCTHeaderAdvanceSearch", excelRowcount, sheetName));

		//To Uncheck the all the check boxes
		Assert.assertTrue(SeleniumActions.deSelectCheckBoxes(excelRowcount, sheetName));

		//To close the alert window
		Assert.assertTrue(SeleniumActions.closeAlertWidnow(excelRowcount, sheetName));

		// To open Analyze Parcel panel
		Assert.assertTrue(SeleniumActions.openParcelPanel("esriCTHeaderAnalysis", excelRowcount, sheetName));

		// To open Layer Info panel
		Assert.assertTrue(SeleniumActions.openAnalyzeParcelWidget("esriCTTocLogo", excelRowcount, sheetName));

		// To expand Layer Info panel
		Assert.assertTrue(SeleniumActions.expandLayerInfoLayers("Reference Layers", excelRowcount, sheetName));

		// To select Local Info layers radio button
		Assert.assertTrue(SeleniumActions.clickRadioButton(referenceLayerName, radioBtnClassName, excelRowcount, sheetName));

		// To zoom the map 
		Assert.assertTrue(SeleniumActions.zoomIn(zoomInLevel, excelRowcount, sheetName));

		// To click on selected layers feature on map 
		Assert.assertTrue(SeleniumActions.clickOnSelectedArea(layerId, layerTag, excelRowcount, sheetName));

		// To validate the info pop-up title name with the selected layer name
		Assert.assertTrue(Validations.validateInfoPopupTitleName(driver, infoPoupTitle, excelRowcount, sheetName));

		//Write result status in excel
		CommonUtility.writeResultsInExcel(excelRowcount, "PASS", sheetName, null);
	}

	/* Close the browser */
	@AfterMethod
	public void afterTest(){
		TestBase.closeBrowser();
	}
}
