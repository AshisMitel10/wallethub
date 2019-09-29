package wallethub.test;

/**************************************************
#Project Name: wallethub
#Class Name: Insurance
Test case name 	1: Review_InsuranceComment_Test()
#Description:1. This class holds all the test case methods related to Insurance Review
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
import wallethub.utils.wallethubHelper_Insurance;;

public class Insurance extends wallethubHelper_Insurance {

	public static Hashtable<String, String> hashTable = new Hashtable<String, String>();

	ReusableTestKeywords selUtils = new ReusableTestKeywords();
	WebDriver driver = ExecDriver;
	public static boolean openBrwoser = false;

	/**************************************************
	 * #Functionality Name: initHashTable() 
	 * #Input parameter : N/A 
	 * #Description: This method get the value from excel sheet and store in hashtable in the form of Key to use in the test case 
	 * #Owner: Ashis Kumar Pradhan 
	 * #Author:Ashis Kumar Pradhan 
	 * #Date of creation: 23-Sep-2019 
	 * #Name of person modifying: (Tester Name): 
	 * #Date of modification: 
	 **************************************************/

	public void initHashTable() {

		hashTable.put("WHBaseUrl", wallethubHelper_Insurance.wallethubTestConfig.get("_WallethubBaseURL"));
		hashTable.put("WHUname", wallethubHelper_Insurance.wallethubTestConfig.get("_WHUname"));
		hashTable.put("WHSecreat", wallethubHelper_Insurance.wallethubTestConfig.get("_WHSecreat"));
		hashTable.put("WHID", wallethubHelper_Insurance.wallethubTestConfig.get("_WHID"));
		hashTable.put("WHUserURL", wallethubHelper_Insurance.wallethubTestConfig.get("_ReviewURL"));
		
		STALogger.Log("Global variables initaited for the test are : ==> " + hashTable.values().toString(),
				STAGlobalVar.ProcessState.Initiated.toString(), STALogger.LogType.Info);
	}

	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------
	 *   			 	Below are the test cases having wallethub insurance functionality
	 
	 *                                       TEST CASES BY ASHIS
	 ------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	/**************************************************
	 * #Functionality Name: Review_InsuranceComment_Test() 
	 * #Input parameter : String id, String name, String description, String browser,String runmode,String locale,String parameter, String result 
	 * #Description: This method is responsible for a successful transaction for pillow purchase
	 * #Owner: Ashis Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan 
	 * #Date of creation: 23-Sep-2019 
	 * #Name of person modifying: (Tester Name):
	 * #Date of modification: 
	 **************************************************/
	@Test(dataProvider = "STADP", dataProviderClass = STACore.staUtils.STADataProvider.class)
	public void Review_InsuranceComment_Test(String id, String name, String description, String browser,
			String runmode, String locale, String parameter, String result) throws Exception {
		
		STALogger.Log(STAGlobalVar.CurrentExecTestCaseName + ": The test case parameters from Spread Sheet are : ==>"
				+ parameter, STAGlobalVar.ProcessState.Initiated.toString(), STALogger.LogType.Info);

		wallethubHelper_Insurance.PopulateData(parameter);
		initHashTable();
		String WHPostingMessage = wallethubHelper_Insurance.PopulateData(parameter).get(0);
		hashTable.put("WHMessage", WHPostingMessage);

		STALogger.Log(
				STAGlobalVar.CurrentExecTestCaseName + ": The test data from proj DP is "
						+ hashTable.values().toString(),
				STAGlobalVar.ProcessState.InProgress.toString(), STALogger.LogType.Info);

		String LogFile = "Review_InsuranceComment_Test" + System.currentTimeMillis();

		try {
			
			selUtils.openUrl(hashTable.get("WHBaseUrl"));
			
			// Posting comment in wallethub insurance
			WH_InsuranceComment(driver,hashTable.get("WHMessage"));
			
			//Verrify Authuntication providing valid credentials
			WH_VerifyAuthentication(hashTable.get("WHUname"),hashTable.get("WHSecreat"));
			
			selUtils.openUrl("https://wallethub.com/profile/"+hashTable.get("WHID")+"/reviews/");
			
			//Verrify review comment
			WH_VerifyReviewComment(hashTable.get("WHID"),hashTable.get("WHMessage"));
					
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