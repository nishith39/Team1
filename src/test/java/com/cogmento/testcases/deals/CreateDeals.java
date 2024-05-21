package com.cogmento.testcases.deals;

import com.cogmento.pages.*;
import com.cogmento.reporting.ExtentTestManager;
import com.cogmento.testcases.BaseTest;
import com.cogmento.utils.CustomException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

public class CreateDeals extends BaseTest {
    private static final String SHEETNAME = "Deals";
    private final String TESTCASENAME = "TC_01";
    //private CasesPage casesPage;
    private DealsPage dealsPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private String userEmailId, userPwd;
    private HashMap<String, String> testData = new HashMap<>();

    @BeforeMethod
    public void setUp() {

        // Get Test data
        userEmailId = configurationDetails.getUserName();
        userPwd = configurationDetails.getPassword();
        //testData = xlsFile.getExcelRowValuesIntoMapBasedOnKey(SHEETNAME, TESTCASENAME);


        // Initiate Pages
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        dealsPage = new DealsPage(getDriver());
        //casesPage = new CasesPage(getDriver());
    }

    @Test(description = "Create Deals")
    public void testCaseFlow() throws Exception {
        try {

            // Login Page - Login to Application
            loginPage.loginToApp(userEmailId, userPwd);
            ExtentTestManager.getTest().pass("Logged in to application");

            // Step 1 :  Create Company
            homePage.selectEntity(EntityPanel.Deals);
            //casesPage.createCase(testData).verifyCase(testData.get("Title"));

            dealsPage.createDeals("QzDeals", "Hi Ramya", "DEAl_123");
            homePage.selectEntity(EntityPanel.Deals);
            dealsPage.delete("ramya","Delete");


        } catch (Exception e) {
            throw new CustomException(e);

        }
    }

//    @AfterMethod
//    public void tearDown() throws Exception {
//        homePage.selectEntity(EntityPanel.Deals);
//        //casesPage.deleteAndPurgeCase(testData.get("Title"));
//    }
}