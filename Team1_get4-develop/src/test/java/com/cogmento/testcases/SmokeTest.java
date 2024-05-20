package com.cogmento.testcases;

import com.cogmento.pages.LoginPage;
import com.cogmento.reporting.ExtentTestManager;
import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {


    @Test(description = "Login to application")
    public void sampleTest() throws Exception {

        //Login to Application
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginToApplication(configurationDetails.getUserName(), configurationDetails.getPassword());
        ExtentTestManager.getTest().pass("Logged in to application");

    }
}

