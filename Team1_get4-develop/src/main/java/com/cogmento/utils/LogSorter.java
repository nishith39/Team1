package com.cogmento.utils;

import com.cogmento.constants.ApplicationConstants;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class LogSorter {

    private String timestamp;
    private String level;
    private String testNumber;
    private String message;

    private static String logsDirectoryPath =ApplicationConstants.LOGS_DIR_PATH;
    public LogSorter(String timestamp, String level, String testNumber, String message) {
        this.timestamp = timestamp;
        this.level = level;
        this.testNumber = testNumber;
        this.message = message;
    }

    public String getTestNumber() {
        return testNumber;
    }

    public String toString() {
        return "[" + timestamp + "] [" + level + "] [Test " + testNumber + "] " + message;
    }

    public static String getFileNameInLogDir(){

        logsDirectoryPath = ApplicationConstants.LOGS_DIR_PATH;
        String fileName ="";
        File directory = new File(logsDirectoryPath);
        FilenameFilter fileFilter = (dir, name) -> new File(dir, name).isFile();
        File[] files = directory.listFiles(fileFilter);
        // Check if there is exactly one file in the directory
        if (files != null && files.length == 1) {
            fileName = files[0].getName();
            System.out.println("The single file in the directory is: " + fileName);
        } else {
            System.out.println("The directory does not contain a single file.");
        }
        return fileName;
    }

    public static void sortLog() {

        String logFilePath = logsDirectoryPath +"/" + getFileNameInLogDir(); // Replace with the actual path to your log file
        List<LogSorter> logLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming log lines have the format: [timestamp] [level] [TestNG-test=Regression tests-X] [class:line] message
                String[] parts = line.split("\\s+", 6); // Split into 6 parts at whitespace
                if (parts.length >= 6) {
                    String timestamp = parts[0] + " " + parts[1];
                    String level = parts[2];
                    String testNumber = parts[4].substring(parts[4].lastIndexOf("-") + 1, parts[4].length() - 1);
                    String message = parts[5];
                    logLines.add(new LogSorter(timestamp, level, testNumber, message));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort log lines based on test number
        Collections.sort(logLines, Comparator.comparing(LogSorter::getTestNumber));
        // Write the sorted log lines back to the log file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath))) {
            for (LogSorter logLine : logLines) {
                writer.write(logLine.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void archieveLogFile(){

        String fileName = getPreviousFileToArchive(); // Replace with the file name you want to move
        Path sourceFilePath = Path.of(logsDirectoryPath, fileName);
        Path destinationFilePath = Path.of(ApplicationConstants.LOGS_ARCHIVE_DIR_PATH,fileName);

        try {
            Files.move(sourceFilePath, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPreviousFileToArchive(){
        String directoryPath = logsDirectoryPath; // Replace with the directory path containing the files

        File directory = new File(directoryPath);
        File oldestFile = null;

        FilenameFilter fileFilter = (dir, name) -> new File(dir, name).isFile();
        File[] files = directory.listFiles(fileFilter);// List files in the directory using the filter

        if (files != null && files.length > 0) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified)); //sort files
            oldestFile = files[0];// The oldest file will be the first element in the sorted array

            System.out.println("The oldest file is: " + oldestFile.getName());
        } else {
            System.out.println("No files found in the directory.");
        }
        assert oldestFile != null;
        return oldestFile.getName();
    }
}
