package validations;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import commonUtilities.CommonUtility;
import createLog.CreateLog;

import seleniumActions.SeleniumActions;
import testBase.TestBase;

public class Validations {

	/* To check operational layers on load */
	public static boolean validateOperationalLayersOnLoad(WebDriver driver, JavascriptExecutor js, 
			Long operationalLayersCount) throws Exception{
		boolean flag = false;
		try {
			WebElement svgTag = driver.findElement(By.id("esriCTParentDivContainer_gc"));
			List<WebElement> gTag = svgTag.findElements(By.tagName("g"));
			int count = 0;

			// to check G tag id contains operational layer name value
			for (int i = 0; i < operationalLayersCount; i++) {
				String operationalLayersName = (js.executeScript("return appGlobals.configData.OperationalLayers[" + i +"].LayerName")).toString();
				for (WebElement gTagId : gTag) {
					if((gTagId.getAttribute("id")).contains(operationalLayersName)){
						flag = true;
						count++;
						break;
					}
				}
				if(flag){
					System.out.println(operationalLayersName + " layer is present on map.");
				}
			}
			System.out.println("Count is: " + count);
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}
		return flag;
	}

	/* To check the rgb color code of legend from Property class legend and */
	public static boolean validateRGBcolorCode(WebDriver driver, String rgbValue, String layerName, int lastRowInExcel, String sheetName) throws Exception{
		try {
			WebElement svgTag = driver.findElement(By.tagName("svg"));
			List<WebElement> cricleTag = svgTag.findElements(By.tagName("circle"));
			for (WebElement webElement : cricleTag) {
				String rgbFeatureLayer = webElement.getAttribute("fill");
				if(rgbValue.equals(rgbFeatureLayer)){
					return true;
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return false;
	}

	/* To validate the Grid record details with Info popup */
	public static boolean validateGridDetails(WebDriver driver, int dGridRecordNum, String incentiveName,
			String dGridColumnName, int lastRowInExcel, String sheetName) throws Exception{
		boolean boolValue = false;
		int count = 0;
		try {
			String dGridColumnValue = SeleniumActions.dGridCellValue(dGridColumnName, dGridRecordNum, lastRowInExcel, sheetName);
			System.out.println("Grid column value is :" + dGridColumnValue);
			WebElement esriPopup = driver.findElement(By.className("esriPopupVisible"));

			if(esriPopup.isDisplayed()){
				WebElement popupContentPane = esriPopup.findElement(By.className("contentPane"));
				List<WebElement> rightInfoPoupContentList = popupContentPane.findElements(By.className("rightInfoContentDiv"));
				for (WebElement rightInfoPoupContentListItem : rightInfoPoupContentList) {
					System.out.println("Poup items is : "+rightInfoPoupContentListItem.getText());
					if((rightInfoPoupContentListItem.getText().trim()).contains(incentiveName) || 
							(rightInfoPoupContentListItem.getText().trim()).contains(dGridColumnValue) ){
						System.out.println("Incentive name is : " + incentiveName);
						System.out.println("Column name cell value is " + dGridColumnValue);
						count++;
						System.out.println("Count in for loop : "+ count);
					}
				}
				if(count == 2){
					boolValue = true;
					System.out.println("Site specific Incentives validated with esri popup");
					System.out.println("Total count is: " +count);
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return boolValue;
	}

	/* To validate the block and lot values in drid */
	public static boolean validateBlockAndLotValue(WebDriver driver, int dGridRecordNumber, String blockValue, 
			String lotValue, String infopoupMunicipalityName, int lastRowInExcel, String sheetName) throws Exception{
		//int blockCellNum1 = SeleniumActions.dGridHeaderCellNumber("Block");
		//int lotCellNum1 = SeleniumActions.dGridHeaderCellNumber("Lot");
		//int municipalityCellNum1 = SeleniumActions.dGridHeaderCellNumber("Municipality");

		// to check and match the values at the cell position Block, lot and Municipality column
		/*WebElement dGridContentRecord = driver.findElement(By.className("dgrid-content"));
		if(dGridContentRecord.isDisplayed()){
			List<WebElement> trTagList = dGridContentRecord.findElements(By.tagName("tr"));
			for (WebElement trTagListItem : trTagList) {
				List<WebElement> tdTagList = trTagListItem.findElements(By.tagName("td"));
				if(tdTagList.get(blockCellNum1 - 1).getText().equals(blockValue) && 
						tdTagList.get(lotCellNum1 - 1).getText().equals(lotValue) && tdTagList.get(municipalityCellNum1 - 1).getText().toUpperCase().contains(municipalityName)){
					flag = true;
					break;
				}
			}

		}*/	
		try {
			String dGridBlockValue = SeleniumActions.dGridCellValue("Block", dGridRecordNumber, lastRowInExcel, sheetName);
			String dGridLotValue = SeleniumActions.dGridCellValue("Lot", dGridRecordNumber, lastRowInExcel, sheetName);
			String dGridMunicipalityValue = SeleniumActions.dGridCellValue("Municipality", dGridRecordNumber, lastRowInExcel, sheetName);
			System.out.println("dGridMunicipalityValue : " + dGridMunicipalityValue.toLowerCase());
			System.out.println("municipalityName : " + infopoupMunicipalityName.toLowerCase());
			if(dGridBlockValue.equals(blockValue) && dGridLotValue.equals(lotValue) && dGridMunicipalityValue.equals(infopoupMunicipalityName)){
				return true;
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return false;
	}

	// To validate the Acreage value in dGrid
	public static boolean validateAcreageRange(WebDriver driver, int dGridRecordNumber, String minRange, 
			String maxRange, String dGridHeaderName, int lastRowInExcel, String sheetName) throws NumberFormatException, Exception{
		try {
			double fromRange = Double.parseDouble(minRange);
			double toRange = Double.parseDouble(maxRange);
			System.out.println("From range value :" + fromRange);
			System.out.println("To range value :" + toRange);
			double dGridBlockValue = Double.parseDouble(SeleniumActions.dGridCellValue(dGridHeaderName, dGridRecordNumber, lastRowInExcel, sheetName));
			if(dGridBlockValue >= fromRange && dGridBlockValue <= toRange){
				System.out.println("Validate acrerange if condition");
				return true;
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
		CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
		CreateLog.createLogFile(stackTraceElements, null, screenShotPath, driver);
		return false;
	}

	//To verify the selected layer name in ID of img tag
	public static boolean verifySelectedLayername(WebDriver driver, String infoLayerName, String selectedLayerId, 
			int lastRowInExcel, String sheetName) throws Exception {
		String infoLayerunder = null;
		boolean boolValue = false;
		try {
			if(infoLayerName.contains(" - ")){
				infoLayerName= infoLayerName.replace(" - ", " ");
			}
			infoLayerunder= infoLayerName.replace(" ", "_");
			//String inforLayerNameModified = infoLayerunder.replace("-", "");
			WebElement parentDiv = driver.findElement(By.id(selectedLayerId));
			List<WebElement> imagTagList = parentDiv.findElements(By.tagName("img"));

			if(!imagTagList.isEmpty()){
				for (WebElement imagTagListItem : imagTagList) {
					if(imagTagListItem.isDisplayed()){
						//imagTagListItem.click();
						String abc = imagTagListItem.getAttribute("id");
						if(imagTagListItem.getAttribute("id").contains(infoLayerunder)){
							boolValue = true;
						}
						else{
							boolValue = false;
						}
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
		return boolValue;
	}

	// To validate that feature is present on map
	public static boolean validateFeatureIspresent(WebDriver driver, String selectedLayerId, String featureTagName) throws Exception{
		try {
			WebElement parentDiv = driver.findElement(By.id(selectedLayerId));
			List<WebElement> imagTagList = parentDiv.findElements(By.tagName(featureTagName));
			if(!imagTagList.isEmpty()){
				return true;
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}
		return false;
	}

	// To validate the info pop-up title name with the selected layer name
	public static boolean validateInfoPopupTitleName(WebDriver driver, String selectedLayerName, int lastRowInExcel, String sheetName) throws Exception{
		try {
			System.out.println("in validateInfoPopupTitleName");
			Thread.sleep(1000);
			WebElement esriPopup = driver.findElement(By.className("esriPopupWrapper"));
			if(esriPopup.isDisplayed()){
				WebElement esriPopupTitle = esriPopup.findElement(By.className("title"));
				System.out.println("Selected layer name : " + selectedLayerName);
				System.out.println("esriPopupTitle : " + esriPopupTitle.getText());
				if(esriPopupTitle.getText().contains(selectedLayerName)){
					System.out.println(esriPopupTitle.getText());
					return true;
				} else{
					StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
					String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
					CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
				}
			} 
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return false;
	}

	// To validate the pdf report
	public static boolean validateReport(WebDriver driver, String bookmarkText, int lastRowInExcel, String sheetName) throws Exception{
		Thread.sleep(2000);
		try {
			for (String windowHandle : driver.getWindowHandles()) { //Gets the new window handle
				System.out.println(windowHandle);
				driver.switchTo().window(windowHandle);        // switch focus of WebDriver to the next found window handle (that's your newly opened window)              
			}
			String pdfUrl = driver.getCurrentUrl();
			if (verifyPDFContent(pdfUrl, bookmarkText)) {
				return true;
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return false;
	}

	//
	public static boolean validateCompareTable(WebDriver driver, ArrayList<String> bookMarktextList) throws Exception{
		boolean boolVaue = false;
		try {
			WebElement compareHeaderTable = driver.findElement(By.className("compareHeaderTable"));
			List<WebElement> compareAttrHeaderList = compareHeaderTable.findElements(By.className("compareAttrHeader"));
			for (int i = 0; i < compareAttrHeaderList.size(); i++) {
				for (int j = 0; j < bookMarktextList.size(); j++) {
					// compare list.get(i) and list.get(j)
					String a = compareAttrHeaderList.get(i).getText();
					String b = bookMarktextList.get(j);
					if(compareAttrHeaderList.get(i).getText().equals(bookMarktextList.get(j))){
						boolVaue = true;
					}
				}
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}
		return boolVaue;
	}

	//
	public static boolean testMethod(WebDriver driver) throws Exception{
		String abc = driver.getWindowHandle();
		System.out.println("Current windows name : " + abc);
		Thread.sleep(200);
		for (String  window1 : driver.getWindowHandles()) {
			System.out.println(window1);
			driver.switchTo().window(window1);
		}
		String urlnew = driver.getCurrentUrl();
		System.out.println("Current url name : " + urlnew);

		WebElement pdf = driver.findElement(By.className("pdfViewer"));
		WebElement textLayer = pdf.findElement(By.className("textLayer"));
		List<WebElement> textList = textLayer.findElements(By.tagName("div"));
		for (WebElement textListItem : textList) {
			String abc1 = textListItem.getText();
			System.out.println("PDF text is : " + abc1);
			if(textListItem.getText().equals("abc")){
				return true;
			}
		}
		return false;
	}

	// To validate query builder dGrid results
	public static boolean validateQueryBuilderGrid(WebDriver driver, String layerName, String fieldValue, String queryValue,  int lastRowInExcel, String sheetName) throws Exception{
		// To validate layer as per layer name
		try {
			switch (layerName) {

			case "County Boundary":
				Assert.assertTrue(validateQueryBuilderLayers(driver, fieldValue, queryValue, lastRowInExcel, sheetName));
				break;

			case "Municipal Boundary":
				Assert.assertTrue(validateQueryBuilderLayers(driver, fieldValue, queryValue, lastRowInExcel, sheetName));
				break;	

			case "Commercial Parcel":	

				//To select record from query builder dGrid
				Assert.assertTrue(SeleniumActions.selectQueryBuilderContentGridRecord(lastRowInExcel, sheetName));

				// To click on polygon feature on map
				Assert.assertTrue(SeleniumActions.clickFeatureOnMap("selectePolygonLayer_layer", lastRowInExcel, sheetName));

				// To validate query builder Info pop-up result with query value
				Assert.assertTrue(validateQueryBuilderInfoPoupDetails(driver, fieldValue, queryValue, lastRowInExcel, sheetName));

				// To validate query builder dGrid result with query value
				Assert.assertTrue(validateQueryBuilderLayers(driver, fieldValue, queryValue, lastRowInExcel, sheetName));
				break;

			case "Urban Enterprize Zone (UEZ)":
			case "Foreign Trade Zone (FTZ)":
			case "Brownfield Development Area (BDA)":
			case "Special Improvement District (SID)":
			case "Main Street Designation (MSD)":
			case "Redevelopment Areas (RDA)":
			case "Port Fields (PF)":
			case "Transit Villages (TV)":
			case "Urban Transit Hub (UTH)":
				//To select record from query builder dGrid
				Assert.assertTrue(SeleniumActions.selectQueryBuilderContentGridRecord(lastRowInExcel, sheetName));

				Assert.assertTrue(validateQueryBuilderLayers(driver, fieldValue, queryValue, lastRowInExcel, sheetName));

				//SeleniumActions.zoomIn(3);

				// To click on polygon feature on map
				//SeleniumActions.clickFeatureOnMap("attributeQueryLayer_layer");
				break;

			case "Rail Roads":
				//To select record from query builder dGrid
				Assert.assertTrue(SeleniumActions.selectQueryBuilderContentGridRecord(lastRowInExcel, sheetName));

				Assert.assertTrue(validateQueryBuilderLayers(driver, fieldValue, queryValue, lastRowInExcel, sheetName));
				break;

			case "Waterfront":
				//To select record from query builder dGrid
				Assert.assertTrue(SeleniumActions.selectQueryBuilderContentGridRecord(lastRowInExcel, sheetName));

				Assert.assertTrue(validateQueryBuilderLayers(driver, fieldValue, queryValue, lastRowInExcel, sheetName));
				break;

			default:
				System.out.println("Layer name is not present");
				break;
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

	// To validate query builder dGrid result with query value
	public static boolean validateQueryBuilderLayers(WebDriver driver, String fieldValue, String queryValue, int lastRowInExcel, String sheetName) throws Exception{
		try {
			String dGridCellValue = SeleniumActions.queryBuilderdGridCellValue(fieldValue, lastRowInExcel, sheetName);
			if(dGridCellValue.equalsIgnoreCase(queryValue)){
				return true;
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return false;
	}

	// To validate query builder Info pop-up result with query value
	public static boolean validateQueryBuilderInfoPoupDetails(WebDriver driver, String fieldValue, String queryValue, int lastRowInExcel, String sheetName) throws Exception{
		try {
			WebElement esriPopup = driver.findElement(By.className("esriPopupVisible"));

			if(esriPopup.isDisplayed()){
				WebElement popupContentPane = esriPopup.findElement(By.className("contentPane"));
				List<WebElement> rightInfoPoupContentList = popupContentPane.findElements(By.className("rightInfoContentDiv"));
				for (WebElement rightInfoPoupContentListItem : rightInfoPoupContentList) {
					if(rightInfoPoupContentListItem.getText().contains(queryValue) ){
						return true;
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
		return false;
	}

	//To verify PDF contents
	public static boolean verifyPDFContent(String pdfUrl, String reqTextInPDF) {
		boolean flag = false;

		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		String parsedText = null;

		try {
			URL url = new URL(pdfUrl);
			BufferedInputStream file = new BufferedInputStream(url.openStream());
			PDFParser parser = new PDFParser(file);

			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(1);

			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
		} catch (MalformedURLException e2) {
			System.err.println("URL string could not be parsed "+e2.getMessage());
		} catch (IOException e) {
			System.err.println("Unable to open PDF Parser. " + e.getMessage());
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}
		System.out.println("+++++++++++++++++");
		System.out.println(parsedText);
		System.out.println("+++++++++++++++++");

		if(parsedText.contains(reqTextInPDF)) {
			flag=true;
		}
		return flag;
	}
}

