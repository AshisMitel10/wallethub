package wallethub.utils;

import java.awt.Desktop.Action;

/**************************************************
#Project Name: wallethub
#Class Name: wallethubHelper
#Description:1. This class is responsible for all the user defiend methods created for wallethub project
#Owner: Ashis Kumar Pradhan
#Author: Ashis Kumar Pradhan
#Date of creation: 26-Jul-2019
#Name of person modifying: (Tester Name): 
#Date of modification: 
‘**************************************************/

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import com.aventstack.extentreports.Status;
import STACommonUtils.UIHandler.webUI.InitBrowser;
import STACore.logger.STALogger;
import STACore.staGlobalVariable.STAGlobalVar;
import STACore.staUtils.ReusableTestKeywords;
import STACore.staUtils.TestAnnotations;
import wallethub.pages.facebook;

public class wallethubHelper_FB extends TestAnnotations {

	public static WebDriver InvocationDriver;
	public static WebDriver ExecDriver;
	public static InitBrowser BF;
	public static ReusableTestKeywords selUtils;

	// Initiating the pages
	public void InitatePages(WebDriver driver) {
		FB = new facebook(driver);
	}

	public facebook FB;

	/**************************************************
	 * #Functionality Name: STAGeneralConfig() 
	 * #Input parameter : N/A
	 * #Description: This method get the config date and open the Browser
	 * #Owner: Ashis Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan 
	 * #Date of creation: 26-Jul-2019 
	 * #Name of person modifying: (Tester Name): 
	 * #Date of modification: 
	 **************************************************/

	@BeforeMethod
	public void STAGeneralConfig(Method CurrTestMethod) throws InterruptedException {
		STAGlobalVar.CurrentExecTestCaseName = CurrTestMethod.getName();
		STALogger.InitiateExtTest(STAGlobalVar.CurrentExecTestCaseName);
		STALogger.Log("Initiating the Test : ==> " + STAGlobalVar.CurrentExecTestCaseName,
				STAGlobalVar.ProcessState.Initiated.toString(), STALogger.LogType.Info);

		// Initiating the browser with Implicit wait and maximum page load
		// timeout
		BF = new InitBrowser(InvocationDriver,
				STAGlobalVar.testCasesToExecute.get(STAGlobalVar.CurrentExecTestCaseName).getBrowser(), 10, 40);
		try {
			ExecDriver = STACommonUtils.UIHandler.webUI.InitBrowser.openBrowser();
			selUtils = new ReusableTestKeywords(ExecDriver);
			InitatePages(ExecDriver);
		} catch (IOException e) {
			e.printStackTrace();
			STALogger.Log(e, "A Driver functions Exceptions has occured with " + e.getMessage()
					+ ". While launching the browser.", STALogger.LogType.Error);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**************************************************
	 * #Functionality Name: TearDownBrowser() 
	 * #Input parameter : N/A
	 * #Description: This method responsible for closing Browser 
	 * #Owner: Ashis Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan 
	 * #Date of creation: 26-sep-2019
	 * #Name of person modifying: (Tester Name): 
	 * #Date of modification: 
	 **************************************************/

	@AfterMethod
	public void TearDownBrowser(ITestResult result) throws IOException {
		try {
			BF.closebrowser();
		} catch (InterruptedException e) {
			e.printStackTrace();
			STALogger.Log(e,
					"A Driver Functions Exception occured during the tear down. Message is : " + e.getMessage(),
					STALogger.LogType.Error);
		}

		STALogger.ExtHTMLReport.flush();
		STAGlobalVar.ScreenShotNumber = 1;
		if (result.getStatus() == ITestResult.FAILURE) {
			STAGlobalVar.FailedTests.put(result.getName(), true);
		}
	}

	// This HashMap Holds the complete Test Bed configuration of wallethub.
	public static HashMap<String, String> wallethubTestConfig = new HashMap<String, String>();

	// This HashMap holds the information about which configuration is set to
	// which Test Suite.
	// eg : (Sanity , Config001).
	public static HashMap<String, String> TestSuiteConfigMap = new HashMap<String, String>();

	// This HashMap holds the information about the which Test case should use
	// what configuration based on Test Suite.
	// eg: (Testcase1_Test, Config001; from above example.)
	public static HashMap<String, String> TestCaseConfigMap = new HashMap<String, String>();

	/**************************************************
	 * #Functionality name: LoadConfigData() 
	 * #Input parameter : N/A
	 * #Description: This method responsible for loading the test data from the Configsheet
	 * #Owner: Ashis Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan
	 * #Date of creation: 26-sep-2019 
	 * #Name of person modifying: (Tester Name):
	 * #Date of modification: 
	 **************************************************/
	@BeforeTest
	public static void LoadConfigData() {

		// Generating wallethub Test Suite to Configuration Map.

		HashMap<String, LinkedHashMap<Integer, LinkedList<String>>> localdata = new HashMap<String, LinkedHashMap<Integer, LinkedList<String>>>();
		int i = 1;
		while (i < STAGlobalVar.allTestSuit.get("TestPlan").size()) {
			List<String> localList = STAGlobalVar.allTestSuit.get("TestPlan").get(i);
			System.out.println("The config that is to be loaded is : " + localList.get(2));
			String confName = localList.get(2);
			STALogger.Log("Loading the Application Test Configuration from " + confName,
					STAGlobalVar.ProcessState.Initiated.toString(), STALogger.LogType.Info);
			for (int k = 1; k < STAGlobalVar.allTestSuit.get(localList.get(2)).size(); k++) {
				wallethubHelper_FB.wallethubTestConfig.put(STAGlobalVar.allTestSuit.get(localList.get(2)).get(k).get(0),
						STAGlobalVar.allTestSuit.get(localList.get(2)).get(k).get(1));
				STALogger.Log(
						"Currently in -->  Loading Key, Value pair of "
								+ STAGlobalVar.allTestSuit.get(localList.get(2)).get(k).get(0) + " , "
								+ STAGlobalVar.allTestSuit.get(localList.get(2)).get(k).get(1) + " into the Map "
								+ wallethubHelper_FB.wallethubTestConfig.getClass().getName(),
						STAGlobalVar.ProcessState.Initiated.toString(), STALogger.LogType.Info);
			}
			i++;
		}
	}

	public static List<String> PopulateData(String TCData) {

		List<String> TCdata = new ArrayList<String>();

		if ((TCData != null && TCData.indexOf(",") > 0) || TCData.indexOf(",") < 0) {

			StringTokenizer sTokenizer = new StringTokenizer(TCData, ",");
			while (sTokenizer.hasMoreElements()) {
				TCdata.add(sTokenizer.nextElement().toString());

			}
		} else {
			TCdata.add(TCData);
		}
		return TCdata;
	}

	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------
	 *   			 	Below are the test methods which supports the test cases  
	 *                                       METHODS BY ASHIS
	 ------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	/**************************************************
	 * #Functionality name: Login_to_FB() 
	 * #Input parameter : String baseUrl,String title,String username,String Password 
	 * #Description: This method responsible for Logging into the facebook
	 * #Owner: Ashis  Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan 
	 * #Date of creation: 22-sep-2019
	 * #Name of person modifying: (Tester Name): 
	 * #Date of modification: ‘
	 **************************************************/
	public void Login_to_FB(String baseUrl, String title, String username, String Password){
		try {
			
			// Opening url
			selUtils.openUrl(baseUrl);

			// Asserting title of the page
			Assert.assertEquals(selUtils.getTitle(), title);

			// Logging into Facebook
			if (selUtils.isElementPresent(FB.FBUID_input,"Verify if UserID input field is present")) {
				selUtils.type(FB.FBUID_input, username, "Providing the username");
				if (selUtils.isElementPresent(FB.FBSecreat_input,"Verify if Password field is present")) {
					selUtils.type(FB.FBSecreat_input, Password, "Providing the password");
					if(selUtils.isElementPresent(FB.LogIn_input, "Verify if Login Button is present")){
						selUtils.click(FB.LogIn_input, "Clicking on Login Button");
						selUtils.updateStep("Successfully Login to Facebook", Status.PASS);
					}
					
				} else
					selUtils.updateStep("Facebook Password field is not available", Status.FAIL);
			} else
				selUtils.updateStep("Facebook Username field is not available", Status.FAIL);
		} catch (Exception e) {
			selUtils.updateStep("Failed to Login to Facebook", Status.FAIL);
		}
	}

	/**************************************************
	 * #Functionality name: FB_StatusPosting() 
	 * #Input parameter : driver , String Message 
	 * #Description: This method responsible for posting status message in facebook
	 * #Owner: Ashis  Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan 
	 * #Date of creation: 22-sep-2019
	 * #Name of person modifying: (Tester Name): 
	 * #Date of modification:
	 **************************************************/
	public void FB_StatusPosting(String Message){
		try {
			
			WebDriverWait wait = new WebDriverWait(ExecDriver, 500);
		    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//textarea")));
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea")));
		    ExecDriver.findElement(By.xpath("//textarea")).click();
		    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@contenteditable='true']")));
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@contenteditable='true']")));     
		    ExecDriver.findElement(By.xpath("//div[@contenteditable='true']")).sendKeys(Message);
		    if(selUtils.isElementPresent(FB.StatusPost_span, "Verify if Post button is present"))
		     {
		     selUtils.click(FB.StatusPost_span, "Posting the Status");
		     selUtils.updateStep("Successfully posted Facebook status Mesage", Status.PASS);
		     }
		    else
		     {
		    	 selUtils.updateStep("Post Button is not present", Status.FAIL);
		     }
		    				
		} catch (Exception e) {
			selUtils.updateStep("Failed to Post status message in Facebook", Status.FAIL);
		}
	}
	
	/*----------------------------------------------------------------------------------------------------------------------
	 *              TEST METHODS ENDS HERE BY ASHIS                                                                       
	 ----------------------------------------------------------------------------------------------------------------------*/

}
