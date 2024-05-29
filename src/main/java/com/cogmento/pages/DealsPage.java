package com.cogmento.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.xml.sax.Locator;

import java.util.HashMap;

import static com.cogmento.utils.CommonUtil.waitUntilTime;

public class DealsPage extends BasePage{
    public DealsPage(WebDriver driver) {
        super(driver);
    }

    private final By titleDeal = By.xpath("//label[text()='Title']//following-sibling::div//input");
    private final By descriptionDeal = By.xpath("//label[text()='Description']//following-sibling::textarea");

    public void createDeal(HashMap<String, String> data) throws Exception {
        clickOnCreate();
        enterDealsDetails(data);
        clickOnSave();
        checkDealsHeader(data.get("Title"));
    }

    public void editDeal(String oldTitle, HashMap<String, String> data) throws Exception {
        performTableOperation(oldTitle, "edit");
        waitUntilTime(2000);
        enterDealsDetails(data);
        clickOnSave();
//        checkDealsHeader(data.get("Title"));
    }

    public void clickOnCreate () throws Exception {
        createButton();
    }

    public void enterDealsDetails (HashMap<String, String> data){

        scriptAction.clearAndInputText(titleDeal, data.get("Title"));
        scriptAction.clearAndInputText(descriptionDeal, data.get("Description"));
    }

    public void clickOnSave () throws Exception {
        saveButton();
    }

    public void checkDealsHeader (String title) throws Exception {
        checkPageHeader(title);
    }

    public void clickOnDelete(String title, String popUpValue) throws Exception {
        deleteRecord(title, popUpValue);
    }

    //public void clickOnDropdown(String )

}