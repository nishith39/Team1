package com.cogmento.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/

public class DealsPage extends BasePage{
//    private static final Logger logger = LoggerFactory.getLogger(DealsPage.class);
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
    public void deleteDeal(String title,String popupValue,boolean check) throws Exception {
        deleteRecord(title,popupValue,check);

    }

    public void clickOnCreate () throws Exception {
        createButton();
    }
    /*public void performActions() throws Exception {
        performTableOperation("Google","view");
    }*/
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

    public void clickOnDelete(String title, String popUpValue,boolean check) throws Exception {
        deleteRecord(title, popUpValue,check);
    }

}
