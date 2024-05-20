package com.cogmento.utils;

import com.cogmento.constants.ApplicationConstants;
import com.epam.healenium.SelfHealingDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.cogmento.utils.CommonUtil.waitUntilTime;


public class WebUtil {
    private static Logger logger = LoggerFactory.getLogger(WebUtil.class);
    private static final long IMPLICIT_TIME = 5;

    private WebDriver driver;
    private Actions action;
    private JavascriptExecutor js;

    public WebUtil(WebDriver driver) {
        this.driver = driver;
        action = new Actions(driver);
        js = (JavascriptExecutor) driver;
    }

    public void clickElement(By locator) {
        getWebElement(locator).click();
        logger.info("Clicked element : " + locator);
    }

    public void clickAndWaitElement(By locator, int timer) {
        getWebElement(locator).click();
        waitUntilTime(timer);
    }

    public void doubleClick(By locator) {
        action.doubleClick(getWebElement(locator));
    }

    public void jsClickElement(By locator) {
        js.executeScript("arguments[0].click();", getWebElement(locator));
    }

    public void inputText(By locator, String sText) {
        getWebElement(locator).sendKeys(sText);
    }

    public void clearAndInputText(By locator, String sText) {
        WebElement ele = getWebElement(locator);
        ele.clear();
        ele.sendKeys(sText);
    }

    public void clearInput(By locator) {
        getWebElement(locator).clear();
    }

    public void scrollToElement(By locator) {
        js.executeScript("arguments[0].scrollIntoView(true);", getWebElement(locator));
    }

    public void hoverOverElement(By locator){
        WebElement ele = getWebElement(locator);
        Actions action = new Actions(driver);
        action.moveToElement(ele).perform();
    }

    public String getText(By locator) {
        try {
            String elText = getWebElement(locator).getText();
            logger.info(String.format("Element { %s } text retrieved : '%s'", locator, elText));
            return elText;
        } catch (Exception e) {
            logger.error("Failed to get element text");
            return null;
        }
    }

    public String getAttribute(By locator, String sAttrKey) {
        return getWebElement(locator).getAttribute(sAttrKey);
    }

    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isAttributePresent(By locator, String sAttrKey) {
        try {
            String value = getWebElement(locator).getAttribute(sAttrKey);
            if (value != null) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean isElementVisible(By locator, long iTimeInSeconds) throws Exception{
        try {
            waitUntilElementIsVisible(locator, iTimeInSeconds);
            logger.info(String.format("%s Element is displayed.", locator.toString()));
            return true;
        } catch (CustomException e) {
            logger.warn("Element was not displayed.", e.getMessage());
            return false;
        }
    }

    public boolean isElementVisible(By locator) throws Exception {
        return isElementVisible(locator, IMPLICIT_TIME);
    }

    public boolean isAlertVisible() {
        try {
            waitForAlert(5);
            logger.info("alert is displayed..");
            return true;
        } catch (NoAlertPresentException e) {
            logger.warn(e.toString());
            return false;
        }
    }

    public void waitUntilElementIsVisible(By locator, long iTimeout) throws Exception {

        try {
            new WebDriverWait(driver, Duration.ofSeconds(iTimeout)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        }catch (Exception e){
            logger.error("Element is not displayed after waiting.." + locator);
            throw new CustomException(e);
        }
    }

    public void waitUntilElementIsVisible(By locator, long iTimeout, String errorMessage) throws Exception {
        try {
            waitUntilElementIsVisible(locator, iTimeout);
        } catch (CustomException e) {
            logger.error(errorMessage, e.getCause());
            throw new CustomException(e);
        }
    }

    public void waitUntilElementIsInVisible(By locator, long iTimeout) {
        new WebDriverWait(driver, Duration.ofSeconds(iTimeout)).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        logger.info("Element is invisible after waiting.. : " + locator);
    }

    public void waitForAlert(long iTimeout) {
        new WebDriverWait(driver, Duration.ofSeconds(iTimeout)).until(ExpectedConditions.alertIsPresent());
    }

    public void waitUntilElementIsClickable(By locator, long iTimeout) {
        new WebDriverWait(driver, Duration.ofSeconds(iTimeout)).until(ExpectedConditions.elementToBeClickable(locator));
        logger.info("Element is clickable after waiting.. : " + locator.toString());
    }

    public void waitTillClickableAndClick(By locator, long iTimeout) {
        for (int counter = 0; counter < iTimeout; counter++) {
            try {
                clickElement(locator);
                break;
            } catch (ElementClickInterceptedException | StaleElementReferenceException | NoSuchElementException e) {
                logger.warn(e.getMessage());
                waitUntilTime(1000);
            }
        }
    }

    public void waitForFrameAndSwitch(By locator, long iTimeout) {
        new WebDriverWait(driver, Duration.ofSeconds(iTimeout)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
        logger.info(String.format("switched to frame %s after waiting..", locator));
    }

    public int getMatchingXpathCount(By locator) {
        return getMatchingWebElements(locator).size();
    }

    public void switchToFrame(By locator) {
        this.driver.switchTo().frame(getWebElement(locator));
    }

    public void switchToDefault() {
        this.driver.switchTo().defaultContent();
    }

    public String takeScreenshotAndSave(String screensShotName) {
        final File scrFile = takeScreenShot();
        final String destDir = ApplicationConstants.SCREENSHOTS_DIR;
        final String destFile = screensShotName + ".png";
        String filePath = destDir + File.separator + destFile;
        try {
            FileUtils.copyFile(scrFile, new File(filePath));
            logger.info("Screenshot saved at..." + filePath);
        } catch (IOException e) {
            logger.error("taking Screenshot failed...", e.getCause());
            throw new RuntimeException("Screenshot not saved properly." + e.getMessage());
        }
        return filePath;
    }

    public String takeScreenshotAndSave() {
        final File scrFile = takeScreenShot();
        final String destDir = ApplicationConstants.SCREENSHOTS_DIR;
        final String destFile = "Screenshot" + "_" + CommonUtil.getTimeStamp() + ".png";
        String filePath = destDir + File.separator + destFile;
        try {
            FileUtils.copyFile(scrFile, new File(filePath));
            logger.info("Screenshot saved at..." + filePath);
        } catch (IOException e) {
            logger.error("taking Screenshot failed..." + e.getMessage());
            throw new RuntimeException("Screenshot not saved properly." + e.getMessage());
        }
        return filePath;
    }

    public void openNewWindowWithURL(String sURL) {
        js.executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(sURL);
    }

    public void closeCurrentWindow() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        this.driver.close();
        if (tabs.size() != 0) {
            driver.switchTo().window(tabs.get(0));
        }
    }

    private File takeScreenShot() {
        SelfHealingDriver screenDriver = (SelfHealingDriver) driver;
        return ((TakesScreenshot) screenDriver.getDelegate())
                .getScreenshotAs(OutputType.FILE);
    }

    public void acceptAlert() {
        this.driver.switchTo().alert().accept();
        logger.info("alert is accepted");
    }

    public void dismissAlert() {
        this.driver.switchTo().alert().dismiss();
        logger.info("alert is dismissed");
    }

    public void switchToWindow(String windowHandle) {
        this.driver.switchTo().window(windowHandle);
        logger.info(String.format("switched to window %s", windowHandle));
    }

    public void switchToNewWindow() {
        String sMainWindow = this.driver.getWindowHandle();
        Set<String> windowHandles = this.driver.getWindowHandles();
        if (windowHandles.size() < 2) {
            throw new NoSuchWindowException("No new window was found available to switch");
        }
        for (String sWin : windowHandles) {
            if (!sWin.equalsIgnoreCase(sMainWindow))
                driver.switchTo().window(sWin);
        }
        logger.info("switched to new window..");
    }

    public WebElement getWebElement(By locator) {
        return this.driver.findElement(locator);
    }

    public List<WebElement> getMatchingWebElements(By locator) {
        try {
            this.driver.manage().timeouts().implicitlyWait(IMPLICIT_TIME, TimeUnit.SECONDS);
            List<WebElement> webElements = this.driver.findElements(locator);
            logger.info("List of WebElements found by locator :" + locator + "Count: " + webElements.size());
            return webElements;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    public void highLightElement(WebElement element) {
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }

    public List<WebElement> findElements(By locator) {
        try {
            List<WebElement> webElements = driver.findElements(locator);
            logger.info("List of WebElements found by locator :" + locator + "Count: " + webElements.size());
            return webElements;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}