package wallethub.pages;

/**************************************************
#Project Name: wallethub
#Page Name: shoping
#Description: This page is the object repo for facebook page 
#Owner: Ashis Kumar Pradhan
#Author: Ashis Kumar Pradhan
#Date of creation: 22-sep-2019
#Name of person modifying: (Tester Name): 
#Date of modification: 
**************************************************/

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

//page class in object repository
public class facebook {

	public WebDriver driver;

	public facebook(WebDriver driver) {
		this.driver = driver;

		// This initElements method will create all WebElements (this
		// initelements methos of pagefactory class for initializing
		// webelements)
		PageFactory.initElements(driver, this);

		// This is a lazy loading, wait will start only if we perform operation
		// on control(after 100 sec if element is not visible to perform an
		// operation, timout exception will appear)
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 100);
		PageFactory.initElements(factory, this);

	}

	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------
	 *   						Below page objects are to be used by the test cases
	 
	 *                                       FACEBOOK PAGE OBJECTS BY ASHIS
	 ------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Page name : Login Page -> User ID field
	 **/
	@FindBy(xpath = "//input[@id='email']")
	public WebElement FBUID_input;

	/**
	 * Page name : Login Page -> Secreat Field
	 **/
	@FindBy(xpath = "//input[@id='pass']")
	public WebElement FBSecreat_input;

	/**
	 * Page name : Login Page -> Login Button
	 **/
	@FindBy(xpath = "//input[@id='u_0_b']")
	public WebElement LogIn_input;
	
	/**
	 * Page name : Home Page -> Status text area
	 **/
	@FindBy(xpath="//textarea")
	public String PostArea_textarea;
	
	/**
	 * Page name : Home Page -> Content Edit Table
	 **/
	@FindBy(xpath="//div[@contenteditable='true']")
	public String ContentTable_div;
	
	
	/**
	 * Page name : Home Page -> Status Post span
	 **/
	@FindBy(xpath="//span[text()='Post']")
	public WebElement StatusPost_span;
	

	/*----------------------------------------------------------------------------------------------------------------------
	 *              FACEBOOK PAGE OBJECTS ENDS HERE BY ASHIS                                                                      |
	 ----------------------------------------------------------------------------------------------------------------------*/

}
