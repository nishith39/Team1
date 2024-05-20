package com.cogmento.utils;

import com.cogmento.beans.BrowserDetails;
import com.cogmento.beans.ConfigurationDetails;
import com.cogmento.config.ApplicationConfig;
import org.aeonbits.owner.ConfigFactory;


public class ConfigurationDetailsUtil {

    private static ConfigurationDetails configurationDetails;
    private static ConfigurationDetailsUtil configurationDetailsUtil;

    public ConfigurationDetails getConfigurationDetails() {
        if (configurationDetails == null) {
            configurationDetails = getDetailsFromPropertyFile();
        }
        return configurationDetails;
    }

    public static ConfigurationDetailsUtil getInstance() {
        if (configurationDetailsUtil == null) {
            return configurationDetailsUtil = new ConfigurationDetailsUtil();
        } else {
            return configurationDetailsUtil;
        }
    }

    public ConfigurationDetails getDetailsFromPropertyFile() {
        ConfigurationDetails configurationDetails = new ConfigurationDetails();
        ApplicationConfig appConfig = ConfigFactory.create(ApplicationConfig.class);
        BrowserDetails browserDetails = new BrowserDetails();

        browserDetails.setBrowserName(appConfig.browserName());
        browserDetails.setApplicationURL(appConfig.applicationURL());

        configurationDetails.setBrowserDetails(browserDetails);
        configurationDetails.setUserName(appConfig.userName());
        configurationDetails.setPassword(appConfig.password());
        configurationDetails.setTestDataFileLocation(appConfig.testDataLocation());
        configurationDetails.setResultJSONFile(appConfig.resultJSONFile());
        return configurationDetails;
    }

}