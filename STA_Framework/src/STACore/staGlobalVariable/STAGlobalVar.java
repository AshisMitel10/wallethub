package STACore.staGlobalVariable;

/**************************************************
#Project Name: wallethub
#Class Name: STAGlobalVar
#Description: This class is responsible for Initiating the browser and making all desired capability related to browser
#Owner: Ashis Kumar Pradhan
#Author: Ashis Kumar Pradhan
#Date of creation: 26-Jul-2019
#Name of person modifying: (Tester Name): 
#Date of modification: 
‘**************************************************/

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;
import org.openqa.selenium.WebDriver;

public class STAGlobalVar {

	public static HashMap<String, Integer> Headers = new HashMap<String, Integer>();
	public static HashMap<String, HashMap<Integer, String>> Data = new HashMap<String, HashMap<Integer, String>>();
	public static HashMap<String, TestCase> testCasesToExecute = new HashMap();
	public static HashMap<String, String> AttributeValuelist = new HashMap();
	public static HashMap<String, LinkedHashMap<Integer, LinkedList<String>>> allTestSuit = new HashMap<String, LinkedHashMap<Integer, LinkedList<String>>>();
	public static Hashtable<String, String> TestSuite = new Hashtable<String, String>();
	public static HashMap<Integer, String> TestIndex = new HashMap<Integer, String>();
	public static int indexVal = 0;
	public static HashMap<String, String> TestSuites = new HashMap<String, String>();
	public static HashMap<String, Boolean> FailedTests = new HashMap<String, Boolean>();
	public static int headerseq = 0;
	public static String AppName;
	public static String AppVersion;
	public static String TestDataFileName;
	public static String TestConfigFileName;
	public static String CurrentApplicationLogPath;
	public static String CurrentExecutionLogPath;
	public static String CurrentSystemLogPath;
	public static String RunReport;
	public static String CurrentExecTestCaseName;
	public static String ScreenShotPath;
	public static int ScreenShotNumber = 0;
	public static String reRunOnFail;
	public static String screenshotOnFail;

	public STAGlobalVar(String appName) {
		this.AppName = appName;
	}

	/**************************************************
	 * #enum Name: TestResult #Description: Test result status enum. This should
	 * be used for logging the Test Status into either log4j logs / Extent
	 * report logs. #Owner: Ashis Kumar Pradhan #Author: Ashis Kumar Pradhan
	 * #Date of creation: 26-Jul-2019 #Name of person modifying: (Tester Name):
	 * #Date of modification: ‘
	 **************************************************/
	public enum TestResult {
		Pass, Fail
	}

	/**************************************************
	 * #enum Name: ProcessState #Description: STA Process state enum. Any
	 * process that is invoked should have some state. This is to ne logged into
	 * log4j / Extent report logs. The process state should start with Initiated
	 * and should be terminated by logging its final state [Stopped / Killed /
	 * Connection Closed]. #Owner: Ashis Kumar Pradhan #Author: Ashis Kumar
	 * Pradhan #Date of creation: 26-Jul-2019 #Name of person modifying: (Tester
	 * Name): #Date of modification: ‘
	 **************************************************/
	public enum ProcessState {
		Initiated, InProgress, Stopped, Hanged, ConnectionClosed, Killed
	}
}