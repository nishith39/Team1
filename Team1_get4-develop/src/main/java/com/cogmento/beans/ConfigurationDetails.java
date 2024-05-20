package com.cogmento.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigurationDetails {

    private BrowserDetails browserDetails;
    private String userName;
    private String password;
    private String testDataFileLocation;
    private String resultJSONFile;
}
