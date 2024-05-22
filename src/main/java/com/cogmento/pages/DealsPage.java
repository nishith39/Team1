package com.cogmento.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DealsPage  extends BasePage{
    public DealsPage(WebDriver driver) {
        super(driver);
    }

    private final By dealTitle = By.xpath("//label[text()='Title']//following-sibling::div//input");
    private final By dealDescription = By.xpath("//label[text()='Description']//following-sibling::textarea");

    public void createDeal(String title, String description,boolean check) throws Exception {
        clickOnCreate();
        enterDealsDetails(title, description);
        clickOnSave();
        if(check) {
            checkDealsHeader(title);
        }
    }
    public void deleteDeal(String title,String popupValue) throws Exception {
        deleteRecord(title,popupValue);

    }

    public void clickOnCreate () throws Exception {
        createButton();
    }
    /*public void performActions() throws Exception {
        performTableOperation("Google","view");
    }*/
    public void enterDealsDetails (String title, String description){
        scriptAction.inputText(dealTitle, title);
        scriptAction.inputText(dealDescription, description);
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
