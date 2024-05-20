package com.cogmento.pages;

import com.cogmento.constants.ApplicationConstants;
import com.cogmento.utils.CustomException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginPage extends BasePage {
    private static Logger logger = LoggerFactory.getLogger(LoginPage.class);


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private By txtUserEmailID = By.name("email");
    private By txtUserPassword = By.name("password");
    private By btnLogin = By.xpath("//div[text()='Login']");
    private By lnkHome = By.xpath("//i[@class='home icon']");

    public LoginPage loginToApplication(String sUserName, String sPassword) throws Exception {

        scriptAction.waitUntilElementIsVisible(txtUserEmailID, ApplicationConstants.MEDIUM_TIMEOUT, "Username field is not displayed.");
        //enter username
        scriptAction.inputText(txtUserEmailID, sUserName);
        //enter password
        scriptAction.inputText(txtUserPassword, sPassword);
        //click login
        scriptAction.clickElement(btnLogin);
        //wait until Home page is displayed
        scriptAction.waitUntilElementIsVisible(lnkHome, ApplicationConstants.LONG_TIMEOUT, "Home page is not displayed after login. Check your login details.");
        return this;
    }


    public LoginPage waitForEmailFieldVisible() throws Exception {

        scriptAction.waitUntilElementIsVisible(txtUserEmailID, ApplicationConstants.MEDIUM_TIMEOUT, "Username field is not displayed.");
        return this;
    }

    public LoginPage enterEmail(String emailId) {
        scriptAction.inputText(txtUserEmailID, emailId);
        return this;
    }

    public LoginPage enterPwd(String sPassword) {
        scriptAction.inputText(txtUserPassword, sPassword);
        return this;
    }

    public LoginPage clickLogin() {
        scriptAction.clickElement(btnLogin);
        return this;
    }

    //wait until Home page is displayed
    public void waitUntilHomeLinkVisible() throws Exception {
        String noDisplay = "Home page is not displayed after login. Check your login details.";
        scriptAction.waitUntilElementIsVisible(lnkHome, ApplicationConstants.LONG_TIMEOUT, noDisplay);
    }

    public void loginToApp(String sUserName, String sPassword) throws Exception {
        try {
            waitForEmailFieldVisible().
                    enterEmail(sUserName).
                    enterPwd(sPassword).
                    clickLogin().
                    waitUntilHomeLinkVisible();
            sScreenShotFilePath = scriptAction.takeScreenshotAndSave();
        } catch (Exception e){
            throw new CustomException(e);
        }
    }
}
