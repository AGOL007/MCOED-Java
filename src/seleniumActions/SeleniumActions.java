package seleniumActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import testBase.TestBase;
import commonUtilities.CommonUtility;
import createLog.CreateLog;

public class SeleniumActions extends TestBase {

	/* To enter the user credentials in the application */
	public static boolean userLogin(Properties properties, int lastRowInExcel, String sheetName) throws Exception{
		try {
			Properties prop = CommonUtility.readConfig(properties); // Read the app config
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			//Thread.sleep(5000);
			WebElement sigInBox = driver.findElement(By.id("sign-in-box"));
			WebElement usernameTextBox = sigInBox.findElement(By.id("email"));
			usernameTextBox.sendKeys(username);

			WebElement passwordTextBox = sigInBox.findElement(By.id("password"));
			passwordTextBox.sendKeys(password);
			WebElement loginButton = driver.findElement(By.id("btnLogin"));
			loginButton.click();
			//waitForProcessing("loader-wrapper");
			Thread.sleep(5000);
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	/* To close the splash screen */
	public static boolean closeSplashScreen(int lastRowInExcel, String sheetName) throws Exception{
		//WebDriverWait wait = new WebDriverWait(driver, 30);
		try {
			WebElement splashScreenCloseButton = null;
			try {
				//waitForProcessing("esriCTDivLoaderImg");
				splashScreenCloseButton = driver.findElement(By.className("splashEnablecloseButton"));
			} catch (Exception e) {
				if(driver instanceof ChromeDriver || driver instanceof InternetExplorerDriver){
					//driver.findElement(By.className("splashEnablecloseButton"));
					Thread.sleep(45000);
				}

				splashScreenCloseButton = driver.findElement(By.className("splashEnablecloseButton"));
			}
			if(splashScreenCloseButton.isDisplayed() && splashScreenCloseButton.isEnabled()){
				splashScreenCloseButton.click();
				Thread.sleep(7000);
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;

		//CommonUtility.splashScreenLoadingIndicatorWait(driver, splashScreenElement, 30, "esriCTDivLoaderImg");

		//wait.until(ExpectedConditions.elementToBeClickable((By.className("splashEnablecloseButton"))));
		/*splashScreenCloseButton = driver.findElement(By.className("splashEnablecloseButton"));
		if(splashScreenCloseButton.isDisplayed() && splashScreenCloseButton.isEnabled()){
			splashScreenCloseButton.click();
		}*/
	}

	/* To close the alert window */
	public static boolean closeAlertWidnow(int lastRowInExcel, String sheetName) throws Exception{
		try {
			WebElement alertDialog = driver.findElement(By.className("alert-content"));
			WebElement alertOkButton = alertDialog.findElement(By.tagName("button"));
			if(alertOkButton.isDisplayed() && alertOkButton.isEnabled()){
				alertOkButton.click();
			}
			Thread.sleep(2000);
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	/* To open Parcel panel*/
	public static boolean openParcelPanel(String elementLocator, int lastRowInExcel , String sheetName) throws Exception{
		try {
			WebElement  rightTabElement = driver.findElement(By.className("esriCTRightTab"));
			WebElement findParcelEelement = rightTabElement.findElement(By.className(elementLocator));
			findParcelEelement.click();
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	/* To click property class layer check boxes */
	/*public static Boolean clickPropertyClassCheckBox(String rgbValue, String layerName) throws Exception{
		WebElement finParcelTitlePane = driver.findElement(By.className("findParcelTitlepaneContentDiv"));
		List<WebElement> parcelContainer = finParcelTitlePane.findElements(By.className("parcelContainer"));

		for (WebElement item : parcelContainer) {
			WebElement label = item.findElement(By.tagName("label"));
			WebElement checkBox = item.findElement(By.tagName("input"));
			if(label.getText().equals(layerName)){
				checkBox.click();
				Thread.sleep(3000);
				break;
			}
		}

		WebElement svgTag = driver.findElement(By.tagName("svg"));
		List<WebElement> cricleTag = svgTag.findElements(By.tagName("circle"));
		Boolean flag = false;
		for (WebElement webElement : cricleTag) {
			String rgbFeatureLayer = webElement.getAttribute("fill");
			if(rgbValue.equals(rgbFeatureLayer)){
				flag = true;
				break;
			}
		}

		return flag;
	}*/

	/* to get the rgb color code of the legend*/
	/*public static String getRgbColor(){
		WebElement propertyClassPanelContent = driver.findElement(By.className("findParcelTitlepaneContentDiv"));
		//WebElement commercialCheckbox = propertyClassPanelContent.findElement(By.id("chkDivParcelType_00"));
		//commercialCheckbox.click();

		String legendStyle = propertyClassPanelContent.findElement(By.id("divLegend_Farm")).getAttribute("style");
		String rgbValue = legendStyle.replace("background-color: ", "").replace(";", "");
		System.out.println(rgbValue);
		return rgbValue;

	}*/

	/* To click property class layer check boxes & get RGB color code of legend */
	public static String checkPropertyCLassCheckbox(String layerName, int lastRowInExcel, String sheetName) throws Exception{
		String rgbValue = null;
		try {
			WebElement finParcelTitlePane = driver.findElement(By.className("findParcelTitlepaneContentDiv"));
			List<WebElement> parcelContainer = finParcelTitlePane.findElements(By.className("parcelContainer"));

			for (WebElement item : parcelContainer) {
				WebElement label = item.findElement(By.tagName("label"));
				WebElement checkBox = item.findElement(By.tagName("input"));
				if(label.getText().equals(layerName)){
					checkBox.click();
					Thread.sleep(3000);

					String legendStyle = item.findElement(By.className("parcelLegend")).getAttribute("style");
					rgbValue = legendStyle.replace("background-color: ", "").replace(";", "");
					break;
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			//return false;
		}
		return rgbValue;
	}

	/* To close the sub-panel Property class*/
	public static boolean closePropertyClassPanel(int lastRowInExcel, String sheetName) throws Exception{
		try {
			WebElement panel = driver.findElement(By.className("dijitTitlePaneTitleOpen"));
			if(panel.isDisplayed() && panel.isEnabled()){
				panel.click();
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	/* To Deselect the all the check boxes*/
	public static boolean deSelectCheckBoxes(int lastRowInExcel, String sheetName) throws Exception{
		try {
			List<WebElement> checkBoxContent = driver.findElements(By.className("headerCheckboxContent"));
			for (WebElement checkBoxContentItem : checkBoxContent){
				WebElement selectAllCheckBoxElement = checkBoxContentItem.findElement(By.className("checkAllTypes"));
				if(selectAllCheckBoxElement.isDisplayed() && selectAllCheckBoxElement.isEnabled()){
					selectAllCheckBoxElement.click();
					Thread.sleep(3000);
					break;
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	/* To open Find parcel accordions */
	public static boolean openFindParcelAccordion(String accordionName, int lastRowInExcel, String sheetName) throws Exception{
		try {
			List<WebElement> closedPanelList = driver.findElements(By.className("dijitTitlePaneTitleClosed"));
			boolean bool = false;
			for (WebElement item : closedPanelList) {
				List<WebElement> spanList = item.findElements(By.tagName("span"));
				for (WebElement zone : spanList) {
					if(zone.getText().trim().equals(accordionName)){
						zone.click();
						bool = true;
						break;
					}
				}
				if(bool){
					break;
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	/* to check the check boxes */
	public static boolean checkIncentiveCheckBox(String zoneName, int lastRowInExcel, String sheetName) throws Exception{
		try {
			List<WebElement> finParcelTitlePane = driver.findElements(By.className("findParcelTitlepaneContentDiv"));
			boolean bool = false;

			for (WebElement finParcelTitlePaneItem : finParcelTitlePane) {
				if(finParcelTitlePaneItem.isDisplayed()){
					List<WebElement> parcelContainer = finParcelTitlePaneItem.findElements(By.className("parcelContainer"));

					for (WebElement parcelContainerItem : parcelContainer) {
						WebElement label = parcelContainerItem.findElement(By.tagName("label"));
						String abc = label.getText();
						System.out.println(abc);
						WebElement checkBox = parcelContainerItem.findElement(By.tagName("input"));
						if(label.getText().equals(zoneName)){
							checkBox.click();
							bool = true;
							Thread.sleep(5000);
							break;
						}
					}
					if(bool){
						break;
					}
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	/* To open content grid*/
	public static boolean openContentGrid(int lastRowInExcel, String sheetName) throws Exception{
		try {
			WebElement titlePanel = driver.findElement(By.className("dijitTitlePaneTitleOpen"));
			if(titlePanel.isDisplayed()){
				WebElement contentGrid = titlePanel.findElement(By.className("divContentGrid"));
				contentGrid.click();
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	// To select record from content grid
	public static boolean selectContentGridRecord(int recordNumber, String featureClassName, int lastRowInExcel, String sheetName) throws Exception{
		try {
			WebElement dgridContent = driver.findElement(By.className("dgrid-content"));
			if(dgridContent.isDisplayed()){

				//int numOfRows = dgridContent.findElements(By.tagName("tr")).size();
				List<WebElement> dgridRowList = dgridContent.findElements(By.tagName("tr"));
				Thread.sleep(2000);
				if(dgridRowList != null && dgridRowList.size() > 0){
					dgridRowList.get(recordNumber - 1).findElement(By.className(featureClassName)).click();
					//parcelIdField.click();
					Thread.sleep(3000);
				}	
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	/* To click on polygon feature on map */
	public static boolean clickFeatureOnMap(String selectedPloygonLayerId, int lastRowInExcel, String sheetName) throws Exception{
		try {
			WebElement svgTag = driver.findElement(By.tagName("svg"));
			WebElement selectedPolygonLayer = svgTag.findElement(By.id(selectedPloygonLayerId));
			if(selectedPolygonLayer.isDisplayed()){
				WebElement selectedPolygonLayerPath = selectedPolygonLayer.findElement(By.tagName("path"));
				if(selectedPolygonLayerPath.isDisplayed()){
					selectedPolygonLayerPath.click();
					Thread.sleep(2000);
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	/* To select Municipality name from the drop down */
	public static boolean selectMuncipality(String municipalityName, int lastRowInEcxel, String sheetName) throws Exception{
		try {
			// open Municipality drop down
			WebElement muncipalityDropdown = driver.findElement(By.className("attributeFieldSelect"));
			if(muncipalityDropdown.isDisplayed()){
				muncipalityDropdown.click();
				Thread.sleep(2000);

				// select Municipality name from drop down
				List<WebElement> municipalityNameList = driver.findElements(By.className("dijitMenuItemLabel"));
				for (WebElement municipalityNameListItem : municipalityNameList) {
					if(municipalityNameListItem.getText().equals(municipalityName)){
						municipalityNameListItem.click();
						break;
					}
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInEcxel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	/* Enter block and lot values */
	public static boolean enterBlockAndLotValues(String blockValue, String lotValue, int lastRowInExcel, String sheetName) throws Exception{
		try {
			List<WebElement> paneContentList = driver.findElements(By.className("dijitTitlePaneContentInner"));
			boolean bool = false;
			for (WebElement paneContentListItem : paneContentList) {
				if(paneContentListItem.isDisplayed()){
					WebElement blockAndLotTextBoxWrapper = paneContentListItem.findElement(By.className("textboxWrapper"));
					List<WebElement> textBoxColList = blockAndLotTextBoxWrapper.findElements(By.className("textboxCol"));

					// To enter values in Block and Lot text box, click on Go button
					for (WebElement textBoxColListItem : textBoxColList) {
						//String x = textBoxColListItem.getText();
						if(textBoxColListItem.getText().trim().equals("Block")){
							WebElement blockTextBox = textBoxColListItem.findElement(By.tagName("input"));
							blockTextBox.clear();
							blockTextBox.sendKeys(blockValue);
						}
						else if(textBoxColListItem.getText().trim().equals("Lot")){
							WebElement lotTextBox = textBoxColListItem.findElement(By.tagName("input"));
							lotTextBox.clear();
							lotTextBox.sendKeys(lotValue);
						}
						else if(textBoxColListItem.getText().trim().equals("Go")){
							WebElement goButton = textBoxColListItem.findElement(By.tagName("button"));
							if(goButton.isDisplayed() && goButton.isEnabled()){
								goButton.click();
								bool = true;
								Thread.sleep(2000);
							}
						}
					}
					if(bool){
						break;
					}
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	/* To get cell number of the given Header in dGrid */
	public static int dGridHeaderCellNumber(String dGridHeaderName, int lastRowInExcel, String sheetName) throws Exception{
		int dGridheaderCellNum = 0;
		try {
			System.out.println("into grid header cell number method");
			WebElement dGridHeader = driver.findElement(By.className("dgrid-header"));
			if(dGridHeader.isDisplayed()){
				List<WebElement> thTagList = dGridHeader.findElements(By.tagName("th"));
				int count = 0;

				// to get cell position of Block, lot and Municipality header
				for (WebElement thTagListItem : thTagList) {

					// will scroll until given element is not appeared on page.
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", thTagListItem);
					count++;
					if(thTagListItem.getText().trim().equals(dGridHeaderName)){
						dGridheaderCellNum = count;
						System.out.println("Grid header cell number = " +dGridheaderCellNum);
						break;
					}
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}
		return dGridheaderCellNum;	
	}

	/* To get the dGrid records cell's value */
	public static String dGridCellValue(String dGridHeaderName, int dGridRecordNum, int lastRowInExcel, String sheetName) throws Exception{
		String selectedHeadervalue = null;
		try {
			System.out.println("into grid cellvalue method");
			int dGridHeaderCellNum = SeleniumActions.dGridHeaderCellNumber(dGridHeaderName, lastRowInExcel, sheetName);

			int count = 0;
			WebElement dGridContentRecord = driver.findElement(By.className("dgrid-content"));

			if(dGridContentRecord.isDisplayed()){
				WebElement selectedDgridRecord = dGridContentRecord.findElement(By.className("dgrid-selected"));
				if(selectedDgridRecord.isDisplayed()){
					List<WebElement> trTagList = dGridContentRecord.findElements(By.tagName("tr"));
					boolean bool = false;

					//for (WebElement trTagListItem : trTagList) {
					List<WebElement> tdTagList = trTagList.get(dGridRecordNum - 1).findElements(By.tagName("td"));

					for (WebElement tdTagListItem : tdTagList) {
						// will scroll until given element is not appeared on page.
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tdTagListItem);
						count++;
						if(count == dGridHeaderCellNum){
							selectedHeadervalue = tdTagList.get(dGridHeaderCellNum -1).getText();
							System.out.println("Grid header value = " + selectedHeadervalue);
							bool = true;
							break;
						}
					}
					/*if(bool){
							break;
						}*/
					//}
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}
		return selectedHeadervalue ;
	}

	/* To expand the dGrid */
	public static boolean expandDgrid(int lastRowInExcel, String sheetName) throws Exception{
		try {
			WebElement expandDgrid = driver.findElement(By.className("searchGridDialogResizeCollaps"));
			if(expandDgrid.isDisplayed() && expandDgrid.isEnabled()){
				expandDgrid.click();
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	} 

	/* To collapse the dGrid */
	public static boolean collapseDgrid(int lastRowInExcel, String sheetName) throws Exception{
		try {
			WebElement collapseDgrid = driver.findElement(By.className("searchGridDialogResizeExpand"));
			if(collapseDgrid.isDisplayed() && collapseDgrid.isEnabled()){
				collapseDgrid.click();
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	/* To close the dGrid panel */
	public static void closeDgrid() throws Exception{
		try {
			WebElement searchGridDialogHeader = driver.findElement(By.className("searchGridDialogHeader"));
			if(searchGridDialogHeader.isDisplayed()){
				WebElement dGridHeaderOptionsContainer = searchGridDialogHeader.findElement(By.className("searchGridDialogHeaderOptions"));
				WebElement dGridCloseIcon = dGridHeaderOptionsContainer.findElement(By.className("searchGridDialogHeaderClose"));
				if(dGridCloseIcon.isDisplayed() && dGridCloseIcon.isEnabled()){
					dGridCloseIcon.click();
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}
	}

	/* To enter the values in Minimum and Maximum range text box */
	public static boolean enterMinMaxRange(String minRangeValue, String maxRangeValue, int lastRowInExcel, String sheetName) throws Exception{
		try {
			List<WebElement> minMaxDivContainer = driver.findElements(By.id("Div_Container"));
			boolean bool = false;
			for (WebElement minMaxDivContainerItem : minMaxDivContainer) {
				if(minMaxDivContainerItem.isDisplayed()){
					// enter values in Minimum range text box
					List<WebElement> minimumRangeTextBox = minMaxDivContainerItem.findElements(By.className("divClassSaleprice"));
					for (WebElement minimumRangeTextBoxItem : minimumRangeTextBox) {
						if(minimumRangeTextBoxItem.isDisplayed()){
							WebElement minRangeInputTag = minimumRangeTextBoxItem.findElement(By.tagName("input"));
							minRangeInputTag.clear(); // to clear the text box
							minRangeInputTag.sendKeys(minRangeValue);
							break;
						}
					}

					// enter values in Maximum range text box
					List<WebElement> maximumRangeTextBox = minMaxDivContainerItem.findElements(By.className("divClassAcerage"));
					for (WebElement maximumRangeTextBoxItem : maximumRangeTextBox) {
						if(maximumRangeTextBoxItem.isDisplayed()){
							WebElement maxRangeInputTag = maximumRangeTextBoxItem.findElement(By.tagName("input"));
							maxRangeInputTag.click();
							maxRangeInputTag.clear(); // to clear the text box
							Thread.sleep(1000);
							maxRangeInputTag.sendKeys(maxRangeValue);
							maxRangeInputTag.sendKeys(Keys.ENTER);
							bool = true;
							Thread.sleep(2000);
							break;
						}
					}
					if(bool){
						break;
					}
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	// To open analyze parcels widgets
	public static boolean openAnalyzeParcelWidget(String elementLocator, int lastRowInExcel, String sheetName) throws Exception{
		try {
			WebElement analysisLogoContainer = driver.findElement(By.className("esriCTAnalysisLogos"));
			WebElement analysisLogo = analysisLogoContainer.findElement(By.className(elementLocator));
			analysisLogo.click();
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	/*// Expand Parcel layers
	public static boolean expandLayerInfoLayers(String infoLayerId, String infoLayerName, int lastRowInExcel) throws Exception{
		//List<WebElement> layerInforLayerConatiner = driver.findElements(By.className("dijitTreeNodeContainer"));
		for (WebElement layerInforLayerConatinerItem : layerInforLayerConatiner) {
			if(layerInforLayerConatinerItem.isDisplayed()){
				List<WebElement> layerInforList = layerInforLayerConatinerItem.findElements(By.className("dijitTreeIsRoot"));

				for (WebElement layerInforListItem : layerInforList) {
					List<WebElement> spanList = layerInforListItem.findElements(By.tagName("span"));
					for (WebElement spanListItem : spanList) {

						if(spanListItem.getText().equals("Parcel Layers")){
							//WebElement expandIcon = spanListItem.findElement(By.className("dijitTreeExpandoClosed"));
							//spanList.get(3).click();
							break;
						}
					}
				}
			}
		try {
			WebElement nodeId = driver.findElement(By.id(infoLayerId));
			if(nodeId.getText().trim().equals(infoLayerName)){
				WebElement expandIcon = nodeId.findElement(By.className("dijitTreeExpandoClosed"));
				expandIcon.click();
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL");
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}*/

	// Expand Parcel layers
	public static boolean expandLayerInfoLayers(String infoLayerName, int lastRowInExcel, String sheetName) throws Exception{
		try {
			WebElement dndContainer = driver.findElement(By.className("dijitTreeContainer"));
			List<WebElement> radioButtonList = dndContainer.findElements(By.className("dijitTreeNodeContainer"));
			boolean boolValue = false;
			for (WebElement radioButtonListItem : radioButtonList) {
				if(radioButtonListItem.isDisplayed() )
				{
					List<WebElement> radioButton = radioButtonListItem.findElements(By.className("dijitTreeIsRoot"));
					for (WebElement radio : radioButton) {
						if(radio.getText().equals(infoLayerName) )
						{
							WebElement expandIcon = radio.findElement(By.className("dijitTreeExpando"));
							expandIcon.click();
							boolValue = true;
							break;
						}
					}
					if(boolValue){
						break;
					}
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	// To click radio button of Parcel layers
	public static boolean clickRadioButton(String infoLayerName, String radioButtonClassName, int lastRowInExcel, String sheetName) throws Exception{
		try {
			WebElement dndContainer = driver.findElement(By.className("dijitTreeContainer"));
			List<WebElement> radioButtonList = dndContainer.findElements(By.className("dijitTreeNodeContainer"));
			boolean boolValue = false;
			for (WebElement radioButtonListItem : radioButtonList) {
				if(radioButtonListItem.isDisplayed()) {
					List<WebElement> radioButtonsList = radioButtonListItem.findElements(By.className("dijitTreeContentExpanded"));
					for (WebElement radioButtonItem : radioButtonsList) {
						WebElement treeLabel = radioButtonItem.findElement(By.className("dijitTreeLabel"));
						String abc = treeLabel.getText();

						if(treeLabel.isDisplayed()){
							//if(treeLabel.getText().contains(infoLayerName)){
							/*if(abc.contains("\n")){
									int x =abc.indexOf("\n");
									abc = abc.substring(0, x);
								}*/

							if(treeLabel.getText().startsWith(infoLayerName)){
								WebElement uncheckedRadioButton = treeLabel.findElement(By.className(radioButtonClassName));
								uncheckedRadioButton.click();
								Thread.sleep(1500);
								boolValue = true;
								break;
							}
						}
					}
					if(boolValue){
						break;
					}
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;

		/*	WebElement dndContainer = driver.findElement(By.className("dijitTreeContainer"));
		String infoLayerunder= infoLayerName.replace(" ", "_");
		if(dndContainer.isDisplayed()){
			List<WebElement> radioButtonList = dndContainer.findElements(By.className("unchecked"));
			for (WebElement radioButtonListItem : radioButtonList) {
				String xyz = radioButtonListItem.getAttribute("id");
				if(radioButtonListItem.getAttribute("id").contains(infoLayerunder)){
					radioButtonListItem.click();
					break;
				}
			}
		}*/
	}

	// To zoom the map 
	public static boolean zoomIn(String zoomInlevel, int lastRowInExcel, String sheetName) throws Exception{
		try {
			int zoomInScale = Integer.parseInt(zoomInlevel);
			WebElement zoomInButton = driver.findElement(By.className("esriSimpleSliderIncrementButton"));
			for (int i = 0; i < zoomInScale; i++) {
				if(zoomInButton.isDisplayed()){
					zoomInButton.click();
					Thread.sleep(2500);
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	// To click on selected layers feature on map
	public static boolean clickOnSelectedArea(String featureId, String featureTagName, int lastRowInExcel, String sheetName) throws Exception{
		/*if(featureId.equals("RailRoads_layer_layer") || featureId.equals("Roads_layer_layer")){

		}*/
		Actions action = new Actions(driver);
		try {
			System.out.println("1");
			WebElement svgTag = driver.findElement(By.id("esriCTParentDivContainer_gc"));
			Thread.sleep(1500);
			WebElement gTag = svgTag.findElement(By.id(featureId));
			Thread.sleep(1500);
			List<WebElement> pathTag = gTag.findElements(By.tagName(featureTagName));
			System.out.println("2");
			for (WebElement pathTagItem : pathTag) {
				System.out.println("click on selected area FOR loop");
				if(pathTagItem.isDisplayed() && pathTagItem.isEnabled()){
					System.out.println("click on selected area if loop");
					Thread.sleep(1500);
					action.moveToElement(pathTag.get(2)).click().build().perform();
					//((JavascriptExecutor)driver).executeScript("arguments[0].click();", pathTagItem); 
					System.out.println("Polygon is clicked");
					Thread.sleep(1500);
					break;
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	// To select parcel from book mark section
	public static String selectParcelFromBookMark(int lastRowInExcel, String sheetName) throws Exception{
		String bookmarkText = null;
		try {
			WebElement bookmarkContainer = driver.findElement(By.className("esriCTHeaderBookMarkContainer"));
			WebElement bookmarkParent = bookmarkContainer.findElement(By.className("esriCTBookmarkParent"));
			//WebElement bookMark = bookmarkParent.findElement(By.id("esriApplicationbookmarkcontent"));
			//List<WebElement> bookMarkContentList = bookmarkParent.findElements(By.className("esriCTbookmarkcontent"));
			//int abc = bookMarkContentList.size();
			List<WebElement> bookMarkContentDivList = bookmarkParent.findElements(By.className("esriCTContent"));
			if(bookMarkContentDivList.isEmpty()){
				Assert.assertTrue(selectBookMark(lastRowInExcel, sheetName));
			}
			List<WebElement> bookMarkContentDiv = bookmarkParent.findElements(By.className("esriCTContent"));
			for (WebElement bookMarkContentDivItem : bookMarkContentDiv) {
				bookmarkText = bookMarkContentDivItem.getText();
				WebElement radioButton = bookmarkParent.findElement(By.className("esriCTBookmarkUncheck"));
				radioButton.click();
				break;
			}
			WebElement nextButton = bookmarkParent.findElement(By.className("next-button"));
			nextButton.click();
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}
		return bookmarkText;
	}

	// To select book mark from dGrid to generate report
	public static boolean selectBookMark(int lastRowInExcel, String sheetName) throws Exception{
		try {
			Assert.assertTrue(openParcelPanel("esriCTHeaderAdvanceSearch", lastRowInExcel, sheetName));
			Assert.assertTrue(openContentGrid(lastRowInExcel, sheetName));
			Assert.assertTrue(selectContentGridRecord(1, "searchGridBookmarkUnselected", lastRowInExcel, sheetName));

			// To open Analyze Parcel panel
			Assert.assertTrue(openParcelPanel("esriCTHeaderAnalysis", lastRowInExcel, sheetName));

			// To open Reports panel
			Assert.assertTrue(openAnalyzeParcelWidget("esriCTBookMarkLogo", lastRowInExcel, sheetName));
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	// To select report
	public static boolean selectReport(String reportName, int lastRowInExcel, String sheetName) throws Exception{
		try {
			WebElement reportContainer = driver.findElement(By.className("esriCTReportContentContainer"));
			List<WebElement> reportContentList = reportContainer.findElements(By.className("esriCTReportContentLabel"));
			for (WebElement reportContentListItem : reportContentList) {
				String checkBoxLabel = reportContentListItem.findElement(By.className("reportCheckBoxLabel")).getText();
				WebElement reportCheckbox = reportContentListItem.findElement(By.className("reportCheckbox"));
				if(checkBoxLabel.equals(reportName)){
					reportCheckbox.click();
					WebElement generateButton = reportContainer.findElement(By.className("generateReportButton"));
					generateButton.click();
					break;
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	// Wait till Processing bar is invisible
	public static boolean waitUntilLoadingIndicatorIsdispalyed(String featureClassName, int lastRowInExcel, String sheetName) throws Exception{
		//WebElement status = driver.findElement(By.className("downloadingStatus"));
		try {
			int timeoutInSeconds = 15;
			if(driver instanceof ChromeDriver || driver instanceof InternetExplorerDriver){
				timeoutInSeconds = 20;
			}
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
			//boolean element = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(featureClassName)));
			wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("featureClassName"))));
			//return element;
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	// To select book mark to compare
	public static ArrayList<String> selectBookmarkToCompare(int numOfParcel, int lastRowInExcel) throws Exception{
		ArrayList<String> bookMarktextList = null;
		try {
			bookMarktextList = new ArrayList<String>();
			WebElement bookmarkContainer = driver.findElement(By.className("esriCTHeaderCompareContainer"));
			WebElement bookmarkParent = bookmarkContainer.findElement(By.className("esriCTBookmarkParent"));
			List<WebElement> bookMarkContentDivList = bookmarkParent.findElements(By.className("esriCTBookmarkContentdiv"));
			if(bookMarkContentDivList.isEmpty() && bookMarkContentDivList.size() < 2){
				selectBokmarkFromGrid(lastRowInExcel);
			}
			List<WebElement> bookMarkContentDivList1 = bookmarkParent.findElements(By.className("esriCTBookmarkContentdiv"));
			for (int i = 0; i < numOfParcel; i++) {
				WebElement parcelLabel = bookMarkContentDivList1.get(i).findElement(By.className("parcelLabel"));
				bookMarktextList.add(parcelLabel.getText());
				WebElement checkBox = bookMarkContentDivList1.get(i).findElement(By.tagName("input"));
				if(checkBox.getAttribute("aria-checked").equals("false")){
					checkBox.click();
					waitUntilLoadingIndicatorIsdispalyed("esriCTdisableBackgroundLoading", lastRowInExcel, null);
				}
			}
			WebElement buttonWrapper = bookmarkParent.findElement(By.className("esriCTbuttonWrapper"));
			WebElement compareButton = buttonWrapper.findElement(By.className("next-button"));
			compareButton.click();
			Thread.sleep(1000);
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}
		return bookMarktextList;
	}

	public static void selectBokmarkFromGrid(int lastRowInExcel) throws Exception{
		try {
			openParcelPanel("esriCTHeaderAdvanceSearch", lastRowInExcel, null);
			openContentGrid(lastRowInExcel, null);
			selectMultipleContentGridRecord(2, "searchGridBookmarkUnselected");

			// To open Compare Parcel panel
			openParcelPanel("esriCTHeaderComparison", lastRowInExcel, null);
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}

	}

	public static void selectMultipleContentGridRecord(int recordNumber, String featureClassName) throws Exception{
		try {
			WebElement dgridContent = driver.findElement(By.className("dgrid-content"));
			if(dgridContent.isDisplayed()){
				//int numOfRows = dgridContent.findElements(By.tagName("tr")).size();
				List<WebElement> dgridRowList = dgridContent.findElements(By.tagName("tr"));
				Thread.sleep(2000);
				if(dgridRowList != null && dgridRowList.size() > 0){
					for (int i = 0; i < recordNumber; i++) {
						dgridRowList.get(i).findElement(By.className(featureClassName)).click();
						//parcelIdField.click();
						Thread.sleep(3000);
					}
				}	
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}
	}

	// To open Query Builder widgets
	public static boolean openQueryBuilderWidget(String elementLocator, int lastRowInExcel, String sheetName) throws Exception{
		try {
			WebElement analysisLogoContainer = driver.findElement(By.className("esriCTPowerToolLogos"));
			WebElement analysisLogo = analysisLogoContainer.findElement(By.className(elementLocator));
			analysisLogo.click();
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;

	}

	// To enter values in query builder text box
	public static boolean enterValuesInQueryBuilder(String layerName, String fieldValue, String operatorValue, 
			String queryValue, int lastRowInExcel, String sheetName) throws Exception{
		try {
			List<WebElement> selectWrapperList = driver.findElements(By.className("selectWrapper"));
			for (WebElement selectWrapperListItem : selectWrapperList) {
				if(selectWrapperListItem.isDisplayed()){
					WebElement selectLayer = selectWrapperListItem.findElement(By.className("queryBuilderselectBox"));
					selectLayer.sendKeys(layerName);
				}
			}

			WebElement fieldWrapper = driver.findElement(By.className("fieldWrapper"));
			WebElement fieldBox = fieldWrapper.findElement(By.className("fieldBox"));
			WebElement fieldTextBox = fieldBox.findElement(By.className("fieldSelectBox"));
			fieldTextBox.sendKeys(fieldValue);
			Thread.sleep(1000);
			WebElement operatorBox = fieldWrapper.findElement(By.className("operatorBox"));
			WebElement operatorTextBox = operatorBox.findElement(By.className("fieldSelectBox"));
			operatorTextBox.sendKeys(operatorValue);
			Thread.sleep(1000);

			WebElement valueBox = fieldWrapper.findElement(By.className("valueBox"));
			WebElement valueTextBox = valueBox.findElement(By.tagName("input"));
			valueTextBox.click();
			valueTextBox.sendKeys(queryValue);
			Thread.sleep(1000);
			WebElement buttonWrapper = driver.findElement(By.className("buttonWrapper"));
			List<WebElement> buttonList = buttonWrapper.findElements(By.tagName("button"));
			for (WebElement buttonListItem : buttonList) {
				if(buttonListItem.getText().equals("Search")){
					buttonListItem.click();
					break;
				}
			}
			waitUntilLoadingIndicatorIsdispalyed("queryBuilderLoaderImg", lastRowInExcel, sheetName);
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	//To get Query builder dGrid header cell number
	public static int queryBuilderdGridHeaderCellNumber(String dGridHeaderName, int lastRowInExcel, String sheetName) throws Exception{
		int dGridheaderCellNum = 0;
		try {
			Thread.sleep(1000);
			WebElement dGridHeader = driver.findElement(By.className("compareHeaderTable"));
			if(dGridHeader.isDisplayed()){
				List<WebElement> thTagList = dGridHeader.findElements(By.tagName("th"));
				int count = 0;

				// to get cell position of Block, lot and Municipality header
				for (WebElement thTagListItem : thTagList) {

					// will scroll until given element is not appeared on page.
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", thTagListItem);
					count++;
					if(thTagListItem.getText().trim().equals(dGridHeaderName)){
						dGridheaderCellNum = count;
						break;
					}
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}
		return dGridheaderCellNum;	
	}

	/* To get Query builder dGrid records cell's value */
	public static String queryBuilderdGridCellValue(String dGridHeaderName, int lastRowInExcel, String sheetName) throws Exception{
		String selectedHeadervalue = null;
		int count = 0;
		try {
			int dGridHeaderCellNum = queryBuilderdGridHeaderCellNumber(dGridHeaderName, lastRowInExcel, sheetName);
			WebElement dGridContentRecord = driver.findElement(By.className("queryBuilderTableContent"));
			if(dGridContentRecord.isDisplayed()){
				WebElement selectedDgridRecord = dGridContentRecord.findElement(By.className("queryBuilderTable"));
				if(selectedDgridRecord.isDisplayed()){
					List<WebElement> trTagList = dGridContentRecord.findElements(By.tagName("tr"));
					boolean bool = false;
					for (WebElement trTagListItem : trTagList) {
						List<WebElement> tdTagList = trTagListItem.findElements(By.tagName("td"));
						for (WebElement tdTagListItem : tdTagList) {
							// will scroll until given element is not appeared on page.
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tdTagListItem);
							count++;
							if(count == dGridHeaderCellNum){
								selectedHeadervalue = tdTagList.get(dGridHeaderCellNum -1).getText();
								bool = true;
								break;
							}
						}
						if(bool){
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}
		return selectedHeadervalue ;
	}

	// To select record from Query Builder content grid
	public static boolean selectQueryBuilderContentGridRecord(int lastRowInExcel, String sheetName) throws Exception{
		try {
			WebElement dGridContentRecord = driver.findElement(By.className("queryBuilderTable"));
			if(dGridContentRecord.isDisplayed()){
				List<WebElement> trTagList = dGridContentRecord.findElements(By.tagName("tr"));
				Thread.sleep(500);
				for (WebElement trTagListItem : trTagList) {
					if(trTagList != null && trTagList.size() > 0){
						//trTagList.get(recordNumber - 1).findElement(By.className(featureClassName)).click();
						//parcelIdField.click();
						trTagListItem.click();
						Thread.sleep(2000);
						break;
					}
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}
}

