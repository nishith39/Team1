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
    private final HashMap<String, String> testData = new HashMap<>();
    private HashMap<String,String> newTestData = new HashMap<>();

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
    public void testCaseCreate() throws Exception {
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
    public void testCaseEdit() throws Exception {
        try {
            newTestData.put("Title","Samsung");
            // Login Page - Login to Application
            loginPage.loginToApplication(userEmailId, userPwd);
            ExtentTestManager.getTest().pass("Logged in to application");
            HashMap<String,String> data = createDealData();
            // Step 1 :  Create Company
            homePage.selectEntity(EntityPanel.Deals);
            dealsPage.clickOnEdit(data,newTestData);
//            dealsPage.clickOnSave();
//            dealsPage.editDeal("GoogleDoodle","Apple","Apple Took Over the deal !!");
        } catch (Exception e) {
            throw new CustomException(e);

        }
    }

    @Test(priority = 3, description = "Max Characters")
    public void testcaseMaxChar() throws CustomException {
        try {
            loginPage.loginToApplication(userEmailId,userPwd);
            ExtentTestManager.getTest().pass("Logged into Application");

            homePage.selectEntity(EntityPanel.Deals);
            HashMap<String,String> data = createDealData();
            data.put("Title","AAAAArt is a powerful medium that transcends cultural and linguistic barriers, fostering a universal dialogue. Through various forms like painting, sculpture, music, and dance, art expresses the complexities of human emotions and experiences. It challenges perspectives, evokes empathy, and inspires change. By reflecting society's values and struggles, art not only captures history but also shapes the future, making it an indispensable aspect of human civilization.");
            dealsPage.createDeal(data,false);
            dealsPage.toastErrorMessage("Title is longer than 350 characters");
        } catch (Exception e) {
            throw new CustomException(e);

        }
    }

    @Test(priority = 4, description = "No Mandatory")
    public void testcaseNoMandate() throws Exception {
        try {
            loginPage.loginToApplication(userEmailId,userPwd);
            ExtentTestManager.getTest().pass("Logged into Application");

            homePage.selectEntity(EntityPanel.Deals);
            HashMap<String,String> data = createDealData();
            data.put("Title","");
            dealsPage.createDeal(data,false);
            dealsPage.verifyErrorMessage("The field Title is required.");
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Test(priority = 5, description = "Delete Deals")
    public void testCaseDelete() throws Exception {
        try {

            /* Login Page - Login to Application */
            loginPage.loginToApplication(userEmailId, userPwd);
            ExtentTestManager.getTest().pass("Logged in to application");

            // Step 1 :  Create Company
            homePage.selectEntity(EntityPanel.Deals);
            HashMap<String,String> data = createDealData();
            dealsPage.deleteDeal(data.get("Title"),"Delete",false);
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