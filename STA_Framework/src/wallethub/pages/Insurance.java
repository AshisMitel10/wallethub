package wallethub.pages;

/**************************************************
#Project Name: wallethub
#Page Name: insurance comment
#Description: This page is the object repo for wallethub insurance page 
#Owner: Ashis Kumar Pradhan
#Author: Ashis Kumar Pradhan
#Date of creation: 26-sep-2019
#Name of person modifying: (Tester Name): 
#Date of modification: 
**************************************************/

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

//page class in object repository
public class Insurance {

	public WebDriver driver;

	public Insurance(WebDriver driver) {
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
	 
	 *                                       WALLETHUB PAGE OBJECTS BY ASHIS
	 ------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Page name : Login Page -> User ID field
	 **/
	@FindBy(xpath = "//span[text()='Login']")
	public WebElement WHLogin_span;
	
	/**
	 * Page name : Login Page -> User ID field
	 **/
	@FindBy(xpath = "//input[@placeholder='Email Address']")
	public WebElement WHUID_input;

	/**
	 * Page name : Login Page -> Secreat Field
	 **/
	@FindBy(xpath = "//input[@placeholder='Password']")
	public WebElement WHSecreat_input;

	/**
	 * Page name : Login Page -> Login Button Tab
	 **/
	@FindBy(xpath = "//span[text()='Login']")
	public WebElement WHLogIn_input;
	
	/**
	 * Page name : Home Page -> Review star BG Color
	 **/
	@FindBy(xpath="(//div[@class='rating-box-wrapper'])[3]/*[4]/*/*[@fill='#4ae0e1']")
	public WebElement star4th_div;
	
	/**
	 * Page name : Home Page -> Review star parent action
	 **/
	@FindBy(css=".review-action .rvs-star-svg:nth-child(4) path:nth-child(1)")
	public WebElement star4thCSS_div;
	

	/**
	 * Page name : Home Page -> select dropdown menu
	 **/
	@FindBy(xpath="//section[@id='reviews-section']/modal-dialog/div/div/write-review/div/ng-dropdown/div/span")
	public String insurance_dropdown;
	
	/**
	 * Page name : Home Page -> select value from dropdown menu
	 **/
	@FindBy(xpath="//section[@id='reviews-section']/modal-dialog/div/div/write-review/div/ng-dropdown/div/ul/li[2]")
	public String insurance_dropdown_sevValue;
	
	/**
	 * Page name : Home Page -> Comment text area
	 **/
	@FindBy(xpath="//section[@id='reviews-section']/modal-dialog/div/div/write-review/div/div/textarea")
	public WebElement comment_Textbox;
	
	/**
	 * Page name : Home Page -> Submit button
	 **/
	@FindBy(xpath="//div[contains(text(),'Submit')]")
	public WebElement submit_Button;
	
	
	/**
	 * Page name : Home Page -> Login tab
	 **/
	@FindBy(xpath="(//a[contains(text(),'Login')])[2]")
	public WebElement login_tab;
	
			
	/**
	* Page name : Home Page -> user id field
	**/
	@FindBy(xpath="//input[@name='em']")
	public WebElement email_input;
			
			
	/**
	 * Page name : Home Page -> secreat field
	 **/
	@FindBy(xpath="//input[@name='pw1']")
	public WebElement secreat_input;
			
			
	/**
	 * Page name : Home Page -> Login Button
	 **/
	@FindBy(xpath="//span[contains(text(),'Login')]")
	public WebElement login_submit;
	
	/**
	 * Page name : Home Page -> Verify review comment text
	 **/
	@FindBy(xpath="//*[contains(text(),'Your review has been posted.')]")
	public WebElement verifyReviewSuccessComment_text;
	
	
	/**
	 * Page name : Home Page -> Continue button
	 **/
	@FindBy(xpath="//div[contains(text(),'Continue')]")
	public WebElement continue_button;
	
	/**
	 * Page name : Home Page -> Locator for text Insurance company text
	 **/
	@FindBy(xpath="(//*[contains(text(),'Test Insurance Company')])[1]")
	public WebElement testInsurance_text;
	

	/*----------------------------------------------------------------------------------------------------------------------
	 *              FACEBOOK PAGE OBJECTS ENDS HERE BY ASHIS                                                                      |
	 ----------------------------------------------------------------------------------------------------------------------*/

}
