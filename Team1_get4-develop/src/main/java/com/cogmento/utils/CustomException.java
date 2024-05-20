package com.cogmento.utils;

import com.cogmento.reporting.ExtentTestManager;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;

public class CustomException extends Exception {

    public CustomException(Exception e) {

        if(e instanceof TimeoutException) {
            ExtentTestManager.getTest().fail("Time Out Exception : " + e.getMessage());
            Assert.fail(e.getMessage());
        }
        else if (e instanceof WebDriverException) {
            ExtentTestManager.getTest().fail("Selenium Exception : " + e.getMessage());
            Assert.fail(e.getMessage());
        }
        else{
            ExtentTestManager.getTest().fail("General Exception : " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

}

