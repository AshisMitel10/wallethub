package STACore.staLauncher;

/**************************************************
#Project Name: wallethub
#Class Name: STALauncher
#Description: This class is responsible to start the Execution
#Owner: Ashis Kumar Pradhan
#Author: Ashis Kumar Pradhan
#Date of creation: 26-Jul-2019
#Name of person modifying: (Tester Name): 
#Date of modification: 
‘**************************************************/

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.ITestNGListener;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.beust.testng.TestNG;

import STACore.staGlobalVariable.STAGlobalVar;
import STACore.staInitializer.STAInitializer;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import wallethub.utils.wallethubHelper_FB;
import STACore.staGlobalVariable.STAGlobalVar;
import STACore.logger.STALogger;

@SuppressWarnings("deprecation")
public class STALauncher {

	public static List<XmlClass> myClasses = new ArrayList<XmlClass>();
	public static STALogger Execlogger = new STALogger();
	static Boolean suiteRun = false;
	static int testCount = 0;
	XmlSuite mySuite = new XmlSuite();
	List<XmlTest> myTestsjp = new ArrayList<XmlTest>();
	XmlTest myTest;
	public static char[] Platform_Ver;
	public static String PVersion;
	public static ExcelHandling EH;

	public String testPlanName;

	/**************************************************
	 * #Functionality Name: executionController #Parameters : String
	 * appName,String appLog,String sysLog, String build, String reRunOnFail,
	 * String screenShotOnFail #Description: This is responsible for grabbing
	 * the test data from TestNG #Owner: Ashis Kumar Pradhan #Author: Ashis
	 * Kumar Pradhan #Date of creation: 26-Jul-2019 #Name of person modifying:
	 * (Tester Name): #Date of modification: ‘
	 **************************************************/
	@Parameters({ "appName", "appLog", "sysLog", "Build", "reRunOnFail", "screenShotOnFail" })
	@BeforeSuite
	public void executionController(String appName, String appLog, String sysLog, String build, String reRunOnFail,
			String screenShotOnFail) throws Exception {
		EH = new ExcelHandling();
		STAGlobalVar.AppName = appName;
		STAGlobalVar.AppVersion = build;
		STAGlobalVar.reRunOnFail = reRunOnFail;
		STAGlobalVar.screenshotOnFail = screenShotOnFail;
		System.setProperty("webdriver.gecko.driver",
				System.getProperty("user.dir") + "\\src\\STACore\\DriverExe\\geckodriver.exe");
		STAInitializer.InitializeLogger(appName, appLog, sysLog);
		try {
			STAInitializer.InitializeApplicationVariable(STAGlobalVar.AppName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		run(appName);
	}

	/**************************************************
	 * #Functionality Name: run #Parameters : String appName #Description: This
	 * is responsible for start the execution process #Owner: Ashis Kumar
	 * Pradhan #Author: Ashis Kumar Pradhan #Date of creation: 26-Jul-2019 #Name
	 * of person modifying: (Tester Name): #Date of modification: ‘
	 **************************************************/
	public void run(String appName) throws Exception {
		suiteRun = true;
		try {

			TestNG myTestNG = new TestNG();

			XmlSuite mySuite = new XmlSuite();
			mySuite.setName(appName + " Portal Automation");
			mySuite.setPreserveOrder("true");

			XmlTest myTest = new XmlTest(mySuite);
			myTest.setName(appName);

			Map<String, List<String>> TestMethodsToExecutes = new HashMap<String, List<String>>();

			// Update STAGlobalVar.testCasesToExecute with class names of
			// TestCases.
			STAInitializer stl = new STAInitializer();
			stl.UpdateClassNameinCollection(appName);
			System.out.println("size all "+STAGlobalVar.TestIndex.values().size());
			for (String testcase : STAGlobalVar.TestIndex.values()) {
            System.out.println("test case name is "+testcase);
            
				String ClassNameForMethod = STAGlobalVar.testCasesToExecute.get(testcase).getClassname();
				System.out.println("ClassNameForMethod is "+ClassNameForMethod);
				XmlClass xmlcls = new XmlClass(ClassNameForMethod);

				if (!myClasses.contains(xmlcls)) {
					myClasses.add(xmlcls);
				}

			}

			// Assign that to the XmlTest Object created earlier.
			myTest.setXmlClasses(myClasses);

			// Add the suite to the list of suites.
			List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
			mySuites.add(mySuite);

			// Set the list of Suites to the testNG object you created earlier.
			myTestNG.setXmlSuites(mySuites);

			// invoke run() - this will run your class.
			myTestNG.run();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**************************************************
	 * #Functionality Name: ExcelHandling #Parameters : String appName
	 * #Description: This is responsible for Excel sheet handelling #Owner:
	 * Ashis Kumar Pradhan #Author: Ashis Kumar Pradhan #Date of creation:
	 * 26-Jul-2019 #Name of person modifying: (Tester Name): #Date of
	 * modification: ‘
	 **************************************************/
	public static class ExcelHandling {

		int intColumnValue;
		int intNum_Val_Row = 0;
		static String testDescription;
		Workbook wb;
		static Sheet S1;
		static int numCols = 1;
		String oldStrFilePath;
		int cellCount = 0;

		public void openExecl(String newStrFilePath, String sheetName) {
			System.out.println("****************************************************");
			System.out.println("New File Path ---------" + newStrFilePath);
			System.out.println("New File Path ---------" + newStrFilePath);
			System.out.println("Old File Path ---------" + oldStrFilePath);
			System.out.println("****************************************************");

			try {
				newStrFilePath = newStrFilePath + ".xls";
				if (numCols == 1 || (!newStrFilePath.equals(oldStrFilePath))) {
					S1 = null;
					wb = Workbook.getWorkbook(new File(newStrFilePath));
					System.out.println(wb.getNumberOfSheets() + "********");
					System.out.println(wb.getSheet(sheetName).getName());
					S1 = wb.getSheet("Sanity");
					System.out.println("Opening the Excel");
				} else {
					System.out.println("Reusing the excel");
				}
			} catch (BiffException e) {

			} catch (IOException e) {
				e.printStackTrace();
			}
			oldStrFilePath = newStrFilePath;
			numCols++;
		}

		public Object[][] defreadExcel(String strClassName) throws BiffException, IOException {
			ArrayList<Integer> listrow_val = new ArrayList<Integer>();
			intNum_Val_Row = 0;
			cellCount = 0;
			System.out.println("************************************************************");
			System.out.println("---------------Inside Read Excel---------------");
			System.out.println("No of Total Rows are " + S1.getRows());
			System.out.println("No of Total Cols are " + S1.getColumns());
			System.out.println(" Class Name is: " + strClassName);
			int rownum = S1.findCell(strClassName).getRow();
			System.out.println("The TestCase is present at  " + rownum + " row.");

			Boolean dataFound = true;
			while (dataFound.equals(true)) {
				for (int j = 0; j < S1.getColumns(); j++) {
					if (j == (S1.getColumns() - 1)) {
						dataFound = false;
					}

					try {
						if (S1.getCell(j, rownum).getContents().trim().length() <= 0) {
							dataFound = false;
						}

						else {
							System.out.println("Cellcount is :" + cellCount);
							System.out.println("Value is " + S1.getCell(j, rownum).getContents());
							System.out.println("length is :" + S1.getCell(j, rownum).getContents().length());

							cellCount++;
						}

					} catch (NullPointerException e) {
						dataFound = false;
					}
				}
			}

			System.out.println("No of Parameters passed to the Test Case(Including TC ID and TC Desc): " + cellCount);
			System.out.println("No of Parameters passed to the Test Case(Including TC ID and TC Desc): " + cellCount);
			/*************************************************************************
			 * get the number of Times the TC is present in the Input Excel.
			 * Store the value in the variable intNum_Val_Row
			 **************************************************************************/
			for (int j = 0; j < S1.getRows(); j++) {
				if (S1.getCell(0, j).getContents().equals(strClassName)) {
					testDescription = S1.getCell(1, j).getContents();

					System.out.println("Description in " + j + " >>>>" + j);
					listrow_val.add(j);
					intNum_Val_Row++;
				}
			}

			System.out.println("Row value: " + intNum_Val_Row);
			System.out.println("Cell count :" + cellCount);

			// Reads the TC related values from the Sheet
			Object[][] arrTable = new Object[intNum_Val_Row][cellCount - 2];
			for (int i = 0; i < intNum_Val_Row; i++) {
				System.out.println("cell vlaue --" + cellCount);
				for (int k = 2; k < cellCount; k++) {
					if (!S1.getCell(k, listrow_val.get(i)).getContents().isEmpty()) {
						arrTable[i][k - 2] = S1.getCell(k, listrow_val.get(i)).getContents();
						System.out.println("inside for loop");
						System.out.println(arrTable[i][k - 2]);
					}
				}
			}
			System.out.println(" Test Case Desc:" + testDescription);
			System.out.println(" No of Rows for the Test Case :" + intNum_Val_Row);

			System.out.println("Row :" + intNum_Val_Row);
			System.out.println("************************************************************");

			return arrTable;
		}

		public String getTestDescription() {
			System.out.println("inside getTestDescription " + testDescription);
			return testDescription;
		}
	}
}