package testBase;

import java.util.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import commonUtilities.CommonUtility;
import createLog.CreateLog;


public class TestBase {
	
	public static WebDriver driver = null;
	//public static int excelRowcount = 0;
	
	public static boolean setup(Object properties, int lastRowInExcel, String sheetName) throws Exception {
		//Logger logger = Logger.getLogger(TestBase.class);
		try {
			Properties prop = CommonUtility.readConfig((Properties) properties);
			String browser= prop.getProperty("Browser");
			switch(browser){
				case "Firefox":
					driver = new FirefoxDriver();
					break;
				
				case "Chrome":
					String chromeDir = System.getProperty("user.dir") +"\\ChromeDriver\\chromedriver.exe";
					System.setProperty("webdriver.chrome.driver", chromeDir);
					driver = new ChromeDriver();
					break;
					
				case "IE":
					String IEDir = System.getProperty("user.dir") +"\\IEDriver\\IEDriverServer.exe";
					System.setProperty("webdriver.ie.driver", IEDir);
					//DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
					
					//Capability that defines to clean or not browser cache before launching IE by IEDriverServer
					//capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
					
					//Capability that defines to ignore or not browser protected mode settings during starting by IEDriverServer
					//capabilities.setCapability(InternetExplorerDriver.
							//INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
					
					driver = new InternetExplorerDriver();
					break;
				
				default:
					System.out.println(browser + " not found.");
			 }
			
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			Thread.sleep(3000);
			//driver.navigate().refresh();
			//log.info(browser + " browser is opened successfully.");
			String Url= prop.getProperty("url");
			driver.get(Url);
			//log.info(Url + " is launched successfully.");// URL retrieved
		} catch (Exception e) {
			//boolValue = false;
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String screenShotPath = CommonUtility.getscreenshot(stackTraceElements);
			CommonUtility.writeResultsInExcel(lastRowInExcel, "FAIL", sheetName, screenShotPath);
			CreateLog.createLogFile(stackTraceElements, e, screenShotPath, driver);
			return false;
		}
		return true;
	}

	
	/* To quit the browser*/
	public static void closeBrowser(){
		driver.quit();
		//log.info("Browser is closed successfully.");
	}

}

