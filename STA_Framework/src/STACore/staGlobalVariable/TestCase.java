package STACore.staGlobalVariable;

/**************************************************
#Project Name: wallethub
#Class Name: TestCase
#Description: This class is responsible for Initiating the browser and making all desired capability related to browser
#Owner: Ashis Kumar Pradhan
#Author: Ashis Kumar Pradhan
#Date of creation: 26-Jul-2019
#Name of person modifying: (Tester Name): 
#Date of modification: 
‘**************************************************/

import java.util.List;

public class TestCase {

	private String id;
	private String name;
	private String parameter;
	private String browser;
	private String description;
	private String locale;
	private String result;
	private String classname;
	private String runmode;
	private int retryCount;
	private List<String> attributeList;

	/**************************************************
	 * #Functionality Name: TestCase #Description: This is responsible for
	 * getting the test case data #Owner: Ashis Kumar Pradhan #Author: Ashis
	 * Kumar Pradhan #Date of creation: 26-Jul-2019 #Name of person modifying:
	 * (Tester Name): #Date of modification: ‘
	 **************************************************/
	public TestCase(String id, String name, String description, String browser, String runmode, String locale,
			String parameter, String result) {
		this.id = id;
		this.name = name;
		this.parameter = parameter;
		this.browser = browser;
		this.runmode = runmode;
		this.description = description;
		this.locale = locale;
		this.result = result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRunmode() {
		return runmode;
	}

	public void setRunmode(String runmode) {
		this.runmode = runmode;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public List<String> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(List<String> attributeList) {
		this.attributeList = attributeList;
	}
}
