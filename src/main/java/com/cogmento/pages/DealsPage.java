package com.cogmento.pages;

import com.cogmento.utils.CustomException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/

public class DealsPage extends BasePage{
//    private static final Logger logger = LoggerFactory.getLogger(DealsPage.class);
    public DealsPage(WebDriver driver) {
        super(driver);
    }

    private final By titleDeal = By.xpath("//label[text()='Title']//following-sibling::div//input");
    private final By descriptionDeal = By.xpath("//label[text()='Description']//following-sibling::textarea");
    private final By errorToast = By.xpath("//div[@class=\"content\"]//p");

    public void createDeal(HashMap<String,String> data,boolean check) throws Exception {
        clickOnCreate();
        enterDealsDetails(data);
        clickOnSave();
        if(check) {
            checkDealsHeader(data.get("Title"));
        }
    }

    public void editDeal(String title, HashMap<String,String> data) throws Exception {
        clickOnEdit(title,data);
    }
    public void deleteDeal(String title,String popupValue,boolean look) throws Exception {
        deleteRecord(title,popupValue,look);

    }

    public void clickOnCreate () throws Exception {
        createButton();
    }
    public void enterDealsDetails (HashMap<String,String> data){
        for (String s : data.keySet()){
            if (s.equalsIgnoreCase("Title")){
                scriptAction.inputText(titleDeal,data.get(s));
            }
            if(s.equalsIgnoreCase("Description")){
                scriptAction.inputText(descriptionDeal,data.get(s));
            }
        }
    }

    public void clickOnSave () throws Exception {
        saveButton();
    }

    public void checkDealsHeader (String title) throws Exception {
        checkPageHeader(title);
    }

    public void clickOnEdit(String title,HashMap<String,String> data) throws Exception {
        performTableOperation(title,"edit");

//        scriptAction.clearAndInputText(titleDeal,editTitle);
//        scriptAction.clearAndInputText(descriptionDeal,editDescription);
        enterDealsDetails(data);
    }

    public void maxCharacter(){

    }


}
