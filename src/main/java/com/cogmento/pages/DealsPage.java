package com.cogmento.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DealsPage extends BasePage {

    private By title = By.name("title");
   // private By status = By.xpath("(//i[@class='dropdown icon'])[4]");
    //private By inactive = By.name("status");
    private By nextSteps = By.name("next_step");
    private By identifier = By.name("identifier");

    public DealsPage(WebDriver driver) {
        super(driver);

    }

    public void createDeals(String titleName, String stepsField, String identifierField) throws Exception {
        clickOnCreateButton();
        enterDealsDetails(titleName, stepsField, identifierField);
        clikOnDropDown();
        clickOnSave();
        /*if(check){
            verifyPageTitle(headerTitle);
        }*/

    }

    public void clickOnCreateButton() throws Exception {
        createButton();
    }

    public void enterDealsDetails(String titleName, String stepsField, String identifierField) {
        scriptAction.inputText(title, titleName);
        scriptAction.inputText(nextSteps,stepsField );
        scriptAction.inputText(identifier,identifierField);
    }



    public void clikOnDropDown() throws Exception {
        selectItemFromDropdown("Stage", "Qualify");
    }
    public void clickOnSave() throws Exception {
        saveButton();
    }
    public void delete(String title, String operation) throws Exception {
        deleteRecord(title,operation);
    }
   /* public void verifyPageTitle(String headerTitle) throws Exception {
        checkPageHeader(headerTitle);
    }*/



}
