package STACore.staUtils;

/**************************************************
#Project Name: wallethub
#Class Name: ReusableUiCoreKeywords()
#Description: This Class handles all testNG annotations based on certain conditions
#Input Parameters:
#Owner: Ashis Kumar Pradhan
#Author: Ashis Kumar Pradhan
#Date of creation: 03-July-16
#Name of person modifying: (Tester Name): 
#Date of modification: 
â€˜**************************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import com.aventstack.extentreports.Status;
import com.beust.testng.TestNG;
import STACore.logger.STALogger;
import STACore.staGlobalVariable.STAGlobalVar;

public class TestAnnotations implements ITestListener {

	/**************************************************
	 * #Functionality Name: reRun #Parameters : N/A #Description: This method is
	 * responsble to re execution of failour test cases #Owner: Ashis Kumar
	 * Pradhan #Author: Ashis Kumar Pradhan #Date of creation: 26-Jul-2019 #Name
	 * of person modifying: (Tester Name): #Date of modification: ‘
	 **************************************************/
	@AfterTest
	public void reRun() {

		System.out.println("In the re-run method...");

		if (STAGlobalVar.reRunOnFail.equalsIgnoreCase("Yes")) {

			int rerunCount = 0;
			while (rerunCount < 3) {

				List<XmlClass> myClasses = new ArrayList<XmlClass>();
				XmlSuite mySuite = new XmlSuite();
				List<XmlTest> myReRunTests = new ArrayList<XmlTest>();
				XmlTest myTest;

				TestNG myTestNG = new TestNG();
				mySuite.setName(STAGlobalVar.AppName + " Re-Run Test Suite");
				myTest = new XmlTest(mySuite);
				myTest.setName(STAGlobalVar.AppName);
				Map<String, List<String>> TestMethodsToExecutes = new HashMap<String, List<String>>();

				for (String C : STAGlobalVar.FailedTests.keySet()) {
					String ClassNameForMethod = STAGlobalVar.testCasesToExecute.get(C).getClassname();
					String MethodName = STAGlobalVar.testCasesToExecute.get(C).getName();
					if (TestMethodsToExecutes.keySet().contains(ClassNameForMethod)) {
						// update list and add another item in the list
						TestMethodsToExecutes.get(ClassNameForMethod).add(MethodName);
					} else {
						TestMethodsToExecutes.put(ClassNameForMethod, new ArrayList<String>(Arrays.asList(MethodName)));
						// add key and value as new list and test method to it
					}
				}

				for (String classname : TestMethodsToExecutes.keySet()) {
					XmlClass xmlcls = new XmlClass(classname);
					for (String method : TestMethodsToExecutes.get(classname)) {
						xmlcls.getIncludedMethods().add(new XmlInclude(method));
					}
					myClasses.add(xmlcls);
				}

				// Assign that to the XmlTest Object created earlier.
				myTest.setXmlClasses(myClasses);

				// Add the suite to the list of suites.
				List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
				mySuites.add(mySuite);

				// Set the list of Suites to the testNG object you created
				// earlier.
				myTestNG.setXmlSuites(mySuites);

				// invoke run() - this will run your class.
				myTestNG.run();
				rerunCount++;
			}
		} else {
			STALogger.Log("Re-Run is not set to execute with failed Test Cases", Status.INFO);
		}

	}

	@Override
	public void onTestStart(ITestResult result) {
		STALogger.UpdateReport(Status.INFO, "Started Testcase Execution.");
	}
	
	/**************************************************
	 * #Functionality Name: onTestSuccess #Parameters : N/A #Description: This
	 * method is responsble for reporting after successful execution of test
	 * cases/suits #Owner: Ashis Kumar Pradhan #Author: Ashis Kumar Pradhan
	 * #Date of creation: 26-Jul-2019 #Name of person modifying: (Tester Name):
	 * #Date of modification: ‘
	 **************************************************/
	@Override
	public void onTestSuccess(ITestResult result) {
		STALogger.UpdateReport(Status.PASS, "Test Case Passed.");
		if ((STAGlobalVar.FailedTests.size() != 0)
				&& (STAGlobalVar.FailedTests.containsKey(STAGlobalVar.CurrentExecTestCaseName))
				&& (STAGlobalVar.FailedTests.get(STAGlobalVar.CurrentExecTestCaseName) != true)) {
			STAGlobalVar.FailedTests.remove(STAGlobalVar.CurrentExecTestCaseName);
		}
	}

	/**************************************************
	 * #Functionality Name: onTestFailure #Parameters : N/A #Description: This
	 * method is responsble for log reporting after successful execution of test
	 * cases/suits #Owner: Ashis Kumar Pradhan #Author: Ashis Kumar Pradhan
	 * #Date of creation: 26-Jul-2019 #Name of person modifying: (Tester Name):
	 * #Date of modification: ‘
	 **************************************************/
	@Override
	public void onTestFailure(ITestResult result) {
		STALogger.ExtTest.assignCategory("Re-Run Tests");
		STALogger.UpdateReport(Status.INFO, "Updating the Failed test HashMap with : -> " + result.getName());
		STAGlobalVar.FailedTests.put(result.getName(), true);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		STALogger.ExtTest.assignCategory("SKipped Tests");

		STALogger.UpdateReport(Status.SKIP, "Test Got Skipped.");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {

	}

	@Override
	public void onFinish(ITestContext context) {
		STALogger.UpdateReport(Status.INFO, "Finished Testcase Execution.");
	}
}
