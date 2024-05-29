package com.cogmento.testcases.cases;
import com.cogmento.pages.*;
import com.cogmento.reporting.ExtentTestManager;
import com.cogmento.testcases.BaseTest;
import com.cogmento.utils.CustomException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
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

    public HashMap<String,String> createDeal(){
        HashMap<String, String> data = new HashMap<>();
        data.put("Title", "Samsung");
        data.put("Description", "Galaxy is being launched");
        return data;
    }
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

    }
    @Test(description = "Create Deals")
    public void testCaseFlow() throws Exception {
        try {
            // Login Page - Login to Application
            loginPage.loginToApp(userEmailId, userPwd);
            ExtentTestManager.getTest().pass("Logged in to application");
            // Step 1 :  Create Deal
            homePage.selectEntity(EntityPanel.Deals);
            HashMap<String,String> data = createDeal();
            //casesPage.createCase(testData).verifyCase(testData.get("Title"));
            dealsPage.createDeal(data);
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
