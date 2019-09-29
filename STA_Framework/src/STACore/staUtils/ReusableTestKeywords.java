/*----------------------------------------------------------------------------------------------------------------------

 *             THIS CLASS STORES ALL TEST CASE LEVEL KEYWORDS                                                        |

----------------------------------------------------------------------------------------------------------------------*/

package STACore.staUtils;

/**************************************************
#Project Name: wallethub
#Class Name: ReusableTestKeywords()
#Description: This Class stores all the test case level keywords
#Owner: Ashis Kumar Pradhan
#Author: Ashis Kumar Pradhan
#Date of creation: 26-Jul-2019
#Name of person modifying: (Tester Name): 
#Date of modification: 
‘**************************************************/

import java.awt.AWTException;

import java.awt.Robot;

import java.awt.Toolkit;

import java.awt.datatransfer.StringSelection;

import java.awt.event.KeyEvent;

import java.io.BufferedReader;

import java.io.DataInputStream;

import java.io.File;

import java.io.FileFilter;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.IOException;

import java.io.InputStreamReader;

import java.util.Arrays;

import java.util.Random;

import java.util.Scanner;



import org.apache.commons.io.FileUtils;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import org.openqa.selenium.Alert;

import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.Keys;

import org.openqa.selenium.NoAlertPresentException;

import org.openqa.selenium.NoSuchElementException;

import org.openqa.selenium.OutputType;

import org.openqa.selenium.TakesScreenshot;

import org.openqa.selenium.TimeoutException;

import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.ie.InternetExplorerDriver;

import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.Select;

import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.model.Log;

import STACommonUtils.UIHandler.webUI.InitBrowser;
import STACore.logger.STALogger;
import STACore.logger.STALogger.LogType;
import STACore.staGlobalVariable.STAGlobalVar;

public class ReusableTestKeywords extends ReusableUiCoreKeywords {

	public static WebDriver ExecDriver;

	public ReusableTestKeywords(WebDriver driver) {
		this.ExecDriver=driver;
	}

	public ReusableTestKeywords() {
	}
	
	/**
	 * The method highlights the WebElement
	 * @param ele
	 * @throws InterruptedException
	 */
	private void highlightElement(WebElement element) throws InterruptedException {
			if (InitBrowser.headless == true) {
				return;
			}
			try {
			JavascriptExecutor js = (JavascriptExecutor) ExecDriver;
			 js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
			element, "color: ; border: 5px solid yellow;");
			 updateStep("Successfully highlighted the WebElement", Status.PASS);
			STALogger.UpdateReport(Status.PASS, "Highlight successful ");
			}
	 catch (Exception e) {
		updateStep("Exception \t"+e.getClass().getSimpleName(), Status.FAIL);
		STALogger.UpdateReport(Status.FAIL, "Highlight unsuccessful");
	}
		}
		
	/**
	 * This method unhighlight the WebElement
	 * @param ele
	 * @throws InterruptedException
	 */
	private void unhighlight(WebElement element) throws InterruptedException {
		if (InitBrowser.headless == true) {
			return;
		}
		try {
		JavascriptExecutor js = (JavascriptExecutor) ExecDriver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",element, "");
		updateStep("Successfully highlighted the WebElement", Status.PASS);
		STALogger.UpdateReport(Status.PASS, "Unhighlight successful ");
		}
		 catch (Exception e) {
			 updateStep("Exception \t"+e.getClass().getSimpleName(), Status.FAIL);
			STALogger.UpdateReport(Status.FAIL, "Unhighlight unsuccessful");
		}
	}
	
	/**
	 * The method take the Screenshot of the Page. CALLED BY OTHER METHODS.
	 * @param Path
	 * @throws IOException
	 */
	private void takescreenshot(String Path) throws IOException {

		if (InitBrowser.headless == true) {
			return;
		}
		 try {
			 	File scrFile = ((TakesScreenshot) ExecDriver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(Path));
				updateStep("Successfully taken the screenshot", Status.PASS);
				STALogger.UpdateReport(Status.PASS, "Screenshot successful ");
			 }
			 catch (Exception e) {
					 updateStep("Exception \t"+e.getClass().getSimpleName(), Status.FAIL);
					STALogger.UpdateReport(Status.FAIL, "Screenshot unsuccessful");
				}
	}

	/**
	 * This method can be used for any operations.Its collects the starts of the WebElement and returns the webelement.
	 * @param element
	 * @param description
	 * @return WebElement
	 * @throws IOException
	 */
	public WebElement executeStep(WebElement element, String description) throws IOException {
		int xCoordinate = 0;
		int yCoordinate = 0;
		String screenshotPath = reportPathBrowser + "\\screenshots\\"+ serielNo + ".png";
		try {
			xCoordinate = element.getLocation().x;
			yCoordinate = element.getLocation().y;
			highlightElement(element);
			status = "Pass";
			if (screenshotOnFail.equals("Y")) {
				System.out.println("Only screenshot will be captured in failed cases");
			} else {
				takescreenshot(screenshotPath);
			}
			Thread.sleep(500);
			unhighlight(element);
		} catch (Exception e) {
			STALogger.UpdateReport(Status.FAIL, "Element Not Found" + " ==> Exception: "+"\n\t\t"+ e.getClass().getSimpleName());
		} 
		return element;
	}

	/**
	 * This method send info to the Framework for reporting purpose
	 * @param description
	 * @param status
	 */
	public void updateStep(String description, Status status) {
		 STALogger.Log(description, status);
		 String currentTCName = STAGlobalVar.CurrentExecTestCaseName;
		 String screenshotpath = STAGlobalVar.ScreenShotPath; 
		 //Checking if the test case has Broswer set.
		 if(STAGlobalVar.testCasesToExecute.get(currentTCName).getBrowser() != "NA") {
			 String screenshotPath = screenshotpath + "\\"+ currentTCName+"_"+STAGlobalVar.ScreenShotNumber + ".png";
			 if (screenshotOnFail.equalsIgnoreCase("Y")) {
				 if (status.equals(Status.FAIL)) {
					 try {
						 takescreenshot(screenshotPath);
						 STALogger.UpdateReport(status, description, screenshotPath);
						 STAGlobalVar.ScreenShotNumber++;
					 } catch (IOException e) {
						 e.printStackTrace();
					 }
				 }
			 } else {
				 try {
					 takescreenshot(screenshotPath);
					 STAGlobalVar.ScreenShotNumber++;				 
					 if (status.equals(Status.FAIL)) {
						 STALogger.UpdateReport(status, description, screenshotPath);
					 }				 
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 } 			 
		 }
		 if (status.equals(Status.FAIL)) {
			 Assert.assertTrue(false);
		 }
	 }

	/**
	 * This method will validate the downloaded file
	 *@param path
	 * @param file name
	 */
	public boolean isFileDownloaded(String downloadPath, String fileName) {
		boolean flag = false;
		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();
		for (int i = 0; i < dir_contents.length; i++) {

			if (dir_contents[i].getName().equals(fileName))

				return flag=true;
		}
		return flag;
	}



	/*----------------------------------------------------------------------------------------------------------------------
	 *                                              Driver related functions                                              |
     ----------------------------------------------------------------------------------------------------------------------*/

	/**
	 * This method opens the Url in the Browser
	 * @param url
	 */
	public void openUrl(String url) {
		try {
			//ExecDriver.get(url);
			ExecDriver.navigate().to(url);
			STALogger.UpdateReport(Status.PASS, "Opening the URL: " + url);

		} catch (Exception e) {
			e.getStackTrace();
			STALogger.UpdateReport(Status.FAIL, "Opening the URL: " + url + "Failed & Exception is : ==> " + "\n\t\t"+ e.getClass().getSimpleName());
		}
	}
	
	/**
	 * This method transfers the control to the Child window based on Index.Base
	 * Window has index of 0.
	 * @param window
	 * @throws Exception
	 */
	public void switchWindow(int window) throws Exception {
		try {
     		Object[] windows = ExecDriver.getWindowHandles().toArray();
			ExecDriver.switchTo().window(windows[window].toString());
			STALogger.UpdateReport(Status.PASS, "Switched the window successfully of index " + window);

		} catch (Exception e) {
			STALogger.UpdateReport(Status.FAIL, "Switching to Window of index : " + window + "Failed & the Exception is : ==> "+ "\n\t\t" + e.getClass().getSimpleName());
		}
	}

	/**
	 * This method transfers the control to the child window based on the Title.
	 * @param title
	 * @throws Exception
	 **/
	public void switchWindow(String title) throws Exception {
		try {
			Object[] windows = ExecDriver.getWindowHandles().toArray();
			for (int i = 0; i < windows.length; i++) {
				ExecDriver.switchTo().window(windows[i].toString());
				if (ExecDriver.getTitle().trim().equalsIgnoreCase(title)) {
					STALogger.UpdateReport(Status.PASS, "Successfully switched to window " + title);
					return;
				}
			}
		} catch (Exception e) {
			STALogger.UpdateReport(Status.FAIL, "Switching to Window : " + title + "Failed & the Exception is : ==> "+ "\n\t\t" + e.getClass().getSimpleName());
		}
	}

	/**
	 * This Method Clicks OK on the Javascript Alert
	 **/
	public void alertAccept() {
		try {
			Alert alert = ExecDriver.switchTo().alert();
			alert.accept();
			STALogger.UpdateReport(Status.PASS, "Accepted Alert");
		} catch (Exception e) {
			STALogger.UpdateReport(Status.FAIL, "Failed to Accept the Alert");
		}
	}
	
	/**
	 * This Method clicks the Cancel Button on Javascript Alert.
	 */
	public void alertReject() {
		try {
			Alert alert = ExecDriver.switchTo().alert();
			alert.dismiss();
			 STALogger.UpdateReport(Status.PASS, "Dismiss Alert");
		} catch (Exception e) {
			 STALogger.UpdateReport(Status.FAIL, "Failed to Dismiss Alert");
		}
	}

	/**
	 * This Method Returns the Text Present on the alert
	 * @return String
	 */
	@SuppressWarnings("finally")
	public String getAlertdata() {
		try {
			Alert alert = ExecDriver.switchTo().alert();
			String text = alert.getText();
			STALogger.UpdateReport(Status.PASS, "Successfully got the text from Alert ");
			return text;
		}
		catch (UnhandledAlertException e) {
			UnhandledAlertException ua = new UnhandledAlertException(Browser);
			STALogger.UpdateReport(Status.FAIL, "Failed to get the Alert text");
			return ua.getAlertText();
		}
	}



	/**

	 * This Method writes into the textbox present on the alert

	 *

	 * @param input

	 */

	public void setAlert(String input) {

		//Log.info(".......SET ALERT DATA...........");

		try {

			Alert alert = ExecDriver.switchTo().alert();

			alert.sendKeys(input);

			alert.accept();

			//updateStep("Set  Alert", "Pass");

		} catch (Exception e) {



		}

	}



	/**

	 * This Method checks if the alert is Present.

	 *

	 * @return Boolean

	 */



	public Boolean isAlertPresent() {

		// Log.info(".......IS ALERT PRESENT...........");

		try {

			Alert alert = ExecDriver.switchTo().alert();

			// alert.

			 STALogger.UpdateReport(Status.PASS, "Alert  Present");
			return true;

		} catch (NoAlertPresentException e) {

			STALogger.UpdateReport(Status.FAIL, "Alert Not Present :  ==>" + "\n\t\t"+ e.getClass().getSimpleName());
			return false;

		} catch (UnhandledAlertException e2) {
			STALogger.UpdateReport(Status.FAIL, "Alert Not Present :  ==>" + "\n\t\t"+ e2.getClass().getSimpleName());
			return true;
		}
	}



	/**

	 * This Method checks if a webelement is Present or not.

	 *

	 * @param ele

	 * @param description

	 * @return Boolean

	 */

	public Boolean isElementPresent(WebElement ele, String description) {

		//Log.info(".......IS ELEMENT PRESENT...........");

		try {

			highlightElement(ele);
			STALogger.UpdateReport(Status.PASS, "Element found on the page. " + ele);
			unhighlight(ele);

			return true;

		} catch (Exception e) {

			//updateStep(" ElementNot Found :" + description, "Fail");
			STALogger.UpdateReport(Status.FAIL, "Element not found on the page. " + ele + "the exception caught is : ==> " + e.getMessage());
			return false;

		}

	}



	/**

	 * This Method compares 2 values of any datatype.

	 *

	 * @param Expected

	 * @param Actual

	 * @return Generics

	 * @throws Exception

	 */

	public <T> T assertEquals(T Expected, T Actual) {

		try {

			//Log.info(".......CHECKS IF EQUAL...........");

			//Log.info("Exp :" + Expected);

			//Log.info("Act :" + Actual);

			if (Expected.equals(Actual)) {

				STALogger.UpdateReport(Status.PASS, "Validation Passed -Expected:  " + Expected + " Actual: " + "Actual" + "\n\t\t"+ getClass().getSimpleName());


				//updateStep("Validation Passed -Expected: " + Expected + " Actual: " + Actual, "Pass");

			} else {
				STALogger.UpdateReport(Status.FAIL, "Validation Passed -Expected:  " + Expected + " Actual: " + "Actual" + "\n\t\t"+ getClass().getSimpleName());


				//updateStep("Validation Failed -Expected: " + Expected+ " Actual: " + Actual, "Fail");

			}

		} catch (Exception e) {
			STALogger.UpdateReport(Status.FAIL, "Validation Passed -Expected:  " + Expected + " Actual: " + "Actual" + "\n\t\t"+ getClass().getSimpleName());

			//updateStep("Validation Failed -Expected: " + Expected + " Actual: "+ Actual, "Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		}

		return Actual;

	}



	/**

	 * This method validates if the Input is true.

	 *

	 * @param Result

	 * @throws Exception

	 */

	public void assertTrue(Boolean Result) throws Exception {

		//Log.info(".......CHECKS IF TRUE...........");

		if (Result.equals(true)) {

			//updateStep("Validation Passed", "Pass");

		} else {



			//updateStep("Validation Failed", "Fail");

		}



	}



	/**

	 * This Method Returns the string present in a BaseString by taking the

	 * endpoint Strings.

	 *

	 * @param baseword

	 * @param startword

	 * @param endword

	 * @return String

	 */

	public String getStringValue(String baseword, String startword, String endword) {

		//Log.info(".......getStringValue...........");

		int a = startword.length();

		int b = baseword.indexOf(startword);

		int startPoint = a + b;

		int length = baseword.substring(a + b).indexOf(endword);

		int endPoint = startPoint + length;

		//Log.info(baseword.substring(a + b));

		//Log.info(baseword.substring(a + b).indexOf(" "));



		return (baseword.substring(startPoint, endPoint));



	}

	
	


	/**

	 * This Method transfers the control to the frame based on FrameId

	 *

	 * @param frameid

	 */



	public void switchFrame(String frameid) {

		try {

			ExecDriver.switchTo().frame(frameid);

			//updateStep("Switch to Frame " + frameid, "Pass");

		} catch (Exception e) {

			//updateStep("Switch to Frame " + frameid, "Fail - Exception: "+ "\n\t\t" + e.getClass().getSimpleName());

		}

	}

	/**

	 * This Method transfers the control to the frame based on FrameId

	 *

	 * @param frameid

	 */



	public void switchFrame(int frameid) {

		try {

			ExecDriver.switchTo().frame(frameid);

			//updateStep("Switch to Frame " + frameid, "Pass");

		} catch (Exception e) {

			//updateStep("Switch to Frame " + frameid, "Fail - Exception: "+ "\n\t\t" + e.getClass().getSimpleName());

		}

	}
	

	/**

	 * This Method transfers the control to the frame present at the WebElement

	 *

	 * @param element

	 */



	public void switchFrame(WebElement element) {

		//Log.info("------------SWITCH FRAME-------");



		try {

			ExecDriver.switchTo().frame(element);

			//updateStep("Switch to Frame ", "Pass");

		} catch (Exception e) {

			//updateStep("Switch to Frame ", "Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		}

	}



	/**

	 * This Method Transfers the control from frames to the Default page.

	 *

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void switchtoDefaultContent() throws IOException,

	InterruptedException {

		ExecDriver.switchTo().defaultContent();

		//updateStep("Switch to Default Content ", "Pass");

	}



	/**

	 * This Method waits for the Element to be visible till the time provided as

	 * input.Polls every second Input time in miliseconds.

	 *

	 * @param element

	 * @param time_in_ms

	 */

	public void waitForElementPresent(WebElement element, int time_in_ms) {

		int waittime = 0;

		Boolean bln_found = false;

		while (waittime < time_in_ms) {

			try {

				highlightElement(element);

				//updateStep("Element found", "Pass");

				bln_found = true;

				return;

			} catch (NoSuchElementException e) {

				waittime = waittime + 1000;

			} catch (InterruptedException e) {

			}

		}

		if (bln_found == false) {

			//updateStep("Element Not  found", "Fail");

		}

	}



	/**

	 * This method is a hard wait.

	 *

	 * @param time_in_ms

	 * @throws InterruptedException

	 */

	public void wait(int time_in_ms) throws InterruptedException {

		Thread.sleep(time_in_ms);

	}



	/**

	 * This method enables execution of Javascript on the WebElement

	 *

	 * @param action

	 * @param element

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void executeJScript(String action, WebElement element) throws IOException, InterruptedException {

		try {

			highlightElement(element);

			JavascriptExecutor js = (JavascriptExecutor) ExecDriver;

			js.executeScript(action, element);

			unhighlight(element);

			//updateStep("Javascript Command", "Pass");

		} catch (Exception e) {

			//updateStep("Error in Executing Javascript", "Fail - Exception: "+ "\n\t\t" + e.getClass().getSimpleName());

		}

	}



	/**
	 * This method performs JavaScriptClick Operation on the WebElement
	 * @param ele
	 * @param description
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void executeJScriptClick(WebElement ele, String description) throws IOException, InterruptedException {

		try {
			highlightElement(ele);	
			WebElement element = ele;
			JavascriptExecutor executor = (JavascriptExecutor)ExecDriver;
			executor.executeScript("arguments[0].click();", element);
			updateStep(description, Status.PASS);
			STALogger.UpdateReport(Status.PASS, description);
		} catch (Exception e) {
			updateStep("Exception \t"+e.getClass().getSimpleName()+"\t Caught On "+ description, Status.FAIL);
			STALogger.UpdateReport(Status.FAIL, "Failed JavaScript click operation " + "\n\t\t"+ e.getClass().getSimpleName());
		}
	}



	/**

	 * This method returns the text present in an element using Javascript

	 *

	 * @param element

	 * @return

	 * @throws IOException

	 * @throws InterruptedException

	 */

	@SuppressWarnings("finally")

	public String executeJScriptGetText(WebElement element) throws IOException, InterruptedException {

		String text = null;

		try {

			highlightElement(element);

			JavascriptExecutor js = (JavascriptExecutor) ExecDriver;



			text = (String) js.executeScript("return arguments[0].value;",

					element);

			unhighlight(element);

			//updateStep("Javascript Command", "Pass");

		} catch (Exception e) {

			//updateStep("Error in Executing Javascript", "Fail - Exception: "+ "\n\t\t" + e.getClass().getSimpleName());

			text = "";

		} finally {

			System.out.println("Text Returned is " + text);

			return text;

		}

	}



	/**

	 * Returns the Title of the WebPage.

	 *

	 * @return String

	 * @throws IOException

	 * @throws InterruptedException

	 */

	@SuppressWarnings("finally")

	public String getTitle() throws IOException, InterruptedException {

		String title = null;

		try {

			//updateStep("Get the Title", "Pass");

			title = ExecDriver.getTitle();

		} catch (Exception e) {



			//updateStep("Get the Title", "Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

			title = "";

		} finally {

			return title;

		}

	}



	/**

	 * Validates the WebPage Title with the expected Page Title.

	 *

	 * @param title

	 * @return boolean

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public boolean assertTitle(String title) throws IOException, InterruptedException {

		try {

			if (ExecDriver.getTitle().contains(title)) {

				//updateStep("Title Matched :" + title, "Pass");

				return true;

			} else {

				//updateStep("Title Not Matched- Expected: " + title+ " Actual: " + ExecDriver.getTitle(), "Fail");

				return false;

			}

		} catch (Exception e) {

			//updateStep("Error in getting Page Title", "Fail - Exception: "+ "\n\t\t" + e.getClass().getSimpleName());

			return false;

		}

	}



	/**

	 * Similar to clicking in Back Button on the Browser.

	 *

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void back() throws IOException, InterruptedException {

		try {

			ExecDriver.navigate().back();

			//updateStep("Navigate to Previous page", "Pass");

		} catch (Exception e) {

			//updateStep("Navigate to Previous page", "Fail");

		}

	}



	/**

	 * Similar to Clicking on Next Button.

	 *

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void forward() throws IOException, InterruptedException {

		try {

			ExecDriver.navigate().forward();

			//updateStep("Navigate to Next page", "Pass");

		} catch (Exception e) {

			//updateStep("Navigate to Next page", "Fail");

		}



	}



	/**

	 * This method Refreshes the Browser.

	 *

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void refresh() throws IOException, InterruptedException {

		try {

			ExecDriver.navigate().refresh();

			//updateStep("Page Refreshed", "Pass");

		} catch (Exception e) {

			//updateStep("Page Refreshed", "Fail");

		}

	}



	/*----------------------------------------------------------------------------------------------------------------------

	 *                                            Native Selenium Commands                                     |

                ----------------------------------------------------------------------------------------------------------------------*/

	/**

	 * This method performs Click Operation on the WebElement

	 *

	 * @param ele

	 * @param description

	 * @throws IOException

	 * @throws InterruptedException
	 * @throws STAExceptions 

	 */

	public void click(WebElement ele, String description) throws IOException, InterruptedException, STAExceptions {

		try {
			Thread.sleep(300);
			highlightElement(ele);

			// Thread.sleep(1000);
			STALogger.UpdateReport(Status.PASS, description);

			// System.out.println("2-----");
			Thread.sleep(300);
			unhighlight(ele);
			Thread.sleep(500);
			// System.out.println("3------");
			ele.click();
			// System.out.println("4-------");
			// Log.info(description + " :  Pass");

		} catch (NoSuchElementException e) {

			STALogger.UpdateReport(Status.FAIL, "No such element found Exception occured :  ==>" + "\n\t\t"+ e.getClass().getSimpleName());
			 updateStep("Exception \t"+e.getClass().getSimpleName()+"\t Caught On "+ description, Status.FAIL);
			throw new STAExceptions(e.getMessage());
			//updateStep(description, "Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

			//Log.error(description + ":   Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());


		} catch (TimeoutException e) {
			STALogger.UpdateReport(Status.FAIL, "WebDriver timeout Exception occured :  ==>" + "\n\t\t"+ e.getClass().getSimpleName());

			 updateStep("Exception \t"+e.getClass().getSimpleName()+"\t Caught On "+ description, Status.FAIL);

			//Log.error(description + ":   Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

			e.printStackTrace();

			return;

		} catch (Exception e) {

			STALogger.UpdateReport(Status.FAIL, "General Exception occured :  ==>" + "\n\t\t"+ e.getClass().getSimpleName());
			 updateStep("Exception \t"+e.getClass().getSimpleName()+"\t Caught On "+ description, Status.FAIL);
			//updateStep(description, "Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

			//Log.error(description + ":   Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		}

	}
	
	



	/**

	 * This method Types the Value in the TextBox given in the WebElement.

	 *

	 * @param ele

	 * @param Value

	 * @param description

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void type(WebElement ele, String Value, String description) throws IOException, InterruptedException {

		try {

			highlightElement(ele);

			ele.clear();

			ele.sendKeys(Value);
			STALogger.UpdateReport(Status.PASS, description);

			 updateStep(description, Status.PASS);

			//System.out.println("sysout : "+Value);

			//Log.info(description + " :  Pass");

			unhighlight(ele);

		} catch (Exception e) {

			STALogger.UpdateReport(Status.FAIL, "General Exception Occured: ==> " + "\n\t\t"+ e.getClass().getSimpleName());
			 updateStep("Exception \t"+e.getClass().getSimpleName()+"\t Caught On "+ description, Status.FAIL);

			//Log.error(description + ":   Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		}

	}



	/**

	 * Selects a DropDown by its Value Attribute.

	 *

	 * @param ele

	 * @param Value

	 * @param description

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void selectDropdownByValue(WebElement ele, String Value, String description) throws IOException, InterruptedException {

		try {

			highlightElement(ele);

			Select se = new Select(ele);

			se.selectByValue(Value);

			//updateStep(description, "Pass");

			unhighlight(ele);

		} catch (Exception e) {

			//updateStep(description, "Fail - Exception: " + "\n\t\t" + e.getClass().getSimpleName());

		}

	}



	/**

	 * Select the Dropdown value by the index of its occurrence.

	 *

	 * @param ele

	 * @param Value

	 * @param description

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void selectDropdownByIndex(WebElement ele, int Value, String description) throws IOException, InterruptedException {

		try {

			highlightElement(ele);

			Select se = new Select(ele);

			se.selectByIndex(Value);

			//updateStep(description, "Pass");

			unhighlight(ele);

		} catch (Exception e) {

			//updateStep(description, "Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		}

	}



	/**

	 * Selects DropDown by the Value displayed in the list.

	 *

	 * @param ele

	 * @param Value

	 * @param description

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void selectDropdownByVisibleText(WebElement ele, String Value, String description) throws IOException, InterruptedException {

		try {

			highlightElement(ele);

			Select se = new Select(ele);

			se.selectByVisibleText(Value);

			//updateStep(description, "Pass");

			unhighlight(ele);

		} catch (Exception e) {

			//updateStep(description, "Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		}

	}



	/**

	 * Returns the text visible on the WebElement/Label.

	 *

	 * @param ele

	 * @param description

	 * @return String

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public String getText(WebElement ele, String description) throws IOException, InterruptedException {

		String text;

		try {

			highlightElement(ele);

			text = ele.getText();

			// //updateStep(description, "Pass");

			unhighlight(ele);

		} catch (Exception e) {

			text = "";

			//updateStep(description, "Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		}

		return text;



	}


	/**

	 * Returns the text visible on the WebElement/Label.

	 *

	 * @param ele

	 * @param description

	 * @return String

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public String getCSSValue(WebElement ele, String CSSValue,String description) throws IOException, InterruptedException {

		String text = null;

		try {

			highlightElement(ele);

			text = ele.getCssValue(CSSValue);

			// //updateStep(description, "Pass");

			unhighlight(ele);

		} catch (Exception e) {

			text = "";

			//updateStep(description, "Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		}

		return text;



	}

	
	

	/**

	 * Validates the text Present at the WebElement is exactly the same as

	 * Expected.

	 *

	 * @param ele

	 * @param expText

	 * @return boolean

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public Boolean assertTextEquals(WebElement ele, String expText) throws IOException, InterruptedException {

		String actText;

		try {

			highlightElement(ele);

			actText = ele.getText();

			if (actText.equalsIgnoreCase(expText)) {

				//updateStep("Text Matched :" + expText, "Pass");

				unhighlight(ele);

				return true;

			} else {

				//updateStep("Text Not Matched Expected:" + expText + " Actual: "+ actText, "Fail");

				return false;

			}

		} catch (Exception e) {



			//updateStep("Error while Retrieving Text", "Fail - Exception: " + "\n\t\t" + e.getClass().getSimpleName());

			return false;

		}



	}



	/**

	 * Validates the text Present at the WebElement is partially same as

	 * Expected.

	 *

	 * @param ele

	 * @param expText

	 * @return boolean

	 * @throws IOException

	 * @throws InterruptedException

	 */
	public Boolean assertTextContains(WebElement ele, String expText) throws IOException, InterruptedException {

		String actText;

		try {

			highlightElement(ele);

			actText = ele.getText();

			unhighlight(ele);

			if (actText.contains(expText)) {

				//updateStep("Text Present :" + expText, "Pass");



				return true;

			} else {

				//updateStep("Text Not Present- Expected:" + expText+ " Actual: " + actText, "Fail");

				return false;

			}

		} catch (Exception e) {



			//updateStep("Error while Retrieving Text", "Fail - Exception: "+ "\n\t\t" + e.getClass().getSimpleName());

			return false;

		}



	}



	/*----------------------------------------------------------------------------------------------------------------------

	 *                                            SYNTHETIC Selenium Commands(ACTIONS CLASS)                                     |

                ----------------------------------------------------------------------------------------------------------------------*/

	/**

	 * Performs MouseOver on the Element.

	 *

	 * @param ele

	 * @param description

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void mouseOver(WebElement ele, String description) throws IOException, InterruptedException {

		try {

			highlightElement(ele);

			Actions act = new Actions(ExecDriver);

			act.moveToElement(ele).build().perform();

			//updateStep(description, "Pass");

			unhighlight(ele);

		} catch (Exception e) {

			//updateStep(description, "Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		}

	}



	/**

	 * Similar to Click.Can click on Hidden Elements also.

	 *

	 * @param ele

	 * @param description

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void clickAt(WebElement ele, String description) throws IOException, InterruptedException {

		try {

			highlightElement(ele);

			Actions act = new Actions(ExecDriver);

			act.click(ele).build().perform();

			////updateStep(description, "Pass");

			unhighlight(ele);

		} catch (Exception e) {

			////updateStep(description, "Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		}

	}



	/**

	 * Performs Double Click on the Element.

	 *

	 * @param ele

	 * @param description

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void doubleClick(WebElement ele, String description) throws IOException, InterruptedException {

		try {

			highlightElement(ele);

			Actions act = new Actions(ExecDriver);

			act.doubleClick(ele).build().perform();

			//updateStep(description, "Pass");

			unhighlight(ele);

		} catch (Exception e) {

			//updateStep(description, "Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		}

	}



	/**

	 * Performs Right Click on the Element.

	 *

	 * @param ele

	 * @param description

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void rightClick(WebElement ele, String description) throws IOException, InterruptedException {

		try {

			highlightElement(ele);

			Actions act = new Actions(ExecDriver);

			act.contextClick(ele).build().perform();

			//updateStep(description, "Pass");

			unhighlight(ele);

		} catch (Exception e) {

			//updateStep(description, "Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		}

	}



	/**

	 * Drags an Element and drops at another location.

	 *

	 * @param ele1

	 * @param ele2

	 * @param description

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void dragAndDrop(WebElement ele1, WebElement ele2, String description) throws IOException, InterruptedException {

		try {

			highlightElement(ele1);

			highlightElement(ele2);

			Actions act = new Actions(ExecDriver);

			act.dragAndDrop(ele1, ele2).build().perform();

			//updateStep(description, "Pass");

			unhighlight(ele1);

			unhighlight(ele2);

		} catch (Exception e) {

			//updateStep(description, "Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		}

	}



	/**

	 * Slides an element by X and Y Coordinates.

	 *

	 * @param ele

	 * @param x

	 * @param y

	 * @param description

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void slideBy(WebElement ele, int x, int y, String description) throws IOException, InterruptedException {

		try {

			highlightElement(ele);

			Actions act = new Actions(ExecDriver);

			act.dragAndDropBy(ele, x, y).build().perform();

			//updateStep(description, "Pass");

			unhighlight(ele);



		} catch (Exception e) {

			//updateStep(description, "Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		}

	}



	/**

	 * Date:9th July This Method enables fileUpload

	 *

	 * @throws AWTException

	 * @throws InterruptedException

	 */



	public void fileUploadUsingRobot(String fileName) throws AWTException, InterruptedException {

		StringSelection ss = new StringSelection(fileName);

		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_ENTER);

		robot.keyRelease(KeyEvent.VK_ENTER);

		robot.keyPress(KeyEvent.VK_CONTROL);

		robot.keyPress(KeyEvent.VK_V);

		robot.keyRelease(KeyEvent.VK_V);

		robot.keyRelease(KeyEvent.VK_CONTROL);

		Thread.sleep(2000);

		robot.keyPress(KeyEvent.VK_ENTER);

		robot.keyRelease(KeyEvent.VK_ENTER);

	}



	/**

	 * File Upload using AutoIt

	 *

	 * @throws IOException

	 */

	public void fileUpload(WebElement ele, String path) throws IOException {

		File file = new File(path);

		path = file.getCanonicalPath();

		String cmd = null;

		if (ExecDriver instanceof FirefoxDriver) {

			cmd = ".\\lib\\fileuploadFF.exe";

		}

		if (ExecDriver instanceof ChromeDriver) {

			cmd = ".\\lib\\fileuploadCHROME.exe";

		}

		if (ExecDriver instanceof InternetExplorerDriver) {

			cmd = ".\\lib\\fileuploadIE.exe";

		}



		ProcessBuilder pb = new ProcessBuilder(cmd, path);

		pb.start();

		try {

			highlightElement(ele);

			//updateStep("Click on Browse Button for File Upload", "Pass");

			ele.click();

			unhighlight(ele);



		} catch (Exception e) {

			//updateStep("Exception while clicking Browse Button","Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		}

	}



	/**

	 * This Method returns the URL of the current page

	 *

	 * @return String

	 */

	public String getCurrentUrl() {

		//Log.info(".......Get the current url...........");

		return ExecDriver.getCurrentUrl();

	}



	/**

	 * This Method returns the value of the WebElememt based on the attribute

	 *

	 * @param ele

	 * @param attribute

	 * @param description

	 * @return String

	 */

	public String getAttribute(WebElement ele, String attribute, String description) {

		//Log.info(".......Get the attribute value...........");

		try {

			highlightElement(ele);

			//updateStep(description, "Pass");

			unhighlight(ele);

			return ele.getAttribute(attribute);

		} catch (InterruptedException e) {

			//updateStep(description, "Fail");

			return null;

		}



	}



	/*

	 * KeyBoard Shortcut Starts here

	 */

	/**

	 * This method performs keyboard Event Escape

	 */

	public void pressEscape() {

		Actions act = new Actions(ExecDriver);

		act.sendKeys(Keys.ESCAPE).build().perform();

	}



	/**

	 * This method performs keyboard Event Enter

	 */

	public void pressEnter() {

		Actions act = new Actions(ExecDriver);

		act.sendKeys(Keys.ENTER).build().perform();

	}



	public void endReport() {

		//updateStep("End Of Execution", "END");

		super.endOfTestCase = true;

	}



	/**

	 * Returns a random number

	 */

	public int generateRandomNumber(int range, int startNum) {

		Random rand = new Random();

		int val = rand.nextInt(range) + startNum;

		return val;

	}



	public void typeusingRobot(String fileName) throws AWTException, InterruptedException {

		StringSelection ss = new StringSelection(fileName);

		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_CONTROL);

		robot.keyPress(KeyEvent.VK_V);

		robot.keyRelease(KeyEvent.VK_V);

		robot.keyRelease(KeyEvent.VK_CONTROL);

		Thread.sleep(2000);
	}



	public void pressTABusingRobot() throws AWTException, InterruptedException {

		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_TAB);

		robot.keyRelease(KeyEvent.VK_TAB);

	}



	public void pressTab() {

		Actions act = new Actions(ExecDriver);

		act.sendKeys(Keys.TAB).build().perform();

	}



	public void pressEnterusingRobot() throws AWTException, InterruptedException {

		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_ENTER);

		robot.keyRelease(KeyEvent.VK_ENTER);

	}



	/*----------------------------------------------------------------------------------------------------------------------

	 *             TEST CLASS  RESUABLE KEYWORDS ENDS HERE                                                                        |

                ----------------------------------------------------------------------------------------------------------------------*/

	/*	 public void checkDependency(String tcId) {

		 if (SuiteRunner.suiteRun == true) {

			 // String path=reportPath+"\"+tcid"

			 String status = null;

			 FileInputStream fstream = null;

			 try {

				 System.out.println("The report Path is " + reportPathSuite

						 + "\\" + tcId + "\\" + "status.txt");

				 fstream = new FileInputStream(reportPathSuite + "\\" + tcId

						 + "\\" + "status.txt");



			 } catch (FileNotFoundException e) {

				 e.printStackTrace();

				 //updateStep("The dependent test Case " + tcId + " has not run ",

						 "Fail");



			 }

			 DataInputStream in = new DataInputStream(fstream);

			 BufferedReader br = new BufferedReader(new InputStreamReader(in));

			 String strLine;



			 try {

				 if ((strLine = br.readLine()) != null) {

					 String[] data = strLine.split("###");

					 status = data[0];

					 // arrTcDesc[i] = data[1];

				 }

			 } catch (IOException e) {

				 e.printStackTrace();

			 }

			 if (status.equalsIgnoreCase("Fail")) {

				 //updateStep("The dependent test Case " + tcId

						 + " has Failed:Skipping test case ", "Fail");

			 }

		 }

	 }*/


	/**

	 * Retrieves the latest filename

	 */

	public String getTheNewestFile(String filePath, String ext) {

		File theNewestFile = null;

		File dir = new File(filePath);

		FileFilter fileFilter = new WildcardFileFilter("*."+ext);

		File[] files = dir.listFiles(fileFilter);



		if (files.length > 0) {

			/** The newest file comes first **/

			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);

			theNewestFile = files[0];

		}

		System.out.println(theNewestFile.getName());

		return theNewestFile.getName();

	}



	/*----------------------------------------------------------------------------------------------------------------------

	 *                                            Native Selenium Commands                                     |

                  ----------------------------------------------------------------------------------------------------------------------*/

	/**

	 * This method performs Download Operation

	 *

	 * @param ele

	 * @param description

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void fileDownloadUsingRobot() throws AWTException, InterruptedException {

		Robot robot = new Robot();

		//For clicking on the Ok button on the dialog box  

		robot.keyPress(KeyEvent.VK_ENTER);   

		robot.keyRelease(KeyEvent.VK_ENTER);   

		Thread.sleep(2000);  



		robot.keyPress(KeyEvent.VK_LEFT);   

		robot.keyRelease(KeyEvent.VK_LEFT);   

		Thread.sleep(2000);  



		//For clicking on Ok button  

		//robot.keyPress(KeyEvent.VK_ENTER);   

		//robot.keyRelease(KeyEvent.VK_ENTER);   

	}  





	/*----------------------------------------------------------------------------------------------------------------------

	 *                                            Native Selenium Commands                                     |

                  ----------------------------------------------------------------------------------------------------------------------*/

	/**

	 * This method performs Click Operation on the WebElement

	 *

	 * @param ele

	 * @param description

	 * @throws IOException

	 * @throws InterruptedException

	 */

	public void clear(WebElement ele, String description) throws IOException, InterruptedException {

		try {

			highlightElement(ele);

			// System.out.println("1---");

			//updateStep(description, "Pass");

			// System.out.println("2-----");

			unhighlight(ele);

			// System.out.println("3------");

			ele.clear();

			// System.out.println("4-------");

			// Log.info(description + " :  Pass");

		} catch (NoSuchElementException e) {

			//updateStep(description, "Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

			//Log.error(description + ":   Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		} catch (TimeoutException e) {

			//updateStep("TimeOut Exception", "Fail - Exception: " + "\n\t\t" + e.getClass().getSimpleName());

			//Log.error(description + ":   Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

			e.printStackTrace();

			return;

		} catch (Exception e) {

			//updateStep(description, "Fail - Exception: " + "\n\t\t" + e.getClass().getSimpleName());

			//Log.error(description + ":   Fail - Exception: " + "\n\t\t"+ e.getClass().getSimpleName());

		}

	}



}