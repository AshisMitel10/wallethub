package STACore.staInitializer;

/**************************************************
#Project Name: wallethub
#Class Name: STAInitializer
#Description: This class is responsible for initializing the AppVariables, Initialiazing log parameters and testCase to run suites
#Owner: Ashis Kumar Pradhan
#Author: Ashis Kumar Pradhan
#Date of creation: 26-Jul-2019
#Name of person modifying: (Tester Name): 
#Date of modification: 
‘**************************************************/

import STACore.configurationReader.ConfigurationReader;
import STACore.staGlobalVariable.STAGlobalVar;
import STACore.staGlobalVariable.TestCase;
import STACore.staUtils.STAExceptions;
import STACore.logger.STALogger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.beust.testng.TestNG;
import com.google.common.reflect.ClassPath;
import STACore.logger.STALogger;

public class STAInitializer {

	ClassPath classpath = null;
	final ClassLoader loader = Thread.currentThread().getContextClassLoader();
	protected static Class<?> testClass = null;

	/**************************************************
	 * #Functionality Name: InitializeApplicationVariable() #Parameters: String
	 * appName #Description: This class is responsible for reading build value
	 * from testng.xml #Owner: Ashis Kumar Pradhan #Author: Ashis Kumar Pradhan
	 * #Date of creation: 26-Jul-2019 #Name of person modifying: (Tester Name):
	 * #Date of modification: ‘
	 **************************************************/
	public static void InitializeApplicationVariable(String appName) {
		STALogger.Log("Loading the test case data for the application : " + STAGlobalVar.AppName,
				STAGlobalVar.ProcessState.Initiated.toString(), STALogger.LogType.Info);
		LoadTestData(STAGlobalVar.AppName);

		// Conditionally getting the build version of the application.
		// When there is no value passed in Build parameter inside TestNG.xml,
		// Application version / Build version is getting from the server it
		// self.
		if (!STAGlobalVar.AppVersion.equals("")) {
			STALogger.Log("Initialized app version from TestNG.xml.", STAGlobalVar.AppVersion, STALogger.LogType.Info);
		} else {
			STALogger.Log("No Build value is passed in TestNG.xml, Hence get the build value from the server",
					STAGlobalVar.AppVersion, STALogger.LogType.Info);

		}
	}

	public void UpdateClassNameinCollection(String appName) throws IOException, ClassNotFoundException {
		Map<String, String> tcs = new HashMap<String, String>();
		for (String testcase : STAGlobalVar.testCasesToExecute.keySet()) {
			tcs.put(STAGlobalVar.testCasesToExecute.get(testcase).getName().toString(), testcase);
		}

		classpath = ClassPath.from(loader);
		for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive(appName)) {
			testClass = Class.forName(classInfo.toString());
			for (Method met : testClass.getMethods()) {
				String name = met.getName();
				if (tcs.keySet().contains(name)) {
					STAGlobalVar.testCasesToExecute.get(tcs.get(name)).setClassname(testClass.getName());
				}
			}
		}
	}

	public Method ReturnMethodviaRefelection(String appName, String ClsName, String MethodName) {
		try {
			try {
				classpath = ClassPath.from(loader);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive(appName)) {
				testClass = Class.forName(classInfo.toString());
				String ClassName = classInfo.getName();
				if (ClassName.equals(ClsName)) {
					testClass = Class.forName(classInfo.toString());
					for (Method met : testClass.getMethods()) {
						String name = met.getName();
						if (name.equalsIgnoreCase(MethodName))

							return testClass.getMethod(name, String.class, String.class, boolean.class, boolean.class);

					}
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println(
					"Exception occured is of type " + e.getMessage() + ".\n" + "Stack Trace is : " + e.toString());
		} catch (NoSuchMethodException x) {
			x.printStackTrace();
		}

		return null;
	}

	/**************************************************
	 * #Functionality Name: InitializeLogger() #Parameters: String
	 * appName,String appLog, String SysLog #Description: This method is
	 * responsible for Initializing the FrameWork and Execution Loggers.
	 * Parameters this method take are : Application Name, App log enabled?, Sys
	 * log enabled? Application Name : Application Automation to be launched.
	 * String value from testNg.xml appLog : Application logging is to be
	 * collected?. Yes / No. Value from testNg.xml SysLog : Application logging
	 * is to be collected?. Yes / No. Value from testNg.xml #Owner: Ashis Kumar
	 * Pradhan #Author: Ashis Kumar Pradhan #Date of creation: 26-Jul-2019 #Name
	 * of person modifying: (Tester Name): #Date of modification: ‘
	 **************************************************/
	public static void InitializeLogger(String appName, String appLog, String SysLog) {

		boolean isAppLog = false;
		boolean isSysLog = false;
		if (appLog.equalsIgnoreCase("Yes"))
			isAppLog = true;
		if (SysLog.equalsIgnoreCase("Yes"))
			isSysLog = true;
		STAInitializer l = new STAInitializer();
		Method method = null;
		method = l.ReturnMethodviaRefelection("STACore", "STACore.logger.STALogger", "InitializeLogger");

		// Sample invoker for logs.
		// To invoke through reflections, we need to pass the parameters in
		// below sequence.
		// method : Method we get from reflection. followed by its parameters.
		// appName : Name of the application.
		// BuildID : Build version of the application.
		// isAppLog : is Application loging necessary.
		// isSysLog : is System loging necessary.
		try {
			method.invoke(method, appName, STAGlobalVar.AppVersion, isAppLog, isSysLog);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**************************************************
	 * #Functionality Name: LoadTestData() #Parameters: String appName
	 * #Description: This method is responsible for Loading the Test Data
	 * provided the AUT name. This value is taken from testNg.xml file. appName
	 * : String value from TestNg.xml file. [wallethub etc..] #Owner: Ashis Kumar
	 * Pradhan #Author: Ashis Kumar Pradhan #Date of creation: 26-Jul-2019 #Name
	 * of person modifying: (Tester Name): #Date of modification: ‘
	 **************************************************/
	@SuppressWarnings("unchecked")
	public static void LoadTestData(String appName) {
		String runAll = "NO";
		String SuitToSelect = null;

		/*
		 * if (STAGlobalVar.AppName.equals("ABC")) {
		 * STAGlobalVar.TestDataFileName =
		 * System.getProperty("user.dir")+"\\src\\"+appName+"\\Config\\
		 * "+appName+"TestPlan.xlsx"; STAGlobalVar.TestConfigFileName =
		 * System.getProperty("user.dir")+"\\src\\"+appName+"\\Config\\TestData.
		 * xlsx"; }else { STAGlobalVar.TestDataFileName =
		 * System.getProperty("user.dir")+"\\src\\"+appName+"\\Config\\
		 * "+appName+"_TestPlan.xlsx"; }
		 */
		STAGlobalVar.TestDataFileName = System.getProperty("user.dir") + "\\src\\" + appName + "\\Config\\" + appName
				+ "_TestPlan.xlsx";

		File tplan = new File(STAGlobalVar.TestDataFileName);

		try {
			if (!(tplan.exists() && tplan.isFile())) {
				System.out.println("The Test Data file for the application " + appName + " is missing.");
				throw new STAExceptions("The Test Data file" + appName + "_TestPlan.xlsx //" + appName
						+ "TestPlan.xlsx  for the application " + appName + " is missing.");
			} else {

				/*
				 * if (STAGlobalVar.AppName.equals("ABC")) {
				 * STAGlobalVar.allTestSuit=
				 * ConfigurationReader.loadExcelLines(STAGlobalVar.
				 * TestDataFileName);
				 * STAGlobalVar.allTestSuit.putAll(ConfigurationReader.
				 * loadExcelLines(STAGlobalVar.TestConfigFileName)); } else {
				 * STAGlobalVar.allTestSuit=
				 * ConfigurationReader.loadExcelLines(STAGlobalVar.
				 * TestDataFileName); }
				 */

				STAGlobalVar.allTestSuit = ConfigurationReader.loadExcelLines(STAGlobalVar.TestDataFileName);
              System.out.println("original size is "+ STAGlobalVar.allTestSuit.get("TestPlan").size());
				for (int i = 1; i < STAGlobalVar.allTestSuit.get("TestPlan").size(); i++) {

					if (STAGlobalVar.allTestSuit.get("TestPlan").get(i).get(3).equalsIgnoreCase("Yes"))
					{
						int yesyesCount=0;
						System.out.println("Run all as Yes");
						for (LinkedList<String> record : STAGlobalVar.allTestSuit.get(SuitToSelect).values()) {
							if(yesyesCount == 0)
							{
								yesyesCount++;
								continue;
							}
							if(record.size() <= 1)
							{
								System.out.println("break the code");
								break;
							}
							else
							 getTestCasesToRun(STAGlobalVar.testCasesToExecute, record);
							yesyesCount++;
						}
					}
					else if (STAGlobalVar.allTestSuit.get("TestPlan").get(i).get(0).equalsIgnoreCase("Yes"))
					{
						SuitToSelect = STAGlobalVar.allTestSuit.get("TestPlan").get(i).get(1);

						switch (STAGlobalVar.AppName) {

						case "wallethub":
						}
						int yesyesCount=0;
						System.out.println("size is if yes " + STAGlobalVar.allTestSuit.get(SuitToSelect).values().size());
						for (LinkedList<String> record : STAGlobalVar.allTestSuit.get(SuitToSelect).values()) {
							if(yesyesCount == 0)
							{
								yesyesCount++;
								continue;
							}
							if(record.size() <= 1)
							{
								System.out.println("break the code");
								break;
							}
							else
							 getTestCasesToRun(STAGlobalVar.testCasesToExecute, record);
							yesyesCount++;
						}
					
					}
					else
					{
						System.out.println("No test case to execute");
					}
					
					
					
					
					
					
					
					
					
					
					
					
					
					/*if (STAGlobalVar.allTestSuit.get("TestPlan").get(i).get(0).equalsIgnoreCase("yes")) {
						SuitToSelect = STAGlobalVar.allTestSuit.get("TestPlan").get(i).get(1);

						switch (STAGlobalVar.AppName) {

						case "wallethub":
							runAll = STAGlobalVar.allTestSuit.get("TestPlan").get(i).get(3);
						}
						if (runAll.equalsIgnoreCase("NO")) {
							for (LinkedList<String> record : STAGlobalVar.allTestSuit.get(SuitToSelect).values()) {
								if ((record.size() > 1) && (record.size() >= 8)) {
									if (record.get(4).equalsIgnoreCase("y") || record.get(4).equalsIgnoreCase("yes")) {
										getTestCasesToRun(STAGlobalVar.testCasesToExecute, record);
									}
								}
							}
						} else {
							int yesyesCount=0;
							System.out.println("size is if yes " + STAGlobalVar.allTestSuit.get(SuitToSelect).values().size());
							for (LinkedList<String> record : STAGlobalVar.allTestSuit.get(SuitToSelect).values()) {
								if(yesyesCount == 0)
								{
									yesyesCount++;
									continue;
								}
								if(record.size() <= 1)
								{
									System.out.println("break the code");
									break;
								}
								else
								 getTestCasesToRun(STAGlobalVar.testCasesToExecute, record);
								yesyesCount++;
							}
						}
					}
					else if (STAGlobalVar.allTestSuit.get("TestPlan").get(i).get(0).equalsIgnoreCase("no")) {
						SuitToSelect = STAGlobalVar.allTestSuit.get("TestPlan").get(i).get(1);

						switch (STAGlobalVar.AppName) {

						case "wallethub":
							runAll = STAGlobalVar.allTestSuit.get("TestPlan").get(i).get(3);
						}
						if (runAll.equalsIgnoreCase("NO")) {
							System.out.println("size is if no  no " + STAGlobalVar.allTestSuit.get(SuitToSelect).values().size());
							System.out.println("no config selected");
						} else {
							int noyesCount=0;
							System.out.println("size is if  no yes " + STAGlobalVar.allTestSuit.get(SuitToSelect).values().size());
							for (LinkedList<String> record : STAGlobalVar.allTestSuit.get(SuitToSelect).values()) {
								if(noyesCount == 0)
								{
									noyesCount++;
									continue;
								}
								if(record.size() <= 1)
								{
									System.out.println("break the code");
									break;
								}
								else
								getTestCasesToRun(STAGlobalVar.testCasesToExecute, record);
								noyesCount++;
							}
						}
					}*/
				}
			}
		} catch (STAExceptions e) {
			e.printStackTrace();
		}
	}

	/**************************************************
	 * #Functionality Name: getTestCasesToRun() #Parameters: N/A #Description:
	 * This method will get all the test cases suppose to Execute appName :
	 * String value from TestNg.xml file. [wallethub etc..] #Owner: Ashis Kumar
	 * Pradhan #Author: Ashis Kumar Pradhan #Date of creation: 26-Jul-2019 #Name
	 * of person modifying: (Tester Name): #Date of modification: ‘
	 **************************************************/
	private static void getTestCasesToRun(HashMap<String, TestCase> testCaseObjHashMapToExecute,
			LinkedList<String> record) {
		TestCase testcase = new TestCase(record.get(0), record.get(1), record.get(2), record.get(3), record.get(4),
				record.get(5), record.get(6), record.get(7));
		ArrayList<String> additionalParameter = new ArrayList<String>();
		for (int i = 10; i < record.size(); i++) {
			additionalParameter.add(record.get(i));
		}
		testcase.setAttributeList(additionalParameter);
		testCaseObjHashMapToExecute.put(record.get(1), testcase);
		STAGlobalVar.TestIndex.put(STAGlobalVar.indexVal, record.get(1));
		STAGlobalVar.indexVal++;

	}
}
