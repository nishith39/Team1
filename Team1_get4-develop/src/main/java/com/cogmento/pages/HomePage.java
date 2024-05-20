package com.cogmento.pages;

import com.cogmento.reporting.ExtentTestManager;
import com.cogmento.utils.CustomException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage extends BasePage {

    private static Logger logger = LoggerFactory.getLogger(BasePage.class);

    private By left_menu = By.xpath("//div[@id='main-nav']");
    private String lnkLeftPaneEntityName = "//div[@id='main-nav']//span[text()='%s']"; //Left PaneL
    private String pageVerification = "//span[@class = 'selectable ' and text()='%s']";
    private By header_name = By.id("top-header-menu");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public By getLeftPanetLocator(EntityPanel entity) {
        String entityName = entity.toString();
        return By.xpath(String.format(lnkLeftPaneEntityName, entityName));

    }

    public HomePage selectEntity(EntityPanel entity) throws Exception {

        try {
            scriptAction.hoverOverElement(left_menu);
            By entityLoc = getLeftPanetLocator(entity);
            scriptAction.clickAndWaitElement(entityLoc, 500);
            scriptAction.hoverOverElement(header_name);
            verifyPageHeader(entity);
            return this;
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    public By getPageVerificationLocator(EntityPanel entity) {

        String entityName = entity.toString();
        return By.xpath(String.format(pageVerification, entityName));
    }

    public String getEntityText(By entityLoc) {
        return scriptAction.getText(entityLoc);
    }

    public void verifyPageHeader(EntityPanel entity) {

        By entityLoc = getPageVerificationLocator(entity);
        String pgHeaderName = getEntityText(entityLoc);
        boolean checkCasePage = pgHeaderName.equals(entity.toString());
        if (checkCasePage) ExtentTestManager.getTest().pass(entity.toString() + " Page Display Successful");
        else ExtentTestManager.getTest().fail(entity.toString() + " Page not displayed");
    }
}
