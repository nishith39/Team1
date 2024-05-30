package com.cogmento.pages;

import io.netty.handler.codec.http.DefaultLastHttpContent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class DealsPage extends BasePage{

    private static final By title = By.xpath("//label[text()='Title']//following-sibling::div//input");
    private static final By description = By.xpath("//label[text()='Description']//following-sibling::textarea");

    public DealsPage(WebDriver driver) {
        super(driver);
    }

    public DealsPage createDeal(HashMap<String, String> testData, boolean check) throws Exception {
        clickOnCreate();
        enterDealsDetails(testData);
        clickOnSave();
        if(check) {
            checkHeader(testData.get("Title"));
        }
        return this;
    }

    public DealsPage clickOnCreate() throws Exception {
        createButton();
        return this;
    }

    public DealsPage enterDealsDetails(HashMap<String, String> testData) {
        if(testData.containsKey("Title")) scriptAction.clearAndInputText(title, testData.get("Title"));
        if(testData.containsKey("Description")) scriptAction.clearAndInputText(description, testData.get("Description"));
        return this;
    }

    public DealsPage clickOnSave() throws Exception {
        saveButton();
        return this;
    }

    public DealsPage checkHeader(String header) throws Exception {
        checkPageHeader(header);
        return this;
    }

    public DealsPage editDeal(String dealTitle, HashMap<String, String> newData) throws Exception {
        performTableOperation(dealTitle, "edit");
        enterDealsDetails(newData);
        clickOnSave();
        return this;
    }

    public DealsPage deleteDeal(String dealTitle) throws Exception {
        deleteRecord(dealTitle, "Delete");
        return this;
    }

}
