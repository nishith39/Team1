package com.cogmento.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.xml.sax.Locator;

public class DealsPage extends BasePage{
    public DealsPage(WebDriver driver) {
        super(driver);
    }

    private final By titleDeal = By.xpath("//label[text()='Title']//following-sibling::div//input");
    private final By descriptionDeal = By.xpath("//label[text()='Description']//following-sibling::textarea");

    public void createDeal(String title, String description) throws Exception {
        clickOnCreate();
        enterDealsDetails(title, description);
        clickOnSave();
        checkDealsHeader(title);
    }

    public void clickOnCreate () throws Exception {
        createButton();
    }

    public void enterDealsDetails (String title, String description){
        scriptAction.inputText(titleDeal, title);
        scriptAction.inputText(descriptionDeal, description);
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

}