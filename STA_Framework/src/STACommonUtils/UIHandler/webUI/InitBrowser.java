package STACommonUtils.UIHandler.webUI;

/**************************************************
#Project Name: wallethub
#Class Name: InitBrowser
#Description: This class is responsible for Initiating the browser and making all desired capability related to browser
#Owner: Ashis Kumar Pradhan
#Author: Ashis Kumar Pradhan
#Date of creation: 26-Jul-2019
#Name of person modifying: (Tester Name): 
#Date of modification: 
‘**************************************************/

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import STACore.staGlobalVariable.STAGlobalVar;

public class InitBrowser {

	public static WebDriver driver;

	public static String browser;
	private static int implicitWait;
	private static int pageLoadTimeOut;
	protected static DesiredCapabilities dCaps;
	public static boolean headless = false;

	/**************************************************
	 * #Functionality Name: openBrowser () Parameters : N/A 
	 * #Description:1. This class is responsible for Instantiating the browser 
	 * #Owner: Ashis Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan 
	 * #Date of creation: 26-Jul-2019 
	 * #Name of person modifying:
	 * (Tester Name): #Date of modification: 
	 **************************************************/

	@SuppressWarnings("deprecation")
	public static WebDriver openBrowser() throws IOException, InterruptedException {
		if (headless == true) {
			try {
				LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
						"org.apache.commons.logging.impl.NoOpLog");
				java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
				java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit")
						.setLevel(java.util.logging.Level.OFF);
				Thread.sleep(500);

				// Maximizing the Browser window
				driver.manage().window().maximize();

				driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(pageLoadTimeOut, TimeUnit.SECONDS);
				driver.manage().timeouts().setScriptTimeout(pageLoadTimeOut, TimeUnit.SECONDS);
				return driver;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// for firefox
		if (browser.equalsIgnoreCase("firefox")) {
			FirefoxProfile profile = new FirefoxProfile();
			String path = System.getProperty("user.dir") + "\\src\\" + STAGlobalVar.AppName + "\\Config\\";
			File file = new File(path);
			System.out.println("this directory is :" + System.getProperty("user.dir"));
			path = file.getCanonicalPath();
			System.out.println(path);
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download.dir", path);
			profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"application/msword,application/csv,text/csv,image/png ,image/jpeg, application/pdf, text/html,text/plain,application/octet-stream");
			profile.setPreference("browser.download.manager.focusWhenStarting", false);
			profile.setPreference("browser.download.useDownloadDir", true);
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			profile.setPreference("browser.download.manager.closeWhenDone", false);
			profile.setPreference("browser.download.manager.showAlertOnComplete", false);
			profile.setPreference("browser.download.manager.useWindow", false);
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability(FirefoxDriver.PROFILE, profile);
			capabilities.setCapability(CapabilityType.SUPPORTS_ALERTS, "true");
			capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
			driver = new FirefoxDriver(capabilities);

			// for chrome
		} else if (browser.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\STACore\\DriverExe\\chromedriver.exe");
			Thread.sleep(500);
			driver = new ChromeDriver(options);

			// for iexplorer
		} else if (browser.equalsIgnoreCase("iexplorer")) {
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + "\\src\\STACore\\DriverExe\\IEDriverServer.exe");
			Thread.sleep(500);
			driver = new InternetExplorerDriver();
		}
		Thread.sleep(300);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(pageLoadTimeOut, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(pageLoadTimeOut, TimeUnit.SECONDS);

		return driver;
	}

	/**************************************************
	 * #Functionality Name: closebrowser () 
	 * #parameters : N/A 
	 * #Description: This class is responsible for closing the web browser 
	 * #Owner: Ashis Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan 
	 * #Date of creation: 26-Jul-2019 
	 * #Name of person modifying: (Tester Name): 
	 * #Date of modification: 
	 **************************************************/

	public void closebrowser() throws InterruptedException {
		Object[] windows = driver.getWindowHandles().toArray();
		for (int i = 0; i < windows.length; i++) {
			driver.switchTo().window(windows[i].toString());
			driver.quit();
		}
	}

	/**************************************************
	 * #Functionality Name: InitBrowser () 
	 * #parameters : N/A 
	 * #Description: This method is responsible for initiating the browser 
	 * #Owner: Ashis Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan 
	 * #Date of creation: 26-Jul-2019 
	 * #Name of person modifying: (Tester Name): 
	 * #Date of modification: 
	 **************************************************/

	public InitBrowser(WebDriver driver, String browser, int implicitWait, int pageLoadTimeOut) {
		this.driver = driver;
		this.browser = browser;
		this.implicitWait = implicitWait;
		this.pageLoadTimeOut = pageLoadTimeOut;

	}

	/**************************************************
	 * #Functionality Name: checkInstanceofPhantom () 
	 * #parameters : N/A
	 * #Description: This method is responsible for invoking browser without a
	 * graphical user interface 
	 * #Owner: Ashis Kumar Pradhan #Author: Ashis Kumar
	 * Pradhan #Date of creation: 26-Jul-2019 #Name of person modifying: (Tester
	 * Name): #Date of modification: ‘
	 **************************************************/

	@SuppressWarnings("unused")
	private Boolean checkInstanceofPhantom() throws IOException {
		Boolean found = false;
		String pidInfo;
		Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

		String line;
		while ((line = input.readLine()) != null) {
			pidInfo = line;
			if (pidInfo.contains("phantomjs.exe")) {
				found = true;
			}
		}
		return found;
	}
}