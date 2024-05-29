package com.cogmento.testcases;

import com.cogmento.beans.ConfigurationDetails;
import com.cogmento.constants.ApplicationConstants;
import com.cogmento.listeners.CustomNGListener;
import com.cogmento.listeners.RetryAnalyzerListener;
import com.cogmento.reporting.ConvertHTMLToJson;
import com.cogmento.reporting.ExtentManager;
import com.cogmento.reporting.ExtentTestManager;
import com.cogmento.support.BrowserFactory;
import com.cogmento.support.ExcelDataReader;
import com.cogmento.support.FilesHelper;
import com.cogmento.utils.ConfigurationDetailsUtil;
import com.cogmento.utils.LogSorter;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;

@Listeners({CustomNGListener.class, RetryAnalyzerListener.class})
public class BaseTest extends BrowserFactory {
    private static Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected static ConfigurationDetails configurationDetails;
    protected static ExcelDataReader xlsFile = null;


    //execute the static block first
    static {
        configurationDetails = ConfigurationDetailsUtil.getInstance().getConfigurationDetails();
        xlsFile = new ExcelDataReader(configurationDetails.getTestDataFileLocation());
    }

    @BeforeSuite(alwaysRun = true)
    public void setUpSuite() {
        logger.debug("initializing logs and reports, creating directories for test runs ....");
        createDirectories();
        ExtentManager.initExtentReport(configurationDetails);
        //LogSorter.archieveLogFile();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        ExtentManager.getInstance().flush();
        logger.debug("in suite teardown..");
        //LogSorter.sortLog();
        String resultFilePath = configurationDetails.getResultJSONFile();
        ConvertHTMLToJson.convertHTMLReportToJSONReport(resultFilePath);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(ITestContext iTestContext, Method method) {
        Class testClass;
        try {
            testClass = Class.forName(this.getClass().getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        logger.info("test started: " + testClass.getCanonicalName() + "." + method.getName());
        ExtentTestManager.startTest(testClass.getCanonicalName());
        startSession(configurationDetails.getBrowserDetails());
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        iTestContext.setAttribute("driver", getDriver());
        iTestContext.setAttribute("test-name", method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestContext iTestContext, Method method) {
        WebDriver driver = BrowserFactory.getDriver();
        if (driver != null) {
            closeSession();
        }
        ExtentTestManager.endTest();
        logger.info("test ended: " + method.getName());
    }

    private void createDirectories() {
        FilesHelper.createDirectory(ApplicationConstants.REPORTS_FILE_PATH);
        FilesHelper.createDirectory(ApplicationConstants.SCREENSHOTS_DIR);
    }
}