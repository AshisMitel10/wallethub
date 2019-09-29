package STACore.staUtils;

/**************************************************
 * #Project Name: wallethub #Class Name: STAExceptions #Description: This is a
 * custom exception defined for STA. The constructor takes the Message that we
 * pass while throwing STA exception. Usage : throws new STAExceptions(
 * "Message to be passed to handle.") #Input Parameters: #Owner: Ashis Kumar
 * Pradhan #Author: Ashis Kumar Pradhan #Date of creation: 26-Jul-2019 #Name of
 * person modifying: (Tester Name): #Date of modification: ‘
 **************************************************/

public class STAExceptions extends Exception {

	public STAExceptions(String errorMessage) {
		super(errorMessage);
	}

	public void SeleniumException(String errorMessage) {

	}

	public void SSHException(String errorMessage) {

	}

	public void STASoapException(String errorMessage) {

	}
}
