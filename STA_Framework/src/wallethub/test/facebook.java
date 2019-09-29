package wallethub.test;

/**************************************************
#Project Name: wallethub
#Class Name: facebook
Test case name 	1: Facebook_StatusPost_Test()
#Description:1. This class holds all the test case methods related to Facebook
#Owner: Ashis Kumar Pradhan
#Author: Ashis Kumar Pradhan
#Date of creation: 23-Sep-2019
#Name of person modifying: (Tester Name): 
#Date of modification: 
‘**************************************************/

import java.util.Hashtable;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import STACore.logger.STALogger;
import STACore.staGlobalVariable.STAGlobalVar;
import STACore.staUtils.ReusableTestKeywords;
import wallethub.utils.wallethubHelper_FB;

public class facebook extends wallethubHelper_FB {

	public static Hashtable<String, String> hashTable = new Hashtable<String, String>();

	ReusableTestKeywords selUtils = new ReusableTestKeywords();
	WebDriver driver = ExecDriver;
	public static boolean openBrwoser = false;

	/**************************************************
	 * #Functionality Name: initHashTable() 
	 * #Input parameter : N/A 
	 * #Description: This method get the value from excel sheet and store in hashtable in theform of Key to use in the test case 
	 * #Owner: Ashis Kumar Pradhan 
	 * #Author:Ashis Kumar Pradhan 
	 * #Date of creation: 23sep-2019 
	 * #Name of person
	 * modifying: (Tester Name): 
	 * #Date of modification: 
	 **************************************************/

	public void initHashTable() {

		hashTable.put("BaseUrl", wallethubHelper_FB.wallethubTestConfig.get("_BaseFBURL"));
		hashTable.put("FBUname", wallethubHelper_FB.wallethubTestConfig.get("_FBUname"));
		hashTable.put("FBSecreat", wallethubHelper_FB.wallethubTestConfig.get("_FBSecreat"));


		STALogger.Log("Global variables initaited for the test are : ==> " + hashTable.values().toString(),
				STAGlobalVar.ProcessState.Initiated.toString(), STALogger.LogType.Info);
	}

	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------
	 *   			 	Below are the test cases having facebook functionality
	 
	 *                                       TEST CASES BY ASHIS
	 ------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	/**************************************************
	 * #Functionality Name: Facebook_StatusPost_Test() 
	 * #Input parameter : String id, String name, String description, String browser,String runmode,String locale,String parameter, String result 
	 * #Description: Thismethod is responsible for a successful transaction for pillow purchase
	 * #Owner: Ashis Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan 
	 * #Date of creation: 23-Sep-2019 
	 * #Name of person modifying: (Tester Name): 
	 * #Date of modification: 
	 **************************************************/
	@Test(dataProvider = "STADP", dataProviderClass = STACore.staUtils.STADataProvider.class)
	public void Facebook_StatusPost_Test(String id, String name, String description, String browser,
			String runmode, String locale, String parameter, String result) throws Exception {
		
		STALogger.Log(STAGlobalVar.CurrentExecTestCaseName + ": The test case parameters from Spread Sheet are : ==>"
				+ parameter, STAGlobalVar.ProcessState.Initiated.toString(), STALogger.LogType.Info);

		wallethubHelper_FB.PopulateData(parameter);
		initHashTable();
		String FBPostingMessage = wallethubHelper_FB.PopulateData(parameter).get(0);
		hashTable.put("FBMessage", FBPostingMessage);
		String title = "Facebook – log in or sign up";

		STALogger.Log(
				STAGlobalVar.CurrentExecTestCaseName + ": The test data from proj DP is "
						+ hashTable.values().toString(),
				STAGlobalVar.ProcessState.InProgress.toString(), STALogger.LogType.Info);

		String LogFile = "Facebook_StatusPost_Test" + System.currentTimeMillis();

		try {
			
			// Login to Facebook
			Login_to_FB(hashTable.get("BaseUrl"), title ,hashTable.get("FBUname"),hashTable.get("FBSecreat"));
			
			//Posting status in Fcaebook
			FB_StatusPosting(hashTable.get("FBMessage"));
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			// Generating report for the test case
			selUtils.endReport();
		}
	}	
/*----------------------------------------------------------------------------------------------------------------------
	 *              TEST CASES ENDS HERE BY ASHIS                                                                       
	 ----------------------------------------------------------------------------------------------------------------------*/
}