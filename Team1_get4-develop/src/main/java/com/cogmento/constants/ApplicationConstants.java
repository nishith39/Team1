package com.cogmento.constants;

import java.io.File;

public class ApplicationConstants {

    public static final String AUT = "QA";   //QA, STAGING etc.
    public static final String OS = System.getProperty("os.name");
    public static final String PROJECT_WORKING_DIR = System.getProperty("user.dir");
    public static final String AUT_PROPERTY_FILE = "application.properties";
    public static final String EXTENT_REPORT_FILE_NAME = "Test-Automaton-Report.html";
    public static String REPORTS_FILE_PATH = PROJECT_WORKING_DIR + File.separator + "reports";
    public static final String SCREENSHOTS_DIR = REPORTS_FILE_PATH + File.separator + "screenshots";
    public static String LOGS_DIR_PATH = PROJECT_WORKING_DIR + File.separator + "logs";
    public static String LOGS_ARCHIVE_DIR_PATH = PROJECT_WORKING_DIR + File.separator + "logs\\archive";
    public static final long SHORT_TIMEOUT = 5;
    public static final long MEDIUM_TIMEOUT = 20;
    public static final long LONG_TIMEOUT = 45;

    public static final String errorMessageForLengthCompanies = "Validation error: Name is longer than 250 characters";
    public static final String errorMessageForMandatoryFieldNameCompany = "The field Name is required.";
    public static final String errorMessageForContactFirstName = "Validation error: First Name is longer than 80 characters";
    public static final String errorMessageForContactLastName = "Validation error: Last Name is longer than 80 characters";
    public static final String errorMessageForContactMiddleName = "Validation error: Middle Name(s) is longer than 150 characters";
    public static final String errInlineMsgForContactFirstName = "The field First Name is required.";
    public static final String errInlineMsgForContactLastName = "The field Last Name is required.";
    public static final String errorMessageForLengthDeals = "Title is longer than 350 characters cannot POST https://api.cogmento.com/api/1/deals/ (400)";
    public static final String errorMessageForMandatoryFieldNameDeal = "The field Title is required.";
}
