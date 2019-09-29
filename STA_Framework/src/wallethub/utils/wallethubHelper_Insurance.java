package wallethub.utils;

import java.awt.Desktop.Action;

/**************************************************
#Project Name: wallethub
#Class Name: wallethubHelper
#Description:1. This class is responsible for all the user defiend methods created for wallethub project
#Owner: Ashis Kumar Pradhan
#Author: Ashis Kumar Pradhan
#Date of creation: 23-sep-2019
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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import wallethub.pages.Insurance;

public class wallethubHelper_Insurance extends TestAnnotations {

	public static WebDriver InvocationDriver;
	public static WebDriver ExecDriver;
	public static InitBrowser BF;
	public static ReusableTestKeywords selUtils;
	public static String CSSvalue = "fill";
	// Initiating the pages
	public void InitatePages(WebDriver driver) {
		WH = new Insurance(driver);
	}

	public Insurance WH;

	/**************************************************
	 * #Functionality Name: STAGeneralConfig() 
	 * #Input parameter : N/A
	 * #Description: This method get the config date and open the Browser
	 * #Owner: Ashis Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan #Date of
	 * creation: 26-Jul-2019 
	 * #Name of person modifying: (Tester Name): #Date of
	 * modification: 
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
	 * #Date of creation: 26-Jul-2019
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
	 * #Date of creation: 26-Jul-2019 
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
				wallethubHelper_Insurance.wallethubTestConfig.put(STAGlobalVar.allTestSuit.get(localList.get(2)).get(k).get(0),
						STAGlobalVar.allTestSuit.get(localList.get(2)).get(k).get(1));
				STALogger.Log(
						"Currently in -->  Loading Key, Value pair of "
								+ STAGlobalVar.allTestSuit.get(localList.get(2)).get(k).get(0) + " , "
								+ STAGlobalVar.allTestSuit.get(localList.get(2)).get(k).get(1) + " into the Map "
								+ wallethubHelper_Insurance.wallethubTestConfig.getClass().getName(),
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
	 * #Functionality name: Login_to_WH() 
	 * #Input parameter : String baseUrl,String title,String username,String Password 
	 * #Description: This method responsible for Logging into the wallethub insurance portal
	 * #Owner: Ashis  Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan 
	 * #Date of creation: 22-sep-2019
	 * #Name of person modifying: (Tester Name): 
	 * #Date of modification:
	 **************************************************/
	public void Login_to_WH(String baseUrl, String title, String username, String Password){
		try {
			
			// Opening url
			selUtils.openUrl(baseUrl);

			// Asserting title of the page
			String ActTitle = selUtils.getTitle();
			if(ActTitle.contains(title))
			{
				selUtils.updateStep("Successfully Landed in wallethub Insurance test page", Status.PASS);
			}
			
			if (selUtils.isElementPresent(WH.login_submit,"Verify if Login span is present")) {
				selUtils.click(WH.login_submit,  "Clicking on Login span");
				if (selUtils.isElementPresent(WH.WHUID_input,"Verify if Username field is present")) {
					selUtils.type(WH.WHUID_input, username, "Providing the username");		
					if (selUtils.isElementPresent(WH.WHSecreat_input,"Verify if Password field is present")) {
						selUtils.type(WH.WHSecreat_input, Password, "Providing the password");			
					if(selUtils.isElementPresent(WH.WHLogIn_input, "Verify if Login Button is present")){
						selUtils.click(WH.WHLogIn_input, "Clicking on Login Button");
						ExecDriver.navigate().to(baseUrl);
						selUtils.updateStep("Successfully Login to wallethub Insurance portal", Status.PASS);
					}else
						selUtils.updateStep("Facebook Login button field is not available", Status.FAIL);
					}else
						selUtils.updateStep("Facebook Secreat field is not available", Status.FAIL);
				} else
					selUtils.updateStep("Facebook Username field is not available", Status.FAIL);
			} else
				selUtils.updateStep("Facebook Login span field is not available", Status.FAIL);
		} catch (Exception e) {
			selUtils.updateStep("Failed to Login to Facebook", Status.FAIL);
		}
	}

	/**************************************************
	 * #Functionality name: WH_InsuranceComment() 
	 * #Input parameter : driver , String Message 
	 * #Description: This method responsible for posting comment into the wallethub insurance test
	 * #Owner: Ashis  Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan 
	 * #Date of creation: 22-sep-2019
	 * #Name of person modifying: (Tester Name): 
	 * #Date of modification:
	 **************************************************/
	public void WH_InsuranceComment(WebDriver BF,String comment){
		try {
			
			
			
			String fill1 = selUtils.getCSSValue(WH.star4th_div,CSSvalue, "");
			if (fill1.equals("rgb(74, 224, 225)"))
			{
				//fill = rgb(74, 224, 225)
				//#4ae0e1
				selUtils.updateStep("Successfully Validated the review start color as #4ae0e1", Status.PASS);
			}
			
			// hover over ratings panel to expose ratings stars
			Actions act = new Actions(ExecDriver);
			act.moveToElement(ExecDriver.findElement(By.cssSelector(".review-action .rvs-star-svg:nth-child(4) path:nth-child(1)"))).perform();

			// click specified star
			ExecDriver.findElement(By.xpath("(//div[@class='rating-box-wrapper'])[3]/*[4]/*/*[@fill='#4ae0e1']")).click();
			
			ExecDriver.findElement(By.xpath("//section[@id='reviews-section']/modal-dialog/div/div/write-review/div/ng-dropdown/div/span")).click();
			ExecDriver.findElement(By.xpath("//section[@id='reviews-section']/modal-dialog/div/div/write-review/div/ng-dropdown/div/ul/li[2]")).click();
			
			selUtils.type(WH.comment_Textbox, comment, "Providing the Review Comment");
			selUtils.click(WH.submit_Button, "Clicking on tyhe submit button");
						
		} catch (Exception e) {
			selUtils.updateStep("Failed to Post comments in wallethub insurance", Status.FAIL);
		}
	}
	
	 /* #Functionality name: WH_VerifyAuthentication() 
	 * #Input parameter : driver , String Message 
	 * #Description: This method responsible for user authentication
	 * #Owner: Ashis  Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan 
	 * #Date of creation: 22-sep-2019
	 * #Name of person modifying: (Tester Name): 
	 * #Date of modification:
	 **************************************************/
	public void WH_VerifyAuthentication(String UN,String Secreat){
		try {
			
			selUtils.click(WH.login_tab, "Clicking on Login tab");
			selUtils.type(WH.email_input,UN , "Providing the User name to login to the wallethub portal");
			selUtils.type(WH.secreat_input, Secreat, "Providing the Secreat name to login to the wallethub portal");
			selUtils.click(WH.login_submit, "Clicking on Submit button");	
			
		} catch (Exception e) {
			selUtils.updateStep("Authentication Failed", Status.FAIL);
		}
	}
	
	 /* #Functionality name: WH_VerifyReviewComment() 
	 * #Input parameter : driver , String Message 
	 * #Description: This method responsible for Verrifying review comment
	 * #Owner: Ashis  Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan 
	 * #Date of creation: 22-sep-2019
	 * #Name of person modifying: (Tester Name): 
	 * #Date of modification:
	 **************************************************/
	public void WH_VerifyReviewComment(String Uname,String comment){
		try {
		
			selUtils.click(WH.testInsurance_text, "Clicking on test Insurance link");
			Thread.sleep(3000);
			String Comment = ExecDriver.findElement(By.xpath("//*[contains(text(),'"+Uname+"')]/../../../div[4]")).getText();
			
			//Validate the review coment
			Assert.assertTrue(Comment.equals(comment), "Comment Verrified successfully");
			
		} catch (Exception e) {
			selUtils.updateStep("Failed to verify comments in wallethub insurance", Status.FAIL);
		}
	}
	
	/*----------------------------------------------------------------------------------------------------------------------
	 *              TEST METHODS ENDS HERE BY ASHIS                                                                       
	 ----------------------------------------------------------------------------------------------------------------------*/

}
