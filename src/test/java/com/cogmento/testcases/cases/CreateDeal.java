package com.cogmento.testcases.cases;

import com.cogmento.pages.*;
import com.cogmento.constants.ApplicationConstants;
import com.cogmento.reporting.ExtentTestManager;
import com.cogmento.testcases.BaseTest;
import com.cogmento.utils.CustomException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

public class CreateDeal extends BaseTest {
    private static final String SHEETNAME = "Cases";
    private final String TESTCASENAME = "TC_01";
    //private CasesPage casesPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private DealsPage dealsPage;
    private String userEmailId, userPwd;
    private HashMap<String, String> testData = new HashMap<>();

    @BeforeMethod
    public void setUp() {

        // Get Test data
        userEmailId = configurationDetails.getUserName();
        userPwd = configurationDetails.getPassword();
        testData = xlsFile.getExcelRowValuesIntoMapBasedOnKey(SHEETNAME, TESTCASENAME);


        // Initiate Pages
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        dealsPage = new DealsPage(getDriver());
        //casesPage = new CasesPage(getDriver());
    }

    @Test(description = "Create a Deal")
    public void createDeal() throws Exception {
        try {

            // Login Page - Login to Application
            loginPage.loginToApp(userEmailId, userPwd);
            ExtentTestManager.getTest().pass("Logged in to application");

            // Step 1 :  Navigate to Deals Page
            homePage.selectEntity(EntityPanel.Deals);
            ExtentTestManager.getTest().pass("Navigated to the Deals Page");

            // Step 2 :  Create a Deal
            System.out.println(testData);
            dealsPage.createDeal(testData, true);
            ExtentTestManager.getTest().pass("Deal is Created");
//            Thread.sleep(2000);

        } catch (Exception e) {
            throw new CustomException(e);

        }
    }

    @Test(description = "Deleting a deal")
    public void deleteDeal() throws Exception {
        // Login Page - Login to Application
        loginPage.loginToApp(userEmailId, userPwd);
        ExtentTestManager.getTest().pass("Logged in to application");

        // Step 1 :  Navigating to Deals Page
        homePage.selectEntity(EntityPanel.Deals);
        ExtentTestManager.getTest().pass("Navigated to the Deals Page");

        // Step 2 :  Deleting the Deal
        dealsPage.deleteDeal("TestAuto1716978676220");
        ExtentTestManager.getTest().pass("Deleted the Deal");
//        Thread.sleep(2000);
    }

    @Test(description = "Editing a deal")
    public void editDeal() throws Exception {
        // Login Page - Login to Application
        loginPage.loginToApp(userEmailId, userPwd);
        ExtentTestManager.getTest().pass("Logged in to application");

        // Step 1 :  Navigating to Deals Page
        homePage.selectEntity(EntityPanel.Deals);

        // Step 2 :  Edit a Deal
        HashMap<String, String> newData = testData;
        newData.put("Title", "SnapDeal");
        dealsPage.editDeal("TestAuto1716978676220", newData);
//        Thread.sleep(2000);
    }

    @Test(description = "Saving a deal without mandatory fields")
    public void noMandatoryFields() throws Exception {
        // Login Page - Login to Application
        loginPage.loginToApp(userEmailId, userPwd);
        ExtentTestManager.getTest().pass("Logged in to application");

        // Step 1 :  Navigating to Deals Page
        homePage.selectEntity(EntityPanel.Deals);
        ExtentTestManager.getTest().pass("Navigated to the Deals Page");

        // Step 2 :  Create a Deal without mandatory fields
        HashMap<String, String> newData  = testData;
        newData.put("Title", "");
        dealsPage.createDeal(newData, false);
        dealsPage.verifyErrorMessage(ApplicationConstants.errorMessageForMandatoryFieldNameDeal);
        ExtentTestManager.getTest().pass("Tried to create a Deal without mandatory fields");
//        Thread.sleep(2000);
    }

    @Test(description = "Checking maximum characters of Title Field")
    public void maxChar() throws Exception {
        // Login Page - Login to Application
        loginPage.loginToApp(userEmailId, userPwd);
        ExtentTestManager.getTest().pass("Logged in to application");

        // Step 1 :  Navigating to Deals Page
        homePage.selectEntity(EntityPanel.Deals);
        ExtentTestManager.getTest().pass("Navigated to the Deals Page");

        // Step 2 :  Edit a Deal
        HashMap<String, String> newData = testData;
        newData.put("Title", dealsPage.generateRandomString(351));
        dealsPage.createDeal(newData,false);
        dealsPage.toastErrorMessage("Title is longer than 350 characters");

        Thread.sleep(2000);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        homePage.selectEntity(EntityPanel.Deals);
        //casesPage.deleteAndPurgeCase(testData.get("Title"));
    }
}