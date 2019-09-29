package STACore.staUtils;

/**************************************************
#Project Name: wallethub
#Class Name: STADataProvider
#Description: Passing the data to data provider
#Input Parameters:
#Owner: Ashis Kumar Pradhan
#Author: Ashis Kumar Pradhan
#Date of creation: 26-Jul-2019
#Name of person modifying: (Tester Name): 
#Date of modification: 
‘**************************************************/

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import STACore.staGlobalVariable.STAGlobalVar;

public class STADataProvider {

	@DataProvider(name = "STADP")
	public static Object[][] TestData(Method testMethod) {
		String currTest = testMethod.getName();
		return new Object[][] { { STAGlobalVar.testCasesToExecute.get(currTest).getId(),
				STAGlobalVar.testCasesToExecute.get(currTest).getName(),
				STAGlobalVar.testCasesToExecute.get(currTest).getDescription(),
				STAGlobalVar.testCasesToExecute.get(currTest).getBrowser(),
				STAGlobalVar.testCasesToExecute.get(currTest).getRunmode(),
				STAGlobalVar.testCasesToExecute.get(currTest).getLocale(),
				STAGlobalVar.testCasesToExecute.get(currTest).getParameter(),
				STAGlobalVar.testCasesToExecute.get(currTest).getResult() } };
	}
}