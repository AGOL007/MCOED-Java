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

public class LayerInfoParcelLayers extends TestBase {
	String filepath = "E:\\Selenium scripts\\MCOEDTest\\excelData";
	String filename = "McoedData.xlsx";
	String sheetName = "AnalyzeLayerInfoParcelLayer";
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

	/* Test case 1 */
	/* To verify Layer Info-Parcel Layer display on map. */
	@Test (dataProvider="searchProvider")
	public void layerInfoParcelLayers(String parcelLayerName, String radioButtonClassName, String layerId) throws Exception{
		// To open Analyze Parcel panel
		Assert.assertTrue(SeleniumActions.openParcelPanel("esriCTHeaderAnalysis", excelRowcount, sheetName));

		// To open Layer Info panel
		Assert.assertTrue(SeleniumActions.openAnalyzeParcelWidget("esriCTTocLogo", excelRowcount, sheetName));

		// To expand Layer Info panel
		//SeleniumActions.expandLayerInfoLayers("dijit__TreeNode_1", "Parcel Layers");

		// To expand Layer Info panel
		Assert.assertTrue(SeleniumActions.expandLayerInfoLayers("Parcel Layers", excelRowcount, sheetName));

		// To select Parcel Layers radio button
		Assert.assertTrue(SeleniumActions.clickRadioButton(parcelLayerName, radioButtonClassName, excelRowcount, sheetName));

		//To verify the selected layer name in ID of img tag
		Assert.assertTrue(Validations.verifySelectedLayername(driver, parcelLayerName, layerId, excelRowcount, sheetName));

		//SeleniumActions.zoomIn();

		//SeleniumActions.clickOnImageTile();

		//Write result status in excel
		CommonUtility.writeResultsInExcel(excelRowcount, "PASS", sheetName, null);
	}

	/* Close the browser */
	@AfterMethod
	public void afterTest(){
		TestBase.closeBrowser();
	}
}
