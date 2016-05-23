package createLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import testBase.TestBase;

public class CreateLog extends TestBase {
	// To append the message in text file
	public static void createLogFile(StackTraceElement[] stackTraceElements, Exception e2, String screenShotPath, WebDriver driver) throws Exception{
		//File file = new File("E:/Selenium scripts/MCOEDTest/Logs/ErrorLog.txt");
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String fileName = "E:/Selenium scripts/MCOEDTest/Logs/" + currentDate + "_ErrorLog.txt"; 
		File file = new File(fileName);
		
		// if file doesnt exists, then create it
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Unable to create new file at " + file);
				e.printStackTrace();
			}
		}
		//FileWriter fw = new FileWriter(file.getAbsoluteFile());
		OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(file, true), "UTF-8");
		BufferedWriter bw = new BufferedWriter(writer);
		PrintWriter ps = new PrintWriter(bw, true);
		String curDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E").format(new Date());
		ps.println("======================================================================================");
		//bw.newLine();
		ps.println("Exception time     : " + curDateTime.toString());
		//bw.newLine();
		ps.println("File name          : " + stackTraceElements[1].getFileName());
		//bw.newLine();
		ps.println("Class name         : " + stackTraceElements[1].getClassName());
		//bw.newLine();
		ps.println("Method name        : " + stackTraceElements[1].getMethodName());
		//bw.newLine();
		ps.println("Exception name     : " + e2.getClass().getSimpleName());
		ps.println("Screenshot path    : " + screenShotPath);
		//bw.newLine();
		//ps.println("Below is the exception message : ");
		//bw.newLine();
		//e2.printStackTrace(ps);
		//bw.newLine();
		ps.println("======================================================================================");
		//bw.write("");
		
		bw.close();
		writer.close();
		//assert false;
		//driver.close();
		//Assert.fail();
		//Assert.assertFalse(true);		
	}
}
