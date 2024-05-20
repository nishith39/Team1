package com.cogmento.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.cogmento.constants.ApplicationConstants;
import com.cogmento.reporting.ExtentTestManager;
import com.cogmento.utils.CommonUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;

import java.io.File;
import java.util.Objects;

public class CustomNGListener extends TestListenerAdapter implements IInvokedMethodListener {
    private static Logger logger = LoggerFactory.getLogger(CustomNGListener.class);

    private static final String EMPTY = "";

    @Override
    public void onTestSuccess(final ITestResult tr) {
        super.onTestSuccess(tr);
    }

    @Override
    public void onTestFailure(final ITestResult tr) {

        String failedScreenShotpath = captureBase64ScreenShot(tr.getTestContext()); // String failedScreenShotpath = captureScreenShot(tr.getTestContext());
        logger.error(tr.getThrowable().toString());

        //log fail and take screenshot only when whole test status is pass
        if (ExtentTestManager.getTest().getStatus() == Status.PASS) {
            ExtentTestManager.getTest().fail(tr.getThrowable());
        }
        if (!failedScreenShotpath.equalsIgnoreCase(EMPTY)) {
            //ExtentTestManager.getTest().fail("failed step screenshot", MediaEntityBuilder.createScreenCaptureFromPath(failedScreenShotpath).build());
            ExtentTestManager.getTest().fail("failed step screenshot", MediaEntityBuilder.createScreenCaptureFromBase64String(failedScreenShotpath).build());
        }
        super.onTestFailure(tr);
    }

    @Override
    public void onTestSkipped(final ITestResult tr) {
        ExtentTestManager.getTest().skip("skipping the test");
        super.onTestSkipped(tr);
    }

    private String captureScreenShot(final ITestContext context) {
        WebDriver driver = (WebDriver) context.getAttribute("driver");
        if (driver == null) {
            logger.warn("driver instance is closed. Skipping the screenshot");
            return EMPTY;
        }
        final String destDir = ApplicationConstants.REPORTS_FILE_PATH;
        final String destFile = context.getAttribute("test-name") + "_" + CommonUtil.getTimeStamp() + ".png";
        final String screenShotPath = destDir + File.separator + destFile;

        try {
            final File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(screenShotPath));
        } catch (final Exception e) {
            logger.info("An exception occurred while taking screenshot " + e.getMessage());
        }
        return screenShotPath;
    }

    private String captureBase64ScreenShot(final ITestContext context) {

        String base64Screenshot ="";
        WebDriver driver = (WebDriver) context.getAttribute("driver");
        if (driver == null) {
            logger.warn("driver instance is closed. Skipping the screenshot");
            return EMPTY;
        }
        final String destDir = ApplicationConstants.REPORTS_FILE_PATH;
        final String destFile = context.getAttribute("test-name") + "_" + CommonUtil.getTimeStamp() + ".png";
        final String screenShotPath = destDir + File.separator + destFile;

        try {
            final File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(screenShotPath));
            base64Screenshot ="data:image/png;base64," + ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (final Exception e) {
            logger.info("An exception occurred while taking screenshot " + e.getMessage());
        }

        return  base64Screenshot;
    }


}