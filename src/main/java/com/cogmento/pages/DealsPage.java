package com.cogmento.pages;

import com.cogmento.utils.CustomException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

import static com.cogmento.utils.CommonUtil.waitUntilTime;
/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/

public class DealsPage extends BasePage{
//    private static final Logger logger = LoggerFactory.getLogger(DealsPage.class);
    public DealsPage(WebDriver driver) {
        super(driver);
    }

    private final By titleDeal = By.xpath("//label[text()='Title']//following-sibling::div//input");
    private final By descriptionDeal = By.xpath("//label[text()='Description']//following-sibling::textarea");

    public void createDeal(HashMap<String,String> data,boolean check) throws Exception {
        clickOnCreate();
        enterDealsDetails(data,false);
        clickOnSave();
        if(check) {
            checkDealsHeader(data.get("Title"));
        }
    }

    public void deleteDeal(String title,String popupValue,boolean look) throws Exception {
        deleteRecord(title,popupValue,look);

    }

    public void clickOnCreate () throws Exception {
        createButton();
    }
    public void enterDealsDetails (HashMap<String,String> data,boolean isEdit){
            if (data.containsKey("Title")){
                if (isEdit){
                    scriptAction.clearAndInputText(titleDeal,data.get("Title"));
                } else {
                    scriptAction.inputText(titleDeal,data.get("Title"));
                }
            }
            if(data.containsKey("Description")){
                if (isEdit){
                    scriptAction.clearAndInputText(descriptionDeal,data.get("Description"));
                } else {
                    scriptAction.inputText(descriptionDeal,data.get("Description"));
                }
            }
    }

    public void clickOnSave () throws Exception {
        saveButton();
    }

    public void checkDealsHeader (String title) throws Exception {
        checkPageHeader(title);
    }

    public void clickOnEdit(HashMap<String,String> data,HashMap<String,String> newTestData) throws Exception {
        performTableOperation(data.get("Title"),"edit");
        waitUntilTime(2000);
        enterDealsDetails(newTestData,true);
        clickOnSave();
    }

    public void maxCharacter(){

    }


}
