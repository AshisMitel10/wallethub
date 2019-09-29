package STACore.logger;

/**************************************************
#Project Name: wallethub
#Class Name: STALogger
#Description: This class is responsible for logger and reporting structure
#Owner: Ashis Kumar Pradhan
#Author: Ashis Kumar Pradhan
#Date of creation: 26-Jul-2019
#Name of person modifying: (Tester Name): 
#Date of modification: 
‘**************************************************/

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.apache.commons.exec.OS;
import org.apache.commons.exec.launcher.OS2CommandLauncher;
import org.apache.log4j.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import STACore.staGlobalVariable.STAGlobalVar;
import wallethub.utils.wallethubHelper_FB;

public class STALogger {
	public static String buildID = "Unknown";
	public static Logger SystemLogger;
	public static Logger rootLogger;
	public static Logger AppLogger;
	public static ExtentHtmlReporter ExtHTMLReporter;
	public static ExtentReports ExtHTMLReport;
	public static ExtentTest ExtTest;
	private static String CommonlogFolderPath;

	public static enum LogType {
		Error, Info, Debug
	}

	/**************************************************
	 * #Functionality Name: InitializeLogger 
	 * #Parameters : String appName,String BuildID,boolean isAppLog, boolean isSysLog 
	 * #Description: This is responsible for creating logs and reports 
	 * #Owner: Ashis Kumar Pradhan
	 * #Author: Ashis Kumar Pradhan 
	 * #Date of creation: 26-Jul-2019 
	 * #Name of person modifying: (Tester Name): 
	 * #Date of modification: 
	 **************************************************/
	public static void InitializeLogger(String appName, String BuildID, boolean isAppLog, boolean isSysLog) {
		buildID = BuildID;
		String testRunStartTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		CommonlogFolderPath = System.getProperty("user.dir") + "/Reports/" + appName + "/" + BuildID + "/"
				+ testRunStartTime;
		CommonlogFolderPath = CommonlogFolderPath.replaceAll("\\\\", "/");
		STAGlobalVar.ScreenShotPath = CommonlogFolderPath + "\\ScreenShots";

		// creating the screenshot directory
		File screenshots = new File(STAGlobalVar.ScreenShotPath);

		// Create Initial Folder Structure Based on BuildID, DateTime
		File executionLogDir = new File(CommonlogFolderPath + "\\ExecutionLog");

		// creating the directory if not created
		if (!executionLogDir.exists()) {
			if (executionLogDir.mkdirs()) {
				System.out.println("Log Folder Created - Execution Log");
				STAGlobalVar.CurrentExecutionLogPath = CommonlogFolderPath + "\\ExecutionLog\\";
				STAGlobalVar.CurrentExecutionLogPath = STAGlobalVar.CurrentExecutionLogPath.replaceAll("\\\\", "/");
			} else {
				System.out.println("Failed to create Log Fodler Execution Log!");
			}
		}
		if (isAppLog) {
			File appLogDir = new File(CommonlogFolderPath + "\\ApplicationLog");
			if (!appLogDir.exists()) {
				if (appLogDir.mkdirs()) {
					System.out.println("Log Folder Created - Application Log");
					STAGlobalVar.CurrentApplicationLogPath = CommonlogFolderPath + "\\ApplicationLog\\";
					STAGlobalVar.CurrentApplicationLogPath = STAGlobalVar.CurrentApplicationLogPath.replaceAll("\\\\",
							"/");
				} else {
					System.out.println("Failed to create Log Fodler Application Log!");
				}
			}
		}
		if (isSysLog) {
			File sysLogDir = new File(CommonlogFolderPath + "\\SystemLog");
			if (!sysLogDir.exists()) {
				if (sysLogDir.mkdirs()) {
					System.out.println("Log Folder Created - System Log");
					STAGlobalVar.CurrentSystemLogPath = CommonlogFolderPath + "\\SystemLog\\";
					STAGlobalVar.CurrentSystemLogPath = STAGlobalVar.CurrentSystemLogPath.replaceAll("\\\\", "/");
				} else {
					System.out.println("Failed to create Log Fodler System Log!");
				}
			}
		}

		// Initlalize Log4j
		log4jConfig(isAppLog, isSysLog);
		InitiateExtent();
	}

	/**************************************************
	 * #Functionality Name: log4jConfig #Parameters : boolean applog, boolean
	 * syslog #Description: This method holds the log4j configuraiton. As the
	 * call is made not use the properties, Configuration is made using the
	 * below method. This method needs two boolean values true / false to
	 * initialize Application logging and to initialize System loggging. These
	 * values are set from testng.xml of a certain application. #Owner: Ashis
	 * Kumar Pradhan #Author: Ashis Kumar Pradhan #Date of creation: 26-Jul-2019
	 * #Name of person modifying: (Tester Name): #Date of modification: ‘
	 **************************************************/
	public static void log4jConfig(boolean applog, boolean syslog) {

		PatternLayout layout = new PatternLayout();
		String conversionPattern = "%-7p %d [%t] %c %x - %m%n";
		layout.setConversionPattern(conversionPattern);

		// creates file System Log appender
		FileAppender CurrentExecutionAppender = new FileAppender();
		CurrentExecutionAppender.setFile(STAGlobalVar.CurrentExecutionLogPath + "Execution.log");
		CurrentExecutionAppender.setLayout(layout);
		CurrentExecutionAppender.activateOptions();

		// configuration of root logger for the framework.
		rootLogger = Logger.getRootLogger();
		rootLogger.setLevel(Level.INFO);
		rootLogger.addAppender(CurrentExecutionAppender);

		// creates a custom logger for System and log messages
		// The default log level for System logger is INFO
		SystemLogger = Logger.getLogger(STALogger.class);
		SystemLogger.setLevel(Level.INFO);

		if (syslog) {
			// creates file System Log appender
			FileAppender CurrentSysAppender = new FileAppender();
			CurrentSysAppender.setFile(STAGlobalVar.CurrentSystemLogPath + "System.log");
			CurrentSysAppender.setLayout(layout);
			CurrentSysAppender.activateOptions();
			SystemLogger.addAppender(CurrentSysAppender);
		}

		// creates a custom logger for application and log messages
		// The default log level for System logger is INFO
		AppLogger = Logger.getLogger(STALogger.class);
		AppLogger.setLevel(Level.INFO);

		if (applog) {
			// creates file System Log appender
			FileAppender CurrentAppAppender = new FileAppender();
			CurrentAppAppender.setFile(STAGlobalVar.CurrentApplicationLogPath + "Application.log");
			CurrentAppAppender.setLayout(layout);
			CurrentAppAppender.activateOptions();
			AppLogger.addAppender(CurrentAppAppender);
		}
	}

	/**************************************************
	 * #Functionality Name: InitiateExtent() #Parameters : N/A #Description:
	 * Initiating Extent Reoport globally. Updating the step can be done using
	 * Log method. #Owner: Ashis Kumar Pradhan #Author: Ashis Kumar Pradhan
	 * #Date of creation: 26-Jul-2019 #Name of person modifying: (Tester Name):
	 * #Date of modification: ‘
	 **************************************************/
	public static void InitiateExtent() {
		File ExtentDir = new File(CommonlogFolderPath + "\\HTMLReport\\");
		String _HTML_Report_Name = "\\" + STAGlobalVar.AppName + "_Automation_Report.html";
		ExtHTMLReporter = new ExtentHtmlReporter(ExtentDir + _HTML_Report_Name);
		ExtHTMLReporter.config().setDocumentTitle("Test Summary Report: " + STAGlobalVar.AppName);
		ExtHTMLReporter.config().setReportName("Test Summary Report: " + STAGlobalVar.AppName);
		ExtHTMLReporter.config().setTheme(Theme.DARK);

		ExtHTMLReport = new ExtentReports();
		ExtHTMLReport.attachReporter(ExtHTMLReporter);
		ExtHTMLReport.setSystemInfo("Exec Platform Os", System.getProperty("os.name"));
		ExtHTMLReport.setSystemInfo(STAGlobalVar.AppName + "-version",
				STAGlobalVar.AppVersion/*
										 * wallethubHelper.MidtransTestConfig.get(
										 * "_PM_SubSystem_Version")
										 */);
	}

	/**************************************************
	 * #Functionality Name: Log() 
	 * #Parameters : N/A #Description: Simple Log
	 * method to log the flow messages into either Execution / Application /System logger. 
	 * #Owner: Ashis Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan
	 * #Date of creation: 26-Jul-2019 #Name of person modifying: (Tester Name):
	 * #Date of modification: 
	 **************************************************/
	public static void Log(String Desc, String Result, STALogger.LogType logType) {
		switch (logType) {
		case Debug:
			rootLogger.debug(Desc + ".And the result is :" + Result);
		case Error:
			rootLogger.error(Desc + ". The error is :" + Result);
		default:
			rootLogger.info(Desc + ".And the result is :" + Result);
		}
	}

	/**************************************************
	 * #Functionality Name: Log() 
	 * #Parameters : N/A 
	 * #Description: Excepton Log method to log the Exception Messgae, Stack Trace with Description.
	 * #Owner: Ashis Kumar Pradhan 
	 * #Author: Ashis Kumar Pradhan 
	 * #Date of creation: 26-Jul-2019 
	 * #Name of person modifying: (Tester Name): 
	 * #Date of modification: 
	 **************************************************/
	public static void Log(String step, Status state) {
		rootLogger.debug("Test Step is : ==> " + step + ". And step execution result is  : ==> " + state);
	}

	/**************************************************
	 * #Functionality Name: Log() #Parameters : N/A #Description: Excepton Log
	 * method to log the Exception Messgae, Stack Trace with Description.
	 * #Owner: Ashis Kumar Pradhan #Author: Ashis Kumar Pradhan #Date of
	 * creation: 26-Jul-2019 #Name of person modifying: (Tester Name): #Date of
	 * modification: ‘
	 **************************************************/
	public static void Log(Exception ex, String Desc, STALogger.LogType logType) {
		switch (logType) {
		case Error:
			rootLogger.error(Desc + ". The Exception is :" + ex);
		default:
			rootLogger.info(Desc + ". The Exception Message is  :" + ex.getMessage() + ". The Stack Trace is : "
					+ ex.getStackTrace());
		}
	}

	/**************************************************
	 * #Functionality Name: InitiateExtTest() #Parameters : N/A #Description:
	 * This is responsible for Initiating the Extent report #Owner: Ashis Kumar
	 * Pradhan #Author: Ashis Kumar Pradhan #Date of creation: 26-Jul-2019 #Name
	 * of person modifying: (Tester Name): #Date of modification: ‘
	 **************************************************/
	public static void InitiateExtTest(String TcName) {
		ExtTest = ExtHTMLReport.createTest(TcName);
	}

	/**************************************************
	 * #Functionality Name: UpdateReport() #Parameters : Status status, String
	 * Desc #Description: This is responsible for updating the report #Owner:
	 * Ashis Kumar Pradhan #Author: Ashis Kumar Pradhan #Date of creation:
	 * 26-Jul-2019 #Name of person modifying: (Tester Name): #Date of
	 * modification: ‘
	 **************************************************/
	public static void UpdateReport(Status status, String Desc) {
		// ExtTest.log(status, Desc);
		ExtTest.createNode(Desc).log(status, Desc);
		if (status == Status.SKIP) {
			ExtTest.log(Status.SKIP, Desc);
		}
	}

	/**************************************************
	 * #Functionality Name: UpdateReport() #Parameters : Status status, String
	 * Desc, String screenshotname #Description: This is responsible for
	 * updating the report #Owner: Ashis Kumar Pradhan #Author: Ashis Kumar
	 * Pradhan #Date of creation: 26-Jul-2019 #Name of person modifying: (Tester
	 * Name): #Date of modification: ‘
	 **************************************************/
	public static void UpdateReport(Status status, String Desc, String screenshotname) throws IOException {
		// ExtTest.log(status, Desc);
		if (status == Status.PASS) {
			ExtTest.createNode(Desc).log(status, Desc);
		} else {
			ExtTest.createNode(Desc).log(status, Desc).addScreenCaptureFromPath(screenshotname);
		}
	}
}
