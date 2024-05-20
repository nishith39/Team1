package com.cogmento.utils;

import com.cogmento.reporting.ExtentTestManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class CommonUtil {
    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }

    public static void waitUntilTime(long iTimeInMilliSeconds) {
        try {
            Thread.sleep(iTimeInMilliSeconds);
        } catch (InterruptedException e) {
        }
    }

    public static String getHostName(){
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            logger.warn("Host name not found for the given machine");
            return "UNKNOWN HOST";
        }
    }

    public static String getCurrentTime(){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        return timeStamp;
    }

    public static String getCurrentTime2(){
        String timeStamp = new SimpleDateFormat("yyMMddHHmmss").format(new java.util.Date());
        return timeStamp;
    }

    public static String getRandomString(String prefix,int length){
        int randomStringLen = length - prefix.length();
        String generatedString = "_"+ RandomStringUtils.randomAlphanumeric(randomStringLen) ;
        return prefix.concat(generatedString);
    }

    public static String getMaxRandomString(int length){
        return RandomStringUtils.randomAlphanumeric(length);
    }

}
