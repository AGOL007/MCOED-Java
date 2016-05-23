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

public class PropertyClass extends TestBase {
	String filepath = "E:\\Selenium scripts\\MCOEDTest\\excelData";
	String filename = "McoedData.xlsx";
	String sheetName = "PropertyClass";
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

	// Test case 2
	// To validate that Property class layers are present on map
	@Test (dataProvider = "searchProvider")
	public void propertyClass(String propertyClassLayerfromExcel) throws Exception{
		// To open Find Parcel panel
		Assert.assertTrue(SeleniumActions.openParcelPanel("esriCTHeaderAdvanceSearch", excelRowcount, sheetName));

		//To Uncheck the all the check boxes
		Assert.assertTrue(SeleniumActions.deSelectCheckBoxes(excelRowcount, sheetName));

		//To close the alert window
		Assert.assertTrue(SeleniumActions.closeAlertWidnow(excelRowcount, sheetName));

		/* To click property class layer check boxes & get RGB color code of legend */
		String rgbColor = SeleniumActions.checkPropertyCLassCheckbox(propertyClassLayerfromExcel, excelRowcount, sheetName);

		//WebElement propertyClassPanelElement = driver.findElement(By.id("dijit_TitlePane_6_pane"));
		/*WebElement propertyClassPanelContent = propertyClassPanelElement.findElement(By.className("findParcelTitlepaneContentDiv"));
		//WebElement commercialCheckbox = propertyClassPanelContent.findElement(By.id("chkDivParcelType_00"));
		//commercialCheckbox.click();

		String legendStyle = propertyClassPanelContent.findElement(By.id("divLegend_Farm")).getAttribute("style");
		String rgbValue = legendStyle.replace("background-color: ", "").replace(";", "");
		System.out.println(rgbValue);*/

		//To get the RGB color code of legend from Property class panel
		//String rgbColor = SeleniumActions.getRgbColor();

		//To validate Checked property class layer is present on map
		Assert.assertTrue(Validations.validateRGBcolorCode(driver, rgbColor, "Railroad", excelRowcount, sheetName));

		//Write result status in excel
		CommonUtility.writeResultsInExcel(excelRowcount, "PASS", sheetName, null);
	}

	/* Close the browser */
	@AfterMethod
	public void afterTest(){
		TestBase.closeBrowser();
	}
}
