package com.cogmento.pages;

import com.cogmento.constants.ApplicationConstants;
import com.cogmento.utils.CustomException;
import com.cogmento.utils.WebUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.List;

//this class should have only common web related option
public class BasePage {
    private static Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected final WebUtil scriptAction;
    protected final WebDriver driver;

    public String sScreenShotFilePath;

    private static final By btnNext = By.xpath("//a[@class='icon item']");
    private static final By rubbishBinIcon = By.xpath("//button[@class='ui basic button item']");
    private static final By publicButton = By.xpath("//label[text()='Access']/parent::div//button[@class='ui small fluid positive toggle button']");
    private static final By privateButton = By.xpath("/label[text()='Access']/parent::div//button[@class='ui small fluid negative toggle button']");
    private static final By accessDropDown = By.xpath("//div[@class='ui fluid multiple selection dropdown']");
    private static final By saveBtn = By.xpath("//button[text()='Save']");
    private static final By createButton = By.xpath("//button[@class='ui linkedin button']//i[@class='edit icon']/ancestor::button");
    private static final By addButton = By.xpath("//div[@aria-selected='true']/span[@class='text']/b");
    private static final By errMessages = By.xpath("//span[@class='inline-error-msg']");//Error Messages at fields
    private static final By lengthErrorMsg = By.xpath("//div[@class='ui error floating icon message']"); //Error Messages for max input
    private static final By withoutUsersXpath = By.xpath("//div[text()='Access']/parent::div/span");

    // Dynamcal Elements
    private String rubbishBinOperation = "//button[text()='%s Selected']";
    private String pageNamePath = "//div[@class='ui menu']/a[text()='%s']";
    private String valueNamePath = "//td/a[text()='%s']/ancestor::tr//td[@class='collapsing']";
    private String recordName = "//a[text()='%s']";
    private String popUpOperation = "//div[@class='ui page modals dimmer transition visible active']//button[text()='%s']";
    private String accessDropDownItems = "//div[@class='visible menu transition']//span[text()='%s']";
    private String lnkLeftPaneEntityName = "//div[@id='main-nav']//span[text()='%s']"; //Left Pane or Page Navigation Method
    private String pageVerification = "//span[@class = 'selectable ' and text()='%s']";//Page verification method
    private String dropDownValue = "//div[@aria-expanded='true']/div/div/span[text()='%s']"; //DropDown
    private String dropDownLabelName = "//label[text()='%s']/parent::div/div[@role='listbox']";
    private String popUpHeader = "//div[@class='ui page modals dimmer transition visible active']//div[@class='header' and text()='%s']"; //Pop-up method

    //table actions
    private String viewXpath = "//a[text()='%s']/../following-sibling::td[@class='right aligned collapsing options-buttons-container']//i[@class='unhide icon']";
    private String editXpath = "//a[text()='%s']/../following-sibling::td[@class='right aligned collapsing options-buttons-container']//i[@class='edit icon']";
    private String deleteXpath = "//a[text()='%s']/../following-sibling::td[@class='right aligned collapsing options-buttons-container']//i[@class='trash icon']";

    //SNSComboBox
    private String comboBox = "//label[text()='%s'] //parent:: div //div[@role='combobox']//input";
    private String comboBoxValue = "//div[@aria-selected='true']/span[text()='%s']";
    private String allDetailsVerification = "//div[text()='%s']/ancestor::div/div/p[text()='%s']";


    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.scriptAction = new WebUtil(driver);
    }

    public static Logger getLogger() {
        return logger;
    }
    public String takePageScreenShot() {
        return this.scriptAction.takeScreenshotAndSave();
    }

    public void rubbishBin(String pageName, String valueName, String purpose, String popUpOperation) throws Exception {
        scriptAction.clickElement(rubbishBinIcon);
        scriptAction.waitUntilElementIsVisible(By.xpath(String.format(pageNamePath, pageName)), ApplicationConstants.MEDIUM_TIMEOUT);
        scriptAction.clickElement(By.xpath(String.format(pageNamePath, pageName)));
        scriptAction.waitUntilElementIsVisible(By.xpath(String.format(valueNamePath, valueName)), ApplicationConstants.MEDIUM_TIMEOUT);
        scriptAction.clickElement(By.xpath(String.format(valueNamePath, valueName)));
        scriptAction.waitTillClickableAndClick(By.xpath(String.format(rubbishBinOperation, purpose)), ApplicationConstants.MEDIUM_TIMEOUT);
        performActionsOnPopUp(popUpOperation);
    }

    public void purgeItem(String pageName, String valueName, String purpose, String popUpOperation) throws Exception {
        scriptAction.clickElement(rubbishBinIcon);
        scriptAction.waitUntilElementIsVisible(By.xpath(String.format(pageNamePath, pageName)), ApplicationConstants.MEDIUM_TIMEOUT);
        scriptAction.clickElement(By.xpath(String.format(pageNamePath, pageName)));

        String record = String.format(valueNamePath, valueName);
        List<WebElement> webElements;
        List<WebElement> nextPage;
        boolean flag = true;
        while (flag) {
            webElements = scriptAction.getMatchingWebElements(By.xpath(record));
            if (webElements.size() == 1)
                flag = false;
            else {
                nextPage = scriptAction.getMatchingWebElements(btnNext);
                if (nextPage.size() == 1)
                    nextPage.get(0).click();
            }
        }

        scriptAction.clickElement(By.xpath(String.format(valueNamePath, valueName)));
        scriptAction.waitTillClickableAndClick(By.xpath(String.format(rubbishBinOperation, purpose)), ApplicationConstants.MEDIUM_TIMEOUT);
        performActionsOnPopUp(popUpOperation);
    }

    public void checkRecordDisplayed(String sSearchRecord) throws Exception {
        recordVerification(sSearchRecord, true);
    }

    public void checkRecordNotDisplayed(String sSearchRecord) throws Exception {
        recordNotFoundVerification(sSearchRecord);
    }

    private boolean recordVerification(String recordValue, boolean expectedConditions) throws Exception {
        String record = String.format(recordName, recordValue);
        Thread.sleep(2000);
        boolean d = scriptAction.isElementVisible(By.xpath(record), ApplicationConstants.SHORT_TIMEOUT);
        if (!d == expectedConditions) {
            throw new Exception("Record verification failed");
        }
        return true;
    }

    private boolean recordNotFoundVerification(String recordValue) throws Exception {
        String record = String.format(recordName, recordValue);
        List<WebElement> webElements = scriptAction.getMatchingWebElements(By.xpath(record));
        if (!webElements.isEmpty()) {
            throw new Exception("Record verification failed");
        }
        return true;
    }

    public void performActionsOnPopUp(String operation) throws Exception {
        // String operationLoc = String.format(popUpOperation,operation);
        scriptAction.waitUntilElementIsVisible(By.xpath(String.format(popUpOperation, operation)), ApplicationConstants.MEDIUM_TIMEOUT);
        scriptAction.clickAndWaitElement(By.xpath(String.format(popUpOperation, operation)), 1000);
    }

    //deleteRecord("qualizeal","Cancel" or "Delete")
    public void deleteRecord(String sValue, String popUpOperation) throws Exception {
        performTableOperation(sValue, "delete");
        checkPopupIsDisplayed("Confirm Deletion");
        performActionsOnPopUp(popUpOperation);
        pageRefresh();
        checkRecordNotDisplayed(sValue);
    }

    /*Page Navigation Method*/
    public void selectEntity(String sEntityName) throws Exception {
        String pageName = String.format(lnkLeftPaneEntityName, sEntityName);
        scriptAction.isElementVisible(By.xpath(pageName));
        scriptAction.clickElement(By.xpath(pageName));
        checkPageHeader(sEntityName);
    }

    /*Page Verification Method*/
    public void checkPageHeader(String sValue) throws Exception {
        String pageName = String.format(pageVerification, sValue);
        scriptAction.waitUntilElementIsVisible(By.xpath(pageName), ApplicationConstants.MEDIUM_TIMEOUT);
    }

    /*DropDown Method*/
    public void selectItemFromDropdown(String sDropdownLocator, String sSearchValue) throws Exception {
        /*Select DropDown*/
        String dpLabelLoc = String.format(dropDownLabelName, sDropdownLocator);
        scriptAction.waitUntilElementIsVisible(By.xpath(dpLabelLoc), ApplicationConstants.MEDIUM_TIMEOUT);
        scriptAction.clickElement(By.xpath(dpLabelLoc));
        /*Select option from DropDown*/
        String value = String.format(dropDownValue, sSearchValue);
        scriptAction.waitUntilElementIsVisible(By.xpath(value), ApplicationConstants.MEDIUM_TIMEOUT);
        scriptAction.clickElement(By.xpath(value));
    }

    /*Create button Method*/
    public void createButton() throws Exception {
        scriptAction.waitUntilElementIsVisible(createButton, ApplicationConstants.MEDIUM_TIMEOUT);
        scriptAction.clickElement(createButton);
    }

    public void scroll() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,400)");

    }

    //Table operations methods
    public void performTableOperation(String sSearchValue, String operation) throws Exception {
        switch (operation.toLowerCase()) {
            case "view": {
                scriptAction.waitUntilElementIsVisible(By.xpath(String.format(viewXpath, sSearchValue)), ApplicationConstants.SHORT_TIMEOUT, "Record Not Found");
                scriptAction.clickElement(By.xpath(String.format(viewXpath, sSearchValue)));
                //Wait till the page is displayed
                break;
            }
            case "edit": {
                scriptAction.waitUntilElementIsVisible(By.xpath(String.format(editXpath, sSearchValue)), ApplicationConstants.SHORT_TIMEOUT, "Record Not Found");
                scriptAction.clickElement(By.xpath(String.format(editXpath, sSearchValue)));
                //Wait till the page is displayed
                break;
            }
            case "delete": {
                scriptAction.waitUntilElementIsVisible(By.xpath(String.format(deleteXpath, sSearchValue)), ApplicationConstants.SHORT_TIMEOUT, "Record Not Found");
                scriptAction.clickElement(By.xpath(String.format(deleteXpath, sSearchValue)));
                break;
            }
        }
    }
    public boolean check(String locator){
        try{
            driver.findElement(By.xpath(locator));
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    //Search and Select ComboBox
    public boolean searchNSelectItemFromList(String sSearchLocator, String value) throws Exception {
        try {
            /*Select ComboBox*/
            String cBName = String.format(comboBox, sSearchLocator);
            scriptAction.isElementVisible(By.xpath(cBName), ApplicationConstants.MEDIUM_TIMEOUT);
            /*Click on selected ComboBox*/
            scriptAction.clickElement(By.xpath(cBName));
            /*Enter value in ComboBox*/
            scriptAction.inputText(By.xpath(cBName), value);
            /*verify weather value is exists or not*/
            String valueOfCB = String.format(comboBoxValue, value);
            boolean verify = check(valueOfCB);
            /*If value exists select from it*/
            if (verify) {
                scriptAction.clickElement(By.xpath(valueOfCB));
                /*If value doesn't exist Add it*/
            } else {
                scriptAction.waitTillClickableAndClick(addButton, ApplicationConstants.MEDIUM_TIMEOUT);
            }
//            scriptAction.clickElement(By.xpath(cBName));
            return true;

        }catch (CustomException e){
            throw new CustomException(e);
        }
    }

    public boolean searchAndSelectItemFromList(By locator, String value) {

        boolean foundElement = false;
        try {
            if (scriptAction.isElementVisible(locator)) {
                List<WebElement> webElements = scriptAction.getMatchingWebElements(locator);
                for (WebElement element : webElements)
                    if (element.getText().equals(value)) {
                        element.click();
                        foundElement = true;
                        break;
                    }
                logger.info(String.format("Selected %s from locator : %s", value, locator));
            }
        } catch (Exception e) {
            logger.error(String.format("Failed to select %s from locator : %s", value, locator));
            logger.error(e.getMessage());
            Assert.fail("Failed to select element" + e.getMessage());
        }
        return foundElement;
    }

    public void verifyErrorMessage(String sExpectedErrorMessage) throws Exception {
        try {
            String sActualErrorMessage = getFieldErrorMessages();
            scriptAction.waitUntilElementIsVisible(errMessages, ApplicationConstants.MEDIUM_TIMEOUT); //wait until the error message is display
            Assert.assertTrue(sExpectedErrorMessage.contains(sActualErrorMessage), "error message is not matched");
            logger.info("Expected Error Message displayed : " + sExpectedErrorMessage);
        }catch (CustomException e){
            logger.error("Expected Error message is not matched");
            throw new CustomException(e);
        }
    }

    private String getFieldErrorMessages() {

        String sAllErrorMessage = "";
        List<WebElement> listOfElements = scriptAction.getMatchingWebElements(errMessages);
        //Loop all the error message
        for (int iCounter = 0; iCounter < listOfElements.size(); iCounter++) {
            String sCurrentMessage;
            sCurrentMessage = listOfElements.get(iCounter).getText();
            sAllErrorMessage = sAllErrorMessage.concat(sCurrentMessage).concat(",");
        }
        //Remove last character
        if (sAllErrorMessage.length() > 0) {
            sAllErrorMessage = sAllErrorMessage.substring(0, sAllErrorMessage.length() - 1);
        }
        return sAllErrorMessage;
    }

    /*Page Refresh Method*/
    public void pageRefresh() {
        scriptAction.refresh();
    }

    public void pageRefresh(By elementXpath, Long timeOut) throws Exception {
        boolean verify = scriptAction.isElementVisible(elementXpath, timeOut);
        if (verify == false) {
            pageRefresh();
        }
    }

    /*PopUp verification*/
    public void checkPopupIsDisplayed(String sHeaderName) throws Exception {
        scriptAction.waitUntilElementIsVisible(By.xpath(String.format(popUpHeader, sHeaderName)), ApplicationConstants.MEDIUM_TIMEOUT, "Its not Displayed");
    }

    /*Private or public access */
    public void setPrivateOrPublic(String operation, String accessNames) throws Exception {
        //access operation public
        switch (operation) {
            case "public":
                //verify access in public or not if its in private change it to public
                boolean action = scriptAction.isElementVisible(publicButton, ApplicationConstants.MEDIUM_TIMEOUT);
                //access operation for private
                if (action == false) {
                    scriptAction.clickElement(privateButton);
                }
                break;
            case "private":

                //click on public and make it private
                scriptAction.waitTillClickableAndClick(publicButton, ApplicationConstants.MEDIUM_TIMEOUT);
                //click accessDropdown

                if (accessNames.length() > 0) {
                    String[] names = accessNames.split(",");
                    scriptAction.waitTillClickableAndClick(accessDropDown, ApplicationConstants.MEDIUM_TIMEOUT);
                    //getting elements from the dropdown and storing in list as web elements
                    for (String sEntity : names) {
                        String updatedLoc = String.format(accessDropDownItems, sEntity);
                        scriptAction.clickElement(By.xpath(updatedLoc));
                        //check added or not
                    }
                    break;
                }
        }
    }

    public void privateAccessValidation(String byUser) throws Exception {
        switch (byUser) {
            case "withOutUsers":
                scriptAction.waitUntilElementIsVisible(withoutUsersXpath, ApplicationConstants.MEDIUM_TIMEOUT);
                String actual = scriptAction.getText(withoutUsersXpath);
                String expected = "Only you can see this record.";
                Assert.assertEquals(actual, expected);
                break;

            case "withUser":
                scriptAction.waitUntilElementIsVisible(withoutUsersXpath, ApplicationConstants.MEDIUM_TIMEOUT);
                String actual1 = scriptAction.getText(withoutUsersXpath);
                System.out.println(actual1);
                break;
        }
    }

    public void saveButton() throws Exception {
        scriptAction.waitUntilElementIsVisible(saveBtn, ApplicationConstants.MEDIUM_TIMEOUT);
        scriptAction.clickElement(saveBtn);
    }

    public void editRecordVerification(String labelName, String sValue) {
        String re = String.format(allDetailsVerification, labelName, sValue);
        System.out.println(re);
        // scriptAction.waitUntilElementIsVisible(By.xpath(re),ApplicationConstants.MEDIUM_TIMEOUT);
    }




    //Usage selectToggleButton("Closed","ON") or selectToggleButton("Do not Text","OFF")
    public boolean selectToggleButton(String sToggleLabel, String sOperation) {
        //sOperation = "ON","OFF"
        return true;
    }
}