package com.cogmento.testcases.cases;

import com.cogmento.pages.*;
import com.cogmento.reporting.ExtentTestManager;
import com.cogmento.testcases.BaseTest;
import com.cogmento.utils.CustomException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testcontainers.shaded.org.apache.commons.io.filefilter.TrueFileFilter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class CreateDeal extends BaseTest {
    private static final String SHEETNAME = "Deals";
    private final String TESTCASENAME = "TC_01";
    //private CasesPage casesPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private DealsPage dealsPage;
    private String userEmailId, userPwd;
    private HashMap<String, String> testData = new HashMap<>();

    public HashMap createDealData() throws IOException {
        HashMap<String, String> data = new HashMap<>();
        File filename = new File("C:\\Users\\Sathvik\\Downloads\\deals.xlsx");
        FileInputStream finput = new FileInputStream(filename);
        XSSFWorkbook workbook = new XSSFWorkbook(finput);
        XSSFSheet sheet = workbook.getSheet("DealsSheet");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            String key = sheet.getRow(i).getCell(0).getStringCellValue();
            String value = sheet.getRow(i).getCell(1).getStringCellValue();
            data.put(key,value);
        }
        return data;
    }
//        data.put("Title","");
//        data.put("Description","Samsung retook the Deal");
//        return data;
//    }
//
//    public HashMap editDealData(){
//        HashMap<String,String> data = new HashMap<>();
//        data.put("Title","Facebook");
//        data.put("Description","Facebook back in the race");
//        return data;
//    }

    @BeforeMethod
    public void setUp() {

        // Get Test data
        userEmailId = configurationDetails.getUserName();
        userPwd = configurationDetails.getPassword();
//        testData = xlsFile.getExcelRowValuesIntoMapBasedOnKey(SHEETNAME, TESTCASENAME);


        // Initiate Pages
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        dealsPage = new DealsPage(getDriver());
        //casesPage = new CasesPage(getDriver());
    }

    @Test(priority = 1, description = "Create Deals")
    public void testCaseFlow1() throws Exception {
        try {

            // Login Page - Login to Application
            loginPage.loginToApplication(userEmailId, userPwd);
            ExtentTestManager.getTest().pass("Logged in to application");

            // Step 1 :  Create Company
            homePage.selectEntity(EntityPanel.Deals);
            HashMap<String,String> data = createDealData();
            dealsPage.createDeal(data,true);
//            dealsPage.createDeal("GoogleDoodle", "Google Deal Done",true);
        } catch (Exception e) {
            throw new CustomException(e);

        }
    }

    @Test(priority = 2, description = "Edit Deals")
    public void testCaseFlow2() throws Exception {
        try {

            // Login Page - Login to Application
            loginPage.loginToApplication(userEmailId, userPwd);
            ExtentTestManager.getTest().pass("Logged in to application");

            // Step 1 :  Create Company
            homePage.selectEntity(EntityPanel.Deals);
            HashMap<String,String> data = createDealData();
            dealsPage.editDeal("Samsung",data);
//            dealsPage.clickOnSave();
//            dealsPage.editDeal("GoogleDoodle","Apple","Apple Took Over the deal !!");
        } catch (Exception e) {
            throw new CustomException(e);

        }
    }

    @Test(priority = 3, description = "No Mandatory")
    public void testcaseFlow3() throws Exception {
        try {
            loginPage.loginToApplication(userEmailId,userPwd);
            ExtentTestManager.getTest().pass("Logged into Application");

            homePage.selectEntity(EntityPanel.Deals);
            HashMap<String,String> data = createDealData();
            dealsPage.createDeal(data,false);
            dealsPage.verifyErrorMessage("The field Title is required.");
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Test(priority = 4, description = "Delete Deals")
    public void testCaseFlow4() throws Exception {
        try {

            /* Login Page - Login to Application */
            loginPage.loginToApplication(userEmailId, userPwd);
            ExtentTestManager.getTest().pass("Logged in to application");

            // Step 1 :  Create Company
            homePage.selectEntity(EntityPanel.Deals);
            dealsPage.deleteDeal("GoogleDoodle","Delete",true);
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        homePage.selectEntity(EntityPanel.Deals);

        //casesPage.deleteAndPurgeCase(testData.get("Title"));
    }
}