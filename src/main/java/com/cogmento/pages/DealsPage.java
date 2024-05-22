package com.cogmento.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.xml.sax.Locator;

public class DealsPage extends BasePage{
    public DealsPage(WebDriver driver) {
        super(driver);
    }

    private By titleDeal = By.xpath("//label[text()='Title']//following-sibling::div//input");
    private By descriptionDeal = By.xpath("//label[text()='Probability']//following-sibling::div//input");
    private By probabilityDeal = By.xpath("//input[@name='probability']");


    public void createDeal(String title, String description,String probability) throws Exception {
        clickOnCreate();
        enterDealsDetails(title, description,probability);
        clickOnSave();
        checkDealsHeader(title);
    }

    public void editDeal(String description,String probability) throws Exception {
        clickOnCreate();
        enterEditDealsDetails(description,probability);
        clickOnSave();
    }


    public void clickOnCreate () throws Exception {
        createButton();
    }

    public void enterDealsDetails (String title, String description, String probability){
        scriptAction.inputText(titleDeal, title);
        scriptAction.inputText(descriptionDeal, description);
        scriptAction.inputText(probabilityDeal, probability);

    }
    public void enterEditDealsDetails ( String description, String probability){

        scriptAction.inputText(descriptionDeal, description);
        scriptAction.inputText(probabilityDeal, probability);

    }

    public void clickOnSave () throws Exception {
        saveButton();
    }

    public void checkDealsHeader (String title) throws Exception {
        checkPageHeader(title);
    }

}