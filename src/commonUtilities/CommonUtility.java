package commonUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;

import createLog.CreateLog;
import testBase.TestBase;

public class CommonUtility extends TestBase {
	
	/*To read the appConfig file */
	public static Properties readConfig(Properties properties) throws Exception{
		Properties prop = null;
		try {
			prop = new Properties(); //used to maintain lists of values in which the key is a String and the value is also a String.
			String configPath = "E:\\Selenium scripts\\MCOEDTest\\src\\appConfig\\config.properties";
			InputStream inputstream = null;
			try {
				inputstream = new FileInputStream(configPath);
			} catch (FileNotFoundException e) {
				StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
				String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
				CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			}
			// TODO Auto-generated catch block
			try {
				prop.load(inputstream);
			} catch (IOException e) {
				StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
				String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
				CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			}
		} catch (Exception e) {
			// TODO: handle exception
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
		}
		return prop;
	}

	// Wait till splash screen loading indicator is displayed
	/*public static void splashScreenLoadingIndicatorWait(WebDriver driver, WebElement element, int timeoutSeconds, String className) throws Exception{
           driver.switchTo().defaultContent();
           WebElement loading= null;
           int i=0;
           do
             {
	              i++;
	              Thread.sleep(1000);
	              loading = element.findElement(By.className(className)); 
             }
           while (loading.isDisplayed() && (i <= timeoutSeconds));
    }*/

	// To get the excel data
	public static String[][] getExcelData(String filepath, String filename, String SheetName)throws Exception {
		String[][] tabArray = null;
		Workbook workbk = null;
		Row row;
		//int rowCount, colCount;

		//Create a object of File class to open xlsx file
		File file= new File(filepath+'\\'+filename);

		//Create an object of FileInputStream class to read excel file
		FileInputStream inputstream= new FileInputStream(file);

		//Find the file extension by splitting file name in substring and getting only extension name
		String fileExtensionName= filename.substring(filename.indexOf("."));

		//Check condition if the file is xlsx or xls file
		if (fileExtensionName.equals(".xlsx")) {
			workbk= new XSSFWorkbook(inputstream);
			System.out.println("Xlsx file");
			//log.info("Excel file is" + fileExtensionName);
		} else if (fileExtensionName.equals(".xls")) {
			workbk= new HSSFWorkbook(inputstream);
			//log.info("Excel file is" + fileExtensionName);
		}else {
			System.out.println("File Name not found");
			//log.error("File name not found");
		}

		//Read sheet inside the workbook by its name
		Sheet sheet= workbk.getSheet(SheetName);

		//Find number of rows in excel file
		int rowCount= sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colCount = row.getLastCellNum();
		tabArray = new String[rowCount][colCount];

		//Create a loop over all the rows of excel file to read it
		for (int i = 1, k = 0; i <= rowCount; i++, k++) {
			row = sheet.getRow(i);
			for(int j = 0; j < colCount; j++){
				/*DataFormatter will format the cell and giving you back
				a nicely formatted string at the end.*/
				DataFormatter df = new DataFormatter();
				Cell cell = row.getCell(j);
				String asItLooksInExcel = df.formatCellValue(cell);
				System.out.println(asItLooksInExcel);
				tabArray[k][j] = asItLooksInExcel;
			}
		}
		return (tabArray);
	}

	// To capture screenshot
	public static String getscreenshot(StackTraceElement[] stackTraceElements) throws Exception {
		String currentDate = new SimpleDateFormat("yyyy-MM-dd_hhmmss").format(new Date());
		String methodName = stackTraceElements[1].getMethodName();
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//The below method will save the screen shot in d drive with name "screenshot.png"
		//String methodName = tr.getName();
		String screenShotPath = "E:/MCOED/Screenshots/" + currentDate + "_" + methodName + "_" + "screenshot.png";
		FileUtils.copyFile(scrFile, new File(screenShotPath));
		return screenShotPath;
	}

	//To write in excel sheet
	public static void writeResultsInExcel(int excelRow, String status, String sheetName, String screenShotPath){
		String targetFile = null;
		try {
			String sourceExcelFile = "E:\\Selenium scripts\\MCOEDTest\\excelData\\McoedData.xlsx";
			
			FileInputStream file = new FileInputStream(sourceExcelFile);
			Workbook workbook = null;
			
			if(sourceExcelFile.endsWith("xlsx")){
				workbook = new XSSFWorkbook();
			}else if(sourceExcelFile.endsWith("xls")){
				workbook = new HSSFWorkbook();
			}
			
			workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheet(sheetName);
			System.out.println("Sheet name is : " + sheetName);
			System.out.println("Excel row is :" + excelRow);
			int col = sheet.getRow(excelRow).getLastCellNum();
			System.out.println("Last cell Num : " + col);
			
			/* Let us create some XSSFHyperlink objects */
	        /* First get XSSFCreationHelper object using the workbook*/
	        CreationHelper helper= workbook.getCreationHelper();
	        
	        /* Now use createHyperlink method to get XSSFHyperlink */
	        //Hyperlink url_link=helper.createHyperlink(Hyperlink.LINK_URL);
	        Hyperlink file_link=helper.createHyperlink(Hyperlink.LINK_FILE);
	        //Hyperlink email_link=helper.createHyperlink(Hyperlink.LINK_EMAIL);
			
			//To write screenshot url in excel
			if(status.equalsIgnoreCase("Fail")){
				/* Define the data for these hyperlinks */
		        file_link.setAddress(screenShotPath);
		        
				//int cellNumber = sheet.getRow(excelRow).getLastCellNum();
				Cell cell = sheet.getRow(excelRow).createCell(col);
				CellStyle style = workbook.createCellStyle();
				
				// Create a new font and alter it.
				Font font = workbook.createFont();
				font.setColor(IndexedColors.BLUE.getIndex());
				font.setUnderline(Font.U_SINGLE);
				style.setFont(font);
				
				cell.setCellStyle(style);
				cell.setCellValue(status);
				cell.setHyperlink(file_link);
			} else {
				sheet.getRow(excelRow).createCell(col).setCellValue(status);
			}
			FileOutputStream outFile = new FileOutputStream(new File(sourceExcelFile));
			workbook.write(outFile);
			workbook.close();
			outFile.close();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// To copy excel file from one location to other
	public static File copyExcelFile(String excelFile){
		//String excelFile1 = "E:\\Idhao\\McoedData.xlsx";
		String target = "E:\\MCOED\\";
		File sourceFile = new File(excelFile);
		String name = sourceFile.getName();
		System.out.println(sourceFile);
		File targetFile = new File(target+name);
		System.out.println(targetFile);
		try {
			FileUtils.copyFile(sourceFile, targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return targetFile;
	}
}

